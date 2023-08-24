package mobioptions.fakecall;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;

import java.util.concurrent.TimeUnit;

public class Choose_Category extends AppCompatActivity implements MaxAdListener {

    private MaxInterstitialAd interstitialAd;
    private int retryAttempt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_category);

        AppLovinSdk.getInstance(this).setMediationProvider("max");
        AppLovinSdk.initializeSdk(this);

        interstitialAd = new MaxInterstitialAd("8978dcd968620b9d", this);
        interstitialAd.setListener(this);

        // Load the first ad
        interstitialAd.loadAd();

        Button btnCategory1 = findViewById(R.id.btn_category_1);
        Button btnCategory2 = findViewById(R.id.btn_category_2);

        btnCategory1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd.isReady()) {
                    interstitialAd.showAd();
                }
                // Wait until ad is closed to call chooseCategory
            }
        });

        btnCategory2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (interstitialAd.isReady()) {
                    interstitialAd.showAd();
                }
                // Wait until ad is closed to call chooseCategory
            }
        });

        // Add more button click listeners for more categories
    }

    private void chooseCategory(String category) {
        Intent intent = new Intent(Choose_Category.this, Downloading.class);
        intent.putExtra("category", category);
        startActivity(intent);
    }

    // ... other listener methods ...

    @Override
    public void onAdLoaded(MaxAd maxAd) {

    }

    @Override
    public void onAdDisplayed(MaxAd maxAd) {

    }

    @Override
    public void onAdHidden(final MaxAd maxAd) {
        // Interstitial ad is hidden. Pre-load the next ad
        interstitialAd.loadAd();

        // Now that the ad is closed, call chooseCategory with the chosen category
        String category = "Category 1"; // or "Category 2" depending on the button clicked
        chooseCategory(category);
    }

    @Override
    public void onAdClicked(MaxAd maxAd) {

    }

    @Override
    public void onAdLoadFailed(String s, MaxError maxError) {

    }

    @Override
    public void onAdDisplayFailed(MaxAd maxAd, MaxError maxError) {

    }
}
