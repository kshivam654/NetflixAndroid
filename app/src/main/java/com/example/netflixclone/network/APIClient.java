package com.example.netflixclone.network;

import com.example.netflixclone.network.events.APIEvent;

import org.greenrobot.eventbus.EventBus;
import org.json.JSONObject;

import java.nio.charset.Charset;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import okio.Buffer;
import okio.BufferedSource;
import retrofit2.Retrofit;
import retrofit2.converter.scalars.ScalarsConverterFactory;

import static com.example.netflixclone.network.APIConstants.*;
public class APIClient {
    public static Retrofit getClient() {
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor)
                .addInterceptor(chain -> {
                    Request request = chain.request();
                    Response response = chain.proceed(request);
                    ResponseBody responseBody = response.body();
                    BufferedSource source = responseBody.source();
                    source.request(Long.MAX_VALUE); //Buffer the entire body
                    Buffer buffer = source.buffer();
                    String respData = buffer.clone().readString(Charset.defaultCharset());
                    JSONObject resp = null;
                    try {
                        resp = new JSONObject(respData);
                    }catch (Exception e) {
                        e.printStackTrace();
                    }

                    if (resp != null) {
                        switch (resp.optInt(Params.ERROR_CODE)) {
                            case ErrorCodes.TOKEN_EXPIRED:
                                emitEvent(ErrorCodes.TOKEN_EXPIRED, resp.optString(Params.ERROR_MSG));
                                break;
                            case ErrorCodes.SUB_PROFILE_DOESNT_EXIST:
                                emitEvent(ErrorCodes.SUB_PROFILE_DOESNT_EXIST,resp.optString(Params.ERROR_MSG));
                                break;

                            case ErrorCodes.USER_DOESNT_EXIST:
                                emitEvent(ErrorCodes.USER_DOESNT_EXIST,resp.optString(Params.ERROR_MSG));
                                break;
                            case ErrorCodes.USER_RECORD_DELETED_CONTACT_ADMIN:
                                emitEvent(ErrorCodes.USER_RECORD_DELETED_CONTACT_ADMIN,resp.optString(Params.ERROR_MSG));
                                break;

                            case ErrorCodes.INVALID_TOKEN:
                                emitEvent(ErrorCodes.INVALID_TOKEN,resp.optString(Params.ERROR_MSG));
                                break;
                            case ErrorCodes.USER_LOGIN_DECLINED:
                                emitEvent(ErrorCodes.USER_LOGIN_DECLINED,resp.optString(Params.ERROR_MSG));
                                break;
                            case ErrorCodes.EMAIL_NOT_ACTIVATED:
                                emitEvent(ErrorCodes.USER_LOGIN_DECLINED,resp.optString(Params.ERROR_MSG));

                        }
                    }
                    return response;
                }).build();

        return new Retrofit.Builder()
                .baseUrl(URLs.BASE_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .client(client)
                .build();
    }

    private static void emitEvent(int code, String message) {
        EventBus.getDefault().post(new APIEvent(message,code));
    }
}
