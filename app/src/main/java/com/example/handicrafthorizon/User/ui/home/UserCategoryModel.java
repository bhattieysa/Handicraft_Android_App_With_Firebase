package com.example.handicrafthorizon.User.ui.home;

public class UserCategoryModel {
    private String name;
    private String image;
    private String id;


    public UserCategoryModel(String image, String name, String id ) {

        this.name=name;
        this.image=image;
        this.id=id;


    }

    public UserCategoryModel() {



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
