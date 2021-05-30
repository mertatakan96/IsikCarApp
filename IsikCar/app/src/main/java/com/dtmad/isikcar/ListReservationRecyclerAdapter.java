package com.dtmad.isikcar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListReservationRecyclerAdapter extends RecyclerView.Adapter<ListReservationRecyclerAdapter.ReservationHolder> {

    private ArrayList<String> reservationToList;
    private ArrayList<String> reservationWhereList;
    private ArrayList<String> reservationDateList;
    private ArrayList<String> reservationTimeList;
    private ArrayList<String> reservationPriceList;
    private ArrayList<String> reservationDriverNameList;
    private ArrayList<String> reservationPlateList;
    private ArrayList<String> reservationDriverPhoneList;

    public ListReservationRecyclerAdapter(ArrayList<String> reservationToList, ArrayList<String> reservationWhereList, ArrayList<String> reservationDateList,
                                          ArrayList<String> reservationTimeList, ArrayList<String> reservationPriceList, ArrayList<String> reservationDriverNameList,
                                          ArrayList<String> reservationPlateList, ArrayList<String> reservationDriverPhoneList) {
        this.reservationToList = reservationToList;
        this.reservationWhereList = reservationWhereList;
        this.reservationDateList = reservationDateList;
        this.reservationTimeList = reservationTimeList;
        this.reservationPriceList = reservationPriceList;
        this.reservationDriverNameList = reservationDriverNameList;
        this.reservationPlateList = reservationPlateList;
        this.reservationDriverPhoneList = reservationDriverPhoneList;
    }

    @NonNull
    @Override
    public ReservationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row_reservations,parent,false);
        return new ReservationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReservationHolder holder, int position) {

        holder.placeToText.setText("Nereden: " + reservationToList.get(position));
        holder.placeWhereText.setText("Nereye: " + reservationWhereList.get(position));
        holder.dateText.setText("Tarih: " + reservationDateList.get(position));
        holder.timeText.setText("Saat: " + reservationTimeList.get(position));
        holder.priceText.setText("Ücret: " + reservationPriceList.get(position) + " ₺");
        holder.driverNameText.setText("Sürücü İsmi: " + reservationDriverNameList.get(position));
        holder.driverPhoneText.setText("Telefon No: " + reservationDriverPhoneList.get(position));
        holder.plateText.setText("Plaka: " + reservationPlateList.get(position));

    }

    @Override
    public int getItemCount() {
        return reservationToList.size();
    }

    class ReservationHolder extends RecyclerView.ViewHolder{

        TextView placeToText, placeWhereText, dateText, timeText, priceText, driverNameText, driverPhoneText, plateText;

        public ReservationHolder(@NonNull View itemView) {
            super(itemView);

            placeToText = itemView.findViewById(R.id.recyclerview_searchRow_placeTo_text);
            placeWhereText = itemView.findViewById(R.id.recyclerview_searchRow_placeWhere_text);
            dateText = itemView.findViewById(R.id.recyclerview_searchRow_Date_text);
            timeText = itemView.findViewById(R.id.recyclerview_searchRow_Time_text);
            priceText = itemView.findViewById(R.id.recyclerview_searchRow_Price_text);
            driverNameText = itemView.findViewById(R.id.recyclerview_searchRow_Name_text);
            driverPhoneText = itemView.findViewById(R.id.recyclerview_searchRow_phone_text);
            plateText = itemView.findViewById(R.id.recyclerview_searchRow_Plate_text);
        }
    }
}
