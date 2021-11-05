package com.mine.alertadddelete.modifiedscreen;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mine.alertadddelete.R;
import com.mine.alertadddelete.ws_module.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

public class RemodeledRecycleActivity extends AppCompatActivity {

    Button malert_btn,mimage_picking_btn;
    RecyclerView rv;
    RecyclerAdapter recyclerAdapter;
    ArrayList<SetMethods> rlist = new ArrayList<>();
    Spinner mspecies_selection;
    EditText mfingerlings_no,mtotal_kgs,mvehicleno,msupplier_seed,mleftover_seed;
    TextView mtotal_fingerlings;
    LinearLayout mseasonal_layout,mmandal_layout,mintended_layout,mintended_body_layout
            ,mintended_supplier_layout,msupplier_layout,msupplier_seed_layout,mintended_leftover_layout,
            mprimary_mandal_layout,mprimary_village_layout,mfinancial_layout,mcultural_type_layout;

    Spinner mculture_type_selection,mfin_year_selection,mseasonlity_selection,mprimary_mandal_selection,
            mprimary_village_selection,mintended_water_body_selection,mintended_suppier_selection;

    ArrayList<String> culture_type_list,financial_year,seasonal_list,mandal_list,village_list,
    water_body_list,intended_suppliers_list,culture_type_list_one,culture_type_list_two,
            seasonal_fish_list,seasonal_fish_list_two;
    ImageView mimageView;

    final int REQUEST_CODE_GALLERY = 999;
    ArrayList<String> imageUris;

    //    Service mapping values
    JSONObject finYearJsonObject,culturalTypeJsonObject,seasonalJsonObject;
    JSONObject finYearLoopJsonObject,culturalTypeLoopJsonObject,seasonalLoopJsonObject;
    ArrayList<String> finYear_list,cultureType_list,seasonalSelection_list;
    ArrayAdapter finYearAdap,cultureTypeAdap,seasonalAdap;

    String finYearService,cultureTypeService,seasonalService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modified_seed_stocking);

        imageUris = new ArrayList<>();

        mcultural_type_layout = findViewById(R.id.cultural_type_layout);
        mculture_type_selection = findViewById(R.id.culture_type_selection);
        mfin_year_selection = findViewById(R.id.fin_year_selection);
        mseasonlity_selection = findViewById(R.id.seasonlity_selection);
        mprimary_mandal_selection = findViewById(R.id.primary_mandal_selection);
        mprimary_village_selection = findViewById(R.id.primary_village_selection);
        mintended_water_body_selection = findViewById(R.id.intended_water_body_selection);
        mintended_suppier_selection = findViewById(R.id.intended_suppier_selection);

        mfinancial_layout = findViewById(R.id.financial_layout);
        mseasonal_layout = findViewById(R.id.seasonal_layout);
        mmandal_layout = findViewById(R.id.mandal_layout);
        mintended_layout = findViewById(R.id.intended_layout);
        mintended_body_layout = findViewById(R.id.intended_body_layout);
        mintended_supplier_layout = findViewById(R.id.intended_supplier_layout);
        msupplier_layout = findViewById(R.id.supplier_layout);
        msupplier_seed_layout = findViewById(R.id.supplier_seed_layout);
        mintended_leftover_layout = findViewById(R.id.intended_leftover_layout);
        mprimary_mandal_layout = findViewById(R.id.primary_mandal_layout);
        mprimary_village_layout = findViewById(R.id.primary_village_layout);

        msupplier_seed = findViewById(R.id.supplier_seed);
        mleftover_seed = findViewById(R.id.leftover_seed);

        mimage_picking_btn = findViewById(R.id.image_picking_btn);
        mimageView = findViewById(R.id.img);

        msupplier_seed.setText("225");
        mleftover_seed.setText("225");

//        Service Arraylists
        finYear_list = new ArrayList<>();
        cultureType_list = new ArrayList<>();
        seasonalSelection_list = new ArrayList<>();

        culture_type_list = new ArrayList<>();

        malert_btn = findViewById(R.id.alert_btn);
        malert_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alert_popup();
            }
        });

        mimage_picking_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                /*ActivityCompat.requestPermissions(TestImage.this,new String[]
                        {Manifest.permission.READ_EXTERNAL_STORAGE},REQUEST_CODE_GALLERY);*/

                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, REQUEST_CODE_GALLERY);

