package com.songjumin.mainactivity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.songjumin.mainactivity.adapter.RecyclerViewAdapter;
import com.songjumin.mainactivity.data.DatabaseHandler;
import com.songjumin.mainactivity.model.Model;
import com.songjumin.mainactivity.util.Util;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    Button btnMemo;
    RecyclerView recyclerView;
    RecyclerViewAdapter recyclerViewAdapter;
    ArrayList<Model> modelArrayList;

    EditText editSearch;
    ImageView imgSearch;
    ImageView imgClear;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnMemo = findViewById(R.id.btnMemo);
        recyclerView = findViewById(R.id.recyclerView);
        editSearch = findViewById(R.id.editSearch); //
        imgSearch = findViewById(R.id.imgSearch); //
        imgClear = findViewById(R.id.imgClear); //

        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));

        btnMemo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this, AddMemo.class);
                startActivity(i);
            }
        });
        // 엑스 이미지 누르면 썼던거 다 지워라
        imgClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editSearch.setText("");

                // 지우고 데이터베이스에서 데이터 가져와서 띄워라
                DatabaseHandler dh = new DatabaseHandler(MainActivity.this);
                modelArrayList = dh.getAllMemo();
                recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, modelArrayList);
                recyclerView.setAdapter(recyclerViewAdapter);
  //              recyclerViewAdapter.notifyDataSetChanged();
            }
        });
        // 돋보기 누를때 적혀있는 글자 가져와라. 그걸 키워드에 저장하라.
        imgSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = editSearch.getText().toString().trim();
                DatabaseHandler dh = new DatabaseHandler(MainActivity.this);
                modelArrayList = dh.search(keyword);
                recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, modelArrayList);
                recyclerView.setAdapter(recyclerViewAdapter);

 //               recyclerViewAdapter.notifyDataSetChanged();
            }
        });
        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String keyword = s.toString();
                DatabaseHandler dh = new DatabaseHandler(MainActivity.this);
                modelArrayList = dh.search(keyword);
                recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, modelArrayList);
                recyclerView.setAdapter(recyclerViewAdapter);
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        DatabaseHandler dh = new DatabaseHandler(MainActivity.this);
        modelArrayList = dh.getAllMemo();
        // 어댑터를 연결해야지 화면에 표시가됨.
        recyclerViewAdapter = new RecyclerViewAdapter(MainActivity.this, modelArrayList);
        recyclerView.setAdapter(recyclerViewAdapter);
    }

}



