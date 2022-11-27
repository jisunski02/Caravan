package edu.ucu.cite.caravan;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ARentsAdapter extends RecyclerView.Adapter<ARentsAdapter.ARentsViewHolder>{

    private Context contextA;
    private List<ARents> arentList;

    public ARentsAdapter(Context ct3, List<ARents> arentList){
        contextA = ct3;
        this.arentList=arentList;

    }

    @NonNull
    @Override
    public ARentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contextA);
        View view = inflater.inflate(R.layout.c_rents_layout,parent,false);
        return new ARentsAdapter.ARentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ARentsViewHolder holder, int position) {


        ARents arents = arentList.get(position);
        holder.Vehicle_Name.setText(arents.getVehicle_Name());
        holder.Driver_Name.setText(arents.getDriver_Name());
        holder.Total_Price.setText(arents.getTotal_Price());
        holder.Package.setText(String.valueOf(arents.getPackage()));

        holder.ViewFullDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(contextA,displayDetails.class);
                intent.putExtra("key_Vehicle_Name", arents.getVehicle_Name());
                intent.putExtra("key_Driver_Name", arents.getDriver_Name());
                intent.putExtra("key_Driver_Number", arents.getDriver_Number());
                intent.putExtra("key_Driver_Address", arents.getDriver_Address());
                intent.putExtra("key_Plate_Number", arents.getPlate_Number());
                intent.putExtra("key_Total_Price", arents.getTotal_Price());
                intent.putExtra("key_Package", arents.getPackage());
                intent.putExtra("key_Pickup_Date_Time", arents.getPickup_Date_Time());
                intent.putExtra("key_Return_Date_Time", arents.getReturn_Date_Time());
                intent.putExtra("key_Location", arents.getLocation());
                intent.putExtra("key_Rent_days", arents.getRent_days());
                intent.putExtra("key_MOP", arents.getMOP());
                contextA.startActivity(intent);

            }

        });



    }

    @Override
    public int getItemCount() {
        return arentList.size();
    }

    public class ARentsViewHolder extends RecyclerView.ViewHolder {

        TextView Vehicle_Name,Driver_Name,Total_Price,Package;
        Button ViewFullDetails;

        public ARentsViewHolder(@NonNull View itemView) {
            super(itemView);
            Vehicle_Name=itemView.findViewById(R.id.id_Vehicle_Name);
            Driver_Name =itemView.findViewById(R.id.id_Driver_Name);
            Total_Price =itemView.findViewById(R.id.id_Total_Price);
            Package =itemView.findViewById(R.id.id_Package);
            ViewFullDetails = itemView.findViewById(R.id.ViewFullDetails);

        }
    }

}
