package com.android.base.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.base.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProgressLayout extends RelativeLayout {

    private static final String TAG_PROGRESS = "ProgressLayout.TAG_PROGRESS";
    private static final String TAG_ERROR = "ProgressLayout.TAG_ERROR";

    public static enum State {
        CONTENT, PROGRESS, ERROR
    }

    private View mProgressView;
    private TextView mErrorTextView;
    private List<View> mContentViews = new ArrayList<View>();

    private State mState = State.CONTENT;

    public ProgressLayout(Context context) {
        super(context);
    }

    public ProgressLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs);
    }

    public ProgressLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs);
    }

    private void init(AttributeSet attrs) {
        TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressLayout);
        int backgroundColor = a.getColor(R.styleable.ProgressLayout_progressBackground, Color.TRANSPARENT);
        boolean progress = a.getBoolean(R.styleable.ProgressLayout_progress, false);
        a.recycle();

        LayoutParams layoutParams;

        // if progressBackground color == Color.TRANSPARENT just add progress bar
        if (backgroundColor == Color.TRANSPARENT) {
            mProgressView = new ProgressBar(getContext());

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.addRule(CENTER_IN_PARENT);
        } else { // else wrap progress bar in LinearLayout and set background color to LinearLayout
            LinearLayout linearLayout = new LinearLayout(getContext());
            linearLayout.setGravity(Gravity.CENTER);
            linearLayout.setBackgroundColor(backgroundColor);

            layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);

            ProgressBar progressBar = new ProgressBar(getContext());
            linearLayout.addView(progressBar);

            mProgressView = linearLayout;
        }

        mProgressView.setTag(TAG_PROGRESS);
        addView(mProgressView, layoutParams);

        // add error text view
        mErrorTextView = new TextView(getContext());
        mErrorTextView.setTag(TAG_ERROR);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.addRule(CENTER_IN_PARENT);

        addView(mErrorTextView, layoutParams);

        mProgressView.setVisibility(progress ? VISIBLE : GONE);
    }

    @Override
    public void addView(View child, int index, ViewGroup.LayoutParams params) {
        super.addView(child, index, params);

        if (child.getTag() == null || (!child.getTag().equals(TAG_PROGRESS) && !child.getTag().equals(TAG_ERROR))) {
            mContentViews.add(child);
        }
    }

    public void showProgress() {
        switchState(State.PROGRESS, null, Collections.<Integer>emptyList());
    }

    public void showProgress(List<Integer> skipIds) {
        switchState(State.PROGRESS, null, skipIds);
    }

    public void showErrorText() {
        switchState(State.ERROR, null, Collections.<Integer>emptyList());
    }

    public void showErrorText(List<Integer> skipIds) {
        switchState(State.ERROR, null, skipIds);
    }

    public void showErrorText(String error) {
        switchState(State.ERROR, error, Collections.<Integer>emptyList());
    }

    public void showErrorText(String error, List<Integer> skipIds) {
        switchState(State.ERROR, error, skipIds);
    }

    public void showContent() {
        switchState(State.CONTENT, null, Collections.<Integer>emptyList());
    }

    public void showContent(List<Integer> skipIds) {
        switchState(State.CONTENT, null, skipIds);
    }

    public void switchState(State state) {
        switchState(state, null, Collections.<Integer>emptyList());
    }

    public void switchState(State state, String errorText) {
        switchState(state, errorText, Collections.<Integer>emptyList());
    }

    public void switchState(State state, List<Integer> skipIds) {
        switchState(state, null, skipIds);
    }

    public void switchState(State state, String errorText, List<Integer> skipIds) {
        mState = state;

        switch (state) {
            case CONTENT:
                mErrorTextView.setVisibility(View.GONE);
                mProgressView.setVisibility(View.GONE);
                setContentVisibility(true, skipIds);
                break;
            case PROGRESS:
                mErrorTextView.setVisibility(View.GONE);
                mProgressView.setVisibility(View.VISIBLE);
                setContentVisibility(false, skipIds);
                break;
            case ERROR:
                if (TextUtils.isEmpty(errorText)) {
                    mErrorTextView.setText(R.string.unknown_error);
                } else {
                    mErrorTextView.setText(errorText);
                }
                mErrorTextView.setVisibility(View.VISIBLE);
                mProgressView.setVisibility(View.GONE);
                setContentVisibility(false, skipIds);
                break;
        }
    }

    public State getState() {
        return mState;
    }

    public boolean isProgress() {
        return mState == State.PROGRESS;
    }

    public boolean isContent() {
        return mState == State.CONTENT;
    }

    public boolean isError() {
        return mState == State.ERROR;
    }

    private void setContentVisibility(boolean visible, List<Integer> skipIds) {
        for (View v : mContentViews) {
            if (!skipIds.contains(v.getId())) {
                v.setVisibility(visible ? View.VISIBLE : View.GONE);
            }
        }
    }
}
