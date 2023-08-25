package com.iosflashscreen.phonecallerid.screencaller.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.iosflashscreen.phonecallerid.screencaller.R;

import java.util.List;

public class BlockedPersonsAdapter extends RecyclerView.Adapter<BlockedPersonsAdapter.ViewHolder> {

    private List<String> blockedPersonsList;

    public BlockedPersonsAdapter(List<String> blockedPersonsList) {
        this.blockedPersonsList = blockedPersonsList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_blocked_person, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String blockedPerson = blockedPersonsList.get(position);
        holder.blockedPersonTextView.setText(blockedPerson);
    }

    @Override
    public int getItemCount() {
        return blockedPersonsList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView blockedPersonTextView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            blockedPersonTextView = itemView.findViewById(R.id.blockedPersonTextView);
        }
    }
}
