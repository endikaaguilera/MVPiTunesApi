package com.endikaaguilera.mvpitunesapi.api;

import com.endikaaguilera.mvpitunesapi.models.IAData;

import retrofit2.Call;
import retrofit2.http.GET;

import static com.endikaaguilera.mvpitunesapi.utils.IANetUtils.API_QUERY;

public interface IAApi {

    @GET(API_QUERY)
    Call<IAData> getData();

}
