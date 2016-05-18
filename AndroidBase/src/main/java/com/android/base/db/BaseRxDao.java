/*
package com.android.base.db;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.android.base.db.impl.DbCallBack;
import com.android.base.util.rx.RxUtil;
import com.apkfuns.logutils.LogUtils;

import java.util.List;
import java.util.concurrent.Callable;

import rx.Subscriber;
import rx.subscriptions.CompositeSubscription;

*/
/**
 * 提供同步与异步两种方式读写数据库
 * 如果使用异步方式读写数据库，必须调用{@link BaseRxDao#subscribe()}方法订阅，
 * 调用{@link BaseRxDao#unsubscribe()}方法取消订阅
 *//*

public abstract class BaseRxDao<T, Integer> extends BaseOrmLiteDao<T, Integer> {

    private CompositeSubscription mSubscriptions;
    private boolean mCache;
    private Class<T> mClazz;
    private String mTableName;

    public BaseRxDao(Context context, Class<T> cls) {
        this(context, cls, true);
    }

    */
/**
     * @param cls     表结构clazz
     * @param cache   是否缓存，如果设置缓存，数据查询将优先读取缓存
     *//*

    public BaseRxDao(Context context, Class<T> cls, boolean cache) {
        super(context);
        this.mClazz = cls;
        this.mCache = cache;
        mTableName = DatabaseUtil.extractTableName(cls);
    }

    */
/**
     * 订阅读写操作的返回结果
     * <p/>
     * 注意：调用{@link BaseRxDao#unsubscribe()}方法后，如果需要重新订阅读写操作，必须调用此方法
     *//*

    public void subscribe() {
        mSubscriptions = RxUtil.getNewCompositeSubIfUnsubscribed(mSubscriptions);
    }

    */
/**
     * 异步读写后，必须调用此方法取消订阅
     *//*

    public void unsubscribe() {
        RxUtil.unsubscribeIfNotNull(mSubscriptions);
    }

    */
/**
     * 增加一条记录
     *//*

    public boolean insert(T t) {
        boolean result = super.insert(t);
        if (result) {
            DbCache.getInstance().clearByTable(mTableName);
        }
        return result;
    }

    */
/**
     * 增加一条记录
     *//*

    */
/*public Observable rxInsert(final T t, final DbCallBack listener) {
        Observable observable = subscribe(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return insert(t);
            }
        }, new Action1<Boolean>() {
            @Override
            public void call(Boolean result) {
                listener.onComplete(result);
            }
        });
        return observable;
    }*//*


    */
/**
     * 增加一条记录
     *//*

    public void rxInsert(final T t, final DbCallBack listener) {
        RxUtil.subscribe(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return insert(t);
            }
        }, new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }

            @Override
            public void onNext(Boolean result) {
                listener.onComplete(result);
            }
        }, mSubscriptions);
    }

    */
/**
     * 批量插入;
     *//*

    public boolean insertForBatch(List<T> list) {
        boolean result = super.insertForBatch(list);
        if (result) {
            DbCache.getInstance().clearByTable(mTableName);
        }
        return result;
    }

    */
/**
     * 批量插入
     *//*
*/
/*
    public Observable rxInsertForBatch(final List<T> list, final DbCallBack listener) {
        Observable observable = subscribe(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return insertForBatch(list);
            }
        }, new Action1<Boolean>() {
            @Override
            public void call(Boolean result) {
                listener.onComplete(result);
            }
        });
        return observable;
    }*//*


    */
/**
     * 批量插入
     *//*

    public void rxInsertForBatch(final List<T> list, final DbCallBack listener) {
        RxUtil.subscribe(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return insertForBatch(list);
            }
        }, new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }

            @Override
            public void onNext(Boolean result) {
                listener.onComplete(result);
            }
        }, mSubscriptions);
    }

    */
/**
     * 清空数据
     *//*

    public boolean clearTableData() {
        boolean result = super.clearTableData();
        if (result) {
            DbCache.getInstance().clearByTable(mTableName);
        }
        return result;
    }

    */
