package com.example.inclass09_group22;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class IndoxListViewAdapter extends ArrayAdapter<InboxData> {
    public IndoxListViewAdapter(@NonNull Context context, int resource, @NonNull List<InboxData> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final InboxData mail = getItem(position);
        ViewHolder viewHolder;

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.inbox_listview, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.tv_subject = convertView.findViewById(R.id.tv_subject);
            viewHolder.tv_date = convertView.findViewById(R.id.tv_date);
            viewHolder.btn_delete = convertView.findViewById(R.id.btn_delete);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }


        viewHolder.tv_date.setText(mail.created_at);
        viewHolder.tv_subject.setText(mail.subject);
        viewHolder.tv_subject.setFocusable(false);
        viewHolder.tv_date.setFocusable(false);
        viewHolder.btn_delete.setFocusable(false);
        viewHolder.btn_delete.setTag(mail);

        return convertView;
    }

    private static class ViewHolder {
        TextView tv_subject;
        TextView tv_date;
        ImageButton btn_delete;
    }
}
