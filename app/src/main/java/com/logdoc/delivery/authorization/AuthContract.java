package com.logdoc.delivery.authorization;

import android.view.View;

public interface AuthContract {

    interface Presenter{

        void checkConnection(String login, String password);

        void signIn(String login, String password);

        boolean validLogin(String login);

        boolean validPassword(String password);

    }

    interface View{

        void initViews(android.view.View root);

        void initListeners();

        void setBackground();

        void setPresenter(AuthContract.Presenter presenter);

        void requestFocus(android.view.View view);

        void showToastNoConnection();

    }

}
