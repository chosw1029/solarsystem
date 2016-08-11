package nextus.solarsystem.viewmodel;

import android.content.Context;
import android.databinding.BaseObservable;
import android.view.View;

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

    public void onItemClick(View view) {
        //Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(getUrl()));
        //context.startActivity(intent);
    }

    public int getBoardID() { return boardItem.board_id; }
    public String getBoardTitle() { return boardItem.board_title; }
    public String getBoardInfo() { return boardItem.board_info; }
    public String getDate() { return boardItem.date; }
    public String getImageCount() { return boardItem.image_count; }

    public String getViewCount() {
        String temp = ""+boardItem.view_count;
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
