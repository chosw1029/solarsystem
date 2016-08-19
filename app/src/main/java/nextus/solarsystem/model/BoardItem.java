package nextus.solarsystem.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by chosw on 2016-08-11.
 */


/**
 *  Model : Business Logic and  Data
 *  RecyclerView의 item Layout에 들어갈 Data 설정
 *
 */
public class BoardItem {

    //public String board_img;
    @SerializedName("board_data") public List<BoardData> boardData = new ArrayList<>();
    @SerializedName("board_imgList") public List<List<String>> board_img = new ArrayList<>();

    public List<BoardData> getBoardData() {
        return boardData;
    }

    public List<List<String>> getBoard_img() {
        return board_img;
    }
/*
    public static class BoardImage
    {
        @SerializedName("board_img") public List<String> board_img;

        public List<String> getBoard_img() {
            return board_img;
        }

        public BoardImage()
        {}

    }
*/
    public static class BoardData implements Parcelable
    {
        @SerializedName("board_id") public int board_id;
        @SerializedName("user_id") public String user_id;
        @SerializedName("board_text") public String board_text;
        @SerializedName("user_name") public String user_name;
        @SerializedName("user_thumnail") public String user_thumnail;
        @SerializedName("date") public String date;

        @SerializedName("image_count") public int image_count;
        @SerializedName("view_count") public int view_count;
        @SerializedName("like_count") public int like_count;
        @SerializedName("comment_count") public int comment_count;

        public BoardData(Parcel in)
        {
            this.board_id = in.readInt();
            this.user_id = in.readString();
            this.user_name = in.readString();
            this.board_text = in.readString();
            this.user_thumnail = in.readString();
            this.date = in.readString();
            this.image_count = in.readInt();
            this.view_count = in.readInt();
            this.like_count = in.readInt();
            this.comment_count = in.readInt();
        }

        @Override
        public int describeContents() {
            return 0;
        }

        @Override
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(this.board_id);
            parcel.writeString(this.user_id);
            parcel.writeString(this.user_thumnail);
            parcel.writeString(this.user_name);
            parcel.writeString(this.board_text);
            parcel.writeString(this.date);
            parcel.writeInt(this.image_count);
            parcel.writeInt(this.view_count);
            parcel.writeInt(this.like_count);
            parcel.writeInt(this.comment_count);
        }

        public final Creator<BoardData> CREATOR = new Creator<BoardData>() {
            public BoardData createFromParcel(Parcel source) {
                return new BoardData(source);
            }

            public BoardData[] newArray(int size) {
                return new BoardData[size];
            }
        };
    }

    /*
    protected BoardItem(Parcel in)
    {
        this.board_id = in.readInt();
        this.user_id = in.readString();
        this.user_name = in.readString();
        this.board_text = in.readString();
        this.user_thumnail = in.readString();
        this.board_img = in.readArrayList(ClassLoader.getSystemClassLoader());
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
*/
}
