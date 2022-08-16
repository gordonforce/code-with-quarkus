package com.leftcoast;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name="SHOPPING_LIST_ITEMS")
public class ShoppingListItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "ITEM_NAME")
    private String itemName;

    @Column(name = "AMOUNT")
    private int amount;

    public ShoppingListItem() {
    }

    public ShoppingListItem(String itemName, int amount) {
        this.itemName = itemName;
        this.amount = amount;
    }

    public Integer getId() {
        return id;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ShoppingListItem item = (ShoppingListItem) o;
        return amount == item.amount && Objects.equals(id, item.id) && Objects.equals(itemName, item.itemName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, itemName, amount);
    }

    @Override
    public String toString() {
        return "ShoppingListItem{" +
                "id=" + id +
                ", itemName='" + itemName + '\'' +
                ", amount=" + amount +
                '}';
    }

}
