package sg.edu.nus.iss.phoenix.user.android.ui;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.TextView;

import java.util.ArrayList;

import sg.edu.nus.iss.phoenix.R;
import sg.edu.nus.iss.phoenix.radioprogram.entity.RadioProgram;
import sg.edu.nus.iss.phoenix.user.android.entity.User;

/**
 * Created by kmb on 16/9/17.
 */

public class UserAdapter extends ArrayAdapter<User> {
    public UserAdapter(@NonNull Context context, ArrayList<User> users){
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.user_list_item, parent, false);
        }
        //    Word currentWord = getItem(position);
        User user = getItem(position);

        TextView lblUserName = (TextView)listItemView.findViewById(R.id.lblUserName);
        lblUserName.setText(user.getName());

        return listItemView;
    }
}
