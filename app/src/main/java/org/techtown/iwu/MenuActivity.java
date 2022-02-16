package org.techtown.iwu;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

// 로딩 후 나오는 메뉴 Activity

public class MenuActivity extends AppCompatActivity {
    Button EnterButton, RuleButton,LogoutButton, InforButton; // 입장하기, 게임설명, 로그아웃, 게임정보
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu); // activity_menu 띄움.

        //EnterButton(입장하기)의 Click이벤트
        EnterButton = (Button) findViewById(R.id.EnterButton);

        EnterButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class); // MainActivity 실행
                startActivity(intent);
            }
        });

        //RuleButton(게임방법)의 Click이벤트
        RuleButton = (Button) findViewById(R.id.RuleButton);

        RuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RuleActivity.class); // RuleActivity 실행
                startActivity(intent);
            }
        });


        //InforButton(게임정보)의 Click이벤트
        InforButton = (Button) findViewById(R.id.InforButton);

        InforButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InforActivity.class); // InforActivity 실행
                startActivity(intent);
            }
        });
    }
}