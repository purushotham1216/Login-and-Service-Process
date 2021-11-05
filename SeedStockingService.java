package com.mine.alertadddelete.modifiedscreen;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.snackbar.Snackbar;
import com.mine.alertadddelete.R;
import com.mine.alertadddelete.RecyclerAdapter;
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

import static android.content.ContentValues.TAG;

public class SeedStockingService extends AppCompatActivity {

    DatabaseStore db;
    ProgressDialog progressDialog;
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

    final int SELECT_PICTURE  = 999;
    ArrayList<String> imageUris;

    //    Service mapping values
    JSONObject finYearJsonObject,culturalTypeJsonObject,seasonalJsonObject,mandalJsonObject,villageJsonObject,
            waterBodyJsonObject,supplierJsonObject;
    JSONObject finYearLoopJsonObject,culturalTypeLoopJsonObject,seasonalLoopJsonObject,mandalLoopJsonObject,villageLoopJsonObject,
            waterBodyLoopJsonObject,supplierLoopJsonObject;
    ArrayList<String> finYear_list,cultureType_list,seasonalSelection_list,mandalSelection_list,mandalSelectionCode_list,villageSelection_list,
            waterBody_list,supplier_list;
    ArrayAdapter finYearAdap,cultureTypeAdap,seasonalAdap,mandalAdap,villageAdap,waterBodyAdap,supplierAdap;

    String finYearService,cultureTypeService,seasonalService,mandalService,villageService,waterBodyService,supplierService;

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
        mandalSelection_list = new ArrayList<>();
        seasonalSelection_list = new ArrayList<>();
        villageSelection_list = new ArrayList<>();


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
                ActivityCompat.requestPermissions(SeedStockingService.this,new String[]
                        {Manifest.permission.READ_EXTERNAL_STORAGE},SELECT_PICTURE );

//                Will go to the gallery directly to pick the image
                Intent gallery = new Intent(Intent.ACTION_GET_CONTENT);
                gallery.setType("image/*");
                startActivityForResult(gallery, SELECT_PICTURE );

//         ImagePicking will go for all the gallery contents and will provide the option to get selected
                imagePicking();
//                pickImage();

            }
        });

        db = new DatabaseStore(SeedStockingService.this);

        if (db.getMandalCount() == 0){

        }
        inItViews();
        callFinYear();
        cultureType();
        callMandal();
        callseasonality();
        callMandal();
