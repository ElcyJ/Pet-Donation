package com.example.elcy.apppet;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.util.List;

public class PetArrayAdapter extends ArrayAdapter<Card> {

    Context context;

    public PetArrayAdapter(Context context, int resourceId, List<Card> items){
        super(context, resourceId, items);
    }

    public View getView(int position, View convertView, ViewGroup parent){
        Card card_item = getItem(position);

        if (convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.item, parent, false);
        }

        TextView sex = (TextView)(convertView.findViewById(R.id.nameCard));
        TextView owner = (TextView)(convertView.findViewById(R.id.ownerCard));
        ImageView image = convertView.findViewById(R.id.image);


        sex.setText(card_item.getSex());
        owner.setText(card_item.getOwnerId());
        Glide.with(getContext()).load(card_item.getPetImageUrl()).into(image);

        return convertView;
    }


}
