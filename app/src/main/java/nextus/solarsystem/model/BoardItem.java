package nextus.solarsystem.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by chosw on 2016-08-11.
 */


/**
 *  Model : Business Logic and  Data
 *  RecyclerView의 item Layout에 들어갈 Data 설정
 *
 */
public class BoardItem implements Parcelable{

    public int board_id;
    public String user_id;
    public String board_text;
    public String user_name;
    public String user_thumnail;
    public String date;
    public String board_img;

    public int image_count;
    public int view_count;
    public int like_count;
    public int comment_count;

    protected BoardItem(Parcel in)
    {
        this.board_id = in.readInt();
        this.user_id = in.readString();
        this.user_name = in.readString();
        this.board_text = in.readString();
        this.user_thumnail = in.readString();
        this.board_img = in.readString();
        this.date = in.readString();
        this.image_count = in.readInt();
        this.view_count = in.readInt();
        this.like_count = in.readInt();
        this.comment_count = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.board_id);
        parcel.writeString(this.user_id);
        parcel.writeString(this.user_thumnail);
        parcel.writeString(this.user_name);
        parcel.writeString(this.board_text);
        parcel.writeString(this.board_img);
        parcel.writeString(this.date);
        parcel.writeInt(this.image_count);
        parcel.writeInt(this.view_count);
        parcel.writeInt(this.like_count);
        parcel.writeInt(this.comment_count);
    }

    public static final Creator<BoardItem> CREATOR = new Creator<BoardItem>() {
        public BoardItem createFromParcel(Parcel source) {
            return new BoardItem(source);
        }

        public BoardItem[] newArray(int size) {
            return new BoardItem[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof BoardItem)) return false;

        BoardItem boardItem = (BoardItem) o;

        if (board_id != boardItem.board_id) return false;
        if (image_count != boardItem.image_count) return false;
        if (view_count != boardItem.view_count) return false;
        if (like_count != boardItem.like_count) return false;
        if (comment_count != boardItem.comment_count) return false;
        if (user_id != null ? !user_id.equals(boardItem.user_id) : boardItem.user_id != null)
            return false;
        if (board_text != null ? !board_text.equals(boardItem.board_text) : boardItem.board_text != null)
            return false;
        if (user_name != null ? !user_name.equals(boardItem.user_name) : boardItem.user_name != null)
            return false;
        if (user_thumnail != null ? !user_thumnail.equals(boardItem.user_thumnail) : boardItem.user_thumnail != null)
            return false;
        if (date != null ? !date.equals(boardItem.date) : boardItem.date != null) return false;
        return board_img != null ? board_img.equals(boardItem.board_img) : boardItem.board_img == null;

    }

    @Override
    public int hashCode() {
        int result = board_id;
        result = 31 * result + (user_id != null ? user_id.hashCode() : 0);
        result = 31 * result + (board_text != null ? board_text.hashCode() : 0);
        result = 31 * result + (user_name != null ? user_name.hashCode() : 0);
        result = 31 * result + (user_thumnail != null ? user_thumnail.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (board_img != null ? board_img.hashCode() : 0);
        result = 31 * result + image_count;
        result = 31 * result + view_count;
        result = 31 * result + like_count;
        result = 31 * result + comment_count;
        return result;
    }
}
