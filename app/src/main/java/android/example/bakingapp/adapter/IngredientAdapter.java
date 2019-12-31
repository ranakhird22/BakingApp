package android.example.bakingapp.adapter;

import android.content.Context;

import android.example.bakingapp.R;

import android.example.bakingapp.model.Ingredient;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.MyViewHolder> {
    private Context mContext;
    private List<Ingredient> ingredientList=new ArrayList<>();

    public IngredientAdapter(Context mContext, List<Ingredient> ingredientList) {
        this.mContext = mContext;
        this.ingredientList = ingredientList;
    }

    @NonNull
    @Override
    public IngredientAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.ingredient_item , parent, false);


        return  new IngredientAdapter.MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientAdapter.MyViewHolder holder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        String In,M,Q;
        In=ingredient.getIngredient();
        M=ingredient.getMeasure();
        Q=ingredient.getQuantity().toString();
        holder.in.setText( M+" "+Q+" "+In);
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView in,m,q;

        public MyViewHolder(@NonNull View itemView) {
            super( itemView );
           in=itemView.findViewById(R.id.ingredient1);

        }
    }
}
