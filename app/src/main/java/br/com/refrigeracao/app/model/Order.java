package br.com.refrigeracao.app.model;

import java.util.Date;
import java.util.HashMap;

/**
 * Created by elder-dell on 2017-03-11.
 */

public class Order {

    private String brand;
    private String description;
    private String model;

    public Order(){

    }

    public Order(String brand, String description, String model) {
        this.brand = brand;
        this.description = description;
        this.model = model;
    }

    public HashMap<String,Object> toHashMap() {

        HashMap<String,Object> hashMap = new HashMap<>();

        hashMap.put("brand", this.getBrand());
        hashMap.put("model", this.getModel());
        hashMap.put("description", this.getDescription());

        return hashMap;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }
}
