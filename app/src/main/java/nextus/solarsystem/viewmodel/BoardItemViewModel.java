package nextus.solarsystem.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.support.design.widget.Snackbar;
import android.util.Log;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import nextus.solarsystem.model.BoardItem;

/**
 * Created by chosw on 2016-08-11.
 */

/**
 *  Recyclerview에 들어가는 layout에 대한 ViewModel
 *  layout에 존재하는 data 세팅을 여기서 해줌
 */

public class BoardItemViewModel extends BaseObservable implements ViewModel{

    private Context context;
    private BoardItem boardItem;

    public BoardItemViewModel(Context context, BoardItem boardItem)
    {
        this.context = context;
        this.boardItem = boardItem;
    }

    public void setBoardItem(BoardItem boardItem)
    {
        this.boardItem = boardItem;
        notifyChange();
    }

    public void onClick(View view) {
        Snackbar.make(view,"준비중",Snackbar.LENGTH_SHORT).show();
    }

    public int getBoardID() { return boardItem.board_id; }
    public String getBoardTitle() { return boardItem.board_title; }
    public String getBoardInfo() { return boardItem.board_info; }
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
    public String getImageCount() { return boardItem.image_count; }

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
    public String getBoardImg() { return boardItem.board_img; }

    @Override
    public void destroy() {
    }

}
