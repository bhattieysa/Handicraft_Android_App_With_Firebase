package com.example.handicrafthorizon.Admin.ui.home;

public class OrdersModel {

    String tota_price;
    String status;
    String order_id;
    String order_date;
    String c_name;
    String c_number;
    String c_city;
    String c_address;


    public String getTota_price() {
        return tota_price;
    }

    public String getStatus() {
        return status;
    }

    public String getOrder_id() {
        return order_id;
    }

    public String getOrder_date() {
        return order_date;
    }

    public String getC_name() {
        return c_name;
    }

    public String getC_number() {
        return c_number;
    }

    public String getC_city() {
        return c_city;
    }

    public String getC_address() {
        return c_address;
    }

    public OrdersModel(String tota_price, String status, String order_id, String order_date, String c_name, String c_number, String c_city, String c_address) {

        this.order_date=order_date;
        this.order_id=order_id;
        this.tota_price=tota_price;
        this.status=status;
        this.c_address=c_address;
        this.c_city=c_city;
        this.c_name=c_name;
        this.c_number=c_number;


    }

    public OrdersModel() {



    }

}
