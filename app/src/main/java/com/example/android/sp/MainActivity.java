package com.example.android.sp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b_moveToMarkets = (Button) findViewById(R.id.b_markets);
        b_moveToMarkets.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(com.example.android.sp.MainActivity.this, Screen2_listActivity.class);
                startActivity(intent);
            }
        });

    }


}



