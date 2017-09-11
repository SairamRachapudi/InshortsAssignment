package com.sairam.data.model.retrofit;


import com.sairam.data.model.ApiConstants;
import com.sairam.data.model.NewsArticleModel;
import java.util.List;
import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Sairam Rachapudi on 10/09/17.
 */

public interface HackerEarthApiService {

    @GET(ApiConstants.NEWS_API)
    Observable<List<NewsArticleModel>> getListFromAPI();
}
