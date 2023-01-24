package edu.ucu.cite.caravanrental;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class VehicleAdapterHome extends RecyclerView.Adapter<VehicleAdapterHome.VehicleViewHolder>{

    private Context contextX;
    private List<Vehicles> vehiclesList;
    private String url_image = "https://caravan-rental-cars.online/vehicles-photo/";
    String item_price = "";
    double price;
    String convertedprice = "";

    public VehicleAdapterHome(Context ct1, List<Vehicles> vehiclesList){
        contextX = ct1;
        this.vehiclesList=vehiclesList;

    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contextX);
        View view = inflater.inflate(R.layout.homecarlayout,parent,false);
        return new VehicleAdapterHome.VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {

        Vehicles vehicles = vehiclesList.get(position);

        Glide.with(holder.vimage).load(url_image+vehicles.getVehicle_photo())
                .thumbnail(0.5f)
                .error(R.drawable.ic_car_24)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.vimage);

        holder.transmission.setText(vehicles.getTransmission());
        holder.seat_capacity1.setText(vehicles.getSeat_capacity());
        holder.vehicles_name.setText(vehicles.getVehicle_name());

        holder.vimage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //disclaimer Dialog show
                Dialog viewcarDetails;
                viewcarDetails = new Dialog(contextX);
                viewcarDetails.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
                viewcarDetails.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                viewcarDetails.setContentView(R.layout.dialog_view_carhome);

                ImageView carImage = viewcarDetails.findViewById(R.id.carImage);
                TextView carName = viewcarDetails.findViewById(R.id.carName);
                TextView plateNum = viewcarDetails.findViewById(R.id.plateNum);
                TextView regExpiry = viewcarDetails.findViewById(R.id.regExpiry);
                TextView transmission = viewcarDetails.findViewById(R.id.transmission);
                TextView yearmodel = viewcarDetails.findViewById(R.id.year_model);
                TextView seatcapacity = viewcarDetails.findViewById(R.id.seat_capacity);
                TextView manufacturedby = viewcarDetails.findViewById(R.id.manufactured_by);
                ImageView close = viewcarDetails.findViewById(R.id.imageViewClose);


                close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        viewcarDetails.dismiss();
                    }
                });

                Glide.with(carImage).load(url_image+vehicles.getVehicle_photo())
                        .thumbnail(0.5f)
                        .error(R.drawable.ic_car_24)
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .into(carImage);

                carName.setText(vehicles.getVehicle_name());
                plateNum.setText(vehicles.getVehicle_platenum());
                regExpiry.setText(vehicles.getVehicle_regexpiry());
                transmission.setText(vehicles.getTransmission());
                yearmodel.setText(vehicles.getYear_model());
                if(vehicles.getSeat_capacity().equals("1")){
                    seatcapacity.setText(vehicles.getSeat_capacity()+ " seat");
                }
                else{
                    seatcapacity.setText(vehicles.getSeat_capacity()+" seats");
                }

                manufacturedby.setText(vehicles.getManufactured_by());

                viewcarDetails.setCancelable(false);
                viewcarDetails.show();
            }
        });

    }

    @Override
    public int getItemCount() {
        return vehiclesList.size();
    }

    public class VehicleViewHolder extends RecyclerView.ViewHolder {
        ImageView vimage;
        TextView vehicles_id,vehicles_name,vehicles_platenum,seat_capacity1,seat_capacity2,transmission,price, price2;
        Button select_vehiclebtn;
        Button selectVehicle;
        Button viewVehicle;
        ImageView notAvailable;

        public VehicleViewHolder(@NonNull View itemView) {
            super(itemView);
            vehicles_name =itemView.findViewById(R.id.carName);
            transmission=itemView.findViewById(R.id.transmission);
            seat_capacity1=itemView.findViewById(R.id.seat_capacity);
            vimage = itemView.findViewById(R.id.carImage);
        }
    }
}
