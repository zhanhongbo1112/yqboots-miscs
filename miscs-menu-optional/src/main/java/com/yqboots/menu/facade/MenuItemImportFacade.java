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
package com.yqboots.menu.facade;

import com.yqboots.menu.core.MenuItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.io.InputStream;

/**
 * Facade for MenuItem importing.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public interface MenuItemImportFacade {
    Page<MenuItem> getMenuItems(String name, Pageable pageable);

    /**
     * Imports an XML-presented file, which contains menu items.
     *
     * @param inputStream the file stream
     * @throws IOException if failed
     */
    void imports(InputStream inputStream) throws IOException;
}
