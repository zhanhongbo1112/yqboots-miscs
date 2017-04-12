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
package com.yqboots.dict.context;

import com.yqboots.dict.service.DataDictImportService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * A listener, which importing data dictionaries from XML after the application context refreshed.
 *
 * @author Eric H B Zhan
 * @see com.yqboots.dict.core.DataDict
 * @since 1.0.0
 */
@Component
public class DataDictImportListener implements ApplicationListener<ContextRefreshedEvent> {
    private static final Logger LOG = LoggerFactory.getLogger(DataDictImportListener.class);

    /**
     * {@inheritDoc}
     */
    @Override
    public void onApplicationEvent(final ContextRefreshedEvent event) {
        final ApplicationContext context = event.getApplicationContext();

        final DataDictImportService service = context.getBean(DataDictImportService.class);
        final DataDictImportProperties properties = context.getBean(DataDictImportProperties.class);
        if (!properties.isEnabled()) {
            return;
        }

        final String location = properties.getLocation();
        if (StringUtils.isEmpty(location)) {
            LOG.warn("Data Dict Importing is enabled, but location was not set");
            return;
        }

        try {
            final File file = ResourceUtils.getFile(location);
            try (final InputStream inputStream = new FileInputStream(file)) {
                service.imports(inputStream);
            }
        } catch (IOException e) {
            LOG.error("Failed to import Data Dicts", e);
        }
    }
}
