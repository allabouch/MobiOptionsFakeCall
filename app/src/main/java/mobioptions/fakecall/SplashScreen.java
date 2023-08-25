package mobioptions.fakecall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_splash_screen);

        ImageView logo = findViewById(R.id.splash_logo);

        Animation animation = AnimationUtils.loadAnimation(this, R.anim.splash_animation);
        logo.startAnimation(animation);

        // Delay for the splash screen
        new Handler().postDelayed(() -> {
            Intent intent = new Intent(SplashScreen.this, Choose_Category.class);
            startActivity(intent);
            finish();
        }, 3000); // 3 seconds
    }
}
