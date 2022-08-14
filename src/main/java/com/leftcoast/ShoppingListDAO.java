package com.leftcoast;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import java.util.Objects;

@ApplicationScoped
public class ShoppingListDAO {

    private final EntityManager entityManager;

    public ShoppingListDAO(EntityManager entityManager) {

        this.entityManager = entityManager;

    }

    public ShoppingListItem getShoppingListItem(final Integer id) {

        return entityManager.find(ShoppingListItem.class, id);

    }

    public void storeShoppingListItem(final String itemName, final int amount) {

        entityManager.persist((new ShoppingListItem(itemName, amount)));

    }

    public void replaceShoppingListItem(final Integer id, final String itemName, final int amount) {

        final ShoppingListItem item = getShoppingListItem(id);
        item.setItemName(itemName);
        item.setAmount(amount);

        entityManager.persist(item);

    }

    public void updateShoppingListItem(final Integer id, final String itemName, final Integer amount) {

        final ShoppingListItem item = getShoppingListItem(id);

        if (Objects.nonNull(amount) && amount != item.getAmount()) item.setAmount(amount);

        if (Objects.nonNull(itemName) && !itemName.equals(item.getItemName())) item.setItemName(itemName);

        entityManager.persist(item);

    }

    public boolean deleteShoppingListItem(final Integer id) {

        final ShoppingListItem item = getShoppingListItem(id);

        final boolean deleted = item != null;

        if (deleted) entityManager.detach(item);

        return deleted;

    }


}
