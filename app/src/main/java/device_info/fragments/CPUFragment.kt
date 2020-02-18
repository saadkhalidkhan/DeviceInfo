package device_info.fragments

import android.annotation.SuppressLint
import android.annotation.TargetApi
import android.app.ActivityManager
import android.content.ContentValues.TAG
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.os.Handler
import androidx.annotation.RequiresApi
import android.util.Log
import android.view.*
import android.widget.TextView
import androidx.core.content.ContextCompat
import com.droidgeeks.deviceinfo.R
import com.github.lzyzsd.circleprogress.ArcProgress
import device_info.models.FeaturesHWModel
import device_info.adapters.CPUAdapter
import device_info.utils.Methods
import java.io.File
import java.io.IOException
import java.io.InputStream
import java.text.DecimalFormat
import java.util.*


class CPUFragment : BaseFragment() {


    var arcCpu: ArcProgress? = null
    var arcRAM: ArcProgress? = null
    var tvSystemAppsMemory: TextView? = null
    var tvAvailableRAM: TextView? = null
    var tvTotalRAMSpace: TextView? = null
    private var rvCpuFeatureList: androidx.recyclerview.widget.RecyclerView? = null

    var activityManager: ActivityManager? = null
    var memoryInfo: ActivityManager.MemoryInfo? = null
    var inputStream: InputStream? = null
    var byteArray = ByteArray(1024)
    var cpuData: String = ""

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val contextThemeWrapper = ContextThemeWrapper(activity, R.style.ProcessorTheme)
        val localInflater = inflater.cloneInContext(contextThemeWrapper)
        val view = localInflater.inflate(R.layout.fragment_cpu, container, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = activity!!.window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = ContextCompat.getColor(context!!, R.color.colorPrimary)
            window.navigationBarColor = ContextCompat.getColor(context!!, R.color.colorPrimary)

        }
        /*  val intent = activity!!.intent
          activity!!.finish()
          startActivity(intent)*/

        arcCpu = view.findViewById(R.id.arc_cpu)
        arcRAM = view.findViewById(R.id.arc_ram)
        tvSystemAppsMemory = view.findViewById(R.id.tv_system_apps_memory)
        tvAvailableRAM = view.findViewById(R.id.tv_available_ram)
        tvTotalRAMSpace = view.findViewById(R.id.tv_total_ram_space)
        rvCpuFeatureList = view.findViewById(R.id.rv_cpu_feature_list)


        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rvCpuFeatureList?.layoutManager =
            androidx.recyclerview.widget.LinearLayoutManager(mActivity)
        rvCpuFeatureList?.hasFixedSize()

        getCpuInfoMap()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            getMemoryInfo()
        }

        // Init
        val handler = Handler()
        val runnable = object : Runnable {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            override fun run() {
                val totalRamValue = totalRamMemorySize()
                val freeRamValue = freeRamMemorySize()
                val usedRamValue = totalRamValue - freeRamValue
                arcRAM?.progress =
                    Methods.calculatePercentage(usedRamValue.toDouble(), totalRamValue.toDouble())
                handler.postDelayed(this, 500)
            }
        }
        handler.postDelayed(runnable, 500)
    }

    override fun onHiddenChanged(hidden: Boolean) {
        super.onHiddenChanged(hidden)
        if (!hidden && isAdded) {
        }
    }


    @SuppressLint("SetTextI18n")
    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun getMemoryInfo() {
        activityManager = mActivity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager?
        memoryInfo = ActivityManager.MemoryInfo()
        activityManager?.getMemoryInfo(memoryInfo)
        val freeMemory = memoryInfo?.availMem
        val totalMemory = memoryInfo?.totalMem
        val usedMemory = freeMemory?.let { totalMemory?.minus(it) }
        tvSystemAppsMemory?.text =
            getString(R.string.system_and_apps) + ":  ".plus(formatSize(usedMemory!!))
        tvAvailableRAM?.text =
            getString(R.string.available_ram) + ":  ".plus(formatSize(freeMemory))
        tvTotalRAMSpace?.text =
            getString(R.string.total_ram_space) + ":  ".plus(formatSize(totalMemory!!))
    }


    private fun freeRamMemorySize(): Long {
        val mi = ActivityManager.MemoryInfo()
        val activityManager =
            mActivity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(mi)

        return mi.availMem
    }

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN)
    private fun totalRamMemorySize(): Long {
        val mi = ActivityManager.MemoryInfo()
        val activityManager =
            mActivity.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
        activityManager.getMemoryInfo(mi)
        return mi.totalMem
    }

    private fun formatSize(size: Long): String {
        if (size <= 0)
            return "0"
        val units = arrayOf("B", "KB", "MB", "GB", "TB")
        val digitGroups = (Math.log10(size.toDouble()) / Math.log10(1024.0)).toInt()
        return DecimalFormat("#,##0.#").format(
            size / Math.pow(
                1024.0,
                digitGroups.toDouble()
            )
        ) + " " + units[digitGroups]
    }

    private fun getCpuInfoMap() {
        val lists = ArrayList<FeaturesHWModel>()
        try {
            val s = Scanner(File("/proc/cpuinfo"))
            while (s.hasNextLine()) {
                val vals = s.nextLine().split(": ")
                if (vals.size > 1)
                    lists.add(
                        FeaturesHWModel(
                            vals[0].trim({ it <= ' ' }),
                            vals[1].trim({ it <= ' ' })
                        )
                    )
            }
        } catch (e: Exception) {
            Log.e("getCpuInfoMap", Log.getStackTraceString(e))
        }

        val adapter = CPUAdapter(mActivity, lists)
//        now adding the adapter to RecyclerView
        rvCpuFeatureList?.adapter = adapter
    }

    fun getCpuInfo() {
        try {
            val proc = Runtime.getRuntime().exec("cat /proc/cpuinfo")
            val cpuDetails = proc.inputStream
            println("cpuDetails = " + cpuDetails)
        } catch (e: IOException) {
            Log.e(TAG, "------ getCpuInfo " + e.printStackTrace())
        }
    }

    private fun getCPUInformation() {

        /*  try {
              val proc = Runtime.getRuntime().exec("/sys/devices/system/cpu/cpu0/cpufreq/scaling_cur_freq")
              inputStream = proc?.inputStream

              while (inputStream?.read(byteArray) != -1) {
                  cpuData += String(byteArray)
                  println("cpuData###### ==" + cpuData)
              }
              inputStream!!.close()

          } catch (e: Exception) {
              println(e.printStackTrace())
          }*/
    }
}
