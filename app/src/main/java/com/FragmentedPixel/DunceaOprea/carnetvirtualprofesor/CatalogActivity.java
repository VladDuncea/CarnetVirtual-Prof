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

    private enum Pages {Grades, Presences, Messages}
    private Pages selectedPage=Pages.Grades;

    ListView lv1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);
        lv1 = (ListView) findViewById(R.id.lv1);

        lv1.setOnTouchListener(new OnSwipeTouchListener(CatalogActivity.this)
        {
            public void onSwipeRight()
            {
                if(selectedPage == Pages.Grades) {
                    RefreshLists(Pages.Presences);
                }
                else if(selectedPage == Pages.Presences)
                    RefreshLists(Pages.Messages);

                else if(selectedPage == Pages.Messages)
                    RefreshLists(Pages.Grades);
            }

            public void onSwipeLeft()
            {
                if(selectedPage == Pages.Grades)
                    RefreshLists(Pages.Messages);

                else if(selectedPage == Pages.Presences)
                    RefreshLists(Pages.Grades);

                else if(selectedPage == Pages.Messages)
                    RefreshLists(Pages.Presences);
            }
        }
        );

        SetStudentsSpinner();
    }


    private void ToGrades()
    {
        selectedPage=Pages.Grades;

        GradesAdapter adapter = new GradesAdapter(this, gradesList);
        lv1.setAdapter(adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Remove(gradesList.get(position));
            }
        });
    }

    private void ToPresences()
    {
        selectedPage=Pages.Presences;

        PresencesAdapter adapter = new PresencesAdapter(this, presenecesList);
        lv1.setAdapter(adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Remove(presenecesList.get(position));
            }
        });
    }

    private void ToMessages()
    {
        selectedPage=Pages.Messages;

        ChatAdapter adapter = new ChatAdapter(this, Teacher.teacher.selectedClass.messages);
        lv1.setAdapter(adapter);

        lv1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                //TODO: Duncea pls. O duncea frumos, O duncea frumos. Fuck me, daddy. FIXME: 2/21/2017 
               // Remove(Teacher.teacher.selectedClass.messages.get(position));
            }
        });
    }

    private void SetStudentsSpinner() {
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
                            Integer GState = grade.getInt("GState");
                            String SBName = Teacher.teacher.selectedSubject;
                            if(GState==1)
                                SBName+="(X)In Asteptare";

                            gradesList.add(new Grades(GID, GValue,SBName, date, GState));
                        }
                        RefreshLists(selectedPage);

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
    private void RefreshLists(Pages p){
        selectedPage = p;

        if(p ==  Pages.Grades)
            ToGrades();
        else if(p == Pages.Presences)
            ToPresences();
        else if(p == Pages.Messages)
            ToMessages();
    }

    private void Remove(Presences p) {
            if (!p.PValue)
                SendRemove(p.PID,"Presence",p,null);
    }

    private void Remove(Grades g) {
        if(g.GState==0)
            SendRemove(g.GID,"Grade",null,g);
        else if(g.GState==1)
            Toast.makeText(this, "In asteptare", Toast.LENGTH_SHORT).show();
    }

    private void SendRemove (Integer ID,String Type,final Presences p, final Grades g){
        Response.Listener<String> responseListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if (!jsonResponse.getBoolean("success")) {
                        AlertDialog.Builder alert = new AlertDialog.Builder(CatalogActivity.this);
                        alert.setMessage("Maintenance").setNegativeButton("Inapoi", null).create().show();
                    }
                    boolean success = jsonResponse.getBoolean("success");
                    if (success)
                    {
                        String Type = jsonResponse.getString("Type");
                        if(Type.equals("Grade"))
                        {
                            RefreshLists(Pages.Grades);
                            if(g.GState==0)
                            {
                                g.GState = 1;
                                g.SbName = g.SbName + "(X)In Asteptare";
                            }
                            Toast.makeText(CatalogActivity.this, "Nota trimisa", Toast.LENGTH_SHORT).show();
                        }
                        else
                        {
                            RefreshLists(Pages.Presences);
                            p.PValue = true;
                            Toast.makeText(CatalogActivity.this, "Absenta motivata", Toast.LENGTH_SHORT).show();
                        }

                    } else {
                        AlertDialog.Builder alert = new AlertDialog.Builder(CatalogActivity.this);
                        alert.setMessage("Eroare").setNegativeButton("Inapoi", null).create().show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        };

        _Catalog_Upload catalog_Request = new _Catalog_Upload(ID.toString(),Type, responseListener);
        RequestQueue catalog_Queue = Volley.newRequestQueue(CatalogActivity.this);
        catalog_Queue.add(catalog_Request);
    }

}


