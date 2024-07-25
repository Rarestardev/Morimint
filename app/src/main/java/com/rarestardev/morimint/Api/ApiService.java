package com.rarestardev.morimint.Api;

import com.rarestardev.morimint.Model.ApplicationSetupModel;
import com.rarestardev.morimint.Model.DailyRewardModel;
import com.rarestardev.morimint.Model.GiftCodeModel;
import com.rarestardev.morimint.Model.MoriNewsModel;
import com.rarestardev.morimint.Model.Users;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface ApiService {


    @GET("/controls/news")
    Call<List<MoriNewsModel>> GetMoriNews(@Header("Authorization") String token);


    @GET("/controls/confing")
    Call<ApplicationSetupModel> GetApplicationSetup(@Header("Authorization") String token);

    @GET("/controls/Dailybonus")
    Call<List<DailyRewardModel>> GetDailyReward(@Header("Authorization") String token);


    @Headers("Content-Type: application/json")
    @POST("/controls/Dailybonus")
    Call<DailyRewardResponse> SetDailyReward(@Header("Authorization") String token,@Body DailyRewardModel dailyRewardModel);

    @Multipart
    @POST("/user/signup")
    Call<ApiResponse> Sign_up(@Part("username") RequestBody username,
                              @Part("email") RequestBody email,
                              @Part("password") RequestBody password);

    @Multipart
    @POST("/user/login")
    Call<ApiResponse> Login(@Part("username") RequestBody username, @Part("password") RequestBody password);


    @GET("/user/userdata")
    Call<Users> UserData(@Header("Authorization") String token);

    @Multipart
    @PUT("/user/UpdateUserData")
    Call<Users> putCoinData(@Part MultipartBody.Part coin, @Header("Authorization") String token);

    @Headers("Content-Type: application/json")
    @POST("/user/giftcheck")
    Call<ApiResponse> SiteGiftCode(@Header("Authorization") String token, @Body GiftCodeModel giftCodeModel);

    @Headers("Content-Type: application/json")
    @POST("/user/code")
    Call<ApiResponse> GiftCode(@Header("Authorization") String token, @Body GiftCodeModel giftCodeModel);

}
