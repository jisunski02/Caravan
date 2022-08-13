package edu.ucu.cite.caravanrental.Adapters;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.json.JSONObject;

import java.io.InputStream;
import java.io.LineNumberReader;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucu.cite.caravanrental.Constants;
import edu.ucu.cite.caravanrental.DataModels.UserRentsDataModel;
import edu.ucu.cite.caravanrental.Mainscreen;
import edu.ucu.cite.caravanrental.R;
import edu.ucu.cite.caravanrental.SharedPrefManager;
import edu.ucu.cite.caravanrental.UploadPaymentActivity;
import edu.ucu.cite.caravanrental.ViewRentDetails;

public class UserRentsAdapter extends RecyclerView.Adapter<UserRentsAdapter.ViewHolder> {


    private Context context;
    private List<UserRentsDataModel> rentsDataModels;

    String item_price = "";
    double price;
    String convertedprice = "";

    String reason = "";
    Dialog cancelRentDialog;
    EditText cancelReason;
    Button btnCancelRent;
    ImageView close;

    Dialog dialogPayNow;
    EditText amount;
    TextView paymentType;
    Button btnPayNow;
    Button btnUpload;
    ImageView paymentImage;

    Dialog dialogPaymentOption;
    ListView paymentOption;
    ArrayList<String> arrayList = new ArrayList<>();

    String id;

    private String url_image = "https://caravan-rental-cars.online/vehicles-photo/";

    public UserRentsAdapter(Context context, List<UserRentsDataModel> rentsDataModels){
        this.context = context;
        this.rentsDataModels = rentsDataModels;
    }

