package org.vaadin.training.fundamentals.happening.launcher;

import org.mortbay.jetty.Server;
import org.mortbay.jetty.webapp.WebAppContext;

public class ServletContainerLauncher {
    private static final int httpPort = 8888;
    private static final String contextPath = "/";
    private static final String resourceBase = "WebContent";
    
    public static void main(String args[]) throws Exception {
        Server server = new Server(httpPort);
        WebAppContext webapp = new WebAppContext();
        webapp.setContextPath(contextPath);
        webapp.setResourceBase(resourceBase);
        webapp.setClassLoader(Thread.currentThread().getContextClassLoader());
        server.setHandler(webapp);
        server.start();
        server.join();
    }
}
