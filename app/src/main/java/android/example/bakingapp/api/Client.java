package android.example.bakingapp.api;



import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Client {

  private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";


    private static Retrofit retrofit=null;

    public static Retrofit getClient(){
        if (retrofit==null){
            retrofit =new retrofit2.Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory( GsonConverterFactory.create())

                    .build();
        }
        return  retrofit;

    }}

