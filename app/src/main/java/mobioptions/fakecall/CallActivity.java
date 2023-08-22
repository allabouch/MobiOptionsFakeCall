package mobioptions.fakecall;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.Chronometer;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class CallActivity extends AppCompatActivity {

    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_call);

        ImageView callImage = findViewById(R.id.callImage);

// Create a slide-in animation
        TranslateAnimation slideInAnimation = new TranslateAnimation(
                TranslateAnimation.RELATIVE_TO_SELF, 1.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f,
                TranslateAnimation.RELATIVE_TO_SELF, 0.0f
        );
        slideInAnimation.setDuration(500); // Duration in milliseconds
        slideInAnimation.setFillAfter(true);

// Start the animation
        callImage.startAnimation(slideInAnimation);


        ImageView callerImage = findViewById(R.id.callImage);
        TextView callerName = findViewById(R.id.callName);
        TextView callerNumber = findViewById(R.id.callerNumber);
        Chronometer chronometer = findViewById(R.id.chronometer);
        Button endCallButton = findViewById(R.id.endCallButton);

        int imageResource = getIntent().getIntExtra("imageResource", 0);
        String name = getIntent().getStringExtra("name");
        String number = getIntent().getStringExtra("number");
        int audioResource = getIntent().getIntExtra("audioResource", 0);

        callerImage.setImageResource(imageResource);
        callerName.setText(name);
        callerNumber.setText(number);
        chronometer.start();

        if (audioResource != 0) {
            mediaPlayer = MediaPlayer.create(this, audioResource);
            mediaPlayer.start();
        } else {
            Log.e("CallActivity", "Invalid audio resource ID");
        }

        endCallButton.setOnClickListener(v -> {
            if (mediaPlayer != null) {
                mediaPlayer.stop();
            }
            MediaPlayer endCallSound = MediaPlayer.create(CallActivity.this, R.raw.accept_reject);
            endCallSound.start();
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null) {
            mediaPlayer.release();
        }
    }
}
