# AdMob4Kivy
## Author

**Sahil Pixel**  
📧 [GitHub](https://github.com/sahil-pixel)


AdMob4Kivy is a lightweight AdMob integration layer for Kivy Android apps using Pyjnius. It supports banner ads, interstitial ads, and rewarded ads with full Java-to-Python callback support.

## Features

- ✅ Banner Ads (Top or Bottom)
- ✅ Interstitial Ads
- ✅ Rewarded Ads with reward callback
- ✅ Java event listener to receive ad status in Python
- ✅ Simple API with auto-threading for UI safety

---

## 🔧 Buildozer Setup
Make a local copy of admob4kivy.py , java_code folder in your project 

Make sure your Java code (e.g., `AdmobManager.java`, `AdmobListener.java`) is inside `./java_code/org/kivy/admob4kivy/`.

Then edit your `buildozer.spec` as follows:

```ini
# Java source directory
android.add_src = ./java_code

# Required permissions
android.permissions = INTERNET,ACCESS_NETWORK_STATE

# AdMob App ID (Test ID shown below; replace with your own production ID)
android.meta_data = com.google.android.gms.ads.APPLICATION_ID=ca-app-pub-3940256099942544~3347511713

# Required AdMob + Firebase Ads SDK
android.gradle_dependencies = com.google.android.gms:play-services-ads:22.6.0,com.google.firebase:firebase-ads:21.4.0
```

You may also want to ensure:

```ini
requirements = python3,kivy,pyjnius,android
```

---

## Java Integration

- `AdmobManager.java`: Loads and shows ads using the Google Mobile Ads SDK.
- `AdmobListener.java`: Forwards Java AdMob events to Python using Pyjnius.

---

## Python Usage

### 1. Import and Initialize

```python
from admob4kivy import AdmobManager

admob = AdmobManager(callback=ad_event_callback)
```

### 2. Load and Show Ads

```python
# Banner
admob.load_banner(top=True)
admob.show_banner()
admob.hide_banner()

# Interstitial
admob.load_interstitial()
admob.show_interstitial()

# Rewarded
admob.load_rewarded()
admob.show_rewarded()
```

### 3. Listen for Ad Events

```python
def ad_event_callback(event, *args):
    print(f"[AdEvent] {event}: {args}")
```

#### Possible events:

- `ad_loaded`
- `ad_failed`
- `ad_opened`
- `ad_closed`
- `reward_earned`

---



## Google Test Ad IDs

Use these IDs for testing:

```python
class TestIDs:
    APP = "ca-app-pub-3940256099942544~3347511713"
    BANNER = "ca-app-pub-3940256099942544/6300978111"
    INTERSTITIAL = "ca-app-pub-3940256099942544/1033173712"
    REWARDED = "ca-app-pub-3940256099942544/5224354917"
```

---

## 📷 Screenshot

Here's what the app looks like on Android:

![Admob4Kivy screenshot](screenshot.png)


## Example Kivy App


```python
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

```



## Pay for Support

If you find this project helpful and want to support development, you can donate for support:

-  [![Support me on Ko-fi](https://img.shields.io/badge/Support%20me%20on-Ko--fi-%23FF5F5F.svg?style=flat&logo=ko-fi&logoColor=white)](https://ko-fi.com/sahilpixel)
  
- [![Donate with PayPal](https://img.shields.io/badge/Donate-PayPal-blue?style=flat&logo=paypal&logoColor=white)](https://paypal.me/SKSAHILIN?country.x=IN&locale.x=en_GB)


Thank you for your support!
---

## License

MIT License
