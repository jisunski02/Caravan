package edu.ucu.cite.caravanrental.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import java.util.List;

import edu.ucu.cite.caravanrental.DataModels.VehiclePhotoModel;
import edu.ucu.cite.caravanrental.R;
import edu.ucu.cite.caravanrental.VehicleAdapter;

public class VehiclePhotoAdapter extends RecyclerView.Adapter<VehiclePhotoAdapter.ViewHolder> {

    private Context context;
    List<VehiclePhotoModel> vehiclesPhoto;
    private String url_image = "https://caravan-rental-cars.online/vehicles-photo/";

    public VehiclePhotoAdapter(Context context, List<VehiclePhotoModel> vehiclesPhoto){
        this.context = context;
        this.vehiclesPhoto = vehiclesPhoto;

    }
    @NonNull
    @Override
    public VehiclePhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.vehicle_photos_layout,parent,false);
        return new VehiclePhotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehiclePhotoAdapter.ViewHolder holder, int position) {
        VehiclePhotoModel vehiclePhotoModel = vehiclesPhoto.get(position);

        Glide.with(holder.vehicle_slidephoto).load(url_image+vehiclePhotoModel.getVehicle_name())
                .thumbnail(0.5f)
                .error(R.drawable.ic_car_24)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.vehicle_slidephoto);
    }

    @Override
    public int getItemCount() {
        return vehiclesPhoto.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView vehicle_slidephoto;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            vehicle_slidephoto = itemView.findViewById(R.id.vehicle_slidephoto);
        }
    }
}
