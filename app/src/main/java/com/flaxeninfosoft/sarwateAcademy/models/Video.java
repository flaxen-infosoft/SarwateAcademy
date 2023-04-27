package com.flaxeninfosoft.sarwateAcademy.models;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.sarwateAcademy.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

public class Video {

    @SerializedName("courseId")
    @Expose
    private Long courseId;

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("videoLink")
    @Expose
    private String videoLink;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("time")
    @Expose
    private String time;
    @SerializedName("duration")
    @Expose
    private String duration;
    @SerializedName("language")
    @Expose
    private String language;
    @SerializedName("teacherId")
    @Expose
    private String teacherId;
    @SerializedName("isFree")
    @Expose
    private String isFree;

    public Video() {
    }

    public Video(String title, String imageUrl ,String link) {
        this.title = title;
        this.image = imageUrl;
        this.videoLink = link;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getVideoLink() {
        return videoLink;
    }

    public void setVideoLink(String videoLink) {
        this.videoLink = videoLink;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherName) {
        this.teacherId = teacherName;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    @Override
    public String toString() {
        return "Video{" +
                "courseId='" + courseId + '\'' +
                ", id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", videoLink='" + videoLink + '\'' +
                ", image='" + image + '\'' +
                ", date='" + date + '\'' +
                ", time='" + time + '\'' +
                ", duration='" + duration + '\'' +
                ", language='" + language + '\'' +
                ", teacherId='" + teacherId + '\'' +
                '}';
    }

    @BindingAdapter("set_image")
    public static void set_image(ImageView view, String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()){
//            Glide.with(view).load(imageUrl).placeholder(R.drawable.sarwate_logo).into(view);
            Picasso.get().load(imageUrl).placeholder(R.drawable.sarwate_logo).into(view);
        }
    }
}
