package com.example.fit2081_week4;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.ViewHolder> {
    ArrayList<Item> data;
    @NonNull
    @Override
    public MyRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false); //CardView inflated as RecyclerView list item
        return new ViewHolder(v);
    }

    public MyRecyclerViewAdapter(ArrayList<Item> data){
        this.data = data;
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.ViewHolder holder, int position) {
        holder.textviewID.setText(data.get(position).getID());
        holder.textviewTitle.setText(data.get(position).getTitle());
        holder.textviewTSBN.setText(data.get(position).getISBN());
        holder.textviewAuthor.setText(data.get(position).getAuthor());
        holder.textviewDescription.setText(data.get(position).getDescription());
        holder.textviewPrice.setText(data.get(position).getPrice());
        holder.BookPosition.setText(data.get(position).getPosition());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textviewID;
        public TextView textviewTitle;
        public TextView textviewTSBN;
        public TextView textviewAuthor;
        public TextView textviewDescription;
        public TextView textviewPrice;
        public TextView BookPosition;

        public ViewHolder(View itemView) {
            super(itemView);
            textviewID = itemView.findViewById(R.id.textViewID);
            textviewTitle = itemView.findViewById(R.id.textViewTitle);
            textviewTSBN = itemView.findViewById(R.id.textViewISBN);
            textviewAuthor = itemView.findViewById(R.id.textViewAuthor);
            textviewDescription = itemView.findViewById(R.id.textViewDescription);
            textviewPrice = itemView.findViewById(R.id.textViewPrice);
            BookPosition = itemView.findViewById(R.id.book_position);
        }
    }
}
