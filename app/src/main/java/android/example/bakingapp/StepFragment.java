package android.example.bakingapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.TextView;

import androidx.fragment.app.Fragment;



public class StepFragment extends Fragment {
    private  String DESC;
    public StepFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {



        View rootView = inflater.inflate( R.layout.description_fragment, container, false );



       final TextView desc = rootView.findViewById( R.id.description);
       if (getArguments()!=null){
           DESC=getArguments().getString( "desc" );
       }
        desc.setText( DESC );


        return rootView;
    }
}
