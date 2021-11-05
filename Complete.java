package com.mine.alertadddelete.modifiedscreen;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import org.json.JSONObject;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static androidx.constraintlayout.motion.utils.Oscillator.TAG;

public class Complete extends Fragment {
    private static final String TAG = "SriRam -" + Complete.class.getName();

    View stockView;
    @BindView(R.id.culture_type_selection)
            Spinner spCulture;
    @BindView(R.id.fin_year_selection)
            Spinner spFinYear;

    Unbinder unbinder;

    PojoClass pojoClass;

    public Complete() {
        // Required empty public constructor
    }

    List<PojoClass> cultureObjList = new ArrayList<>();
    List<PojoClass> finYearObjArrayList = new ArrayList<>();

    SpCulturalAdapter spCulturalAdapter;
//    SpFinYearAdapter spFinYearAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {

        stockView = inflater.inflate(R.layout.activity_modified_seed_stocking,container,false);
        unbinder = ButterKnife.bind(this,stockView);

        new GetCulture().execute();
        return stockView;
    }

    public class GetCulture extends AsyncTask<String,Void,String> {
        ProgressDialog loading;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loading = new ProgressDialog(loading.getOwnerActivity());
            loading.setMessage("Please wait...");
            loading.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            loading.setCancelable(false);
            loading.show();
        }

        @Override
        protected String doInBackground(String... strings) {
            String jsonResp = "null";

            OkHttpClient client = new OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .writeTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(30, TimeUnit.SECONDS)
                    .build();
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");

            Request request = new Request.Builder()
                    .url(Utility.URL)
                    .build();

            try {
                Response response = client.newCall(request).execute();
                jsonResp = response.body().string();

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
                loading.dismiss();
                try {
                    JSONObject ParentjObject = new JSONObject(response);
                        JSONArray jsonArr = ParentjObject.getJSONArray("Data");

                        Gson gson = new Gson();
                        Type type = new TypeToken<List<PojoClass>>() {
                        }.getType();

                        cultureObjList.addAll(gson.fromJson(jsonArr.toString(), type));

                        Log.v(TAG, "cultureObjList -" + cultureObjList.size());

                        spCulturalAdapter = new SpCulturalAdapter(getContext(), cultureObjList);
                        spCulture.setAdapter(spCulturalAdapter);


                } catch (Exception e) {

                }

            }
        }
    }

    public static class SpCulturalAdapter extends ArrayAdapter<PojoClass> {

        // Your sent context
        private Context context;
        // Your custom values for the spinner (User)
        private List<PojoClass> arrayList;
        // invoke the suitable constructor of the ArrayAdapter class
        public SpCulturalAdapter(@NonNull Context context, List<PojoClass> arrayList) {

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
