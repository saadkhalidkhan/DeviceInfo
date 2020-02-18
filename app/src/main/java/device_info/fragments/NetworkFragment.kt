package device_info.fragments

import android.annotation.SuppressLint
import android.content.Context.WIFI_SERVICE
import android.net.wifi.WifiManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.TextView
import com.droidgeeks.deviceinfo.R
import device_info.utils.Methods


class NetworkFragment : BaseFragment() {


    var tvConnectionStatus: TextView? = null
    var tvDataType: TextView? = null
    var tvNetworkType: TextView? = null
    var tvIpAddress: TextView? = null
    var tvMACAddress: TextView? = null
    var tvSSID: TextView? = null
    var tvLinkSpeed: TextView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.NetworkTheme)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        val view = localInflater.inflate(R.layout.fragment_network, container, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity!!.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.colorPrimary)
            window.navigationBarColor = resources.getColor(R.color.colorPrimary)

        }

        tvConnectionStatus = view.findViewById(R.id.tv_connection_status)
        tvDataType = view.findViewById(R.id.tv_data_type)
        tvNetworkType = view.findViewById(R.id.tv_network_type)
        tvIpAddress = view.findViewById(R.id.tv_ip_address)
        tvMACAddress = view.findViewById(R.id.tv_mac_address)
        tvSSID = view.findViewById(R.id.tv_ssid)
        tvLinkSpeed = view.findViewById(R.id.tv_link_speed)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        getNetworkInfo()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isAdded) {
        }
    }


    @SuppressLint("SetTextI18n", "WifiManagerLeak")
    private fun getNetworkInfo(): Unit {

        if (Methods.isNetworkConnected(mActivity)) {
            tvConnectionStatus?.text = getString(R.string.connect)
            tvIpAddress?.text = Methods.getIPAddress(true)
        } else {
            tvConnectionStatus?.text = getString(R.string.disconnect)
            tvIpAddress?.text = getString(R.string.unavailable)
        }

        if (Methods.isWifiConnected(mActivity) == getString(R.string.wifi)) {
            val wifiManager = mActivity.getSystemService(WIFI_SERVICE) as WifiManager
            val wifiInfo = wifiManager.connectionInfo
            tvDataType?.text = getString(R.string.wifi)
            tvNetworkType?.text = getString(R.string.wifi)
            tvSSID?.text = wifiInfo.ssid
            tvMACAddress?.text = Methods.getMACAddress("wlan0")
            tvLinkSpeed?.text = wifiInfo.linkSpeed.toString() + getString(R.string.mbps)
        } else if (Methods.isWifiConnected(mActivity) == getString(R.string.network)) {
            tvDataType?.text = getString(R.string.network)
            tvNetworkType?.text = getString(R.string.network)
            tvSSID?.text = getString(R.string.unavailable)
            tvMACAddress?.text = Methods.getMACAddress("eth0")
            tvLinkSpeed?.text = getString(R.string.unavailable)
        } else {
            tvDataType?.text = getString(R.string.unavailable)
            tvNetworkType?.text = getString(R.string.unavailable)
        }
    }
}