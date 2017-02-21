package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;


import java.util.Date;

class Grades
{
    int GID;
    int GValue;
    String SbName;
    Date GDate;
    int GState;

    Grades(int GID, int GValue, String SbName, Date GDate,int GState)
    {
        this.GID = GID;
        this.GValue = GValue;
        this.SbName = SbName;
        this.GDate = GDate;
        this.GState = GState;
    }
}
