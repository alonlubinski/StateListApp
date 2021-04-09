package com.alon.statelistapp.Daos;

import com.alon.statelistapp.Models.State;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface StateListAPI {

    @GET("all?fields=name;nativeName;borders;flag")
    Call<ArrayList<State>> loadStateList();

    @GET("{stateCode}?fields=name;nativeName;flag")
    Call<State> loadState(@Path("stateCode") String stateCode);
}
