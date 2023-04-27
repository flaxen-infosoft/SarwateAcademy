package com.flaxeninfosoft.sarwateAcademy.api;

import com.flaxeninfosoft.sarwateAcademy.models.LoginModel;
import com.flaxeninfosoft.sarwateAcademy.models.Teacher;
import com.flaxeninfosoft.sarwateAcademy.models.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface AuthApiInterface {
    @FormUrlEncoded
    @POST(ApiEndpoints.STUDENT_LOGIN)
    Call<User> loginStudent(@Field(Constants.ROLE) String role,
                            @Field(Constants.EMAIL) String email,
                            @Field(Constants.PHONE) String phone,
                            @Field(Constants.PASSWORD) String password);

    @FormUrlEncoded
    @POST(ApiEndpoints.REGISTER_USER)
    Call<User> registerStudent(@Field(Constants.ROLE) String role,
                               @Field(Constants.NAME) String name,
                               @Field(Constants.EMAIL) String email,
                               @Field(Constants.PHONE) String phone,
                               @Field(Constants.PASSWORD) String password);

    @FormUrlEncoded
    @POST(ApiEndpoints.REGISTER_TEACHER)
    Call<User> registerTeacher(@Field(Constants.ROLE) String role,
                               @Field(Constants.NAME) String name,
                               @Field(Constants.EMAIL) String email,
                               @Field(Constants.PHONE) String phone,
                               @Field(Constants.PASSWORD) String password);

    @FormUrlEncoded
    @POST(ApiEndpoints.TEACHER_LOGIN)
    Call<User> loginTeacher(@Field(Constants.ROLE) String role,
                            @Field(Constants.EMAIL) String email,
                            @Field(Constants.PHONE) String phone,
                            @Field(Constants.PASSWORD) String password);
}
