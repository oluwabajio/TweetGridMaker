package twitphoto.photo.grid.maker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {


    Bitmap yourBitmap;
    ImageView imgImage, displayImage;
    public static final int PICK_IMAGE = 1;
    public static final int PICK_IMAGE_2 = 2;
    Bitmap resized;
    ArrayList<Bitmap> globalBmp = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

       // imgImage = findViewById(R.id.ivImage);
        displayImage = findViewById(R.id.displayImage);
    }


    public void uploadImage(View view) {

        CropImage.activity()
                .setGuidelines(CropImageView.Guidelines.ON)

                .setAspectRatio(30, 17)
                .setCropShape(CropImageView.CropShape.RECTANGLE)
                .start(this);

    }

    public void split_image_only(View view) {


        int imageHeight = yourBitmap.getHeight();
        int imageWidth = yourBitmap.getWidth();



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

        displayImage.setImageBitmap(yourBitmap);

//        GridView image_grid = (GridView) findViewById(R.id.gridView);
//
//        image_grid.setAdapter(new GridTwoImageAdapter(this, smallImage));
//        image_grid.setNumColumns(2);
    }


    public void split_image_only() {
        int imageHeight = yourBitmap.getHeight();
        int imageWidth = yourBitmap.getWidth();

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
        displayImage.setImageBitmap(yourBitmap);

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
            //    imgImage.setImageBitmap(yourBitmap);
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
              //  imgImage.setImageBitmap(yourBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {

                try {
                   // imgImage.setImageURI(result.getUri());
                    yourBitmap =
                            MediaStore.Images.Media.getBitmap(this.getContentResolver(), result.getUri());

                    split_image_only();

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


        for (int i = 0; i<globalBmp.size(); i++) {

            MainActivity.SaveTask saveTask = new MainActivity.SaveTask(globalBmp.get(i), i);
            saveTask.execute();
            Log.e("ff", "doing" + i);
        }

    }





    public class SaveTask extends AsyncTask<Void, Void, Long> {

        private Bitmap bitmap;
        private int position;

        public SaveTask(Bitmap bitmap, int position) {
            this.bitmap = bitmap;
            this.position = position;
        }


        @Override
        protected Long doInBackground(Void... voids) {
            try {
//                    saveBitmapFile(resized);
                File mediaStorageDir = new File(
                        Environment.getExternalStorageDirectory(),
                        "TweetGrid");

                String fileName = "Twitter_File_00" + position + 1;
                String filePath = mediaStorageDir.getPath() + File.separator
                        + "IMG_" + fileName + ".jpg";
                File imageFile = new File(filePath);
                imageFile.getParentFile().mkdirs();
                imageFile.createNewFile();
                FileOutputStream stream = new FileOutputStream(imageFile);
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
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
}
