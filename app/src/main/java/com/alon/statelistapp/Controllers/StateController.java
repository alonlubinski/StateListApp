package com.alon.statelistapp.Controllers;

import android.util.Log;

import com.alon.statelistapp.Models.State;
import com.alon.statelistapp.Daos.StateListAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StateController implements Callback<State> {

    static final String BASE_URL = "https://restcountries.eu/rest/v2/alpha/";

    private StateController.Callback_State callback_state;

    public void start(String stateCode, Callback_State callback_state){
        this.callback_state = callback_state;
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        StateListAPI stateListAPI = retrofit.create(StateListAPI.class);
        Call<State> call = stateListAPI.loadState(stateCode);
        call.enqueue(this);
    }
    @Override
    public void onResponse(Call<State> call, Response<State> response) {
        if(response.isSuccessful()){
            if(callback_state != null){
                callback_state.onResult(response.body());
            }
        } else {
            Log.d("pttt", response.errorBody().toString());
        }
    }

    @Override
    public void onFailure(Call<State> call, Throwable t) {
        t.printStackTrace();
    }

    public interface Callback_State {
        void onResult(State state);
    }
}
