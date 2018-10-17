package com.example.alien.course05task11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity implements LineDecorationDialogFragment.ICallback {

    private DrawingView mDrawingView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDrawingView = findViewById(R.id.drawing);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.mi_editLineDecoration) {
            LineDecorationDialogFragment dialogFragment =
                    LineDecorationDialogFragment.newInstance(mDrawingView.getLineColor(), mDrawingView.getLineWidth());
            dialogFragment.setCallback(this);
            dialogFragment.show(getSupportFragmentManager(), "LineDecorationDialogFragment");
            return true;
        } else return false;
    }

    @Override
    public void callback(LineDecoration lineDecoration) {
        mDrawingView.setLineColor(lineDecoration.getColor());
        mDrawingView.setLineWidth(lineDecoration.getLineWidth());
    }
}
