package com.mine.alertadddelete.modifiedscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.Layout;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import com.mine.alertadddelete.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class ModifiedSeedStocking extends AppCompatActivity {

    private ArrayList<String> fishtype_list;
    Spinner mspecies_selection,mfinyear_selection;
    LinearLayout mlist_module,mlist_modulee;
    private EditText mfingerlings_no,mtotal_kgs;
    private TextView mtotal_fingerlings;
    String mfingerlings_no_str,mtotal_kgs_str,mtotal_fingerlings_str;
    Button mdetail_button;

//    Service mapping values
    JSONObject finYearJsonObject;
    JSONObject finYearLoopJsonObject;

    ArrayList<String> finYear_list;
    ArrayAdapter finYearAdap;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modified_seed_stocking);

        mspecies_selection = findViewById(R.id.species_selection);
        mlist_module = findViewById(R.id.list_module);
        mlist_modulee = findViewById(R.id.list_modulee);
        mfingerlings_no = findViewById(R.id.fingerlings_no);
        mtotal_kgs = findViewById(R.id.total_kgs);
        mtotal_fingerlings = findViewById(R.id.total_fingerlings);

        fishtype_list = new ArrayList<>();
        fishtype_list.add("<--- Select the type of Fish --->");
        fishtype_list.add("Catla");
        fishtype_list.add("Rohu");
        fishtype_list.add("Common Carp");
        fishtype_list.add("Mrigala");

        finYear_list = new ArrayList<String>();

        fishDetails();
//        caluculation();
        mlist_module.setVisibility(View.GONE);
        mlist_modulee.setVisibility(View.GONE);
        mdetail_button.setVisibility(View.GONE);
        mtotal_fingerlings.setText("");

        mfingerlings_no.addTextChangedListener(textWatcher);
        mtotal_kgs.addTextChangedListener(textWatcher);

    }

    private void callFinYear(){
        String finYearService;

        try {
            finYearService = new FinancialYear().execute().get();
            finYearJsonObject = new JSONObject(finYearService);

            Log.d("jjjjjj",""+finYearJsonObject);
            String totalString = finYearJsonObject.getString("Data");
            JSONArray jsonArray = new JSONArray(totalString);

            if (jsonArray.length() > 0){
                for (int i = 0;i < jsonArray.length();i++){
                    finYearLoopJsonObject =jsonArray.getJSONObject(i);

                    String year_code = finYearLoopJsonObject.getString("Year_Code");
                    String year_description = finYearLoopJsonObject.getString("Year_Desc");
                    String fin_year_desc = finYearLoopJsonObject.getString("Fisilyear_Desc");

                    finYear_list.add(finYearLoopJsonObject.getString(year_description));
                            finYearAdap = new ArrayAdapter<String>(
                            getApplicationContext(), android.R.layout.simple_list_item_activated_1,
                            finYear_list);
//                    finYear_list.setAdapter(finYearAdap);
                }
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if (!TextUtils.isEmpty(mfingerlings_no.getText().toString().trim())
                    && !TextUtils.isEmpty(mtotal_kgs.getText().toString().trim())
            ) {

                int answer = 0;
                try {
                    answer = Integer.parseInt(mfingerlings_no.getText().toString().trim()) *
                            Integer.parseInt(mtotal_kgs.getText().toString().trim());
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }

                Log.e("RESULT", String.valueOf(answer));
                mtotal_fingerlings.setText(String.valueOf(answer));
            }else {
                mtotal_fingerlings.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

        }
    };

    public void fishDetails(){
        final ArrayAdapter<String> district_list = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, fishtype_list);
        district_list.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mspecies_selection.setAdapter(district_list);

        mspecies_selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    mlist_module.setVisibility(View.VISIBLE);
                    mlist_modulee.setVisibility(View.VISIBLE);
                    mdetail_button.setVisibility(View.VISIBLE);
                }else{
                    mlist_module.setVisibility(View.GONE);
                    mlist_modulee.setVisibility(View.GONE);
                    mdetail_button.setVisibility(View.GONE);
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    /*public void caluculation(){
        mfingerlings_no_str = mfingerlings_no.getText().toString();
        mtotal_kgs_str = mtotal_kgs.getText().toString();
        mtotal_fingerlings_str = mtotal_fingerlings.getText().toString();

        int totalKgs = 0;
        try {
            int finger_no = Integer.parseInt(mfingerlings_no_str);
            int total_no_kgs = Integer.parseInt(mtotal_kgs_str);
            totalKgs = finger_no + total_no_kgs;
        } catch (NumberFormatException e) {

            e.printStackTrace();
        }
        if (!(mfingerlings_no == null && mtotal_kgs == null)) {
            mtotal_fingerlings.setText(String.valueOf(totalKgs));
        }else{
            mtotal_fingerlings.setText("");
        }
    }*/

    public class FinancialYear extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            return null;
        }
    }
}

    /*EditText edtOne, edtTwo, edtThree, edtFour;
    TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtOne = findViewById(R.id.edtOne);
        edtTwo = findViewById(R.id.edtTwo);
        edtThree = findViewById(R.id.edtThree);
        edtFour = findViewById(R.id.edtFour);

        tvResult = findViewById(R.id.tvResult);

        edtOne.addTextChangedListener(textWatcher);
        edtTwo.addTextChangedListener(textWatcher);
        edtThree.addTextChangedListener(textWatcher);
        edtFour.addTextChangedListener(textWatcher);

    }

    TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            if (!TextUtils.isEmpty(edtOne.getText().toString().trim())
                    || !TextUtils.isEmpty(edtTwo.getText().toString().trim())
                    || !TextUtils.isEmpty(edtThree.getText().toString().trim())
                    || !TextUtils.isEmpty(edtFour.getText().toString().trim())
            ) {


                int answer = Integer.parseInt(edtOne.getText().toString().trim()) +
                        Integer.parseInt(edtTwo.getText().toString().trim()) +
                        Integer.parseInt(edtThree.getText().toString().trim()) +
                        Integer.parseInt(edtFour.getText().toString().trim());

                Log.e("RESULT", String.valueOf(answer));
                tvResult.setText(String.valueOf(answer));
            }else {
                tvResult.setText("");
            }
        }

        @Override
        public void afterTextChanged(Editable editable) {

        }
    };*/