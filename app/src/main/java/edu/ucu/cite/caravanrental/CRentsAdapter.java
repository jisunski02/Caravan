package edu.ucu.cite.caravanrental;

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

public class CRentsAdapter extends RecyclerView.Adapter<CRentsAdapter.CRentsViewHolder>{

    private Context contextZ;
    private List<CRents> crentList;

    public CRentsAdapter(Context ct2, List<CRents> crentList){
        contextZ = ct2;
        this.crentList=crentList;

    }

    @NonNull
    @Override
    public CRentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contextZ);
        View view = inflater.inflate(R.layout.c_rents_layout,parent,false);
        return new CRentsAdapter.CRentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CRentsViewHolder holder, int position) {


        CRents crents = crentList.get(position);
        holder.Vehicle_Name.setText(crents.getVehicle_Name());
        holder.Driver_Name.setText(crents.getDriver_Name());
        holder.Total_Price.setText(crents.getTotal_Price());
        holder.Package.setText(String.valueOf(crents.getPackage()));

        holder.ViewFullDetails.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(contextZ,displayDetails.class);
                intent.putExtra("key_Vehicle_Name", crents.getVehicle_Name());
                intent.putExtra("key_Driver_Name", crents.getDriver_Name());
                intent.putExtra("key_Driver_Number", crents.getDriver_Number());
                intent.putExtra("key_Driver_Address", crents.getDriver_Address());
                intent.putExtra("key_Plate_Number", crents.getPlate_Number());
                intent.putExtra("key_Total_Price", crents.getTotal_Price());
                intent.putExtra("key_Package", crents.getPackage());
                intent.putExtra("key_Pickup_Date_Time", crents.getPickup_Date_Time());
                intent.putExtra("key_Return_Date_Time", crents.getReturn_Date_Time());
                intent.putExtra("key_Location", crents.getLocation());
                intent.putExtra("key_Rent_days", crents.getRent_days());
                intent.putExtra("key_MOP", crents.getMOP());
                contextZ.startActivity(intent);

            }

        });



    }

    @Override
    public int getItemCount() {
        return crentList.size();
    }

    public class CRentsViewHolder extends RecyclerView.ViewHolder {

        TextView Vehicle_Name,Driver_Name,Total_Price,Package;
        Button ViewFullDetails;

        public CRentsViewHolder(@NonNull View itemView) {
            super(itemView);
            Vehicle_Name=itemView.findViewById(R.id.id_Vehicle_Name);
            Driver_Name =itemView.findViewById(R.id.id_Driver_Name);
            Total_Price =itemView.findViewById(R.id.id_Total_Price);
            Package =itemView.findViewById(R.id.id_Package);
            ViewFullDetails = itemView.findViewById(R.id.ViewFullDetails);



        }
    }

}
