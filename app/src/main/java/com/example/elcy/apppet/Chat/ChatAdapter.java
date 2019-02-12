package com.example.elcy.apppet.Chat;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.elcy.apppet.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.FavoriteViewHolder>{
    private Context context;
    private List<ChatObject> chatList;

    public ChatAdapter(Context context, List<ChatObject> favoriteList) {
        this.context = context;
        this.chatList = favoriteList;
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{

        public FavoriteViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
        }


    }

    @Override
    public FavoriteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate((R.layout.item_favorite), null, false);
        RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        view.setLayoutParams(lp);

        return new FavoriteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FavoriteViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }


}
