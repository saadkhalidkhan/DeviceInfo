package device_info.adapters;

import android.content.Context;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.droidgeeks.deviceinfo.R;
import device_info.models.SimInfoModel;

import java.util.ArrayList;

public class SimListAdapter extends RecyclerView.Adapter<SimListAdapter.PopularHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<SimInfoModel> simList;


    public SimListAdapter(Context context, ArrayList<SimInfoModel> arrayList) {
        this.mContext = context;
        this.simList = arrayList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public PopularHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PopularHolder(inflater.inflate(R.layout.row_sim_item, parent, false));
    }

    @Override
    public void onBindViewHolder(PopularHolder holder, int position) {

        SimInfoModel model = simList.get(position);

        holder.tvLabel.setText( model.getSimLable());
        holder.tvSimInformation.setText( model.getSimData());
    }

    @Override
    public int getItemCount() {
        return simList.size();
    }


    class PopularHolder extends RecyclerView.ViewHolder {

        public TextView tvLabel,tvSimInformation;





        public PopularHolder(View itemView) {
            super(itemView);

            tvLabel = itemView.findViewById(R.id.tvLabel);
            tvSimInformation = itemView.findViewById(R.id.tvSimInformation);


        }

    }

}
