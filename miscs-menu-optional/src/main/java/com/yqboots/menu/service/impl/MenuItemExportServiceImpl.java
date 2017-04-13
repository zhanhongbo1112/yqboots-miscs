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

import com.yqboots.fss.core.support.FileType;
import com.yqboots.menu.MenuItemConstants;
import com.yqboots.menu.context.MenuItemExportProperties;
import com.yqboots.menu.core.MenuItem;
import com.yqboots.menu.core.MenuItems;
import com.yqboots.menu.service.MenuItemExportService;
import com.yqboots.menu.service.repository.MenuItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;

/**
 * It Manages the MenuItem related functions.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class MenuItemExportServiceImpl implements MenuItemExportService {
    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private MenuItemExportProperties properties;

    /**
     * For marshalling and unmarshalling Menu Item.
     */
    @Autowired
    @Qualifier(MenuItemConstants.BEAN_JAXB2_MARSHALLER)
    private Jaxb2Marshaller jaxb2Marshaller;

    /**
     * {@inheritDoc}
     */
    @Override
    public Path exports() throws IOException {
        final String fileName = LocalDate.now() + FileType.DOT_XML;

        if (!Files.exists(properties.getLocation())) {
            Files.createDirectories(properties.getLocation());
        }

        final Path result = Paths.get(properties.getLocation() + File.separator + fileName);

        final List<MenuItem> menuItems = menuItemRepository.findAll();

        try (FileWriter writer = new FileWriter(result.toFile())) {
            jaxb2Marshaller.marshal(new MenuItems(menuItems), new StreamResult(writer));
        }

        return result;
    }
}
