package com.example.administrator.icdsalesrep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.icdsalesrep.R;
import com.example.administrator.icdsalesrep.network.model.Document;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by nitin on 24/08/16.
 */
public class DocumentListAdapter extends ArrayAdapter<Document> {


    Context context;

    static class ViewHolder {
        @InjectView(R.id.document_name) TextView document_name;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


    public DocumentListAdapter(Context context, ArrayList<Document> items) {
        super(context, 0, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.documents_item_layout, parent, false);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Document item = getItem(position);
        if (item!= null) {
            viewHolder.document_name.setText(item.documentName);
        }

        return convertView;
    }

}
