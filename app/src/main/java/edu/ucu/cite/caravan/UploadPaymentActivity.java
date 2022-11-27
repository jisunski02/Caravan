package edu.ucu.cite.caravan;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class UploadPaymentActivity extends AppCompatActivity {

    EditText amount;
    TextView paymentType;
    Button btnPayNow;
    Button btnUpload;
    ImageView paymentImage;
    ImageView close;
    Bitmap bitmap;
    String encodeImageString;
    private static final String url="https://caravan-rental-cars.online/payNow.php";

    Dialog dialogPaymentOption;
    ListView paymentOption;
    ArrayList<String> arrayList = new ArrayList<>();

    String id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_paynow);


        //Initialize array list
        //Add value in array list
        arrayList.add("Full Payment");
        arrayList.add("Initial Payment");

        btnUpload = findViewById(R.id.btnUpload);
        paymentImage = findViewById(R.id.paymentImage);
        amount = findViewById(R.id.amount);
        paymentType = findViewById(R.id.payment_type);
        btnPayNow = findViewById(R.id.btnUploadReq);
        close = findViewById(R.id.close);

        close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        btnPayNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //insert API pay now
                uploadPaymentToDb2();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dexter.withActivity(UploadPaymentActivity.this)
                        .withPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                        .withListener(new PermissionListener() {
                            @Override
                            public void onPermissionGranted(PermissionGrantedResponse response)
                            {
                                Intent intent=new Intent(Intent.ACTION_PICK);
                                intent.setType("image/*");
                                startActivityForResult(Intent.createChooser(intent,"Browse Image"),1);
                            }

                            @Override
                            public void onPermissionDenied(PermissionDeniedResponse response) {

                            }



                            @Override
                            public void onPermissionRationaleShouldBeShown(PermissionRequest permission, PermissionToken token) {
                                token.continuePermissionRequest();
                            }
                        }).check();
            }
        });

        paymentType.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogPaymentOption = new Dialog(UploadPaymentActivity.this);
                dialogPaymentOption.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
                dialogPaymentOption.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                dialogPaymentOption.setContentView(R.layout.dialog_paymentoption);

                paymentOption = dialogPaymentOption.findViewById(R.id.listviewpayment);
                //Initialize array adapter
                ArrayAdapter<String> adapter = new ArrayAdapter<>(UploadPaymentActivity.this
                        , android.R.layout.simple_list_item_1,arrayList);
                //Set adapter
                paymentOption.setAdapter(adapter);
                paymentOption.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        paymentType.setText(adapter.getItem(position));
                        dialogPaymentOption.dismiss();
                    }
                });
                dialogPaymentOption.setCancelable(true);
                dialogPaymentOption.show();


            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        if(requestCode==1 && resultCode==RESULT_OK)
        {
            Uri filepath=data.getData();
            try
            {
                InputStream inputStream=getContentResolver().openInputStream(filepath);
                bitmap= BitmapFactory.decodeStream(inputStream);
                paymentImage.setImageBitmap(bitmap);
                encodeBitmapImage(bitmap);
            }catch (Exception ex)
            {

            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void encodeBitmapImage(Bitmap bitmap)
    {
        ByteArrayOutputStream byteArrayOutputStream=new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG,100,byteArrayOutputStream);
        byte[] bytesofimage=byteArrayOutputStream.toByteArray();
        encodeImageString=android.util.Base64.encodeToString(bytesofimage, Base64.DEFAULT);
    }

    private boolean uploadPaymentToDb2()
    {
        String amountString = amount.getText().toString().trim();
        if(encodeImageString == null || amount.getText().toString().trim().isEmpty() || paymentType.getText().toString().trim().isEmpty() ){
            Toast.makeText(UploadPaymentActivity.this, "Make sure you attached image, payment type and amount", Toast.LENGTH_SHORT).show();
        return false;
        }

        else if(!(encodeImageString.isEmpty() && amount.getText().toString().isEmpty() && paymentType.getText().toString().isEmpty())){
            StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response)
                {
                    try {
                        JSONObject jsonObject = new JSONObject(response);
                        String result = jsonObject.getString("success");
                        if (result.equals("1")) {
                            finish();
                            Toast.makeText(UploadPaymentActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                        }
                        else if(!encodeImageString.equals("") && !amount.getText().toString().equals("") &&
                                !paymentType.getText().toString().equals("")){
                            amount.setText("");
                            paymentType.setText("");
                            paymentImage.setImageResource(R.drawable.payment);
                            Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                            finish();
                        }
                    }
                    catch (JSONException e){
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(getApplicationContext(),"Attach image first",Toast.LENGTH_LONG).show();
                }
            })
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {
                    id = String.valueOf(SharedPrefManager.getInstance(UploadPaymentActivity.this).getId());
                    Map<String,String> map=new HashMap<String, String>();
                    //map.put("t1",name);
                    // map.put("t2",dsg);
                    map.put("proof_of_payment",encodeImageString);
                    map.put("booking_id", Constants.booking_id);
                    map.put("customer_id", id);
                    map.put("payment_type", paymentType.getText().toString());
                    map.put("amount", amount.getText().toString());
                    return map;
                }
            };


            RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
            queue.add(request);

            return true;
        }

        return false;

    }

    private void uploadPaymentToDb()
    {

        StringRequest request=new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)
            {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("success");
                    if (encodeImageString.equals("") && amount.getText().toString().equals("") &&
                            paymentType.getText().toString().equals("")) {
                        Toast.makeText(getApplicationContext(), "Make sure, you have attached image, payment option and amount", Toast.LENGTH_LONG).show();
                    }
                    else if(!encodeImageString.equals("") && !amount.getText().toString().equals("") &&
                            !paymentType.getText().toString().equals("")){
                        amount.setText("");
                        paymentType.setText("");
                        paymentImage.setImageResource(R.drawable.payment);
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        finish();
                    }
                }
                catch (JSONException e){
                        e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getApplicationContext(),"Attach image first",Toast.LENGTH_LONG).show();
            }
        })
        {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError
            {
                id = String.valueOf(SharedPrefManager.getInstance(UploadPaymentActivity.this).getId());
                Map<String,String> map=new HashMap<String, String>();
                //map.put("t1",name);
                // map.put("t2",dsg);
                map.put("proof_of_payment",encodeImageString);
                map.put("booking_id", Constants.booking_id);
                map.put("customer_id", id);
                map.put("payment_type", paymentType.getText().toString());
                map.put("amount", amount.getText().toString());
                return map;
            }
        };


        RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
        queue.add(request);
    }

}