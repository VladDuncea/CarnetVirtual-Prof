package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

public class CatalogActivity extends AppCompatActivity
{

    private ArrayList<Grades> gradesList;
    private ArrayList<Presences> presenecesList;

    private ArrayList<String> gradesNames;
    private ArrayList<String> presencesNames;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        SetStudentsSpinner();
    }

    private void SetStudentsSpinner()
    {
        ArrayList<String> students = new ArrayList<>();

        for (Student s: Teacher.teacher.selectedClass.students)
            students.add(s.stName + " " + s.stForname);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, students);
        Spinner studentsSpinner = (Spinner) findViewById(R.id.catalog_elevi_spinner);
        studentsSpinner.setAdapter(adapter);

        studentsSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id)
            {
                int STID =Teacher.teacher.selectedClass.students.get(position).stID;
                GetData(STID);
                RefreshLists();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void GetData(Integer STID)
    {
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if(!jsonResponse.getBoolean("success"))
                    {
                        AlertDialog.Builder alert = new AlertDialog.Builder(CatalogActivity.this);
                        alert.setMessage("Maintenance").setNegativeButton("Inapoi",null).create().show();
                    }
                    boolean success = jsonResponse.getBoolean("success");
                    if(success){

                        Integer Grade_nr = jsonResponse.getInt("Grade_nr");
                        Integer Presence_nr = jsonResponse.getInt("Presence_nr");

                        for(int i=0;i<Presence_nr;i++)
                        {
                            JSONObject presence = jsonResponse.getJSONObject("Presence"+i);
                            String PDate = presence.getString("PDate");
                            DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
                            Date date = format.parse(PDate);
                            Boolean PValue = presence.getBoolean("PValue");
                            Integer PID = presence.getInt("PID");
                            presenecesList.add(new Presences(PID, date, Teacher.teacher.selectedSubject, PValue));
                        }

                        for(int i=0;i<Grade_nr;i++)
                        {
                            JSONObject grade = jsonResponse.getJSONObject("Grade"+i);
                            String GDate = grade.getString("GDate");
                            DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
                            Date date = format.parse(GDate);
                            Integer GValue = grade.getInt("GValue");
                            Integer GID = grade.getInt("GID");
                            gradesList.add(new Grades(GID, GValue,Teacher.teacher.selectedSubject, date));
                        }

                    }
                    else{
                        AlertDialog.Builder alert = new AlertDialog.Builder(CatalogActivity.this);
                        alert.setMessage("Eroare").setNegativeButton("Inapoi",null).create().show();
                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }

            }
        };

        _Catalog_Update catalog_Request = new _Catalog_Update(STID.toString(),Teacher.teacher.selectedSubject,responseListener);
        RequestQueue catalog_Queue = Volley.newRequestQueue(CatalogActivity.this);
        catalog_Queue.add(catalog_Request);



    }

    private void RefreshLists()
    {
        ListView lv1 = (ListView) findViewById(R.id.lv1);
        ListView lv2 = (ListView) findViewById(R.id.lv2);


        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, gradesNames);
        ArrayAdapter<String> adapter2 = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, android.R.id.text1, presencesNames);

        lv1.setAdapter(adapter1);
        lv2.setAdapter(adapter2);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                gradesNames.remove(position);
                RemoveGrade(gradesList.get(position));
            }
        });

        lv2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                presencesNames.remove(position);
                RemovePresence(presenecesList.get(position));
            }
        });
    }

    private void RemoveGrade(Grades g)
    {
        Toast.makeText(CatalogActivity.this, "Remove grade with GID = " + g.GID, Toast.LENGTH_SHORT).show();
        gradesList.remove(g);
        RefreshLists();
    }

    private void RemovePresence(Presences p)
    {
        Toast.makeText(CatalogActivity.this, "Remove presence with PID = " + p.PID, Toast.LENGTH_SHORT).show();
        presenecesList.remove(p);
        RefreshLists();
    }
}


