package org.techtown.iwu;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

//회원가입을 위한 DB연동 Activity

public class RegisterRequest extends StringRequest {

    // 서버 URL 설정 ( PHP 파일 연동 )
    final static private String URL = "http://taekyung.dothome.co.kr/register.php";
    private Map<String, String> map;

    // 요청 값 받아와서 mapping
    public RegisterRequest(String userID, String userPassword, String userName, String userMajor, String userPhone, int userMid, Response.Listener<String> listener) {
        super(Method.POST, URL, listener, null);

        map = new HashMap<>();
        map.put("u_id",userID);
        map.put("u_pw", userPassword);
        map.put("u_name", userName);
        map.put("u_major", userMajor);
        map.put("u_phone", userPhone);
        map.put("u_mid", userMid + "");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}