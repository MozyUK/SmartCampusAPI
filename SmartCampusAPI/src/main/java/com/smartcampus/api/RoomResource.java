package com.smartcampus.api;

import com.smartcampus.config.DataStore;
import com.smartcampus.model.Room;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Path("/rooms")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class RoomResource {

    @GET
    public List<Room> getAllRooms() {
        return new ArrayList<>(DataStore.rooms.values());
    }

    @POST
    public Response createRoom(Room room) {
        DataStore.rooms.put(room.getId(), room);
        return Response.status(Response.Status.CREATED).entity(room).build();
    }

    @GET
    @Path("/{roomId}")
    public Response getRoom(@PathParam("roomId") String roomId) {
        Room room = DataStore.rooms.get(roomId);
        if (room == null) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
        return Response.ok(room).build();
    }

    @DELETE
    @Path("/{roomId}")
    public Response deleteRoom(@PathParam("roomId") String roomId) {
        Room room = DataStore.rooms.get(roomId);
        
        if (room == null) {
             return Response.status(Response.Status.NOT_FOUND).build();
        }

        if (!room.getSensorIds().isEmpty()) {
            return Response.status(Response.Status.CONFLICT)
                    .entity("{\"error\": \"Room cannot be deleted: active sensors assigned.\"}")
                    .build();
        }
        
        DataStore.rooms.remove(roomId);
        return Response.noContent().build();
    }
}