package com.leftcoast;

import javax.enterprise.context.ApplicationScoped;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.Collection;
import java.util.Objects;

@ApplicationScoped
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Path("shoppingList")
public class ShoppingListEndpoints {

    private final ShoppingListDAO shoppingListDAO;

    public ShoppingListEndpoints(final ShoppingListDAO shoppingListDAO) {
        this.shoppingListDAO = shoppingListDAO;
    }

    @GET
    @Path("{id}")
    public ShoppingListItem getShoppingListItem(@PathParam("id") final Integer id) {

        return shoppingListDAO.getShoppingListItem(id);

    }

    @GET
    public Collection<ShoppingListItem> getAllShoppingListItem() {

        return shoppingListDAO.getAllShoppingListItems();

    }

    @POST
    public Response postShoppingListItem(
            @QueryParam("itemName") @NotBlank final String itemName,
            @QueryParam("amount") @NotNull final Integer amount,
            @Context UriInfo uriInfo) {

        final ShoppingListItem created = shoppingListDAO.storeShoppingListItem(itemName, amount);

        return Response
                .created(UriBuilder
                        .fromUri(uriInfo.getAbsolutePath())
                        .segment(created.getId().toString())
                        .build())
                .build();

    }

    @PUT
    public Response putShoppingListItem(
            @QueryParam("id") final Integer id,
            @QueryParam("itemName") @NotBlank final String itemName,
            @QueryParam("amount") @NotNull final Integer amount,
            @Context @NotNull final UriInfo uriInfo) {

        if (Objects.nonNull(id)) {

            shoppingListDAO.replaceShoppingListItem(id, itemName, amount);

            return Response.noContent().build();

        } else {

            return postShoppingListItem(itemName, amount, uriInfo);

        }

    }

    @PATCH
    public Response patchShoppingListItem(
            @QueryParam("id") final @NotNull Integer id,
            @QueryParam("itemName") final String itemName,
            @QueryParam("amount") final Integer amount) {

        if (Objects.nonNull(itemName) || Objects.nonNull(amount))
            shoppingListDAO.updateShoppingListItem(id, itemName, amount);

        return Response.noContent().build();

    }

    @DELETE
    @Path("{id}")
    public Response deleteShoppingListItem(@PathParam("id") final Integer id) {

        return (shoppingListDAO.deleteShoppingListItem(id))
                ? Response.noContent().build()
                : Response.status(Response.Status.NOT_FOUND).build();
        // Not found should return a 200 with an internal error code as success flag equal to false.

    }

}
