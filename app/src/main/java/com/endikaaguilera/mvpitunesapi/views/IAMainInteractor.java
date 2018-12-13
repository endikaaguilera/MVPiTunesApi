package com.endikaaguilera.mvpitunesapi.views;

import android.util.Log;

import com.endikaaguilera.mvpitunesapi.api.IAApi;
import com.endikaaguilera.mvpitunesapi.models.IAData;
import com.endikaaguilera.mvpitunesapi.utils.IARetrofitUtils;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class IAMainInteractor {

    public interface IAGetDataListener {

        void setData(IAData results);

    }

    private IAGetDataListener listener;

    void onDestroy() {

        this.listener = null;

    }

    void getData(IAGetDataListener listener) {

        this.listener = listener;
        getData();

    }

    private void getData() {

        IAApi restClient = IARetrofitUtils.getInstance().create(IAApi.class);

        Call<IAData> call = restClient.getData();

        //noinspection NullableProblems
        call.enqueue(new Callback<IAData>() {
            @Override
            public void onResponse(
                    Call<IAData> call,
                    Response<IAData> response) {

                if (response.code() == IARetrofitUtils.REQUEST_CODE_OK) {

                    IAData results = response.body();

                    if (results == null ||
                            results.getBalkanBeatBox() == null ||
                            results.getBalkanBeatBox().size() == 0) {

                        if (listener != null) listener.setData(null);
                        return;
                    }

                    if (listener != null) listener.setData(results);

                } else {

                    if (listener != null) listener.setData(null);

                }

            }

            @Override
            public void onFailure(Call<IAData> call, Throwable t) {

                Log.e("TEST", "error 04");
                if (listener != null) listener.setData(null);

            }

        });

    }

}
