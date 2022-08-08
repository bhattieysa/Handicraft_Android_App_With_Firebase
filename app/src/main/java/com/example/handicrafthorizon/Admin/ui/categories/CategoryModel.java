package com.example.handicrafthorizon.Admin.ui.categories;

public class CategoryModel {
    private String name;
    private String image;
    private String id;


    public CategoryModel( String image,String name,String id ) {

        this.name=name;
        this.image=image;
        this.id=id;


    }

    public CategoryModel() {



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
