package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import java.util.ArrayList;

/**
 * Created by elev on 2/13/2017.
 */

public class Classes
{
    public Integer CID;
    public Integer CValue;
    public String CName;
    public ArrayList<String> subjects;
    public ArrayList<Student> students;

    public String selsubject;

    public Classes (Integer CID,Integer CValue,String CName,ArrayList<String> subjects, ArrayList<Student> students)
    {
        this.CID = CID;
        this.CValue = CValue;
        this.CName = CName;
        this.subjects = subjects;
        this.students = students;
    }
}
