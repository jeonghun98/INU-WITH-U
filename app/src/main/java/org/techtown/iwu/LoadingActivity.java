package org.techtown.iwu;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;


import com.yanzhenjie.permission.Action;
import com.yanzhenjie.permission.AndPermission;
import com.yanzhenjie.permission.runtime.Permission;

//로딩화면 Activity

public class LoadingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading); // 로딩화면 실행

        AndPermission.with(this) // 카메라, 위치 권한 받고 확인
                .runtime()
                .permission(Permission.CAMERA,Permission.ACCESS_COARSE_LOCATION, Permission.ACCESS_FINE_LOCATION)
                .onGranted(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        showToast("허용된 권한 갯수 : " + permissions.size());

                    }
                })
                .onDenied(new Action<List<String>>() {
                    @Override
                    public void onAction(List<String> permissions) {
                        showToast("거부된 권한 갯수 : " + permissions.size());
                    }
                })
                .start();
    }

    // 로딩 2초 -> 터치 이벤트 발생 코드로 수정
    // 위의 권한을 부여받는 동안 화면이 넘어가는 현상을 해결하기 위해서 아래 코드를 수정 함
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN: //화면 누르면 발생하는 event
                Intent intent = new Intent(getBaseContext(), MenuActivity.class); // 로딩화면 후 로그인 화면 실행
                startActivity(intent); // LogInActivity 시작
                finish(); // LoadingActivity 종료
                break;
            default: break;
        }
        return true;
    }

//    private void startLoading() { // 로딩화면 구현 함수
//        Handler handler = new Handler(); // delay 주기 위해 선언함.
//        handler.postDelayed(new Runnable() {
//            @Override
//            public void run() {  // 로딩 후 구현될 Activity 설정하는 함수
//                Intent intent = new Intent(getBaseContext(), MenuActivity.class); // 로딩화면 후 로그인 화면 실행
//                startActivity(intent); // LogInActivity 시작
//                finish(); // LoadingActivity 종료
//            }
//        }, 2000); //약 2초간 실행
//    }

    public void showToast(String data) { //toast 메소드
        Toast.makeText(this, data, Toast.LENGTH_LONG).show();
    }
}
