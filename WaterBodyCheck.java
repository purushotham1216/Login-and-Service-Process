package com.mine.alertadddelete.modifiedscreen;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.widget.Spinner;

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
import java.util.concurrent.ExecutionException;

public class WaterBodyCheck extends AppCompatActivity {

    Spinner mintended_water_body_selection;
    JSONObject waterBodyJsonObject,waterBodyJsonObjectLoop;
    String waterBodyTypeService;

    DatabaseStore db;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_water_body_check);

        mintended_water_body_selection = findViewById(R.id.intended_water_body_selection);
        cultureType();
    }
    private void cultureType(){

        try {
            Log.i("Initial","Initial");
            waterBodyTypeService = new CulturalType().execute().get();
            waterBodyJsonObject = new JSONObject(waterBodyTypeService);

            Log.i("waterBodyTypeService",""+waterBodyTypeService);
            String totalString = waterBodyJsonObject.getString("Data");
            JSONArray jsonArray = new JSONArray(totalString);

            if (jsonArray.length() > 0){
                for (int i = 0;i < jsonArray.length();i++){
                    waterBodyJsonObjectLoop =jsonArray.getJSONObject(i);

                    Log.d("jsonArray",""+jsonArray);

                    /*db.insertCultureType(""+jsonArray.getJSONObject(i).getString("Fishculture_cd"),
                            ""+jsonArray.getJSONObject(i).getString("Fishculture_Name"));*/

                    /*db.insertCultureType(""+Fishculture_cd,
                            ""+Fishculture_Name);*/

                }
                /*if (db.getFinancialCount() == 0){
                    finYearType();
                }*/
//                callingMst();

            }

        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
//        callingMst();
//        finYearType();
    }

    public class CulturalType extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {

            SoapObject request = new SoapObject(Utility.NAMESPACE, Utility.METHODNAME_WaterbodyMst);
            request.addProperty("Distcode","01");
            request.addProperty("Mandalcd","01");
            request.addProperty("villcode","015");
            request.addProperty("Seasonnalitycd","01");
            request.addProperty("year","2021-2022");
            request.addProperty("CultureType","01");
            request.addProperty("WS_UserName",Utility.wsusername);
            request.addProperty("WS_Password",Utility.wspassword);
            request.addProperty("MobileVersion",Utility.getVersionNameCode(WaterBodyCheck.this));
            Log.d("aredqw","aftfedr");
            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);
            Log.d("envelope",""+envelope);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            Log.d("call","aftfedr");
            HttpTransportSE transport = new HttpTransportSE(Utility.URL);

            Log.d("call",""+transport);
            try {
                Log.d("Bedfolred","aftfedr");
                transport.call(Utility.SOAPaCTION_WaterbodyMst,envelope);

                Log.d("aftfedr","aftfedr");
            } catch (IOException e) {
                Log.d("eddd",""+e);
                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }catch (ClassCastException e) {
                e.printStackTrace();
            }
            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("response",""+response);
            SoapPrimitive result = (SoapPrimitive) response.getProperty("GetIndentedWaterbodyResult");
            Log.d("result",""+result);
            waterBodyTypeService = result.toString();
            Log.d("GetIndentedWaterbodyResult",waterBodyTypeService);
            return result.toString();
        }
    }
}