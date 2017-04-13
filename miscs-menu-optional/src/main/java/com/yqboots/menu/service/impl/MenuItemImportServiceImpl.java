/*
 * Copyright 2015-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.yqboots.menu.service.impl;

import com.yqboots.menu.MenuItemConstants;
import com.yqboots.menu.cache.MenuItemCache;
import com.yqboots.menu.cache.impl.NullMenuItemCache;
import com.yqboots.menu.core.MenuItem;
import com.yqboots.menu.core.MenuItems;
import com.yqboots.menu.service.MenuItemImportService;
import com.yqboots.menu.service.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;

/**
 * It Manages the MenuItem related functions.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class MenuItemImportServiceImpl implements MenuItemImportService {
    @Autowired
    private MenuItemRepository menuItemRepository;

    /**
     * For marshalling and unmarshalling Menu Item.
     */
    @Autowired
    @Qualifier(MenuItemConstants.BEAN_JAXB2_MARSHALLER)
    private Jaxb2Marshaller jaxb2Marshaller;

    @Autowired(required = false)
    private MenuItemCache cache = new NullMenuItemCache();

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void imports(final InputStream inputStream) throws IOException {
        final MenuItems menuItems = (MenuItems) jaxb2Marshaller.unmarshal(new StreamSource(inputStream));
        if (menuItems == null) {
            return;
        }
        for (final MenuItem item : menuItems.getMenuItems()) {
            final MenuItem existOne = menuItemRepository.findByName(item.getName());
            if (existOne == null) {
                final MenuItem result = menuItemRepository.save(item);
                cache.put(result);
                continue;
            }

            existOne.setUrl(item.getUrl());
            existOne.setMenuGroup(item.getMenuGroup());
            existOne.setMenuItemGroup(item.getMenuItemGroup());
            final MenuItem result = menuItemRepository.save(existOne);
            cache.put(result);
        }
    }
}
