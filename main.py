from kivy.app import App
from kivy.lang import Builder
from kivy.uix.boxlayout import BoxLayout
from admob4kivy import AdmobManager,TestIDs

KV = '''
<AdTestUI>:
    orientation: 'vertical'
    spacing: dp(10)
    padding: dp(10)

    Button:
        text: "Show Banner (Top)"
        on_press: app.load_banner(True)

    Button:
        text: "Show Banner (Bottom)"
        on_press: app.load_banner(False)

    Button:
        text: "Hide Banner"
        on_press: app.hide_banner()

    Button:
        text: "Load Interstitial"
        on_press: app.load_interstitial()

    Button:
        text: "Show Interstitial"
        on_press: app.show_interstitial()

    Button:
        text: "Load Rewarded"
        on_press: app.load_rewarded()

    Button:
        text: "Show Rewarded"
        on_press: app.show_rewarded()
'''

class AdTestUI(BoxLayout):
    def __init__(self, **kwargs):
        super().__init__(**kwargs)
        

    

class AdmobKivyApp(App):
    def build(self):
        Builder.load_string(KV)
        self.admob = AdmobManager(callback=self.ad_event_callback)
        return AdTestUI()

    def load_banner(self, top=True):
        self.admob.load_banner(TestIDs.BANNER, top=top)

    def hide_banner(self):
        self.admob.hide_banner()

    def load_interstitial(self):
        self.admob.load_interstitial(TestIDs.INTERSTITIAL)

    def show_interstitial(self):
        self.admob.show_interstitial()

    def load_rewarded(self):
        self.admob.load_rewarded(TestIDs.REWARDED)

    def show_rewarded(self):
        self.admob.show_rewarded()

    def ad_event_callback(self, event, *args):
        print(f"[AdEvent in Kivy APP ] {event}: {args}")



if __name__ == "__main__":
    AdmobKivyApp().run()
