package com.example.hyunlim.resttest1;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.example.hyunlim.resttest1.R;

import java.util.ArrayList;
import java.util.List;

import static com.example.hyunlim.resttest1.R.id.listView;

public class MemListActivity extends AppCompatActivity {

    private ListView mListView;
    private MemListAdapter memListAdapter;

    /*내가 만든 스크롤 위치 저장*/
    private int pos; //스크롤의 위치를 저장할 변수


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mem_list);

        mListView = (ListView)findViewById(listView);
        memListAdapter = new MemListAdapter(this);
        mListView.setAdapter(memListAdapter);

    }//onCreate





    @Override
    protected void onResume() {
        super.onResume();
        memListAdapter.updateMemberListTask(); //데이터 갱신

        /*내가 만든 스크롤 위치 저장 */
        mListView.setSelection(pos);//스크롤 뷰 위치 설정
    }//onResume

    @Override
    public void onPause() {
        super.onPause();

        /*내가 만든 스크롤 위치 저장 */
        pos = mListView.getFirstVisiblePosition(); //현재 스크롤 뷰의 위치를 저장
    }



}//end class







