package com.mine.alertadddelete.modifiedscreen;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.mine.alertadddelete.R;
import com.mine.alertadddelete.ws_module.Utility;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ksoap2.SoapEnvelope;
import org.ksoap2.SoapFault;
import org.ksoap2.serialization.PropertyInfo;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class LoginActivity extends AppCompatActivity {

    static EditText mlogin_email;
    static EditText mlogin_pwd;
    Button mlogin_button;
    JSONObject j2,jsonObject;
    String totalString,loginService, userId;
    SharedPreferences sharePref;

    TelephonyManager telephonyManager;
    public static String imeiNo = "";
    private static final int REQUEST_PERMISSION_SETTING = 101;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        mlogin_email = findViewById(R.id.login_email);
        mlogin_pwd = findViewById(R.id.login_password);
        mlogin_button = findViewById(R.id.login_button);

        int imeiI = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_PHONE_STATE);
        if (imeiI == PackageManager.PERMISSION_GRANTED){
            telephonyManager = (TelephonyManager) getSystemService(Context.TELEPHONY_SERVICE);
            imeiNo = telephonyManager.getDeviceId();
        }else {
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.READ_PHONE_STATE},REQUEST_PERMISSION_SETTING);
        }

        mlogin_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mlogin_email.setText("dfo_adi");
                mlogin_pwd.setText("Sa@12345");
                callLoginData();
            }

        });

    }


    private void callLoginData() {

        progressDialog = new ProgressDialog(LoginActivity.this, R.style.Widget_AppCompat_SeekBar);
        progressDialog.setMessage("Please wait...");
        progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        progressDialog.setCancelable(false);
        progressDialog.show();



    }

    public void parsingLoginDataResp(SoapObject response) {

        int i = 0;
        if (Utility.showLogs == 0)
            Log.d("response: ", "response: " + response.toString());

        PropertyInfo pi = new PropertyInfo();
        response.getPropertyInfo(i, pi);
        Object property = response.getProperty(i);

        if (pi.name.equals("ValidateLogin") && property instanceof SoapObject /*||
                pi.name.equals("MandMstData") && property instanceof SoapObject ||
                pi.name.equals("VillMstData") && property instanceof SoapObject*/) {

            SoapObject s = (SoapObject) property;

            if (Utility.showLogs == 0) {
                Log.d("response: ", "SuccessFlag: " + s.getProperty("SuccessFlag").toString().trim());
                Log.d("response: ", "SuccessMsg: " + s.getProperty("SuccessMsg").toString().trim());
            }

            if (s.getProperty("SuccessFlag").toString().trim().equalsIgnoreCase("1")) {

                for (int j = 0; j < response.getPropertyCount(); j++) {

                    PropertyInfo pi1 = new PropertyInfo();
                    response.getPropertyInfo(j, pi1);
                    Object property1 = response.getProperty(j);

                    if (pi1.name.equals("ValidateLogin") && property1 instanceof SoapObject) {
                        SoapObject transDetail = (SoapObject) property1;

                        String PPBNoPrefixStr = transDetail.getProperty("PPBNO_Prefix").toString().trim();
                        if (PPBNoPrefixStr.equalsIgnoreCase("") ||
                                PPBNoPrefixStr == "" ||
                                PPBNoPrefixStr.isEmpty() ||
                                PPBNoPrefixStr.equalsIgnoreCase("anyType{}"))
                            PPBNoPrefixStr = "";

                       /* db.createVillageData(
                                "" + transDetail.getProperty("DistCode").toString().trim(),
                                "" + transDetail.getProperty("MandCode").toString().trim(),
                                "" + transDetail.getProperty("VillCode").toString().trim(),
                                "" + transDetail.getProperty("VillName").toString().trim(),
                                "" + transDetail.getProperty("VillName_Tel").toString().trim(),
                                "" + transDetail.getProperty("ClusterCode").toString().trim(),
                                "" + transDetail.getProperty("ClusterName").toString().trim(),
                                "" + transDetail.getProperty("V_LgCode").toString().trim()

                        );*/
                    }
                }

//                progressDialog.dismiss();

                new CountDownTimer(500, 500) {
                    //   getFarmerDetailsCropSurvey();


                    @Override
                    public void onTick(long millisUntilFinished) {

//                        progressDialog.dismiss();
                    }

                    @Override
                    public void onFinish() {

                        progressDialog.dismiss();


                    }
                }.start();

            }
        }
        class Loginn extends AsyncTask<String, Void, SoapObject> {

        LocalService localService;
        private LoginActivity loginActivity;
        private String activityVal = "0";
        private final String TAG = Loginn.class.getSimpleName();

        public Loginn(LoginActivity activity, String val) {
            this.loginActivity = activity;
            activityVal = val;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();

            if (activityVal.equalsIgnoreCase("District")||
                    activityVal.equalsIgnoreCase("Mandal")||
                    activityVal.equalsIgnoreCase("Village")) {
                // Check network available.
                if (!Utility.isNetworkAvailable(loginActivity)) {
                    localService.onError("Network error");
                }
            }
        /*else if (activityVal==1) {
            // Check network available.
            if (!Utility.isNetworkAvailable(activityRegistration)) {
                activityRegistration.onError("Network error");
            }
        }else if (activityVal==4) {
            // Check network available.
            if (!Utility.isNetworkAvailable(activityCropSurveyNavigationMenu)) {
                activityCropSurveyNavigationMenu.onError("Network error");
            }
        }else{
            // Check network available.
            if (!Utility.isNetworkAvailable(activityGiveItUp)) {
                activityGiveItUp.onError("Network error");
            }
        }*/
        }

        @Override
        protected SoapObject doInBackground(String... strings) {

            SoapObject request = null;
            SoapObject returnSoapObj = null;

       /* if (activityVal.equalsIgnoreCase("District"))
            request = new SoapObject(Utility.WSDL_TARGET_NAMESPACE, Utility.OPERATION_NAME_GetDistMst_Data);
        else if (activityVal.equalsIgnoreCase("Mandal"))
            request = new SoapObject(Utility.WSDL_TARGET_NAMESPACE, Utility.OPERATION_NAME_GetMandMst_Data);
        else *//*if (activityVal.equalsIgnoreCase("ValidateLogin"))
                request = new SoapObject(Utility.NAMESPACE, Utility.METHODNAME_ValidateLogin);*/

            if (activityVal.equalsIgnoreCase("ValidateLogin")){
                request = new SoapObject(Utility.NAMESPACE, Utility.METHODNAME_ValidateLogin);
                request.addProperty("UserName",mlogin_email);
                request.addProperty("Pwd",mlogin_pwd);
                request.addProperty("IMEI_No",imeiNo);
                request.addProperty("WS_UserName",Utility.wsusername);
                request.addProperty("WS_Password",Utility.wspassword);
                request.addProperty("MobileVersion",Utility.getVersionNameCode(LoginActivity.this));
            }

            String Url = Utility.URL;

            Url = Utility.URL;

            if (Utility.showLogs == 0)
                Log.d(TAG, "request: " + request);

            if (activityVal.equalsIgnoreCase("ValidateLogin"))
                returnSoapObj = getXMLResult(request, Url,
                        "" + Utility.SOAPaCTION_ValidateLogin);
           /* else if (activityVal.equalsIgnoreCase("Mandal"))
                returnSoapObj = getXMLResult(request, Url,
                        "" + Utility.SOAP_ACTION_GetMandMst_Data);
            else if (activityVal.equalsIgnoreCase("Village"))
                returnSoapObj = getXMLResult(request, Url,
                        "" + Utility.SOAP_ACTION_GetVillMst_Data);*/


            return returnSoapObj;

        }

        /**
         * get service result and catching exceptions
         *
         * @param request
         * @param url
         * @param soapAction
         * @return
         */
        protected SoapObject getXMLResult(SoapObject request, String url, String soapAction) {
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
        protected void onPostExecute(SoapObject response) {
            super.onPostExecute(response);

            if (response == null) {
                if (activityVal.equalsIgnoreCase("ValidateLogin") /*|| activityVal.equalsIgnoreCase("Mandal")
                        || activityVal.equalsIgnoreCase("Village") || activityVal.equalsIgnoreCase("OSSDS_Crop")
                        || activityVal.equalsIgnoreCase("FinYearMst")*/)
                    localService.onError("Getting Data Error");
            /*else  if (activityVal==1)
                activityRegistration.onError("Getting Data Error");

            else  if (activityVal==4)
                activityCropSurveyNavigationMenu.onError("Getting Data Error");
            else
                activityGiveItUp.onError("Getting Data Error");*/
            }  if (response.hasProperty("ValidateLogin")) {
                loginActivity.parsingLoginDataResp(response);
            }  else {
                if (activityVal.equalsIgnoreCase("ValidateLogin")/* || activityVal.equalsIgnoreCase("Mandal")
                        || activityVal.equalsIgnoreCase("Village"))*/)
                localService.onError("Data Not Found");

            }
        }

        /**
         * Get result (list of soap objects) from web service
         *
         * @param strURL
         * @param strSoapAction
         * @param request
         * @return
         * @throws XmlPullParserException
         * @throws IOException
         */
        public  SoapObject getServiceResult(String strURL, String strSoapAction, SoapObject request)
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
                response = (SoapObject) envelope.getResponse();
                StringBuffer result;
                result = new StringBuffer(response.toString());

                if (Utility.showLogs == 0)
                    Log.d(TAG, result.toString());

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
            return response;
        }
    }
   /* public class LoginService extends AsyncTask<String,String,String> {

        @Override
        protected String doInBackground(String... strings) {

            SoapObject request = new SoapObject(Utility.NAMESPACE, Utility.METHODNAME_ValidateLogin);
            request.addProperty("UserName",mlogin_email);
            request.addProperty("Pwd",mlogin_pwd);
            request.addProperty("IMEI_No",imeiNo);
            request.addProperty("WS_UserName",Utility.wsusername);
            request.addProperty("WS_Password",Utility.wspassword);
            request.addProperty("MobileVersion",Utility.getVersionNameCode(LoginActivity.this));

            SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER10);

            envelope.dotNet = true;
            envelope.setOutputSoapObject(request);

            HttpTransportSE transport = new HttpTransportSE(Utility.URL);

            try {

                transport.call(Utility.SOAPaCTION_ValidateLogin,envelope);

            } catch (IOException e) {

                e.printStackTrace();
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            }
            SoapObject response = (SoapObject) envelope.bodyIn;
            Log.d("response",""+response);
            SoapPrimitive result = (SoapPrimitive) response.getProperty("ValidateLoginResult");
            Log.d("result_culture",""+result);
            loginService = result.toString();
            Log.d("ValidateLoginResult",loginService);
            return result.toString();
        }
    }*/
}
}