package com.example.administrator.icdsalesrep.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.administrator.icdsalesrep.R;
import com.example.administrator.icdsalesrep.network.model.ICDService;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by nitin on 24/08/16.
 */
public class ICDServiceListAdapter extends ArrayAdapter<ICDService> {


    Context context;

    static class ViewHolder {
        @InjectView(R.id.email_q) TextView email_q;
        @InjectView(R.id.email_a) TextView email_a;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


    public ICDServiceListAdapter(Context context, ArrayList<ICDService> items) {
        super(context, 0, items);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.icd_services_item, parent, false);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        ICDService item = getItem(position);
        if (item!= null) {

            viewHolder.email_q.setText(item.serviceName);
            viewHolder.email_a.setText(item.serviceNotes);
        }

        return convertView;
    }

}
