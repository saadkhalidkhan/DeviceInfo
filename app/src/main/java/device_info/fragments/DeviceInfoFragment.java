package device_info.fragments;

import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.droidgeeks.deviceinfo.R;

import butterknife.BindView;
import butterknife.ButterKnife;
import device_info.utils.SystemInfo;

public class DeviceInfoFragment extends Fragment {


    @BindView(R.id.txt_dvice_info)
    TextView txtDviceInfo;
    @BindView(R.id.txt_model)
    TextView txtModel;

    @BindView(R.id.txt_manufacutrer)
    TextView txt_manufacutrer;
    @BindView(R.id.txtDevice)
    TextView txtDevice;

    @BindView(R.id.txt_hardware)
    TextView txtHardware;

    @BindView(R.id.txt_android_id)
    TextView txtAndroidID;

    @BindView(R.id.txt_device_board)
    TextView txtDeviceBoard;


    View view;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR2)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_device, container, false);
        ButterKnife.bind(this, view);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getActivity().getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(getResources().getColor(R.color.device_color));
            window.setNavigationBarColor(getResources().getColor(R.color.device_color));

        }


        initUI();

        return view;
    }

    private void initUI() {

        txtDviceInfo.setText(SystemInfo.getModel());
        txtModel.setText(SystemInfo.getModel());
        txt_manufacutrer.setText(SystemInfo.getManufacturer());
        txtHardware.setText(SystemInfo.getHardware());
        txtDevice.setText(SystemInfo.getManufacturer());
        txtDeviceBoard.setText(SystemInfo.getBoard());

        String android_id = Settings.Secure.getString(getContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        txtAndroidID.setText(android_id);


    }


}