package com.example.netflixclone.network;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import static com.example.netflixclone.network.APIConstants.APIs;
public interface APIInterface {

    @FormUrlEncoded
    @POST(APIs.LOGIN)
    Call<String> loginUser(@Field())
}
