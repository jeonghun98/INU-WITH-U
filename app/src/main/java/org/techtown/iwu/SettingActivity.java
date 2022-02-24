package org.techtown.iwu;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

//메인화면에서의 Setting Activity

public class SettingActivity extends AppCompatActivity {
    Button RuleButton,LogoutButton, InforButton; // 게임방법, 로그아웃, 게임정보 버튼 선언
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting); // 메인 setting 화면 띄우기

        // RuleButton 클릭 시 이벤트
        RuleButton = (Button) findViewById(R.id.ruleButton);

        RuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RuleActivity.class); // RuleActivity 실행
                startActivity(intent);
            }
        });

        // LogoutButton 클릭 시 이벤트
        LogoutButton = (Button) findViewById(R.id.logOutButton);

        LogoutButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), LogInActivity.class); // LogInActivity 실행
                startActivity(intent);
                Toast.makeText(getApplicationContext(),"로그아웃 되었습니다.",Toast.LENGTH_SHORT).show();
            }
        });

        // InforButton 클릭 시 이벤트
        InforButton = (Button) findViewById(R.id.inforButton);

        InforButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), InforActivity.class); // InforActivity 실행
                startActivity(intent);
            }
        });

    }
}
