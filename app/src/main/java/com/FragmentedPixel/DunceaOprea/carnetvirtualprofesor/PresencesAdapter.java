package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

/**
 * Created by oalex on 2017-02-18 .
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by oalex on 2017-02-18 .
 */

public class PresencesAdapter extends ArrayAdapter<Presences>
{
    public PresencesAdapter(Context context, ArrayList<Presences> presencesList)
    {
        super(context, 0, presencesList);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Presences presences = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.presence_item, parent, false);

        CheckBox gradeBox = (CheckBox) convertView.findViewById(R.id.presence_checkBox);

        if(presences != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.getDefault());
            gradeBox.setText(presences.PSBName + " - " +sdf.format(presences.PDate));
            gradeBox.setChecked(presences.PValue);
        }
        return convertView;
    }
}

