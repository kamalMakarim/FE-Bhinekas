package com.example.fe_bhinekas.request;

import com.example.fe_bhinekas.model.BaseResponse;
import com.example.fe_bhinekas.model.Chat;
import com.example.fe_bhinekas.model.Extracurricular;
import com.example.fe_bhinekas.model.Kelas;
import com.example.fe_bhinekas.model.Log;
import com.example.fe_bhinekas.model.Student;
import com.example.fe_bhinekas.model.Teacher;
import com.example.fe_bhinekas.model.User;
import com.example.fe_bhinekas.model.response.LoginReponse;
import com.example.fe_bhinekas.model.response.LoginRequest;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface BaseApiService {
    @POST("user/login")
    Call<BaseResponse<LoginReponse>> login(@Body LoginRequest loginRequest);

    @GET("log/getLogs")
    Call<BaseResponse<List<Log>>> getLogs(@Query("class_id") String class_id, @Query("for_special_kids") boolean for_special_kids);

    @GET("chat/getChatLogs")
    Call<BaseResponse<List<Chat>>>getChatLogs(@Query("student_id") String student_id, @Query("log_id") String log_id, @Query("reader_id") String reader_id);

    @FormUrlEncoded
    @POST("chat/postChatLog")
    Call<BaseResponse<Chat>> postChatLog(@Field("sender_id") String sender_id, @Field("student_id") String student_id, @Field("message") String message, @Field("log_id") String log_id);

    @GET("teacher/getMystudents")
    Call<BaseResponse<List<Student>>> getMyStudents(@Query("class_id") String class_id);

    @FormUrlEncoded
    @POST("log/postLog")
    Call<BaseResponse<Log>> postLog(@Field("message") String message, @Field("class_id") String class_id, @Field("for_special_kids") boolean for_special_kids);

    @FormUrlEncoded
    @POST("admin/addUser")
    Call<BaseResponse<User>> addUser(@Field("username") String username, @Field("password") String password, @Field("display_name") String display_name, @Field("role") String role);

    @GET("admin/getStudents")
    Call<BaseResponse<List<Student>>> getStudents(@Query("student_class_id") String student_class_id, @Query("parent_name") String parent_name, @Query("batch") int batch, @Query("special_needs") boolean special_needs, @Query("extracurricular_id") String extracurricular_id);

    @GET("admin/getAllKelas")
    Call<BaseResponse<List<Kelas>>> getAllKelas();

    @GET("admin/getAllExtracurriculars")
    Call<BaseResponse<List<Extracurricular>>> getAllExtracurriculars();
}
