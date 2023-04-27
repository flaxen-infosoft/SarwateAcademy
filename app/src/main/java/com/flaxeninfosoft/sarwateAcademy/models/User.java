package com.flaxeninfosoft.sarwateAcademy.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.sarwateAcademy.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class User {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("role")
    @Expose
    private String role;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("email")
    @Expose
    private String email;

    @SerializedName("phone")
    @Expose
    private String phone;

    @SerializedName("city")
    @Expose
    private String city;

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @SerializedName("password")
    @Expose
    private String password;

    @SerializedName("state")
    @Expose
    private String state;

    @SerializedName("profileImg")
    @Expose
    private String profileImg;

    @SerializedName("token")
    @Expose
    private String token;

    @SerializedName("gender")
    @Expose
    private String gender;

    public User(String name,String profileImg,String phone) {
        this.name = name;
        this.profileImg = profileImg;
        this.phone = phone;
    }

    public User() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getProfileImg() {
        return profileImg;
    }

    public void setProfileImg(String profileImg) {
        this.profileImg = profileImg;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", profileImg='" + profileImg + '\'' +
                ", token='" + token + '\'' +
                '}';
    }

    @BindingAdapter("set_image")
    public static void set_image(ImageView view, String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()){
            Glide.with(view).load(imageUrl).placeholder(R.drawable.avatar_profile_icon).into(view);
        }

    }
}
