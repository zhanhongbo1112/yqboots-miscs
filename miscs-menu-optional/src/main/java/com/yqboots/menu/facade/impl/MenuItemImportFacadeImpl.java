package com.yqboots.menu.facade.impl;

import com.yqboots.menu.core.MenuItem;
import com.yqboots.menu.facade.MenuItemImportFacade;
import com.yqboots.menu.service.MenuItemImportService;
import com.yqboots.menu.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;

@Component
public class MenuItemImportFacadeImpl implements MenuItemImportFacade {
    @Autowired
    private MenuItemService menuItemService;

    @Autowired
    private MenuItemImportService menuItemImportService;

    @Override
    public Page<MenuItem> getMenuItems(String name, Pageable pageable) {
        return menuItemService.getMenuItems(name, pageable);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void imports(final InputStream inputStream) throws IOException {
        menuItemImportService.imports(inputStream);
    }
}
