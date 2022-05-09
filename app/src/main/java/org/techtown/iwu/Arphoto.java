package org.techtown.iwu;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class Arphoto extends AppCompatActivity {

    private int b_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_arphoto);

        ImageView image = (ImageView)findViewById(R.id.ar_photo);
        TextView text = (TextView)findViewById(R.id.ar_photo_text);

        Intent intent = getIntent();
        b_id = intent.getIntExtra("b_id", 0);

        if(b_id == 1) image.setImageResource(R.drawable.b_1);
        else if(b_id == 6) image.setImageResource(R.drawable.b_6);
        else if(b_id == 7) image.setImageResource(R.drawable.b_7);
        else if(b_id == 11) image.setImageResource(R.drawable.b_11);

        text.setText(b_id + "호관 인식 이미지");
    }
}