//        callVillage();
    }

    private  void imagePicking(){
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, SELECT_PICTURE );
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE ) {
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    mimageView.setImageURI(selectedImageUri);
                }
            }
        }

        /*@Override
        protected void onActivityResult(int requestCode, int resultCode, Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            if (requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK) {
                Uri imageUri = data.getData();
                mimageView.setImageURI(imageUri);
            }
        }*/
    }


    private void inItViews(){
        rv = findViewById(R.id.recyclerview);
        rv.setHasFixedSize(true);
    }

    private void prepareRCItems(){
        recyclerAdapter = new SeedStockingService.RecyclerAdapter(SeedStockingService.this,rlist);
        rv.setLayoutManager(new LinearLayoutManager(SeedStockingService.this));
        rv.setAdapter(recyclerAdapter);
    }

    public void alert_popup(){

        Button mclose_btn,msubmit_btn;

        final  AlertDialog.Builder alert_dialog = new AlertDialog.Builder(SeedStockingService.this);
        alert_dialog.setCancelable(false);
        LayoutInflater inflater = SeedStockingService.this.getLayoutInflater();
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

        /*mfingerlings_no.addTextChangedListener(textWatcher);
        mtotal_kgs.addTextChangedListener(textWatcher);*/
        mtotal_fingerlings.setText("");

//        fishDetails(mspecies_selection);

       /* mseasonlity_selection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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
        });*/

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
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void callMandal(){
        try {
            mandalService = new Mandal().execute().get();

            Log.d("mandalService response",""+mandalService);
            mandalJsonObject = new JSONObject(mandalService);

            Log.d("mandalJsonObject",""+mandalJsonObject);
            String totalString = mandalJsonObject.getString("Data");
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

                    /*mseasonlity_selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                            if (position > 0){
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
                    });*/
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

    private void callVillage(){
        try {
            villageService = new Village().execute().get();

            Log.d("villageService response",""+villageService);
            villageJsonObject = new JSONObject(villageService);

            Log.d("villageJsonObject",""+villageJsonObject);
            String totalString = villageJsonObject.getString("Data");
            JSONArray jsonArray = new JSONArray(totalString);

            if (jsonArray.length() > 0){
                for (int i = 0;i < jsonArray.length();i++){
                    villageLoopJsonObject =jsonArray.getJSONObject(i);

                    String DistCode = villageLoopJsonObject.getString("DistCode");
                    String MandCode = villageLoopJsonObject.getString("MandCode");
                    String VillCode = villageLoopJsonObject.getString("VillCode");
                    String VillName = villageLoopJsonObject.getString("VillName");


                    villageSelection_list.add(villageLoopJsonObject.getString("VillName"));
                    villageAdap = new ArrayAdapter<String>(
                            getApplicationContext(), android.R.layout.simple_list_item_activated_1,
                            villageSelection_list);
                    mprimary_village_selection.setAdapter(villageAdap);

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
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void callWaterBody(){
        try {
            waterBodyService = new WaterBody().execute().get();

            Log.d("waterBodyService response",""+waterBodyService);
            waterBodyJsonObject = new JSONObject(waterBodyService);

            Log.d("waterBodyJsonObject",""+waterBodyJsonObject);
            String totalString = waterBodyJsonObject.getString("Data");
            JSONArray jsonArray = new JSONArray(totalString);

            if (jsonArray.length() > 0){
                for (int i = 0;i < jsonArray.length();i++){
                    villageLoopJsonObject =jsonArray.getJSONObject(i);

                    String DistCode = waterBodyLoopJsonObject.getString("DistCode");
                    String MandCode = waterBodyLoopJsonObject.getString("MandCode");
                    String VillCode = waterBodyLoopJsonObject.getString("VillCode");
                    String VillName = waterBodyLoopJsonObject.getString("VillName");


                    waterBody_list.add(waterBodyLoopJsonObject.getString("VillName"));
                    waterBodyAdap = new ArrayAdapter<String>(
                            getApplicationContext(), android.R.layout.simple_list_item_activated_1,
                            waterBody_list);
                    mintended_water_body_selection.setAdapter(waterBodyAdap);

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
            }
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }


    private void assignMandSpin(){
        mandalSelection_list = new ArrayList<>();
        mandalSelection_list.add("<--Select-->");

        mandalSelectionCode_list = new ArrayList<>();
        mandalSelectionCode_list.add("0");

        ArrayList<PojoClass> stringArrayList = db.getMandData("01");

        for (int i=0;i<stringArrayList.size();i++){
            mandalSelection_list.add(stringArrayList.get(i).getDistName());
            mandalSelectionCode_list.add(stringArrayList.get(i).getDistCode());
        }

        ArrayAdapter<String> distAdapter = new ArrayAdapter<String>(getApplicationContext(),
                android.R.layout.simple_dropdown_item_1line,mandalSelection_list);
        distAdapter.setDropDownViewResource(android.R.layout.simple_list_item_activated_1);
        mprimary_mandal_selection.setAdapter(distAdapter);
        /*mprimary_mandal_selection.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                if (position > 0) {

                    if (mandalSelection_list.get(position).toString().equalsIgnoreCase("mandalSelectionCode_list"))
                        assignMandSpin();


//                    mand_list.get(Integer.parseInt(mandCode_list.get(position)));

                   *//*else {
                       mand_list.get(Integer.parseInt(distCode_list.get(position)));
                    }
*//*
                    if (Utility.showLogs == 0) {
                        Log.d(TAG, "distCode: " +  mandalSelectionCode_list.get(position));

                    }

                } else {

                    *//*if (dist_list.get(position).toString().equalsIgnoreCase("DistCode")){
                        assignMandSpin(mand_list.get(Integer.parseInt(distCode_list.get(position).toString())));
                    }*//*

                }

            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });*/
    }

    public class FinancialYear extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(Utility.NAMESPACE, Utility.METHODNAME_FinYearMST);

            request.addProperty("WS_UserName",Utility.wsusername);
            request.addProperty("WS_Password",Utility.wspassword);
            request.addProperty("MobileVersion",Utility.getVersionNameCode(SeedStockingService.this));

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
            request.addProperty("MobileVersion",Utility.getVersionNameCode(SeedStockingService.this));

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
            request.addProperty("MobileVersion",Utility.getVersionNameCode(SeedStockingService.this));

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

    public class Mandal extends AsyncTask<String,String,String> {

        Utility utility = new Utility();
        /*@Override
        protected void onPreExecute() {
            super.onPreExecute();

            progressDialog = new ProgressDialog(SeedStockingService.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
            progressDialog.setMessage("Please wait while submitting data...");
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setCancelable(false);
            progressDialog.setCanceledOnTouchOutside(true);
            progressDialog.show();
            if (!Utility.isNetworkAvailable(SeedStockingService.this)) {
                utility.onError("Network error");
            }
        }*/

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(Utility.NAMESPACE, Utility.METHODNAME_MandalMST);
            request.addProperty("WS_UserName",Utility.wsusername);
            request.addProperty("WS_Password",Utility.wspassword);
            request.addProperty("MobileVersion",Utility.getVersionNameCode(SeedStockingService.this));

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(Utility.URL);
            try {
                transport.call(Utility.SOAPaCTION_MandalMST,envelope);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            SoapObject response = (SoapObject) envelope.bodyIn;
            SoapPrimitive result = (SoapPrimitive) response.getProperty("MandalMasterResult");
            Log.d("result_Mandal",""+result);
            mandalService = result.toString();
            Log.d("MandalMasterResult",mandalService);
            return result.toString();
        }
    }

    public class Village extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(Utility.NAMESPACE, Utility.METHODNAME_VillageMST);
            request.addProperty("WS_UserName",Utility.wsusername);
            request.addProperty("WS_Password",Utility.wspassword);
            request.addProperty("MobileVersion",Utility.getVersionNameCode(SeedStockingService.this));

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(Utility.URL);
            try {
                transport.call(Utility.SOAPaCTION_VillageMST,envelope);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            SoapObject response = (SoapObject) envelope.bodyIn;
            SoapPrimitive result = (SoapPrimitive) response.getProperty("VillageMasterResult");
            Log.d("result_Village",""+result);
            villageService = result.toString();
            Log.d("VillageMasterResult",villageService);
            return result.toString();
        }
    }

    public class WaterBody extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {
            SoapObject request = new SoapObject(Utility.NAMESPACE, Utility.METHODNAME_WaterbodyMst);
            request.addProperty("WS_UserName",Utility.wsusername);
            request.addProperty("WS_Password",Utility.wspassword);
            request.addProperty("MobileVersion",Utility.getVersionNameCode(SeedStockingService.this));

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(Utility.URL);
            try {
                transport.call(Utility.SOAPaCTION_WaterbodyMst,envelope);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            SoapObject response = (SoapObject) envelope.bodyIn;
            SoapPrimitive result = (SoapPrimitive) response.getProperty("WaterbodyMstResult");
            Log.d("result_waterBody",""+result);
            waterBodyService = result.toString();
            Log.d("WaterbodyMstResult",waterBodyService);
            return result.toString();
        }
    }

    public class RecyclerAdapter extends RecyclerView.Adapter {
        public RecyclerAdapter(SeedStockingService seedStockingService, ArrayList<SetMethods> rlist) {
        }

        @Override
        public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            return null;
        }

        @Override
        public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        }

        @Override
        public int getItemCount() {
            return 0;
        }
    }
}
