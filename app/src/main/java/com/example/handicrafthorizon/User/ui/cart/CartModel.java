package com.example.handicrafthorizon.User.ui.cart;


public class CartModel {
    private String name;
    private String image;
    private String user_id;
    private String quantity;
    private String price;
    private String product_id;
    private String id;


    public CartModel(String image, String name, String id ,String user_id, String product_id, String price, String quantity) {

        this.name=name;
        this.image=image;
        this.id=id;
        this.product_id=product_id;
        this.user_id=user_id;
        this.price=price;
        this.quantity=quantity;


    }

    public CartModel() {



    }
    public String getUser_id()
    {
        return user_id;
    }
    public String getPrice()
    {
        return price;
    }
    public String getQuantity()
    {
        return quantity;
    }
    public String getProduct_id()
    {
        return product_id;
    }

    public String getName()
    {
        return name;
    }
    public String getId()
    {
        return id;
    }


    public String getImage()
    {
        return image;
    }


}
