package com.flaxeninfosoft.sarwateAcademy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CoursePurchaseRequest {

    @SerializedName("id")
    @Expose
    private Long id;


    @SerializedName("UPI")
    @Expose
    private String UPI;

    @SerializedName("barcode_image")
    @Expose
    private String barcode_image;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUPI() {
        return UPI;
    }

    public void setUPI(String UPI) {
        this.UPI = UPI;
    }

    public String getBarcode_image() {
        return barcode_image;
    }

    public void setBarcode_image(String barcode_image) {
        this.barcode_image = barcode_image;
    }

    @Override
    public String toString() {
        return "CoursePurchaseRequest{" +
                "id=" + id +
                ", UPI=" + UPI +
                ", barcode_image=" + barcode_image +
                '}';
    }
}
