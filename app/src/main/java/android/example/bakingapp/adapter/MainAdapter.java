package android.example.bakingapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.example.bakingapp.MasterList;

import android.example.bakingapp.R;
import android.example.bakingapp.model.Baking;
import android.example.bakingapp.model.Ingredient;
import android.example.bakingapp.model.Step;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainAdapter extends RecyclerView.Adapter<MainAdapter.MyViewHolder>{
    private Context mContext;
    private List<Baking> BakingList=new ArrayList<>();

    public MainAdapter(Context mContext, List<Baking> BakingList) {
        this.mContext = mContext;
        this.BakingList = BakingList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.baking_list , parent, false);


        return  new MyViewHolder( view );

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        Baking recipe = BakingList.get(position);


        holder.Name.setText(recipe.getName());
      String thumbnail = recipe.getImage();

        if(position == 0){
            recipe.setImage( thumbnail );
            Glide.with(mContext)
                    .load(thumbnail)
                    .into(holder.Img);
        }



    }

    @Override
    public int getItemCount() {
        return BakingList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        private TextView Name;

        ImageView Img;
        public MyViewHolder(@NonNull View itemView) {
            super( itemView );

            Img=itemView.findViewById(R.id.imageView);
            Name=itemView.findViewById(R.id.Rname);


            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION) {
                        Baking clickedItem=BakingList.get( position );
                        String name=clickedItem.getName();
                          int id=clickedItem.getId();

                     List<Ingredient> ingredientsList=new ArrayList<>();
                     List<Step> stepList=new ArrayList<>(  );

                       ingredientsList=clickedItem.getIngredients();
                        stepList=clickedItem.getSteps();


                        Intent intent=new Intent( mContext, MasterList.class );


                        intent.putExtra("ingredient", (Serializable) ingredientsList );
                        intent.putExtra("steps", (Serializable) stepList );
                        intent.putExtra( "name",name );
                        intent.putExtra( "id",id );
                        intent.addFlags( android.content.Intent.FLAG_ACTIVITY_NEW_TASK );
                        mContext.startActivity(intent);

                    }
                }
            });


    }
    }
}
