package edu.ucu.cite.caravanrental.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.text.DecimalFormat;
import java.util.List;

import edu.ucu.cite.caravanrental.DataModels.CompletePackageRentsDataModel;
import edu.ucu.cite.caravanrental.R;
import edu.ucu.cite.caravanrental.ViewRentDetails;

public class CompletePackageRentsAdapter extends RecyclerView.Adapter<CompletePackageRentsAdapter.ViewHolder> {


    private Context context;
    private List<CompletePackageRentsDataModel> rentsDataModels;

    String item_price = "";
    double price;
    String convertedprice = "";

    private String url_image = "https://caravan-rental-cars.000webhostapp.com/vehicles-photo/";

    public CompletePackageRentsAdapter(Context context, List<CompletePackageRentsDataModel> rentsDataModels){
        this.context = context;
        this.rentsDataModels = rentsDataModels;
    }

    @NonNull
    @Override
    public CompletePackageRentsAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.rents_layoutv2,parent,false);
        return new CompletePackageRentsAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletePackageRentsAdapter.ViewHolder holder, int position) {

        CompletePackageRentsDataModel currentPosition = rentsDataModels.get(position);

        Glide.with(holder.vehicle_photo).load(url_image+currentPosition.getVehicle_photo())
                .thumbnail(0.5f)
                .error(R.drawable.ic_car_24)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.vehicle_photo);

        holder.vehicle_name.setText(currentPosition.getVehicle_name());

        holder.pick_up_date.setText(currentPosition.getPick_up_date());
        holder.return_date.setText(currentPosition.getReturn_date());
        holder.rent_package.setText(currentPosition.getRent_package());

        if(currentPosition.getRent_days().equals("1")){
            holder.rent_days.setText(currentPosition.getRent_days()+" day");
        }
        else {
            holder.rent_days.setText(currentPosition.getRent_days()+" days");
        }


        item_price = currentPosition.getTotal_price();
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
                intent.putExtra("rent_package", currentPosition.getRent_package());
                intent.putExtra("rent_days", currentPosition.getRent_days());
                intent.putExtra("total_price", currentPosition.getTotal_price());
                intent.putExtra("mode_of_payment", currentPosition.getMode_of_payment());
                intent.putExtra("rent_status", currentPosition.getRent_status());
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
                intent.putExtra("price", currentPosition.getPrice());
                intent.putExtra("driver_photo", currentPosition.getDriver_photo());
                intent.putExtra("driver_name", currentPosition.getDriver_name());
                intent.putExtra("contact_no", currentPosition.getContact_no());
                intent.putExtra("birthdate", currentPosition.getBirthdate());
                intent.putExtra("license_no", currentPosition.getLicense_no());
                intent.putExtra("license_expiry", currentPosition.getLicense_expiry());
                intent.putExtra("total_exp", currentPosition.getTotal_exp());
                intent.putExtra("address", currentPosition.getAddress());
                intent.putExtra("date_joining", currentPosition.getDate_joining());

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

        private TextView pick_up_date, return_date, rent_package, rent_days, total_price, vehicle_name;
        ImageView vehicle_photo;
        Button viewDetails;
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
        }
    }
}
