package nextus.solarsystem.viewmodel;

import android.content.Context;
import android.content.Intent;
import android.databinding.BaseObservable;
import android.databinding.BindingAdapter;
import android.graphics.Color;
import android.support.design.widget.Snackbar;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import nextus.solarsystem.GlobalApplication;
import nextus.solarsystem.R;
import nextus.solarsystem.adapter.BoardItemRecyclerAdapter;
import nextus.solarsystem.model.BoardItem;
import nextus.solarsystem.utils.ContentService;
import nextus.solarsystem.view.CommentsActivity;
import nextus.solarsystem.view.MainActivity;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by chosw on 2016-08-11.
 */

/**
 *  Recyclerview에 들어가는 layout에 대한 ViewModel
 *  layout에 존재하는 data 세팅을 여기서 해줌
 */

public class BoardItemViewModel extends BaseObservable implements ViewModel{

    private Context context;
    private BoardItem.BoardData boardItem;
    private BoardItemRecyclerAdapter adapter;
    private LinearLayoutManager layoutManager;
    private GridLayoutManager gridLayoutManager;
    private List<String> data;

    public BoardItemViewModel(Context context, BoardItem.BoardData boardItem)
    {
        this.context = context;
        this.boardItem = boardItem;
        this.data = Collections.emptyList();
        this.adapter = new BoardItemRecyclerAdapter(context, data);
        this.layoutManager = new LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false);
        this.gridLayoutManager = new GridLayoutManager(context, 2);
    }

    public void setBoardItem(BoardItem.BoardData boardItem)
    {
        this.boardItem = boardItem;
        notifyChange();
    }

    public BoardItemRecyclerAdapter getAdapter() { return adapter; }

    public void setAdapter(BoardItemRecyclerAdapter adapter)
    {
        this.adapter = adapter;
    }

    public String getImageUrl()
    {
        return data.get(0);
    }

    @BindingAdapter({"bind:imageUrl"})
    public static void loadImage(ImageView view, String imageUrl)
    {
        Glide.with(view.getContext()).load(imageUrl).thumbnail(0.1f).centerCrop().into(view);
    }

    public void setRecyclerView(RecyclerView recyclerView)
    {
        recyclerView.setAdapter(adapter);
        adapter.setImageList(data);
        recyclerView.setLayoutManager(layoutManager);
    }

    public void setData(List<String> data)
    {
        this.data = data;
        notifyChange();
    }

    public interface startBoardItemActivity
    {
        public void boardItemView(View view);
    }

    public void onClickComment()
    {

    }

    public void setLike()
    {
        Log.e(""+GlobalApplication.getGlobalApplicationContext().getUserProfile().getId(), ""+boardItem.board_id);
        Call<ResponseBody> call = ContentService.Factory.create().createLike(""+GlobalApplication.getGlobalApplicationContext().getUserProfile().getId(), ""+boardItem.board_id);

        call.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                ((MainActivity)context).update();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                Log.e("LIKE", t.getMessage());
            }
        });
    }


    public void onClick(View view) {
        switch (view.getId())
        {
            case R.id.like_button:
                setLike();
                break;

            case R.id.comment_button:
                Intent intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("board_id", boardItem.board_id);
                context.startActivity(intent);
                break;

            case R.id.comment_count:
                intent = new Intent(context, CommentsActivity.class);
                intent.putExtra("board_id", boardItem.board_id);
                context.startActivity(intent);
                break;

            case R.id.share_button:
                Snackbar.make(view, "공유", Snackbar.LENGTH_SHORT).show();
                break;

            default:
                Snackbar.make(view,"준비중",Snackbar.LENGTH_SHORT).show();
        }
    }

    public List<String> getData()
    {
        return this.data;
    }

    public int getBoardID() { return boardItem.board_id; }
    public String getBoardText() { return boardItem.board_text; }
    public String getUserThumnail() { return boardItem.board_text; }
    public String getDate()
    {
        String date_string = boardItem.date;
        String date = "";

        Log.e("DATE_STRING",date_string);
        SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm", Locale.KOREA);

        Date current = new Date(System.currentTimeMillis());


        try{
            Date d = format.parse(date_string) ;
            long time_long = current.getTime() - d.getTime();
            int time = (int)time_long/1000;

            Log.e("Time", ""+time);
            if( time < 60 )
            {
                date = "방금";
                //boardData.get(position).date = "방금";
                //holder.binding.date.setText("방금");
            }
            else if( time >=60 && time < 3600 )
            {
                time = time/60;
                date = ""+time+"분 전";
                //boardData.get(position).date = ""+time+"분 전";
                //notifyDataSetChanged();
                //holder.binding.date.setText(""+time+"분 전");
            }
            else if( time >=3600 && time < 86400 )
            {
                time = time/3600;
                date = ""+time+"시간 전";
              //  holder.binding.date.setText(""+time+"시간 전");
            }
            else
            {
                date = date_string;
                //holder.binding.date.setText(date_string);
            }
        }catch (Exception e)
        {
            e.printStackTrace();
        }
        return date;
    }
    public int getImageCount() { return boardItem.image_count; }

    public String getViewCount() {
        String temp = "조회수 "+boardItem.view_count;
        return  temp; }

    public String getCommentCount() {
        String temp = "댓글수 "+boardItem.comment_count;
        return  temp; }
    public String getLikeCount() {
        String temp = "좋아요 "+boardItem.like_count;
        return  temp; }
    public String getUserName() { return boardItem.user_name; }

    public int likeColor() { return boardItem.isLike == 1 ? Color.BLUE : Color.GRAY ;}

    @Override
    public void destroy() {
    }

}
