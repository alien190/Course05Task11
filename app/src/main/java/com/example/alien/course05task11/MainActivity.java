package com.example.alien.course05task11;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mi_editLineDecoration){
            LineDecorationDialogFragment dialogFragment = LineDecorationDialogFragment.newInstance("");
            dialogFragment.show(getSupportFragmentManager(), "LineDecorationDialogFragment");
            return true;
        }
        else return false;
    }
}
