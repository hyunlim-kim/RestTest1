package com.example.hyunlim.resttest1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.io.InputStream;
import java.net.URL;

public class MemUpdateActivity extends AppCompatActivity {

    private EditText edtJoinName,edtJoinId,edtJoinPW,edtJoinHp;
    private ImageView mImgProfile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_update);

        edtJoinName = (EditText)findViewById(R.id.edtJoinName);
        edtJoinId = (EditText)findViewById(R.id.edtJoinId);
        edtJoinPW = (EditText)findViewById(R.id.edtJoinPw);
        edtJoinHp = (EditText)findViewById(R.id.edtJoinHp);
        mImgProfile = (ImageView)findViewById(R.id.imgProfile);

        MemberBean.MemberBeanSub memberBean = (MemberBean.MemberBeanSub)getIntent().getSerializableExtra("memberBean");

        edtJoinName.setText(memberBean.getName());
        edtJoinId.setText(memberBean.getUserId());
        edtJoinPW.setText(memberBean.getUserpw());
        edtJoinHp.setText(memberBean.getHp());

        new ImageLoaderTask(mImgProfile).execute(Constants.BASE_URL + memberBean.getProfileImg());



        findViewById(R.id.btnUpdateOK).setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                new UpdateTask().execute();
            }
        });

    }//end onCreate


    //회원수정 처리
    private class UpdateTask extends AsyncTask<String, Void, String> {


        //URL고치기
        public static final String URL_UPDATE_PROC = Constants.BASE_URL +"/rest/updateMember.do";

        private String name,userId,userpw,hp;

        @Override
        protected void onPreExecute() {

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

                return restTemplate.postForObject(URL_UPDATE_PROC, request,String.class);

            }catch (Exception e){
                e.printStackTrace();
            }

            return null;
        }//end doInBackground

        @Override
        protected void onPostExecute(String s) {


            Gson gson = new Gson();
            try{
                MemberBean bean = gson.fromJson(s, MemberBean.class);
                if(bean !=null){
                    if(bean.getResult().equals("OK")){
                        //회원 수정 성공
                        finish();
                    } else{
                        //회원 수정 실패
                        Toast.makeText(MemUpdateActivity.this,bean.getResultMsg(),Toast.LENGTH_SHORT).show();
                    }
                }
            }catch (Exception e){
                Toast.makeText(MemUpdateActivity.this, "파싱 실패", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }
    }//end MemeberAddTask

    //이미지 비동기 로딩 Task
    class ImageLoaderTask extends AsyncTask<String, Void, Bitmap> {

        private ImageView dispImageView;

        public ImageLoaderTask(ImageView dispImgView) {
            this.dispImageView =dispImgView;
        }

        @Override
        protected Bitmap doInBackground(String... params) {

            String imgUrl = params[0];

            Bitmap bmp = null;

            try {
                bmp = BitmapFactory.decodeStream(  (InputStream)new URL(imgUrl).getContent()  );
            } catch (Exception e) {
                e.printStackTrace();
            }

            return bmp;
        }//end doInBackground()

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            if(bitmap != null){
                //표시
                dispImageView.setImageBitmap(bitmap);
            }
        }

    };
}//end class
