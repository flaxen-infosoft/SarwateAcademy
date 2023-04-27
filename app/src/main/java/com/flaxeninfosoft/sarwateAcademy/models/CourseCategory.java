package com.flaxeninfosoft.sarwateAcademy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseCategory {

    @SerializedName("ID")
    @Expose
    Long ID;

    @SerializedName("Product_category_name")
    @Expose
    String Product_category_name;

    @Override
    public String toString() {
        return "CourseCategory{" +
                "ID=" + ID +
                ", Product_category_name='" + Product_category_name + '\'' +
                ", CreationDate='" + CreationDate + '\'' +
                '}';
    }

    public Long getID() {
        return ID;
    }

    public void setID(Long ID) {
        this.ID = ID;
    }

    public String getProduct_category_name() {
        return Product_category_name;
    }

    public void setProduct_category_name(String product_category_name) {
        Product_category_name = product_category_name;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    @SerializedName("CreationDate")
    @Expose
    String CreationDate;
}
