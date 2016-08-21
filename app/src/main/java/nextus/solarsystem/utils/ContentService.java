package nextus.solarsystem.utils;


import java.util.List;
import java.util.Map;

import nextus.solarsystem.model.BoardItem;
import nextus.solarsystem.model.Comment;
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

    @GET("pokemongo/getBoardData.jsp")
    Observable<BoardItem> getData();

    @FormUrlEncoded
    @POST("pokemongo/getCommentData.jsp")
    Observable<List<Comment>> getCommentData(@Field("board_id") int board_id);

    @FormUrlEncoded
    @POST("pokemongo/addUser.jsp")
    Call<ResponseBody> addUserData(
            @Field("userID") String userID,
            @Field("userNickName") String userNickName,
            @Field("userThumnail") String userThumnail
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
