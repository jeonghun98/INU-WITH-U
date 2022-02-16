package org.techtown.iwu;

import android.Manifest;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;

import com.naver.maps.geometry.LatLng;
import com.naver.maps.geometry.LatLngBounds;
import com.naver.maps.map.CameraPosition;
import com.naver.maps.map.LocationTrackingMode;
import com.naver.maps.map.MapFragment;
import com.naver.maps.map.NaverMap;
import com.naver.maps.map.OnMapReadyCallback;
import com.naver.maps.map.overlay.Align;
import com.naver.maps.map.overlay.LocationOverlay;
import com.naver.maps.map.overlay.Marker;
import com.naver.maps.map.util.FusedLocationSource;
import com.naver.maps.map.util.MarkerIcons;


// 지도 + 현재위치 불러오는 네이버맵 Activity

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private static final String TAG = "MapActivity";

    private static final int PERMISSION_REQUEST_CODE = 100; // 위치를 이용하기 위해 권한 요청
    private static final String[] PERMISSIONS = {
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
    };

    private FusedLocationSource mLocationSource; // 최적 위치 반환을 위해 선언
    private NaverMap mNaverMap; // 네이버맵 선언

    private Marker markers[] = new Marker[21];
    private static final int b_name[] = {1,2,5,6,7,8,11,12,13,14,15,16,17,18,20,24,28,29,30,31,32};
    private static final String b_name_str[] = {"미유카페", "솔찬공원"};
    private static final double b_location[][] = {{37.376690, 126.634591}, {37.377547, 126.633710}, {37.375722, 126.634515},
            {37.375177, 126.633909},{37.374544, 126.633402},{37.373660, 126.632508},
            {37.374472, 126.631827},{37.375283, 126.632570},{37.375958, 126.633253},
            {37.376634, 126.632989},{37.375525, 126.631994},{37.374711, 126.631248},
            {37.374129, 126.630849},{37.373926, 126.629960},{37.374885, 126.629619},
            {37.375974, 126.635774},{37.371838, 126.632742},{37.372351, 126.631278},
            {37.373775, 126.634232},{37.372568, 126.631217},{37.371343, 126.629608}};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map); // activity_map 띄우기

        // 지도 객체 생성
        FragmentManager fm = getSupportFragmentManager(); // MapActivity와 activity_map의 fragment를 연결해주는 매니저
        MapFragment mapFragment = (MapFragment)fm.findFragmentById(R.id.map); // activity_map의 fragment
        if (mapFragment == null) {
            mapFragment = MapFragment.newInstance();
            fm.beginTransaction().add(R.id.map, mapFragment).commit(); // fragment를 activity_map 내의 map에 삽입
        }

        // getMapAsync를 호출하여 비동기로 onMapReady 콜백 메서드 호출
        mapFragment.getMapAsync(this);

        // 위치를 반환하는 구현체인 FusedLocationSource 생성
        mLocationSource = new FusedLocationSource(this, PERMISSION_REQUEST_CODE);
    }

    private void setMakers(@NonNull NaverMap naverMap){
        for(int i = 0; i < markers.length; i++) {
            markers[i] = new Marker();
            markers[i].setPosition(new LatLng(b_location[i][0], b_location[i][1]));
            markers[i].setWidth(60);
            markers[i].setHeight(80);
            if(b_name[i] == 31 || b_name[i] == 32)
                markers[i].setCaptionText(b_name_str[i-19]);
            else markers[i].setCaptionText(b_name[i] + "호관");
            markers[i].setCaptionAligns(Align.Top);
            markers[i].setMap(naverMap);
        }
    }

    // onMapReady에서 NaverMap 객체를 받음
    @Override
    public void onMapReady(@NonNull NaverMap naverMap) {
        Log.d( TAG, "onMapReady");

        setMakers(naverMap);

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
}

