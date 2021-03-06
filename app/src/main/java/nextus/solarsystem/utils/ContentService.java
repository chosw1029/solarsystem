package nextus.solarsystem.utils;


import java.util.List;
import java.util.Map;

import nextus.solarsystem.model.BoardItem;
import nextus.solarsystem.model.Comment;
import nextus.solarsystem.model.UserData;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;
import rx.Observable;

/**
 * Created by chosw on 2016-08-10.
 */

public interface ContentService {

    @FormUrlEncoded
    @POST("pokemongo/deleteComment.jsp")
    Call<ResponseBody> deleteComment(
            @Field("comment_id") String comment_id,
            @Field("board_id") String board_id
    );

    @GET ("pokemongo/getUserData.jsp")
    Observable<List<UserData>> getUserData();

    @FormUrlEncoded
    @POST("pokemongo/updateToken.jsp")
    Call<ResponseBody> updateToken(
            @Field("user_id") String user_id,
            @Field("token") String token) ;

    @FormUrlEncoded
    @POST("pokemongo/getBoardData.jsp")
    Observable<BoardItem> getData(@Field("user_id") String user_id) ;

    @FormUrlEncoded
    @POST("pokemongo/getCommentData.jsp")
    Observable<List<Comment>> getCommentData(@Field("board_id") int board_id);

    @FormUrlEncoded
    @POST("pokemongo/createLike.jsp")
    Call<ResponseBody> createLike(
            @Field("user_id") String user_id,
            @Field("board_id") String board_id
            );

    @FormUrlEncoded
    @POST("pokemongo/addUser.jsp")
    Call<ResponseBody> addUserData(
            @Field("userID") String userID,
            @Field("userNickName") String userNickName,
            @Field("userThumnail") String userThumnail,
            @Field("user_token") String token,
            @Field("user_birthday") String user_birthday,
            @Field("user_phone") String user_phone
    );

    @FormUrlEncoded
    @POST("pokemongo/createComment.jsp")
    Call<ResponseBody> createComment(
            @Field("board_id") String board_id,
            @Field("user_id") String user_id,
            @Field("user_name") String user_name,
            @Field("comment_data") String comment_data,
            @Field("date") String date
    );

    @Multipart
    @POST("pokemongo/multipart_temp.jsp")
    Call<ResponseBody> upload(@Part("description") RequestBody description,
                              @Part MultipartBody.Part file);

    @Multipart
    @POST("pokemongo/multipart_temp.jsp")
    Call<ResponseBody> uploadFileWithPartMap(@PartMap() Map<String, RequestBody> partMap, @Part MultipartBody.Part file);

    /*
    @GET
    Observable<User> userFromUrl(@Url String userUrl);
*/

    class Factory {
        public static ContentService create() {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://restartallkill.nextus.co.kr/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
            return retrofit.create(ContentService.class);
        }
    }
}
