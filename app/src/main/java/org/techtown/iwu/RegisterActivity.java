package org.techtown.iwu;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.PhoneNumberFormattingTextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Spinner;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

// 회원가입 Activity

public class RegisterActivity extends AppCompatActivity {

    private EditText u_id, u_pw, u_name, u_phone;
    private Button btn_register, btn_major, u_id_btn;
    private TextView u_mid;
    private boolean u_id_check=false; // 학번 중복처리 체크 여부 확인
    private AlertDialog dialog;
    private boolean u_mid_check = false; // 전공 선택 체크 여부 확인


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        // 아이디 값 찾아주기
        u_id = findViewById(R.id.u_id);
        u_pw = findViewById(R.id.u_pw);
        u_name = findViewById(R.id.u_name);
        Spinner spinner = (Spinner)findViewById(R.id.majorbox); // 학과는 Spinner를 통해 받음
        u_phone = findViewById(R.id.u_phone);
        u_phone.setInputType(android.text.InputType.TYPE_CLASS_PHONE); //휴대폰 형태로 변경
        u_phone.addTextChangedListener(new PhoneNumberFormattingTextWatcher()); // 입력값에 하이픈(-) 포맷팅 **Android system language가 한국어일때만 3-4-4 가능
        u_mid = findViewById(R.id.codenum); // 비어있더라도 예외처리를 위해서 가져옴

        u_id_btn=findViewById(R.id.u_id_btn);
        u_id_btn.setOnClickListener(new View.OnClickListener() { //u_id 중복체크 추가함
            @Override
            public void onClick(View view) {
                String userID = u_id.getText().toString();
                if(u_id_check)
                {
                    register_alert_dialog(0);
                    return;
                }

                if(userID.equals("")){
                    AlertDialog.Builder builder=new AlertDialog.Builder( RegisterActivity.this );
                   /* dialog = builder.setMessage("학번은 빈 칸일 수 없습니다")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();*/
                    register_alert_dialog(1);
                    return;
                }
                Response.Listener<String> responseListener=new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse=new JSONObject(response);
                            boolean success=jsonResponse.getBoolean("success");
                            if(success){
                                /*AlertDialog.Builder builder=new AlertDialog.Builder( RegisterActivity.this );
                                dialog=builder.setMessage("사용할 수 있는 학번입니다.")
                                        .setPositiveButton("확인",null)
                                        .create();
                                dialog.show();*/
                                register_alert_dialog(2);
                                u_id.setEnabled(false); // -> id 더이상 고칠 수 없게 함
                                u_id_check=true;
                                //validateButton.setText("확인"); // button text 변경할지는 추후에 결정
                            }
                            else{
                                /*AlertDialog.Builder builder=new AlertDialog.Builder( RegisterActivity.this );
                                dialog=builder.setMessage("이미 등록된 학번입니다.")
                                        .setNegativeButton("확인",null)
                                        .create();
                                dialog.show();*/
                                register_alert_dialog(3);
                                u_id_check = false;
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };
                ValidateRequest validateRequest=new ValidateRequest(userID,responseListener);
                RequestQueue queue= Volley.newRequestQueue(RegisterActivity.this);
                queue.add(validateRequest);

            }
        });

        btn_major = findViewById(R.id.majorbtn);
        btn_major.setOnClickListener(new View.OnClickListener() { // major 선택완료 버튼 누를 시 실행되는 함수
            @Override
            public void onClick(View v) {
                String userMid = spinner.getSelectedItem().toString(); // spinner에서 선택된 string 가져옴
                userMid = userMid.substring(userMid.length()-2); // 맨 뒤의 2자리 숫자 받아오기
                u_mid = (TextView) findViewById(R.id.codenum); // TextView인 codenum의 주소를 받아와서
                u_mid.setText(userMid); // userMid 값 넣기
                u_mid_check = true;
            }
        });

        // 회원가입 버튼 클릭 시 수행
        btn_register = findViewById(R.id.RegisterButton);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // EditText에 현재 입력되어있는 값을 get(가져온다)해옴.
                String userID = u_id.getText().toString();
                String userPass = u_pw.getText().toString();
                String userName = u_name.getText().toString();
                String userMajor = spinner.getSelectedItem().toString(); // Spinner 값을 string으로 받아 userMajor로 넘김
                userMajor = userMajor.substring(0, userMajor.length()-3);

                //학번 중복처리 안 한 경우
                if(!u_id_check) {
                    /*AlertDialog.Builder builder=new AlertDialog.Builder( RegisterActivity.this );
                    dialog = builder.setMessage("학번 중복을 확인해주세요.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();*/
                    register_alert_dialog(4);
                    return;
                }

