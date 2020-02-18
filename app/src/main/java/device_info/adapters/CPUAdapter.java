package device_info.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.droidgeeks.deviceinfo.R;
import device_info.models.FeaturesHWModel;

import java.util.ArrayList;

public class CPUAdapter extends RecyclerView.Adapter<CPUAdapter.PopularHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<FeaturesHWModel> cpuList;


    public CPUAdapter(Context context, ArrayList<FeaturesHWModel> arrayList) {
        this.mContext = context;
        this.cpuList = arrayList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public PopularHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PopularHolder(inflater.inflate(R.layout.row_cpu_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PopularHolder holder, int position) {

        FeaturesHWModel model = cpuList.get(position);
        holder.tvFeatureName.setText("" + model.getFeatureLable());
        holder.tvFeatureValue.setText("" + model.getFeatureValue());

        if (position%2==0){
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorDriver));
        }else {
            holder.itemView.setBackgroundColor(mContext.getResources().getColor(R.color.colorWhite));
        }



    }

    @Override
    public int getItemCount() {
        return cpuList.size();
    }


    class PopularHolder extends RecyclerView.ViewHolder {

        public TextView tvFeatureName, tvFeatureValue;



        public PopularHolder(View itemView) {
            super(itemView);
            tvFeatureName = itemView.findViewById(R.id.tv_cpu_feature_name);
            tvFeatureValue = itemView.findViewById(R.id.tv_cpu_feature_value);


        }

    }

}
