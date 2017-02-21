package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import java.util.Date;

/**
 * Created by elev on 2/21/2017.
 */

public class ChatMessage
{
    public Date date;
    private Date expirationDate;
    String message;
    String autor;
    int type;

    ChatMessage(Date date, Date expirationDate, String message, String autor, int type)
    {
        this.date = date;
        this.expirationDate = expirationDate;
        this.message = message;
        this.autor = autor;
        this.type = type;
    }
}
