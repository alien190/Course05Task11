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
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class LineDecorationDialogFragment extends DialogFragment {

    protected static final String ARG_WIDTH_KEY = "WidthKey";
    protected static final String ARG_COLOR_KEY = "ColorKey";
    private static final String SAVE_STATE_PREFERENCE_VALUE = "TrackDecorationPreferencesDialogFragment.preferenceValue";

    private int mScreenWidth;

    private LineDecoration mLineDecoration;
    private ICallback mCallback;

    @BindView(R.id.ivSample)
    protected ImageView mImageView;
    @BindView(R.id.tvWidthValue)
    protected TextView mTvWidthValue;


    public static LineDecorationDialogFragment newInstance(int color, int width) {

        Bundle args = new Bundle();
        args.putInt(ARG_COLOR_KEY, color);
        args.putInt(ARG_WIDTH_KEY, width);
        LineDecorationDialogFragment fragment = new LineDecorationDialogFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState != null) {
            initLineDecoration(savedInstanceState.getString(SAVE_STATE_PREFERENCE_VALUE));
        } else {
            Bundle args = getArguments();
            if (args != null) {
                initLineDecoration(args.getInt(ARG_COLOR_KEY, 0),
                        args.getInt(ARG_WIDTH_KEY, 10));
            } else {
                initLineDecoration("");
            }
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_line_decoration_dialog, container, false);
        ButterKnife.bind(this, view);
        initScreenWidth();
        drawLine();
        return view;
    }

    private void initLineDecoration(int color, int width) {
        mLineDecoration = new LineDecoration();
        mLineDecoration.setColor(color);
        mLineDecoration.setLineWidth(width);
    }

    private void initLineDecoration(String value) {
        mLineDecoration = new LineDecoration();
        if (value != null && !value.isEmpty()) {
            mLineDecoration.deserialize(value);
        } else {
            mLineDecoration.deserialize("");
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

    private void drawLine() {
        try {
            mTvWidthValue.setText(String.valueOf(mLineDecoration.getLineWidth()));
            Bitmap bitmap = Bitmap.createBitmap(mScreenWidth, mLineDecoration.getLineWidth(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmap);
            Paint paint = new Paint();
            paint.setColor(mLineDecoration.getColor());
            canvas.drawRect(0, 0, mScreenWidth, mLineDecoration.getLineWidth(), paint);
            mImageView.setImageBitmap(bitmap);
        } catch (Throwable throwable) {
            throwable.printStackTrace();
        }
    }


    @OnClick(R.id.ibIncreaseWithValue)
    protected void increaseWeightValue() {
        if (mLineDecoration.getLineWidth() < 100) {
            mLineDecoration.setLineWidth(mLineDecoration.getLineWidth() + 1);
            drawLine();
        }
    }

    @OnClick(R.id.ibDecreaseWithValue)
    protected void decreaseWeightValue() {
        if (mLineDecoration.getLineWidth() > 1) {
            mLineDecoration.setLineWidth(mLineDecoration.getLineWidth() - 1);
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
            mLineDecoration.setColor(((ColorDrawable) view.getBackground()).getColor());
            drawLine();
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(SAVE_STATE_PREFERENCE_VALUE, mLineDecoration.serialize());
    }

    @OnClick({R.id.btOk})
    public void onOkClick() {
        if (mCallback != null) {
            mCallback.callback(mLineDecoration);
        }
        dismiss();
    }

    @OnClick({R.id.btCancel})
    public void onCancelClick() {
        dismiss();
    }

    public void setCallback(ICallback callback) {
        mCallback = callback;
    }

    interface ICallback {
        void callback(LineDecoration lineDecoration);
    }

}
