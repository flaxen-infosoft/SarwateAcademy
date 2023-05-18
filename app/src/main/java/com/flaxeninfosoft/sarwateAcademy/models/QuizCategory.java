package com.flaxeninfosoft.sarwateAcademy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class QuizCategory {

    @SerializedName("id")
    @Expose
    Long id;

    @SerializedName("quiz_category_name")
    @Expose
    String quiz_category_name;

    @SerializedName("CreationDate")
    @Expose
    String CreationDate;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuiz_category_name() {
        return quiz_category_name;
    }

    public void setQuiz_category_name(String quiz_category_name) {
        this.quiz_category_name = quiz_category_name;
    }

    public String getCreationDate() {
        return CreationDate;
    }

    public void setCreationDate(String creationDate) {
        CreationDate = creationDate;
    }

    @Override
    public String toString() {
        return "QuizCategory{" +
                "id=" + id +
                ", quiz_category_name='" + quiz_category_name + '\'' +
                ", CreationDate='" + CreationDate + '\'' +
                '}';
    }

}
