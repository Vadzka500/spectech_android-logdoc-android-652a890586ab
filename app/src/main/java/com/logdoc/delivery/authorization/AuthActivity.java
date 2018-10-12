package com.logdoc.delivery.authorization;


import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.logdoc.delivery.App;
import com.logdoc.delivery.Internet;
import com.logdoc.delivery.R;
import com.logdoc.delivery.api.API;
import com.logdoc.delivery.api.APICallback;
import com.logdoc.delivery.api.model.CommonResponse;
import com.logdoc.delivery.db.LOGDOCDatabase;
import com.logdoc.delivery.utils.Prefs;
import com.logdoc.delivery.utils.ThreadExecutors;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;


public class AuthActivity extends AppCompatActivity {

    private AuthPresenter mPresenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_auth);

        AuthFragment authFragment = AuthFragment.getInstance();
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.authFrame, authFragment).commit();

        mPresenter = new AuthPresenter(authFragment, this);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}