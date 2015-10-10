package com.android.base.http;

import com.android.base.common.assist.Check;

import java.util.Map;

/**
 * Created by Administrator on 2015/10/9 0009.
 */
public class OkHttpUtils {

    /**
     * 拼接url
     *
     * @param url
     * @param extraParams 请求参数
     * @return
     */
    public static String appendUrlParams(String url, Map<String, String> extraParams) {
        if (extraParams == null) {
            return url;
        }
        StringBuilder sb = new StringBuilder();
        sb.append(url);
        if (!url.contains("?")) {
            sb.append('?');
        } else if (!url.endsWith("?") && !url.endsWith("&")) {
            sb.append("&");
        }
        for (Map.Entry<String, String> entry : extraParams.entrySet()) {
            if (Check.isEmpty(entry.getKey()) || Check.isEmpty(entry.getValue())) {
                continue;
            }
            sb.append(entry.getKey() + "=" + entry.getValue() + "&");//如果参数有中文则调用此方法 URLEncoder.encode(entry.getValue(), "UTF-8")
        }
        String tempUrl = sb.toString();
        if (tempUrl.endsWith("&")) {
            tempUrl = tempUrl.substring(0, sb.length() - 1);
        }
        return tempUrl;
    }
}
