package com.flaxeninfosoft.sarwateAcademy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Banner {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("link")
    @Expose
    private String link;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    @Override
    public String toString() {
        return "Banner{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
