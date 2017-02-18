package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import android.content.Context;
import android.widget.Toast;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Created by oalex on 2017-02-15 .
 */

public class Serialization implements Serializable
{
    public static Serialization serialization;
    private static String fileName = "teacherdata.srl";

    public String email;
    public String password;

    public Serialization(String email, String password)
    {
        this.email = email;
        this.password = password;

        serialization = this;
    }

    public static void saveSerializable(Context context)
    {
        if(serialization != null) {
            try {
                FileOutputStream fileOutputStream = context.openFileOutput(fileName, Context.MODE_PRIVATE);
                ObjectOutputStream objectOutputStream = new ObjectOutputStream(fileOutputStream);
                objectOutputStream.writeObject(serialization);

                objectOutputStream.close();
                fileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else
        {
            context.deleteFile(fileName);
        }
    }

    public static void readSerializable(Context context)
    {

        try
        {

            FileInputStream fileInputStream = context.openFileInput(fileName);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            serialization = (Serialization) objectInputStream.readObject();
            if(serialization!=null)

            objectInputStream.close();
            fileInputStream.close();
        }
        catch (IOException | ClassNotFoundException e)
        {
            e.printStackTrace();
        }

    }
}
