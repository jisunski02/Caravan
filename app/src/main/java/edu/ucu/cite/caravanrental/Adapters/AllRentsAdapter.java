package edu.ucu.cite.caravanrental.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
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

import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucu.cite.caravanrental.Constants;
import edu.ucu.cite.caravanrental.DataModels.AllRentsDataModel;
import edu.ucu.cite.caravanrental.DataModels.UserRentsDataModel;
import edu.ucu.cite.caravanrental.R;
import edu.ucu.cite.caravanrental.SharedPrefManager;
import edu.ucu.cite.caravanrental.UploadPaymentActivity;
import edu.ucu.cite.caravanrental.ViewRentDetails;

public class AllRentsAdapter extends RecyclerView.Adapter<AllRentsAdapter.ViewHolder> {


    private Context context;
    private List<AllRentsDataModel> rentsDataModels;

    String item_price = "";
    double price;
    String convertedprice = "";


    private String url_image = "https://caravan-rental-cars.online/vehicles-photo/";

    public AllRentsAdapter(Context context, List<AllRentsDataModel> rentsDataModels){
        this.context = context;
        this.rentsDataModels = rentsDataModels;
    }

    @NonNull
    @Override
    public AllRentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rents_layoutv3,parent,false);
        return new AllRentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllRentsAdapter.ViewHolder holder, int position) {

        AllRentsDataModel currentPosition = rentsDataModels.get(position);

        Glide.with(holder.vehicle_photo).load(url_image+currentPosition.getVehicle_photo())
                .thumbnail(0.5f)
                .error(R.drawable.ic_car_24)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.vehicle_photo);

        holder.vehicle_name.setText(currentPosition.getVehicle_name());

        holder.pick_up_date.setText(currentPosition.getPick_up_date());
        holder.return_date.setText(currentPosition.getReturn_date());
        holder.rent_package.setText(currentPosition.getPackage_type());

        if(currentPosition.getRent_days().equals("1")){
            holder.rent_days.setText(currentPosition.getRent_days()+" day");

        }
        else {
            holder.rent_days.setText(currentPosition.getRent_days()+" days");
        }

        try {
            item_price = currentPosition.getTotal_amount();
            convertedprice = item_price.replaceAll("[^\\d.]", "");

            price = Double.parseDouble(convertedprice);

            DecimalFormat formatter = new DecimalFormat("#,###,###");
            String formattedPrice = formatter.format(price);

            holder.total_price.setText("₱" + formattedPrice + ".00");
        }
        catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        int num = 1;
        //display top 5 rents
        if(num*5 > rentsDataModels.size()){

            return rentsDataModels.size();
        }else{
            return num*5;
        }
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView pick_up_date, return_date, rent_package, rent_days, total_price, vehicle_name;
        ImageView vehicle_photo;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pick_up_date = itemView.findViewById(R.id.pick_up_date);
            return_date = itemView.findViewById(R.id.return_date);
            rent_package = itemView.findViewById(R.id.tvPackage);
            rent_days = itemView.findViewById(R.id.rent_days);
            total_price = itemView.findViewById(R.id.total_price);
            vehicle_name = itemView.findViewById(R.id.vehicle_name);
            vehicle_photo = itemView.findViewById(R.id.vehicle_photo);
        }
    }



}
