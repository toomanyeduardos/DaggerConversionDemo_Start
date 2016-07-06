package com.eduardoflores.daggerconversiondemo_start.service;

import com.eduardoflores.daggerconversiondemo_start.model.InitialResponse;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * @author Eduardo Flores
 */
public interface Services
{
    @GET("/bins/4lqo7")
    Call<InitialResponse> getWidget();
}
