package org.techtown.iwu;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;


import org.json.JSONException;
import org.json.JSONObject;

// 메뉴에서 '입장하기'를 선택하면 나타나는 로그인 Activity

public class LogInActivity extends AppCompatActivity {
    private EditText u_id, u_pw; // 넘겨받는 ID, PW
    private Button btn_login, btn_signup; // 로그인, 유저등록 버튼
    private AlertDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login); // 맨 처음 로그인 화면 띄움

        u_id = findViewById(R.id.u_id); // 입력받은 ID와 PW 넘겨받기
        u_pw = findViewById(R.id.u_pw);
        btn_login = findViewById(R.id.LogInButton); // 로그인버튼과 유저등록버튼 넘겨받기
        btn_signup = findViewById(R.id.SignUpButton);


        // 회원가입 버튼을 클릭 시 수행
        btn_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LogInActivity.this, RegisterActivity.class); // 유저등록하는 RegisterActivity로 넘어감
                startActivity(intent); // Register Activity 시작
            }
        });

        // 로그인 버튼 클릭 시
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해옴
                String userID = u_id.getText().toString(); // string으로 받기
                String userPass = u_pw.getText().toString();

                if(userID.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder( LogInActivity.this );
                    dialog = builder.setMessage("학번을 입력해 주세요.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }
                if(userPass.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder( LogInActivity.this );
                    dialog = builder.setMessage("비밀번호를 입력해 주세요.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();
                    return;
                }

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { // DB와 연동하여 로그인된 회원의 정보를 받아오는 함수
                        try {
                            JSONObject jsonObject = new JSONObject(response); // string들로 구성된 회원 정보들을 받음
                            boolean success = jsonObject.getBoolean("success"); // 인자로 전달된 key에 대한 객체를 "success" 할 시 True 리턴
                            if (success) { // 로그인에 성공한 경우 유저의 정보를 모두 받아옴.
                                String userID = jsonObject.getString("u_id");
                                String userPass = jsonObject.getString("u_pw");
                                String userName = jsonObject.getString( "u_name" );
                                String userMajor = jsonObject.getString( "u_major" );
                                int userPhone = jsonObject.getInt( "u_phone" );
                                int userMid = jsonObject.getInt("u_mid");

                                Toast.makeText(LogInActivity.this,userMajor+ " "+ userName+"학생, 환영합니다!",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LogInActivity.this, MainButtonActivity.class); // 로그인 후 메뉴화면으로 이동
                                intent.putExtra("u_id", userID); // MainActivity로 유저정보 넘김
                                intent.putExtra("u_pw", userPass);
                                intent.putExtra("u_name", userName);
                                intent.putExtra("u_major", userMajor);
                                intent.putExtra("u_phone", userPhone);
                                intent.putExtra("u_mid", userMid);

                                startActivity(intent); // MainButtonActivity 시작
                            }
                            else { // 로그인에 실패한 경우
                                Toast.makeText(LogInActivity.this, "로그인 실패하였습니다.", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) { // 예외처리
                            e.printStackTrace();
                        }
                    }
                };

                // 서버로 Volley를 이용해 정보 넘김.
                LogInRequest loginRequest = new LogInRequest(userID, userPass, responseListener);
                RequestQueue queue = Volley.newRequestQueue(LogInActivity.this);
                queue.add(loginRequest);
            }
        });


    }
}