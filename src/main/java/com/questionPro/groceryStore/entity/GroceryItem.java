package com.questionPro.groceryStore.entity;


import jakarta.persistence.*;

@Entity
@Table(name = "grocery_item")
public class GroceryItem {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer grocery_item_id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Double price;

    @Column(nullable = false)
    private Integer quantity;

    public GroceryItem(){
    }

    public GroceryItem(Integer grocery_item_id, String name, Double price, Integer quantity) {
        this.grocery_item_id = grocery_item_id;
        this.name = name;
        this.price = price;
        this.quantity = quantity;
    }

    public Integer getGroceryItemId() {
        return grocery_item_id;
    }

    public void setGroceryItemId(Integer grocery_item_id) {
        this.grocery_item_id = grocery_item_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }
}

