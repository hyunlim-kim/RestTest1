package com.example.hyunlim.resttest1;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.hyunlim.resttest1.ImageLoaderTask;
import com.google.gson.Gson;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.FormHttpMessageConverter;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by qnqnqn1239 on 2017. 7. 25..
 */

public class MemListAdapter extends BaseAdapter {

    private Context mcontext;
    private List<MemberBean.MemberBeanSub> memberlist = new ArrayList<MemberBean.MemberBeanSub>();

    //생성자
    public MemListAdapter(Context context) {
        mcontext = context;
        updateMemberListTask();
    }

    @Override
    public int getCount() {
        return memberlist.size();
    }

    @Override
    public Object getItem(int position) {
        return memberlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public void updateMemberListTask(){
        new MemberListTask().execute();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        //1.인플레이트 하기
        LayoutInflater li = (LayoutInflater)mcontext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView=  li.inflate(R.layout.view_memberlist, null);

        //2.인플레이터한 레이아웃에서 컴포넌트를 찾는다
        ImageView imgProfile = (ImageView)convertView.findViewById(R.id.imgProfile);
        TextView txtName = (TextView)convertView.findViewById(R.id.txtName);
        TextView txtId = (TextView)convertView.findViewById(R.id.txtId);

        //3.데이터를 가져온다
        final MemberBean.MemberBeanSub bean = memberlist.get(position);

        //4.찾은 컴포넌트에 데이터 대입
        new ImageLoaderTask(imgProfile).execute(Constants.BASE_URL + bean.getProfileImg());
        txtName.setText(bean.getName());
        txtId.setText(bean.getUserId());


        //리스트를 누르면 화면이 이동하는
        convertView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) { //익명클래스 -->클래스 밖에있는 변수를 사용할것이기 때문에 사용할 변수를 밖에서 final로 고정

                Intent intent = new Intent(mcontext , MemUpdateActivity.class); //현제는 인플레이터를 통해 context에 현재 액티비트의 정보를 가지고 있음
                intent.putExtra("memberBean",bean);
                mcontext.startActivity(intent);

            }
        });


        return convertView;

    };//end getView


    //화면이 나타날때마다 갱신해줘야 하기 때문에 adapter가 뜰때마다 회원정보를 가져옴
    class MemberListTask extends AsyncTask<String, Void,String>{

        private String URL_MEMBER_LIST = Constants.BASE_URL + "/rest/selectMemberList.do";
        @Override
        protected String doInBackground(String... params) {

            try{
                RestTemplate restTemplate = new RestTemplate();
                restTemplate.getMessageConverters().add(new FormHttpMessageConverter());

                MultiValueMap<String, Object> map = new LinkedMultiValueMap<String, Object>();

                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.ALL.APPLICATION_FORM_URLENCODED);

                HttpEntity<MultiValueMap<String,Object>> request = new HttpEntity<>(map,headers);

                return restTemplate.postForObject(URL_MEMBER_LIST, request,String.class);

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
                        memberlist = bean.getMemberList();
                        //리스트 뷰에서 수정된 데이터만 갈아 끼워지도록 하기 위한 메소드
                        MemListAdapter.this.notifyDataSetInvalidated();
                    }
                }
            }catch (Exception e){
                Toast.makeText(mcontext, "파싱 실패", Toast.LENGTH_SHORT).show();
                e.printStackTrace();
            }

        }//end onPostExecute
    }//end MemberListTask






}//end class

