package com.example.alien.course05task11;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.preference.DialogPreference;
import android.support.v7.preference.PreferenceDialogFragmentCompat;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LineDecorationDialogFragment extends DialogFragment {

    protected static final String ARG_KEY = "key";
    private static final String SAVE_STATE_PREFERENCE_VALUE = "TrackDecorationPreferencesDialogFragment.preferenceValue";

    private int mScreenWidth;

    private LineDecoration mTrackDecoration;

    @BindView(R.id.ivSample)
    protected ImageView mImageView;
    @BindView(R.id.tvWidthValue)
    protected TextView mTvWidthValue;


    public static LineDecorationDialogFragment newInstance(String key) {

        Bundle args = new Bundle();
        args.putString(ARG_KEY, key);
        LineDecorationDialogFragment fragment = new LineDecorationDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            initTrackDecoration(savedInstanceState.getString(SAVE_STATE_PREFERENCE_VALUE));
        } else {
            initTrackDecoration("");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_line_decoration_dialog, container, false);
        ButterKnife.bind(this, view);
        initScreenWidth();
        // initMarkerButtons();
        drawLine();
        return view;
    }

    private void initTrackDecoration(String value) {
        mTrackDecoration = new LineDecoration();
        if (value != null && !value.isEmpty()) {
            mTrackDecoration.deserialize(value);
        } else {
           // mTrackDecoration.deserialize(getPreferenceValue());
        }
    }

    private void initScreenWidth() {
        try {
            Display display = getActivity().getWindowManager().getDefaultDisplay();
            Point size = new Point();
            display.getSize(size);
            mScreenWidth = size.x;
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            mScreenWidth = 5000;
        }
    }

//    private void initMarkerButtons() {
//        mIbMarkerTrack01.setImageResource(mCurrentPreferences.getMarkerResId(1));
//        mIbMarkerTrack02.setImageResource(mCurrentPreferences.getMarkerResId(2));
//        mIbMarkerTrack03.setImageResource(mCurrentPreferences.getMarkerResId(3));
//    }

    private void drawLine() {
        try {
            mTvWidthValue.setText(String.valueOf(mTrackDecoration.getLineWidth()));
            //int markerResId = mCurrentPreferences.getMarkerResId(mTrackDecoration.getMarkerType());
            //mIvLeftMarker.setImageResource(markerResId);
            //mIvRightMarker.setImageResource(markerResId);
            Bitmap bitmap = Bitmap.createBitmap(mScreenWidth, mTrackDecoration.getLineWidth(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setColor(mTrackDecoration.getColor());
            canvas.drawRect(0, 0, mScreenWidth, mTrackDecoration.getLineWidth(), paint);
            mImageView.setImageBitmap(bitmap);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    @OnClick(R.id.ibIncreaseWithValue)
    protected void increaseWeightValue() {
        if (mTrackDecoration.getLineWidth() < 100) {
            mTrackDecoration.setLineWidth(mTrackDecoration.getLineWidth() + 1);
            drawLine();
        }
    }

    @OnClick(R.id.ibDecreaseWithValue)
    protected void decreaseWeightValue() {
        if (mTrackDecoration.getLineWidth() > 1) {
            mTrackDecoration.setLineWidth(mTrackDecoration.getLineWidth() - 1);
            drawLine();
        }
    }

    @OnClick({R.id.ibColorTrack01,
            R.id.ibColorTrack02,
            R.id.ibColorTrack03,
            R.id.ibColorTrack04,
            R.id.ibColorTrack05,
            R.id.ibColorTrack06})
    protected void changeColor(View view) {
        if (view.getBackground() instanceof ColorDrawable) {
            mTrackDecoration.setColor(((ColorDrawable) view.getBackground()).getColor());
            drawLine();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVE_STATE_PREFERENCE_VALUE, mTrackDecoration.serialize());
    }

//    private String getPreferenceValue() {
//        DialogPreference preference = getPreference();
//        if (preference instanceof LineDecoration) {
//            LineDecoration lineDecorationPreference = (LineDecoration) preference;
//            return lineDecorationPreference.getValue();
//        } else {
//            return "";
//        }
//    }
//
//    private void setPreferenceValue(String value) {
//        DialogPreference preference = getPreference();
//        if (preference instanceof TrackDecorationPreference) {
//            TrackDecorationPreference trackDecorationPreference = (TrackDecorationPreference) preference;
//            trackDecorationPreference.setValue(value);
//        }
//    }
}
