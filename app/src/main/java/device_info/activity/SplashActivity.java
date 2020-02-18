package device_info.activity;

import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.droidgeeks.deviceinfo.R;
import device_info.models.DeviceInfoModel;

import device_info.utils.AppConst;

import java.util.List;


public class SplashActivity extends AppCompatActivity {


    private final Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        mHandler.postDelayed(mPendingLauncherRunnable, 2000);

    }

    private final Runnable mPendingLauncherRunnable = new Runnable() {
        @Override
        public void run() {

            getAppsList();

            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            SplashActivity.this.finish();

        }
    };


    public void getAppsList() {

        AppConst.systemAppsList.clear();
        AppConst.userAppsList.clear();
        int flags = PackageManager.GET_META_DATA | PackageManager.GET_SHARED_LIBRARY_FILES;
        PackageManager pm = getPackageManager();
        List<ApplicationInfo> applications = pm.getInstalledApplications(flags);

        for (ApplicationInfo appInfo : applications) {
            if ((appInfo.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                // System application
                Drawable icon = pm.getApplicationIcon(appInfo);
                AppConst.systemAppsList.add(new DeviceInfoModel(1, icon, pm.getApplicationLabel(appInfo).toString(), appInfo.packageName));

            } else {
                Drawable icon = pm.getApplicationIcon(appInfo);
                AppConst.userAppsList.add(new DeviceInfoModel(2, icon, pm.getApplicationLabel(appInfo).toString(), appInfo.packageName));

            }

        }


    }


}



