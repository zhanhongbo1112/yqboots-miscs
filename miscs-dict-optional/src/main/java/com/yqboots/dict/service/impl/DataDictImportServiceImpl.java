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
import com.yqboots.dict.cache.DataDictCache;
import com.yqboots.dict.cache.impl.NullDataDictCache;
import com.yqboots.dict.core.DataDict;
import com.yqboots.dict.core.DataDicts;
import com.yqboots.dict.service.DataDictImportService;
import com.yqboots.dict.service.repository.DataDictRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.oxm.jaxb.Jaxb2Marshaller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.xml.transform.stream.StreamSource;
import java.io.IOException;
import java.io.InputStream;

/**
 * Service for DataDict importing.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Service
@Transactional(readOnly = true)
public class DataDictImportServiceImpl implements DataDictImportService {
    private static final Logger LOG = LoggerFactory.getLogger(DataDictImportServiceImpl.class);

    @Autowired
    private DataDictRepository dataDictRepository;

    @Autowired
    @Qualifier(DataDictConstants.BEAN_JAXB2_MARSHALLER)
    private Jaxb2Marshaller jaxb2Marshaller;

    @Autowired(required = false)
    private DataDictCache cache = new NullDataDictCache();

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void imports(final InputStream inputStream) throws IOException {
        final DataDicts dataDicts = (DataDicts) jaxb2Marshaller.unmarshal(new StreamSource(inputStream));
        if (dataDicts == null) {
            return;
        }

        for (final DataDict dict : dataDicts.getDataDicts()) {
            LOG.debug("importing data dict with name \"{}\", text \"{}\" and value \"{}\"",
                    dict.getName(), dict.getText(), dict.getValue());
            final DataDict existOne = dataDictRepository.findByNameAndValue(dict.getName(), dict.getValue());
            if (existOne == null) {
                DataDict result = dataDictRepository.save(dict);
                cache.put(result);
                continue;
            }

            existOne.setText(dict.getText());
            existOne.setDescription(dict.getDescription());
            final DataDict result = dataDictRepository.save(existOne);

            cache.put(result);
        }
    }
}
