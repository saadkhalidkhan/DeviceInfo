package device_info.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.droidgeeks.deviceinfo.R;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.material.bottomsheet.BottomSheetBehavior;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import device_info.adapters.MenuAdapter;
import device_info.fragments.AboutUsFragment;
import device_info.fragments.BatteryFragment;
import device_info.fragments.BlueToothFragment;
import device_info.fragments.CPUFragment;
import device_info.fragments.CameraFragment;
import device_info.fragments.DeviceInfoFragment;
import device_info.fragments.DisplayFragment;
import device_info.fragments.HomesFragment;
import device_info.fragments.NetworkFragment;
import device_info.fragments.OSFragment;
import device_info.fragments.PhoneFeaturesFragment;
import device_info.fragments.SensorCategoryFragment;
import device_info.fragments.SimFragment;
import device_info.fragments.StorageFragment;
import device_info.fragments.SystemAppsFragment;
import device_info.fragments.UserAppsFragment;
import device_info.utils.MenuList;
import device_info.utils.Methods;

public class HomeActivity extends AppCompatActivity {


    private static final String TAG = HomeActivity.class.getSimpleName();


    @BindView(R.id.bottom_sheet)
    LinearLayout layoutBottomSheet;

    @BindView(R.id.txtTile)
    TextView txtTile;

    @BindView(R.id.rlMenu)
    RelativeLayout rlMenu;
    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    BottomSheetBehavior sheetBehavior;

    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);


        initUI();

    }

    private void initUI() {


        MenuAdapter adapter = new MenuAdapter(HomeActivity.this, MenuList.getMenuList(this));
        recyclerView.setLayoutManager(new GridLayoutManager(this, 4, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new MenuAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, String value) {
                // TODO Auto-generated method stub

                selectedButton(value);
                toggleBottomSheet();

            }
        });


        sheetBehavior = BottomSheetBehavior.from(layoutBottomSheet);
        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         * */
        sheetBehavior.addBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            @Override
            public void onStateChanged(@NonNull View bottomSheet, int newState) {
                switch (newState) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        break;
                    case BottomSheetBehavior.STATE_EXPANDED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_COLLAPSED: {
                    }
                    break;
                    case BottomSheetBehavior.STATE_DRAGGING:
                        break;
                    case BottomSheetBehavior.STATE_SETTLING:
                        break;
                    case BottomSheetBehavior.STATE_HALF_EXPANDED:
                        break;
                }
            }

            @Override
            public void onSlide(@NonNull View bottomSheet, float slideOffset) {

            }
        });

        selectedButton(getResources().getString(R.string.device));

    }


    /**
     * manually opening / closing bottom sheet on button click
     */
    @OnClick(R.id.rlMenu)
    public void rlMenu() {
        toggleBottomSheet();
    }


    public void toggleBottomSheet() {
        if (sheetBehavior.getState() != BottomSheetBehavior.STATE_EXPANDED) {
            sheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        } else {
            sheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);

        }
    }

    public void selectedButton(String selectedValue) {
        txtTile.setText(selectedValue);
        if (getResources().getString(R.string.device).equals(selectedValue)) {
            replicasFragments(new HomesFragment());
        } else if (getResources().getString(R.string.mobile_info).equals(selectedValue)) {
            replicasFragments(new DeviceInfoFragment());
        } else if (getResources().getString(R.string.os).equals(selectedValue)) {
            replicasFragments(new OSFragment());
        } else if (getResources().getString(R.string.sensor).equals(selectedValue)) {
            replicasFragments(new SensorCategoryFragment());
        } else if (getResources().getString(R.string.processor_label).equals(selectedValue)) {
            replicasFragments(new CPUFragment());
        } else if (getResources().getString(R.string.battery).equals(selectedValue)) {
            replicasFragments(new BatteryFragment());
        } else if (getResources().getString(R.string.network).equals(selectedValue)) {
            replicasFragments(new NetworkFragment());
        } else if (getResources().getString(R.string.sim).equals(selectedValue)) {
            replicasFragments(new SimFragment());
        } else if (getResources().getString(R.string.camera).equals(selectedValue)) {
            replicasFragments(new CameraFragment());
        } else if (getResources().getString(R.string.storage).equals(selectedValue)) {
            replicasFragments(new StorageFragment());
        } else if (getResources().getString(R.string.bluetooth).equals(selectedValue)) {
            replicasFragments(new BlueToothFragment());
        } else if (getResources().getString(R.string.display).equals(selectedValue)) {
            replicasFragments(new DisplayFragment());
        } else if (getResources().getString(R.string.features).equals(selectedValue)) {
            replicasFragments(new PhoneFeaturesFragment());

        } else if (getResources().getString(R.string.user_apps).equals(selectedValue)) {
            replicasFragments(new UserAppsFragment());
        } else if (getResources().getString(R.string.system_apps).equals(selectedValue)) {
            replicasFragments(new SystemAppsFragment());
        } else if (getResources().getString(R.string.about_us).equals(selectedValue)) {
            replicasFragments(new AboutUsFragment());
        } else if (getResources().getString(R.string.share).equals(selectedValue)) {
            Methods.sharing("https://play.google.com/store/apps/details?id=com.master.deviceinfo");
        }


        /*mInterstitial Ad*/

        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.interstitial_id));
        mInterstitialAd.loadAd(new AdRequest.Builder().build());


        mInterstitialAd.setAdListener(new AdListener() {
            @Override
            public void onAdClosed() {
                mInterstitialAd.loadAd(new AdRequest.Builder().build());
            }
        });


    }


    public void replicasFragments(Fragment fragment) {


        FragmentTransaction transaction;
        transaction = ((FragmentActivity) this).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayout, fragment).commit();
    }


}
