package com.example.hyunlim.resttest1;

import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

public class JoinActivity extends AppCompatActivity {


    private EditText edtJoinName,edtJoinId,edtJoinPW,edtJoinHp;
    private ProgressBar JoinProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_join);



        //컨퍼런스 찾기
        edtJoinName = (EditText)findViewById(R.id.edtJoinName);
        edtJoinId = (EditText)findViewById(R.id.edtJoinId);
        edtJoinPW = (EditText)findViewById(R.id.edtJoinPw);
        edtJoinHp = (EditText)findViewById(R.id.edtJoinHp);

        JoinProgressBar = (ProgressBar)findViewById(R.id.JoinprogressBar);


        //회원가입 버튼 클릭 리스너 지정
        findViewById(R.id.btnJoinOK).setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                new JoinTask().execute();
            }//end onClick
        });//setOnClickListener

    }//end onCreate




    //회원가입 처리
    private class JoinTask extends AsyncTask<String, Void, String> {


        //URL고치기
        public static final String URL_JOIN_PROC = Constants.BASE_URL +"/rest/insertMember.do";

        private String name,userId,userpw,hp;

        @Override
        protected void onPreExecute() {
            JoinProgressBar.setVisibility(View.VISIBLE);

            //스래드에서는 컨퍼런스에 접근할 수 없기 때문에 변수에 미리 넣어준다
            name = edtJoinName.getText().toString();
            userId = edtJoinId.getText().toString();
            userpw = edtJoinPW.getText().toString();
            hp = edtJoinHp.getText().toString();

        }//end onPreExcute

        @Override
        protected String doInBackground(String... params) {

            try{
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

                MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();
                map.add("name",name);
                map.add("userId",userId);
                map.add("userpw",userpw);
                map.add("hp",hp);

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.ALL.APPLICATION_FORM_URLENCODED);

                HttpEntity<MultiValueMap<String,Object>> request = new HttpEntity<>(map,headers);

                return restTemplate.postForObject(URL_JOIN_PROC, request,String.class);

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }//end doInBackground

        @Override
        protected void onPostExecute(String s) {

            JoinProgressBar.setVisibility(View.INVISIBLE);

            Gson gson = new Gson();
            try{
                MemberBean bean = gson.fromJson(s, MemberBean.class);
                if(bean !=null){
                    if(bean.getResult().equals("OK")){
                        //회원가입 성공
                        Intent i = new Intent(JoinActivity.this,LoginSuccActivity.class);
                        startActivity(i);
                        finish();
                    } else{
                        //회원가입 실패
                        Toast.makeText(JoinActivity.this,bean.getResultMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }catch (Exception e){
                Toast.makeText(JoinActivity.this, "파싱 실패", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
    }//end MemeberAddTask

}//end class
