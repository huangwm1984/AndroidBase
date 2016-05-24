package com.hwm.test.http.model.entity;

/**
 * Created by Administrator on 2016/5/20.
 */
public class StartInfo {

    public String text;

    public String img;

    public String getText() {
        return text;
    }

    public String getImg() {
        return img;
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public String toString() {
        return "StartInfoResponse{" +
                "text='" + text + '\'' +
                ", img='" + img + '\'' +
                "} " + super.toString();
    }

}
