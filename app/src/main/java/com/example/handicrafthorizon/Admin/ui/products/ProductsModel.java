package com.example.handicrafthorizon.Admin.ui.products;

public class ProductsModel {
    private String name;
    private String image;
    private String id;
    private String price;
    private String category;


    public ProductsModel( String image,String name,String id ,String price, String category) {

        this.name=name;
        this.image=image;
        this.id=id;
        this.category=category;
        this.price=price;


    }

    public ProductsModel() {



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