/**
     * 清空数据
     *//*
*/
/*
    public Observable rxClearTableData(final DbCallBack listener) {
        Observable observable = subscribe(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return clearTableData();
            }
        }, new Action1<Boolean>() {
            @Override
            public void call(Boolean result) {
                listener.onComplete(result);
            }
        });
        return observable;
    }*//*


    */
/**
     * 清空数据
     *//*

    public void rxClearTableData(final DbCallBack listener) {
        RxUtil.subscribe(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return clearTableData();
            }
        }, new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }

            @Override
            public void onNext(Boolean result) {
                listener.onComplete(result);
            }
        }, mSubscriptions);
    }

    */
/**
     * 根据id删除记录
     *//*

    public boolean deleteById(Integer id) {
        boolean result = super.deleteById(id);
        if (result) {
            DbCache.getInstance().clearByTable(mTableName);
        }
        return result;
    }

    */
/**
     * 根据id删除记录
     *//*
*/
/*
    public Observable rxDeleteById(final Integer id, final DbCallBack listener) {
        Observable observable = subscribe(new Callable<Boolean>() {
            @Override
            public Boolean call() {
                return deleteById(id);
            }
        }, new Action1<Boolean>() {
            @Override
            public void call(Boolean result) {
                listener.onComplete(result);
            }
        });
        return observable;
    }*//*


    */
/**
     * 根据id删除记录
     *//*

    public void rxDeleteById(final Integer id, final DbCallBack listener) {
        RxUtil.subscribe(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return deleteById(id);
            }
        }, new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }

            @Override
            public void onNext(Boolean result) {
                listener.onComplete(result);
            }
        }, mSubscriptions);
    }

    public List<T> queryForAll() {
        if (!mCache) {
            return super.queryForAll();
        }
        String json = DbCache.getInstance().getCache(mTableName, "queryForAll");
        List<T> result = JSON.parseArray(json, mClazz);
        if (result != null) {
            LogUtils.d("---------query from cache--");
            return result;
        }
        result = super.queryForAll();
        DbCache.getInstance().addCache(mTableName, "queryForAll", result);
        return result;
    }

    */
/*public Observable queryForAllObservable() {
        return getDbObservable(new Callable() {
            @Override
            public Object call() throws Exception {
                return queryForAll();
            }
        });
    }*//*


    */
/*public Observable rxQueryForAll(final DbCallBack listener) {
        Observable observable = subscribe(new Callable<List<T>>() {
            @Override
            public List<T> call() {
                return queryForAll();
            }
        }, new Action1<List<T>>() {
            @Override
            public void call(List<T> result) {
                listener.onComplete(result);
            }
        });

        return observable;
    }*//*


    */
/**
     * 查询全部
     * @param listener
     * @return
     *//*

    public void rxQueryForAll(final DbCallBack listener) {
        RxUtil.subscribe(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return queryForAll();
            }
        }, new Subscriber<List<T>>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }

            @Override
            public void onNext(List<T> result) {
                listener.onComplete(result);
            }
        }, mSubscriptions);
    }

    */
/*public <T> Observable<T> subscribe(Callable<T> callable, Action1<T> action) {
        Observable<T> observable = getDbObservable(callable);
        Subscription subscription = observable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(action);
        if (mSubscriptions == null) {
            throw new RuntimeException("Do you call subscribe()");
        }
        mSubscriptions.add(subscription);
        return observable;
    }


    private <T> Observable<T> getDbObservable(final Callable<T> func) {
        return Observable.create(
                new Observable.OnSubscribe<T>() {
                    @Override
                    public void call(Subscriber<? super T> subscriber) {
                        try {
                            subscriber.onNext(func.call());
                        } catch (Exception ex) {
                            LogUtils.e("Error reading from the database", ex);
                        }
                    }
                });
    }*//*



    */
/**
     * 增加一条记录
     *//*

    public void rxUpdate(final T t, final DbCallBack listener) {
        RxUtil.subscribe(new Callable<Boolean>() {
            @Override
            public Boolean call() throws Exception {
                return update(t);
            }
        }, new Subscriber<Boolean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                listener.onError(e);
            }

            @Override
            public void onNext(Boolean result) {
                listener.onComplete(result);
            }
        }, mSubscriptions);
    }
}
*/
