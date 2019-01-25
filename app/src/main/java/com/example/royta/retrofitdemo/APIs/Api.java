package com.example.royta.retrofitdemo.APIs;

import com.example.royta.retrofitdemo.ModelClass.DefaultResponse;
import com.example.royta.retrofitdemo.ModelClass.LoginResponse;
import com.example.royta.retrofitdemo.ModelClass.UserExist;
import com.example.royta.retrofitdemo.ModelClass.UsersResponse;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface Api {
    @FormUrlEncoded        //sending a FormUrlEncodedRequest
    @POST("createuser")    //defining end point from url

    /*
    Call<ResponseBody> createUser(
        @Field("email") String email,
        @Field("password") String password,        //creating a Call. <Response type of the call(What response we are going to get)
        @Field("name") String name,                //We can use ResponseBody if we don't have any idea what response we will get
        @Field("school") String school
    );
    */

    Call<DefaultResponse> createUser(
            @Field("email") String email,
            @Field("password") String password,
            @Field("name") String name,
            @Field("school") String school
    );

    @FormUrlEncoded
    @POST("userlogin")
    Call<LoginResponse> userLogin (
            @Field("email") String email,
            @Field("password") String password
    );

    @GET("allusers")
    Call<UsersResponse> getUsers();

    @FormUrlEncoded
    @PUT("updateuser/{id}")
    Call<LoginResponse> updateUser (
            @Path("id") int id,
            @Field("email") String email,
            @Field("name") String name,
            @Field("school") String school
    );

    @FormUrlEncoded
    @PUT("updatepassword")
    Call<DefaultResponse> updatePassword(
            @Field("currentpassword") String currentpassword,
            @Field("newpassword") String newpassword,
            @Field("email") String email
    );

    @DELETE("deleteuser/{id}")
    Call<DefaultResponse> deleteUser (
            @Path("id") int id
    );

    /*
    @FormUrlEncoded
    @POST("finduser")
    Call<UserExist> isUserExist(
            @Field("email") String email
    ); */



    //Recover forgotten password
    @GET("finduser")
    Call<UserExist> isUserExist(
            @Query("email") String email

    );
}
