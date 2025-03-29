package saraclasses.class10th.objective_question;



















import android.content.Context;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.browser.customtabs.CustomTabsIntent;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.rewarded.RewardedAd;

public class customtab_manager {


    static InterstitialAd interstitialAd;
    static RewardedAd rewardedAd;

    public static void open_tab(Context context, String url) {

        interstitialAd = ads_manager.mInterstitialAd;


        Uri uri = Uri.parse(url);

        CustomTabsIntent.Builder intentebuilder = new CustomTabsIntent.Builder();
        intentebuilder.setToolbarColor(context.getColor(R.color.app_color));
        intentebuilder.setUrlBarHidingEnabled(true);
        intentebuilder.setDefaultShareMenuItemEnabled(false);
        intentebuilder.setInstantAppsEnabled(true);


        CustomTabsIntent customTabsIntent = intentebuilder.build();
        customTabsIntent.intent.setPackage("com.android.chrome");


        if (ads_manager.mInterstitialAd == null) {

            //Toast.makeText(context, "No ads For Custom Tab", Toast.LENGTH_SHORT).show();
            customTabsIntent.launchUrl(context, uri);
            ads_manager.intads_load(context);

        } else {

            ads_manager.show_intads(context);
            interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    ads_manager.intads_load(context);
                    customTabsIntent.launchUrl(context, uri);
                    super.onAdDismissedFullScreenContent();
                }

                @Override
                public void onAdFailedToShowFullScreenContent(@NonNull AdError adError) {

                    super.onAdFailedToShowFullScreenContent(adError);
                }

                @Override
                public void onAdImpression() {
                    super.onAdImpression();
                }

                @Override
                public void onAdShowedFullScreenContent() {
                    super.onAdShowedFullScreenContent();
                }
            });


        }


    }
}






