package org.kivy.admob4kivy;

public interface AdmobListener {
    void onAdLoaded(String adType);
    void onAdFailed(String adType, String error);
    void onAdOpened(String adType);
    void onAdClosed(String adType);
    void onUserEarnedReward(String rewardType, int amount);
}
