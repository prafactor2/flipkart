package com.example.khaalijeb.newlistview_module;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Profile extends BaseActivity implements View.OnClickListener {

    // keep track of camera capture intent
    final int CAMERA_CAPTURE = 1;
    // keep track of cropping intent
    final int PIC_CROP = 2;
    // captured picture uri
    private Uri picUri;

    /**
     * Called when the activity is first created.
     */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout frameLayout = (FrameLayout) findViewById(R.id.frame);
        // inflate the custom activity layout
        LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View activityView = layoutInflater.inflate(R.layout.activity_profile, null, false);
        // add the custom layout of this activity to frame layout.
        frameLayout.addView(activityView);

        TextView txt = (TextView) findViewById(R.id.textname);
        Typeface tf = Typeface.createFromAsset(getAssets(), "fonts/final.ttf");
        txt.setTypeface(tf);

        TextView txt1 = (TextView) findViewById(R.id.textjeb);
        Typeface tf1 = Typeface.createFromAsset(getAssets(), "fonts/final.ttf");
        txt1.setTypeface(tf1);


        TextView txt2 = (TextView) findViewById(R.id.textmail);
        Typeface tf2 = Typeface.createFromAsset(getAssets(), "fonts/final.ttf");
        txt2.setTypeface(tf2);

        TextView txt3 = (TextView) findViewById(R.id.textphone);
        Typeface tf3 = Typeface.createFromAsset(getAssets(), "fonts/final.ttf");
        txt3.setTypeface(tf3);

        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onBackPressed();

            }
        });

        FloatingActionButton f1 = (FloatingActionButton) findViewById(R.id.capture_btnx);

        f1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Profile.this, brandprofile.class);
                startActivity(i);

            }
        });

   /*     // retrieve a reference to the UI button
        android.support.design.widget.FloatingActionButton captureBtn = (android.support.design.widget.FloatingActionButton) findViewById(R.id.capture_btn);
        // handle button clicks
        captureBtn.setOnClickListener(this);




        ImageButton Button1 = (ImageButton) findViewById(R.id.capture_btnx);
        Button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent x = new Intent(Profile.this,WalletActivity.class);
                startActivity(x);

            }
        });

*/
        FloatingActionButton captureBtn = (FloatingActionButton) findViewById(R.id.capture_btn);
        captureBtn.setOnClickListener(this);
    }

    /**
     * Click method to handle user pressing button to launch camera
     */
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
                Toast toast = Toast.makeText(this, errorMessage,
                        Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

    /**
     * Handle user returning from both capturing and cropping the image
     */
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        SharedPreferences myprefs = getSharedPreferences("image", MODE_PRIVATE);
        myprefs.edit();
        if (resultCode == RESULT_OK) {
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
                ImageView picView = (ImageView) findViewById(R.id.picture);
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
                    .makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

  /*  private void fab() {

        final ImageView fabIconNew = new ImageView(this);

        fabIconNew.setImageDrawable(getResources().getDrawable(R.drawable.plus));
        final FloatingActionButton rightLowerButton = new FloatingActionButton.Builder(this)
                .setContentView(fabIconNew)
                .setBackgroundDrawable(R.drawable.floatingbuttoncolor)
                .build();


        SubActionButton.Builder rLSubBuilder = new SubActionButton.Builder(this);
        ImageView iconChat = new ImageView(this);
        ImageView iconCamera = new ImageView(this);
        ImageView iconVideo = new ImageView(this);
        ImageView iconPlace = new ImageView(this);

        iconChat.setImageDrawable(getResources().getDrawable(R.drawable.x2_1));
        iconCamera.setImageDrawable(getResources().getDrawable(R.drawable.n_image_4));
        iconVideo.setImageDrawable(getResources().getDrawable(R.drawable.n_image_3));
        iconPlace.setImageDrawable(getResources().getDrawable(R.drawable.aaaa));


        final FloatingActionMenu rightLowerMenu = new FloatingActionMenu.Builder(this)
                .addSubActionView(rLSubBuilder.setContentView(iconChat).build())
                .addSubActionView(rLSubBuilder.setContentView(iconCamera).build())
                .addSubActionView(rLSubBuilder.setContentView(iconVideo).build())
                .addSubActionView(rLSubBuilder.setContentView(iconPlace).build())
                .attachTo(rightLowerButton)
                .build();

    /*    iconChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(MainActivity.this, Chat.class);
                startActivity(i);

            }
        });

        iconCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent i1 = new Intent(MainActivity.this, Profile.class);
                startActivity(i1);
                finish();
            }
        });

        iconVideo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i2 = new Intent(MainActivity.this, setting.class);
                startActivity(i2);
                finish();
            }
        });

        iconPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i3 = new Intent(MainActivity.this, wallet.class);
                startActivity(i3);
                finish();
            }
        });
*/
  /*      rightLowerMenu.setStateChangeListener(new FloatingActionMenu.MenuStateChangeListener() {
            @Override
            public void onMenuOpened(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees clockwise
                fabIconNew.setRotation(0);
                PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofFloat(View.ROTATION, 45);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, propertyValuesHolder);
                animation.start();
            }

            @Override
            public void onMenuClosed(FloatingActionMenu menu) {
                // Rotate the icon of rightLowerButton 45 degrees counter-clockwise
                fabIconNew.setRotation(45);
                PropertyValuesHolder propertyValuesHolder = PropertyValuesHolder.ofFloat(View.ROTATION, 0);
                ObjectAnimator animation = ObjectAnimator.ofPropertyValuesHolder(fabIconNew, propertyValuesHolder);
                animation.start();
            }
        });
    }

*/

}




