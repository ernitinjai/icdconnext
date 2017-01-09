package com.example.administrator.icdsalesrep.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.icdsalesrep.R;
import com.example.administrator.icdsalesrep.network.model.OrderList;

import java.util.ArrayList;

/**
 * Created by nitin on 24/08/16.
 */
public class OrderListAdapter  extends ArrayAdapter<OrderList> {


    Context context;

    String orderStatus;

    private static class ViewHolder {
        private TextView file_no;
        private TextView DateOpened;
        private TextView LoanAmount;
        private TextView PropertyAddress;
        private TextView ContactCompanyName;
        private ImageView prelimicon;
        private View filetype;
    }

    public OrderListAdapter(Context context,  ArrayList<OrderList> items, String orderStatus) {

        super(context, 0, items);
        this.context = context;
        this.orderStatus = orderStatus;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.order_list, parent, false);

            viewHolder = new ViewHolder();
            viewHolder.file_no = (TextView) convertView.findViewById(R.id.file_no);
            viewHolder.DateOpened = (TextView) convertView.findViewById(R.id.DateOpened);
            viewHolder.LoanAmount = (TextView) convertView.findViewById(R.id.LoanAmount);
            viewHolder.PropertyAddress = (TextView) convertView.findViewById(R.id.PropertyAddress);
            viewHolder.ContactCompanyName = (TextView) convertView.findViewById(R.id.ContactCompanyName);
            viewHolder.prelimicon = (ImageView) convertView.findViewById(R.id.prelimicon);
            viewHolder.filetype = (View) convertView.findViewById(R.id.filetype);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        OrderList item = getItem(position);
        if (item!= null) {
            if(!TextUtils.isEmpty(item.fileNo))
            viewHolder.file_no.setText(item.fileNo);
            
            if(orderStatus.equalsIgnoreCase("Closing"))
                viewHolder.DateOpened.setText(item.dateClosed);
            else
                viewHolder.DateOpened.setText(item.dateOpened);

            viewHolder.LoanAmount.setText(item.getLoanAmount());
            viewHolder.PropertyAddress.setText(item.propertyAddress);
            viewHolder.ContactCompanyName.setText(item.customerName);
            if(item.isPreLimReady.equalsIgnoreCase("Y"))
                viewHolder.prelimicon.setVisibility(View.VISIBLE);
            else
                viewHolder.prelimicon.setVisibility(View.INVISIBLE);

            if(TextUtils.isEmpty(item.orderType)){
                viewHolder.filetype.setBackgroundColor(context.getResources().getColor(R.color.white));
            }else if(item.orderType.equalsIgnoreCase("Sale")){
                viewHolder.filetype.setBackgroundColor(context.getResources().getColor(R.color.blue));
            }else if(item.orderType.equalsIgnoreCase("Refi")){
                viewHolder.filetype.setBackgroundColor(context.getResources().getColor(R.color.green));
            }
        }

        return convertView;
    }

}
