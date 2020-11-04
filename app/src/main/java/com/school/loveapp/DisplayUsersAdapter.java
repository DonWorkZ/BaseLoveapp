package com.school.loveapp;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;


import java.io.ByteArrayInputStream;
import java.util.ArrayList;

public class DisplayUsersAdapter extends RecyclerView.Adapter<DisplayUsersAdapter.MyViewHolder> {

    private ArrayList<USERList> usertArrayList;

    Context mContext;
    private View view;

    public DisplayUsersAdapter(Activity mActivity, ArrayList<USERList> cartList) {

        this.usertArrayList = cartList;
        this.mContext = mActivity;

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView tvUserName, tvUserAge, tvUserInter;
        ImageView UserImage;


        public MyViewHolder(View view) {
            super(view);
            tvUserName = view.findViewById(R.id.tvUserName);
            tvUserAge = view.findViewById(R.id.tvUserAge);
            tvUserInter = view.findViewById(R.id.tvUserInter);
            UserImage = view.findViewById(R.id.userImage);


        }
    }


    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_users, parent, false);
        return new DisplayUsersAdapter.MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        byte[] outImage=usertArrayList.get(position).getUserImage();
        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
        holder.UserImage.setImageBitmap(theImage);

        holder.tvUserName.setText(usertArrayList.get(position).getUserName());
        holder.tvUserAge.setText(usertArrayList.get(position).getUserAGE());
        holder.tvUserInter.setText(usertArrayList.get(position).getUserInter());
    }
    @Override
    public int getItemCount() {

        return usertArrayList.size();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


}

