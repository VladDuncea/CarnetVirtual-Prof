package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import java.util.ArrayList;

class Classes
{
    Integer CID;
    Integer CValue;
    String CName;
    Boolean CMaster;
    ArrayList<String> subjects;
    ArrayList<Student> students;
    ArrayList<ChatMessage> messages;

    Classes(Integer CID, Integer CValue, String CName, Boolean CMaster,ArrayList<String> subjects, ArrayList<Student> students, ArrayList<ChatMessage> messages)
    {
        this.CID = CID;
        this.CValue = CValue;
        this.CName = CName;
        this.CMaster = CMaster;
        this.subjects = subjects;
        this.students = students;
        this.messages = messages;
    }
}
