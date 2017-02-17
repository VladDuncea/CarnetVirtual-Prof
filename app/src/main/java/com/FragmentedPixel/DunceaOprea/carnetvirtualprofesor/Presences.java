package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import java.util.Date;

/**
 * Created by oalex on 2017-02-14 .
 */

public class Presences
{
    public String PID;
    public Date PDate;
    public String PSBName;
    public boolean PValue;

    public Presences(String PID, Date PDate, String PSBName, boolean PValue)
    {
        this.PID = PID;
        this.PDate = PDate;
        this.PSBName = PSBName;
        this.PValue = PValue;
    }

}
