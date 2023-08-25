package mobioptions.fakecall;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;

import java.util.concurrent.TimeUnit;

public class Choose_Category extends AppCompatActivity implements MaxAdListener {

    private MaxInterstitialAd interstitialAd;
    private int retryAttempt;
    private MaxNativeAdLoader nativeAdLoader;
    private MaxAd nativeAd;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_choose_category);

        AppLovinSdk.getInstance( this ).setMediationProvider( "max" );
        AppLovinSdk.initializeSdk( this, new AppLovinSdk.SdkInitializationListener() {
            @Override
            public void onSdkInitialized(final AppLovinSdkConfiguration configuration)
            {
                // AppLovin SDK is initialized, start loading ads
            }
        } );

        FrameLayout nativeAdContainer = findViewById( R.id.native_ad_layout );

        String adUnitId = getString(R.string.ad_unit_id);
        nativeAdLoader = new MaxNativeAdLoader(adUnitId, this);

        nativeAdLoader.setNativeAdListener( new MaxNativeAdListener()
        {
            @Override
            public void onNativeAdLoaded(final MaxNativeAdView nativeAdView, final MaxAd ad)
            {
                // Clean up any pre-existing native ad to prevent memory leaks.
                if ( nativeAd != null )
                {
                    nativeAdLoader.destroy( nativeAd );
                }

                // Save ad for cleanup.
                nativeAd = ad;

                // Add ad view to view.
                nativeAdContainer.removeAllViews();
                nativeAdContainer.addView( nativeAdView );
            }

            @Override
            public void onNativeAdLoadFailed(final String adUnitId, final MaxError error)
            {
                // We recommend retrying with exponentially higher delays up to a maximum delay
            }

            @Override
            public void onNativeAdClicked(final MaxAd ad)
            {
                // Optional click callback
            }
        } );

        nativeAdLoader.loadAd();

        AppLovinSdk.getInstance(this).setMediationProvider("max");
        AppLovinSdk.initializeSdk(this);

        String interstitialAdUnitId = getString(R.string.interstitial_ad_unit_id);
        interstitialAd = new MaxInterstitialAd(interstitialAdUnitId, this);

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
                String url = "https://play.google.com/store/apps/developer?id=YourDeveloperName"; // Replace this "YourDeveloperName" By your Dev Name Or You can replace link by any link
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                startActivity(intent);
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
