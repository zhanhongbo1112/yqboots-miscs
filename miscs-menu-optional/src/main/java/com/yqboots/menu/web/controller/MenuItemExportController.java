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
package com.yqboots.menu.web.controller;

import com.yqboots.menu.core.MenuItemPermissions;
import com.yqboots.menu.facade.MenuItemExportFacade;
import com.yqboots.web.support.WebKeys;
import com.yqboots.web.util.FileWebUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Controller for Menu exporting.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/menu")
public class MenuItemExportController {
    @Autowired
    private MenuItemExportFacade menuItemExportFacade;

    @PreAuthorize(MenuItemPermissions.READ)
    @RequestMapping(value = WebKeys.MAPPING_EXPORTS, method = {RequestMethod.GET, RequestMethod.POST})
    public HttpEntity<byte[]> exports() throws IOException {
        final Path path = menuItemExportFacade.exports();

        return FileWebUtils.downloadFile(path, MediaType.APPLICATION_XML);
    }
}
