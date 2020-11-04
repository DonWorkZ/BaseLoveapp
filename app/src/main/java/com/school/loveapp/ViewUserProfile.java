package com.school.loveapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;


import java.io.ByteArrayInputStream;

public class ViewUserProfile extends AppCompatActivity {

    Context mContext;
    DataBaseHandler mDataBaseHandler;
    USERList cart = new USERList();
    public TextView tvUserName, tvUserAge, tvUserInter;


    ImageView UserImage;
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_user_profile);
        mContext=this;
        mDataBaseHandler=new DataBaseHandler(mContext);

        tvUserName=findViewById(R.id.tvUserName);
        tvUserAge=findViewById(R.id.tvUserAge);
        tvUserInter=findViewById(R.id.tvUserInter);
        UserImage=findViewById(R.id.UserImage);

        getCarDetails();

    }
    private void getCarDetails(){
        Cursor c = mDataBaseHandler.getCarDetailsSingle(getIntent().getStringExtra("GENDER"), getIntent().getStringExtra("INTER"));
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    cart.setUserName(c.getString(c.getColumnIndex(DataBaseHandler.KEY_NAME)) );
                    cart.setUserAGE(c.getString(c.getColumnIndex(DataBaseHandler.KEY_AGE)));
                    cart.setUserInter(c.getString(c.getColumnIndex(DataBaseHandler.KEY_INTER)));
//                    cart.setUserImage(c.getBlob(c.getColumnIndex(DataBaseHandler.KEY_IMAGE)) );


                } while (c.moveToNext());
            }
        }

        tvUserName.setText(cart.getUserName());
        tvUserAge.setText(cart.getUserAGE());
        tvUserInter.setText(cart.getUserInter());
//        byte[] outImage=cart.getUserImage();
//        ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
//        Bitmap theImage = BitmapFactory.decodeStream(imageStream);
//        UserImage.setImageBitmap(theImage);


    }
}