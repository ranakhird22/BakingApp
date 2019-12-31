package android.example.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveVideoTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;





public class VideoFragment  extends Fragment {
    private static final String PLAYER_POSITION ="" ;
    private SimpleExoPlayer exoPlayer;
    private  String Video;
    private long startPosition;
    private long playerPosition;
    private static final String KEY_POSITION = "position";

    private long mPlaybackPosition;


    private int mCurrentWindow;

    private boolean mPlayWhenReady;


    private long playbackPosition;





    public static final String STATE_PLAYBACK_POSITION = "state_playback_position";
    public static final String STATE_CURRENT_WINDOW = "state_current_window";
    public static final String STATE_PLAY_WHEN_READY = "state_play_when_ready";



    private SimpleExoPlayerView mPlayerView;
    private TextView novideo;
    private String step_thumbnailURL;
    private ImageView img;


    public VideoFragment() {
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putLong( STATE_PLAYBACK_POSITION,mPlaybackPosition );
        outState.putInt( STATE_CURRENT_WINDOW,mCurrentWindow );
        outState.putBoolean( STATE_PLAY_WHEN_READY,mPlayWhenReady );


    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {


                initializePlayer();

            }


    }

    @Override
    public void onResume() {
        super.onResume();

        if ((Util.SDK_INT <= 23 || mPlayerView == null)) {


                initializePlayer();

            }


        }


    @Override
    public void onPause() {
        super.onPause();



          if (Util.SDK_INT <= 23) {
                releasePlayer();
            }
        }
    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate( R.layout.video_fragment, container, false );
        mPlayerView=rootView.findViewById( R.id.playerView2 );
        novideo=rootView.findViewById( R.id.novideo );
        img=rootView.findViewById( R.id.step_thumbnailURL );


        if (savedInstanceState != null) {

            mPlaybackPosition = savedInstanceState.getLong(STATE_PLAYBACK_POSITION);
            mCurrentWindow = savedInstanceState.getInt(STATE_CURRENT_WINDOW);
            mPlayWhenReady = savedInstanceState.getBoolean(STATE_PLAY_WHEN_READY);

        } else {

            mCurrentWindow = C.INDEX_UNSET;

            mPlaybackPosition = C.TIME_UNSET;
            mPlayWhenReady = true;
        }



        if (getArguments()!=null){
            Video=getArguments().getString( "video" );
            step_thumbnailURL=getArguments().getString( "thumbnailURL" );
        }
        if(Video!=""){

        }
        else if(step_thumbnailURL !=""){
            Glide.with(getContext())
                    .load(step_thumbnailURL)
                    .into(img);
        }
        else{
            novideo.setText( "no video " );
        }

        return rootView;
    }





    private void initializePlayer() {

        if (getArguments()!=null){
            Video=getArguments().getString( "video" );
        }
        if(Video!=""){
            try{

               if (exoPlayer==null) {

                   BandwidthMeter bandwidthMeter=new DefaultBandwidthMeter(  );

                   TrackSelector trackSelector=new DefaultTrackSelector(new AdaptiveVideoTrackSelection.Factory( bandwidthMeter ) );


                   LoadControl loadControl = new DefaultLoadControl();

                   exoPlayer = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector, loadControl);

                   mPlayerView.setPlayer(exoPlayer  );
                   exoPlayer.setPlayWhenReady( mPlayWhenReady );

               }


                Uri uri = Uri.parse(Video);



                String userAgent = Util.getUserAgent(getActivity(), "MediaPlayerApp");
                MediaSource mediaPlayer = new ExtractorMediaSource(uri, new DefaultDataSourceFactory(getContext(), userAgent), new DefaultExtractorsFactory()
                        , null, null);

                boolean haveStartPosition = mCurrentWindow != C.INDEX_UNSET;
                if (haveStartPosition) {
                    exoPlayer.seekTo(mCurrentWindow, mPlaybackPosition);

                }
                 exoPlayer.prepare(mediaPlayer,!haveStartPosition ,false );




            } catch (Exception e) {
                e.printStackTrace();
                Toast.makeText(getActivity(),e.toString() , Toast.LENGTH_SHORT).show();
            }
        }
        else{
            novideo.setText( "no video " );
        }


    }





     private void releasePlayer () {
         updateCurrentPosition();
         exoPlayer.stop();

         exoPlayer.release();
         exoPlayer = null;


     }
    private void updateCurrentPosition() {
        if (exoPlayer != null) {
            mPlaybackPosition = exoPlayer.getCurrentPosition();
             mCurrentWindow = exoPlayer.getCurrentWindowIndex();
            mPlayWhenReady = exoPlayer.getPlayWhenReady();
        }
    }






 }
