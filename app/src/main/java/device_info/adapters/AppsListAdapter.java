package device_info.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidgeeks.deviceinfo.R;
import device_info.models.DeviceInfoModel;

import java.util.ArrayList;

public class AppsListAdapter extends RecyclerView.Adapter<AppsListAdapter.PopularHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<DeviceInfoModel> appsList;


    public AppsListAdapter(Context context,  ArrayList<DeviceInfoModel> arrayList) {
        this.mContext = context;
        this.appsList = arrayList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public PopularHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PopularHolder(inflater.inflate(R.layout.row_infomation, parent, false));
    }

    @Override
    public void onBindViewHolder(PopularHolder holder, int position) {

        DeviceInfoModel model = appsList.get(position);
        holder.tvAppname.setText( model.getAppLable());
        holder.tvAppPackageName.setText( model.getPackageName());
        holder.ivAppLogo.setImageDrawable(model.getAppLogo());

    }

    @Override
    public int getItemCount() {
        return appsList.size();
    }


    class PopularHolder extends RecyclerView.ViewHolder {

        public TextView tvAppname,tvAppPackageName;
        public ImageView ivAppLogo;


        public PopularHolder(View itemView) {
            super(itemView);

            ivAppLogo = itemView.findViewById(R.id.iv_app_icon);
            tvAppname = itemView.findViewById(R.id.tv_app_name);
            tvAppPackageName = itemView.findViewById(R.id.tv_app_package_name);


        }

    }

}
