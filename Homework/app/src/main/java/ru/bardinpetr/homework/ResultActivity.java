package ru.bardinpetr.homework;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

public class ResultActivity extends AppCompatActivity {
    ImageView iv;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        tv = findViewById(R.id.name_tv);
        iv = findViewById(R.id.imageView);

        tv.setText(getIntent().getStringExtra(MainActivity.KEY_NAME));
        iv.setImageBitmap(preparePicture(getIntent().getStringExtra(MainActivity.KEY_PHOTO_PATH)));
    }

    private Bitmap preparePicture(String path) {
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, bmOptions);
        int photoW = bmOptions.outWidth;
        int photoH = bmOptions.outHeight;

        bmOptions.inJustDecodeBounds = false;
        return BitmapFactory.decodeFile(path, bmOptions);
    }
}
