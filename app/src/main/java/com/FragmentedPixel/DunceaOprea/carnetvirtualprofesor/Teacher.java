package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import java.util.ArrayList;

class Teacher
{
    static Teacher teacher;
    ArrayList<Classes> classes = new ArrayList<>();

    Classes selectedClass;
    String selectedSubject;

    String TID;
    String Name;
    String FirstName;
    Boolean IsMaster;

    Teacher(String TID, String Name, String FirstName, Boolean IsMaster, ArrayList<Classes> classes)
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
