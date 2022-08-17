package com.example.handicrafthorizon.User.ui.orders;

public class OrderModel {

    String tota_price;
    String status;
    String order_id;
    String order_date;


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

    public OrderModel(String tota_price, String status, String order_id, String order_date) {

      this.order_date=order_date;
      this.order_id=order_id;
      this.tota_price=tota_price;
      this.status=status;


    }

    public OrderModel() {



    }

}
