package com.school.loveapp;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatTextView;


import java.io.ByteArrayInputStream;

public class ViewUserProfile extends AppCompatActivity {

    Context mContext;
    DataBaseHandler mDataBaseHandler;
    USERList cart = new USERList();
    public TextView tvUserName, tvUserAge, tvUserInter;

    ImageView img_post;
    EditText et_message;
    Button send;
    String oldMSG;

    int userid ;
    String username;


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

        img_post=findViewById(R.id.img_post);
        et_message=findViewById(R.id.et_message);
        send=findViewById(R.id.send);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mDataBaseHandler.updateData(oldMSG + " \n"+ et_message.getText().toString(), username);
            }
        });

        img_post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showAlertDialog();
            }
        });

        getCarDetails();

    }

    private void showAlertDialog() {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);  ;
        //Uncomment the below code to Set the message and title from the strings.xml file
        builder.setMessage("R.string.dialog_message") .setTitle("R.string.dialog_title");

        //Setting message manually and performing action on button click
        builder.setMessage(oldMSG + " \n"+ et_message.getText().toString())
                .setCancelable(false)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(),"you choose yes action for alertbox",
                                Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        //  Action for 'NO' Button
                        dialog.cancel();
                        Toast.makeText(getApplicationContext(),"you choose no action for alertbox",
                                Toast.LENGTH_SHORT).show();
                    }
                });
        //Creating dialog box
        AlertDialog alert = builder.create();
        //Setting the title manually
        alert.setTitle("AlertDialogExample");
        alert.show();
    }

    private void getCarDetails(){
        Cursor c = mDataBaseHandler.getCarDetailsSingle(getIntent().getStringExtra("GENDER"), getIntent().getStringExtra("INTER"));
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    cart.setUserID(c.getString(c.getColumnIndex(DataBaseHandler.KEY_ID)));
                    cart.setUserName(c.getString(c.getColumnIndex(DataBaseHandler.KEY_NAME)) );
                    cart.setUserAGE(c.getString(c.getColumnIndex(DataBaseHandler.KEY_AGE)));
                    cart.setUserInter(c.getString(c.getColumnIndex(DataBaseHandler.KEY_INTER)));
                    cart.setUserMSG(c.getString(c.getColumnIndex(DataBaseHandler.KEY_MSG)));
                    cart.setUserImage(c.getBlob(c.getColumnIndex(DataBaseHandler.KEY_IMAGE)) );


                } while (c.moveToNext());
            }
            userid = Integer.parseInt(cart.getUserID());
            username = cart.getUserName();
            oldMSG = cart.getUserMSG();


            tvUserName.setText(cart.getUserName());
            tvUserAge.setText(cart.getUserAGE());
            tvUserInter.setText(cart.getUserInter());
            byte[] outImage=cart.getUserImage();
            ByteArrayInputStream imageStream = new ByteArrayInputStream(outImage);
            Bitmap theImage = BitmapFactory.decodeStream(imageStream);
            UserImage.setImageBitmap(theImage);
        }else {
            tvUserName.setText("NO name");
            tvUserAge.setText("NO age");
            tvUserInter.setText("NO inerest");

        }




    }
}