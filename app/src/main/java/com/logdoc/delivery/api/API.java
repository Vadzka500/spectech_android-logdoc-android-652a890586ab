package com.logdoc.delivery.api;


import com.logdoc.delivery.api.model.AuthRequest;
import com.logdoc.delivery.api.model.CommonResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

/**
 * Created by Dmitry Ushkevich on 16.05.2018.
 */

public interface API {

    String BASE_URL = "http://31.130.201.235:8080/";

    @GET("user/current")
    Call<CommonResponse> getUser(@Header("Authorization") String bearer);

    @FormUrlEncoded
    @Headers({"Content-Type: application/x-www-form-urlencoded", "Authorization: Basic d2ViOm1vYmlsZQ=="})
    @POST("oauth/token")
//    Call<CommonResponse> authUser(@Body AuthRequest body);//, @Header("Authorization") String basic);
//    Call<CommonResponse> authUser(@Header("Content-Type") String content, @Header("Authorization") String authToken, @Part("username") RequestBody login, @Part("password") RequestBody password, @Part("grant_type") RequestBody grant_type);
    Call<CommonResponse> authUser(@Field("username") String login, @Field("password") String password, @Field("grant_type") String grant_type);

//    @Multipart
//    @POST("XXXX")
//    Call<PlanResponse> myPlans(@Part("login") RequestBody actionId, @Part("password") RequestBody offerCode);
}
