package twitphoto.photo.grid.maker;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import twitphoto.photo.grid.maker.adapters.GridTwoImageAdapter;

public class MainActivity extends AppCompatActivity {

    ImageView imgImage;
    public static final int PICK_IMAGE = 1;
    public static final int PICK_IMAGE_2 = 2;
    Bitmap yourBitmap;
    Bitmap resized;
    ArrayList<Bitmap> globalBmp = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imgImage = findViewById(R.id.ivImage);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void uploadImage(View view) {

        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            return;
        }


        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE);
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    public void uploadImage2(View view) {

        if (checkSelfPermission(android.Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {

        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 101);
            return;
        }


        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent, PICK_IMAGE_2);
    }


    public void uploadImage3(View view) {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)

                .setAspectRatio(30, 17)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .start(this);

    }

    public void resizeImage(View view) {
        resized = Bitmap.createScaledBitmap(yourBitmap, 400, 400, true);
        imgImage.setImageBitmap(resized);
    }

    public void resizeImage2(View view) {


        //This handles the resize
        int ImageWidth = yourBitmap.getWidth();
        int ImageHeight = yourBitmap.getHeight();
        int scaledHeight = (int) (ImageWidth / 1.76470588235);

        int scaledWidth = 0;

        int xCo = 0;
        int yCo = (int) ((ImageHeight - scaledHeight) / 2);
        Log.e("hh", "y = " + yCo + " Imageheight = " + ImageHeight + " scaledheight = " + scaledHeight
                + "");

        if (yCo <= 0) {
             scaledWidth = (int) (ImageHeight * 1.76470588235);

            yCo = 0;
             xCo = (int) ((ImageWidth - scaledWidth) / 2);



            resized = Bitmap.createBitmap(yourBitmap, xCo, yCo, scaledWidth, ImageHeight);
            imgImage.setImageBitmap(resized);
        }
        else {
            resized = Bitmap.createBitmap(yourBitmap, xCo, yCo, ImageWidth, scaledHeight);
            imgImage.setImageBitmap(resized);

        }






/////////////This Handles the division


        int smallimage_Height = yourBitmap.getHeight() /1;
        int smallimage_Width = yourBitmap.getWidth() / 2;

        int yCo2 = 0;
        ArrayList<Bitmap> smallImage = new ArrayList<>();
        int num = 2;

        for (int i = 0; i < 1; i++) {
            int xCo2 = 0;
            for (int j = 0; j < num; j++) {

                Bitmap bmp = null;
                if (yCo <= 0) {
                    bmp = Bitmap.createBitmap(resized, xCo2, yCo2, scaledWidth/2, smallimage_Height);
                    smallImage.add(bmp);
                    xCo2 += scaledWidth/2;
                } else {

                    bmp = Bitmap.createBitmap(resized, xCo2, yCo2, smallimage_Width, scaledHeight);
                    smallImage.add(bmp);
                    xCo2 += smallimage_Width;
                }

            }



        }


        globalBmp = smallImage;

        GridView image_grid = (GridView) findViewById(R.id.gridView);

        image_grid.setAdapter(new GridTwoImageAdapter(this, smallImage));

        image_grid.setNumColumns(2);
    }




    public void split_image_only(View view) {


        int imageHeight = yourBitmap.getHeight();
        int imageWidth = yourBitmap.getWidth();

/////////////This Handles the division


        int smallimage_Width = yourBitmap.getWidth() / 2;

        int yCo2 = 0;
        ArrayList<Bitmap> smallImage = new ArrayList<>();
        int num = 2;

        for (int i = 0; i < 1; i++) {
            int xCo2 = 0;
            for (int j = 0; j < num; j++) {

                Bitmap  bmp = Bitmap.createBitmap(yourBitmap, xCo2, yCo2, imageWidth/2, imageHeight);
                    smallImage.add(bmp);
                    xCo2 += imageWidth/2;
            }

        }


        globalBmp = smallImage;

        GridView image_grid = (GridView) findViewById(R.id.gridView);

        image_grid.setAdapter(new GridTwoImageAdapter(this, smallImage));
        image_grid.setNumColumns(2);
    }



    public void resizeImage3(View view) {


        //This handles the resize
        int ImageWidth = yourBitmap.getWidth();
        int ImageHeight = yourBitmap.getHeight();
        int scaledHeight = (int) (ImageWidth / 1.76470588235);

        int scaledWidth = 0;

        int xCo = 0;
        int yCo = (int) ((ImageHeight - scaledHeight) / 2);
        Log.e("hh", "y = " + yCo + " Imageheight = " + ImageHeight + " scaledheight = " + scaledHeight
                + "");

        if (yCo <= 0) {
            scaledWidth = (int) (ImageHeight * 1.76470588235);

            yCo = 0;
            xCo = (int) ((ImageWidth - scaledWidth) / 2);



            resized = Bitmap.createBitmap(yourBitmap, xCo, yCo, scaledWidth, ImageHeight);
            imgImage.setImageBitmap(resized);
        }
        else {
            resized = Bitmap.createBitmap(yourBitmap, xCo, yCo, ImageWidth, scaledHeight);
            imgImage.setImageBitmap(resized);

        }






/////////////This Handles the division


        int smallimage_Height = yourBitmap.getHeight() /2;
        int smallimage_Width = yourBitmap.getWidth() / 2;

        int yCo2 = 0;
        ArrayList<Bitmap> smallImage = new ArrayList<>();
        int num = 2;

        for (int i = 0; i < num; i++) {
            int xCo2 = 0;
            for (int j = 0; j < num; j++) {

                Bitmap bmp = null;
                if (yCo <= 0) {
                    bmp = Bitmap.createBitmap(resized, xCo2, yCo2, scaledWidth/2, smallimage_Height);
                    smallImage.add(bmp);
                    xCo2 += scaledWidth/2;
                } else {

                    bmp = Bitmap.createBitmap(resized, xCo2, yCo2, smallimage_Width, scaledHeight/2);
                    smallImage.add(bmp);
                    xCo2 += smallimage_Width;
                }

            }

            if (yCo <= 0) {
                yCo2 += smallimage_Height;
            } else {
               yCo2 += scaledHeight/2;
            }


        }
imgImage.setVisibility(View.GONE);

        globalBmp = smallImage;

        GridView image_grid = (GridView) findViewById(R.id.gridView);

        image_grid.setAdapter(new GridTwoImageAdapter(this, smallImage));

        image_grid.setNumColumns(2);
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                Uri imageUri = data.getData();
                yourBitmap =
                        MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imgImage.setImageBitmap(yourBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == PICK_IMAGE_2 && resultCode == Activity.RESULT_OK) {
            if (data == null) {
                //Display an error
                return;
            }
            try {
                Uri imageUri = data.getData();
                yourBitmap =
                        MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imgImage.setImageBitmap(yourBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                try {
                    imgImage.setImageURI(result.getUri());
                    yourBitmap =
                            MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());
                    Toast.makeText(
                            this, "Cropping successful, Sample: " + result.getSampleSize(), Toast.LENGTH_LONG)
                            .show();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Toast.makeText(this, "Cropping failed: " + result.getError(), Toast.LENGTH_LONG).show();
            }
        }


    }


    public void saveImage(View view) {

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
//                    saveBitmapFile(resized);
                    File mediaStorageDir = new File(
                            Environment.getExternalStorageDirectory(),
                            "easyTouchPro2");


                    String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                            .format(Calendar.getInstance().getTime());
                    String mCurrentPath = mediaStorageDir.getPath() + File.separator
                            + "IMG_" + timeStamp + ".jpg";
                    File mediaFile = new File(mCurrentPath);


                    Log.e("hh", mCurrentPath);

                    mediaFile.getParentFile().mkdirs();

                    mediaFile.createNewFile();

                    FileOutputStream stream = new FileOutputStream(mediaFile);
                    resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
                    //   yourBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                    stream.close();


                } catch (final IOException e) {
                    e.printStackTrace();


                }
            }

            @Override
            protected void finalize() throws Throwable {
                Log.e("Twitter", "finished");
            }
        };
        t.start();


    }


    public void saveImage2(View view) {



        for (int i = 0; i<globalBmp.size(); i++) {


            SaveTask2 saveTask = new SaveTask2(globalBmp.get(i));
            saveTask.execute();
            Log.e("ff", "doing" + i);
        }

    }

    public void nextPage(View view) {
        startActivity(new Intent(this, Main2Activity.class));
    }


    public class SaveTask extends AsyncTask<Void, Void, Long> {

        private Bitmap bitmap;

        public SaveTask(Bitmap bitmap) {
            bitmap = bitmap;
        }


        @Override
        protected Long doInBackground(Void... voids) {
            try {
//                    saveBitmapFile(resized);
                File mediaStorageDir = new File(
                        Environment.getExternalStorageDirectory(),
                        "easyTouchPro2");


                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                        .format(Calendar.getInstance().getTime());
                String mCurrentPath = mediaStorageDir.getPath() + File.separator
                        + "IMG_" + timeStamp + ".jpg";
                File mediaFile = new File(mCurrentPath);


                Log.e("hh", mCurrentPath);

                mediaFile.getParentFile().mkdirs();

                mediaFile.createNewFile();

                FileOutputStream stream = new FileOutputStream(mediaFile);
                resized.compress(Bitmap.CompressFormat.PNG, 100, stream);
                //   yourBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                stream.close();


            } catch (final IOException e) {
                e.printStackTrace();


            }

            return null;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            Log.e("hh", "i don finish");
            super.onPostExecute(aLong);
        }
    }


    public class SaveTask2 extends AsyncTask<Void, Void, Long> {

        private Bitmap bitmap;

        public SaveTask2(Bitmap bitmap) {
            this.bitmap = bitmap;
        }


        @Override
        protected Long doInBackground(Void... voids) {
            try {
//                    saveBitmapFile(resized);
                File mediaStorageDir = new File(
                        Environment.getExternalStorageDirectory(),
                        "easyTouchPro2");


                String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                        .format(Calendar.getInstance().getTime()) + getSaltString();
                String mCurrentPath = mediaStorageDir.getPath() + File.separator
                        + "IMG_" + timeStamp + ".jpg";
                File mediaFile = new File(mCurrentPath);


                Log.e("hh", mCurrentPath);

                mediaFile.getParentFile().mkdirs();

                mediaFile.createNewFile();

                FileOutputStream stream = new FileOutputStream(mediaFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
                //   yourBitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);

                stream.close();


            } catch (final IOException e) {
                e.printStackTrace();


            }

            return null;
        }

        @Override
        protected void onPostExecute(Long aLong) {
            Log.e("hh", "i don finish");
            super.onPostExecute(aLong);
        }
    }


    public void saveBitmapFile(Bitmap bitmap) throws IOException {
        File mediaFile = getOutputMediaFile();
        FileOutputStream fileOutputStream = new FileOutputStream(mediaFile);
        bitmap.compress(Bitmap.CompressFormat.JPEG, getQualityNumber(bitmap), fileOutputStream);
        fileOutputStream.flush();
        fileOutputStream.close();
    }

    File getOutputMediaFile() {
        File mediaStorageDir = new File(
                Environment.getExternalStorageDirectory(),
                "easyTouchPro");
        if (mediaStorageDir.isDirectory()) {

            // Create a media file name
            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss")
                    .format(Calendar.getInstance().getTime());
            String mCurrentPath = mediaStorageDir.getPath() + File.separator
                    + "IMG_" + timeStamp + ".jpg";
            File mediaFile = new File(mCurrentPath);
            return mediaFile;
        } else { /// error handling for PIE devices..
            mediaStorageDir.delete();
            mediaStorageDir.mkdirs();
            galleryAddPic(mediaStorageDir);

            return (getOutputMediaFile());
        }
    }

    void galleryAddPic(File f) {
        Intent mediaScanIntent = new Intent(
                "android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        Uri contentUri = Uri.fromFile(f);
        mediaScanIntent.setData(contentUri);

        this.sendBroadcast(mediaScanIntent);
    }

    public static int getQualityNumber(Bitmap bitmap) {
        int size = bitmap.getByteCount();
        int percentage = 0;

        if (size > 500000 && size <= 800000) {
            percentage = 15;
        } else if (size > 800000 && size <= 1000000) {
            percentage = 20;
        } else if (size > 1000000 && size <= 1500000) {
            percentage = 25;
        } else if (size > 1500000 && size <= 2500000) {
            percentage = 27;
        } else if (size > 2500000 && size <= 3500000) {
            percentage = 30;
        } else if (size > 3500000 && size <= 4000000) {
            percentage = 40;
        } else if (size > 4000000 && size <= 5000000) {
            percentage = 50;
        } else if (size > 5000000) {
            percentage = 75;
        }

        return percentage;
    }

    protected String getSaltString() {
        String SALTCHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
        StringBuilder salt = new StringBuilder();
        Random rnd = new Random();
        while (salt.length() < 3) { // length of the random string.
            int index = (int) (rnd.nextFloat() * SALTCHARS.length());
            salt.append(SALTCHARS.charAt(index));
        }
        String saltStr = salt.toString();
        return saltStr;

    }
}
