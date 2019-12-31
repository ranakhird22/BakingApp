package android.example.bakingapp;
import android.content.Intent;
import android.example.bakingapp.model.Step;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;

public class MasterList  extends AppCompatActivity {
    public static boolean mTwoPane;
    Step clickStep;

    Intent intent = getIntent();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        if(findViewById( R.id.liner_layout )!=null){


            mTwoPane=true;

            if(savedInstanceState == null) {



            }

        }else{
            mTwoPane=false;
        }

    }
}
