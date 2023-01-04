package edu.ucu.cite.caravan.Adapters;

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

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.ucu.cite.caravan.Constants;
import edu.ucu.cite.caravan.DataModels.PaymentModel;
import edu.ucu.cite.caravan.DataModels.RequirementsPhotoModel;
import edu.ucu.cite.caravan.R;

public class PaymentAdapter extends RecyclerView.Adapter<PaymentAdapter.ViewHolder> {


    private Context context;
    private List<PaymentModel> paymentModels;


    public PaymentAdapter(Context context, List<PaymentModel> paymentModels){
        this.context = context;
        this.paymentModels = paymentModels;
    }

    @NonNull
    @Override
    public PaymentAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.payment_layout,parent,false);
        return new PaymentAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PaymentAdapter.ViewHolder holder, int position) {

        PaymentModel currentPosition = paymentModels.get(position);

        if(currentPosition.getTransaction_no().equals("null")){
            holder.transactionNum.setText("Transaction No. : N/A");
        }
        else{
            holder.transactionNum.setText("Transaction No. : "+currentPosition.getTransaction_no());
        }

        if(currentPosition.getPayment_type().equals("null")){
            holder.paymentType.setText("Payment Type : N/A");
        }
        else{
            holder.paymentType.setText("Payment Type : "+currentPosition.getPayment_type());
        }

        if(currentPosition.getAmount().equals("null")){
            holder.amount.setText("Amount Paid : N/A");
        }
        else{
            holder.amount.setText("Amount Paid : "+currentPosition.getAmount());
        }

        if(currentPosition.getConfirmation_date().equals("null")){
            holder.confirmationDate.setText("Date Confirmed : N/A");
        }
        else{
            holder.confirmationDate.setText("Date Confirmed : "+currentPosition.getConfirmation_date());
        }

        if(currentPosition.getCreated_at().equals("null")){
            holder.createdAt.setText("Date Paid : N/A");
        }
        else{
            holder.createdAt.setText("Date Paid : "+currentPosition.getCreated_at());
        }

    }

    @Override
    public int getItemCount() {
        return paymentModels.size();
    }



    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView transactionNum, paymentType, amount, confirmationDate, createdAt;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            transactionNum = itemView.findViewById(R.id.transactionNum);
            paymentType = itemView.findViewById(R.id.paymentType);
            amount = itemView.findViewById(R.id.amount);
            confirmationDate = itemView.findViewById(R.id.confirmationDate);
            createdAt = itemView.findViewById(R.id.createdAt);

        }
    }


}
