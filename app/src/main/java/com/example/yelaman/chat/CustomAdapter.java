package com.example.yelaman.chat;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<CustomAdapter.ViewHolder> {
    private List<Message> mMessages;

    public CustomAdapter(List<Message> messages) {
        mMessages = messages;
    }

    @NonNull
    @Override
    public CustomAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        View view = inflater.inflate(R.layout.row_list, null, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CustomAdapter.ViewHolder holder, int position) {
        holder.setEmail(mMessages.get(position).getName());
        holder.setMessageRow(mMessages.get(position).getMessage());
    }

    @Override
    public int getItemCount() {
        Log.d("Adapter", mMessages.size() + "");
        return mMessages.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView messageRow;
        private TextView email;

        public ViewHolder(View itemView) {
            super(itemView);
            messageRow = itemView.findViewById(R.id.textViewRowMessage);
            email = itemView.findViewById(R.id.textViewRowName);
        }

        public void setMessageRow(String message) {
            messageRow.setText(message);
        }

        public void setEmail(String name) {
            email.setText(name);
        }
    }
}
