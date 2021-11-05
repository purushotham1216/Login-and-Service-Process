package com.mine.alertadddelete.modifiedscreen;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.widget.EditText;
import android.widget.TextView;

import com.mine.alertadddelete.R;

import java.text.DecimalFormat;
import java.text.FieldPosition;
import java.text.NumberFormat;
import java.text.ParsePosition;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AroundValues extends AppCompatActivity {

    TextView mround_text;
    EditText mround_edit;
    String mround_edit_str;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_around_values);

        mround_text = findViewById(R.id.round_text);
        mround_edit = findViewById(R.id.round_edit);
        mround_edit_str = mround_edit.getText().toString();
        /*String pattern = "###.##";
        DecimalFormat decimalFormat = new DecimalFormat(pattern);
        decimalFormat.applyPattern(pattern);

        Pattern mPattern = Pattern.compile("^([1-9][0-9]{0,2})?(\\.[0-9]{0,1}?)?$");

        float x = 1000.86932f;
        DecimalFormat df = new DecimalFormat("###.##");
        String l = df.format(x);*/



//        Pattern mPattern = Pattern.compile("^([0-9]{0,2})?(\\.[0-9]{0,1}?)?$");
        Pattern mPattern = Pattern.compile("^([0-9]{0,2})?(\\.[0-9]{0,1}?)?$");
        double x = 1000.86932d;
        DecimalFormat df = new DecimalFormat("###.##");
        String l = df.format(x);

        if (mround_edit_str.matches(mPattern.toString())){
            mround_text.setText(l);
        }else {
            mround_edit.setError("Pattern error");
        }

        TextWatcher textWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

                if (!TextUtils.isEmpty(mround_edit.getText().toString())){
                    int answer = 0;
                    try {
                        answer = Integer.parseInt(mround_edit.getText().toString().trim());
                    }catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                    mround_text.setText(String.valueOf(answer));
                }else {
                    mround_text.setText("");
                }
                }

            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        mround_edit.addTextChangedListener(textWatcher);
        }

    }
