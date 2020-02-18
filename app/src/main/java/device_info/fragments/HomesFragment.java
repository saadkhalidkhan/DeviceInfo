package device_info.fragments;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.BatteryManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.StatFs;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.droidgeeks.deviceinfo.R;
import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;

import java.io.File;
import java.text.DecimalFormat;

import butterknife.BindView;
import butterknife.ButterKnife;
import device_info.utils.AppConst;
import device_info.utils.Methods;
import kotlin.jvm.internal.Intrinsics;

import static android.content.Context.ACTIVITY_SERVICE;

public class HomesFragment extends Fragment {


    @BindView(R.id.arc_ram)
    ArcProgress arc_ram;

    private final Handler mHandler = new Handler();
    @BindView(R.id.tv_system_apps_memory)
    TextView tv_system_apps_memory;
    @BindView(R.id.tv_available_ram)
    TextView tv_available_ram;
    @BindView(R.id.tv_total_ram_space)
    TextView tv_total_ram_space;

    @BindView(R.id.txtUserPersent)
    TextView txtUserPersent;
    @BindView(R.id.txtStrogeSpace)
    TextView txtStrogeSpace;
    @BindView(R.id.progressBarInternalStroge)
    ProgressBar progressBarInternalStroge;


    @BindView(R.id.tv_battery_level)
    TextView tvBatteryLevel;
    @BindView(R.id.tv_battery_temperature)
    TextView tvBatteryTemperature;
    @BindView(R.id.progress_bar_battery)
    ProgressBar progressBarBattery;

    @BindView(R.id.img_battery_icon)
    ImageView imgBatteryIcon;

    @BindView(R.id.txt_battery_title)
    TextView txtBatteryTitle;

    @BindView(R.id.txt_syetem_apps)
    TextView txtSyetemApps;

    @BindView(R.id.txt_user_apps)
    TextView txtUserApps;


    ActivityManager activityManager;


    int health;
    int icon_small;
    int level;
    int plugged;
    boolean present;
    int scale;
    int status;
    String technology;
    int temperature;
    int voltage;
    int deviceStatus;

