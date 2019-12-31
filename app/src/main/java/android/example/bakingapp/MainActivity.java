package android.example.bakingapp;


import android.example.bakingapp.adapter.MainAdapter;
import android.example.bakingapp.api.Client;
import android.example.bakingapp.api.Service;
import android.example.bakingapp.model.Baking;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.test.espresso.idling.CountingIdlingResource;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;


import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class MainActivity extends AppCompatActivity  {

    RecyclerView recyclerView;

    private MainAdapter adapter;
    private List<Baking> BakingList;

    TextView name;

    ImageView img;
    CountingIdlingResource idlingResource =new CountingIdlingResource("DATA_Loader");



    public androidx.test.espresso.IdlingResource getIdlingResource() {
        return idlingResource;
    }




    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_main );





        recyclerView = findViewById(R.id.recycler_view1);

        BakingList= new ArrayList<>(  );
        name=findViewById( R.id.Rname );

        img=findViewById( R.id.imageView );



        adapter=new MainAdapter(getApplicationContext(),BakingList);


        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(layoutManager);

        recyclerView.setItemAnimator( new DefaultItemAnimator() );
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

 idlingResource.increment();
        try {


           Client Client=new Client();
           Service api= android.example.bakingapp.api.Client.getClient().create(Service.class);

            Call<JsonArray> call = api.getbaking();



            call.enqueue(new Callback< JsonArray>() {
                @Override
                public void onResponse(Call< JsonArray> call, Response<JsonArray> response) {

                                             if (response.isSuccessful()) {
                                                 if (response.body() != null) {
                                                     String listString = response.body().toString();

                                                     Type listType = new TypeToken<List<Baking>>() {
                                                     }.getType();
                                                     BakingList = getListFromJson(listString, listType);
                                                     //BakingList.get( 0 ).setImage(  );
                                                     recyclerView.setItemAnimator(new DefaultItemAnimator());
                                                     recyclerView.setAdapter(new MainAdapter(getApplicationContext(), BakingList));
                                                     idlingResource.decrement();
                                                    //TODO mIdlingResource.setIdleState(true);
                                                 }
                                             }



                }

                @Override
                public void onFailure(Call<JsonArray> call, Throwable t) {
                    Log.d("Error", ( t.getMessage() ) );
                    Toast.makeText(getApplicationContext(),"Error fetchig data",Toast.LENGTH_SHORT).show();
                    t.printStackTrace();

                }
            });

        } catch (Exception e) {
            Log.d("Error", ( e.getMessage() ) );
            Toast.makeText(getApplicationContext(),e.toString(),Toast.LENGTH_SHORT).show();
            e.printStackTrace();
        }

    }



    private static <T> List<T> getListFromJson(String jsonString, Type type) {
        if (!isValid(jsonString)) {
            return null;
        }
        return new Gson().fromJson(jsonString, type);
    }

    private static boolean isValid(String json) {
        try {
            new JsonParser().parse(json);
            return true;
        } catch (JsonSyntaxException jse) {
            return false;
        }
    }
}
