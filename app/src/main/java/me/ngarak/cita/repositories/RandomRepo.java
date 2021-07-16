package me.ngarak.cita.repositories;

import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import me.ngarak.cita.animeQueries;
import me.ngarak.cita.models.QuoteResponse;
import me.ngarak.cita.retrofitInstance;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RandomRepo {
    private final String TAG = getClass().getSimpleName();
    private final List<QuoteResponse> responseList = new ArrayList<>();

    public MutableLiveData<List<QuoteResponse>> requestQuote () {
        MutableLiveData<List<QuoteResponse>> mutableLiveData = new MutableLiveData<>();

        //initializing retrofit
        animeQueries queries = retrofitInstance.getAnimeInst().create(animeQueries.class);
        //network call
        Call<List<QuoteResponse>> responseCall = queries.getRandomQuotes();
        responseCall.enqueue(new Callback<List<QuoteResponse>>() {
            @Override
            public void onResponse(@NotNull Call<List<QuoteResponse>> call, @NotNull Response<List<QuoteResponse>> response) {
                if (response.isSuccessful()){
                    if (response.body() != null) {
                        responseList.addAll(response.body());
                        mutableLiveData.postValue(responseList);
                    }
                }
            }

            @Override
            public void onFailure(@NotNull Call<List<QuoteResponse>> call, @NotNull Throwable t) {
                Log.d(TAG, "onFailure: " ,  t);
            }
        });

        return mutableLiveData;
    }
}
