package android.example.bakingapp.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;


import android.example.bakingapp.R;
import android.example.bakingapp.StepClickListener;
import android.example.bakingapp.StepDetail;
import android.example.bakingapp.StepFragment;
import android.example.bakingapp.VideoFragment;

import android.example.bakingapp.model.Step;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.FragmentActivity;

import androidx.recyclerview.widget.RecyclerView;


import java.util.ArrayList;
import java.util.List;

import static android.example.bakingapp.MasterList.mTwoPane;

public class StepsAdapter extends RecyclerView.Adapter<StepsAdapter.MyViewHolder>  implements StepClickListener {



    private Context mContext;
    private List<Step> stepList=new ArrayList<>();
    private StepClickListener itemClickListener;

    public StepsAdapter(Context mContext, List<Step> stepList) {
        this.mContext = mContext;
        this.stepList = stepList;
    }

    @NonNull
    @Override
    public StepsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View view = LayoutInflater.from( parent.getContext() ).inflate( R.layout.steps_list , parent, false);


        return  new StepsAdapter.MyViewHolder( view );
    }

    @Override
    public void onBindViewHolder(@NonNull StepsAdapter.MyViewHolder holder, int position) {



        Step step=stepList.get(position);

        holder.id.setText(step.getId().toString());
        holder.desc.setText(step.getShortDescription());






    }

    @Override
    public int getItemCount() {
        return stepList.size();
    }

    @Override
    public void onItemClick( int pos) {

        Step clickedItem=stepList.get( pos );

        Intent intent=new Intent( mContext, StepDetail.class );

        intent.putExtra("step",clickedItem  );

        StepFragment stepFragment=new StepFragment();

        String d=clickedItem.getDescription();
        String v=clickedItem.getVideoURL();
        String thumbnailURL=clickedItem.getThumbnailURL();
        Bundle extra=new Bundle( );
        extra.putString( "desc",d );
        extra.putString( "video",v );
        extra.putString( "thumbnailURL",thumbnailURL );

        stepFragment.setArguments(extra);
        VideoFragment videoFragment=new VideoFragment();
        ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                .replace(R.id.descriptionFragment, stepFragment)
                .commit();
        videoFragment.setArguments(extra);
        ((FragmentActivity)mContext).getSupportFragmentManager().beginTransaction()
                .replace(R.id.descriptionVideo, videoFragment)
                .commit();

    }

    public class MyViewHolder extends RecyclerView.ViewHolder   {
        TextView id,desc;
        CardView stepCard;
        public MyViewHolder(@NonNull View itemView) {
            super( itemView );
            id=itemView.findViewById( R.id.stepId );
            desc=itemView.findViewById( R.id.shortDescription);
            stepCard=itemView.findViewById( R.id.StepCardView );


            stepCard.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    int position=getAdapterPosition();
                    if(position!=RecyclerView.NO_POSITION) {

                       if (mTwoPane==false){
                           Step clickedItem=stepList.get( position );
                          Intent intent=new Intent( mContext, StepDetail.class );

                         intent.putExtra("step",clickedItem  );
                           intent.addFlags( android.content.Intent.FLAG_ACTIVITY_NEW_TASK );
                           mContext.startActivity(intent);

                        }else{
                           onItemClick(position);

                       }



                    }
                }
                /*public void StepClickListener(ItemClickListener ic)
                {
                    this.itemClickListener=ic;

                }*/
            } );


        }


    }
}
