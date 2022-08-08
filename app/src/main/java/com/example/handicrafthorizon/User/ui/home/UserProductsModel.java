package com.example.handicrafthorizon.User.ui.home;

public class UserProductsModel {
    private String name;
    private String image;
    private String id;
    private String price;
    private String category;


    public UserProductsModel( String image,String name,String id ,String price, String category) {

        this.name=name;
        this.image=image;
        this.id=id;
        this.category=category;
        this.price=price;


    }

    public UserProductsModel() {



    }
    public String getPrice()
    {
        return price;
    }
    public String getCategory()
    {
        return category;
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
