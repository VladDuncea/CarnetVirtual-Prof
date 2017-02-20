package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import android.graphics.pdf.PdfDocument;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.ButtonBarLayout;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
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
import java.util.Enumeration;
import java.util.Locale;

public class CatalogActivity extends AppCompatActivity
{

    private ArrayList<Grades> gradesList;
    private ArrayList<Presences> presenecesList;

    private enum Pages {Grades, Presences, Messages};

    ListView lv1 = (ListView) findViewById(R.id.lv1);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        /*
        lv1.setOnTouchListener(new OnSwipeTouchListener(CatalogActivity.this)
        {
            public void onSwipeRight() {
                if(selectedPage == Pages.Grades)
                    RefreshLists(Pages.Presences);

                else if(selectedPage == Pages.Presences)
                    RefreshLists(Pages.Messages);

                else
                    RefreshLists(Pages.Grades);
            }
            public void onSwipeLeft() {

            }
        }
        );
        */

        Button btGrades = (Button) findViewById(R.id.button_date);
        btGrades.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RefreshLists(Pages.Grades);
            }
        });

        Button btPreseneces = (Button) findViewById(R.id.button_date);
        btPreseneces.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RefreshLists(Pages.Presences);
            }
        });

        Button btMessages = (Button) findViewById(R.id.button_date);
        btMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RefreshLists(Pages.Messages);
            }
        });

        SetStudentsSpinner();
    }


    private void ToGrades()
    {
        GradesAdapter adapter = new GradesAdapter(this, gradesList);
        lv1.setAdapter(adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                RemoveGrade(gradesList.get(position));
            }
        });
    }

    private void ToPresences()
    {
        PresencesAdapter adapter = new PresencesAdapter(this, presenecesList);
        lv1.setAdapter(adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                RemovePresence(presenecesList.get(position));
            }
        });
    }

    //TODO: Add grades Adapter
    private void ToMessages()
    {
        GradesAdapter adapter = new GradesAdapter(this, gradesList);
        lv1.setAdapter(adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                RemoveGrade(gradesList.get(position));
            }
        });
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

                        presenecesList = new ArrayList<>();
                        gradesList = new ArrayList<>();

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

                        RefreshLists(Pages.Grades);

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

    private void RefreshLists(Pages p)
    {
        if(p ==  Pages.Grades)
            ToGrades();
        else if(p == Pages.Presences)
            ToPresences();
        else if(p == Pages.Messages)
            ToMessages();
    }

    private void RemoveGrade(Grades g)
    {
        Toast.makeText(CatalogActivity.this, "Nota cu ID-ul trebuie stearsa " + g.GID, Toast.LENGTH_SHORT).show();
        gradesList.remove(g);
        //TODO: PLS remove from database
        RefreshLists(Pages.Grades);
    }

    private void RemovePresence(Presences p) {
        if (!p.PValue) {
            Toast.makeText(CatalogActivity.this, "Absenta cu ID-ul trebuie movitata " + p.PID, Toast.LENGTH_SHORT).show();
            p.PValue = true;
            //TODO: moviteaza in baza de date pls
            RefreshLists(Pages.Presences);
        }
    }
}


