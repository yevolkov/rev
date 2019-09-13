package com.revolut.transfer.config;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;
import com.revolut.transfer.controller.AccountController;
import com.revolut.transfer.controller.MoneyTransferController;
import com.revolut.transfer.dao.AccountDao;
import com.revolut.transfer.dao.AccountDaoImpl;
import com.revolut.transfer.service.MoneyServiceImpl;
import com.revolut.transfer.service.MoneyTransferImpl;
import com.revolut.transfer.service.api.MoneyService;
import com.revolut.transfer.service.api.MoneyTransfer;
import com.sun.jersey.guice.spi.container.servlet.GuiceContainer;

public class Config extends GuiceServletContextListener {

    @Override
    protected Injector getInjector() {

        return Guice.createInjector(
                new ServletModule() {
                    @Override
                    protected void configureServlets() {
                        bind(MoneyTransferController.class);
                        bind(AccountController.class);
                        bind(AccountDao.class).to(AccountDaoImpl.class);
                        bind(MoneyTransfer.class).to(MoneyTransferImpl.class);
                        bind(MoneyService.class).to(MoneyServiceImpl.class);
                        serve("*").with(GuiceContainer.class);
                    }
                });

    }
}
