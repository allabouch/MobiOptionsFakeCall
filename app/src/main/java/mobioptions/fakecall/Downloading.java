package mobioptions.fakecall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.sdk.AppLovinSdk;

import java.util.Random;

public class Downloading extends AppCompatActivity implements MaxAdListener {

    private TextView textViewStatus;
    private TextView textViewPercentage;
    private ProgressBar progressBar;
    private MaxInterstitialAd interstitialAd;
    private String[] messages = {
            "Please Wait, we are preparing characters.",
            "Please one moment...",
            "Downloading characters...",
            "Almost done...",
            "Loading more and more and more...",
            "Initializing characters... Please wait.",
            "Fetching latest updates... Hold on.",
            "Almost there! Just a few more seconds...",
            "Preparing the ultimate experience for you...",
            "Sit back and relax, we're setting things up..."

    };
    private int currentMessageIndex = 3;
    private int progressStatus = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_downloading);

        AppLovinSdk.getInstance(this).setMediationProvider("max");
        AppLovinSdk.initializeSdk(this);

        String interstitialAdUnitId = getString(R.string.interstitial_ad_unit_id);
        interstitialAd = new MaxInterstitialAd(interstitialAdUnitId, this);

        interstitialAd.setListener(this);
        interstitialAd.loadAd();

        textViewStatus = findViewById(R.id.textViewStatus);
        textViewPercentage = findViewById(R.id.textViewPercentage);
        progressBar = findViewById(R.id.progressBar);

        startDownloadingCharacters();
    }

    private void startDownloadingCharacters() {
        final Handler handler = new Handler();
        final int maxProgress = 100;
        final Random random = new Random();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (progressStatus % 1 == 0) { // Change message every 20% increment
                    currentMessageIndex = random.nextInt(messages.length);
                    textViewStatus.setText(messages[currentMessageIndex]);
                }
                progressStatus += 1;
                progressBar.setProgress(progressStatus);
                textViewPercentage.setText(progressStatus + "%");

                if (progressStatus < maxProgress) {
                    handler.postDelayed(this, 10);
                } else {
                    startCharactersReadyActivity();
                }
            }
        }, 10);
    }


    private void startCharactersReadyActivity() {
        if (interstitialAd.isReady()) {
            interstitialAd.showAd();
        } else {
            navigateToCharactersReadyActivity();
        }
    }

    private void navigateToCharactersReadyActivity() {
        Intent intent = new Intent(Downloading.this, CharactersReadyActivity.class);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAdLoaded(final MaxAd maxAd) {}

    @Override
    public void onAdLoadFailed(final String adUnitId, final MaxError error) {}

    @Override
    public void onAdDisplayFailed(final MaxAd maxAd, final MaxError error) {}

    @Override
    public void onAdDisplayed(final MaxAd maxAd) {}

    @Override
    public void onAdHidden(final MaxAd maxAd) {
        navigateToCharactersReadyActivity();
    }

    @Override
    public void onAdClicked(final MaxAd maxAd) {}
}
