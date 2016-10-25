
Crear applicaciòn  en Facebook developers 

Generar Key Hashes en debug :

MacOSx

keytool -exportcert -alias androiddebugkey -keystore ~/.android/debug.keystore | openssl sha1 -binary | openssl base64

clave :  android

Key Hashes : 

aXXXXXXXXXXXXXXXXXXXXXXXXXX=



´´´
@Override
public void onCreate() {
    super.onCreate();
    FacebookSdk.sdkInitialize(getApplicationContext());
    AppEventsLogger.activateApp(this);
}
´´´

Configuraciòn Android

Referencias :

https://developers.facebook.com/docs/android/
https://developers.facebook.com/docs/facebook-login/android
https://github.com/facebook/facebook-android-sdk

