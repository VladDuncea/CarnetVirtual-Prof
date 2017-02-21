package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.ArrayList;

class StudentsAdapter extends ArrayAdapter<Student>
{
    StudentsAdapter(Context context, ArrayList<Student> studentArrayList)
    {
        super(context, 0, studentArrayList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        Student student = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.presence_item, parent, false);

        final CheckBox checkBox = (CheckBox) convertView.findViewById(R.id.presence_checkBox);
        checkBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkBox.setChecked(true);
            }
        });

        if(student != null)
           checkBox.setText(student.stName + " " + student.stForname);

        return convertView;
    }

}