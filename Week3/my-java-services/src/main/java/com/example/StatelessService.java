
package com.example;

import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/stateless")
public class StatelessService {
    @GET
    @Path("/visit")
    public Response visit(@QueryParam("userId") String userId) {
        String message = "Hello, User " + userId + "! This is a stateless request.";
        return Response.ok(message).build();
    }
}
