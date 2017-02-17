package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import java.util.ArrayList;

/**
 * Created by oalex on 2017-02-11 .
 */

public class Teacher
{
    public static Teacher teacher;
    public ArrayList<Classes> classes = new ArrayList<>();

    public Classes selectedClass;
    public String selectedSubject;

    public String TID;
    public String Name;
    public String FirstName;
    public Boolean IsMaster;

    public Teacher(String TID,String Name,String FirstName,Boolean IsMaster,ArrayList<Classes> classes)
    {
        this.classes = classes;
        this.TID = TID;
        this.Name = Name;
        this.FirstName = FirstName;
        this.IsMaster = IsMaster;

        this.selectedClass = classes.get(0);
        teacher = this;
    }


}