    View view;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.new_fragment_home, container, false);
        ButterKnife.bind(this, view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.colorPrimaryDark));
            window.setNavigationBarColor(getResources().getColor(R.color.colorPrimaryDark));

        }

        initUI();
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mHandler.postDelayed(mPendingLauncherRunnable, 3000);


        long totalRamValue = totalRamMemorySize();
        long freeRamValue = freeRamMemorySize();
        long usedRamValue = totalRamValue - freeRamValue;
        arc_ram.setProgress(Methods.calculatePercentage(usedRamValue, totalRamValue));
        mHandler.postDelayed(mPendingLauncherRunnable, 1000);

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private void initUI() {

        /*banner ad*/

        AdView mAdView = view.findViewById(R.id.adView);
        MobileAds.initialize(getActivity(), getResources().getString(R.string.banner_id));
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);


        IntentFilter filter = new IntentFilter(Intent.ACTION_BATTERY_CHANGED);
        getActivity().registerReceiver(mBatInfoReceiver, filter);
        IntentFilter filter2 = new IntentFilter(Intent.ACTION_BATTERY_LOW);
        getActivity().registerReceiver(mBatLow, filter2);

        getMemoryInfo();
        getInternalStroge();

    }

    //RAM UsedInformation
    private void getMemoryInfo() {
        long totalRamValue = totalRamMemorySize();
        long freeRamValue = freeRamMemorySize();
        long usedRamValue = totalRamValue - freeRamValue;
        tv_system_apps_memory.setText(getResources().getString(R.string.system_and_apps) + " : " + formatSize(usedRamValue));
        tv_available_ram.setText(getResources().getString(R.string.available_ram) + " : " + formatSize(freeRamValue));
        tv_total_ram_space.setText(getResources().getString(R.string.total_ram_space) + " : " + formatSize(totalRamValue));
        txtSyetemApps.setText("" + AppConst.systemAppsList.size());
        txtUserApps.setText("" + AppConst.userAppsList.size());

    }

    private long totalRamMemorySize() {
        activityManager = (ActivityManager) getActivity().getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.totalMem;
    }

    private long freeRamMemorySize() {
        activityManager = (ActivityManager) getActivity().getSystemService(ACTIVITY_SERVICE);
        ActivityManager.MemoryInfo memoryInfo = new ActivityManager.MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        return memoryInfo.availMem;
    }

    private final Runnable mPendingLauncherRunnable = new Runnable() {
        @Override
        public void run() {
            long totalRamValue = totalRamMemorySize();
            long freeRamValue = freeRamMemorySize();
            long usedRamValue = totalRamValue - freeRamValue;
            arc_ram.setProgress(Methods.calculatePercentage(usedRamValue, totalRamValue));
            mHandler.postDelayed(mPendingLauncherRunnable, 1000);
        }
    };

    /**
     * Internal Memory usage
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    public void getInternalStroge() {
        long totalInternalValue = getTotalInternalMemorySize();
        long freeInternalValue = getAvailableInternalMemorySize();
        long usedInternalValue = totalInternalValue - freeInternalValue;
        int value = Methods.calculatePercentage(usedInternalValue, totalInternalValue);
        progressBarInternalStroge.setProgress(value);
        txtUserPersent.setText("" + value + "%");
        txtStrogeSpace.setText(getResources().getString(R.string.total) + "  " + formatSize(totalInternalValue) + " ,  " + getResources().getString(R.string.free) + " " + formatSize(freeInternalValue));
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private long getTotalInternalMemorySize() {
        File path = Environment.getDataDirectory();
        Intrinsics.checkExpressionValueIsNotNull(path, "path");
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getBlockCountLong();
        return totalBlocks * blockSize;


    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    private long getAvailableInternalMemorySize() {
        File path = Environment.getDataDirectory();
        Intrinsics.checkExpressionValueIsNotNull(path, "path");
        StatFs stat = new StatFs(path.getPath());
        long blockSize = stat.getBlockSizeLong();
        long totalBlocks = stat.getAvailableBlocksLong();
        return totalBlocks * blockSize;


    }


    private final String formatSize(long size) {
        if (size <= 0L) {
            return "0";
        } else {
            String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
            int digitGroups = (int) (Math.log10((double) size) / Math.log10(1024.0D));
            return (new DecimalFormat("#,##0.#")).format((double) size / Math.pow(1024.0D, (double) digitGroups)) + " " + units[digitGroups];
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        mHandler.removeCallbacks(mPendingLauncherRunnable);
    }


    @SuppressLint("RestrictedApi")
    private void getBatteryInfo() {
        tvBatteryTemperature.setText("Temperature : ".concat(String.valueOf(temperature)).concat(" " + getActivity().getResources().getString(R.string.c_symbol)));
        tvBatteryLevel.setText("".concat(String.valueOf(level)).concat("%"));
        progressBarBattery.setProgress(level);

        if (deviceStatus == BatteryManager.BATTERY_STATUS_CHARGING) {
            imgBatteryIcon.setImageResource(R.drawable.ic_battery);
            txtBatteryTitle.setText("Battery (Charging)");
        }

        if (deviceStatus == BatteryManager.BATTERY_STATUS_DISCHARGING) {
            imgBatteryIcon.setImageResource(R.drawable.ic_battery_new);

            txtBatteryTitle.setText("Battery");
        }

        if (deviceStatus == BatteryManager.BATTERY_STATUS_FULL) {
            imgBatteryIcon.setImageResource(R.drawable.ic_battery_new);
            txtBatteryTitle.setText("Battery Full");

        }

        if (deviceStatus == BatteryManager.BATTERY_STATUS_UNKNOWN) {
            imgBatteryIcon.setImageResource(R.drawable.ic_battery_new);
            txtBatteryTitle.setText("Battery");
        }

        if (deviceStatus == BatteryManager.BATTERY_STATUS_NOT_CHARGING) {
            imgBatteryIcon.setImageResource(R.drawable.ic_battery_new);
            txtBatteryTitle.setText("Battery");
        }


    }


    private BroadcastReceiver mBatLow = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            fabBatteryCharging.setImageResource(R.drawable.ic_low_battery);
        }
    };


    private BroadcastReceiver mBatInfoReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context c, Intent intent) {

            deviceStatus = intent.getIntExtra(BatteryManager.EXTRA_STATUS, -1);

            level = intent.getIntExtra(BatteryManager.EXTRA_LEVEL, 0);
            health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, 0);
            icon_small = intent.getIntExtra(BatteryManager.EXTRA_ICON_SMALL, 0);

            plugged = intent.getIntExtra(BatteryManager.EXTRA_PLUGGED, 0);
            present = intent.getExtras().getBoolean(BatteryManager.EXTRA_PRESENT);

            scale = intent.getIntExtra(BatteryManager.EXTRA_SCALE, 0);
            status = intent.getIntExtra(BatteryManager.EXTRA_STATUS, 0);

            technology = intent.getExtras().getString(BatteryManager.EXTRA_TECHNOLOGY);
            temperature = (intent.getIntExtra(BatteryManager.EXTRA_TEMPERATURE, 0) / 10);

            voltage = intent.getIntExtra(BatteryManager.EXTRA_VOLTAGE, 0);

            try {
                getBatteryInfo();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
        }
    };


}