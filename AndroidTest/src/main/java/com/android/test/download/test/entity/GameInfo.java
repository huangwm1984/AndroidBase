package com.android.test.download.test.entity;

import com.alibaba.fastjson.annotation.JSONField;

import java.util.List;

/**
 * Created by Administrator on 2015/12/1 0001.
 */
public class GameInfo {


    /**
     * current : 1
     * pages : 4
     * total : 99
     * data : [{"id":600,"topic_cn":"微信","icon":"http://5.595818.com/ico/software/600.png","version":"6.3.7.65","versioncode":660,"address":"http://5.595818.com/2015/software/000/000/63e49407093799e2771a8a57f0d4e5fc.apk","package":"com.tencent.mm","type":"software","size":"31.34 MB","uptime":1448957008,"see":745532,"star":5,"shorturl":"http://2295.com/47izjb","systemcode":17}]
     */

    private int current;
    private int pages;
    private int total;
    /**
     * id : 600
     * topic_cn : 微信
     * icon : http://5.595818.com/ico/software/600.png
     * version : 6.3.7.65
     * versioncode : 660
     * address : http://5.595818.com/2015/software/000/000/63e49407093799e2771a8a57f0d4e5fc.apk
     * package : com.tencent.mm
     * type : software
     * size : 31.34 MB
     * uptime : 1448957008
     * see : 745532
     * star : 5
     * shorturl : http://2295.com/47izjb
     * systemcode : 17
     */

    private List<DataEntity> data;

    public void setCurrent(int current) {
        this.current = current;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public int getCurrent() {
        return current;
    }

    public int getPages() {
        return pages;
    }

    public int getTotal() {
        return total;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class DataEntity {
        private int id;
        private String topic_cn;
        private String icon;
        private String version;
        private int versioncode;
        private String address;
        @JSONField(name = "package")
        private String packageX;
        private String type;
        private String size;
        private int uptime;
        private int see;
        private int star;
        private String shorturl;
        private int systemcode;

        public void setId(int id) {
            this.id = id;
        }

        public void setTopic_cn(String topic_cn) {
            this.topic_cn = topic_cn;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public void setVersioncode(int versioncode) {
            this.versioncode = versioncode;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setPackageX(String packageX) {
            this.packageX = packageX;
        }

        public void setType(String type) {
            this.type = type;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public void setUptime(int uptime) {
            this.uptime = uptime;
        }

        public void setSee(int see) {
            this.see = see;
        }

        public void setStar(int star) {
            this.star = star;
        }

        public void setShorturl(String shorturl) {
            this.shorturl = shorturl;
        }

        public void setSystemcode(int systemcode) {
            this.systemcode = systemcode;
        }

        public int getId() {
            return id;
        }

        public String getTopic_cn() {
            return topic_cn;
        }

        public String getIcon() {
            return icon;
        }

        public String getVersion() {
            return version;
        }

        public int getVersioncode() {
            return versioncode;
        }

        public String getAddress() {
            return address;
        }

        public String getPackageX() {
            return packageX;
        }

        public String getType() {
            return type;
        }

        public String getSize() {
            return size;
        }

        public int getUptime() {
            return uptime;
        }

        public int getSee() {
            return see;
        }

        public int getStar() {
            return star;
        }

        public String getShorturl() {
            return shorturl;
        }

        public int getSystemcode() {
            return systemcode;
        }
    }
}
