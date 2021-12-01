package com.example.demotest;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

public class IntermediateScreenActivity extends AppCompatActivity {

    GoogleSignInClient mGoogleSignInClient;
    private static final int RC_SIGN_IN = 100;
    Intent intent;

    private static final String APP_SHARED_PREFS = "asdasd_preferences";
    SharedPreferences sharedPrefs;
    SharedPreferences.Editor editor;
    private boolean isUserLoggedIn;
    boolean doubleBackToExitPressedOnce = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intermediate_screen);

        sharedPrefs = getApplicationContext().getSharedPreferences(APP_SHARED_PREFS, Context.MODE_PRIVATE);
        isUserLoggedIn = sharedPrefs.getBoolean("userLoggedInState", false);
        editor = sharedPrefs.edit();
        editor.putBoolean("userLoggedInState", true);
        editor.commit();

        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        // Check for existing Google Sign In account, if the user is already signed in
        // the GoogleSignInAccount will be non-null.
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);

        Button buttonBackToLogin = findViewById(R.id.buttonBackLogin);
//        Button buttonHomeScreen = findViewById(R.id.buttonHomeS);

        SignInButton signInButton = findViewById(R.id.sign_in_button);

        signInButton.setSize(SignInButton.SIZE_STANDARD);

        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        Button buttonBackToLog = (Button) findViewById(R.id.buttonBackLogin);
        buttonBackToLog.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(IntermediateScreenActivity.this,LoginScreenActivity.class);
                startActivity(intent);

                /*if(view == findViewById(R.id.buttonBackLogin)){
                    Fragment fragment = new LoginFragment();
                }
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                transaction.commit();*/
            }
        });

        /*buttonHomeScreen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToHome();
            }
        });*/

    }

    @Override
    public void onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed();
            return;
        }

        this.doubleBackToExitPressedOnce = true;
        Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {

            @Override
            public void run() {
                doubleBackToExitPressedOnce=false;
            }
        }, 2000);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menulistintermediate,menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        
        switch (item.getItemId()){
            
            case R.id.itemEd:
                isUserLoggedIn = sharedPrefs.getBoolean("userLoggedInState", false);
                if (isUserLoggedIn) {
                    Toast.makeText(this, "Please log in using google sign in", Toast.LENGTH_SHORT).show();
                }
                else{
                    Toast.makeText(this, "Employee Details unlocked", Toast.LENGTH_SHORT).show();
                }
                return true;
            case R.id.itemAd:
                isUserLoggedIn = sharedPrefs.getBoolean("userLoggedInState", false);
                if (isUserLoggedIn) {
                    Toast.makeText(this, "Please log in using google sign in", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Address Details unlocked", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.itemSd:
                isUserLoggedIn = sharedPrefs.getBoolean("userLoggedInState", false);
                if (isUserLoggedIn) {
                    Toast.makeText(this, "Please log in using google sign in", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Salary Details unlocked", Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.itemEh:
                isUserLoggedIn = sharedPrefs.getBoolean("userLoggedInState", false);
                if (isUserLoggedIn) {
                    Toast.makeText(this, "Please log in using google sign in", Toast.LENGTH_SHORT).show();
                }
                else {
                    Toast.makeText(this, "Employee History unlocked", Toast.LENGTH_SHORT).show();
                }
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            GoogleSignInAccount account = completedTask.getResult(ApiException.class);

            GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
            if (acct != null) {
                String personName = acct.getDisplayName();
                String personGivenName = acct.getGivenName();
                String personFamilyName = acct.getFamilyName();
                String personEmail = acct.getEmail();
                String personId = acct.getId();
                Uri personPhoto = acct.getPhotoUrl();

                Toast.makeText(this, "Welcome " + personName + "!", Toast.LENGTH_SHORT).show();
            }
            startActivity(new Intent(IntermediateScreenActivity.this,PrivacyDetailsActivity.class));
            // Signed in successfully, show authenticated UI.
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            Log.d("Message ", e.toString());
        }
    }
}