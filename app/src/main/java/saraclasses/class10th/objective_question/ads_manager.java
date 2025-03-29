package saraclasses.class10th.objective_question;


















import android.app.Activity;
import android.content.Context;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;

import com.google.android.gms.ads.AdError;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdSize;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.FullScreenContentCallback;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.RewardedAd;

public class ads_manager {

    static InterstitialAd mInterstitialAd;
    static RewardedAd rewardedAd;
    static String IntAds = "true";
    static String VideoAds = "true";

    public static void loadbannerads(LinearLayout layout, Context context) {

        AdView adView = new AdView(context);
        adView.setAdSize(AdSize.BANNER);
        adView.setAdUnitId("ca-app-pub-6013617437235854/9708308944");
        layout.addView(adView);

        AdRequest adRequest = new AdRequest.Builder().build();
        adView.loadAd(adRequest);


    }


    public static void intads_load(Context context) {

        if (mInterstitialAd == null) {

            if (IntAds.equals("true")) {

                IntAds = "false";

                AdRequest adRequest = new AdRequest.Builder().build();

                InterstitialAd.load(context, "ca-app-pub-6013617437235854/7967119348", adRequest,
                        new InterstitialAdLoadCallback() {
                            @Override
                            public void onAdLoaded(@NonNull InterstitialAd interstitialAd) {
                                // The mInterstitialAd reference will be null until
                                // an ad is loaded.
                                mInterstitialAd = interstitialAd;
                                IntAds = "true";
                                //Toast.makeText(context, "Ints Ads Load", Toast.LENGTH_SHORT).show();

                            }

                            @Override
                            public void onAdFailedToLoad(@NonNull LoadAdError loadAdError) {
                                // Handle the error
                                // Toast.makeText(context, "Ints Ads Failed To Load", Toast.LENGTH_SHORT).show();

                                IntAds = "true";

                                mInterstitialAd = null;
                            }
                        });
            }
        }


    }


    public static void show_intads(Context context) {

        if (mInterstitialAd == null) {

            intads_load(context);

        } else {

            mInterstitialAd.show((Activity) context);
            mInterstitialAd = null;
        }

    }

    public static void show_intads_with_callback(Context context) {

        if (mInterstitialAd == null) {

            intads_load(context);

        } else {

            mInterstitialAd.show((Activity) context);

            mInterstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                @Override
                public void onAdClicked() {
                    super.onAdClicked();
                }

                @Override
                public void onAdDismissedFullScreenContent() {
                    mInterstitialAd = null;
                    intads_load(context);
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

