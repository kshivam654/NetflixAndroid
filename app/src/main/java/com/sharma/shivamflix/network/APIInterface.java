package com.sharma.shivamflix.network;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import retrofit2.http.Path;

import static com.sharma.shivamflix.network.APIConstants.APIs;
public interface APIInterface {

    @FormUrlEncoded
    @POST(APIs.LOGIN)
    Call<String> loginUser(@Path("name") String name, @Path("password") String password);
}
