package android.example.bakingapp;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.example.bakingapp.adapter.IngredientAdapter;
import android.example.bakingapp.adapter.StepsAdapter;
import android.example.bakingapp.model.Ingredient;
import android.example.bakingapp.model.Step;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class DetailsFragment extends Fragment
         {
    private RecyclerView recyclerView1;
    private StepsAdapter adapter1;
    private RecyclerView recyclerView2;
    private IngredientAdapter adapter2;
    List<Ingredient> ingredientList=new ArrayList<>();
    int id ;



    public DetailsFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {






        View view = inflater.inflate(R.layout.detail_fragment, container, false);
       List<Step> steps= new ArrayList<>();

        Button  WidgetButton ;
        WidgetButton= view.findViewById( R.id.WidgetButtom );



      recyclerView1 =view.findViewById(R.id.recycler_view2);
        adapter1=new StepsAdapter(getActivity(),steps);

       RecyclerView.LayoutManager layoutManager1 = new LinearLayoutManager(getActivity());
        recyclerView1.setLayoutManager(layoutManager1);

        recyclerView1.setAdapter(adapter1);
        adapter1.notifyDataSetChanged();
        final Intent intent = getActivity().getIntent();


        WidgetButton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(intent.hasExtra( "name" )){

                    String name;


                   name=intent.getStringExtra("name");

                    String allIngredient=" ";
                    if (intent.hasExtra( "ingredient" )){
                        String i =" ";
                        String q=" ";
                        String m=" ";


                        ingredientList= (List<Ingredient>) intent.getSerializableExtra("ingredient");
                    for(int index=0;index<ingredientList.size();index++){


                        i+=ingredientList.get( index ).getIngredient();
                        q+=ingredientList.get( index ).getQuantity().toString();
                        m+=ingredientList.get( index ).getMeasure();
                        allIngredient+=m+" "+q+" of "+i+"\n";



                    }

                        }

                    SharedPreferences sharedPreferences =getActivity().getSharedPreferences("MyData", MODE_PRIVATE);
                     SharedPreferences.Editor editor1 = sharedPreferences.edit();

            String nameR = sharedPreferences.getString("name","");
            String ingredientR = sharedPreferences.getString("ingredient","");
           if(nameR!=null){

            editor1.remove( "name" );
            editor1.remove( "ingredient" );
            editor1.commit();
             }

                    SharedPreferences sharedPref = getActivity().getSharedPreferences("MyData",MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("name",name);
                    editor.putString( "ingredient",allIngredient );

                    editor.commit();
                    Toast.makeText(getActivity(), name , Toast.LENGTH_LONG ).show();




                    UpdateService.ActionUpdateWidgets(getActivity());


                }


            }
        } );

        recyclerView2 = view.findViewById(R.id.recycler_view3);



        adapter2=new IngredientAdapter(getActivity(),ingredientList);



        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView2.setLayoutManager(layoutManager);




        recyclerView2.setAdapter(adapter2);
        adapter2.notifyDataSetChanged();

        if (intent.hasExtra( "ingredient" )){
            ingredientList= (List<Ingredient>) intent.getSerializableExtra("ingredient");


            recyclerView2.setAdapter(new IngredientAdapter(getActivity(), ingredientList));
            adapter2.notifyDataSetChanged();
        if(intent.hasExtra( "steps" )){
            steps= (List<Step>) intent.getSerializableExtra("steps");

            recyclerView1.setAdapter(new StepsAdapter(getActivity(), steps));

            adapter1.notifyDataSetChanged();

        }}



       return  view;




    }
}
