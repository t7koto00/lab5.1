package fi.oamk.students.t7koto00.lab5;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class PartArrayAdapter extends ArrayAdapter<WorkoutPartBase> {

    static final int VIEW_TYPE_PAUSE = 0;
    static final int VIEW_TYPE_WORKOUT = 1;
    static final int VIEW_TYPE_COUNT = 2;

    public PartArrayAdapter(Context context, ArrayList<WorkoutPartBase> parts) {
        super(context,0, parts);
    }

    @Override
    public int getViewTypeCount() {
        return VIEW_TYPE_COUNT;
    }

    @Override
    public int getItemViewType(int position) {
        WorkoutPartBase WorkoutPartBase = getItem(position);

        if (WorkoutPartBase instanceof WorkoutPart) {
            return VIEW_TYPE_WORKOUT;
        }
        else {
            return VIEW_TYPE_PAUSE;
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        WorkoutPartBase WorkoutPartBase = getItem(position);

        if (convertView == null) {
            int layoutId = 0;
            if (getItemViewType(position) == VIEW_TYPE_WORKOUT) {
                layoutId = R.layout.list_row_workout;
            }
            else {
                layoutId = R.layout.list_row_pause;
            }
            convertView = LayoutInflater.from(getContext()).inflate(layoutId, parent, false);
        }

        TextView partText = convertView.findViewById(R.id.part_name);
        partText.setText(WorkoutPartBase.getName() + " " + WorkoutPartBase.getLength());

        return convertView;
    }
}
