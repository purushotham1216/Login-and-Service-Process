package com.mine.alertadddelete.modifiedscreen;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.AttributeSet;
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
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.mine.alertadddelete.R;
import com.mine.alertadddelete.ws_module.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class Direct extends AppCompatActivity {

    /*DatabaseStore db;
    LinearLayout mseasonal_layout,mmandal_layout,mintended_layout,mintended_body_layout
            ,mintended_supplier_layout,msupplier_layout,msupplier_seed_layout,mintended_leftover_layout,
            mprimary_mandal_layout,mprimary_village_layout,mfinancial_layout,mcultural_type_layout;

    Spinner mculture_type_selection,mfin_year_selection,mseasonlity_selection,mprimary_mandal_selection,
            mprimary_village_selection,mintended_water_body_selection,mintended_suppier_selection;

    ImageView mimageView;
    final int REQUEST_CODE_GALLERY = 999;
    ArrayList<String> imageUris;
    ArrayList<String> finYear_list;
    ArrayList<String> cultureType_list;
    ArrayList<String> seasonalSelection_list;
    ArrayList<String> mandalSelection_list;
    ArrayList<String> villageSelection_list;
    ArrayList<String> waterBody_list;
    ArrayList<String> supplier_list;
    ArrayList<String> finYearCode_list,cultureTypeCode_list,seasonalSelectionCode_list,districtSelectionCode_list,mandalSelectionCode_list,villageSelectionCode_list,
            waterBodyCode_list,supplierCode_list;

    Button malert_btn,mimage_picking_btn;
    EditText mfingerlings_no,mtotal_kgs,mvehicleno,msupplier_seed,mleftover_seed;

    //    Service mapping values
    JSONObject finYearJsonObject,culturalTypeJsonObject,seasonalJsonObject,mandalJsonObject,villageJsonObject,
            waterBodyJsonObject,supplierJsonObject;
    JSONObject finYearLoopJsonObject,culturalTypeLoopJsonObject,seasonalLoopJsonObject,mandalLoopJsonObject,villageLoopJsonObject,
            waterBodyLoopJsonObject,supplierLoopJsonObject;
    ArrayAdapter finYearAdap,cultureTypeAdap,seasonalAdap,mandalAdap,villageAdap,waterBodyAdap,supplierAdap;
    String finYearService,cultureTypeService,seasonalService,mandalService,villageService,waterBodyService,supplierService;

    PojoClass pojoClass;
    String str;
    String discode = "01";

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modified_seed_stocking);

        imageUris = new ArrayList<>();
        pojoClass = new PojoClass();

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

        malert_btn = findViewById(R.id.alert_btn);

//        cultureType();
        callCultureType();

    }

    private void callCultureType() {

        progressDialog = new ProgressDialog(Direct.this, R.style.Theme_AppCompat_Light_Dialog_Alert);
        progressDialog.setMessage("Please wait while submitting data...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();
        new CulturalType(Direct.this,"CultureTypeMst").execute();

    }


    public static class CulturalType extends AsyncTask<String, Void, String> {

        Utility utility;

        private Direct direct;
        private String activityVal = "0";
        private final static String TAG = CulturalType.class.getSimpleName();

        public CulturalType(Direct activity, String val) {
            this.direct = activity;
            activityVal = val;
        }



        *//*
            private AfterLoginDetails activityProfileInfoVolunteer;

            public GetMasterData(AfterLoginDetails activity, String val) {
                this.activityProfileInfoVolunteer = activity;
                activityVal = val;
            }
        *//*
        @Override
        protected void onPreExecute() {
            super.onPreExecute();

//        if (activityVal.equalsIgnoreCase(Utility.gender)) {
            // Check network available.
            if (!Utility.isNetworkAvailable(direct)) {
                utility.onError("Network error");
            }
//        }

        }

        @Override
        protected String doInBackground(String... strings) {

            SoapObject request = null;
            String returnSoapObj = null;

            *//*if (activityVal.equalsIgnoreCase(Utility.gender))*//*
            request = new SoapObject(Utility.NAMESPACE, Utility.METHODNAME_CultureTypeMST);


            request.addProperty("WS_UserName", "" + Utility.wsusername);
            request.addProperty("WS_Password", "" + Utility.wspassword);
            request.addProperty("MobileVersion", "" + Utility.getVersionNameCode(direct));
//        request.addProperty("GetSheepGroundState", Utility.OPERATION_NAME_GetDistMst_Data);

            String Url = Utility.URL;

//        if (activityVal.equalsIgnoreCase("GetDistMst_Data"))
//        if (activityVal.equalsIgnoreCase("District") )
//            Url = Utility.Url;

            if (Utility.showLogs == 0)
                Log.d(TAG, "request: " + request);

//        if (activityVal.equalsIgnoreCase(Utility.gender))
//        if (activityVal.equalsIgnoreCase("District"))
            returnSoapObj = getXMLResult(request, Url,
                    "" + Utility.SOAPaCTION_CultureTypeMST);

        *//*else if (activityVal.equalsIgnoreCase(Utility.mandStr))
            returnSoapObj = getXMLResult(request, Url,
                    "" + Utility.SOAP_ACTION_GetMandMst_Data);*//*


            return returnSoapObj;
        }

        *//**
         * get service result and catching exceptions
         *
         * @param request
         * @param url
         * @param soapAction
         * @return
         *//*
        protected String getXMLResult(SoapObject request, String url, String soapAction) {
            try {
                return getServiceResult(url, soapAction, request);
            } catch (SoapFault e) {
                if (Utility.showLogs == 0)
                    e.printStackTrace();
                return null;
            } catch (XmlPullParserException e) {
                if (Utility.showLogs == 0)
                    e.printStackTrace();
                return null;
            } catch (IOException e) {
                if (Utility.showLogs == 0)
                    e.printStackTrace();
                return null;
            } catch (Exception e) {
                if (Utility.showLogs == 0)
                    e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);

            Log.d("response", "" + response);
            if (response == null) {

                utility.onError("Getting Data Error");
            } *//*else if (activityVal.equalsIgnoreCase(Utility.distStr) *//**//*||
                activityVal.equalsIgnoreCase(Utility.mandStr)*//**//*) {
//        } else if (activityVal.equalsIgnoreCase("District") ) {

            mainActivity.parsingGenderDataResp(response);

        }*//* else {

//            mainActivity.parsingGenderDataResp(response);
//            mainActivity.onError("Getting Data Error");
            }
        }

        *//**
         * Get result (list of soap objects) from web service
         *
         * @param strURL
         * @param strSoapAction
         * @param request
         * @return
         * @throws XmlPullParserException
         * @throws IOException
         *//*
        public static String getServiceResult(String strURL, String strSoapAction, SoapObject request)
                throws XmlPullParserException, IOException {
            // Create envelope
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
            envelope.dotNet = true;

            // Set output SOAP object
            envelope.setOutputSoapObject(request);

            // Create HTTP call object
            HttpTransportSE androidHttpTransport = new HttpTransportSE(strURL);
            androidHttpTransport.debug = true;

            SoapObject response;

            String response1;

            // StringBuffer result = null;

            System.setProperty("http.keepAlive", "false");

            try {
                // Invoke web service
                androidHttpTransport.call(strSoapAction, envelope);

                // Get the response
                response1 = envelope.getResponse().toString();
           *//*response = (SoapObject) envelope.getResponse();
           StringBuffer result;
           result = new StringBuffer(response.toString());*//*

                if (Utility.showLogs == 0)
                    Log.d(TAG, response1);

            } catch (SoapFault e) {
                if (Utility.showLogs == 0)
                    Log.e(TAG, "SoapFaultException");
                throw e;
            } catch (XmlPullParserException e) {
                if (Utility.showLogs == 0)
                    Log.e(TAG, "XmlPullParserException");
                throw e;
            } catch (IOException e) {
                if (Utility.showLogs == 0)
                    Log.e(TAG, "IOException");
                throw e;
            } catch (Exception e) {
                if (Utility.showLogs == 0)
                    Log.e(TAG, "HttpResponseException");
                throw e;
            }
            return response1;
        }

    }*/

    private static final String TAG = "SriRam -" + Direct.class.getName();

    View stockView;
    @BindView(R.id.culture_type_selection)
    Spinner spCulture;
    @BindView(R.id.fin_year_selection)
    Spinner spFinYear;

    Unbinder unbinder;

    PojoClass pojoClass;

    ArrayList<PojoClass> cultureObjList = new ArrayList<>();
    List<PojoClass> finYearObjArrayList = new ArrayList<>();

   SpCulturalAdapter spCulturalAdapter;
//    SpFinYearAdapter spFinYearAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modified_seed_stocking);

        pojoClass = new PojoClass();
        new GetCulture().execute();
    }

    /*@Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        stockView = inflater.inflate(R.layout.activity_modified_seed_stocking,container,false);
        unbinder = ButterKnife.bind(this,stockView);

        new GetCulture().execute();
        return stockView;
    }*/

    public class GetCulture extends AsyncTask<String,Void,String> {
        ProgressDialog loading;

       /* @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = new ProgressDialog(loading.getOwnerActivity());
            loading.setMessage("Please wait...");
            loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            loading.setCancelable(false);
            loading.show();
        }*/

        @Override
        protected String doInBackground(String... strings) {
            String jsonResp = "null";

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
//            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            MediaType mediaType = MediaType.parse("text/xml");
//            RequestBody body = RequestBody.create(JSON, "{"jsonExample":"value"}");

           /* RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("http://tempuri.org/", "CultureTypeMst")
                    .build();

            Request request = new Request.Builder()
                    .url(Utility.URL)
                    .post(requestBody)
                    .build();*/

            /*String soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    " <soap:Body>\n" + " <Login xmlns=\"http://tempuri.org/\">\n" +
                    " <Name>"+userName+"</Name>\n" + " <Password>"+password+"</Password>\n" +
                    " </Login>\n" + " </soap:Body>\n" + "</soap:Envelope>";*/

            String soap = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                    "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
                    " <soap:Body>\n" + " <CultureTypeMst xmlns=\"http://tempuri.org/\">\n" +
                    " <Name>"+Utility.wsusername+"</Name>\n" +
                    " <Password>"+Utility.wspassword+"</Password>\n" +
                    " <MobileVersion>"+Utility.getVersionNameCode(Direct.this)+"</MobileVersion>\n" +
                    " </CultureTypeMst>\n" + " </soap:Body>\n" + "</soap:Envelope>";

//            RequestBody body = RequestBody.create(JSON, soap);
            RequestBody body = RequestBody.create(mediaType, soap);
            RequestBody requestBody = new FormBody.Builder()
                    .add("wsusername", "IFDS")
                    .add("wspassword", "IFDS$Admin@123")
                    .add("MobileVersion",Utility.getVersionNameCode(Direct.this))
                    .build();

            Request request = new Request.Builder()
                    .url(Utility.URL)
//                    .post(requestBody)
                    .post(body)
                    .build();

           /* Call call = client.newCall(request);
            call.enqueue(new Callback() {
                @Override
                public void onFailure(Call call, IOException e) {

                }

                @Override
                public void onResponse(Call call, Response response) throws IOException {

                }
            });*/

            try {
                Response response = client.newCall(request).execute();

                Log.d("response",""+response);
                jsonResp = response.body().string();

                Log.d("jsonResp",jsonResp);

                Log.v("TAG", "Test Details- " + jsonResp);

                // Do something with the response.
            } catch (IOException e) {
                e.printStackTrace();
            }
            return jsonResp;
        }

        @Override
        protected void onPostExecute(String response) {
            super.onPostExecute(response);
            if (response != null) {
//                loading.dismiss();
                try {
                    JSONObject ParentjObject = new JSONObject(response);
                    JSONArray jsonArr = ParentjObject.getJSONArray("Data");

                    Gson gson = new Gson();
                    Type type = new TypeToken<List<PojoClass>>() {
                    }.getType();

                    cultureObjList.addAll(gson.fromJson(jsonArr.toString(), type));

                    Log.v(TAG, "cultureObjList -" + cultureObjList.size());

//                    spCulturalAdapter = new SpCulturalAdapter(getContext(), cultureObjList);
                    spCulturalAdapter = new SpCulturalAdapter(getApplicationContext(), cultureObjList);
                    spCulture.setAdapter(spCulturalAdapter);


                } catch (Exception e) {

                }

            }
        }
    }

    public class SpCulturalAdapter extends ArrayAdapter<PojoClass> {

        // Your sent context
        private Context context;
        // Your custom values for the spinner (User)
        private List<PojoClass> arrayList;
        // invoke the suitable constructor of the ArrayAdapter class
        public SpCulturalAdapter(@NonNull Context context, ArrayList<PojoClass> arrayList) {

            // pass the context and arrayList for the super
            // constructor of the ArrayAdapter class
            super(context, 0, arrayList);
            this.context = context;
            this.arrayList = arrayList;
        }

        @Override
        public int getCount() {
            return arrayList.size();
        }

        @Nullable
        @Override
        public PojoClass getItem(int position) {
            return arrayList.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

            TextView tv = (TextView) super.getView(position, convertView, parent);
            tv.setTextColor(Color.BLACK);
            tv.setText(arrayList.get(position).getMandalCode());
            return tv;

        }

        @Override
        public View getDropDownView(int position, View convertView, ViewGroup parent) {

            TextView tvv = (TextView) super.getDropDownView(position,convertView,parent);
            tvv.setTextColor(Color.BLACK);
            tvv.setText(arrayList.get(position).getMandalCode());
            return tvv;
        }
    }

}