    @NonNull
    @Override
    public UserRentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rents_layoutv2,parent,false);
        return new UserRentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserRentsAdapter.ViewHolder holder, int position) {

        UserRentsDataModel currentPosition = rentsDataModels.get(position);

        Glide.with(holder.vehicle_photo).load(url_image+currentPosition.getVehicle_photo())
                .thumbnail(0.5f)
                .error(R.drawable.ic_car_24)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.vehicle_photo);

        holder.vehicle_name.setText(currentPosition.getVehicle_name());

        holder.pick_up_date.setText(currentPosition.getPick_up_date());
        holder.return_date.setText(currentPosition.getReturn_date());
        holder.rent_package.setText(currentPosition.getPackage_type());
        holder.tvReason.setText(currentPosition.getReason());
        holder.cancelledBy.setText(currentPosition.getCancelledBy());

        if(currentPosition.getRent_days().equals("1")){
            holder.rent_days.setText(currentPosition.getRent_days()+" day");

        }
        else {
            holder.rent_days.setText(currentPosition.getRent_days()+" days");
        }

        if(currentPosition.getRent_status().equals("0")){
            holder.reason.setVisibility(View.GONE);
            holder.bookAgain.setVisibility(View.GONE);
        }

        if(currentPosition.getRent_status().equals("1")){
            holder.payNow.setVisibility(View.GONE);
            holder.reason.setVisibility(View.GONE);
            holder.bookAgain.setVisibility(View.GONE);
        }

        if(currentPosition.getRent_status().equals("2")){
            holder.payNow.setVisibility(View.GONE);
            holder.cancelRent.setVisibility(View.GONE);
            holder.bookAgain.setVisibility(View.VISIBLE);

        }

        if(currentPosition.getMode_of_payment().equals("Cash On Pickup")){
            holder.payNow.setVisibility(View.GONE);
        }

        if(currentPosition.getRent_status().equals("3")){
            holder.payNow.setVisibility(View.GONE);
            holder.cancelRent.setVisibility(View.GONE);
            holder.bookAgain.setVisibility(View.GONE);
            holder.reason.setVisibility(View.GONE);
        }

        holder.payNow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(context, UploadPaymentActivity.class);
                Constants.booking_id = currentPosition.getId();
               context.startActivity(intent);

            }
        });

        holder.bookAgain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = currentPosition.getId();
                bookAgain();
                rentsDataModels.remove(holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                notifyDataSetChanged();
            }
        });

        holder.cancelRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                id = currentPosition.getId();
                //cancel Rent Dialog show
                cancelRentDialog = new Dialog(context);
                cancelRentDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
                cancelRentDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                cancelRentDialog.setContentView(R.layout.dailog_cancelrent);


                cancelReason = cancelRentDialog.findViewById(R.id.cancelReason);
                close = cancelRentDialog.findViewById(R.id.close);
                btnCancelRent = cancelRentDialog.findViewById(R.id.btnSubmit);

                cancelReason.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                    }

                    @Override
                    public void afterTextChanged(Editable editable) {

                        if(cancelReason.getText().toString().length() == 0){
                            btnCancelRent.setVisibility(View.GONE);
                        }
                        else{
                            btnCancelRent.setVisibility(View.VISIBLE);
                        }

                    }
                });

                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelRentDialog.dismiss();
                    }
                });

                btnCancelRent.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cancelRent();
                       rentsDataModels.remove(holder.getAdapterPosition());
                        notifyItemRemoved(holder.getAdapterPosition());
                        notifyDataSetChanged();

                    }
                });

                cancelRentDialog.setCancelable(true);
                cancelRentDialog.show();

            }
        });

        item_price = currentPosition.getTotal_amount();
        convertedprice = item_price.replaceAll("[^\\d.]", "");

        price = Double.parseDouble(convertedprice);

        DecimalFormat formatter = new DecimalFormat("#,###,###");
        String formattedPrice = formatter.format(price);

        holder.total_price.setText("₱"+formattedPrice+".00");

        holder.viewDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(context, ViewRentDetails.class);
                intent.putExtra("pick_up_date", currentPosition.getPick_up_date());
                intent.putExtra("return_date", currentPosition.getReturn_date());
                intent.putExtra("rent_package", currentPosition.getPackage_type());
                intent.putExtra("rent_days", currentPosition.getRent_days());
                intent.putExtra("total_price", currentPosition.getTotal_amount());
                intent.putExtra("mode_of_payment", currentPosition.getMode_of_payment());
                intent.putExtra("rent_status", currentPosition.getRent_status());
                intent.putExtra("package_amount", currentPosition.getPackage_amount());
                intent.putExtra("vehicle_photo", currentPosition.getVehicle_photo());
                intent.putExtra("transmission", currentPosition.getVehicle_transmission());
                intent.putExtra("vehicle_name", currentPosition.getVehicle_name());
                intent.putExtra("year_model", currentPosition.getYear_model());
                intent.putExtra("seat_capacity", currentPosition.getSeat_capacity());
                intent.putExtra("manufactured_by", currentPosition.getManufactured_by());
                intent.putExtra("plate_number", currentPosition.getPlate_number());
                intent.putExtra("vehicle_color", currentPosition.getVehicle_color());
                intent.putExtra("registration_expiry", currentPosition.getRegistration_expiry());
                intent.putExtra("reason", currentPosition.getReason());
                intent.putExtra("price", currentPosition.getTotal_amount());

                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return rentsDataModels.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView pick_up_date, return_date, rent_package, rent_days, total_price, vehicle_name, tvReason, cancelledBy;
        ImageView vehicle_photo;
        Button viewDetails, payNow, cancelRent, bookAgain;
        LinearLayout reason;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pick_up_date = itemView.findViewById(R.id.pick_up_date);
            return_date = itemView.findViewById(R.id.return_date);
            rent_package = itemView.findViewById(R.id.tvPackage);
            rent_days = itemView.findViewById(R.id.rent_days);
            total_price = itemView.findViewById(R.id.total_price);
            vehicle_name = itemView.findViewById(R.id.vehicle_name);
            vehicle_photo = itemView.findViewById(R.id.vehicle_photo);
            viewDetails = itemView.findViewById(R.id.viewDetails);
            payNow = itemView.findViewById(R.id.payNow);
            cancelRent = itemView.findViewById(R.id.cancelRent);
            reason = itemView.findViewById(R.id.reasonlinear);
            tvReason = itemView.findViewById(R.id.reason);
            bookAgain = itemView.findViewById(R.id.bookAgain);
            cancelledBy = itemView.findViewById(R.id.cancelledBy);
        }
    }

    private boolean isValidReason(){
        reason = cancelReason.getText().toString().trim();
        if(reason.isEmpty()){
            Toast.makeText(context, "Reason is required", Toast.LENGTH_SHORT).show();
            return false;
        }
        else{
            cancelReason.setText(null);
            cancelRentDialog.dismiss();
            notifyItemRemoved(Integer.parseInt(id));
            Toast.makeText(context, "Rent is Cancelled", Toast.LENGTH_SHORT).show();
            return true;
        }
    }

    private void getUserID() {
        //kunin yung id ng naka-login
        int Temp_Customer_ID = SharedPrefManager.getInstance(context).getId();
        String Customer_IDx = new Integer(Temp_Customer_ID).toString();

        String url = "https://caravan-rental-cars.000webhostapp.com/includes/userrentswithcardetails.php?customer_id=" + Customer_IDx;

        StringRequest stringRequest = new StringRequest(Request.Method.GET,url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //cancelRent(response);
            }
        },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //Toast.makeText(getActivity(), error.getMessage().toString(), Toast.LENGTH_LONG).show();
                        error.printStackTrace();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);

    }

    private void cancelRent(){
        try{
            if(!isValidReason()){
                return;
            }
        }

        catch (Exception e){
            e.printStackTrace();
        }
        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_UPDATE_CANCEL_RENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("success");

                            if(result.equals("1")){
                                notifyItemRemoved(Integer.parseInt(id));
                            }

                            else{
                                notifyItemRemoved(Integer.parseInt(id));
                                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(Exception jsonException){
                            jsonException.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("id", id);
                params.put("reason", reason);
                params.put("rent_status", "2");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }

    private void bookAgain(){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_BOOKAGAIN,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("success");

                            if(result.equals("1")){
                                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                            else{
                                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(Exception jsonException){
                            jsonException.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("id", id);
                params.put("rent_status", "0");
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }
}
