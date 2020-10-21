package com.songjumin.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.songjumin.mainactivity.data.DatabaseHandler;
import com.songjumin.mainactivity.model.Model;

public class UpdateMemo extends AppCompatActivity {

    EditText editTitle;
    EditText editContent;
    Button btnUpdate;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_memo);

        editTitle = findViewById(R.id.editTitle);
        editContent = findViewById(R.id.editContent);
        btnUpdate = findViewById(R.id.btnUpdate);

        // 어댑터의 카드뷰 클릭하면, 여기서 데이터를 받아준다.
        id =  getIntent().getIntExtra("id", -1);
        String title = getIntent().getStringExtra("title");
        String content = getIntent().getStringExtra("content");
        // 화면에 표시
        editTitle.setText(title);
        editContent.setText(content);

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString().trim();
                String content = editContent.getText().toString().trim();

                Model model = new Model(id, title, content);
                DatabaseHandler dh = new DatabaseHandler(UpdateMemo.this);
                dh.updateMemo(model);
                Toast.makeText(UpdateMemo.this, "수정 완료 되었습니다.", Toast.LENGTH_LONG).show();
                finish(); // 수정완료 버튼 누르면 메인화면으로 돌아간다.

            }
        });

    }
}
