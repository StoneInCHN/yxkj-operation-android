package com.yxkj.deliveryman.http;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yxkj.deliveryman.constant.Constants;
import com.yxkj.deliveryman.sharepreference.SharePrefreceHelper;
import com.yxkj.deliveryman.sharepreference.SharedKey;
import com.yxkj.deliveryman.util.LogUtil;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * RetrofitFactory
 * Created by zengqiang on 2017/8/24.
 */
public class RetrofitFactory {

    private static final long TIMEOUT = 30;

    public static OkHttpClient.Builder sHttpBuilder;
    public static Interceptor sInterceptor;
    private static OkHttpClient sHttpClient;

    public static void initHttpClient() {
        sHttpBuilder = new OkHttpClient.Builder();
        initInterceptor();
        sHttpClient = sHttpBuilder.addInterceptor(sInterceptor)
                .addInterceptor(new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                    @Override
                    public void log(String message) {

                    }
                }).setLevel(HttpLoggingInterceptor.Level.BASIC))
                .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
                .readTimeout(TIMEOUT, TimeUnit.SECONDS)
                .build();
    }

    private static void initInterceptor() {
        sInterceptor = new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request.Builder builder = chain.request().newBuilder();
                // 替换为自己的token
                builder.addHeader("X-Auth-Token", SharePrefreceHelper.getInstance().getString(SharedKey.TOKEN));
                LogUtil.i(builder.build().tag().toString());
                return chain.proceed(builder.build());
            }
        };
    }

    private static RetrofitService retrofitService;
    /**
     * header是否变化
     */
    public static boolean isHeaderChange;

    public static RetrofitService getInstance() {
        if (retrofitService == null || isHeaderChange) {
            resetHttpClient();
            initService();
            isHeaderChange = false;
        }
        return retrofitService;
    }

    private static void initService() {
        retrofitService = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                // 添加Gson转换器
                .addConverterFactory(GsonConverterFactory.create(buildGson()))
                // 添加Retrofit到RxJava的转换器
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(sHttpClient)
                .build()
                .create(RetrofitService.class);
    }

    /**
     * 由于header需要添加token，所以需要重新设置httpclient，有无更优雅方法？
     */
    public static void resetHttpClient() {
        isHeaderChange = true;
        initHttpClient();
    }

    private static Gson buildGson() {
        return new GsonBuilder()
                .serializeNulls()
                .setFieldNamingPolicy(FieldNamingPolicy.IDENTITY)
                // 此处可以添加Gson 自定义TypeAdapter
//                .registerTypeAdapter(User.class, new UserTypeAdapter())
                .create();
    }

}
