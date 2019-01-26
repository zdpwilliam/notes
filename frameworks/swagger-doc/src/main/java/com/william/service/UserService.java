package com.william.service;

import com.william.dto.UserDto;
import java.util.List;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import org.springframework.http.MediaType;

/**
 * Created by william on 17-3-24.
 */
@Path("userService")
@Produces({MediaType.APPLICATION_JSON_UTF8_VALUE})
@Consumes({MediaType.APPLICATION_JSON_UTF8_VALUE})
public interface UserService {

    @GET
    @Path("findAll")
    List<UserDto> findAll();

    @POST
    @Path("findAllByName")
    List<UserDto> findAllByName(@QueryParam("name") String name);

}
