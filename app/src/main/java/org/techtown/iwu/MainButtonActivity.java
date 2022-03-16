package org.techtown.iwu;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.Manifest;
import android.content.pm.PackageManager;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.View;

import android.util.Log;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


public class MainButtonActivity extends AppCompatActivity implements OnMapReadyCallback {
    private static final String TAG = "MainButtonActivity";
    ImageButton MainMapbtn, MainSetbtn, MainStampbtn; //이미지버튼 Map, Setting, Stamp 선언
    ImageButton btn; //임시 방편 어워드 버튼
    String userID;
    int b_id = 0;
    private static final int PERMISSION_REQUEST_CODE = 100; // 위치를 이용하기 위해 권한 요청
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };
    private FusedLocationSource mLocationSource; // 최적 위치 반환을 위해 선언
    private NaverMap mNaverMap; // 네이버맵 선언
    private int userBuilding = 15, userBuilding_index = 5; //건물(MajorCode 인문대15호관), b_name_major의 5번째 index -> 15호관
    private Marker markers[] = new Marker[21];

    static final int b_name[] = {1,2,6,11,12,17,18,24,30,31,32}; //31 -> 미유, 32 -> 솔찬
    static final String b_name_str[] = {"미유카페", "솔찬공원"};
    static final double b_location[][] = {{37.376690, 126.634591},{37.377547, 126.633710},{37.375177, 126.633909},
            {37.374472, 126.631827},{37.375283, 126.632570},{37.374129, 126.630849},
            {37.373926, 126.629960},{37.375974, 126.635774},{37.373775, 126.634232},
            {37.372568, 126.631217},{37.371343, 126.629608}};

    static final int b_name_major[] = {5,7,8,13,14,15,16,20,28,29};
    static final double b_location_major[][]={{37.375722, 126.634515},{37.374544, 126.633402},{37.373660, 126.632508},
            {37.375958, 126.633253},{37.376634, 126.632989},{37.375525, 126.631994},
            {37.374711, 126.631248},{37.374885, 126.629619}, {37.371838, 126.632742},
            {37.372351, 126.631278}};

    //DB에서 가져온 stamp 정보 : index 0 -> 건물 / index : 1 -> 현재 stamp 현황
    private int b_stamp [][] = {{1,0},{2,0},{6,0},{11,0},{12,0},{17,0},{18,0},{24,0},{30,0},{31,0},{32,0}};
    private int b_stamp_major = 0; //major building stamp

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainmap); // activity_main 보이기

        FragmentManager fm = getSupportFragmentManager(); // MapActivity와 activity_map의 fragment를 연결해주는 매니저
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.mainmapframe); // activity_map의 fragment
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.mainmapframe, mapFragment).commit(); // fragment를 activity_map 내의 map에 삽입
        }

        // getMapAsync를 호출하여 비동기로 onMapReady 콜백 메서드 호출
        mapFragment.getMapAsync(this);

        // 위치를 반환하는 구현체인 FusedLocationSource 생성
        mLocationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);

        //화면에 나타나는 임시 어워드 (초기 설정 : 보이지 않음) -> 애니메이션 설정 후 setvisibility 작동 x
        btn = findViewById(R.id.imagebtn);
        btn.setVisibility(View.INVISIBLE);

        final Animation rotate_anim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        btn.startAnimation(rotate_anim);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showMessage_award();
            }
        });

        MainMapbtn = (ImageButton) findViewById(R.id.mainmap); // MainMapbtn 받아오기

        MainMapbtn.setOnClickListener(new View.OnClickListener() { // 메인화면에서 지도 버튼 클릭 시 수행
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MapActivity.class); // MapActivity 수행
                startActivity(intent); // MapActivity 시작
            }
        });

        MainSetbtn = (ImageButton) findViewById(R.id.mainset); // MainSettingbtn 받아오기

        MainSetbtn.setOnClickListener(new View.OnClickListener() { // 메인화면에서 setting 버튼 클릭 시 수행
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), SettingActivity.class); // SettingActivity 수행
                startActivity(intent);
            }
        });


        Intent intent = getIntent(); // 앞의 LogInActivity에서 넘긴 정보 받기
        userBuilding = intent.getIntExtra("u_Building", 15); // Major code 받아오기
        String userID = intent.getStringExtra("u_id"); // u_id 받기

        MainStampbtn = (ImageButton) findViewById(R.id.mainstamp); // MainStampbtn 받아오기
        MainStampbtn.setOnClickListener(new View.OnClickListener() { // 메인화면에서 stamp 버튼 클릭 시 수행
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), StampActivity.class); // StampActivity 수행
                intent.putExtra("u_Building", userBuilding); // Major code 넘겨주기
                intent.putExtra("u_id", userID);// u_id 같이 넘겨주기 (string)

                startActivity(intent); // StampActivity 시작
            }
        });

        for(int i = 0; i < b_name_major.length; i++) {   //유저의 전공을 받아 건물이 해당 리스트의 몇번째 인덱스인지 체크
            if (userBuilding == b_name_major[i]) userBuilding_index = i; //아래 위치 정보에서 중복을 방지하기 위해서
        }
        //[hun] onResume 에서도 실행하나 로그인 후 DB를 가져오지 않아 onCreate 에서도 한번 더 추가함
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject JO = new JSONObject(response);
                    boolean success = JO.getBoolean("success");
                    if (success) {
                        b_stamp[0][1] = JO.getInt("b1_stamp");
                        b_stamp[1][1] = JO.getInt("b2_stamp");
                        b_stamp[2][1] = JO.getInt("b6_stamp");
                        b_stamp[3][1] = JO.getInt("b11_stamp");
                        b_stamp[4][1] = JO.getInt("b12_stamp");
                        b_stamp[5][1] = JO.getInt("b17_stamp");
                        b_stamp[6][1] = JO.getInt("b18_stamp");
                        b_stamp[7][1] = JO.getInt("b24_stamp");
                        b_stamp[8][1] = JO.getInt("b30_stamp");
                        b_stamp[9][1] = JO.getInt("b31_stamp");
                        b_stamp[10][1] = JO.getInt("b32_stamp");
                        b_stamp_major = JO.getInt("b0_stamp");
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        StampCheckRequest Request = new StampCheckRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainButtonActivity.this);
        queue.add(Request);

        startLocationService(); //위치 활성화
    }

    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Log.d( TAG, "onMapReady");

        // NaverMap 객체 받아서 NaverMap 객체에 위치 소스 지정
        mNaverMap = naverMap;
        mNaverMap.setLocationSource(mLocationSource);

        //min, max 설정 및 지도 이동 범위 설정
        naverMap.setMinZoom(15.0);
        naverMap.setMaxZoom(18.0);
        naverMap.setExtent(new LatLngBounds(new LatLng(37.370463, 126.627007), new LatLng(37.376835, 126.634652)));

        ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_REQUEST_CODE);
        naverMap.addOnOptionChangeListener(() -> { // 지도 옵션 변경에 대한 이벤트 리스너 등록
            LocationTrackingMode mode = mNaverMap.getLocationTrackingMode(); // 위치추적모드 반환
            mLocationSource.setCompassEnabled(mode == LocationTrackingMode.Follow || mode == LocationTrackingMode.Face); // 나침반(카메라 좌표 이용 시) 사용
            // 권한확인. 결과는 onRequestPermissionsResult 콜백 매서드 호출
        });
    }

    @Override // 권한획득 확인하는 함수
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        // request code와 권한획득 여부 확인
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0
                    && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                mNaverMap.setLocationTrackingMode(LocationTrackingMode.Face); // 위치추적 + 카메라 좌표 + 방향 을 움직이는 모드
            }
        }
        LocationOverlay locationOverlay = mNaverMap.getLocationOverlay(); // 지도로부터 위치 오버레이 객체를 가져옴.
        locationOverlay.setVisible(true); // 지도에 위치 오버레이 나타남.
    }

    //[hun] 다른 activity 종료할때 계속 onResume 에서 stamp db check
    @Override
    protected void onResume() {
        super.onResume();
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject JO = new JSONObject(response);
                    boolean success = JO.getBoolean("success");
                    if (success) {
                        b_stamp[0][1] = JO.getInt("b1_stamp");
                        b_stamp[1][1] = JO.getInt("b2_stamp");
                        b_stamp[2][1] = JO.getInt("b6_stamp");
                        b_stamp[3][1] = JO.getInt("b11_stamp");
                        b_stamp[4][1] = JO.getInt("b12_stamp");
                        b_stamp[5][1] = JO.getInt("b17_stamp");
                        b_stamp[6][1] = JO.getInt("b18_stamp");
                        b_stamp[7][1] = JO.getInt("b24_stamp");
                        b_stamp[8][1] = JO.getInt("b30_stamp");
                        b_stamp[9][1] = JO.getInt("b31_stamp");
                        b_stamp[10][1] = JO.getInt("b32_stamp");
                        b_stamp_major = JO.getInt("b0_stamp");
                    }
                    else {
                        Toast.makeText(getApplicationContext(), "실패", Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };
        StampCheckRequest Request = new StampCheckRequest(userID, responseListener);
        RequestQueue queue = Volley.newRequestQueue(MainButtonActivity.this);
        queue.add(Request);
    }

    private void showMessage_award() { //어워드 획득 후 나타나는 dialog 메소드
        Intent intent = getIntent();
        userID = intent.getStringExtra("u_id"); // u_id 받기
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        String b_name;
        if(b_id == 31 || b_id == 32) b_name = b_name_str[b_id-31]; // 미유와 솔찬공원
        else b_name = b_id + "호관";
        builder.setMessage(b_name + " 아이템을 획득하셨습니다.");

        builder.setNeutralButton("나가기", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        builder.setPositiveButton("2차 퀴즈", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) { //quiz 액티비티 호출
                Intent intent = new Intent(getApplicationContext(), QuizActivity.class);
                intent.putExtra("u_id", userID); // u_id도 같이 넘겨주기 (string)
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

                btn = findViewById(R.id.imagebtn);

                //사용자의 위치가 해당 건물의 위도,경도를 중심으로 원안에 위치해 있을 때 visible
                for(int i = 0; i < b_location.length; i++) {
                    if(b_stamp[i][1] == 0){ //스탬프 현황 확인 후 체크
                        if (Math.pow(0.0004, 2) >= (Math.pow(b_location[i][0] - latitude, 2) + Math.pow(b_location[i][1] - longitude, 2))) {
                            btn.setVisibility(View.VISIBLE);
                            b_id = b_name[i];
                            break;
                        }
                        else {
                            btn.setVisibility(View.INVISIBLE);
                        }
                    }
                    else {
                        btn.setVisibility(View.INVISIBLE);
                    }
                }
                if(b_stamp_major == 0) { //스탬프 현황 확인 후 체크
                    if(Math.pow(0.0004, 2) >= (Math.pow(b_location_major[userBuilding_index][0] - latitude, 2) + Math.pow(b_location_major[userBuilding_index][1] - longitude, 2))){
                        btn.setVisibility(View.VISIBLE);
                        b_id = b_name_major[userBuilding_index]; // == MajorCode
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
                if(b_stamp[i][1] == 0){ //스탬프 현황 확인 후 체크
                    if (Math.pow(0.0004, 2) >= (Math.pow(b_location[i][0] - latitude, 2) + Math.pow(b_location[i][1] - longitude, 2))) {
                        btn.setVisibility(View.VISIBLE);
                        b_id = b_name[i];
                        break;
                    }
                    else {
                        btn.setVisibility(View.INVISIBLE);
                    }
                }
                else {
                    btn.setVisibility(View.INVISIBLE);
                }
            }

            if(b_stamp_major == 0) { //스탬프 현황 확인 후 체크
                if(Math.pow(0.0004, 2) >= (Math.pow(b_location_major[userBuilding_index][0] - latitude, 2) + Math.pow(b_location_major[userBuilding_index][1] - longitude, 2))){
                    btn.setVisibility(View.VISIBLE);
                    b_id = b_name_major[userBuilding_index]; // == MajorCode
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
