package com.revolut.transfer;

import com.google.inject.servlet.GuiceFilter;
import com.revolut.transfer.config.Config;
import com.sun.grizzly.http.embed.GrizzlyWebServer;
import com.sun.grizzly.http.servlet.ServletAdapter;
import org.apache.log4j.BasicConfigurator;

import javax.servlet.http.HttpServlet;

public class Runner {

    public static void main(String[] args) throws Exception {

        GrizzlyWebServer server = new GrizzlyWebServer(8080);
        ServletAdapter adapter = new ServletAdapter(new MyServlet());
        adapter.addServletListener(Config.class.getName());
        adapter.addFilter(new GuiceFilter(), "GuiceFilter", null);
        server.addGrizzlyAdapter(adapter, new String[]{"/"});
        server.start();
        BasicConfigurator.configure();
    }

    public static class MyServlet extends HttpServlet {
    }
}
