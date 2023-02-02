package edu.ucu.cite.caravanrental;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
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

public class UploadRequirementsActivity extends AppCompatActivity {
    Button btnPayNow;
    Button btnUpload;
    ImageView paymentImage;
    ImageView close;
    Bitmap bitmap;
    String encodeImageString;
    private static final String url="https://caravan-rental-cars.online/firstphoto.php";

    String id;
    ProgressDialog progressDialog;

    TextView document_type;
    ArrayList<String> arrayList = new ArrayList<>();
    TextView titleHolder;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_requirements);

        arrayList.add("Philippine Passport");
        arrayList.add("Driver''s License");
        arrayList.add("Unified Multi-purpose Identification");
        arrayList.add("PhilHealth Identification");
        arrayList.add("Taxpayer Identification");
        arrayList.add("Philippine Postal Identification");
        arrayList.add("Professional Identification Card Identification");
        arrayList.add("Senior Citizen Identification");
        arrayList.add("Philippine National Identification");
        arrayList.add("Persons with Disability Identification");
        arrayList.add("The Overseas Workers Welfare Administration e-Card");
        arrayList.add("National Bureau of Investigation Clearance");
        arrayList.add("Barangay Clearance");
        arrayList.add("Police Clearance");
        arrayList.add("PSA Birth Certificate");

        btnUpload = findViewById(R.id.btnUpload);
        paymentImage = findViewById(R.id.paymentImage);
        btnPayNow = findViewById(R.id.btnUploadReq);
        close = findViewById(R.id.close);
        document_type = findViewById(R.id.idordocument);


        close.setOnClickListener(v -> finish());

        btnPayNow.setOnClickListener(v -> {
            //insert API pay now
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Uploading Requirements...");
            progressDialog.setCancelable(false);
            progressDialog.show();
            uploadPaymentToDb2();
        });

        btnUpload.setOnClickListener(v -> Dexter.withActivity(UploadRequirementsActivity.this)
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
                }).check());

            document_type.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ListView paymentOption;
                    Dialog idorDocumentsDialoog;
                    idorDocumentsDialoog = new Dialog(UploadRequirementsActivity.this);
                    idorDocumentsDialoog.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
                    idorDocumentsDialoog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    idorDocumentsDialoog.setContentView(R.layout.dialog_paymentoption);

                    titleHolder = idorDocumentsDialoog.findViewById(R.id.titleHolder);
                    titleHolder.setText("Select ID/Document Type");
                    paymentOption = idorDocumentsDialoog.findViewById(R.id.listviewpayment);
                    //Initialize array adapter
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(UploadRequirementsActivity.this
                            , android.R.layout.simple_list_item_1,arrayList);
                    //Set adapter
                    paymentOption.setAdapter(adapter);
                    paymentOption.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                            document_type.setText(adapter.getItem(position));
                            idorDocumentsDialoog.dismiss();
                        }
                    });
                    idorDocumentsDialoog.setCancelable(true);
                    idorDocumentsDialoog.show();
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

    private void uploadPaymentToDb2()
    {
        id = String.valueOf(SharedPrefManager.getInstance(UploadRequirementsActivity.this).getId());

        if(encodeImageString == null || document_type.getText().toString().isEmpty()){
            progressDialog.dismiss();
            Toast.makeText(UploadRequirementsActivity.this, "Please attached image and also document type", Toast.LENGTH_SHORT).show();
        }

        else if(!(encodeImageString.isEmpty() && document_type.getText().toString().isEmpty())){
            StringRequest request=new StringRequest(Request.Method.POST, url, response -> {
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String result = jsonObject.getString("success");

                    if (result.equals("1")) {
                        progressDialog.dismiss();

                        Toast.makeText(UploadRequirementsActivity.this, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();

                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage("Upload requirements again?");
                        builder.setTitle("ID/Documents");
                        builder.setCancelable(false);
                        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.cancel();
                                finish();
                            }
                        });
                        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                document_type.setText("");
                                document_type.setHint("Select ID/Document Type");
                                paymentImage.setImageResource(R.drawable.card);
                                encodeImageString = null;
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }

                    else if(!encodeImageString.equals("") && !document_type.getText().toString().equals("")){
                        paymentImage.setImageResource(R.drawable.payment);
                        Toast.makeText(getApplicationContext(), response.toString(), Toast.LENGTH_LONG).show();
                        finish();
                    }

                }
                catch (JSONException e){
                    e.printStackTrace();
                }

            }, error -> Toast.makeText(getApplicationContext(),"Attach image first",Toast.LENGTH_LONG).show())
            {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError
                {

                    Map<String,String> map=new HashMap<String, String>();
                    map.put("customer_id", id);
                    map.put("name_of_photo", document_type.getText().toString());
                    map.put("photo",encodeImageString);
                    return map;
                }
            };


            RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
            queue.add(request);

        }

    }

}