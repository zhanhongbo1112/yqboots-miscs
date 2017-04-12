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
package com.yqboots.dict.facade.impl;

import com.yqboots.dict.core.DataDict;
import com.yqboots.dict.facade.DataDictImportFacade;
import com.yqboots.dict.service.DataDictImportService;
import com.yqboots.dict.service.DataDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

/**
 * Facade for DataDict importing.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Component
public class DataDictImportFacadeImpl implements DataDictImportFacade {
    @Autowired
    private DataDictService dataDictService;

    @Autowired
    private DataDictImportService dataDictImportService;

    @Override
    public Page<DataDict> getDataDicts(String name, Pageable pageable) {
        return dataDictService.getDataDicts(name, pageable);
    }

    @Override
    public void imports(InputStream inputStream) throws IOException {
        dataDictImportService.imports(inputStream);
    }
}
