package com.songjumin.mainactivity.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.songjumin.mainactivity.R;
import com.songjumin.mainactivity.UpdateMemo;
import com.songjumin.mainactivity.data.DatabaseHandler;
import com.songjumin.mainactivity.model.Model;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    Context context;
    ArrayList<Model> memoList;

    public RecyclerViewAdapter(Context context, ArrayList<Model> memoList) {
        this.context = context;
        this.memoList = memoList;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // 첫번째 파라미터인, parent 로 부터 뷰를 생성한다.
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.memo_row, parent, false);
        // 리턴에, 위에서 생성한 뷰를, 뷰홀더에 담아서 리턴한다.
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
        Model model = memoList.get(position);
        String title = model.getTitle();
        String content = model.getContent();
        // 뷰홀더에 표시해라
        holder.txtTitle.setText(title);
        holder.txtContent.setText(content);
    }

    @Override
    public int getItemCount() {
        return memoList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView txtTitle;
        public TextView txtContent;
        public ImageView imgDelete;
        public CardView cardView;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            txtTitle = itemView.findViewById(R.id.txtTitle);
            txtContent = itemView.findViewById(R.id.txtContent);
            imgDelete = itemView.findViewById(R.id.imgDelete);
            cardView = itemView.findViewById(R.id.cardView);
            cardView.setOnClickListener(new View.OnClickListener() {  // 카드뷰 누르면 내용나오는 것 셋온클릭리스너
                @Override
                public void onClick(View v) {
                    int index = getAdapterPosition();  //몇번째 카드뷰 눌렀는지 알려주는 함수

                    Model model = memoList.get(index);
                    int id = model.getId();
                    String title = model.getTitle();
                    String content = model.getContent();

                    Intent i = new Intent(context, UpdateMemo.class);
                    i.putExtra("id", id);
                    i.putExtra("title", title);
                    i.putExtra("content", content);
                    context.startActivity(i);

                }
            });

            imgDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    AlertDialog.Builder deletelAlert = new AlertDialog.Builder(context);
                    deletelAlert.setTitle("메모 삭제");
                    deletelAlert.setMessage("정말 삭제하시겠습니까?");
                    deletelAlert.setPositiveButton("YES", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            int index = getAdapterPosition(); //몇번째 카드뷰 눌렀는지 알려주는 함수
                            Model model = memoList.get(index);
                            DatabaseHandler dh = new DatabaseHandler(context);
                            dh.deleteMemo(model);
                            memoList = dh.getAllMemo();
                            notifyDataSetChanged();
                        }
                    });
                    deletelAlert.setNegativeButton("NO", null);
                    deletelAlert.setCancelable(false);
                    deletelAlert.show();
                }
            });
        }
    }
}
