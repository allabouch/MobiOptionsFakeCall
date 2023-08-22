package mobioptions.fakecall;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class FakeCallActivity extends AppCompatActivity {

    private Ringtone ringtone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fake_call);

        int imageResource = getIntent().getIntExtra("imageResource", 0);
        String name = getIntent().getStringExtra("name");
        String number = getIntent().getStringExtra("number");

        ImageView callerImage = findViewById(R.id.callerImage);
        TextView callerName = findViewById(R.id.callerName);
        TextView callerNumber = findViewById(R.id.callerNumber);
        Button rejectButton = findViewById(R.id.rejectButton);
        Button acceptButton = findViewById(R.id.acceptButton);

        callerImage.setImageResource(imageResource);
        callerName.setText(name);
        callerNumber.setText(number);

        ringtone = RingtoneManager.getRingtone(this, RingtoneManager.getDefaultUri(RingtoneManager.TYPE_RINGTONE));
        ringtone.play();

        rejectButton.setOnClickListener(v -> {
            if (ringtone != null) {
                ringtone.stop();
            }
            MediaPlayer mediaPlayer = MediaPlayer.create(FakeCallActivity.this, R.raw.accept_reject);
            mediaPlayer.start();
            finish();
        });

        acceptButton.setOnClickListener(v -> {
            if (ringtone != null) {
                ringtone.stop();
            }
            MediaPlayer mediaPlayer = MediaPlayer.create(FakeCallActivity.this, R.raw.accept_reject);
            mediaPlayer.start();

            int audioResource = getAudioResourceForCharacter(name);
            Intent callIntent = new Intent(FakeCallActivity.this, CallActivity.class);
            callIntent.putExtra("imageResource", imageResource);
            callIntent.putExtra("name", name);
            callIntent.putExtra("number", number);
            callIntent.putExtra("audioResource", audioResource);
            startActivity(callIntent);
            finish();
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (ringtone != null) {
            ringtone.stop();
        }
    }

    private int getAudioResourceForCharacter(String character) {
        switch (character) {
            case "John Smith":
                return R.raw.call_1;
            case "Alice Johnson":
                return R.raw.call_2;
            case "James Brown":
                return R.raw.call_3;
            case "Emily Davis":
                return R.raw.call_4;
            case "Michael Wilson":
                return R.raw.call_5;
            case "Sarah Taylor":
                return R.raw.call_6;
            case "David Moore":
                return R.raw.call_4;
            case "Jessica Thompson":
                return R.raw.call_3;
            case "Daniel White":
                return R.raw.call_4;
            case "Sophia Lewis":
                return R.raw.call_4;
            // Add more cases for other characters
            default:
                return 0;
        }
    }
}
