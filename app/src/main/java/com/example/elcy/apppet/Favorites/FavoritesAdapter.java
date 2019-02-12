package com.example.elcy.apppet.Favorites;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.elcy.apppet.Chat.ChatActivity;
import com.example.elcy.apppet.R;

import java.util.List;

public class FavoritesAdapter extends RecyclerView.Adapter<FavoritesAdapter.FavoriteViewHolder>{
    private Context context;
    private List<FavoritesObject> favoriteList;

    public FavoritesAdapter(Context context, List<FavoritesObject> favoriteList) {
        this.context = context;
        this.favoriteList = favoriteList;
    }

    public static class FavoriteViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView mFavoriteId, mFavoriteSex, mFavoriteAge, mFavoriteOwner;
        ImageView mFavoriteImage;

        public FavoriteViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);

            mFavoriteId = itemView.findViewById(R.id.favoriteId);
            mFavoriteSex = itemView.findViewById(R.id.favoriteSex);
            mFavoriteAge = itemView.findViewById(R.id.favoriteAge);
            mFavoriteOwner = itemView.findViewById(R.id.favoriteOwner);
            mFavoriteImage = itemView.findViewById(R.id.favoriteImage);
        }

        @Override
        public void onClick(View v) {
            Intent intent = new Intent(v.getContext(), ChatActivity.class);
            Bundle b = new Bundle();
            b.putString("ownerId", mFavoriteOwner.getText().toString());
            intent.putExtras(b);
            v.getContext().startActivity(intent);
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
        holder.mFavoriteId.setText(favoriteList.get(position).getFavoriteId());
        holder.mFavoriteSex.setText(favoriteList.get(position).getFavoriteSex());
        holder.mFavoriteAge.setText(favoriteList.get(position).getFavoriteAge());
        holder.mFavoriteOwner.setText(favoriteList.get(position).getFavoriteOwnerId());
        Glide.with(context).load(favoriteList.get(position).getFavoriteImamgeUrl()).into(holder.mFavoriteImage);
    }

    @Override
    public int getItemCount() {
        return favoriteList.size();
    }


}
