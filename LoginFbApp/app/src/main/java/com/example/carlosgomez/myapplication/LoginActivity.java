package com.example.carlosgomez.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class LoginActivity extends AppCompatActivity {

    private static final String TAG = "LoginActivity";
    private LoginButton loginButton;
    private CallbackManager callbackManager;
    private TextView tviUsername;
    private View rlayUserInfo;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        app();
    }

    private void app() {
        ui();
        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList("email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {

                //goMainScreen();
                getUserInfo();
            }

            @Override
            public void onCancel() {
                Toast.makeText(getApplicationContext(), R.string.cancel_login, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(FacebookException error) {
                Toast.makeText(getApplicationContext(), R.string.error_login, Toast.LENGTH_SHORT).show();
            }
        });

        rlayUserInfo.setVisibility(View.GONE);
    }

    private void ui() {
        loginButton = (LoginButton) findViewById(R.id.loginButton);
        tviUsername = (TextView) findViewById(R.id.tviUsername);
        rlayUserInfo = findViewById(R.id.rlayUserInfo);
    }

    private void getUserInfo() {

        GraphRequest graphRequest= GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {

                JSONObject json = response.getJSONObject();
                try {
                    if(json != null)
                    {
                        Log.v(TAG, "json " + json.toString() + "\n" + json.getString("id"));
                        String email=json.getString("email");
                        String fbId=json.getString("id");
                        String firstName= json.getString("first_name");
                        String lastName= json.getString("last_name");
                        JSONObject jsonObject=json.getJSONObject("picture").getJSONObject("data");
                        String photoURL= jsonObject.getString("url");
                        Log.v(TAG, "photoURL " + photoURL);

                        showUserInfo(email, firstName,lastName);

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.v(TAG, "JSONException " + e.toString());
                }

            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "id,name,first_name,last_name,link,email,picture");
        graphRequest.setParameters(parameters);
        graphRequest.executeAsync();
    }

    private void showUserInfo(String email, String firstName, String lastName) {

        tviUsername.setText("Email : "+email+"\nfirstName : "+firstName+"\nlastName: "+lastName);
        rlayUserInfo.setVisibility(View.VISIBLE);
    }

    private void goMainScreen() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
    }

}

