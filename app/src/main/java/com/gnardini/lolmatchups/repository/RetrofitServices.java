package com.gnardini.lolmatchups.repository;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitServices {

    private final Retrofit retrofit;
    private final Map<Class, Object> services;

    public RetrofitServices(String endpoint) {
        services = new HashMap<>();
        retrofit = new Retrofit.Builder()
                .baseUrl(endpoint)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .client(getOkHttpClient())
                .build();
    }

    /**
     * Returns an instance of Gson to use for conversion.
     *
     * @return A configured Gson instance
     */
    private Gson getGson() {
        return new GsonBuilder().create();
    }

    /**
     * Returns an OkHttpClient.
     * This method calls <i>initClient(builder)</i> to configure the builder for OkHttpClient.
     *
     * @return A configured instance of OkHttpClient.
     */
    private OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        initClient(builder);
        return builder.build();
    }

    /**
     * Configures an <i>OkHttpClient.Builder</i>.
     * You must add interceptors and configure the builder inside this method.
     */
    private void initClient(OkHttpClient.Builder builder) {
        HttpLoggingInterceptor loggerInterceptor = new HttpLoggingInterceptor();
        loggerInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        builder.addInterceptor(loggerInterceptor);
    }

    /**
     * Builds and returns a Retrofit Service.
     * If the service wasn't accessed, it'll be created and cached internally.
     * On successive requests, the already created instance will be returned.
     *
     * @param clazz RetrofitService Class
     * @param <T> Service class
     * @return service
     */
    public <T> T getService(Class<T> clazz) {
        T service = (T) services.get(clazz);
        if (service != null) return service;
        service = retrofit.create(clazz);
        services.put(clazz, service);
        return service;
    }

    /* Override this class and define the services like this:
     *
     * public static UserService user() {
     *  return getService(UserService.class);
     * }
     *
     */
}
