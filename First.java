package com.mine.alertadddelete.modifiedscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.mine.alertadddelete.R;

public class First extends AppCompatActivity {

    Button mgo_button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        mgo_button = findViewById(R.id.go_button);

        mgo_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(First.this,Direct.class);
                startActivity(intent);
                finish();
            }
        });

    }
}