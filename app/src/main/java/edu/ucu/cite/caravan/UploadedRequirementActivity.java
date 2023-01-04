package edu.ucu.cite.caravan;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import edu.ucu.cite.caravan.Adapters.RequirementsPhotoAdapter;
import edu.ucu.cite.caravan.Adapters.UserRentsAdapter;
import edu.ucu.cite.caravan.DataModels.RegularPackageRentsDataModel;
import edu.ucu.cite.caravan.DataModels.RequirementsPhotoModel;
import edu.ucu.cite.caravan.DataModels.UserRentsDataModel;

public class UploadedRequirementActivity extends AppCompatActivity {

    Toolbar toolbar;

    private ProgressDialog progressBar;
    RecyclerView requirementRecyclerview;
    List<RequirementsPhotoModel> requirementsPhotoModels;
    LinearLayout noReqPhoto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uploaded_requirement);

        //Toolbar Initialization..
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationIcon(getResources().getDrawable(R.drawable.ic_back_arrow_activity));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UploadedRequirementActivity.super.onBackPressed();
            }
        });
        noReqPhoto = findViewById(R.id.linearReq);
        requirementRecyclerview = findViewById(R.id.reqRecycleview);

        requirementRecyclerview.setLayoutManager(new LinearLayoutManager(this));
        requirementsPhotoModels = new ArrayList<>();

        progressBar = new ProgressDialog(this);
        progressBar.setMessage("Loading requirements...");

        getReqList();

    }

    private void getReqList() {
        //kunin yung id ng naka-login
        progressBar.show();
        int Temp_Customer_ID = SharedPrefManager.getInstance(this).getId();
        String Customer_IDx = new Integer(Temp_Customer_ID).toString();

        String url = Constants.MAIN_URL+ "uploadedreqlist(1).php?customer_id=" + Customer_IDx;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                progressBar.dismiss();
                showJSON(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

    private void showJSON(String response) {
        ArrayList<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
        try {
            JSONObject jsonObject = new JSONObject(response);
            JSONArray result = jsonObject.getJSONArray("tbl_photo");

            for (int i = 0; i < result.length(); i++) {
                JSONObject jo = result.getJSONObject(i);

                String id = jo.getString("id");
                String customer_id = jo.getString("customer_id");
                String photo = jo.getString("photo");
                String created_at = jo.getString("created_at");


                RequirementsPhotoModel requirementsPhoto = new RequirementsPhotoModel(
                        id,
                        customer_id, photo,
                        created_at

                );

                requirementsPhotoModels.add(requirementsPhoto);


            }

            RequirementsPhotoAdapter requirementsPhotoAdapter = new RequirementsPhotoAdapter(this, requirementsPhotoModels);
            requirementRecyclerview.setAdapter(null);
            requirementRecyclerview.setAdapter(requirementsPhotoAdapter);

            if(requirementsPhotoAdapter.getItemCount()==0){
                noReqPhoto.setVisibility(View.VISIBLE);
            }
            else {
                noReqPhoto.setVisibility(View.GONE);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }
}