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
package com.yqboots.dict.web.controller;

import com.yqboots.dict.core.DataDictPermissions;
import com.yqboots.dict.facade.DataDictImportFacade;
import com.yqboots.dict.web.form.FileUploadForm;
import com.yqboots.dict.web.form.FileUploadFormValidator;
import com.yqboots.web.support.WebKeys;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.oxm.XmlMappingException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.io.IOException;
import java.io.InputStream;

/**
 * Controller for DataDict importing.
 *
 * @author Eric H B Zhan
 * @since 1.0.0
 */
@Controller
@RequestMapping(value = "/dict")
public class DataDictImportController {
    private static final String REDIRECT_VIEW_PATH = "redirect:/dict";
    private static final String VIEW_HOME = "dict/index";

    @Autowired
    private DataDictImportFacade dataDictImportFacade;

    @ModelAttribute(WebKeys.FILE_UPLOAD_FORM)
    protected FileUploadForm fileUploadForm() {
        return new FileUploadForm();
    }

    @PreAuthorize(DataDictPermissions.WRITE)
    @RequestMapping(value = WebKeys.MAPPING_IMPORTS, method = RequestMethod.POST)
    public String imports(@ModelAttribute(WebKeys.FILE_UPLOAD_FORM) FileUploadForm fileUploadForm,
                          @PageableDefault final Pageable pageable,
                          final BindingResult bindingResult,
                          final ModelMap model) throws IOException {
        new FileUploadFormValidator().validate(fileUploadForm, bindingResult);
        if (bindingResult.hasErrors()) {
            model.addAttribute(WebKeys.PAGE, dataDictImportFacade.getDataDicts(StringUtils.EMPTY, pageable));
            return VIEW_HOME;
        }

        try (final InputStream inputStream = fileUploadForm.getFile().getInputStream()) {
            dataDictImportFacade.imports(inputStream);
        } catch (XmlMappingException e) {
            bindingResult.rejectValue(WebKeys.FILE, "I0003");
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute(WebKeys.PAGE, dataDictImportFacade.getDataDicts(StringUtils.EMPTY, pageable));
            return VIEW_HOME;
        }

        model.clear();

        return REDIRECT_VIEW_PATH;
    }
}
