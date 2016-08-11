package nextus.solarsystem.utils;

import java.util.List;

import nextus.solarsystem.model.BoardItem;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by chosw on 2016-08-10.
 */

public interface ContentService {

    @GET("pokemongo/getBoardData.jsp")
    Observable<List<BoardItem>> getData();

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
