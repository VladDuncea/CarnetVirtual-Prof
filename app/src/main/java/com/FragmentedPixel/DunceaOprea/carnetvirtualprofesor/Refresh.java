package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AlertDialog;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;


public class Refresh {
    public static void LogIn(final Context context, final String Email, final String Password) {

        Response.Listener<String> loginListener = new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    if(!jsonResponse.getBoolean("success"))
                    {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setMessage("Maintenance").setNegativeButton("Inapoi",null).create().show();
                    }
                    boolean success = jsonResponse.getBoolean("success");
                    boolean is_email_right = jsonResponse.getBoolean("is_email_right");
                    boolean is_password_right = jsonResponse.getBoolean("is_password_right");
                    if(success){

                        if (!is_email_right)
                        {
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setMessage("Email-ul e gresit.").setNegativeButton("Inapoi",null).create().show();
                        }
                        else if (!is_password_right)
                        {
                            AlertDialog.Builder alert = new AlertDialog.Builder(context);
                            alert.setMessage("Parola e necorespunzatoare.").setNegativeButton("Inapoi",null).create().show();
                        }
                        else
                        {
                            Integer NRClasses = jsonResponse.getInt("NRClasses");
                            String TID = jsonResponse.getString("TID");
                            String TName = jsonResponse.getString("TName");
                            String TFirstName = jsonResponse.getString("TFirstName");
                            Boolean TIsMaster = jsonResponse.getBoolean("TIsMaster");

                            Serialization.saveSerializable(context);

                            ArrayList<Classes> teacherClasses = new ArrayList<>();
                            for(int i=0;i<NRClasses;i++)
                            {
                                JSONObject Classes = jsonResponse.getJSONObject("Classes"+i);

                                Integer NRStudents = Classes.getInt("NRStudents");
                                Integer NRSubjects = Classes.getInt("NRSubjects");
                                //Integer NRChats = Classes.getInt("Chat_nr");

                                String CName = Classes.getString("CName");
                                Integer CID = Classes.getInt("CID");
                                Integer CValue = Classes.getInt("CValue");
                                Boolean CMaster = Classes.getBoolean("CMaster");

                                ArrayList<Student> teacherStudent = new ArrayList<>();
                                ArrayList<String> classesSubject = new ArrayList<>();
                                ArrayList<ChatMessage> messages = new ArrayList<>();

                                for(int j=0;j<NRSubjects;j++)
                                {
                                    JSONObject SB = Classes.getJSONObject("SB"+j);
                                    classesSubject.add(j,SB.getString("SBName"));
                                }

                                for(int j=0;j<NRStudents;j++)
                                {
                                    JSONObject Student = Classes.getJSONObject("Student"+j);
                                    Integer STID = Student.getInt("STID");
                                    String  STName = Student.getString("STName");
                                    String  STFirstName = Student.getString("STFirstName");
                                    teacherStudent.add(new Student(STName,STFirstName,STID));
                                }
                                for(int j=0;j<0;j++)
                                {
                                    JSONObject chat = jsonResponse.getJSONObject("Chat"+j);

                                    DateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.ENGLISH);
                                    String CHDate = chat.getString("CHDate");
                                    String CHEDate = chat.getString("CHEDate");
                                    Date chdate = format.parse(CHDate);
                                    Date chedate = format.parse(CHEDate);
                                    Integer CHType = chat.getInt("CHType");
                                    String CHMessage = chat.getString("CHMessage");
                                    messages.add(new ChatMessage(chdate,chedate,CHMessage,TName,CHType));
                                }




                                teacherClasses.add(new Classes(CID,CValue,CName,CMaster,classesSubject,teacherStudent,messages ));
                            }
                            new Teacher(TID,TName,TFirstName,TIsMaster,teacherClasses);
                            context.startActivity(new Intent(context, MainActivity.class));

                        }
                    }
                    else{
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setMessage("Ups.. S-a intamplat ceva neprevazut").setNegativeButton("Inapoi",null).create().show();
                    }
                } catch (JSONException | ParseException e) {
                    e.printStackTrace();
                }

            }
        };
        _Login_Request login_Request = new _Login_Request(Email,Password,loginListener);
        RequestQueue login_Queue = Volley.newRequestQueue(context);
        login_Queue.add(login_Request);


    }
}
