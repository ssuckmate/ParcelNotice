package com.eos.parcelnotice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

import com.eos.parcelnotice.custom_dialog.JoinDialog;
import com.eos.parcelnotice.data.TokenVO;
import com.eos.parcelnotice.databinding.ActivityLoginBinding;
import com.eos.parcelnotice.retrofit.AuthApi;
import com.google.gson.JsonObject;
import com.kakao.auth.ApiErrorCode;
import com.kakao.auth.AuthType;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.network.ErrorResult;
import com.kakao.usermgmt.UserManagement;
import com.kakao.usermgmt.callback.MeV2ResponseCallback;
import com.kakao.usermgmt.response.MeV2Response;
import com.kakao.util.exception.KakaoException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class LoginActivity extends AppCompatActivity {

    private SessionCallBack  sessionCallBack;
    private Boolean loginCheck =false;
    private Retrofit retrofit;
    private SharedPreferences pref;
    SharedPreferences.Editor editor;
    ActivityLoginBinding binding;
    private SharedPreferences settingPrefs, tokenPrefs;
    private SharedPreferences.Editor settingEditor, tokenEditor;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        binding.setActivity(this);
        setContentView(R.layout.activity_login);

        sessionCallBack = new SessionCallBack();

        settingPrefs = getSharedPreferences("setting",this.MODE_PRIVATE);
        settingEditor = settingPrefs.edit();

        tokenPrefs = getSharedPreferences("token", this.MODE_PRIVATE);
        tokenEditor = tokenPrefs.edit();

        retrofit = new Retrofit.Builder()
                .baseUrl(getApplicationContext().getString(R.string.base_url))
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        Session.getCurrentSession().addCallback(sessionCallBack);
        Session.getCurrentSession().checkAndImplicitOpen(); //카카오톡 로그인 한 번 해놓으면 이후 자동로그인 (바로 메인 페이지)

        //자동로그인 (아이디 비밀번호 자동 입력) // 그냥 바로 메인으로 가는 걸로 할까..?
        if(settingPrefs.getBoolean("autoLogin", false)){
            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
            intent.putExtra("userID", settingPrefs.getString("id",""));
            startActivity(intent);
        }

        binding.buttonLoginKakao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Session.getCurrentSession().open(AuthType.KAKAO_LOGIN_ALL, LoginActivity.this);
            }
        });

        binding.buttonLoginLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String email = binding.editTextLoginId.getText().toString();
                String password = binding.editTextLoginPassword.getText().toString();

                JsonObject json = new JsonObject();
                json.addProperty("email", email);
                json.addProperty("password", password);

                Call<TokenVO> call = retrofit.create(AuthApi.class).login(json);
                call.enqueue(new Callback<TokenVO>() {

                    @Override
                    public void onResponse(Call<TokenVO> call, Response<TokenVO> response) {
                        if(response.isSuccessful()){
                            Toast.makeText(LoginActivity.this,response.message().toString(),Toast.LENGTH_SHORT).show();
                            tokenEditor.putString("token", response.body().getToken());
                            tokenEditor.apply();
                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                            startActivity(intent);
                        }else{
                            Toast.makeText(LoginActivity.this,response.message().toString(),Toast.LENGTH_LONG).show();
                        }
                    }
                    @Override
                    public void onFailure(Call call, Throwable t) {
                        Toast.makeText(LoginActivity.this,"알 수 없는 에러입니다. 개발자에게 문의하세요",Toast.LENGTH_LONG).show();
                    }
                });
            }}
        );

        binding.checkboxLoginAutoLogin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    loginCheck = true;
                }
                else{
                    loginCheck = false;
                    settingEditor.clear();
                    settingEditor.commit();
                }
            }
        });

        binding.buttonLoginRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                JoinDialog joinDialog = new JoinDialog(LoginActivity.this);
                joinDialog.setCanceledOnTouchOutside(true);
                joinDialog.setCancelable(true);
                joinDialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
                joinDialog.show();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(Session.getCurrentSession().handleActivityResult(requestCode,resultCode,data)) {
            super.onActivityResult(requestCode, resultCode, data);
            return;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(sessionCallBack);
    }

    private class SessionCallBack implements ISessionCallback {
        @Override
        public void onSessionOpened() {
            UserManagement.getInstance().me(new MeV2ResponseCallback() {
                @Override
                public void onFailure(ErrorResult errorResult) {
                    int result = errorResult.getErrorCode();

                    if(result == ApiErrorCode.CLIENT_ERROR_CODE){
                        Toast.makeText(getApplicationContext(),"네트워크 연결이 불안정 합니다. 다시 시도해 주세요.", Toast.LENGTH_SHORT).show();
                        finish();
                    }
                    else{
                        Toast.makeText(getApplicationContext(),"로그인 도중 오류가 발생했습니다: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onSessionClosed(ErrorResult errorResult) {
                    //로그인 도중 세션이 비정상적인 이유로 닫혔을 때 작동, 거의 없음
                    Toast.makeText(getApplicationContext(),"세션이 닫혔습니다. 다시 시도해 주세요: "+errorResult.getErrorMessage(),Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onSuccess(MeV2Response result) {
                    // 로그인 성공

                    //처음 로그인하는 계정인 경우 ->회원가입


                    //처음 로그인하는 게 아닌 경우
                    Intent intent = new Intent(LoginActivity.this,MainActivity.class);
                    intent.putExtra("userID", result.getId());
                    startActivity(intent);
                }
            });
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            Toast.makeText(getApplicationContext(),"로그인 도중 오류가 발생했습니다. 인터넷 연결을 확인해주세요: "+ exception.toString(), Toast.LENGTH_SHORT).show();
        }
    }
}