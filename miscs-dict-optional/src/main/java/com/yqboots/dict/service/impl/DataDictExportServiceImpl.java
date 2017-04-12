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
package com.yqboots.dict.service.impl;

import com.yqboots.dict.DataDictConstants;
import com.yqboots.dict.context.DataDictExportProperties;
import com.yqboots.dict.core.DataDict;
import com.yqboots.dict.core.DataDicts;
import com.yqboots.dict.service.DataDictExportService;
import com.yqboots.dict.service.repository.DataDictRepository;
import com.yqboots.fss.core.support.FileType;
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
 * Service for DataDict exporting.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class DataDictExportServiceImpl implements DataDictExportService {
    @Autowired
    private DataDictRepository dataDictRepository;

    @Autowired
    private DataDictExportProperties properties;

    @Autowired
    @Qualifier(DataDictConstants.BEAN_JAXB2_MARSHALLER)
    private Jaxb2Marshaller jaxb2Marshaller;

    /**
     * {@inheritDoc}
     */
    @Override
    public Path exports() throws IOException {
        final String fileName = LocalDate.now() + FileType.DOT_XML;

        final Path location = properties.getLocation();
        if (!Files.exists(location)) {
            Files.createDirectories(location);
        }

        final Path result = Paths.get(location + File.separator + fileName);
        try (final FileWriter writer = new FileWriter(result.toFile())) {
            final List<DataDict> dataDicts = dataDictRepository.findAll();
            jaxb2Marshaller.marshal(new DataDicts(dataDicts), new StreamResult(writer));
        }

        return result;
    }
}
