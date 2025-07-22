from jnius import autoclass, PythonJavaClass, java_method
from android.runnable import run_on_ui_thread

PythonActivity = autoclass("org.kivy.android.PythonActivity")

class AdmobListener(PythonJavaClass):
    __javainterfaces__ = ["org/kivy/admob4kivy/AdmobListener"]
    __javacontext__ = "app"

    def __init__(self, callback):
        super().__init__()
        self.callback = callback

    def _dispatch(self, event, *args):

        if self.callback:
            self.callback(event, *args)


    @java_method("(Ljava/lang/String;)V")
    def onAdLoaded(self, ad_type):
        print(f"[Ad Loaded] {ad_type}")
        self._dispatch("ad_loaded", ad_type)

    @java_method("(Ljava/lang/String;Ljava/lang/String;)V")
    def onAdFailed(self, ad_type, error):
        print(f"[Ad Failed] {ad_type}: {error}")
        self._dispatch("ad_failed", ad_type, error)

    @java_method("(Ljava/lang/String;)V")
    def onAdOpened(self, ad_type):
        print(f"[Ad Opened] {ad_type}")
        self._dispatch("ad_opened", ad_type)


    @java_method("(Ljava/lang/String;)V")
    def onAdClosed(self, ad_type):
        print(f"[Ad Closed] {ad_type}")
        self._dispatch("ad_closed", ad_type)

    @java_method("(Ljava/lang/String;I)V")
    def onUserEarnedReward(self, reward_type, amount):
        print(f"[Reward Earned] {reward_type}: {amount}")
        self._dispatch("reward_earned", reward_type, amount)

class TestIDs:
    # Google-provided test ad unit IDs
    APP = "ca-app-pub-3940256099942544~3347511713"
    BANNER = "ca-app-pub-3940256099942544/6300978111"
    INTERSTITIAL = "ca-app-pub-3940256099942544/1033173712"
    REWARDED = "ca-app-pub-3940256099942544/5224354917"



class AdmobManager:

    def __init__(self,callback=None):
        activity = PythonActivity.mActivity
        JavaManager = autoclass("org.kivy.admob4kivy.AdmobManager")
        
        
        
        if callback:
            self.listener = AdmobListener(callback)  # Keep reference alive
        else:
            self.listener = AdmobListener() # Keep reference alive

        self.manager = JavaManager(activity, self.listener)

    @run_on_ui_thread
    def load_banner(self, ad_unit, top=False):
        self.manager.loadBanner(ad_unit, top)

    @run_on_ui_thread
    def show_banner(self):
        self.manager.showBanner()

    @run_on_ui_thread
    def hide_banner(self):
        self.manager.hideBanner()

    @run_on_ui_thread
    def load_interstitial(self, ad_unit):
        self.manager.loadInterstitial(ad_unit)

    @run_on_ui_thread
    def show_interstitial(self):
        self.manager.showInterstitial()

    @run_on_ui_thread
    def load_rewarded(self, ad_unit):
        self.manager.loadRewarded(ad_unit)

    @run_on_ui_thread
    def show_rewarded(self):
        self.manager.showRewarded()
