package com.mine.alertadddelete.modifiedscreen;

import android.content.Context;
import android.os.Bundle;
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
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mine.alertadddelete.DialogInterface;
import com.mine.alertadddelete.R;

import java.util.ArrayList;

public class RecycleViewPopUp extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modified_seed_stocking);

        initViews();

    }

//    <----------------- Start of Declaring the values to bind with id-------->

    private ArrayList<String> fishtype_list;
    Spinner mspecies_selection;
    LinearLayout mlist_module,mlist_modulee;
    private EditText mfingerlings_no,mtotal_kgs,mvehicleno;
    private TextView mtotal_fingerlings;
    String mfingerlings_no_str,mtotal_kgs_str,mtotal_fingerlings_str,mvehicleno_str,mspecies_selection_str;
    RecyclerView rv;
    RecyclerAdapter recyclerAdapter;
    ArrayList<SetMethods> rlist = new ArrayList<>();
    Button mdetail_button,malert_button;
    private DialogInterface.DialogListner listner;

//    <----------------- End of Declaring the values to bind with id-------->

//    <-------------Start of inItemsView method. Here declaring all methods and call init in onCreate------>
    private void initViews(){
        mspecies_selection = findViewById(R.id.species_selection);
        mlist_module = findViewById(R.id.list_module);
        mlist_modulee = findViewById(R.id.list_modulee);
        mfingerlings_no = findViewById(R.id.fingerlings_no);
        mtotal_kgs = findViewById(R.id.total_kgs);
        mtotal_fingerlings = findViewById(R.id.total_fingerlings);
        mvehicleno = findViewById(R.id.vehicleno);

//        mvehicleno_str = mvehicleno.getText().toString().trim();

        rv = findViewById(R.id.recyclerview);
        rv.setHasFixedSize(true);
        malert_button = findViewById(R.id.alert_btn);


        malert_button.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                alert_popup();
            }
        });

        fishDetails();
//        caluculation();
//        addValidations();
        mlist_module.setVisibility(View.GONE);
        mlist_modulee.setVisibility(View.GONE);
        mdetail_button.setVisibility(View.GONE);
//        mdetail_button.setEnabled(false);
        mtotal_fingerlings.setText("");

        mfingerlings_no.addTextChangedListener(textWatcher);
        mtotal_kgs.addTextChangedListener(textWatcher);

//        <------------ Start to show the alert ------------>

//        <------------ End to show the alert ------------>

//        <------------------Start of to Add Button Clicking ----------->

       /* mdetail_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                mspecies_selection_str = mspecies_selection.getSelectedItem().toString();
                mfingerlings_no_str = mfingerlings_no.getText().toString().trim();
                mtotal_kgs_str = mtotal_kgs.getText().toString().trim();
                mtotal_fingerlings_str = mtotal_fingerlings.getText().toString().trim();
                mvehicleno_str = mvehicleno.getText().toString().trim();

                *//*SetMethods setMethods = new SetMethods(
                        mspecies_selection.getSelectedItem().toString().trim(),
                        mfingerlings_no.getText().toString().trim(),
                        mtotal_kgs.getText().toString().trim(),
                        mtotal_fingerlings.getText().toString().trim(),
                        mvehicleno.getText().toString().trim()

                );*//*
//        dbss = new DatabaseSection(this);
//        rlist = dbss.getData();


                if (mfingerlings_no_str.isEmpty()){
                    mfingerlings_no.setError("Please enter the fingerlings");
                    Log.d("setMethods2",""+mfingerlings_no);
                }else if (mtotal_kgs_str.isEmpty()){
                    mtotal_kgs.setError("Please enter the Number of kgs");

                }else if (mvehicleno_str.isEmpty()){
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
                    if (rlist.size() == 1){
                        prepareRCItems();
                    }else {
                        recyclerAdapter.notifyItemInserted(rlist.size()-1);
                    }

                    Log.d("setMethods2",""+setMethods);
                }
                *//*rlist.add(setMethods);
                Log.d("holder cl",rlist.get(rlist.size()-1).getFishtype());*//*


                *//*if (rlist.size() == 1){
                    prepareRCItems();
                }else {
                    recyclerAdapter.notifyItemInserted(rlist.size()-1);
                }*//*
            }
        });*/

//        <------------------End of Add Button Clicking ----------->

    }

//    <-------------End of inItemsView method. Here declaring all methods and call init in onCreate------>

//<-------------------- Start of Vadlidation part to ADD button for not empty fields ---------->

    /*private void addValidations(){
        *//*if (mfingerlings_no_str.isEmpty()){
            mfingerlings_no.setError("Please enter the fingerlings");
            Toast.makeText(this,"Fingerlings not entered",Toast.LENGTH_LONG).show();
        }else if (mtotal_kgs_str.isEmpty()){
            mtotal_kgs.setError("Please enter the Number of kgs");
            Toast.makeText(this,"Enter Number of kgs",Toast.LENGTH_LONG).show();
        }else if (mvehicleno_str.isEmpty()){
            mvehicleno.setError("Please enter the Vehicle Number");
            Toast.makeText(this,"Enter the Vehicle Number",Toast.LENGTH_LONG).show();
        }else {
            prepareRCItems();
        }*//*

        if (mfingerlings_no_str.length() == 0 && mtotal_kgs_str.length() == 0) {
            mdetail_button.setEnabled(false);
        }else {
            mdetail_button.setEnabled(true);
        }

    }*/

//    <-------------------- End of Vadlidation part to ADD button for not empty fields ---------->

//    <------------------Start of Text Watcher of caluculation ----------->
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

//    <------------------End of Text Watcher of caluculation ----------->


