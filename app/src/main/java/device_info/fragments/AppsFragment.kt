package device_info.fragments

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import androidx.core.content.ContextCompat
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import com.droidgeeks.deviceinfo.R
import device_info.models.DeviceInfoModel

import device_info.utils.KeyUtil

class AppsFragment : BaseFragment() {

    var ivMenu: ImageView? = null
    var ivBackArrow: ImageView? = null
    var tvTitle: TextView? = null
    var rvAppsList: androidx.recyclerview.widget.RecyclerView? = null
    var coordinateLayout: androidx.coordinatorlayout.widget.CoordinatorLayout? = null

    var mode: Int? = 0

    companion object {
        fun getInstance(mode: Int): AppsFragment {
            val appsFragment = AppsFragment()
            val bundle = Bundle()
            bundle.putInt(KeyUtil.KEY_MODE, mode)
            appsFragment.arguments = bundle
            return appsFragment
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.UserAppsTheme)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        val view = localInflater.inflate(R.layout.fragment_apps, container, false)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity!!.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(R.color.dark_parrot_green_blue)
            window.navigationBarColor = resources.getColor(R.color.dark_parrot_green_blue)

        }

        ivMenu = view.findViewById(R.id.iv_menu)
        ivBackArrow = view.findViewById(R.id.iv_back)
        tvTitle = view.findViewById(R.id.tv_title)
        rvAppsList = view.findViewById(R.id.rv_apps_list)
        coordinateLayout = view.findViewById(R.id.coordinatorLayout)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getBundleData()
        initToolbar()
        rvAppsList?.layoutManager = androidx.recyclerview.widget.LinearLayoutManager(mActivity)
        rvAppsList?.hasFixedSize()
        initAppsList()
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isAdded) {
            initToolbar()
        }
    }

    /**
     * Get data from bundle
     */
    private fun getBundleData() {
        val bundle = arguments
        if (bundle != null) {
            if (bundle.containsKey(KeyUtil.KEY_MODE)) {
                mode = bundle.getInt(KeyUtil.KEY_MODE)
            }
        }
    }

    private fun initToolbar() {
        ivMenu?.visibility = View.VISIBLE
        ivBackArrow?.visibility = View.GONE
        if (mode?.equals(KeyUtil.IS_USER_COME_FROM_USER_APPS)!!) {
            tvTitle?.text = "User Apps"
        } else {
            tvTitle?.text = "System and Apps"
        }

        ivMenu?.setOnClickListener {

        }
    }

    private fun initAppsList() {

        val lists = ArrayList<DeviceInfoModel>()
//        mActivity.appsList?.filterTo(lists) { it.flags == mode }

        //creating our adapter
//        val adapter = DeviceAdapter(mode, lists, mActivity)

        if (mode == KeyUtil.IS_USER_COME_FROM_USER_APPS) {
            snackBarCustom(coordinateLayout!!, lists.size.toString() + " User Apps")
        } else {
            snackBarCustom(coordinateLayout!!, lists.size.toString() + "System Apps ")

        }
        //now adding the adapter to RecyclerView
//        rvAppsList?.adapter = adapter
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
            mSnackBar.view.findViewById<View>(com.google.android.material.R.id.snackbar_text) as TextView
        mainTextView.textAlignment = View.TEXT_ALIGNMENT_CENTER
        mainTextView.gravity = Gravity.CENTER_HORIZONTAL
        mainTextView.setTextColor(Color.WHITE)
        view?.setBackgroundColor(ContextCompat.getColor(mActivity, R.color.app_snackbar_color))


        mSnackBar.show()
    }
}