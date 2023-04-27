package com.flaxeninfosoft.sarwateAcademy.models;

import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;

import com.bumptech.glide.Glide;
import com.flaxeninfosoft.sarwateAcademy.R;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.squareup.picasso.Picasso;

public class Course {

    @SerializedName("id")
    @Expose
    private Long id;

    @SerializedName("imageurl")
    @Expose
    private String imageUrl;

    @SerializedName("courseName")
    @Expose
    private String courseName;

    @SerializedName("name")
    @Expose
    private String teacherName;

    @SerializedName("syllabusPdf")
    @Expose
    private String syllabusPdf;

    @SerializedName("description")
    @Expose
    private String description;

    @SerializedName("categoryId")
    @Expose
    private Long categoryId;

    @SerializedName("price")
    @Expose
    private String price;

    @SerializedName("category")
    @Expose
    private Category category;

    @SerializedName("start_date")
    @Expose
    private String startDate;

    @SerializedName("end_date")
    @Expose
    private String endDate;

    @SerializedName("course_duration")
    @Expose
    private String course_duration;

    @SerializedName("language")
    @Expose
    private String language;

    @SerializedName("course_validity")
    @Expose
    private String course_validity;


    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    @SerializedName("courseId")
    @Expose
    private Long courseId;
    public String isFree() {
        return isFree;
    }

    public void setFree(String  free) {
        isFree = free;
    }

    @SerializedName("isFree")
    @Expose
    private String isFree;


    @SerializedName("status")
    @Expose
    private String status;

    public Course() {
    }

    public Course(String imageUrl, String teacherName, String courseName, String price) {

        this.courseName = courseName;
        this.imageUrl = imageUrl;
        this.teacherName = teacherName;
        this.price = price;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getCourseName() {
        return courseName;
    }

    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    public String getTeacherName() {
        return teacherName;
    }

    public void setTeacherName(String teacherName) {
        this.teacherName = teacherName;
    }

    public String getSyllabusPdf() {
        return syllabusPdf;
    }

    public void setSyllabusPdf(String syllabusPdf) {
        this.syllabusPdf = syllabusPdf;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }


    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getCourse_duration() {
        return course_duration;
    }

    public void setCourse_duration(String course_duration) {
        this.course_duration = course_duration;
    }

    public String getCourse_validity() {
        return course_validity;
    }

    public void setCourse_validity(String course_validity) {
        this.course_validity = course_validity;
    }


    @Override
    public String toString() {
        return "Course{" +
                "id=" + id +
                ", imageUrl='" + imageUrl + '\'' +
                ", courseName='" + courseName + '\'' +
                ", teacherName='" + teacherName + '\'' +
                ", syllabusPdf='" + syllabusPdf + '\'' +
                ", description='" + description + '\'' +
                ", categoryId=" + categoryId +
                ", price='" + price + '\'' +
                ", category=" + category +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", language='" + language + '\'' +
                ", course_duration='" + course_duration + '\'' +
                ", course_validity='" + course_validity + '\'' +
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
