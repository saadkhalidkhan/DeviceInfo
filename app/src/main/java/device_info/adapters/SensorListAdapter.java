package device_info.adapters;

import android.content.Context;
import android.hardware.Sensor;

import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.droidgeeks.deviceinfo.R;

import device_info.models.SensorDATA;

import java.util.ArrayList;

public class SensorListAdapter extends RecyclerView.Adapter<SensorListAdapter.PopularHolder> {

    private LayoutInflater inflater;
    private Context mContext;
    private ArrayList<SensorDATA> mySensorList;


    public SensorListAdapter(Context context, ArrayList<SensorDATA> arrayList) {
        this.mContext = context;
        this.mySensorList = arrayList;
        inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public PopularHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new PopularHolder(inflater.inflate(R.layout.row_sensors, parent, false));
    }

    @Override
    public void onBindViewHolder(PopularHolder holder, int position) {

        SensorDATA model = mySensorList.get(position);
        holder.tvSensorNameRow.setText(model.getSensorName());

        if (model.getSensorType() == Sensor.TYPE_ACCELEROMETER || model.getSensorType() == Sensor.TYPE_ACCELEROMETER_UNCALIBRATED ||
                model.getSensorType() == Sensor.TYPE_LINEAR_ACCELERATION) {
            holder.ivSensorImage.setImageResource(R.drawable.ic_if_speedometer_172557);
        } else if (model.getSensorType() == Sensor.TYPE_LIGHT) {
            holder.ivSensorImage.setImageResource(R.drawable.ic_if_vector_icons_81_1041639);
        } else if (model.getSensorType() == Sensor.TYPE_STEP_COUNTER || model.getSensorType() == Sensor.TYPE_STEP_DETECTOR) {
            holder.ivSensorImage.setImageResource(R.drawable.ic_if_running_172541);
        } else if (model.getSensorType() == Sensor.TYPE_ROTATION_VECTOR || model.getSensorType() == Sensor.TYPE_GEOMAGNETIC_ROTATION_VECTOR
                || model.getSensorType() == Sensor.TYPE_GAME_ROTATION_VECTOR) {
            holder.ivSensorImage.setImageResource(R.drawable.ic_if_screen_rotation_326583);
        } else if (model.getSensorType() == Sensor.TYPE_GRAVITY) {
            holder.ivSensorImage.setImageResource(R.drawable.ic_if_earth1_216620);
        } else if (model.getSensorType() == Sensor.TYPE_MAGNETIC_FIELD) {
            holder.ivSensorImage.setImageResource(R.drawable.ic_inclined_magnet);
        } else if (model.getSensorType() == Sensor.TYPE_MAGNETIC_FIELD_UNCALIBRATED) {
            holder.ivSensorImage.setImageResource(R.drawable.ic_magnet_with_bolt);
        } else if (model.getSensorType() == Sensor.TYPE_PROXIMITY) {
            holder.ivSensorImage.setImageResource(R.drawable.ic_if_ibeacon_proximity_1613770);
        } else if (model.getSensorType() == Sensor.TYPE_GYROSCOPE || model.getSensorType() == Sensor.TYPE_GYROSCOPE_UNCALIBRATED) {
            holder.ivSensorImage.setImageResource(R.drawable.ic_worldwide_communications);
        } else if (model.getSensorType() == Sensor.TYPE_PRESSURE) {
            holder.ivSensorImage.setImageResource(R.drawable.ic_if_pressure);
        } else if (model.getSensorType() == Sensor.TYPE_RELATIVE_HUMIDITY) {
            holder.ivSensorImage.setImageResource(R.drawable.ic_humidity);
        } else if (model.getSensorType() == Sensor.TYPE_AMBIENT_TEMPERATURE) {
            holder.ivSensorImage.setImageResource(R.drawable.ic_temperature);
        } else if (model.getSensorType() == Sensor.TYPE_HEART_BEAT || model.getSensorType() == Sensor.TYPE_HEART_RATE) {
            holder.ivSensorImage.setImageResource(R.drawable.ic_cardiogram);
        } else {
            holder.ivSensorImage.setImageResource(R.drawable.ic_speedometer);
        }

    }

    @Override
    public int getItemCount() {
        return mySensorList.size();
    }


    class PopularHolder extends RecyclerView.ViewHolder {

        public TextView tvSensorNameRow;
        public ImageView ivSensorImage;


        public PopularHolder(View itemView) {
            super(itemView);

            tvSensorNameRow = itemView.findViewById(R.id.tv_sensor_name_row);
            ivSensorImage = itemView.findViewById(R.id.iv_sensor_image);

        }

    }

}
