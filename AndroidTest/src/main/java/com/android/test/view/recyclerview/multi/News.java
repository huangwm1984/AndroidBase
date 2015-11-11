package com.android.test.view.recyclerview.multi;

/**
 * Created by Administrator on 2015/11/10 0010.
 */
public class News {

    public static final int ITEM_TYPE_TEXT = 0;
    public static final int ITEM_TYPE_IMAGE = 1;
    public static final int ITEM_TYPE_BUTTON = 2;

    private String text;
    private String image;
    private String button;
    private int itemType;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getButton() {
        return button;
    }

    public void setButton(String button) {
        this.button = button;
    }

    public int getItemType() {
        return itemType;
    }

    public void setItemType(int itemType) {
        this.itemType = itemType;
    }
}
