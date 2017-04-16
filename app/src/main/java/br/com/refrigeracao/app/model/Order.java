package br.com.refrigeracao.app.model;

import java.io.Serializable;
import java.util.Date;
import java.util.HashMap;

/**
 * Created by elder-dell on 2017-03-11.
 */

public class Order implements Serializable {


    private static final long serialVersionUID = -5179036813371869616L;

    private String key;
    private String imageName;
    private String brand;
    private String description;
    private String model;

    public Order(){

    }

    public Order(String brand, String description, String model, String key, String imageName) {
        this.brand = brand;
        this.description = description;
        this.model = model;
        this.key = key;
        this.imageName = imageName;
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

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }
}
