package mobioptions.fakecall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ProgressBar;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.sdk.AppLovinSdk;

public class Downloading extends AppCompatActivity implements MaxAdListener {

    private TextView textViewStatus;
    private ProgressBar progressBar;
    private MaxInterstitialAd interstitialAd;
    private String[] messages = {
            "Please Wait, we are preparing characters.",
            "Please one moment...",
            "Downloading characters...",
            "Almost done...",
            "Loading more and more and more..."
    };
    private int currentMessageIndex = 0;
    private int progressStatus = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_downloading);

        AppLovinSdk.getInstance(this).setMediationProvider("max");
        AppLovinSdk.initializeSdk(this);

        interstitialAd = new MaxInterstitialAd("8978dcd968620b9d", this);
        interstitialAd.setListener(this);
        interstitialAd.loadAd();

        textViewStatus = findViewById(R.id.textViewStatus);
        progressBar = findViewById(R.id.progressBar);

        startDownloadingCharacters();
    }

    private void startDownloadingCharacters() {
        final Handler handler = new Handler();
        final int maxProgress = 100; // Define max progress value
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                textViewStatus.setText(messages[currentMessageIndex]);
                currentMessageIndex = (currentMessageIndex + 1) % messages.length;
                progressStatus += 20;
                progressBar.setProgress(progressStatus);

                if (progressStatus < maxProgress) {
                    handler.postDelayed(this, 2000);
                } else {
                    startCharactersReadyActivity();
                }
            }
        }, 2000);
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

    // Implement MaxAdListener methods
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
