package inspire2connect.inspire2connect.aqi_cough;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.HashMap;

import inspire2connect.inspire2connect.R;

public class YoutubePlayVid extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener {
    YouTubePlayerView mYoutubePlayerView;
    RadioGroup radioGroup;
    TextView btnPlay;
    YouTubePlayer.OnInitializedListener mOnInitializedListener;
    TextView laughButton;
    String playID = "N0Gv96uDctM";
    private static final String TAG = "YoutubePlayVid";
    YouTubePlayer mYouTubePlayer;
    HashMap<Integer,String> mapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_youtube_play_vid);
        btnPlay =  findViewById(R.id.btnPlay);
        mYoutubePlayerView = (YouTubePlayerView) findViewById(R.id.youtubeView);
        laughButton =  findViewById(R.id.laughButton);
        radioGroup = findViewById(R.id.radiogroup);
        mapper = new HashMap<>();
        mapper.put(R.id.relCheck,"N0Gv96uDctM");
        mapper.put(R.id.diagnosingCheck,"faqhDcrWfHQ");
        mapper.put(R.id.preventCheck,"hAXnQgU4bsg");
        mapper.put(R.id.primsecCheck,"kHDS42fr17A");
        mapper.put(R.id.treatmentCheck,"2K1ekrZ6ej4");
        mYoutubePlayerView.initialize(YoutubeConfig.getYoutube_API_KEY(),this);

        //Relevance - N0Gv96uDctM
        //Treatment of Active TB - 2K1ekrZ6ej4
        //Preventing TB Transmission - hAXnQgU4bsg
        //Primary and Secondary TB - kHDS42fr17A
        //Diagnosing Active TB - faqhDcrWfHQ
        //TB and HIV ???? - S8s-g7KZR50

        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radioButton = group.findViewById(checkedId);
            }
        });
        btnPlay.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Log.d(TAG, "onClick: Initialising Youtube Player.");
                int selected_id = radioGroup.getCheckedRadioButtonId();
                playID =mapper.get(selected_id);
                if(mYouTubePlayer != null)
                    mYouTubePlayer.loadVideo(playID);
                Log.d("CHECK","HELLO2");
            }

        });

        laughButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent youtIntent = new Intent(YoutubePlayVid.this, MainScreening.class);
                startActivity(youtIntent);
            }

        });

    }

    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean wasRestored) {
        if(!wasRestored)
            mYouTubePlayer = youTubePlayer;
        Log.d(TAG, "onClick: Success Initialising.");
    }


    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
        Log.d(TAG, "onClick: Failed to Initialise.");
    }

}
