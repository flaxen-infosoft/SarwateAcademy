package com.flaxeninfosoft.sarwateAcademy.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.flaxeninfosoft.sarwateAcademy.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

public class Quiz {
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("catId")
    @Expose
    private String catId;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("banner")
    @Expose
    private String banner;
    @SerializedName("free")
    @Expose
    private String free;
    @SerializedName("price")
    @Expose
    private String price;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("totalq")
    @Expose
    private String totalq;
    @SerializedName("totalm")
    @Expose
    private String totalm;
    @SerializedName("CreationDate")
    @Expose
    private String creationDate;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCatId() {
        return catId;
    }

    public void setCatId(String catId) {
        this.catId = catId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getBanner() {
        return banner;
    }

    public void setBanner(String banner) {
        this.banner = banner;
    }

    public String getFree() {
        return free;
    }

    public void setFree(String free) {
        this.free = free;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTotalq() {
        return totalq;
    }

    public void setTotalq(String totalq) {
        this.totalq = totalq;
    }

    public String getTotalm() {
        return totalm;
    }

    public void setTotalm(String totalm) {
        this.totalm = totalm;
    }

    public String getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(String creationDate) {
        this.creationDate = creationDate;
    }

    @Override
    public String toString() {
        return "Quiz{" +
                "id=" + id +
                ", categoryId='" + catId + '\'' +
                ", Name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", banner='" + banner + '\'' +
                ", free='" + free + '\'' +
                ", price='" + price + '\'' +
                ", time='" + time + '\'' +
                ", totalq='" + totalq + '\'' +
                ", totalm='" + totalm + '\'' +
                ", CreationDate='" + creationDate + '\'' +
                '}';
    }

    @BindingAdapter("set_image")
    public static void set_image(ImageView view, String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()){
//            Glide.with(view).load("http://103.118.17.202/~mkeducation/MK_API/User/"+imageUrl).placeholder(R.drawable.sarwate_logo).into(view);
            Picasso.get().load("http://103.118.17.202/~mkeducation/MK_API/User/"+imageUrl).placeholder(R.drawable.sarwate_logo).into(view);
        }
    }
}