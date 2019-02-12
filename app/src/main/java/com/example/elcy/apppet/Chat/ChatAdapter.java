package com.example.elcy.apppet.Chat;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.elcy.apppet.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder>{
    private Context context;
    private List<ChatObject> chatList;

    public ChatAdapter(Context context, List<ChatObject> favoriteList) {
        this.context = context;
        this.chatList = favoriteList;
    }

    public static class ChatViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        private TextView mMessage;
        private LinearLayout mContainer;

        public ChatViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mMessage = itemView.findViewById(R.id.message);
            mContainer = itemView.findViewById(R.id.container);

        }

        @Override
        public void onClick(View v) {
        }


    }

    @Override
    public ChatViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate((R.layout.item_chat), null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ChatViewHolder holder, int position) {
        holder.mMessage.setText(chatList.get(position).getMessage());
        if(chatList.get(position).getCurrentUser()){
            holder.mMessage.setGravity(Gravity.END);
            holder.mMessage.setTextColor(Color.parseColor("#b596fd"));
            holder.mContainer.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }
        else {
            holder.mMessage.setGravity(Gravity.END);
            holder.mMessage.setTextColor(Color.parseColor("#cabcfa"));
            holder.mContainer.setBackgroundColor(Color.parseColor("#FFFFFF"));
        }

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }


}
