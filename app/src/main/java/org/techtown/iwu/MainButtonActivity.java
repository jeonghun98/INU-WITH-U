package org.techtown.iwu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;


public class MainButtonActivity extends AppCompatActivity {
    ImageButton MainMapbtn, MainSetbtn, MainStampbtn; //이미지버튼 Map, Setting, Stamp 선언
    FrameLayout previewFrame; //카메라 뷰를 위한 frame
    CameraSurfaceView cameraView; //카메라
    ImageButton btn; //임시 방편 어워드 버튼
    int b_id = 0;

    //호관, 위도 + 경도(임시로 3개만 넣음)
    static final int b_name[] = {5,7,13};
    static final double b_location[][] = {{37.375722, 126.634515}, {37.374544, 126.633402}, {37.375958, 126.633253}};

    //퀴즈 DB 완성 후 사용 예정
//    static final int b_name[] = {1,2,6,11,12,17,18,24,30,31,32}; //31 -> 미유, 32 -> 솔찬
//    static final String b_name_str[] = {"미유카페", "솔찬공원"};
//    static final double b_location[][] = {{37.376690, 126.634591},{37.377547, 126.633710},{37.375177, 126.633909},
//            {37.374472, 126.631827},{37.375283, 126.632570},{37.374129, 126.630849},
//            {37.373926, 126.629960},{37.375974, 126.635774},{37.373775, 126.634232},
//            {37.372568, 126.631217},{37.371343, 126.629608}};
//
//    static final int b_name_major[] = {5,7,8,13,14,15,16,20,28,29};
//    static final double b_location_major[][]={{37.375722, 126.634515},{37.374544, 126.633402},{37.373660, 126.632508},
//            {37.375958, 126.633253},{37.376634, 126.632989},{37.375525, 126.631994},
//            {37.374711, 126.631248},{37.374885, 126.629619}, {37.371838, 126.632742}
//            {37.372351, 126.631278}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainbuttons); // activity_main 보이기

        previewFrame = findViewById(R.id.previewFrame);
        cameraView = findViewById(R.id.cameraView);

        //화면에 나타나는 임시 어워드
        btn = findViewById(R.id.imagebtn);
        btn.setVisibility(View.INVISIBLE);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage_award();
            }
        });

        MainMapbtn = (ImageButton) findViewById(R.id.mainmapbtn); // MainMapbtn 받아오기

        MainMapbtn.setOnClickListener(new View.OnClickListener() { // 메인화면에서 지도 버튼 클릭 시 수행
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class); // MapActivity 수행
                startActivity(intent); // MapActivity 시작
            }
        });

        MainSetbtn = (ImageButton) findViewById(R.id.mainsetbtn); // MainSettingbtn 받아오기

        MainSetbtn.setOnClickListener(new View.OnClickListener() { // 메인화면에서 setting 버튼 클릭 시 수행
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class); // SettingActivity 수행
                startActivity(intent);
            }
        });


        Intent intent = getIntent(); // 앞의 LogInActivity에서 넘긴 정보 받기
        int MajorCode = intent.getIntExtra("u_mid", 29); // Major code 받아오기
        MainStampbtn = (ImageButton) findViewById(R.id.mainstampbtn); // MainStampbtn 받아오기
        MainStampbtn.setOnClickListener(new View.OnClickListener() { // 메인화면에서 stamp 버튼 클릭 시 수행
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StampActivity.class); // StampActivity 수행
                intent.putExtra("u_mid", MajorCode); // Major code 넘겨주기
                startActivity(intent); // StampActivity 시작
            }
        });
        startLocationService(); //위치 활성화
    }

    private void showMessage_award() { //어워드 획득 후 나타나는 dialog 메소드
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setMessage(b_id + "호관 아이템을 획득하셨습니다.");

        builder.setNeutralButton("나가기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("2차 퀴즈", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { //quiz 액티비티 호출
                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                if(b_id > 0 && b_id < 33)
                    intent.putExtra("b_id", b_id); // quiz activity -> 사용자가 발견한 b_id 넘겨줌
                startActivity(intent);
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    //해당 코드는 4G(3G)를 사용하지 않는 핸드폰에서 작용하지 않아서 -> 현재 virtual device 만 가능

    public void startLocationService() {
        LocationManager manager = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
        try {
            Location location = manager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            if (location != null) {
                double latitude = location.getLatitude();
                double longitude = location.getLongitude();
                //String message = "start -> Latitude : " + latitude + "\nLongitude : " + longitude;
                //showToast(message);
                btn = findViewById(R.id.imagebtn);

                //사용자의 위치가 해당 건물의 위도,경도를 중심으로 원안에 위치해 있을 때 visible
                for(int i = 0; i < b_location.length; i++) {
                    if (Math.pow(0.0004, 2) >= (Math.pow(b_location[i][0] - latitude, 2) + Math.pow(b_location[i][1] - longitude, 2))) {
                        btn.setVisibility(View.VISIBLE);
                        b_id = b_name[i];
                        break;
                    }
                    else {
                        btn.setVisibility(View.INVISIBLE);
                    }
                }
            }
            else showToast("location == null");

            GPSListener gpsListener = new GPSListener();
            long minTime = 2000; // 2초마다 위치 요청
            float minDistance = 0;
            manager.requestLocationUpdates(LocationManager.GPS_PROVIDER, minTime, minDistance, gpsListener);

        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    class GPSListener implements LocationListener {
        @Override
        public void onLocationChanged(Location location) {
            Double latitude = location.getLatitude();
            Double longitude = location.getLongitude();
            //String message = "location -> Latitude : " + latitude + "\nLongitude : " + longitude;
            //showToast(message);

            //사용자의 위치가 해당 건물의 위도,경도를 중심으로 원안에 위치해 있을 때 visible
            btn = findViewById(R.id.imagebtn);
            for(int i = 0; i < b_location.length; i++) {
                if (Math.pow(0.0004, 2) >= (Math.pow(b_location[i][0] - latitude, 2) + Math.pow(b_location[i][1] - longitude, 2))) {
                    btn.setVisibility(View.VISIBLE);
                    b_id = b_name[i];
                    //showToast(b_id + "호관 어워드");
                    break;
                }
                else {
                    btn.setVisibility(View.INVISIBLE);
                }
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {}

        @Override
        public void onProviderEnabled(String provider) {}

        @Override
        public void onProviderDisabled(String provider) {}
    }

    public void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }
}
