package com.school.loveapp;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.loader.content.CursorLoader;

import java.io.ByteArrayOutputStream;

import static android.app.Activity.RESULT_OK;

public class Dash_Enter extends Fragment {

    EditText etName, etAge, etGender, etInter;
    ImageView imgAdd;

    DataBaseHandler mDataBaseHandler;
    Context mContext;

    private static final int CAMERA_REQUEST = 1;
    private static final int SD_REQUEST = 2;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View root = inflater.inflate(R.layout.fragment_enter, container, false);

        etName = root.findViewById(R.id.etName);
        etAge = root.findViewById(R.id.etAge);
        etGender = root.findViewById(R.id.etGender);
        etInter = root.findViewById(R.id.etInter);

        imgAdd = root.findViewById(R.id.imgAdd);

        mContext = getContext();
        mDataBaseHandler = new DataBaseHandler(mContext);

        return root;
    }

    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        imgAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (etName.getText().toString().isEmpty()) {
                    etName.setError("Invalid name");
                } else if (etAge.getText().toString().isEmpty()) {
                    etAge.setError("Invalid age");
                } else if (etGender.getText().toString().isEmpty()) {
                    etGender.setError("Invalid gender");
                }else if (etInter.getText().toString().isEmpty()) {
                    etInter.setError("Invalid interest");
                }else {
                    final String[] option = new String[] {"Camera","Gallery"};
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(mContext,
                            android.R.layout.select_dialog_item, option);
                    AlertDialog.Builder builder = new AlertDialog.Builder(mContext);

                    builder.setTitle("Select Option");
                    builder.setAdapter(adapter, new DialogInterface.OnClickListener() {

                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                            if (which == 0) {
                                callCamera();
                            }else{
                                if (Build.VERSION.SDK_INT >= 23) {
                                    int permissionCheck = ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE);
                                    if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                                        ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
                                    }

                                    Intent sdintent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                                    sdintent.setType("image/*");
                                    startActivityForResult(sdintent, SD_REQUEST);
                                }
                            }


                        }
                    });
                    final AlertDialog dialog = builder.create();
                    dialog.show();
                }
            }
        });

    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != RESULT_OK)
            return;

        switch (requestCode) {
            case CAMERA_REQUEST:

                Bundle extras = data.getExtras();

                if (extras != null) {
                    Bitmap yourImage = extras.getParcelable("data");
                    // convert bitmap to byte
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    yourImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    byte imageInByte[] = stream.toByteArray();

                    // Inserting Contacts
                    mDataBaseHandler.insertCars(etName.getText().toString(), etAge.getText().toString(), etGender.getText().toString(), etInter.getText().toString(),imageInByte);
                    etName.setText("");
                    etAge.setText("");
                    etGender.setText("");
                    etInter.setText("");


                }
                break;
            case SD_REQUEST:

                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                CursorLoader loader = new CursorLoader(mContext, selectedImage, filePathColumn, null, null, null);

                Cursor cursor = mContext.getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap yourSelectedImage = BitmapFactory.decodeFile(filePath);

                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                yourSelectedImage.compress(Bitmap.CompressFormat.PNG, 100, stream);

                mDataBaseHandler.insertCars(etName.getText().toString(), etAge.getText().toString(), etGender.getText().toString(), etInter.getText().toString(),stream.toByteArray());
                etName.setText("");
                etAge.setText("");
                etGender.setText("");
                etInter.setText("");

//                Toast.makeText(getContext(), "Gallery pending", Toast.LENGTH_SHORT).show();

                break;
        }
    }

    public void callCamera()
    {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, CAMERA_REQUEST);
        intent.setType("image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", 0);
        intent.putExtra("aspectY", 0);
        intent.putExtra("outputX", 250);
        intent.putExtra("outputY", 200);
    }

}
