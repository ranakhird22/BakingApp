package android.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import android.content.Intent;
import android.example.bakingapp.adapter.StepsAdapter;
import android.example.bakingapp.model.Step;
import android.os.Bundle;
import android.widget.Toast;

import static android.example.bakingapp.MasterList.mTwoPane;


public class StepDetail extends AppCompatActivity {

     Step clickStep;
      String DESC;
       String  Video;
       String thumbnailURL;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate( savedInstanceState );
        setContentView( R.layout.activity_step_detail );


        StepFragment stepFragment=new StepFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();



        Intent intent = getIntent();
        if(mTwoPane ==false){

        if(intent.hasExtra( "step" )) {
            clickStep = (Step) getIntent().getSerializableExtra( "step" );
            DESC=clickStep.getDescription();
            Video=clickStep.getVideoURL();
            thumbnailURL=clickStep.getThumbnailURL();


            Bundle extra=new Bundle( );
            extra.putString( "desc",DESC );
            extra.putString( "video",Video );
            extra.putString( "thumbnailURL",thumbnailURL );


            if (DESC!=null){

                stepFragment.setArguments( extra );

                fragmentManager.beginTransaction()
                        .add( R.id.descriptionFragment1, stepFragment )
                        .commit();
            }


            if(Video!=""){
                VideoFragment videoFragment1=new VideoFragment();
                videoFragment1.setArguments( extra );
                fragmentManager.beginTransaction()
                        .add( R.id.ExoplayerFragment, videoFragment1 )
                        .commit();

            }
        }}


    }


}
