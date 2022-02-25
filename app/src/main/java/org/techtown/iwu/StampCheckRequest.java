package org.techtown.iwu;
import com.android.volley.AuthFailureError;
import com.android.volley.Response;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

public class StampCheckRequest extends StringRequest {

    //[hun] 현재 stamp 현황 확인
    final static private String URL = "http://taekyung.dothome.co.kr/StampCheck.php"; //[ay.han] bx_stamp 불러오기 위한 php 수정 반영
    private Map<String, String> map;

    public StampCheckRequest(String u_id,Response.Listener<String> listener){
        super(Method.POST,URL,listener,null);
        map=new HashMap<>();
        map.put("u_id",u_id);
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return map;
    }
}
