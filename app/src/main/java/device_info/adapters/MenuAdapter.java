package device_info.adapters;

import android.app.Activity;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.droidgeeks.deviceinfo.R;
import device_info.models.MenuModel;

import java.util.ArrayList;


/**
 * Created by TD on 2/7/2018.
 */

public class MenuAdapter extends RecyclerView.Adapter<MenuAdapter.MyViewHolder> {

    private ArrayList<MenuModel> menuList;
    private Activity mActivity;
    private OnItemClickListener mItemClickListener;

    public MenuAdapter(Activity mActivity, ArrayList<MenuModel> menuList) {
        this.mActivity = mActivity;
        this.menuList = menuList;
    }


    public interface OnItemClickListener {
        void onItemClick(View view, String value);
    }

    public void setOnItemClickListener(final OnItemClickListener mItemClickListener) {
        this.mItemClickListener = mItemClickListener;
    }


    @Override
    public int getItemCount() {
        return menuList.size();
    }


    @Override
    public MenuAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_menu, parent, false);

        return new MenuAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {
        final MenuModel model = menuList.get(position);
        holder.imgCat.setImageDrawable(model.getImg());
        holder.txtName.setText(model.getName());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mItemClickListener.onItemClick(v, model.getName());
            }
        });

    }


    public class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView txtName;
        private ImageView imgCat;

        public MyViewHolder(View view) {
            super(view);
            txtName = (TextView) view.findViewById(R.id.txtName);
            imgCat = (ImageView) view.findViewById(R.id.category_image);
        }
    }


}
