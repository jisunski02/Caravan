package edu.ucu.cite.caravanrental.Adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import edu.ucu.cite.caravanrental.DataModels.VehiclePhotoModel;

public class VehiclePhotoAdapter extends RecyclerView.Adapter<VehiclePhotoAdapter.ViewHolder> {

    private Context context;
    List<VehiclePhotoModel> vehiclesPhoto;

    public VehiclePhotoAdapter(){

    }
    @NonNull
    @Override
    public VehiclePhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull VehiclePhotoAdapter.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
