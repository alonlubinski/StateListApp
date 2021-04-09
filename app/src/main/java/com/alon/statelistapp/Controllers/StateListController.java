package com.alon.statelistapp.Controllers;

import android.util.Log;

import com.alon.statelistapp.Models.State;
import com.alon.statelistapp.Daos.StateListAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class StateListController implements Callback<ArrayList<State>> {

    static final String BASE_URL = "https://restcountries.eu/rest/v2/";

    private Callback_StateList callback_stateList;

    public void start(Callback_StateList callback_stateList){
        this.callback_stateList = callback_stateList;
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        StateListAPI stateListAPI = retrofit.create(StateListAPI.class);
        Call<ArrayList<State>> call = stateListAPI.loadStateList();
        call.enqueue(this);
    }


    @Override
    public void onResponse(Call<ArrayList<State>> call, Response<ArrayList<State>> response) {
        if(response.isSuccessful()){
            if(callback_stateList != null){
                callback_stateList.onResult(response.body());
            }
        } else {
            Log.d("pttt", response.errorBody().toString());
        }
    }

    @Override
    public void onFailure(Call<ArrayList<State>> call, Throwable t) {
        t.printStackTrace();
    }

    public interface Callback_StateList {
        void onResult(ArrayList<State> stateList);
    }
}
