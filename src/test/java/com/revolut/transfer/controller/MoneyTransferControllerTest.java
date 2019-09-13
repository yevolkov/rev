package com.revolut.transfer.controller;


import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.TypeLiteral;
import com.google.inject.servlet.ServletModule;
import com.revolut.transfer.service.api.MoneyService;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import com.sun.jersey.api.client.config.DefaultClientConfig;
import com.sun.jersey.api.container.grizzly2.GrizzlyServerFactory;
import com.sun.jersey.api.core.PackagesResourceConfig;
import com.sun.jersey.api.core.ResourceConfig;
import com.sun.jersey.core.spi.component.ioc.IoCComponentProviderFactory;
import com.sun.jersey.guice.spi.container.GuiceComponentProviderFactory;
import org.apache.commons.lang3.ObjectUtils;
import org.glassfish.grizzly.http.server.HttpServer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.net.URI;

import static com.revolut.transfer.service.ErrorCode.UNKNOWN_ERROR;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class MoneyTransferControllerTest {

    private HttpServer server;
    private WebResource service;


    @Before
    public void init() throws Exception {
        URI baseURI = UriBuilder.fromUri("http://localhost/").port(8080).build();
        Injector injector = Guice.createInjector(new ServletModule() {
            @Override
            protected void configureServlets() {
                bind(new TypeLiteral<MoneyService>() {
                }).to(MoneyServiceMock.class);
            }
        });
        ResourceConfig rc = new PackagesResourceConfig("com.revolut.transfer.controller");
        IoCComponentProviderFactory ioc = new GuiceComponentProviderFactory(rc, injector);
        server = GrizzlyServerFactory.createHttpServer(baseURI, rc, ioc);
        service = Client.create(new DefaultClientConfig()).resource(baseURI);
    }

    @After
    public void close() {
        server.stop();
    }

    @Test
    public void shouldReceiveSuccessWhenValidQueryParams() {
        ClientResponse resp = service
                .path("/transfer/money")
                .queryParam("fromAccount","1")
                .queryParam("toAccount","2")
                .queryParam("amount","100")
                .accept(MediaType.TEXT_HTML)
                .post(ClientResponse.class);
        assertNotNull(resp);
        assertEquals(Response.Status.OK.getStatusCode(), resp.getStatus());
        assertEquals("SUCCESS", resp.getEntity(String.class));
    }

    @Test
    public void shouldReceiveUnknownErrorWhenNoQueryParams() {
        ClientResponse resp = service
                .path("/transfer/money")
                .accept(MediaType.TEXT_HTML)
                .post(ClientResponse.class);
        assertNotNull(resp);
        assertEquals(Response.Status.INTERNAL_SERVER_ERROR.getStatusCode(), resp.getStatus());
        assertEquals(UNKNOWN_ERROR.getCode(), resp.getEntity(String.class));
    }

    static class MoneyServiceMock implements MoneyService {
        @Override
        public void transfer(String fromAccount, String toAccount, String amount) throws MoneyServiceException {
            if (!ObjectUtils.allNotNull(fromAccount, toAccount, amount)) {
                throw new MoneyService.MoneyServiceException(UNKNOWN_ERROR);
            }
        }
    }

}
