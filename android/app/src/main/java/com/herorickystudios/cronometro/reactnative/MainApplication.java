package com.herorickystudios.cronometro.reactnative;

import android.app.Application;
import android.content.Context;
import android.util.Log;

import com.facebook.react.PackageList;
import com.facebook.react.ReactApplication;
import com.facebook.react.ReactInstanceManager;
import com.facebook.react.ReactNativeHost;
import com.facebook.react.ReactPackage;
import com.facebook.react.config.ReactFeatureFlags;
import com.facebook.soloader.SoLoader;
import com.herorickystudios.cronometro.reactnative.newarchitecture.MainApplicationReactNativeHost;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import com.facebook.ads.*;

public class MainApplication extends Application implements ReactApplication {

    private final String TAG = MainApplication.class.getSimpleName();
    private InterstitialAd interstitialAd;
    private final Boolean testMode = false;
    private String TestString = "";

  private final ReactNativeHost mReactNativeHost =
      new ReactNativeHost(this) {
        @Override
        public boolean getUseDeveloperSupport() {
          return BuildConfig.DEBUG;
        }

        @Override
        protected List<ReactPackage> getPackages() {
          @SuppressWarnings("UnnecessaryLocalVariable")
          List<ReactPackage> packages = new PackageList(this).getPackages();
          // Packages that cannot be autolinked yet can be added manually here, for example:
          // packages.add(new MyReactNativePackage());
          return packages;
        }

        @Override
        protected String getJSMainModuleName() {
          return "index";
        }
      };

  private final ReactNativeHost mNewArchitectureNativeHost =
      new MainApplicationReactNativeHost(this);

  @Override
  public ReactNativeHost getReactNativeHost() {
    if (BuildConfig.IS_NEW_ARCHITECTURE_ENABLED) {
      return mNewArchitectureNativeHost;
    } else {
      return mReactNativeHost;
    }
  }

  @Override
  public void onCreate() {
    super.onCreate();
    // If you opted-in for the New Architecture, we enable the TurboModule system
    ReactFeatureFlags.useTurboModules = BuildConfig.IS_NEW_ARCHITECTURE_ENABLED;
    SoLoader.init(this, /* native exopackage */ false);
    initializeFlipper(this, getReactNativeHost().getReactInstanceManager());

      //Test Mode Verificador
      if(testMode == true){
          TestString = "IMG_16_9_APP_INSTALL#";
      }else if(testMode == false){
          TestString = "";
      }

      interstitialAd = new InterstitialAd(this, TestString + "643353743463488_643353980130131");

      InterstitialAdListener interstitialAdListener = new InterstitialAdListener() {
          @Override
          public void onInterstitialDisplayed(Ad ad) {
              // Interstitial ad displayed callback
              Log.e(TAG, "Interstitial ad displayed.");
          }

          @Override
          public void onInterstitialDismissed(Ad ad) {
              // Interstitial dismissed callback
              Log.e(TAG, "Interstitial ad dismissed.");
          }

          @Override
          public void onError(Ad ad, AdError adError) {
              // Ad error callback
              Log.e(TAG, "Interstitial ad failed to load: " + adError.getErrorMessage());
          }

          @Override
          public void onAdLoaded(Ad ad) {
              // Interstitial ad is loaded and ready to be displayed
              Log.d(TAG, "Interstitial ad is loaded and ready to be displayed!");
              // Show the ad
              interstitialAd.show();
          }

          @Override
          public void onAdClicked(Ad ad) {
              // Ad clicked callback
              Log.d(TAG, "Interstitial ad clicked!");
          }

          @Override
          public void onLoggingImpression(Ad ad) {
              // Ad impression logged callback
              Log.d(TAG, "Interstitial ad impression logged!");
          }
      };

      interstitialAd.loadAd(
              interstitialAd.buildLoadAdConfig()
                      .withAdListener(interstitialAdListener)
                      .build());

  }

  /**
   * Loads Flipper in React Native templates. Call this in the onCreate method with something like
   * initializeFlipper(this, getReactNativeHost().getReactInstanceManager());
   *
   * @param context
   * @param reactInstanceManager
   */
  private static void initializeFlipper(
      Context context, ReactInstanceManager reactInstanceManager) {
    if (BuildConfig.DEBUG) {
      try {
        /*
         We use reflection here to pick up the class that initializes Flipper,
        since Flipper library is not available in release mode
        */
        Class<?> aClass = Class.forName("com.herorickystudios.cronometro.reactnative.ReactNativeFlipper");
        aClass
            .getMethod("initializeFlipper", Context.class, ReactInstanceManager.class)
            .invoke(null, context, reactInstanceManager);
      } catch (ClassNotFoundException e) {
        e.printStackTrace();
      } catch (NoSuchMethodException e) {
        e.printStackTrace();
      } catch (IllegalAccessException e) {
        e.printStackTrace();
      } catch (InvocationTargetException e) {
        e.printStackTrace();
      }
    }
  }
}
