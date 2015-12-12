package com.example.khaalijeb.newlistview_module;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.AvoidXfermode;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Lord Voldemort on 02/10/2015.
 */
public class accountfragment extends Fragment implements View.OnClickListener {


    final int CAMERA_CAPTURE = 1;
    // keep track of cropping intent
    final int PIC_CROP = 2;
    // captured picture uri
    private Uri picUri;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_profile,container,false);
        return view;
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        TextView txt = (TextView)getView().findViewById(R.id.textname);
        Typeface tf = Typeface.createFromAsset(getActivity().getAssets(),"fonts/final.ttf");
        txt.setTypeface(tf);

        TextView txt1 = (TextView)getView().findViewById(R.id.textjeb);
        Typeface tf1 = Typeface.createFromAsset(getActivity().getAssets(),"fonts/final.ttf");
        txt1.setTypeface(tf1);


        TextView txt2 = (TextView)getView().findViewById(R.id.textmail);
        Typeface tf2 = Typeface.createFromAsset(getActivity().getAssets(),"fonts/final.ttf");
        txt2.setTypeface(tf2);

        TextView txt3 = (TextView)getView().findViewById(R.id.textphone);
        Typeface tf3 = Typeface.createFromAsset(getActivity().getAssets(),"fonts/final.ttf");
        txt3.setTypeface(tf3);

        android.support.design.widget.FloatingActionButton f1 = (android.support.design.widget.FloatingActionButton)getView().findViewById(R.id.capture_btnx);

        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(getActivity(), ChangePass.class);
                startActivity(i);

            }
        });


        ImageButton captureBtn = (ImageButton)getView().findViewById(R.id.capture_btn);
        captureBtn.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {


        if (v.getId() == R.id.capture_btn) {
            try {
                // use standard intent to capture an image
                Intent captureIntent = new Intent(
                        Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                // we will handle the returned data in onActivityResult
                startActivityForResult(captureIntent, CAMERA_CAPTURE);
            } catch (ActivityNotFoundException anfe) {
                // display an error message
                String errorMessage = "Whoops - your device doesn't support capturing images!";
                Toast toast = Toast.makeText(getActivity(), errorMessage,
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        SharedPreferences myprefs = getActivity().getSharedPreferences("image", Context.MODE_PRIVATE);
        myprefs.edit();
        if (resultCode == Activity.RESULT_OK) {
            // user is returning from capturing an image using the camera
            if (requestCode == CAMERA_CAPTURE) {
                // get the Uri for the captured image
                picUri = data.getData();
                // carry out the crop operation
                performCrop();
            }
            // user is returning from cropping the image
            else if (requestCode == PIC_CROP) {
                // get the returned data
                Bundle extras = data.getExtras();
                // get the cropped bitmap
                Bitmap thePic = extras.getParcelable("data");
                // retrieve a reference to the ImageView
                ImageView picView = (ImageView) getView().findViewById(R.id.picture);
                // display the returned cropped image
                GraphicsUtil graphicUtil = new GraphicsUtil();
                // picView.setImageBitmap(graphicUtil.getRoundedShape(thePic,(float)1.5,92));
                picView.setImageBitmap(graphicUtil.getCircleBitmap(
                        thePic, 16));
            }
        }
    }

    /**
     * Helper method to carry out crop operation
     */
    private void performCrop() {
        // take care of exceptions
        try {
            // call the standard crop action intent (the user device may not
            // support it)
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            // indicate image type and Uri
            cropIntent.setDataAndType(picUri, "image/*");
            // set crop properties
            cropIntent.putExtra("crop", "true");
            // indicate aspect of desired crop
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            // indicate output X and Y
            cropIntent.putExtra("outputX", 256);
            cropIntent.putExtra("outputY", 256);
            // retrieve data on return
            cropIntent.putExtra("return-data", true);
            // start the activity - we handle returning in onActivityResult
            startActivityForResult(cropIntent, PIC_CROP);
        }
        // respond to users whose devices do not support the crop action
        catch (ActivityNotFoundException anfe) {
            // display an error message
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast
                    .makeText(getActivity(), errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

}
