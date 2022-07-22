package edu.ucu.cite.caravanrental;

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

import java.util.List;

public class DriversAdapter extends RecyclerView.Adapter<DriversAdapter.MyViewHolder> {


    //context = mCtx
    private Context context;
    private List<Drivers> driversList;
    private String url_image = "https://caravan-rental-cars.online/drivers-photo/";

    public DriversAdapter(Context ct, List<Drivers> driversList){
        context = ct;
        this.driversList=driversList;

    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.my_row,parent,false);
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


        holder.select_driverbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context,selectmop.class);
                intent.putExtra("keyDriver_name", drivers.getDrivers_name());
                intent.putExtra("keyDriver_address", drivers.getDrivers_address());
                intent.putExtra("keyDriver_mobile", drivers.getDrivers_mobile());
                intent.putExtra("keyDriver_id", drivers.getDrivers_id());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return driversList.size();
    }

    

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView drivers_name,drivers_address,drivers_mobile, drivers_exp, drivers_license;
        ImageView drivers_photo;
        Button select_driverbtn;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            select_driverbtn=itemView.findViewById(R.id.select_driverbtn);
            drivers_name=itemView.findViewById(R.id.drivers_name);
            drivers_address=itemView.findViewById(R.id.drivers_address);
            drivers_mobile=itemView.findViewById(R.id.drivers_mobile);
            drivers_photo = itemView.findViewById(R.id.driver_photo);
            drivers_exp = itemView.findViewById(R.id.drivers_exp);
            drivers_license = itemView.findViewById(R.id.driver_license_number);

        }
    }
}
