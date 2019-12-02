package user;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@Path("user")
public class UserResource {

    private final static UserService userService = new UserService();

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<User> getUsers() {
        return userService.getUsers();
    }

    @GET
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUser(@PathParam("name") String name) {
        if (name == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User user = userService.getUser(name);
        if (user == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User does not exist[" + name + "]")
                    .build();
        }
        return Response.ok(user).build();
    }

    @PUT
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateUser(@PathParam("name") String name, User user) {
        if (name == null || user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }

        User existUser = userService.getUser(name);

        if (existUser == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User does not exist[" + name + "]")
                    .build();
        }

        User resultUser = userService.updateUser(existUser, user);
        return Response.ok(resultUser).build();
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addUser(User user) throws URISyntaxException {
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        User existUser = userService.getUser(user.getFirstName());
        if (existUser != null) {
            return Response.status(Response.Status.BAD_REQUEST)
                    .entity("User is already exist[" + user.getFirstName() + "]")
                    .build();
        }
        User resultUser = userService.addUser(user);
        return Response.created(new URI("/user/" + user.getFirstName())).entity(resultUser).build();
    }

    @DELETE
    @Path("/{name}")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("name") String name) {
        if (name == null) {
            return Response.status(Response.Status.BAD_REQUEST).build();
        }
        User existUser = userService.getUser(name);
        if (existUser == null) {
            return Response.status(Response.Status.NOT_FOUND)
                    .entity("User does not exist[" + name + "]")
                    .build();
        }
        userService.removeUser(name);
        return Response.ok().build();
    }
}
