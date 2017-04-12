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
package com.yqboots.dict.facade;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Facade for DataDict exporting.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
public interface DataDictExportFacade {
    /**
     * Exports all data dictionaries to a file for downloading.
     *
     * @return the exported file path
     * @throws IOException if failed
     */
    Path exports() throws IOException;
}
