package com.smartcampus.api;

import com.smartcampus.config.DataStore;
import com.smartcampus.model.Sensor;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.List;
import java.util.stream.Collectors;

@Path("/sensors")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class SensorResource {

    @POST
    public Response registerSensor(Sensor sensor) {
        if (!DataStore.rooms.containsKey(sensor.getRoomId())) {
            return Response.status(422)
                    .entity("{\"error\": \"Room ID does not exist in the system.\"}")
                    .build();
        }

        DataStore.sensors.put(sensor.getId(), sensor);

        DataStore.rooms.get(sensor.getRoomId()).getSensorIds().add(sensor.getId());
        
        return Response.status(Response.Status.CREATED).entity(sensor).build();
    }

    @GET
    public List<Sensor> getSensors(@QueryParam("type") String type) {
        return DataStore.sensors.values().stream()
            .filter(s -> type == null || s.getType().equalsIgnoreCase(type))
            .collect(Collectors.toList());
    }

    @Path("/{sensorId}/readings")
    public SensorReadingResource getReadingsResource(@PathParam("sensorId") String sensorId) {
        return new SensorReadingResource(sensorId);
    }
}