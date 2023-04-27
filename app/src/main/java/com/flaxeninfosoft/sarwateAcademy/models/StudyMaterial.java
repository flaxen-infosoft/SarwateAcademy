package com.flaxeninfosoft.sarwateAcademy.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudyMaterial {

    @SerializedName("id")
    @Expose
    private Long id;
    @SerializedName("courseId")
    @Expose
    private Long courseId;
    @SerializedName("fileName")
    @Expose
    private String fileName;
    @SerializedName("description")
    @Expose

    private String description;
    @SerializedName("filePath")
    @Expose
    private String filePath;
    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("teacherId")
    @Expose
    private String teacherId;
    @SerializedName("isFree")
    @Expose
    private String isFree;


    private String course;

    public StudyMaterial() {
    }

    public StudyMaterial(String fileName) {
        this.fileName = fileName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(String teacherId) {
        this.teacherId = teacherId;
    }

    public String getIsFree() {
        return isFree;
    }

    public void setIsFree(String isFree) {
        this.isFree = isFree;
    }

    @Override
    public String toString() {
        return "StudyMaterial{" +
                "id=" + id +
                ", courseId=" + courseId +
                ", fileName='" + fileName + '\'' +
                ", description='" + description + '\'' +
                ", filePath='" + filePath + '\'' +
                ", date='" + date + '\'' +
                ", teacherId='" + teacherId + '\'' +
                ", isFree='" + isFree + '\'' +
                ", course='" + course + '\'' +
                '}';
    }
}
