package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
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

public class PresencesActivity extends AppCompatActivity {


    ArrayList<Student> students = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_presences);

        TextView classText = (TextView) findViewById(R.id.class_text);
        classText.setText(Teacher.teacher.selectedClass.CName);

        for(Student s : Teacher.teacher.selectedClass.students)
            students.add(s);

        PopulateList();
        ListView studentsList = (ListView) findViewById(R.id.students_list);
        studentsList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {

                //PopulateList();
                Presence(Teacher.teacher.selectedClass.students.get(position).stID);
            }
        });
    }

    private void PopulateList()
    {
        StudentsAdapter adapter = new StudentsAdapter(this, students);
        ListView studentsList = (ListView) findViewById(R.id.students_list);
        studentsList.setAdapter(adapter);
    }

    private void Presence(Integer STID)
    {
        Date date = Calendar.getInstance().getTime();
        SimpleDateFormat df = new SimpleDateFormat("YYYY/MM/dd HH:mm", Locale.getDefault());

        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if(!jsonResponse.getBoolean("success"))
                    {
                        AlertDialog.Builder alert = new AlertDialog.Builder(PresencesActivity.this);
                        alert.setMessage("Maintenance").setNegativeButton("Inapoi",null).create().show();
                    }
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){

                        Toast.makeText(PresencesActivity.this,"Absenta trimisa",Toast.LENGTH_LONG).show();

                    }
                    else{
                        AlertDialog.Builder alert = new AlertDialog.Builder(PresencesActivity.this);
                        alert.setMessage("Eroare").setNegativeButton("Inapoi",null).create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        };
        String CValue = Teacher.teacher.selectedClass.CValue.toString();
        String TID = Teacher.teacher.TID;
        String SBName = Teacher.teacher.selectedClass.subjects.get(0); //TODO Materie selectata

        _Presence_Upload presence_Request = new _Presence_Upload(STID.toString(),CValue,TID,SBName,df.format(date),responseListener);
        RequestQueue presence_Queue = Volley.newRequestQueue(PresencesActivity.this);
        presence_Queue.add(presence_Request);
    }
}


