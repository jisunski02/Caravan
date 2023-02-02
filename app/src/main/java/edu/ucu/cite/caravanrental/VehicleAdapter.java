package edu.ucu.cite.caravanrental;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import edu.ucu.cite.caravanrental.Adapters.VehiclePhotoAdapter;
import edu.ucu.cite.caravanrental.DataModels.VehiclePhotoModel;

public class VehicleAdapter extends RecyclerView.Adapter<VehicleAdapter.VehicleViewHolder>{

    private Context contextX;
    private List<Vehicles> vehiclesList;
    private String url_image = "https://caravan-rental-cars.online/vehicles-photo/";

    RecyclerView vehiclePhotoRecyclerview;
    private LinearLayoutManager linearLayoutManager;
    private static final String URL_VEHICLES_PHOTO = "https://caravan-rental-cars.online/includes/displayvehiclePhoto.php";

    List<VehiclePhotoModel> vehiclesPhoto;
    String item_price = "";
    int price;
    String convertedprice = "";

    String item_price2 = "";
    int price2;
    String convertedprice2 = "";

    public VehicleAdapter(Context ct1, List<Vehicles> vehiclesList){
        contextX = ct1;
        this.vehiclesList=vehiclesList;

    }

    @NonNull
    @Override
    public VehicleViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(contextX);
        View view = inflater.inflate(R.layout.car_layout,parent,false);
        return new VehicleAdapter.VehicleViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VehicleViewHolder holder, int position) {

        Vehicles vehicles = vehiclesList.get(position);

        Glide.with(holder.vimage).load(url_image+vehicles.getVehicle_photo())
                .thumbnail(0.5f)
                .error(R.drawable.ic_car_24)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.vimage);

        holder.vehicles_name.setText(vehicles.getVehicle_name());
        holder.transmission.setText(vehicles.getTransmission());
        holder.price.setText(String.valueOf("₱"+vehicles.getRegular_package() + "/day"));
        holder.price2.setText(String.valueOf("₱"+vehicles.getComplete_package() + "/day"));
        if (vehicles.getVehicle_status().equals("1")){
            holder.notAvailable.setVisibility(View.VISIBLE);
            holder.vimage.setVisibility(View.GONE);
        }
        else {
            holder.notAvailable.setVisibility(View.GONE);
            holder.vimage.setVisibility(View.VISIBLE);
        }

        holder.selectVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vehicles.getVehicle_status().equals("1")){

                }
                else {
                    Intent intent = new Intent(contextX, selectpackage.class);
                    intent.putExtra("key_Vehicles_ID", vehicles.getVehicles_id());
                    intent.putExtra("key_Vehicles_Name", vehicles.getVehicle_name());
                    intent.putExtra("key_Vehicles_Platenum", vehicles.getVehicle_platenum());
                    intent.putExtra("key_Vehicles_Capacity", vehicles.getSeat_capacity());
                    intent.putExtra("key_Vehicles_Transmission", vehicles.getTransmission());
                    intent.putExtra("key_Vehicles_Price", vehicles.getRegular_package());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    contextX.startActivity(intent);
                    GlobalVariables.SelectedVehiclesCapacity = Integer.parseInt(vehicles.getSeat_capacity());

                    item_price = vehicles.getRegular_package();
                    convertedprice = item_price.replaceAll("[^\\d.]", "");
                    price = Integer.parseInt(convertedprice);

                    item_price2 = vehicles.getComplete_package();
                    convertedprice2 = item_price2.replaceAll("[^\\d.]", "");
                    price2 = Integer.parseInt(convertedprice2);

                    GlobalVariables.regularVehiclePrice = String.valueOf(price);
                    GlobalVariables.completeVehiclePrice = String.valueOf(price2);
                    Constants.rescodes = vehicles.getRes_code();

                }
            }
        });

        holder.viewVehicle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (vehicles.getVehicle_status().equals("1")){

                }
                else {
                    //disclaimer Dialog show
                    Dialog viewcarDetails;
                    viewcarDetails = new Dialog(contextX);
                    viewcarDetails.requestWindowFeature(Window.FEATURE_NO_TITLE);//...........
                    viewcarDetails.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                    viewcarDetails.setContentView(R.layout.dialog_view_car);

                    vehiclePhotoRecyclerview = viewcarDetails.findViewById(R.id.recycler_view_vehicles);
                    vehiclePhotoRecyclerview.setHasFixedSize(true);
                    linearLayoutManager = new LinearLayoutManager(contextX, LinearLayoutManager.HORIZONTAL, false);
                    vehiclePhotoRecyclerview.setLayoutManager(linearLayoutManager);
                    vehiclesPhoto = new ArrayList<>();

                    loadVehiclePhoto(vehicles.getVehicles_id());

                    ImageView carImage = viewcarDetails.findViewById(R.id.carImage);
                    TextView carName = viewcarDetails.findViewById(R.id.carName);
                    TextView plateNum = viewcarDetails.findViewById(R.id.plateNum);
                    TextView regExpiry = viewcarDetails.findViewById(R.id.regExpiry);
                    TextView transmission = viewcarDetails.findViewById(R.id.transmission);
                    TextView yearmodel = viewcarDetails.findViewById(R.id.year_model);
                    TextView seatcapacity = viewcarDetails.findViewById(R.id.seat_capacity);
                    TextView manufacturedby = viewcarDetails.findViewById(R.id.manufactured_by);
                    Button selectCar = viewcarDetails.findViewById(R.id.buttonSelectCar);
                    ImageView close = viewcarDetails.findViewById(R.id.imageViewClose);

                    selectCar.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(contextX, selectpackage.class);
                            intent.putExtra("key_Vehicles_ID", vehicles.getVehicles_id());
                            intent.putExtra("key_Vehicles_Name", vehicles.getVehicle_name());
                            intent.putExtra("key_Vehicles_Platenum", vehicles.getVehicle_platenum());
                            intent.putExtra("key_Vehicles_Capacity", vehicles.getSeat_capacity());
                            intent.putExtra("key_Vehicles_Transmission", vehicles.getTransmission());
                            intent.putExtra("key_Vehicles_Price", vehicles.getRegular_package());
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            GlobalVariables.SelectedVehiclesCapacity = Integer.parseInt(vehicles.getSeat_capacity());

                            item_price = vehicles.getRegular_package();
                            convertedprice = item_price.replaceAll("[^\\d.]", "");
                            price = Integer.parseInt(convertedprice);

                            DecimalFormat formatter = new DecimalFormat("#,###,###");
                            String formattedPrice = formatter.format(price);

                            item_price2 = vehicles.getComplete_package();
                            convertedprice2 = item_price.replaceAll("[^\\d.]", "");
                            price2 = Integer.parseInt(convertedprice2);

                            DecimalFormat formatter2 = new DecimalFormat("#,###,###");
                            String formattedPrice2 = formatter2.format(price);

                            GlobalVariables.regularVehiclePrice = String.valueOf(price);
                            GlobalVariables.completeVehiclePrice = String.valueOf(price2);
                            Constants.rescodes = vehicles.getRes_code();
                            contextX.startActivity(intent);
                        }
                    });

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
            vehicles_name =itemView.findViewById(R.id.vehicles_name);
            transmission=itemView.findViewById(R.id.id_transmission);
            price=itemView.findViewById(R.id.id_price);
            price2=itemView.findViewById(R.id.id_price2);
            vimage = itemView.findViewById(R.id.Vimage);
            selectVehicle = itemView.findViewById(R.id.buttonSelectCar);
            viewVehicle = itemView.findViewById(R.id.buttonViewCar);
            notAvailable = itemView.findViewById(R.id.notAvailable);
        }
    }

    private void loadVehiclePhoto(int vehicle_photo_id) {

        StringRequest stringRequest = new StringRequest(Request.Method.GET, URL_VEHICLES_PHOTO,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {

                        try {
                            //converting the string to json array object
                            JSONArray vehicles = new JSONArray(response);

                            //traversing through all the object
                            for (int i = 0; i<vehicles.length(); i++) {

                                //getting product object from json array
                                JSONObject vehiclesJSONObject = vehicles.getJSONObject(i);

                                String id = vehiclesJSONObject.getString("id");
                                int vehicle_id = vehiclesJSONObject.getInt("vehicle_id");
                                String vehicle_name = vehiclesJSONObject.getString("vehicle_name");
                                String created_at = vehiclesJSONObject.getString("created_at");


                                VehiclePhotoModel vehicle = new VehiclePhotoModel(id,
                                        vehicle_id,
                                        vehicle_name,
                                        created_at);

                                if(vehicle_photo_id == vehicle_id){
                                    vehiclesPhoto.add(vehicle);
                                }


                            }

                            //creating adapter object and setting it to recyclerview
                            VehiclePhotoAdapter adapter = new VehiclePhotoAdapter(contextX, vehiclesPhoto);
                            vehiclePhotoRecyclerview.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.e("anyText",response);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                });

        Volley.newRequestQueue(contextX).add(stringRequest);
    }
}
