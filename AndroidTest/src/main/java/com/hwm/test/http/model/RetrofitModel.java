package com.hwm.test.http.model;

import com.android.base.util.rxjava.RxUtil;
import com.hwm.test.http.model.entity.Geye;
import com.hwm.test.http.model.entity.News;
import com.hwm.test.http.model.http.GeyeService;
import com.hwm.test.http.model.http.RetrofitServiceFactory;
import com.hwm.test.http.model.http.ZhihuService;
import com.hwm.test.http.presenter.RetrofitContract;

import rx.Observable;

/**
 * Created by Administrator on 2016/4/27.
 */
public class RetrofitModel implements RetrofitContract.Model {

    private static RetrofitModel sInstance;
    private ZhihuService mZhihuService;
    private GeyeService mGeyeService;

    public static RetrofitModel getInstance() {
        if (sInstance == null) {
            synchronized (RetrofitModel.class) {
                if (sInstance == null) {
                    sInstance = new RetrofitModel();
                }
            }
        }
        return sInstance;
    }

    @Override
    public Observable<News> loadLastestNewsData() {
        mZhihuService = RetrofitServiceFactory.provideZhihuService();
        return mZhihuService.getLastestNews().compose(RxUtil.<News>applySchedulersForRetrofit());
    }

    @Override
    public Observable<Geye> loadGeyeData(String geyeId, String isNeedff) {
        mGeyeService = RetrofitServiceFactory.provideGeyeService();
        return mGeyeService.getGeyeData(geyeId, isNeedff).compose(RxUtil.<Geye>applySchedulersForRetrofit());
    }
}
