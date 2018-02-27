package com.geely.mars.sqldemo;

import io.reactivex.Observable;
import retrofit2.http.GET;

/**
 * Created by Shuai.Li13 on 2018/2/9.
 */

public interface RequestInfer {
    @GET("ajax.php?a=fy&f=auto&t=auto&w=hi%20world")
    Observable<RequestDao> getCall();
}
