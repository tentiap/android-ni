package com.example.nutechwallet.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.nutechwallet.R;
import com.example.nutechwallet.model.history.HistoryData;

import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryHolder> {

    private Context ctx;
    private List<HistoryData> historyDataList;

    public HistoryAdapter(Context ctx, List<HistoryData> historyDataList) {
        this.ctx = ctx;
        this.historyDataList = historyDataList;
    }

    private OnItemClickCallback onItemClickCallback;

    public void setOnItemClickCallback(OnItemClickCallback onItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback;
    }

    @NonNull
    @Override
    public HistoryHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_cardview_history, parent, false);
        HistoryHolder holder = new HistoryHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryHolder holder, int position) {
        HistoryData historyData = historyDataList.get(position);

        // Value of Currency
        Locale localeID = new Locale("in", "ID");
        NumberFormat rupiahFormat = NumberFormat.getCurrencyInstance(localeID);
        holder.tvAmount.setText(rupiahFormat.format(historyData.getAmount()));

        // Value of Transacton Type
        holder.tvType.setText(historyData.getTransactionType().substring(0, 1).toUpperCase() + historyData.getTransactionType().substring(1));

        //Value of Transaction Time
        holder.tvTime.setText(historyData.getTransactionTime());

        //Value of Transaction Type
        holder.tvId.setText("ID Transaksi: " + historyData.getTransactionId());

        switch (historyData.getTransactionType()) {
            case "transfer":
                Glide.with(holder.itemView.getContext())
                        .load(holder.itemView.getContext().getDrawable(R.drawable.ic_transfer))
                        .apply(new RequestOptions().override(55, 55))
                        .into(holder.imgIcon);
                break;
            case "topup":
                Glide.with(holder.itemView.getContext())
                        .load(holder.itemView.getContext().getDrawable(R.drawable.ic_topup))
                        .apply(new RequestOptions().override(55, 55))
                        .into(holder.imgIcon);
                break;
            default:
                Glide.with(holder.itemView.getContext())
                        .load(holder.itemView.getContext().getDrawable(R.drawable.ic_money))
                        .apply(new RequestOptions().override(55, 55))
                        .into(holder.imgIcon);
                break;
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickCallback.onItemClicked(historyDataList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return historyDataList.size();
//        return Math.min(historyDataList.size(), 2);
    }

    public interface OnItemClickCallback {
        void onItemClicked(HistoryData data);
    }

    public class HistoryHolder extends RecyclerView.ViewHolder {
        TextView tvId, tvAmount, tvType,tvTime;
        ImageView imgIcon;

        public HistoryHolder(@NonNull View itemView) {
            super(itemView);

            tvId = itemView.findViewById(R.id.tv_id_history);
            tvAmount = itemView.findViewById(R.id.tv_harga_history);
            tvTime = itemView.findViewById(R.id.tv_waktu_history);
            tvType = itemView.findViewById(R.id.tv_tipe_history);
            imgIcon = itemView.findViewById(R.id.img_icon_history);
        }
    }
}
