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
    int userBuilding;
    TextView u_mid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stamp);
        ButterKnife.bind(this); // ButterKnife 라이브러리 사용 (gradle에 따로 선언 해야 함)

        Intent intent = getIntent();
        userBuilding = intent.getIntExtra("u_Building", 15); // 앞의 MainActivity에서 majorcode 받음

        u_mid = (TextView) findViewById(R.id.stamp12txt); // 12번째 스탬프 textview 주소 받아서
        u_mid.setText(userBuilding + "호관"); // 위의 mapselect 삽입

        Mapstamp = (Button) findViewById(R.id.MapStamp); // stamp내의 2D MAP으로 스탬프현황 보기
        Mapstamp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapInStampActivity.class); // MapInStampActivity 실행
                intent.putExtra("u_Building", userBuilding);
                startActivity(intent); // MapInStampActivity 수행행
            }
        });
        MarkStamp(); //스탬프찍음여부에 따라 아이콘 변경
    }


    // 각 건물의 stamp 영역을 누르면 나타나는 WhereActivity 설정
    @OnClick(R.id.stamp1) void where1(){
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 1);
        intent.putExtra("u_Building", userBuilding);
        startActivity(intent);
    }

    @OnClick(R.id.stamp2) void where2() {
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 2);
        intent.putExtra("u_Building", userBuilding);
        startActivity(intent);
    }

    @OnClick(R.id.stamp3) void where3() {
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 6);
        intent.putExtra("u_Building", userBuilding);
        startActivity(intent);
    }

    @OnClick(R.id.stamp4) void where4() {
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 11);
        intent.putExtra("u_Building", userBuilding);
        startActivity(intent);
    }

    @OnClick(R.id.stamp5) void where5(){
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 12);
        intent.putExtra("u_Building", userBuilding);
        startActivity(intent);
    }

    @OnClick(R.id.stamp6) void where6(){
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 17);
        intent.putExtra("u_Building", userBuilding);
        startActivity(intent);
    }

    @OnClick(R.id.stamp7) void where7(){
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 18);
        intent.putExtra("u_Building", userBuilding);
        startActivity(intent);
    }

    @OnClick(R.id.stamp8) void where8(){
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 24);
        intent.putExtra("u_Building", userBuilding);
        startActivity(intent);
    }

    @OnClick(R.id.stamp9) void where9() {
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 30);
        intent.putExtra("u_Building", userBuilding);
        startActivity(intent);
    }

    @OnClick(R.id.stamp10) void where10() {
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 31); //미유카페
        intent.putExtra("u_Building", userBuilding);
        startActivity(intent);
    }

    @OnClick(R.id.stamp11) void where11() {
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", 32); //솔찬공원
        intent.putExtra("u_Building", userBuilding);
        startActivity(intent);
    }

    @OnClick(R.id.stamp12) void where12() {
        Intent intent = new Intent(StampActivity.this, WhereStampActivity.class);
        intent.putExtra("where", userBuilding);
        intent.putExtra("u_Building", userBuilding);
        startActivity(intent);
    }
    public void MarkStamp(){
        /*
            [ay.han]2022.02.23 효율화는 나중에....
            user DB내 각 stamp 정보가 1일때, 스탬프 이미지 바꿈.
         */

        // MainButtonActivity에서 bx_stamp 받음
        Intent intent = getIntent();
        int b1_stamp = intent.getIntExtra("b1_stamp", 0);
        int b2_stamp = intent.getIntExtra("b2_stamp", 0);
        int b6_stamp = intent.getIntExtra("b6_stamp", 0);
        int b11_stamp = intent.getIntExtra("b11_stamp", 0);
        int b12_stamp = intent.getIntExtra("b12_stamp", 0);
        int b17_stamp = intent.getIntExtra("b17_stamp", 0);
        int b18_stamp = intent.getIntExtra("b18_stamp", 0);
        int b24_stamp = intent.getIntExtra("b24_stamp", 0);
        int b30_stamp = intent.getIntExtra("b30_stamp", 0);
        int b31_stamp = intent.getIntExtra("b31_stamp", 0);
        int b32_stamp = intent.getIntExtra("b32_stamp", 0);
        int b0_stamp = intent.getIntExtra("b0_stamp", 0);

        if (b1_stamp == 1) {
            ImageButton btn1 = (ImageButton) findViewById(R.id.stamp1);
            btn1.setImageResource(R.drawable.stamp);
        }if(b2_stamp == 1){
            ImageButton btn2 = (ImageButton) findViewById(R.id.stamp2);
            btn2.setImageResource(R.drawable.stamp);
        }if(b6_stamp == 1){
            ImageButton btn3 = (ImageButton) findViewById(R.id.stamp3);
            btn3.setImageResource(R.drawable.stamp);
        }if(b11_stamp == 1){
            ImageButton btn4 = (ImageButton) findViewById(R.id.stamp4);
            btn4.setImageResource(R.drawable.stamp);
        }if(b12_stamp == 1){
            ImageButton btn5 = (ImageButton) findViewById(R.id.stamp5);
            btn5.setImageResource(R.drawable.stamp);
        }if(b17_stamp == 1){
            ImageButton btn6 = (ImageButton) findViewById(R.id.stamp6);
            btn6.setImageResource(R.drawable.stamp);
        }if(b18_stamp == 1){
            ImageButton btn7 = (ImageButton) findViewById(R.id.stamp7);
            btn7.setImageResource(R.drawable.stamp);
        }if(b24_stamp == 1){
            ImageButton btn8 = (ImageButton) findViewById(R.id.stamp8);
            btn8.setImageResource(R.drawable.stamp);
        }if(b30_stamp == 1){
            ImageButton btn9 = (ImageButton) findViewById(R.id.stamp9);
            btn9.setImageResource(R.drawable.stamp);
        }if(b31_stamp == 1){
            ImageButton btn10 = (ImageButton) findViewById(R.id.stamp10);
            btn10.setImageResource(R.drawable.stamp);
        }if(b32_stamp == 1){
            ImageButton btn11 = (ImageButton) findViewById(R.id.stamp11);
            btn11.setImageResource(R.drawable.stamp);
        }if(b0_stamp == 1){
            ImageButton btn12 = (ImageButton) findViewById(R.id.stamp12);
            btn12.setImageResource(R.drawable.stamp);
        }
    }
}
