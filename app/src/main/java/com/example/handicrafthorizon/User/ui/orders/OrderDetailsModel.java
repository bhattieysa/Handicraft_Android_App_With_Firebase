package com.example.handicrafthorizon.User.ui.orders;

public class OrderDetailsModel {

    String id;
    String image;
    String name;
    String price;
    String quantity;


    public String getId() {
        return id;
    }

    public String getImage() {
        return image;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }

    public String getQuantity() {
        return quantity;
    }

    public OrderDetailsModel(String id, String image, String name, String price, String quantity) {

        this.id=id;
        this.image=image;
        this.name=name;
        this.price=price;
        this.quantity=quantity;


    }

    public OrderDetailsModel() {



    }

}
