package com.FragmentedPixel.DunceaOprea.carnetvirtualprofesor;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Locale;


class ChatAdapter extends ArrayAdapter<ChatMessage>
{
    ChatAdapter(Context context, ArrayList<ChatMessage> users) {
        super(context, 0, users);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, @NonNull ViewGroup parent) {

        ChatMessage message = getItem(position);

        if (convertView == null)
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.chat_message_line, parent, false);

        TextView author = (TextView) convertView.findViewById(R.id.author_textView);
        TextView type = (TextView) convertView.findViewById(R.id.message_type_textView);
        TextView date = (TextView) convertView.findViewById(R.id.data_textView);
        TextView messageText = (TextView) convertView.findViewById(R.id.mesaj_textView);


        if(message != null) {
            author.setText(message.autor);

            type.setText(GetType(message.type));

            if(message.type == 1)
                type.setTextColor(Color.BLACK);
            else if(message.type == 2)
                type.setTextColor(Color.BLUE);
            else
                type.setTextColor(Color.RED);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.getDefault());
            date.setText(sdf.format(message.date));

            messageText.setText(message.message);
        }

        return convertView;
    }

    private String GetType (int type)
    {
        if(type == 1)
            return "✉ Mesaj";
        else if(type == 2)
            return  "☞ Test";
        else
            return  "⌲ Teza";
    }
}

