package com.yqboots.menu.facade.impl;

import com.yqboots.menu.facade.MenuItemExportFacade;
import com.yqboots.menu.service.MenuItemExportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.file.Path;

/**
 * Created by zhanhb on 2017-04-13.
 */
@Component
public class MenuItemExportFacadeImpl implements MenuItemExportFacade {
    @Autowired
    private MenuItemExportService menuItemExportService;

    /**
     * {@inheritDoc}
     */
    @Override
    public Path exports() throws IOException {
        return menuItemExportService.exports();
    }
}
