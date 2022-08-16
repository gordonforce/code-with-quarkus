package com.leftcoast;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import javax.validation.constraints.NotNull;
import java.util.Collection;

@ApplicationScoped
@Transactional
public class ShoppingListDAO {

    private static final Logger LOGGER = LoggerFactory.getLogger(ShoppingListDAO.class);

    private final EntityManager entityManager;

    public ShoppingListDAO(final EntityManager entityManager) {

        this.entityManager = entityManager;

    }

    public ShoppingListItem getShoppingListItem(final Integer id) {

        return entityManager.find(ShoppingListItem.class, id);

    }

    public Collection<ShoppingListItem> getAllShoppingListItems() {

        return entityManager
                .createQuery(
                        "select sli from ShoppingListItem sli ORDER BY sli.id asc",
                        ShoppingListItem.class)
                .getResultList();

    }

    public ShoppingListItem storeShoppingListItem(final String itemName, final int amount) {

        final ShoppingListItem newItem = new ShoppingListItem(itemName, amount);

        entityManager.persist(newItem);

        LOGGER.debug("New item after persist and before read is {}", newItem);

        return newItem;

    }

    public void replaceShoppingListItem(final Integer id, final String itemName, final int amount) {

        final ShoppingListItem item = getShoppingListItem(id);
        item.setItemName(itemName);
        item.setAmount(amount);

        entityManager.persist(item);

    }

    private <T> T returnNonNull(@NotNull final T source, final T candidate) {

        return (source.equals(candidate)) ? source : candidate;

    }

    public void updateShoppingListItem(final Integer id, final String itemName, final Integer amount) {

        final ShoppingListItem item = getShoppingListItem(id);
        item.setItemName(returnNonNull(item.getItemName(), itemName));
        item.setAmount(returnNonNull(item.getAmount(), amount));

        entityManager.persist(item);

    }

    public boolean deleteShoppingListItem(final Integer id) {

        final ShoppingListItem item = getShoppingListItem(id);

        final boolean deleted = item != null;

        if (deleted) entityManager.remove(item);

        return deleted;

    }


}
