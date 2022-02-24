package org.techtown.iwu;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

// 각 stamp 영역을 선택하면 나타나는 Activity

public class WhereStampActivity extends AppCompatActivity {
    FrameLayout stampinmap;
    TextView wheretxt;
    int userBuilding;  //major에 따른 건물
    int where;      //stampactivity에서 선택한 건물

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super .onCreate(savedInstanceState);
        setContentView(R.layout.activity_wherestamp); // oo호관 화면 실행

        Intent intent = getIntent();
        where = intent.getIntExtra("where", 1); // 몇 호관인지 정보 받아와서

        wheretxt = findViewById(R.id.wheretxt);

        if(where == 31) wheretxt.setText("미유카페");
        else if(where == 32) wheretxt.setText("솔찬공원");
        else wheretxt.setText(where+"호관");

        userBuilding = intent.getIntExtra("u_Building", 15); //u_Building default 15

        stampinmap = (FrameLayout) findViewById(R.id.mapframe); // frame 영역에 위치 넣기
        stampinmap.addView(new PointView(this)); // frame 위에 그리기
    }

    public class PointView extends View { // frame 위에 위치 나타내기 시작
        Paint paint = new Paint();

        public PointView(Context context) {
            super(context);
        }

        @Override
        public void onDraw(Canvas canvas) {
            Bitmap img = BitmapFactory.decodeResource(getResources(),R.drawable.marker);
            Bitmap img_red = BitmapFactory.decodeResource(getResources(),R.drawable.marker_red);
            Bitmap bigimg = Bitmap.createScaledBitmap(img, img.getWidth()/16, img.getHeight()/16, false);
            Bitmap bigimg_red = Bitmap.createScaledBitmap(img_red, img.getWidth()/11, img.getHeight()/11, false);

            //각 건물의 bitamp
            Bitmap stamp1, stamp2, stamp6, stamp11, stamp12, stamp17, stamp18, stamp24, stamp30, stamp31, stamp32, stampsele;

            //where은 -> bigimg_red, 나머지는 bigimag
            if(where == 1) stamp1 = bigimg_red; else stamp1 = bigimg;
            if(where == 2) stamp2 = bigimg_red; else stamp2 = bigimg;
            if(where == 6) stamp6 = bigimg_red; else stamp6 = bigimg;
            if(where == 11) stamp11 = bigimg_red; else stamp11 = bigimg;
            if(where == 12) stamp12 = bigimg_red; else stamp12 = bigimg;
            if(where == 17) stamp17 = bigimg_red; else stamp17 = bigimg;
            if(where == 18) stamp18 = bigimg_red; else stamp18 = bigimg;
            if(where == 24) stamp24 = bigimg_red; else stamp24 = bigimg;
            if(where == 30) stamp30 = bigimg_red; else stamp30 = bigimg;
            if(where == 31) stamp31 = bigimg_red; else stamp31 = bigimg;
            if(where == 32) stamp32 = bigimg_red; else stamp32 = bigimg;
            if(where == userBuilding) stampsele = bigimg_red; else stampsele = bigimg;

            //기본 건물 11개 찍기
            canvas.drawBitmap(stamp1, 727, 820, paint); // 1호관 찍히는 위치
            canvas.drawBitmap(stamp2, 790, 700, paint); // 2호관
            canvas.drawBitmap(stamp6, 425, 855, paint); // 6호관
            canvas.drawBitmap(stamp11, 365, 530, paint); // 11 호관
            canvas.drawBitmap(stamp12, 485, 610, paint); // 12 호관
            canvas.drawBitmap(stamp17, 360, 360, paint); // 17 호관
            canvas.drawBitmap(stamp18, 320, 270, paint); // 18 호관
            canvas.drawBitmap(stamp24, 606, 1220, paint); // 24호관
            canvas.drawBitmap(stamp30, 255, 1020, paint); // 30호관
            canvas.drawBitmap(stamp31, 105, 555, paint); // 미유카페 >> 31
            canvas.drawBitmap(stamp32, 123, 135, paint); // 솔찬공원 >> 32

            //사용자의 전공(mapselect)에 따른 건물 찍기
            if (userBuilding == 5) canvas.drawBitmap(stampsele, 545, 980, paint); // 5호관
            else if (userBuilding == 7) canvas.drawBitmap(stampsele, 335, 795, paint); // 7호관
            else if (userBuilding == 8) canvas.drawBitmap(stampsele, 235, 695, paint); // 8호관
            else if (userBuilding == 13) canvas.drawBitmap(stampsele, 644, 702, paint); // 13 호관
            else if (userBuilding == 14) canvas.drawBitmap(stampsele, 680, 600, paint); // 14 호관
            else if (userBuilding == 15) canvas.drawBitmap(stampsele, 540, 478, paint); // 15 호관
            else if (userBuilding == 16) canvas.drawBitmap(stampsele, 430, 400, paint); // 16 호관
            else if (userBuilding == 20) canvas.drawBitmap(stampsele, 480, 150, paint); // 20 호관
                //else if (userBuilding == 23) canvas.drawBitmap(stampsele, 606, 1220, paint); // 23호관 강당(userBuilding 없어서 일단 제외함)
            else if (userBuilding == 28) canvas.drawBitmap(stampsele, 0, 670, paint); // 28호관
            else if (userBuilding == 29) canvas.drawBitmap(stampsele, 105, 555, paint); // 29호관
        }
    }
}
