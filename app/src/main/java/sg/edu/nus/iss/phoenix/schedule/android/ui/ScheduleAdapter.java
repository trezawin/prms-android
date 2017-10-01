package sg.edu.nus.iss.phoenix.schedule.android.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.schedule.android.entity.ProgramSlot;

/**
 * Created by treza on 16/9/17.
 */

public class ScheduleAdapter extends ArrayAdapter<ProgramSlot> {
    public ScheduleAdapter(@NonNull Context context, ArrayList<ProgramSlot> programSlots){
        super(context, 0, programSlots);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MMM-dd HH:mm");
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.schedule_list, parent, false);
        }

        ProgramSlot programSlot = getItem(position);

        TextView lblProgramSlot = (TextView)listItemView.findViewById(R.id.lblSchedule);
        lblProgramSlot.setText(programSlot.getProgramName());

        TextView lblProgramSlotDatetime = (TextView) listItemView.findViewById(R.id.lblScheduleDateTime);
        lblProgramSlotDatetime.setText(sdf.format(programSlot.getDateOfProgram()));

        return listItemView;
    }
}
