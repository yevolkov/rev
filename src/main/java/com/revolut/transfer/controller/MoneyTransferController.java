package com.revolut.transfer.controller;

import com.google.inject.Inject;
import com.revolut.transfer.service.api.MoneyService;

import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/transfer")
public class MoneyTransferController {


    private final MoneyService moneyService;

    @Inject
    public MoneyTransferController(MoneyService moneyService) {
        this.moneyService = moneyService;
    }


    @POST
    @Path("/money")
    public Response doTransfer(@QueryParam("fromAccount") String fromAccount,
                               @QueryParam("toAccount") String toAccount,
                               @QueryParam("amount") String amount) {

        try {
            moneyService.transfer(fromAccount, toAccount, amount);
        } catch (MoneyService.MoneyServiceException e) {
            return Response
                    .status(Response.Status.INTERNAL_SERVER_ERROR)
                    .entity(e.getMessage())
                    .build();
        }

        return Response
                .status(200)
                .entity("SUCCESS")
                .build();

    }

}
