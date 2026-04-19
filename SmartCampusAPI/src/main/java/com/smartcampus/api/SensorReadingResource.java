package com.smartcampus.api;

import com.smartcampus.config.DataStore;
import com.smartcampus.model.Sensor;
import com.smartcampus.model.SensorReading;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorReadingResource {
    
    private String sensorId;

    public SensorReadingResource(String sensorId) {
        this.sensorId = sensorId;
    }

    @GET
    public List<SensorReading> getHistory() {
        return DataStore.readings.getOrDefault(sensorId, new ArrayList<>());
    }

    @POST
    public Response addReading(SensorReading reading) {
        Sensor sensor = DataStore.sensors.get(sensorId);
        
        if (sensor == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }

        if ("MAINTENANCE".equalsIgnoreCase(sensor.getStatus())) {
            return Response.status(Response.Status.FORBIDDEN)
                    .entity("{\"error\": \"Sensor is currently offline for maintenance.\"}")
                    .build();
        }

        DataStore.readings.computeIfAbsent(sensorId, k -> new ArrayList<>()).add(reading);

        sensor.setCurrentValue(reading.getValue());

        return Response.status(Response.Status.CREATED).entity(reading).build();
    }
}