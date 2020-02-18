package device_info.fragments

import android.annotation.SuppressLint
import android.content.Context.SENSOR_SERVICE
import android.graphics.Color
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.droidgeeks.deviceinfo.R
import com.google.android.material.snackbar.Snackbar
import device_info.models.SensorDATA
import device_info.adapters.SensorListAdapter

class SensorCategoryFragment : BaseFragment() {

    var ivMenu: ImageView? = null
    var ivBack: ImageView? = null
    var tvTitle: TextView? = null
    var txtTotel: TextView? = null

    var coordinateLayout: androidx.coordinatorlayout.widget.CoordinatorLayout? = null
    var rvSensorsList: androidx.recyclerview.widget.RecyclerView? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.SensorTheme)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        val view = localInflater.inflate(R.layout.fragment_sensors_categories, container, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity!!.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(context!!, R.color.colorPrimary)
            window.navigationBarColor = ContextCompat.getColor(context!!, R.color.colorPrimary)

        }

        ivMenu = view.findViewById(R.id.iv_menu)
        ivBack = view.findViewById(R.id.iv_back)
        tvTitle = view.findViewById(R.id.tv_title)
        txtTotel = view.findViewById(R.id.txtTotel)
        rvSensorsList = view.findViewById(R.id.rv_sensors_list)
        coordinateLayout = view.findViewById(R.id.coordinatorLayout)

        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initToolbar()
        rvSensorsList?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(mActivity)
        rvSensorsList?.hasFixedSize()
        initSensorsList()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isAdded) {
            initToolbar()

        }
    }

    private fun initToolbar() {
        ivMenu?.visibility = View.VISIBLE
        ivBack?.visibility = View.GONE
        tvTitle?.text = getString(R.string.sensors)
        ivMenu?.setOnClickListener {

        }
    }

    @SuppressLint("SetTextI18n")
    private fun initSensorsList() {
        val lists = ArrayList<SensorDATA>()

        val sm = mActivity.getSystemService(SENSOR_SERVICE) as SensorManager
        val list = sm.getSensorList(Sensor.TYPE_ALL)

        if (list.size > 1) {
            txtTotel!!.setText("Total " + list.size.toString() + " " + getString(R.string.available_sensor))
        } else {
            txtTotel!!.setText("Total " + list.size.toString() + " " + getString(R.string.available_sensor))
        }
        for (s in list) {
            lists.add(SensorDATA(s.name, s.type))
        }

//        creating our adapter
        val adapter = SensorListAdapter(mActivity, lists)
//        now adding the adapter to RecyclerView
        rvSensorsList?.adapter = adapter
    }

    /**
     * Display the Snackbar with the [Snackbar.LENGTH_SHORT] duration.
     *
     * @param message the message text.
     */

    private fun snackBarCustom(view: View, message: String) {
        val mSnackBar = Snackbar.make(view, message, Snackbar.LENGTH_LONG)
        val view: View? = mSnackBar.view
        val mainTextView =
            mSnackBar.view.findViewById<View>(R.id.snackbar_text) as TextView
        mainTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        mainTextView.gravity = Gravity.CENTER_HORIZONTAL
        mainTextView.setTextColor(Color.WHITE)
        view?.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.purple))


        mSnackBar.show()
    }

}

