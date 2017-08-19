package moviesdb.popularmovies;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerView;

/**
 * Created by amokk on 7/15/2017.
 */

public class VideoPlayer extends AppCompatActivity {
    YouTubePlayerView youTubePlayerView;
    YouTubePlayerFragment youTubePlayerFragment;
    SharedPreferences preferences;
    String id, info;
    TextView info_details, Header;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.player);
        info_details = (TextView) findViewById(R.id.Info);
        preferences = getSharedPreferences("pref", MODE_PRIVATE);
        id = preferences.getString("video_key", null);
        info = preferences.getString("info", null);

        info_details = (TextView) findViewById(R.id.Info);
        info_details.setText(info);

        Header = (TextView) findViewById(R.id.header);
        SpannableString string = new SpannableString("Info");
        string.setSpan(new UnderlineSpan(), 0, string.length(), 0);
        Header.setText(string);

        youTubePlayerFragment = (YouTubePlayerFragment) getFragmentManager().findFragmentById(R.id.player);
        youTubePlayerFragment.initialize("AIzaSyDUtJbt21gbZsSDg5ZCrmZehgm37MRZiX4", new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.cueVideo(id);
            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {
                Toast.makeText(VideoPlayer.this, "failed to load video", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
