package device_info.models;

import android.graphics.drawable.Drawable;

public class MenuModel {

    private String name;
    private Drawable img;

    public MenuModel(String name, Drawable img) {
        this.name = name;
        this.img = img;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Drawable getImg() {
        return img;
    }

    public void setImg(Drawable img) {
        this.img = img;
    }



}
