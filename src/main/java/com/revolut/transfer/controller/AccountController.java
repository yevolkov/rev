package com.revolut.transfer.controller;

import com.google.inject.Inject;
import com.revolut.transfer.dao.DataBase;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import static javax.ws.rs.core.MediaType.TEXT_HTML;

@Path("/accounts")
public class AccountController {

    private final DataBase dataBase;

    @Inject
    public AccountController(DataBase dataBase) {
        this.dataBase = dataBase;
    }


    @GET
    @Produces(TEXT_HTML)
    public String getAllAccounts() {
        return dataBase.toString();
    }
}
