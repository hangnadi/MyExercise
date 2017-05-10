package com.hangnadi.toolbar.collapse;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        loadImage();

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

    private void loadImage() {
        ImageView bgHeader = (ImageView) findViewById(R.id.bgheader);
        Glide.with(this)
                .load(getRandomBgImage())
                .centerCrop()
                .into(bgHeader);
    }

    private String getRandomBgImage() {
        Random random = new Random();
        switch (random.nextInt(5)) {
            default:
            case 0:
                return "https://github.com/chrisbanes/cheesesquare/blob/master/app/src/main/res/drawable-nodpi/cheese_1.jpg?raw=true";
            case 1:
                return "https://github.com/chrisbanes/cheesesquare/blob/master/app/src/main/res/drawable-nodpi/cheese_2.jpg?raw=true";
            case 2:
                return "https://github.com/chrisbanes/cheesesquare/blob/master/app/src/main/res/drawable-nodpi/cheese_3.jpg?raw=true";
            case 3:
                return "https://github.com/chrisbanes/cheesesquare/blob/master/app/src/main/res/drawable-nodpi/cheese_4.jpg?raw=true";
            case 4:
                return "https://github.com/chrisbanes/cheesesquare/blob/master/app/src/main/res/drawable-nodpi/cheese_5.jpg?raw=true";
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
