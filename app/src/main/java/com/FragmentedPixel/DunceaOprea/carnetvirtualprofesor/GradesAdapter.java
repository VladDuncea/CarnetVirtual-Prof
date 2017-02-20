package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;



class GradesAdapter extends ArrayAdapter<Grades>
{
    GradesAdapter(Context context, ArrayList<Grades> gradesList)
    {
        super(context, 0, gradesList);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        Grades grades = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.grades_item, parent, false);

        TextView gradeDate = (TextView) convertView.findViewById(R.id.gdate_textView);
        TextView gradeValue = (TextView) convertView.findViewById(R.id.gvalue_textView);
        TextView gradeSubject = (TextView) convertView.findViewById(R.id.gsubject_textView);


        if(grades != null)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.getDefault());

            gradeDate.setText(sdf.format(grades.GDate));
            gradeValue.setText(""+grades.GValue);
            gradeSubject.setText(grades.SbName);
        }

        return convertView;
    }

}
