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

public class PRentsAdapter extends RecyclerView.Adapter<PRentsAdapter.PRentsViewHolder>{

    private Context contextB;
    private List<PRents> prentList;

    public PRentsAdapter(Context ct4, List<PRents> prentList){
        contextB = ct4;
        this.prentList=prentList;

    }

    @NonNull
    @Override
    public PRentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contextB);
        View view = inflater.inflate(R.layout.c_rents_layout,parent,false);
        return new PRentsAdapter.PRentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PRentsViewHolder holder, int position) {


        PRents prents = prentList.get(position);
        holder.Vehicle_Name.setText(prents.getVehicle_Name());
        holder.Driver_Name.setText(prents.getDriver_Name());
        holder.Total_Price.setText(prents.getTotal_Price());
        holder.Package.setText(String.valueOf(prents.getPackage()));

        holder.ViewFullDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(contextB,displayDetails.class);
                intent.putExtra("key_Vehicle_Name", prents.getVehicle_Name());
                intent.putExtra("key_Driver_Name", prents.getDriver_Name());
                intent.putExtra("key_Driver_Number", prents.getDriver_Number());
                intent.putExtra("key_Driver_Address", prents.getDriver_Address());
                intent.putExtra("key_Plate_Number", prents.getPlate_Number());
                intent.putExtra("key_Total_Price", prents.getTotal_Price());
                intent.putExtra("key_Package", prents.getPackage());
                intent.putExtra("key_Pickup_Date_Time", prents.getPickup_Date_Time());
                intent.putExtra("key_Return_Date_Time", prents.getReturn_Date_Time());
                intent.putExtra("key_Location", prents.getLocation());
                intent.putExtra("key_Rent_days", prents.getRent_days());
                intent.putExtra("key_MOP", prents.getMOP());
                contextB.startActivity(intent);

            }

        });



    }

    @Override
    public int getItemCount() {
        return prentList.size();
    }

    public class PRentsViewHolder extends RecyclerView.ViewHolder {

        TextView Vehicle_Name,Driver_Name,Total_Price,Package;
        Button ViewFullDetails;

        public PRentsViewHolder(@NonNull View itemView) {
            super(itemView);
            Vehicle_Name=itemView.findViewById(R.id.id_Vehicle_Name);
            Driver_Name =itemView.findViewById(R.id.id_Driver_Name);
            Total_Price =itemView.findViewById(R.id.id_Total_Price);
            Package =itemView.findViewById(R.id.id_Package);
            ViewFullDetails = itemView.findViewById(R.id.ViewFullDetails);

        }
    }

}
