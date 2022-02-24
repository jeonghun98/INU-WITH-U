package org.techtown.iwu;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class ValidateRequest extends StringRequest { // u_id 중복 체크를 위함
    //서버 url 설정(php파일 연동)
    final static  private String URL="http://taekyung.dothome.co.kr/UserValidate1.php";
    private Map<String,String> map;

    public ValidateRequest(String u_id, Response.Listener<String>listener){
        super(Request.Method.POST,URL,listener,null);

        map=new HashMap<>();
        map.put("u_id", u_id);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}