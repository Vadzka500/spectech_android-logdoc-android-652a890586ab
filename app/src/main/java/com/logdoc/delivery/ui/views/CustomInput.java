package com.logdoc.delivery.ui.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.support.constraint.ConstraintLayout;
import android.text.InputType;
import android.util.AttributeSet;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

import com.logdoc.delivery.R;

public class CustomInput extends ConstraintLayout {

    private ConstraintLayout mContainer;

    private TextView mTextView;

    private EditText mEditText;

    public CustomInput(Context context) {
        super(context);
        initAttr(context);
    }

    public CustomInput(Context context, AttributeSet attrs) {
        super(context, attrs);
        initAttr(context, attrs);
    }

    public CustomInput(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initAttr(context, attrs);
    }

    private void initAttr(Context pContext) {
        init(pContext);
    }

    private void initAttr(Context pContext, AttributeSet pAttributeSet) {
        Drawable drawable;
        String title;
        String hint;
        Integer input;

        TypedArray ta = pContext.obtainStyledAttributes(pAttributeSet, R.styleable.CustomInput);
        try {
            drawable = ta.getDrawable(R.styleable.CustomInput_src);
            title = ta.getString(R.styleable.CustomInput_title);
            hint = ta.getString(R.styleable.CustomInput_hint);
            input = ta.getInteger(R.styleable.CustomInput_input_type, 0);
        } finally {
            ta.recycle();
        }

        init(pContext, title, hint, input, drawable);
    }

    private void init(Context pContext, String pTitle, String pInputHint, Integer pInputType, Drawable pDrawable) {
        inflate(pContext, R.layout.item_input, this);

        mContainer = findViewById(R.id.item_input_container);
        mTextView = findViewById(R.id.input_title_text_view);
        mEditText = findViewById(R.id.input_edit_text);

        mTextView.setText(pTitle);
        mEditText.setHint(pInputHint);
        mEditText.setCompoundDrawablesWithIntrinsicBounds(pDrawable, null, null, null);
        mEditText.setInputType(InputType.TYPE_CLASS_TEXT | pInputType);
        mEditText.setTypeface(null,Typeface.NORMAL);

    }

    private void init(Context pContext) {
        inflate(pContext, R.layout.item_input, this);

        mContainer = findViewById(R.id.item_input_container);
        mTextView = findViewById(R.id.input_title_text_view);
        mEditText = findViewById(R.id.input_edit_text);
    }
}
