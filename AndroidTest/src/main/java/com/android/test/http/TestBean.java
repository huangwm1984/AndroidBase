package com.android.test.http;

import java.util.List;

/**
 * Created by Administrator on 2015/10/10 0010.
 */
public class TestBean {

    /**
     * status : 0
     * desc :
     * advs : [{"cls":"","adv_id":8658,"app_link":"","official_title":"","pkg":"","scheme_url":"","app_name":"","v6_ios_img_url":"","content":"","imgUrl":"","title":"比价旅游banner","scheme_name":"","url_iOS":"","official_link":"","v6_android_img_url":"platform2/upload/adv/img/v6AndroidImg/20150703144006-28230.png","item_type":""}]
     */

    private String status;
    private String desc;
    private List<AdvsEntity> advs;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public void setAdvs(List<AdvsEntity> advs) {
        this.advs = advs;
    }

    public String getStatus() {
        return status;
    }

    public String getDesc() {
        return desc;
    }

    public List<AdvsEntity> getAdvs() {
        return advs;
    }

    public static class AdvsEntity {
        /**
         * cls :
         * adv_id : 8658
         * app_link :
         * official_title :
         * pkg :
         * scheme_url :
         * app_name :
         * v6_ios_img_url :
         * content :
         * imgUrl :
         * title : 比价旅游banner
         * scheme_name :
         * url_iOS :
         * official_link :
         * v6_android_img_url : platform2/upload/adv/img/v6AndroidImg/20150703144006-28230.png
         * item_type :
         */

        private String cls;
        private int adv_id;
        private String app_link;
        private String official_title;
        private String pkg;
        private String scheme_url;
        private String app_name;
        private String v6_ios_img_url;
        private String content;
        private String imgUrl;
        private String title;
        private String scheme_name;
        private String url_iOS;
        private String official_link;
        private String v6_android_img_url;
        private String item_type;

        public void setCls(String cls) {
            this.cls = cls;
        }

        public void setAdv_id(int adv_id) {
            this.adv_id = adv_id;
        }

        public void setApp_link(String app_link) {
            this.app_link = app_link;
        }

        public void setOfficial_title(String official_title) {
            this.official_title = official_title;
        }

        public void setPkg(String pkg) {
            this.pkg = pkg;
        }

        public void setScheme_url(String scheme_url) {
            this.scheme_url = scheme_url;
        }

        public void setApp_name(String app_name) {
            this.app_name = app_name;
        }

        public void setV6_ios_img_url(String v6_ios_img_url) {
            this.v6_ios_img_url = v6_ios_img_url;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public void setImgUrl(String imgUrl) {
            this.imgUrl = imgUrl;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setScheme_name(String scheme_name) {
            this.scheme_name = scheme_name;
        }

        public void setUrl_iOS(String url_iOS) {
            this.url_iOS = url_iOS;
        }

        public void setOfficial_link(String official_link) {
            this.official_link = official_link;
        }

        public void setV6_android_img_url(String v6_android_img_url) {
            this.v6_android_img_url = v6_android_img_url;
        }

        public void setItem_type(String item_type) {
            this.item_type = item_type;
        }

        public String getCls() {
            return cls;
        }

        public int getAdv_id() {
            return adv_id;
        }

        public String getApp_link() {
            return app_link;
        }

        public String getOfficial_title() {
            return official_title;
        }

        public String getPkg() {
            return pkg;
        }

        public String getScheme_url() {
            return scheme_url;
        }

        public String getApp_name() {
            return app_name;
        }

        public String getV6_ios_img_url() {
            return v6_ios_img_url;
        }

        public String getContent() {
            return content;
        }

        public String getImgUrl() {
            return imgUrl;
        }

        public String getTitle() {
            return title;
        }

        public String getScheme_name() {
            return scheme_name;
        }

        public String getUrl_iOS() {
            return url_iOS;
        }

        public String getOfficial_link() {
            return official_link;
        }

        public String getV6_android_img_url() {
            return v6_android_img_url;
        }

        public String getItem_type() {
            return item_type;
        }
    }
}
