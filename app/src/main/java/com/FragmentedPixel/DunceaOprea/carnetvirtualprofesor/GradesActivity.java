package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class GradesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_grades);

        SetUp();
        SetStudentsSpinner();

        Button submiteButton = (Button) findViewById(R.id.grade_submit_button);
        submiteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Submit();
            }
        });
    }

    private void SetUp()
    {
        TextView header = (TextView) findViewById(R.id.classHeader_textView);
        header.setText(Teacher.teacher.selectedClass.CName);

        TextView date = (TextView) findViewById(R.id.date_textView);

        SimpleDateFormat df = new SimpleDateFormat("dd/MM/YY", Locale.getDefault());
        date.setText(df.format(Calendar.getInstance().getTime()));
    }

    private void SetStudentsSpinner()
    {
        ArrayList<String> students = new ArrayList<>();

        for(Student s: Teacher.teacher.selectedClass.students)
            students.add(s.stName + " " + s.stForname);

        Spinner studentsSpinner = (Spinner) findViewById(R.id.students_Spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, students);
        studentsSpinner.setAdapter(adapter);
    }

    private void Submit()
    {
        Spinner studentsSpinner = (Spinner) findViewById(R.id.students_Spinner);
        int index = studentsSpinner.getSelectedItemPosition();

        /* Informatii utile*/    //TODO Note sub 11 si peste 0
        String result = Teacher.teacher.selectedClass.students.get(index).stName + " " + Teacher.teacher.selectedClass.students.get(index).stForname;
        Integer STID = Teacher.teacher.selectedClass.students.get(index).stID;
        Boolean eTeza = ((CheckBox) findViewById(R.id.teza_checkBox)).isChecked();
        Date dateNow =  Calendar.getInstance().getTime();
        EditText text_nota = (EditText) findViewById(R.id.nota_editText);
        String nota = text_nota.getText().toString();
        Toast.makeText(this, nota, Toast.LENGTH_SHORT).show();
        text_nota.setText("");
        if(nota=="")
            Toast.makeText(this, "Introduceti o nota", Toast.LENGTH_SHORT).show();
        else
        {
            Response.Listener<String> responseListener = new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONObject jsonResponse = new JSONObject(response);
                        if (!jsonResponse.getBoolean("success")) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(GradesActivity.this);
                            alert.setMessage("Maintenance").setNegativeButton("Inapoi", null).create().show();
                        }
                        boolean success = jsonResponse.getBoolean("success");
                        if (success) {

                            Toast.makeText(GradesActivity.this, "Succes", Toast.LENGTH_LONG).show();

                        } else {
                            AlertDialog.Builder alert = new AlertDialog.Builder(GradesActivity.this);
                            alert.setMessage("Eroare").setNegativeButton("Inapoi", null).create().show();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            };
            String TID = Teacher.teacher.TID;
            String CValue = Teacher.teacher.selectedClass.CValue.toString();
            String SBName = Teacher.teacher.selectedSubject;
            SimpleDateFormat df = new SimpleDateFormat("YYYY/MM/dd HH:mm", Locale.getDefault());

            _Grade_Upload grade_Request = new _Grade_Upload(STID.toString(), nota, TID, SBName, df.format(dateNow), CValue, eTeza.toString(), responseListener);
            RequestQueue grade_Queue = Volley.newRequestQueue(GradesActivity.this);
            grade_Queue.add(grade_Request);
        }

    }
}