//                pickImage();

            }
        });

        culture_type_list_one = new ArrayList<>();
        culture_type_list_one.add("--Select Year--");
        culture_type_list_one.add("1");
        culture_type_list_one.add("2");

        culture_type_list_two = new ArrayList<>();
        culture_type_list_one.add("--Select Year--");
        culture_type_list_two.add("11");
        culture_type_list_two.add("22");

        seasonal_list = new ArrayList<>();
        seasonal_list.add("--Select Seasonality--");
        seasonal_list.add("Seasonal");
        seasonal_list.add("Seasonal2");

        seasonal_fish_list = new ArrayList<>();
        seasonal_fish_list.add("Seasonal Fish 1");
        seasonal_fish_list.add("Seasonal Fish 2");

        seasonal_fish_list_two = new ArrayList<>();
        seasonal_fish_list_two.add("Seasonal Fish 1");
        seasonal_fish_list_two.add("Seasonal Fish 2");
        seasonal_fish_list_two.add("Seasonal Fish 3");

        inItViews();
        callFinYear();
        cultureType();
        callseasonality();
//        fishDetails();
        /*customType();
        seasonal();
        mandalSelection();
        villageSelection();
        intendedWaterBodySelection();
        intendedSuppliers();*/


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK && requestCode == REQUEST_CODE_GALLERY){
            Uri imageUri = data.getData();
            mimageView.setImageURI(imageUri);
        }
    }

    public void customType(){
        culture_type_list = new ArrayList<>();
        culture_type_list.add("--Select the Fish Culture--");
        culture_type_list.add("Fish1");
        culture_type_list.add("Fish2");


        final ArrayAdapter<String> culture_adap = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, culture_type_list);
        culture_adap.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mculture_type_selection.setAdapter(culture_adap);

        mculture_type_selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0 ){

                    if (culture_type_list.get(position).toString().equalsIgnoreCase("Fish1"))
                        financialYear(culture_type_list_one);
                    else {
                        financialYear(culture_type_list_two);
                    }
                    mfin_year_selection.setSelection(0);
                    mfinancial_layout.setVisibility(View.VISIBLE);
                }else{
                    mfinancial_layout.setVisibility(View.GONE);
                }
                mseasonal_layout.setVisibility(View.GONE);

                mprimary_mandal_layout.setVisibility(View.GONE);
                mprimary_village_layout.setVisibility(View.GONE);
                mintended_layout.setVisibility(View.GONE);
                msupplier_layout.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void financialYear(ArrayList<String> cultural_list){
        financial_year = new ArrayList<>();
        financial_year.add("--Select the Year--");
        financial_year.add("year1");
        financial_year.add("year2");

        final ArrayAdapter<String> financial_year_adap = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, cultural_list);
        financial_year_adap.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mfin_year_selection.setAdapter(financial_year_adap);

        mfin_year_selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    mseasonlity_selection.setSelection(0);
                    mseasonal_layout.setVisibility(View.VISIBLE);
                }else {
                    mseasonal_layout.setVisibility(View.GONE);
                }
                mprimary_mandal_layout.setVisibility(View.GONE);
                mprimary_village_layout.setVisibility(View.GONE);
                mintended_layout.setVisibility(View.GONE);
                msupplier_layout.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void seasonal(){

        final ArrayAdapter<String> seasonal_list_adap = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line,seasonal_list);
        seasonal_list_adap.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mseasonlity_selection.setAdapter(seasonal_list_adap);

        mseasonlity_selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){

                    mprimary_mandal_selection.setSelection(0);
                    mprimary_mandal_layout.setVisibility(View.VISIBLE);
                }else {
                    mprimary_mandal_layout.setVisibility(View.GONE);
                }
                mprimary_village_layout.setVisibility(View.GONE);
                mintended_layout.setVisibility(View.GONE);
                msupplier_layout.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void mandalSelection(){

        mandal_list = new ArrayList<>();
        mandal_list.add("--Select Mandal--");
        mandal_list.add("mandal1");
        mandal_list.add("mandal2");

        final ArrayAdapter<String> mandal_list_adap = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, mandal_list);
        mandal_list_adap.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mprimary_mandal_selection.setAdapter(mandal_list_adap);

        mprimary_mandal_selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){

   /*
   set selection is used for if respected mandal selection was not fixed and
   it is static values that the spinner values become and set to '0' index
   */
                    mprimary_village_selection.setSelection(0);
                    mprimary_village_layout.setVisibility(View.VISIBLE);
                }else {
                    mprimary_village_layout.setVisibility(View.GONE);
                }
                mintended_layout.setVisibility(View.GONE);
                msupplier_layout.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void villageSelection(){

        village_list = new ArrayList<>();
        village_list.add("--Select Village--");
        village_list.add("village1");
        village_list.add("village2");

        final ArrayAdapter<String> village_list_adap = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, village_list);
        village_list_adap.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mprimary_village_selection.setAdapter(village_list_adap);


        mprimary_village_selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    mintended_water_body_selection.setSelection(0);
                    mintended_layout.setVisibility(View.VISIBLE);
                }else {
                    mintended_layout.setVisibility(View.GONE);
                }
                msupplier_layout.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void intendedWaterBodySelection(){

        water_body_list = new ArrayList<>();
        water_body_list.add("--Select Water Body--");
        water_body_list.add("Water Body1");
        water_body_list.add("Water Body2");

        final ArrayAdapter<String> water_body_adap = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, water_body_list);
        water_body_adap.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mintended_water_body_selection.setAdapter(water_body_adap);


        mintended_water_body_selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){

                    mintended_supplier_layout.setVisibility(View.VISIBLE);
                }else {
                    mintended_supplier_layout.setVisibility(View.GONE);
                }
                msupplier_layout.setVisibility(View.GONE);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    public void intendedSuppliers(){
        intended_suppliers_list = new ArrayList<>();
        intended_suppliers_list.add("--Select Suppliers--");
        intended_suppliers_list.add("Suppliers1");
        intended_suppliers_list.add("Suppliers2");

        final ArrayAdapter<String> suppliers_adap = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, intended_suppliers_list);
        suppliers_adap.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mintended_suppier_selection.setAdapter(suppliers_adap);

        mintended_suppier_selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){
                    msupplier_layout.setVisibility(View.VISIBLE);
                }else{
                    msupplier_layout.setVisibility(View.GONE);
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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

    private void inItViews(){
        rv = findViewById(R.id.recyclerview);
        rv.setHasFixedSize(true);
    }

    private void prepareRCItems(){
            recyclerAdapter = new RemodeledRecycleActivity.RecyclerAdapter(RemodeledRecycleActivity.this,rlist);
            rv.setLayoutManager(new LinearLayoutManager(RemodeledRecycleActivity.this));
            rv.setAdapter(recyclerAdapter);
    }
    public void alert_popup(){

        Button mclose_btn,msubmit_btn;

      final  AlertDialog.Builder alert_dialog = new AlertDialog.Builder(RemodeledRecycleActivity.this);
        alert_dialog.setCancelable(false);
        LayoutInflater inflater = RemodeledRecycleActivity.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_popup,null);
        alert_dialog.setView(view);

        AlertDialog alertDialog = alert_dialog.create();

        mclose_btn = view.findViewById(R.id.close_btn);
        msubmit_btn = view.findViewById(R.id.submit_btn);
        mfingerlings_no = view.findViewById(R.id.fingerlings_no);
        mtotal_kgs = view.findViewById(R.id.total_kgs);
        mvehicleno = view.findViewById(R.id.vehicleno);
        mspecies_selection = view.findViewById(R.id.species_selection);
        mtotal_fingerlings = view.findViewById(R.id.total_fingerlings);
        mseasonlity_selection = view.findViewById(R.id.seasonlity_selection);

        mfingerlings_no.addTextChangedListener(textWatcher);
        mtotal_kgs.addTextChangedListener(textWatcher);
        mtotal_fingerlings.setText("");

        fishDetails(mspecies_selection);

        mseasonlity_selection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position > 0){

                    if (seasonal_fish_list.get(position).toString().equalsIgnoreCase("Seasonal")){

                    }
                    mprimary_mandal_selection.setSelection(0);
                    mprimary_mandal_layout.setVisibility(View.VISIBLE);
                }else {
                    mprimary_mandal_layout.setVisibility(View.GONE);
                }
                mprimary_village_layout.setVisibility(View.GONE);
                mintended_layout.setVisibility(View.GONE);
                msupplier_layout.setVisibility(View.GONE);
            }
        });

        msubmit_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mfingerlings_no.getText().toString().isEmpty()){
                    mfingerlings_no.setError("Please enter the fingerlings");
                }else if (mtotal_kgs.getText().toString().isEmpty()){
                    mtotal_kgs.setError("Please enter the Number of kgs");
                }else if (mvehicleno.getText().toString().isEmpty()){
                    mvehicleno.setError("Please enter the Vehicle Number");
                }else {
                    SetMethods setMethods = new SetMethods(

                            mspecies_selection.getSelectedItem().toString().trim(),
                            mfingerlings_no.getText().toString().trim(),
                            mtotal_kgs.getText().toString().trim(),
                            mtotal_fingerlings.getText().toString().trim(),
                            mvehicleno.getText().toString().trim()
                    );

                    rlist.add(setMethods);
                    if (rlist.size() == 1) {
                        prepareRCItems();
                    } else {
                        recyclerAdapter.notifyItemInserted(rlist.size() - 1);
                    }
                }

            }
        });

        mclose_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.cancel();
            }
        });
        alertDialog.show();

    }

    public void fishDetails(Spinner fishhhh_spin){
       // mspecies_selection = findViewById(R.id.species_selection);
        ArrayList<String> fishtype_list;

        fishtype_list = new ArrayList<>();
        fishtype_list.add("<--- Select the type of Fish --->");
        fishtype_list.add("Catla");
        fishtype_list.add("Rohu");
        fishtype_list.add("Common Carp");
        fishtype_list.add("Mrigala");

        final ArrayAdapter<String> fish_list = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, fishtype_list);
        fish_list.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);

        Log.d("fishList",""+fishtype_list);
        fishhhh_spin.setAdapter(fish_list);

        fishhhh_spin.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

    }

    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder>{

        Context context;
        LayoutInflater inflater;
        ArrayList<SetMethods> rlist = new ArrayList();
        public RecyclerAdapter(RemodeledRecycleActivity remodeledRecycleActivity, ArrayList<SetMethods> rlist) {
            this.context = remodeledRecycleActivity;
            inflater =LayoutInflater.from(context);
            this.rlist = rlist;
        }

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            inflater = LayoutInflater.from(context);
            View view =inflater.inflate(R.layout.view_layout,parent,false);
            return new RecyclerAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {

            holder.tv1.setText(rlist.get(position).getFishtype());
            holder.tv2.setText(rlist.get(position).getFingerling_no());
            holder.tv3.setText(rlist.get(position).getTotal_kgs());
            holder.tv4.setText(rlist.get(position).getTotal_fingerlings());
            holder.tv5.setText(rlist.get(position).getVehicle());

            Log.d("holder",rlist.get(position).getFishtype());
        }

        @Override
        public int getItemCount() {
            return rlist.size();
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            TextView tv1,tv2,tv3,tv4,tv5;
            public ViewHolder(View itemView) {
                super(itemView);

                tv1 = itemView.findViewById(R.id.fishname);
                tv2 = itemView.findViewById(R.id.no_fingerlings);
                tv3 = itemView.findViewById(R.id.kgs);
                tv4 = itemView.findViewById(R.id.kgs_total);
                tv5 = itemView.findViewById(R.id.vehicle_no);
            }
        }
    }


    private void cultureType(){

        try {
            cultureTypeService = new CulturalType().execute().get();
            culturalTypeJsonObject = new JSONObject(cultureTypeService);

            Log.d("culturalTypeService",""+culturalTypeJsonObject);
            String totalString = culturalTypeJsonObject.getString("Data");
            JSONArray jsonArray = new JSONArray(totalString);

            if (jsonArray.length() > 0){
                for (int i = 0;i < jsonArray.length();i++){
                    culturalTypeLoopJsonObject =jsonArray.getJSONObject(i);

                    String Fishculture_cd = culturalTypeLoopJsonObject.getString("Fishculture_cd");
                    String Fishculture_Name = culturalTypeLoopJsonObject.getString("Fishculture_Name");

//                    cultureType_list.add(culturalTypeLoopJsonObject.getString(Fishculture_Name));
                    cultureType_list.add(Fishculture_Name);
                    cultureTypeAdap = new ArrayAdapter<String>(
                            getApplicationContext(), android.R.layout.simple_list_item_activated_1,
                            cultureType_list);
                    mculture_type_selection.setAdapter(cultureTypeAdap);

                    mculture_type_selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position > 0){
                                mseasonlity_selection.setSelection(0);
                                mseasonal_layout.setVisibility(View.VISIBLE);
                            }else {
                                mseasonal_layout.setVisibility(View.GONE);
                            }
                            mprimary_mandal_layout.setVisibility(View.GONE);
                            mprimary_village_layout.setVisibility(View.GONE);
                            mintended_layout.setVisibility(View.GONE);
                            msupplier_layout.setVisibility(View.GONE);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
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

    private void callFinYear(){
//        String finYearService;

        try {
            finYearService = new FinancialYear().execute().get();

            Log.d("fin service response",""+finYearService);
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

                    finYear_list.add(finYearLoopJsonObject.getString("Year_Desc"));
                    finYearAdap = new ArrayAdapter<String>(
                            getApplicationContext(), android.R.layout.simple_list_item_activated_1,
                            finYear_list);
                    mfin_year_selection.setAdapter(finYearAdap);

                    mfin_year_selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position > 0){
                                mseasonlity_selection.setSelection(0);
                                mseasonal_layout.setVisibility(View.VISIBLE);
                            }else {
                                mseasonal_layout.setVisibility(View.GONE);
                            }
                            mprimary_mandal_layout.setVisibility(View.GONE);
                            mprimary_village_layout.setVisibility(View.GONE);
                            mintended_layout.setVisibility(View.GONE);
                            msupplier_layout.setVisibility(View.GONE);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
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

    private void callseasonality(){
        try {
            seasonalService = new SeasonalType().execute().get();

            Log.d("seasonalService response",""+seasonalService);
            seasonalJsonObject = new JSONObject(seasonalService);

            Log.d("seasonalJsonObject",""+seasonalJsonObject);
            String totalString = seasonalJsonObject.getString("Data");
            JSONArray jsonArray = new JSONArray(totalString);

            if (jsonArray.length() > 0){
                for (int i = 0;i < jsonArray.length();i++){
                    seasonalLoopJsonObject =jsonArray.getJSONObject(i);

                    String SNo = seasonalLoopJsonObject.getString("SNo");
                    String Seasionality = seasonalLoopJsonObject.getString("Seasionality");

                    seasonalSelection_list.add(seasonalLoopJsonObject.getString("Seasionality"));
                    seasonalAdap = new ArrayAdapter<String>(
                            getApplicationContext(), android.R.layout.simple_list_item_activated_1,
                            seasonalSelection_list);
                    mseasonlity_selection.setAdapter(seasonalAdap);

                    mseasonlity_selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position > 0){
                                mseasonlity_selection.setSelection(0);
                                mseasonal_layout.setVisibility(View.VISIBLE);
                            }else {
                                mseasonal_layout.setVisibility(View.GONE);
                            }
                            mprimary_mandal_layout.setVisibility(View.GONE);
                            mprimary_village_layout.setVisibility(View.GONE);
                            mintended_layout.setVisibility(View.GONE);
                            msupplier_layout.setVisibility(View.GONE);
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> parent) {

                        }
                    });
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

    public class FinancialYear extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(Utility.NAMESPACE, Utility.METHODNAME_FinYearMST);

            request.addProperty("WS_UserName",Utility.wsusername);
            request.addProperty("WS_Password",Utility.wspassword);
            request.addProperty("MobileVersion",Utility.getVersionNameCode(RemodeledRecycleActivity.this));

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(Utility.URL);
            try {
                transport.call(Utility.SOAPaCTION_FinYearMST,envelope);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            SoapObject response = (SoapObject) envelope.bodyIn;
            SoapPrimitive result = (SoapPrimitive) response.getProperty("FinYearMSTResult");
            Log.d("result_financial",""+result);
            finYearService = result.toString();
            return result.toString();
        }
    }

    public class CulturalType extends AsyncTask<String,String,String>{

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(Utility.NAMESPACE, Utility.METHODNAME_CultureTypeMST);
            request.addProperty("WS_UserName",Utility.wsusername);
            request.addProperty("WS_Password",Utility.wspassword);
            request.addProperty("MobileVersion",Utility.getVersionNameCode(RemodeledRecycleActivity.this));

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(Utility.URL);
            try {
                transport.call(Utility.SOAPaCTION_CultureTypeMST,envelope);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            SoapObject response = (SoapObject) envelope.bodyIn;
            SoapPrimitive result = (SoapPrimitive) response.getProperty("CultureTypeMstResult");
            Log.d("result_culture",""+result);
            cultureTypeService = result.toString();
            Log.d("cultureTypeServiceReturn",cultureTypeService);
            return result.toString();
        }
    }

    public class SeasonalType extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(Utility.NAMESPACE, Utility.METHODNAME_SeasonalTypeMST);
            request.addProperty("WS_UserName",Utility.wsusername);
            request.addProperty("WS_Password",Utility.wspassword);
            request.addProperty("MobileVersion",Utility.getVersionNameCode(RemodeledRecycleActivity.this));

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(Utility.URL);
            try {
                transport.call(Utility.SOAPaCTION_SeasonalTypeMST,envelope);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            SoapObject response = (SoapObject) envelope.bodyIn;
            SoapPrimitive result = (SoapPrimitive) response.getProperty("SeasonalityMstResult");
            Log.d("result_Seasonal",""+result);
            seasonalService = result.toString();
            Log.d("SeasonalityMstResult",seasonalService);
            return result.toString();
        }
    }

}
