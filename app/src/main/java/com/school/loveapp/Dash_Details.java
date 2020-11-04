package com.school.loveapp;

import android.content.Context;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class Dash_Details extends Fragment {

    DataBaseHandler mDataBaseHandler;
    RecyclerView rvDisplayUsers;
    DisplayUsersAdapter recyclerCartAdapter;
    ArrayList<USERList> cartsList = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_details, container, false);

        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mDataBaseHandler = new DataBaseHandler(getContext());
        rvDisplayUsers = view.findViewById(R.id.rvDisplayUsers);

        getCarDetails();

    }

    private void getCarDetails(){
        Cursor c = mDataBaseHandler.getCarDetails();
        if (c != null) {
            if (c.moveToFirst()) {
                do {
                    USERList cart = new USERList();
                    cart.setUserName(c.getString(c.getColumnIndex(DataBaseHandler.KEY_NAME)));
                    cart.setUserAGE(c.getString(c.getColumnIndex(DataBaseHandler.KEY_AGE)));
                    cart.setUserGender(c.getString(c.getColumnIndex(DataBaseHandler.KEY_GENDER)));
                    cart.setUserInter(c.getString(c.getColumnIndex(DataBaseHandler.KEY_INTER)));
                    cart.setUserImage(c.getBlob(c.getColumnIndex(DataBaseHandler.KEY_IMAGE)));

                    cartsList.add(cart);
                } while (c.moveToNext());
            }
        }
        System.out.println("GET___CAR___LIST___SIZE___"+cartsList.size());


        if(!cartsList.isEmpty()) {
            recyclerCartAdapter = new DisplayUsersAdapter(getActivity(), cartsList);
            rvDisplayUsers.setHasFixedSize(true);
            rvDisplayUsers.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
            rvDisplayUsers.setNestedScrollingEnabled(false);
            rvDisplayUsers.smoothScrollToPosition(0);
            rvDisplayUsers.setAdapter(/*new AlphaInAnimationAdapter(*/recyclerCartAdapter);
            rvDisplayUsers.setVisibility(View.VISIBLE);
        }else{
            rvDisplayUsers.setVisibility(View.GONE);
        }
    }

    public void onResume(){
        super.onResume();
    }


}
