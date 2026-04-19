package com.smartcampus;

import com.smartcampus.config.AppConfig;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import java.net.URI;

public class Main {
    public static final String BASE_URI = "http://localhost:8080/";

    public static HttpServer startServer() {
        final AppConfig rc = new AppConfig();
        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) {
        try {
            final HttpServer server = startServer();
            System.out.println("✅ Smart Campus API is actively running at: " + BASE_URI);
            System.out.println("🛑 Press Ctrl+C in this terminal to stop the server.");
            Thread.currentThread().join(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}