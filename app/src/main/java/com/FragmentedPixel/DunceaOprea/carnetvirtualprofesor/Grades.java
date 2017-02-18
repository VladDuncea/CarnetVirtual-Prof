package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;


import java.util.Date;

/**
 * Created by oalex on 2017-02-14 .
 */

public class Grades
{
    public int GID;
    public int GValue;
    public String SbName;
    public Date GDate;

    public Grades(int GID, int GValue, String SbName, Date GDate)
    {
        this.GID = GID;
        this.GValue = GValue;
        this.SbName = SbName;
        this.GDate = GDate;
    }
}
