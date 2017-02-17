package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;

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
                GetData();
                RefreshLists();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void GetData()
    {
        gradesList = new ArrayList<>();
        presenecesList = new ArrayList<>();

        //TODO: PLS FILL LISTS WITH INFORMATION #REZIST
        //TODO: HAI PONTA INAPOI CA GLUMEAM
        //TODO: I want my grinzi back

        //TODO: O Vlad frumos, O Vlad frumos

        //TODO: O Vlad frumos, o Vlad frumos cum te tii tu tot verde
        //TODO: Il fac frumos, torn un spumos, lipsesc doar niste fete
        //TODO: O Vlad frumos, o Vlad frumos, ce bine e cu tine
        //TODO: Dar nu fi prost, nu nu fi prost, tu n-o sa vezi cand vine

        //TODO: Pls call me maybe 0757106250

        gradesList.add(new Grades("ID", 10, "Materie1", Calendar.getInstance().getTime()));
        gradesList.add(new Grades("ID", 10, "Materie2", Calendar.getInstance().getTime()));
        gradesList.add(new Grades("ID", 10, "Materie3", Calendar.getInstance().getTime()));
        gradesList.add(new Grades("ID", 10, "Materie4", Calendar.getInstance().getTime()));
        gradesList.add(new Grades("ID", 10, "Materie5", Calendar.getInstance().getTime()));
        gradesList.add(new Grades("ID", 10, "Materie6", Calendar.getInstance().getTime()));

        presenecesList.add(new Presences("ID", Calendar.getInstance().getTime(), "Materie2", true));
        presenecesList.add(new Presences("ID", Calendar.getInstance().getTime(), "Materie3", false));
        presenecesList.add(new Presences("ID", Calendar.getInstance().getTime(), "Materie1", true));
        presenecesList.add(new Presences("ID", Calendar.getInstance().getTime(), "Materie3", false));
        presenecesList.add(new Presences("ID", Calendar.getInstance().getTime(), "Materie1", true));


        gradesNames = new ArrayList<>();
        presencesNames = new ArrayList<>();


        for(Grades g: gradesList)
            gradesNames.add(g.GValue + "/" + g.SbName);

        for(Presences p: presenecesList)
            presencesNames.add(p.PValue + "/" + p.PSBName);


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


