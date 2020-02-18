package device_info.utils;

import android.annotation.SuppressLint;
import android.os.Build;
import androidx.annotation.NonNull;

import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.Date;

public class SystemInfo {

    @NonNull
    public static String getModel() {
        return Build.MODEL;
    }

    @NonNull
    public static String getProduct() {
        return Build.PRODUCT;
    }

    @NonNull
    public static String getBrand() {
        return Build.BRAND;
    }

    @NonNull
    public static String getType() {
        return Build.TYPE;
    }

    @NonNull
    public static String getManufacturer() {
        return Build.MANUFACTURER;
    }

    @NonNull
    public static String getBoard() {
        return Build.BOARD;
    }

    @NonNull
    public static String getHardware() {
        return Build.HARDWARE;
    }

    @NonNull
    public static String getReleaseVersion() {
        return Build.VERSION.RELEASE;
    }

    @NonNull
    public static String getArch() {
        return System.getProperty("os.arch");
    }

    @NonNull
    public static String getKernelVersion() {
        return System.getProperty("os.version");
    }

    @NonNull
    public static int getAPILevel() {
        return Build.VERSION.SDK_INT;
    }

    @NonNull
    public static String getUser() {
        return Build.USER;
    }

    @NonNull
    public static String getHost() {
        return Build.HOST;
    }

    @NonNull
    public static String getFingerprint() {
        return Build.FINGERPRINT;
    }

    @NonNull
    public static String getBootloader() {
        return Build.BOOTLOADER;
    }

    @NonNull
    public static String getDate() {
        Date date = new Date(Build.TIME);
        @SuppressLint("SimpleDateFormat") SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yy");
        return dateFormat.format(date);
    }

    @NonNull
    public static String getCodeName() {
        Field[] fields = Build.VERSION_CODES.class.getFields();
        return fields[Build.VERSION.SDK_INT + 1].getName();
    }

}
