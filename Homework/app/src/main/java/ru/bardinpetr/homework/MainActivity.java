package ru.bardinpetr.homework;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    public static final Integer CAMERA_CAPTURE_REQUEST = 1;
    public static final String KEY_PHOTO_PATH = "user_photo_path";
    public static final String KEY_NAME = "user_name";

    EditText et;
    Button btn;

    String imageFileName = "temp_photo";
    String mCurrentPhotoPath;
    Intent i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et = findViewById(R.id.name_et);
        btn = findViewById(R.id.next_btn);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = et.getText().toString();
                if (!Objects.equals(name, "")) {
                    takePhoto();
                } else {
                    Toast.makeText(getApplicationContext(), "Нет текста", Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    private File createImageFile() throws IOException {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );
        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }

    private void takePhoto() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                Log.e("IO", ex.getMessage());
            }

            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this, "com.example.android.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, CAMERA_CAPTURE_REQUEST);
                getIntent().putExtra(KEY_PHOTO_PATH, mCurrentPhotoPath);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == CAMERA_CAPTURE_REQUEST && resultCode == RESULT_OK) {
            i = new Intent(getApplicationContext(), ResultActivity.class);
            i.putExtra(KEY_NAME, et.getText().toString());
            i.putExtra(KEY_PHOTO_PATH, getIntent().getStringExtra(KEY_PHOTO_PATH));
            startActivity(i);
        }
    }
}
