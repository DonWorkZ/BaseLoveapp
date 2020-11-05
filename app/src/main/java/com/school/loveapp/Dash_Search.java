package com.school.loveapp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


public class Dash_Search extends Fragment {

    DataBaseHandler mDataBaseHandler;
    EditText et_age1, et_age2, etGender, etInter;
    ImageView imgSearch;

    Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = getContext();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_search, container, false);

        mDataBaseHandler = new DataBaseHandler(mContext);

        et_age1 = root.findViewById(R.id.et_age1);
        et_age2 = root.findViewById(R.id.et_age2);
        etGender = root.findViewById(R.id.etGender);
        etInter = root.findViewById(R.id.etInter);

        imgSearch = root.findViewById(R.id.imgSearch);

        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (et_age1.getText().toString().isEmpty()) {
                    et_age1.setError("Invalid car");
                } else if (et_age2.getText().toString().isEmpty()) {
                    et_age2.setError("Invalid minimum");
                } else if (etGender.getText().toString().isEmpty()) {
                    etGender.setError("Invalid maximum");
                }else if (etInter.getText().toString().isEmpty()) {
                    etInter.setError("Invalid maximum");
                }  else {
                    int count = mDataBaseHandler.CheckcarCount(et_age1.getText().toString(), et_age2.getText().toString(), etGender.getText().toString(), etInter.getText().toString());
                    if (count == 0) {
                        Toast.makeText(mContext, "no match", Toast.LENGTH_LONG).show();
                    } else {
                        Intent i = new Intent(mContext, ViewUserProfile.class);
                        i.putExtra("AGE1", et_age1.getText().toString());
                        i.putExtra("AGE2", et_age2.getText().toString());
                        i.putExtra("GENDER", etGender.getText().toString());
                        i.putExtra("INTER", etInter.getText().toString());
                        startActivity(i);
                    }

                }
            }
        });

    }

    public void onResume(){
        super.onResume();
    }



}
