package com.eduardoflores.daggerconversiondemo_start.service;

import com.eduardoflores.daggerconversiondemo_start.BuildConfig;
import com.eduardoflores.daggerconversiondemo_start.model.InitialResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * @author Eduardo Flores
 */
public class Service
{
    private Services servicesInterface;

    private Retrofit retrofit;

    public Service()
    {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient.Builder()
                .addInterceptor(new Interceptor() {
                    @Override
                    public okhttp3.Response intercept(Chain chain) throws IOException
                    {
                        // add headers, if they exist
                        for (Map.Entry<String, String> entry : getRequestHeaders().entrySet())
                        {
                            chain.request().newBuilder().addHeader(entry.getKey(), entry.getValue());
                        }
                        okhttp3.Response response = chain.proceed(chain.request());
                        return response;
                    }
                })
                .addInterceptor(loggingInterceptor)
                .build();

        retrofit = new Retrofit.Builder()
                .baseUrl(BuildConfig.SERVICE_DOMAIN)
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();
    }

    public Call<InitialResponse> getWidget()
    {
        servicesInterface = retrofit.create(Services.class);
        return servicesInterface.getWidget();
    }

    private Map<String, String> getRequestHeaders()
    {
        Map<String, String> headers = new HashMap<>();

        headers.put("Accept", "application/json");
        headers.put("Content-Type", "application/json");
        headers.put("Cache-Control", "no-cache");

        return headers;
    }
}
