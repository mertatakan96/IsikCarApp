package com.dtmad.isikcar;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ListRecyclerAdapter extends RecyclerView.Adapter<ListRecyclerAdapter.ListHolder> {

    private ArrayList<String> matchToList;
    private ArrayList<String> matchWhereList;
    private ArrayList<String> matchUserList;

    public ListRecyclerAdapter(ArrayList<String> matchToList, ArrayList<String> matchWhereList, ArrayList<String> matchUserList) {
        this.matchToList = matchToList;
        this.matchWhereList = matchWhereList;
        this.matchUserList = matchUserList;
    }

    @NonNull
    @Override
    public ListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_row,parent,false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListHolder holder, int position) {

        holder.placeToText.setText(matchToList.get(position));
        holder.placeWhereText.setText(matchWhereList.get(position));
        holder.userNameText.setText(matchUserList.get(position));

    }

    @Override
    public int getItemCount() {
        return matchToList.size();
    }

    class ListHolder extends RecyclerView.ViewHolder{

        TextView placeToText, placeWhereText, userNameText;

        public ListHolder(@NonNull View itemView) {
            super(itemView);

            placeToText = itemView.findViewById(R.id.recyclerview_row_placeTo_text);
            placeWhereText = itemView.findViewById(R.id.recyclerview_row_placeWhere_text);
            userNameText = itemView.findViewById(R.id.recyclerview_row_userName_text);

        }
    }
}
