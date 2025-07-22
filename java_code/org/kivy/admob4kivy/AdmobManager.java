package org.kivy.admob4kivy;

import android.app.Activity;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.view.Gravity;

import com.google.android.gms.ads.*;
import com.google.android.gms.ads.interstitial.InterstitialAd;
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback;
import com.google.android.gms.ads.rewarded.*;

public class AdmobManager {
    private Activity activity;
    private AdmobListener listener;
    private AdView banner;
    private InterstitialAd interstitialAd;
    private RewardedAd rewardedAd;

    public AdmobManager(Activity activity, AdmobListener listener) {
        this.activity = activity;
        this.listener = listener;
        MobileAds.initialize(activity);
    }

    public void loadBanner(String adUnit, boolean top) {
        activity.runOnUiThread(() -> {
            if (banner != null) {
                ((FrameLayout) banner.getParent()).removeView(banner);
                banner.destroy();
            }

            banner = new AdView(activity);
            banner.setAdUnitId(adUnit);
            banner.setAdSize(AdSize.BANNER);

            AdRequest adRequest = new AdRequest.Builder().build();
            banner.setAdListener(new AdListener() {
                @Override
                public void onAdLoaded() {
                    listener.onAdLoaded("banner");
                }

                @Override
                public void onAdFailedToLoad(LoadAdError error) {
                    listener.onAdFailed("banner", error.toString());
                }

                @Override
                public void onAdOpened() {
                    listener.onAdOpened("banner");
                }

                @Override
                public void onAdClosed() {
                    listener.onAdClosed("banner");
                }
            });

            FrameLayout layout = (FrameLayout) activity.findViewById(android.R.id.content);
            FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                    FrameLayout.LayoutParams.WRAP_CONTENT,
                    FrameLayout.LayoutParams.WRAP_CONTENT
            );
            params.gravity = top ? Gravity.TOP | Gravity.CENTER_HORIZONTAL : Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL;

            layout.addView(banner, params);
            banner.loadAd(adRequest);
        });
    }

    public void hideBanner() {
        activity.runOnUiThread(() -> {
            if (banner != null) {
                ((FrameLayout) banner.getParent()).removeView(banner);
                banner.destroy();
                banner = null;
            }
        });
    }

    public void showBanner() {
        activity.runOnUiThread(() -> {
            if (banner != null) {
                banner.setVisibility(AdView.VISIBLE);
            }
        });
    }

    public void loadInterstitial(String adUnit) {
        AdRequest adRequest = new AdRequest.Builder().build();
        InterstitialAd.load(activity, adUnit, adRequest,
            new InterstitialAdLoadCallback() {
                @Override
                public void onAdLoaded(InterstitialAd ad) {
                    interstitialAd = ad;
                    listener.onAdLoaded("interstitial");

                    interstitialAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdShowedFullScreenContent() {
                            listener.onAdOpened("interstitial");
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            listener.onAdClosed("interstitial");
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(LoadAdError error) {
                    interstitialAd = null;
                    listener.onAdFailed("interstitial", error.toString());
                }
            }
        );
    }

    public void showInterstitial() {
        activity.runOnUiThread(() -> {
            if (interstitialAd != null) {
                interstitialAd.show(activity);
            }
        });
    }

    public void loadRewarded(String adUnit) {
        AdRequest adRequest = new AdRequest.Builder().build();
        RewardedAd.load(activity, adUnit, adRequest,
            new RewardedAdLoadCallback() {
                @Override
                public void onAdLoaded(RewardedAd ad) {
                    rewardedAd = ad;
                    listener.onAdLoaded("rewarded");

                    rewardedAd.setFullScreenContentCallback(new FullScreenContentCallback() {
                        @Override
                        public void onAdShowedFullScreenContent() {
                            listener.onAdOpened("rewarded");
                        }

                        @Override
                        public void onAdDismissedFullScreenContent() {
                            listener.onAdClosed("rewarded");
                        }
                    });
                }

                @Override
                public void onAdFailedToLoad(LoadAdError error) {
                    rewardedAd = null;
                    listener.onAdFailed("rewarded", error.toString());
                }
            }
        );
    }

    public void showRewarded() {
        activity.runOnUiThread(() -> {
            if (rewardedAd != null) {
                rewardedAd.show(activity, rewardItem -> {
                    listener.onUserEarnedReward(rewardItem.getType(), rewardItem.getAmount());
                });
            }
        });
    }
}
