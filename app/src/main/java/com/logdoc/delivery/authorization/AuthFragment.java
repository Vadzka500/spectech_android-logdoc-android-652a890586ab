package com.logdoc.delivery.authorization;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.annotation.TargetApi;
import android.app.Activity;

import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.logdoc.delivery.Internet;
import com.logdoc.delivery.R;
import com.logdoc.delivery.mainView.MainFragment;
import com.logdoc.delivery.mainView.MainPresenter;

public class AuthFragment extends Fragment implements AuthContract.View {

    private AuthContract.Presenter mPresenter;

    private EditText mLoginEditText;

    private EditText mPasswordEditText;

    private Button mstartLog_in;

    private FrameLayout mframeLog_in;

    Button mLog_in;

    private TextView mErrorLog_in;

    public static AuthFragment getInstance(){
        AuthFragment authFragment = new AuthFragment();
        return authFragment;
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.auth_frag, container, false);

        initViews(root);
        initListeners();
        setBackground();

        return root;
    }


    @Override
    public void initViews(View root) {
        mLog_in = root.findViewById(R.id.Log_in);
        mErrorLog_in = root.findViewById(R.id.errorLog_in);
        mLoginEditText = root.findViewById(R.id.login_edit_text);
        mPasswordEditText = root.findViewById(R.id.password_edit_text);
        mstartLog_in = root.findViewById(R.id.startLog_in);
        mframeLog_in = root.findViewById(R.id.fragmentLog_in);
    }

    @Override
    public void initListeners() {

        mstartLog_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mstartLog_in.setVisibility(View.GONE);
                Animation animation1 = AnimationUtils.loadAnimation(getActivity(), R.anim.auth_popup);
                mframeLog_in.setVisibility(View.VISIBLE);
                mframeLog_in.startAnimation(animation1);

            }
        });

        mLog_in.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                /*String login = mLoginEditText.getText().toString().trim();
                String password = mPasswordEditText.getText().toString().trim();
                mPresenter.checkConnection(login,password);*/





                MainFragment mainFragment = MainFragment.getInstance();
                FragmentTransaction fragmentTransaction = getActivity().getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.authFrame, mainFragment).commit();

                new MainPresenter(mainFragment, getActivity());
            }
        });
    }

    @Override
    public void setBackground() {
        Drawable drawable = null;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            drawable = getActivity().getDrawable(R.drawable.back8);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                drawable.setTint(getActivity().getColor(R.color.tint));
            }
            drawable.setTintMode(PorterDuff.Mode.MULTIPLY);
        }
        getActivity().getWindow().setBackgroundDrawable(drawable);
    }

    @Override
    public void setPresenter(AuthContract.Presenter presenter) {
        this.mPresenter = presenter;
    }

    @Override
    public void requestFocus(View view) {
        if (view.requestFocus()) {
            getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
    }

    @Override
    public void showToastNoConnection() {
        Toast.makeText(getActivity(), getActivity().getString(R.string.error_internet),
                Toast.LENGTH_SHORT).show();
    }

}
