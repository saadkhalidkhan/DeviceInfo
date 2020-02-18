package device_info.utils;

import android.content.Context;

import com.droidgeeks.deviceinfo.R;

import java.util.ArrayList;

import device_info.models.MenuModel;

public class MenuList {

    public static ArrayList<MenuModel> getMenuList(Context context) {
        ArrayList<MenuModel> menutList = new ArrayList<>();
        menutList.add(new MenuModel(context.getResources().getString(R.string.device), context.getResources().getDrawable(R.drawable.ic_home)));
        menutList.add(new MenuModel(context.getResources().getString(R.string.mobile_info), context.getResources().getDrawable(R.drawable.ic_tablet)));

        menutList.add(new MenuModel(context.getResources().getString(R.string.os), context.getResources().getDrawable(R.drawable.ic_android_logo)));
        menutList.add(new MenuModel(context.getResources().getString(R.string.sensor), context.getResources().getDrawable(R.drawable.ic_speedometer)));
        menutList.add(new MenuModel(context.getResources().getString(R.string.processor_label), context.getResources().getDrawable(R.drawable.ic_processor)));
        menutList.add(new MenuModel(context.getResources().getString(R.string.battery), context.getResources().getDrawable(R.drawable.ic_battery_new)));
        menutList.add(new MenuModel(context.getResources().getString(R.string.network), context.getResources().getDrawable(R.drawable.ic_wifi_signal)));
        menutList.add(new MenuModel(context.getResources().getString(R.string.camera), context.getResources().getDrawable(R.drawable.ic_lens)));
        menutList.add(new MenuModel(context.getResources().getString(R.string.storage), context.getResources().getDrawable(R.drawable.ic_database)));
        menutList.add(new MenuModel(context.getResources().getString(R.string.bluetooth), context.getResources().getDrawable(R.drawable.ic_bluetooth)));
        menutList.add(new MenuModel(context.getResources().getString(R.string.display), context.getResources().getDrawable(R.drawable.ic_screens)));
        menutList.add(new MenuModel(context.getResources().getString(R.string.features), context.getResources().getDrawable(R.drawable.ic_snowflake)));
        menutList.add(new MenuModel(context.getResources().getString(R.string.user_apps), context.getResources().getDrawable(R.drawable.ic_user)));
        menutList.add(new MenuModel(context.getResources().getString(R.string.system_apps), context.getResources().getDrawable(R.drawable.ic_solar_system)));
        menutList.add(new MenuModel(context.getResources().getString(R.string.about_us), context.getResources().getDrawable(R.drawable.ic_information)));
        menutList.add(new MenuModel(context.getResources().getString(R.string.share), context.getResources().getDrawable(R.drawable.ic_share)));
        return menutList;
    }


}
