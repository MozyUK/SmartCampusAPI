package com.smartcampus.filter;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.container.ContainerResponseContext;
import jakarta.ws.rs.container.ContainerResponseFilter;
import jakarta.ws.rs.ext.Provider;
import java.util.logging.Logger;

@Provider
public class TrafficLogger implements ContainerRequestFilter, ContainerResponseFilter {

    private static final Logger LOG = Logger.getLogger(TrafficLogger.class.getName());

    @Override
    public void filter(ContainerRequestContext req) {
        LOG.info("INCOMING REQ: " + req.getMethod() + " " + req.getUriInfo().getRequestUri());
    }

    @Override
    public void filter(ContainerRequestContext req, ContainerResponseContext res) {
        LOG.info("OUTGOING RES STATUS: " + res.getStatus());
    }
}