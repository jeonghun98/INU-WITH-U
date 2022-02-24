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

                                int UserStamp1 = jsonObject.getInt("b1_stamp");
                                int UserStamp2 = jsonObject.getInt("b2_stamp");
                                int UserStamp3 = jsonObject.getInt("b6_stamp");
                                int UserStamp4 = jsonObject.getInt("b11_stamp");
                                int UserStamp5 = jsonObject.getInt("b12_stamp");
                                int UserStamp6 = jsonObject.getInt("b17_stamp");
                                int UserStamp7 = jsonObject.getInt("b18_stamp");
                                int UserStamp8 = jsonObject.getInt("b24_stamp");
                                int UserStamp9 = jsonObject.getInt("b30_stamp");
                                int UserStamp10 = jsonObject.getInt("b31_stamp");
                                int UserStamp11 = jsonObject.getInt("b32_stamp");
                                int UserStamp12 = jsonObject.getInt("b0_stamp");

                                Toast.makeText(LogInActivity.this,userMajor+ " "+ userName+"학생, 환영합니다!",Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(LogInActivity.this, MainButtonActivity.class);
                                intent.putExtra("u_id", userID); // MainButtonActivity로 유저정보 넘김
                                intent.putExtra("u_pw", userPass);
                                intent.putExtra("u_name", userName);
                                intent.putExtra("u_major", userMajor);
                                intent.putExtra("u_phone", userPhone);
                                intent.putExtra("u_mid", userMid);

                                intent.putExtra("b1_stamp", UserStamp1);
                                intent.putExtra("b2_stamp", UserStamp2);
                                intent.putExtra("b6_stamp", UserStamp3);
                                intent.putExtra("b11_stamp", UserStamp4);
                                intent.putExtra("b12_stamp", UserStamp5);
                                intent.putExtra("b17_stamp", UserStamp6);
                                intent.putExtra("b18_stamp", UserStamp7);
                                intent.putExtra("b24_stamp", UserStamp8);
                                intent.putExtra("b30_stamp", UserStamp9);
                                intent.putExtra("b31_stamp", UserStamp10);
                                intent.putExtra("b32_stamp", UserStamp11);
                                intent.putExtra("b0_stamp", UserStamp12);

                                int userBuilding = 15;
                                if (1 <= userMid && userMid <= 6) userBuilding = 15;
                                else if (7 <= userMid && userMid <= 11) userBuilding = 5;
                                else if (12 <= userMid && userMid <= 21) userBuilding = 13;
                                else if (22 <= userMid && userMid <= 29) userBuilding = 8;
                                else if (30 <= userMid && userMid <= 32) userBuilding = 7;
                                else if (33 <= userMid && userMid <= 34) userBuilding = 14;
                                else if (35 <= userMid && userMid <= 39) userBuilding = 16;
                                else if (40 <= userMid && userMid <= 47) userBuilding = 20;
                                else if (48 <= userMid && userMid <= 50) userBuilding = 28;
                                else if (51 <= userMid && userMid <= 53) userBuilding = 29;
                                intent.putExtra("u_Building", userBuilding);

                                startActivity(intent); // MainButtonActivity 시작
                            }
                            else { // 로그인에 실패한 경우
                                AlertDialog.Builder builder=new AlertDialog.Builder( LogInActivity.this );
                                dialog = builder.setMessage("로그인에 실패하였습니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();
                                return;
                                //Toast.makeText(LogInActivity.this, "로그인 실패하였습니다.", Toast.LENGTH_SHORT).show();
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