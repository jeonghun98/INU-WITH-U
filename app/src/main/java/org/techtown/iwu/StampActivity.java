package org.techtown.iwu;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;

// 메인화면에서 stamp 버튼 클릭 시 나타나는 Activity

public class StampActivity extends AppCompatActivity {
    Button Mapstamp;
    int mapselect;
    TextView u_mid;

    //wherestampactivity와 mapinstampactivity에서 변수 사용의 용이함을 위해 where과 mapselect를 string에서 int형으로 교체함

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stamp);
        ButterKnife.bind(this); // ButterKnife 라이브러리 사용 (gradle에 따로 선언 해야 함)

        Intent intent = getIntent();
        int MajorCode = intent.getIntExtra("u_mid", 29); // 앞의 MainActivity에서 majorcode 받음

        // MajorCode에 따른 마지막 스탬프 위치 설정
        if (1 <= MajorCode && MajorCode <= 6) mapselect = 15;
        else if (7 <= MajorCode && MajorCode <= 11) mapselect = 5;
        else if (12 <= MajorCode && MajorCode <= 21) mapselect = 13;
        else if (22 <= MajorCode && MajorCode <= 29) mapselect = 8;
        else if (30 <= MajorCode && MajorCode <= 32) mapselect = 7;
        else if (33 <= MajorCode && MajorCode <= 34) mapselect = 14;
        else if (35 <= MajorCode && MajorCode <= 39) mapselect = 16;
        else if (40 <= MajorCode && MajorCode <= 47) mapselect = 20;
        else if (48 <= MajorCode && MajorCode <= 50) mapselect = 28;
        else if (51 <= MajorCode && MajorCode <= 53) mapselect = 29;

        u_mid = (TextView) findViewById(R.id.stamp12txt); // 12번째 스탬프 textview 주소 받아서
        u_mid.setText(mapselect + "호관"); // 위의 mapselect 삽입

        Mapstamp = (Button) findViewById(R.id.MapStamp); // stamp내의 2D MAP으로 스탬프현황 보기
        Mapstamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapInStampActivity.class); // MapInStampActivity 실행
                intent.putExtra("mapselect", mapselect);
                startActivity(intent); // MapInStampActivity 수행행
           }
        });
    }


    // 각 건물의 stamp 영역을 누르면 나타나는 WhereActivity 설정
   @OnClick(R.id.stamp1) void where1(){
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 1);
        intent.putExtra("mapselect", mapselect);
        startActivity(intent);
    }

    @OnClick(R.id.stamp2) void where2() {
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 2);
        intent.putExtra("mapselect", mapselect);
        startActivity(intent);
    }

    @OnClick(R.id.stamp3) void where3() {
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 6);
        intent.putExtra("mapselect", mapselect);
        startActivity(intent);
    }

    @OnClick(R.id.stamp4) void where4() {
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 11);
        intent.putExtra("mapselect", mapselect);
        startActivity(intent);
    }

    @OnClick(R.id.stamp5) void where5(){
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 12);
        intent.putExtra("mapselect", mapselect);
        startActivity(intent);
    }

    @OnClick(R.id.stamp6) void where6(){
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 17);
        intent.putExtra("mapselect", mapselect);
        startActivity(intent);
    }

    @OnClick(R.id.stamp7) void where7(){
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 18);
        intent.putExtra("mapselect", mapselect);
        startActivity(intent);
    }

    @OnClick(R.id.stamp8) void where8(){
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 24);
        intent.putExtra("mapselect", mapselect);
        startActivity(intent);
    }

    @OnClick(R.id.stamp9) void where9() {
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 30);
        intent.putExtra("mapselect", mapselect);
        startActivity(intent);
    }

    @OnClick(R.id.stamp10) void where10() {
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 31); //미유카페
        intent.putExtra("mapselect", mapselect);
        startActivity(intent);
    }

    @OnClick(R.id.stamp11) void where11() {
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 32); //솔찬공원
        intent.putExtra("mapselect", mapselect);
        startActivity(intent);
    }

    @OnClick(R.id.stamp12) void where12() {
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", mapselect);
        intent.putExtra("mapselect", mapselect);
        startActivity(intent);
    }

}
