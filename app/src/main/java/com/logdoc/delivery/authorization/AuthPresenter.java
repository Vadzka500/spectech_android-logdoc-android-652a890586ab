package com.logdoc.delivery.authorization;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.util.Base64;

import com.logdoc.delivery.App;
import com.logdoc.delivery.Internet;
import com.logdoc.delivery.R;
import com.logdoc.delivery.api.API;
import com.logdoc.delivery.api.APICallback;
import com.logdoc.delivery.api.model.CommonResponse;
import com.logdoc.delivery.db.LOGDOCDatabase;
import com.logdoc.delivery.utils.ThreadExecutors;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AuthPresenter implements AuthContract.Presenter {

    private final AuthContract.View view;

    private Activity activity;

    private App mApp;

    private LOGDOCDatabase mLOGDOCDatabase;

    private ThreadExecutors mThreadExecutors;

    private Retrofit mRetrofit;

    public AuthPresenter(AuthContract.View view, Activity activity){
        this.view = view;
        this.activity = activity;
        mApp = (App) activity.getApplicationContext();
        mLOGDOCDatabase = mApp.getLOGDOCDatabase();
        mThreadExecutors = mApp.getThreadExecutors();
        mRetrofit = mApp.getRetrofit();
        this.view.setPresenter(this);
    }

    @Override
    public void checkConnection(String login, String password) {
        if (Internet.hasConnection(activity))
            signIn(login, password);
        else
           view.showToastNoConnection();
    }

    @Override
    public void signIn(String login, String password) {

        if (!validLogin(login) || !validPassword(password)) {
            return;
        }

        //view.changeVisibility(false);

        try {

            String basic = activity.getString(R.string.authorization_base64);
            String base64 = Base64.encodeToString(basic.getBytes("UTF-8"), Base64.DEFAULT).trim();

//            Call<CommonResponse> call = mRetrofit.create(API.class).authUser(new AuthRequest(login, password));//, getString(R.string.authorization_basic) + base64);
            Call<CommonResponse> call = mRetrofit.create(API.class).authUser(login, password, "password");//, getString(R.string.authorization_basic) + base64);
//            Call<CommonResponse> call = mRetrofit.create(API.class).authUser("application/x-www-form-urlencoded", "Basic d2ViOm1vYmlsZQ==",
//                    RequestBody.create(
//                            MediaType.parse("text/plain"),
//                            "string"),
//                    RequestBody.create(
//                            MediaType.parse("text/plain"),
//                            "string"),
//                    RequestBody.create(
//                            MediaType.parse("text/plain"),
//                            "password")
//            );//, getString(R.string.authorization_basic) + base64);
            call.enqueue(new APICallback<CommonResponse>() {
                @Override
                public void onResponse(Call<CommonResponse> call, final Response<CommonResponse> response) {

                    if (response.isSuccessful()) {
                        System.out.println("RESPONCE succes");
                        mThreadExecutors.dbExecutor().execute(new Runnable() {
                            @Override
                            public void run() {

                            }
                        });

                    } else {
                        System.out.println("Response fail"+response.code());
                    }
                }

                @Override
                public void onFailure(Call<CommonResponse> call, Throwable t) {
                    System.out.println("FAIL");
                }
            });
        } catch (Exception ex) {
            String s = ex.getMessage();
            ex.printStackTrace();
        }
    }

    @Override
    public boolean validLogin(String login) {
        if (login.isEmpty()) {
          //view.showLoginError();
            return false;
        } else {
            //view.setErrorLoginEnabled();
        }

        return true;
    }

    @Override
    public boolean validPassword(String password) {
        if (password.isEmpty()) {
           //view.showPasswordError();
            return false;
        } else {
            //view.setErrorPasswordEnabled();
        }

        return true;
    }




}
