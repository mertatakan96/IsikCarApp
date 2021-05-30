package com.dtmad.isikcar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class PublicationRecyclerAdapter extends RecyclerView.Adapter<PublicationRecyclerAdapter.PublicationHolder> {

    private ArrayList<String> publicationToList;
    private ArrayList<String> publicationWhereList;
    private ArrayList<String> publicationDateList;
    private ArrayList<String> publicationTimeList;
    private ArrayList<String> publicationPriceList;
    private ArrayList<String> publicationSeatList;

    public PublicationRecyclerAdapter(ArrayList<String> publicationToList, ArrayList<String> publicationWhereList, ArrayList<String> publicationDateList,
                                      ArrayList<String> publicationTimeList, ArrayList<String> publicationPriceList, ArrayList<String> publicationSeatList) {
        this.publicationToList = publicationToList;
        this.publicationWhereList = publicationWhereList;
        this.publicationDateList = publicationDateList;
        this.publicationTimeList = publicationTimeList;
        this.publicationPriceList = publicationPriceList;
        this.publicationSeatList = publicationSeatList;
    }

    @NonNull
    @Override
    public PublicationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row_publication,parent,false);
        return new PublicationHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PublicationHolder holder, int position) {

        holder.placeToText.setText("Nereden: " + publicationToList.get(position));
        holder.placeWhereText.setText("Nereye: " + publicationWhereList.get(position));
        holder.dateText.setText("Tarih: " + publicationDateList.get(position));
        holder.timeText.setText("Saat: " + publicationTimeList.get(position));
        holder.priceText.setText("Ücret: " + publicationPriceList.get(position) + " ₺");
        holder.seatText.setText("Kalan Koltuk: "+ publicationSeatList.get(position));

    }

    @Override
    public int getItemCount() {
        return publicationToList.size();
    }

    class PublicationHolder extends RecyclerView.ViewHolder{

        TextView placeToText, placeWhereText, dateText, timeText, priceText, seatText;

        public PublicationHolder(@NonNull View itemView) {
            super(itemView);

            placeToText = itemView.findViewById(R.id.recyclerview_publishRow_placeTo_text);
            placeWhereText = itemView.findViewById(R.id.recyclerview_publishRow_placeWhere_text);
            dateText = itemView.findViewById(R.id.recyclerview_publishRow_Date_text);
            timeText = itemView.findViewById(R.id.recyclerview_publishRow_Time_text);
            priceText = itemView.findViewById(R.id.recyclerview_publishRow_Price_text);
            seatText = itemView.findViewById(R.id.recyclerview_publishRow_Seat_text);

        }
    }
}
