package edu.ucu.cite.caravan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

public class DriversAdapterHome extends RecyclerView.Adapter<DriversAdapterHome.MyViewHolder> {


    //context = mCtx
    private Context context;
    private List<Drivers> driversList;
    private String url_image = "https://caravan-rental-cars.000webhostapp.com/drivers-photo/";

    public DriversAdapterHome(Context ct, List<Drivers> driversList){
        context = ct;
        this.driversList=driversList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.drivers_layouthome,parent,false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Drivers drivers = driversList.get(position);

        Glide.with(holder.drivers_photo).load(url_image+drivers.getDrivers_photo())
                .thumbnail(0.5f)
                .error(R.drawable.ic_account_box)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.drivers_photo);

        holder.drivers_exp.setText(drivers.getDrivers_exp());
        holder.drivers_name.setText(drivers.getDrivers_name());
        holder.drivers_address.setText(drivers.getDrivers_address());
        holder.drivers_mobile.setText(drivers.getDrivers_mobile());
        holder.drivers_license.setText("License #: "+drivers.getDrivers_license());

    }

    @Override
    public int getItemCount() {
        return driversList.size();
    }

    

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView drivers_name,drivers_address,drivers_mobile, drivers_exp, drivers_license;
        ImageView drivers_photo;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            drivers_name=itemView.findViewById(R.id.drivers_name);
            drivers_address=itemView.findViewById(R.id.drivers_address);
            drivers_mobile=itemView.findViewById(R.id.drivers_mobile);
            drivers_photo = itemView.findViewById(R.id.driver_photo);
            drivers_exp = itemView.findViewById(R.id.drivers_exp);
            drivers_license = itemView.findViewById(R.id.driver_license_number);

        }
    }
}
