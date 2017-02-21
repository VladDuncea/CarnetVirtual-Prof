package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;

class PresencesAdapter extends ArrayAdapter<Presences>
{
    PresencesAdapter(Context context, ArrayList<Presences> presencesList)
    {
        super(context, 0, presencesList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        Presences presences = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.presence_item, parent, false);

        CheckBox gradeBox = (CheckBox) convertView.findViewById(R.id.presence_checkBox);


        if(presences != null) {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.getDefault());
            gradeBox.setText(presences.PSBName + " - " +sdf.format(presences.PDate));
            gradeBox.setChecked(presences.PValue);
        }
        return convertView;//
    }
}

