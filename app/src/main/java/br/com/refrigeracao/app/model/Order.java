package br.com.refrigeracao.app.model;

import java.security.Timestamp;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by elder-dell on 2017-03-11.
 */

public class Order {

    private Date createdAt;
    private int orderId;
    private String brand;
    private String model;
    private String description;

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public HashMap<String,String> toHashMap() {

        HashMap<String,String> hashMap = new HashMap<>();

        hashMap.put("created_at",this.createdAt.toString());
        hashMap.put("brand",this.getBrand());
        hashMap.put("model",this.getModel());
        hashMap.put("description",this.getDescription());

        return hashMap;
    }
}
