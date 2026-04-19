package com.smartcampus.api;

import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/")
public class DiscoveryResource {

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getDiscoveryInfo() {
        String jsonResponse = "{" +
                "\"version\": \"1.0\"," +
                "\"contact\": \"admin@smartcampus.edu\"," +
                "\"links\": {" +
                "\"rooms\": \"/api/v1/rooms\"," +
                "\"sensors\": \"/api/v1/sensors\"" +
                "}" +
                "}";
        return Response.ok(jsonResponse).build();
    }
}