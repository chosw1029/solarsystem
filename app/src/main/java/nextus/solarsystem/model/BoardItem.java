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
    public String board_title;
    public String board_info;
    public String user_name;
    public String board_img;
    public String date;
    public String image_count;
    public int view_count;
    public int like_count;
    public int comment_count;

    protected BoardItem(Parcel in)
    {
        this.board_id = in.readInt();
        this.board_title = in.readString();
        this.board_info = in.readString();
        this.user_name = in.readString();
        this.board_img = in.readString();
        this.date = in.readString();
        this.image_count = in.readString();
        this.view_count = in.readInt();
        this.like_count = in.readInt();
        this.comment_count = in.readInt();
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.board_id);
        parcel.writeString(this.board_title);
        parcel.writeString(this.board_info);
        parcel.writeString(this.user_name);
        parcel.writeString(this.board_img);
        parcel.writeString(this.date);
        parcel.writeString(this.image_count);
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

        BoardItem that = (BoardItem) o;

        if (board_id != that.board_id) return false;
        if (view_count != that.view_count) return false;
        if (like_count != that.like_count) return false;
        if (comment_count != that.comment_count) return false;
        if (board_title != null ? !board_title.equals(that.board_title) : that.board_title != null)
            return false;
        if (board_info != null ? !board_info.equals(that.board_info) : that.board_info != null)
            return false;
        if (user_name != null ? !user_name.equals(that.user_name) : that.user_name != null)
            return false;
        if (board_img != null ? !board_img.equals(that.board_img) : that.board_img != null)
            return false;
        if (date != null ? !date.equals(that.date) : that.date != null) return false;
        return image_count != null ? image_count.equals(that.image_count) : that.image_count == null;

    }

    @Override
    public int hashCode() {
        int result = board_id;
        result = 31 * result + (board_title != null ? board_title.hashCode() : 0);
        result = 31 * result + (board_info != null ? board_info.hashCode() : 0);
        result = 31 * result + (user_name != null ? user_name.hashCode() : 0);
        result = 31 * result + (board_img != null ? board_img.hashCode() : 0);
        result = 31 * result + (date != null ? date.hashCode() : 0);
        result = 31 * result + (image_count != null ? image_count.hashCode() : 0);
        result = 31 * result + view_count;
        result = 31 * result + like_count;
        result = 31 * result + comment_count;
        return result;
    }
}
