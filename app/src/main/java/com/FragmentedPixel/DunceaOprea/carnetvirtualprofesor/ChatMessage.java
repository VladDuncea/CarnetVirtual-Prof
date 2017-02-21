package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import java.util.Date;


class ChatMessage
{
    Integer CHID;
    public Date date;
    private Date expirationDate;
    String message;
    String autor;
    int type;

    ChatMessage(int CHID,Date date, Date expirationDate, String message, String autor, int type)
    {
        this.CHID = CHID;
        this.date = date;
        this.expirationDate = expirationDate;
        this.message = message;
        this.autor = autor;
        this.type = type;
    }
}