//    <------------------Start of fishDetails method for complete fish fish details ----------->

    public void alert_popup(){

        Button msubmit_btn,mclose_btn;

        AlertDialog.Builder alert_dialog = new AlertDialog.Builder(RecycleViewPopUp.this);
        alert_dialog.setCancelable(false);
        LayoutInflater inflater = RecycleViewPopUp.this.getLayoutInflater();
        View view = inflater.inflate(R.layout.alert_popup,null);

        msubmit_btn = findViewById(R.id.submit_btn);
        mclose_btn = findViewById(R.id.close_btn);

        alert_dialog.setView(view);

        alert_dialog.setPositiveButton("", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                msubmit_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        mfingerlings_no_str = mfingerlings_no.getText().toString().trim();
                        mtotal_kgs_str = mtotal_kgs.getText().toString().trim();
                        mtotal_fingerlings_str = mtotal_fingerlings.getText().toString().trim();
                        mvehicleno_str = mvehicleno.getText().toString().trim();

                        if (mfingerlings_no_str.isEmpty()){
                            mfingerlings_no.setError("Please enter the fingerlings");
                            Log.d("setMethods2",""+mfingerlings_no);
                        }else if (mtotal_kgs_str.isEmpty()){
                            mtotal_kgs.setError("Please enter the Number of kgs");

                        }else if (mvehicleno_str.isEmpty()){
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
            }
        });

        alert_dialog.setNegativeButton("", new android.content.DialogInterface.OnClickListener() {
            @Override
            public void onClick(android.content.DialogInterface dialog, int which) {
                mclose_btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
    }

    public void fishDetails(){

        fishtype_list = new ArrayList<>();
        fishtype_list.add("<--- Select the type of Fish --->");
        fishtype_list.add("Catla");
        fishtype_list.add("Rohu");
        fishtype_list.add("Common Carp");
        fishtype_list.add("Mrigala");

        final ArrayAdapter<String> fish_list = new ArrayAdapter<>(this,
                android.R.layout.simple_dropdown_item_1line, fishtype_list);
        fish_list.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        mspecies_selection.setAdapter(fish_list);

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

//    <------------------End of fishDetails method for complete fish fish details ----------->


//    <------------------Start of caluculation method for complete fish fish details ----------->
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

//    <------------------End of fishDetails method for complete fish fish details ----------->


//    <------------------Start of prepareRCItems method for recycler view items and binding the values to recycler adapter ----------->

    GridLayoutManager gridLayoutManager;
    private void prepareRCItems(){
        recyclerAdapter = new RecyclerAdapter(RecycleViewPopUp.this,rlist);
//        gridLayoutManager = new GridLayoutManager(this,1,RecyclerView.VERTICAL,false);
//        rv.setLayoutManager(gridLayoutManager); // adding the gridView here instead of LinearLayout
        rv.setLayoutManager(new LinearLayoutManager(RecycleViewPopUp.this));
        rv.setAdapter(recyclerAdapter);
//        rv.setHasFixedSize(false);
//        recyclerAdapter.notifyItemInserted(rlist.size()-1);
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

//    <------------------End of prepareRCItems method for recycler view items and binding the values to recycler adapter ----------->


//    <------------Start of recycler Adapter-------->
    public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>{
        Context context;
        LayoutInflater inflater;
        ArrayList<SetMethods> rlist = new ArrayList();

        public RecyclerAdapter(Context context,ArrayList<SetMethods> rlist) {
            this.context = context;
            inflater =LayoutInflater.from(context);
            this.rlist = rlist;
        }

        @Override
        public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            inflater = LayoutInflater.from(context);
            View view =inflater.inflate(R.layout.view_layout,parent,false);
//            View view =inflater.inflate(R.layout.alert_popup,parent,false);
            return new MyViewHolder(view);
        }

        @Override
        public void onBindViewHolder(MyViewHolder holder, int position) {

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

        public class MyViewHolder extends RecyclerView.ViewHolder{

            TextView tv1,tv2,tv3,tv4,tv5;
            public MyViewHolder(View itemView) {
                super(itemView);

                /*tv1 = itemView.findViewById(R.id.fishname);
                tv2 = itemView.findViewById(R.id.no_fingerlings);
                tv3 = itemView.findViewById(R.id.kgs);
                tv4 = itemView.findViewById(R.id.kgs_total);
                tv5 = itemView.findViewById(R.id.vehicle_no);*/

                tv1 = itemView.findViewById(R.id.fishname);
                tv2 = itemView.findViewById(R.id.no_fingerlings);
                tv3 = itemView.findViewById(R.id.kgs);
                tv4 = itemView.findViewById(R.id.kgs_total);
                tv5 = itemView.findViewById(R.id.vehicle_no);
            }
        }
    }

//    <------------End of recycler Adapter-------->

    /*public void openDialog() {
        DialogInterface dialogInterface = new DialogInterface();
        dialogInterface.show(getSupportFragmentManager(),"Dialog Interface");
    }*/

   /* private void showCustomDialog() {


        //before inflating the custom alert dialog layout, we will get the current activity viewgroup
        ViewGroup viewGroup = findViewById(android.R.id.content);

        //then we will inflate the custom alert dialog xml that we created
        View dialogView = LayoutInflater.from(this).inflate(R.layout.alert_popup, viewGroup, false);

        //Now we need an AlertDialog.Builder object
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        //setting the view of the builder to our custom view that we already inflated
        builder.setView(dialogView);

        //finally creating the alert dialog and displaying it
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }*/

    public void openDialog() {
        DialogInterface dialogInterface = new DialogInterface();
        dialogInterface.show(getSupportFragmentManager(),"Dialog Interface");
    }



}