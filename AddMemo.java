package com.songjumin.mainactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.songjumin.mainactivity.data.DatabaseHandler;
import com.songjumin.mainactivity.model.Model;

public class AddMemo extends AppCompatActivity {

    EditText editTitle;
    EditText editMemo;
    Button btnOk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_memo);

        editTitle = findViewById(R.id.editTitle);
        editMemo = findViewById(R.id.editMemo);
        btnOk = findViewById(R.id.btnOk);

        btnOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = editTitle.getText().toString().trim();
                String content = editMemo.getText().toString().trim();
                //아무것도 입력안했을때,, 만약에 안써있다면.. 이라는 함수임
                if (title.isEmpty() || content.isEmpty()){
                    Toast.makeText(AddMemo.this, "제목과 내용은 필수입니다.", Toast.LENGTH_LONG).show();
                    return;
                }
                //저장하는 코드
                DatabaseHandler dh = new DatabaseHandler(AddMemo.this);
                Model model = new Model();
                model.setTitle(title);
                model.setContent(content);
                dh.addMemo(model);
                // 잘 저장했다고 토스트 띄우고
                Toast.makeText(AddMemo.this, "잘 저장되었습니다.", Toast.LENGTH_LONG).show();
                // 메인액티비티 다시 보이도록해라
                finish();
            }
        });

    }
}