                //전공 선택 안 한 경우
                if(!u_mid_check) {
                    /*AlertDialog.Builder builder=new AlertDialog.Builder( RegisterActivity.this );
                    dialog = builder.setMessage("전공을 선택해주세요.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();*/
                    register_alert_dialog(5);
                    return;
                }
                //string "" 인 경우 에러이므로 위에 코드에서 체크 후 int 형으로 변환
                int userMid = Integer.parseInt(u_mid.getText().toString());

                //비밀번호 입력 안 한 경우
                if(userPass.equals("")) {
                    /*AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
                    dialog = builder.setMessage("비밀번호를 입력해주세요.")
                            .setPositiveButton("확인", null)
                            .create();
                    dialog.show();*/
                    register_alert_dialog(6);
                    return;
                }

                //이름 입력 안 한 경우
                if(userName.equals("")){
                    /*AlertDialog.Builder builder=new AlertDialog.Builder( RegisterActivity.this );
                    dialog = builder.setMessage("이름을 입력해주세요.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();*/
                    register_alert_dialog(7);
                    return;
                }

                //전화번호 입력 안 한 경우
                String userPhone = u_phone.getText().toString();
                if(userPhone.equals("")){
                    /*AlertDialog.Builder builder=new AlertDialog.Builder( RegisterActivity.this );
                    dialog = builder.setMessage("전화번호를 입력해 주세요.")
                            .setPositiveButton("확인",null)
                            .create();
                    dialog.show();*/
                    register_alert_dialog(8);
                    return;
                }
//                //string "" 인 경우 에러이므로 위에 코드에서 체크 후 int 형으로 변환
//                int userPhone = Integer.parseInt(userPhone);

                Response.Listener<String> responseListener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) { // DB와 연동하여 로그인된 회원의 정보를 받아오는 함수
                        try {
                            JSONObject jsonObject = new JSONObject(response); // 회원 정보들을 받음
                            boolean success = jsonObject.getBoolean("success"); // 인자로 전달된 key에 대한 객체를 "success" 할 시 True 리턴
                            if (success && u_id_check) { // 회원등록과 아이디 중복 체크 성공한 경우
                                Toast.makeText(getApplicationContext(),"회원 등록에 성공하였습니다.",Toast.LENGTH_SHORT).show();
                                //Intent intent = new Intent(RegisterActivity.this, LogInActivity.class);  // 유저 등록 후 로그인화면으로 이동
                                //startActivity(intent);
                                //back 버튼으로 발생되는 유저 데이터 재발행 및 데이터 보호를 위해 finish 사용
                                finish();
                            } else { // 회원등록에 실패한 경우
                                Toast.makeText(getApplicationContext(),"회원 등록에 실패하였습니다.",Toast.LENGTH_SHORT).show();
                                return;
                            }
                        } catch (JSONException e) { // 예외처리
                            e.printStackTrace();
                        }

                    }
                };
                // 서버로 Volley를 이용해 정보 넘김.
                RegisterRequest registerRequest = new RegisterRequest(userID,userPass,userName, userMajor,userPhone, userMid, responseListener);
                RequestQueue queue = Volley.newRequestQueue(RegisterActivity.this);
                queue.add(registerRequest);

            }
        });

    }

    //[ay.han]가입 창에서 사용하는 Custom dialog
    public void register_alert_dialog(int i){
        //0: 학번 중복체크 완료, 1 : 학번 미입력, 2 : 학번 OK, 3 : 학번 NG
        //4 : 학번 중복 안했을때,  5 : 전공 미입력, 6 : 비밀번호 미입력, 7 : 이름 미입력, 8 : 전화번호 미입력

        AlertDialog.Builder builder = new AlertDialog.Builder(RegisterActivity.this);
        View dialogView = getLayoutInflater().inflate(R.layout.dialog_alert, null);
        builder.setView(dialogView);

        final AlertDialog alertDialog = builder.create();
        alertDialog.show();

        TextView login_alert = alertDialog.findViewById(R.id.err_alert);
        Button exit =alertDialog.findViewById(R.id.exit_from_this);

        switch (i){
            case 0:login_alert.setText("중복체크 완료되었습니다.");
                break;
            case 1:login_alert.setText("학번을 입력해주세요.");
                break;
            case 2:login_alert.setText("사용할 수 있는 학번입니다.");
                break;
            case 3:login_alert.setText("이미 등록된 학번입니다.");
                break;
            case 4:login_alert.setText("학번 중복확인을 눌러주세요");
                break;
            case 5:login_alert.setText("전공을 입력해주세요.");
                break;
            case 6:login_alert.setText("비밀번호를 입력해주세요.");
                break;
            case 7:login_alert.setText("이름을 입력해주세요.");
                break;
            case 8:login_alert.setText("전화번호를 입력해주세요.");
                break;
        }

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                alertDialog.dismiss();
            }
        });

        return;
    }
}