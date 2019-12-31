package android.example.bakingapp.api;

import android.example.bakingapp.model.Baking;

import com.google.gson.JsonArray;

import org.json.JSONArray;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface Service {


    @GET("baking.json")
    Call<JsonArray> getbaking();



}
