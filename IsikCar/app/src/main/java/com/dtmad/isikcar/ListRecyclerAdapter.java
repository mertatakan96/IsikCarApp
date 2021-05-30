package com.dtmad.isikcar;

import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.ListHolder> {

    private ArrayList<String> matchToList;
    private ArrayList<String> matchWhereList;
    private ArrayList<String> matchUserList;
    private ArrayList<String> matchSeatList;
    private ArrayList<String> matchDateList;
    private ArrayList<String> matchTimeList;
    private ArrayList<String> matchPriceList;
    private OnItemClickListener mlistener;
    //private ArrayList<String> matchPhoneList;

    public interface OnItemClickListener {
        void onReservationClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mlistener = listener;
    }

    public ListRecyclerAdapter(ArrayList<String> matchToList, ArrayList<String> matchWhereList, ArrayList<String> matchUserList, ArrayList<String> matchSeatList,
                               ArrayList<String> matchDateList, ArrayList<String> matchTimeList, ArrayList<String> matchPriceList) {
        this.matchToList = matchToList;
        this.matchWhereList = matchWhereList;
        this.matchUserList = matchUserList;
        this.matchSeatList = matchSeatList;
        this.matchDateList = matchDateList;
        this.matchTimeList = matchTimeList;
        this.matchPriceList = matchPriceList;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row,parent,false);
        ListHolder listHolder = new ListHolder(view, mlistener);
        return listHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {

        holder.placeToText.setText("Nereden: " + matchToList.get(position));
        holder.placeWhereText.setText("Nereye: " + matchWhereList.get(position));
        holder.userNameText.setText("Sürücü İsmi: " + matchUserList.get(position));
        holder.seatText.setText("Boş Koltuk: " + matchSeatList.get(position));
        holder.dateText.setText("Tarih: " + matchDateList.get(position));
        holder.timeText.setText("Saat: " + matchTimeList.get(position));
        holder.priceText.setText("Ücret: " + matchPriceList.get(position) + " ₺");

    }

    @Override
    public int getItemCount() {
        return matchToList.size();
    }

    class ListHolder extends RecyclerView.ViewHolder{

        TextView placeToText, placeWhereText, userNameText, seatText, dateText, timeText, priceText;
        Button reservationButton;

        public ListHolder(@NonNull View itemView, final OnItemClickListener listener) {
            super(itemView);

            placeToText = itemView.findViewById(R.id.recyclerview_row_placeTo_text);
            placeWhereText = itemView.findViewById(R.id.recyclerview_row_placeWhere_text);
            userNameText = itemView.findViewById(R.id.recyclerview_row_userName_text);
            seatText = itemView.findViewById(R.id.recyclerview_row_seat_text);
            dateText = itemView.findViewById(R.id.recyclerview_row_date_text);
            timeText = itemView.findViewById(R.id.recyclerview_row_time_text);
            priceText = itemView.findViewById(R.id.recyclerview_row_price_text);
            reservationButton = itemView.findViewById(R.id.reservationButton);

            reservationButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (listener != null){
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION){
                            listener.onReservationClick(position);
                        }
                    }
                }
            });
        }
    }
}
