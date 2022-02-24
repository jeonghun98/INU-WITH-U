package org.techtown.iwu;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//로그인 위한 DB연동 Activity

public class LogInRequest extends StringRequest {

    final static private String URL = "http://taekyung.dothome.co.kr/Login.php"; // 서버 URL 설정 ( PHP 파일 연동 )
    private Map<String, String> map;

    public LogInRequest(String userID, String userPassword, Response.Listener<String> listener) { // 요청 값 받아와서 mapping
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("u_id",userID);
        map.put("u_pw", userPassword); // 받은 ID와 PW를 mapping
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map; // map 리턴
    }
}