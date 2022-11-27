package edu.ucu.cite.caravan;

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
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_requirements);

        btnUpload = findViewById(R.id.btnUpload);
        paymentImage = findViewById(R.id.paymentImage);

        btnPayNow = findViewById(R.id.btnUploadReq);
        close = findViewById(R.id.close);

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
        if(encodeImageString == null ){
            progressDialog.dismiss();
            Toast.makeText(UploadRequirementsActivity.this, "Please attached image first", Toast.LENGTH_SHORT).show();
            return false;
        }

        else if(!(encodeImageString.isEmpty())){
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
                                paymentImage.setImageResource(R.drawable.card);
                                encodeImageString = null;
                            }
                        });

                        AlertDialog alert = builder.create();
                        alert.show();
                    }
                    else if(!encodeImageString.equals("")){
                        paymentImage.setImageResource(R.drawable.card);
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
                    id = String.valueOf(SharedPrefManager.getInstance(UploadRequirementsActivity.this).getId());
                    Map<String,String> map=new HashMap<String, String>();
                    map.put("customer_id", id);
                    map.put("photo",encodeImageString);
                    return map;
                }
            };


            RequestQueue queue= Volley.newRequestQueue(getApplicationContext());
            queue.add(request);

            return true;
        }

        return false;

    }

}