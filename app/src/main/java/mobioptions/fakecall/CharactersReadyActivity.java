package mobioptions.fakecall;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class CharactersReadyActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_characters_ready);

        TextView textViewMessage = findViewById(R.id.textViewMessage);
        ImageView imageViewCheckMark = findViewById(R.id.imageViewCheckMark);
        Button buttonGetStarted = findViewById(R.id.buttonGetStarted);

        // Load animation for the checkmark
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.checkmark_animation);
        imageViewCheckMark.startAnimation(animation);

        // Set message
        textViewMessage.setText("Your characters are ready!\nYou can get started.");

        // Handle button click
        buttonGetStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(CharactersReadyActivity.this, MainActivity.class); // Change NextActivity to your next activity
                startActivity(intent);
                finish();
            }
        });
    }
}
