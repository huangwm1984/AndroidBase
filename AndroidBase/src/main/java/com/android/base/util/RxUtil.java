package com.android.base.util;

import rx.Subscription;
import rx.subscriptions.CompositeSubscription;

/**
 * Created by Administrator on 2016/5/3.
 */
public class RxUtil {

    public static void unsubscribeIfNotNull(Subscription subscription) {
        if (subscription != null) {
            subscription.unsubscribe();
        }
    }

    public static CompositeSubscription getNewCompositeSubIfUnsubscribed(CompositeSubscription subscription) {
        if (subscription == null || subscription.isUnsubscribed()) {
            return new CompositeSubscription();
        }
        return subscription;
    }



}
