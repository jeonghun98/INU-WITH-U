package org.techtown.iwu;

import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StampRequest extends StringRequest {

    //서버 url 설정(php파일 연동)
    final static private String URL = "http://taekyung.dothome.co.kr/Stamp.php";
    private Map<String, String> map;

    public StampRequest(String u_id, int b_id, Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        // int succeed = 1;
        map=new HashMap<>();
        map.put("u_id",u_id); // string으로 받은 id
        map.put("b_id",b_id +"");
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
