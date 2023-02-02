package edu.ucu.cite.caravanrental.Adapters;

import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;

import org.json.JSONObject;
import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucu.cite.caravanrental.Constants;
import edu.ucu.cite.caravanrental.DataModels.RequirementsPhotoModel;
import edu.ucu.cite.caravanrental.R;

public class RequirementsPhotoAdapter extends RecyclerView.Adapter<RequirementsPhotoAdapter.ViewHolder> {


    private Context context;
    private List<RequirementsPhotoModel> rentsDataModels;

    String item_price = "";
    double price;
    String convertedprice = "";

    String reason = "";
    Dialog cancelRentDialog;
    EditText cancelReason;
    Button btnCancelRent;
    ImageView close;

    String id, vehicle_id;

    private String url_image = "https://caravan-rental-cars.online/user-photo/";

    public RequirementsPhotoAdapter(Context context, List<RequirementsPhotoModel> rentsDataModels){
        this.context = context;
        this.rentsDataModels = rentsDataModels;
    }

    @NonNull
    @Override
    public RequirementsPhotoAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.requirement_layout,parent,false);
        return new RequirementsPhotoAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RequirementsPhotoAdapter.ViewHolder holder, int position) {

        RequirementsPhotoModel currentPosition = rentsDataModels.get(position);

        Glide.with(holder.photo).load(url_image+currentPosition.getPhoto())
                .thumbnail(0.5f)
                .error(R.drawable.card)
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(holder.photo);

        holder.iddocumentName.setText(currentPosition.getPhoto_name());

        holder.deletePhoto.setOnClickListener(view -> {
            id = currentPosition.getId();
            rentsDataModels.remove(holder.getAdapterPosition());
            notifyItemRemoved(holder.getAdapterPosition());
            notifyDataSetChanged();
            deletePhoto(id);
        });


    }

    @Override
    public int getItemCount() {
        return rentsDataModels.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        ImageButton deletePhoto;
        ImageView photo;
        TextView transactionNum;
        TextView iddocumentName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            iddocumentName = itemView.findViewById(R.id.iddocumentName);
            photo = itemView.findViewById(R.id.photo);
            deletePhoto = itemView.findViewById(R.id.deletePhoto);

        }
    }


    private void deletePhoto(String id){

        StringRequest stringRequest = new StringRequest(Request.Method.POST, Constants.URL_DELETE_REQUIREMENT,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try{
                            JSONObject jsonObject = new JSONObject(response);
                            String result = jsonObject.getString("success");

                            if(result.equals("1")){
                                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }

                            else{
                                Toast.makeText(context, jsonObject.getString("message"), Toast.LENGTH_SHORT).show();
                            }
                        }
                        catch(Exception jsonException){
                            jsonException.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(context, error.toString(), Toast.LENGTH_SHORT).show();
            }
        }){

            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {

                Map<String,String> params = new HashMap<>();
                params.put("id", id);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(stringRequest);
    }


}
