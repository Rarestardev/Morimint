package com.rarestardev.morimint.Api;

import com.rarestardev.morimint.Model.DailyRewardModel;
import com.rarestardev.morimint.Model.MoriNewsModel;
import com.rarestardev.morimint.Model.Users;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;

public interface ApiService {

    @Headers("Authorization: " + ApiClient.SERVER_TOKEN)
    @GET("controls/news")
    Call<List<MoriNewsModel>> GetMoriNews();

    @Headers("Authorization: " + ApiClient.SERVER_TOKEN)
    @GET("controls/Dailybonus")
    Call<List<DailyRewardModel>> GetDailyReward();

    @Multipart
    @POST("user/signup")
    Call<ApiResponse> Sign_up(@Part("username") RequestBody username,
                              @Part("email") RequestBody email,
                              @Part("password") RequestBody password);

    @Multipart
    @POST("user/login")
    Call<ApiResponse> Login(@Part("username") RequestBody username, @Part("password") RequestBody password);


    @GET("user/userdata")
    Call<Users> UserData(@Header("Authorization") String token);

    @Multipart
    @PUT("user/UpdateUserData")
    Call<Users> putCoinData(@Part MultipartBody.Part coin, @Header("Authorization") String token);

}
