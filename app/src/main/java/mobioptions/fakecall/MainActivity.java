package mobioptions.fakecall;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.sdk.AppLovinSdk;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity implements MaxAdListener {

    private MaxInterstitialAd interstitialAd;
    private Character[] characters;
    private Character selectedCharacter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppLovinSdk.getInstance(this).setMediationProvider("max");
        AppLovinSdk.initializeSdk(this);

        interstitialAd = new MaxInterstitialAd("8978dcd968620b9d", this);
        interstitialAd.setListener(this);
        interstitialAd.loadAd();

        characters = new Character[]{
                new Character(R.drawable.character, "John Smith", "123-456-7890", R.raw.call_1),
                new Character(R.drawable.character, "Alice Johnson", "234-567-8901", R.raw.call_2),
                new Character(R.drawable.character, "James Brown", "345-678-9012", R.raw.samsung),
                new Character(R.drawable.character, "Emily Davis", "456-789-0123", R.raw.samsung),
                new Character(R.drawable.character, "Michael Wilson", "567-890-1234", R.raw.samsung),
                new Character(R.drawable.character, "Sarah Taylor", "678-901-2345", R.raw.samsung),
                new Character(R.drawable.character, "David Moore", "789-012-3456", R.raw.samsung),
                new Character(R.drawable.character, "Jessica Thompson", "890-123-4567", R.raw.samsung),
                new Character(R.drawable.character, "Daniel White", "901-234-5678", R.raw.samsung),
                new Character(R.drawable.character, "Sophia Lewis", "012-345-6789", R.raw.samsung),
        };

        CharacterAdapter adapter = new CharacterAdapter(this, characters);
        ListView listView = findViewById(R.id.listView);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            selectedCharacter = characters[position];
            if (interstitialAd.isReady()) {
                interstitialAd.showAd();
            } else {
                startFakeCallActivity();
            }
        });
    }

    private void startFakeCallActivity() {
        Intent intent = new Intent(MainActivity.this, FakeCallActivity.class);
        intent.putExtra("imageResource", selectedCharacter.imageResource);
        intent.putExtra("name", selectedCharacter.name);
        intent.putExtra("number", selectedCharacter.number);
        intent.putExtra("audioResource", selectedCharacter.audioResource);
        startActivity(intent);
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
        startFakeCallActivity();
        interstitialAd.loadAd(); // Load another ad
    }

    @Override
    public void onAdClicked(final MaxAd maxAd) {}

    public static class Character {
        int imageResource;
        String name;
        String number;
        int audioResource;

        Character(int imageResource, String name, String number, int audioResource) {
            this.imageResource = imageResource;
            this.name = name;
            this.number = number;
            this.audioResource = audioResource;
        }
    }
}
