package com.example.administrator.icdsalesrep.adapter;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.icdsalesrep.R;
import com.example.administrator.icdsalesrep.network.model.Contact;

import java.util.ArrayList;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by nitin on 24/08/16.
 */
public class ContactListAdapter extends ArrayAdapter<Contact> {


    Context context;

    static class ViewHolder {
        @InjectView(R.id.contact_type) TextView contact_type;
        @InjectView(R.id.contact_name) TextView contact_name;
        @InjectView(R.id.contact_company_name) TextView contact_company_name;
        @InjectView(R.id.contact_email) TextView contact_email;
        @InjectView(R.id.contact_phone) TextView contact_phone;
        @InjectView(R.id.contact_ref) TextView contact_ref;
        @InjectView(R.id.add_to_phone) TextView add_to_phone;

        public ViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }


    public ContactListAdapter(Context context, ArrayList<Contact> items) {

        super(context, 0, items);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.getContext())
                    .inflate(R.layout.contact_item, parent, false);

            viewHolder = new ViewHolder(convertView);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        final Contact item = getItem(position);
        if (item!= null) {

            viewHolder.contact_type.setText(item.contactType);
            if(TextUtils.isEmpty(item.contactName)) {
                viewHolder.contact_name.setVisibility(View.GONE);
            }else
                viewHolder.contact_name.setText(item.contactName);

            if(TextUtils.isEmpty(item.contactCompanyName)) {
                viewHolder.contact_company_name.setVisibility(View.GONE);
            }else
                viewHolder.contact_company_name.setText(item.contactCompanyName);

            if(TextUtils.isEmpty(item.email)) {
                viewHolder.contact_email.setVisibility(View.GONE);
            }else
                viewHolder.contact_email.setText(item.email);

            if(TextUtils.isEmpty(item.phoneNo)) {
                viewHolder.contact_phone.setVisibility(View.GONE);
            }else
                viewHolder.contact_phone.setText(item.phoneNo);

            if(TextUtils.isEmpty(item.refNo)) {
                viewHolder.contact_ref.setVisibility(View.GONE);
            }else
                viewHolder.contact_ref.setText("Ref: "+ item.refNo);

            if(TextUtils.isEmpty(item.email) && TextUtils.isEmpty(item.phoneNo))
                viewHolder.add_to_phone.setVisibility(View.GONE);
            else
                viewHolder.add_to_phone.setVisibility(View.VISIBLE);


            viewHolder.add_to_phone.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent addContactIntent = new Intent(Intent.ACTION_INSERT);
                    addContactIntent.setType(ContactsContract.Contacts.CONTENT_TYPE);
                    addContactIntent.putExtra(ContactsContract.Intents.Insert.PHONE,item.phoneNo);
                    addContactIntent.putExtra(ContactsContract.Intents.Insert.NAME,item.contactName);
                    addContactIntent.putExtra(ContactsContract.Intents.Insert.EMAIL,item.email);
                    context.startActivity(addContactIntent);
                }
            });
            viewHolder.contact_email.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String[] TO = {item.email};
                    Intent emailIntent = new Intent(Intent.ACTION_SEND);
                    emailIntent.setData(Uri.parse("mailto:"));
                    emailIntent.setType("text/plain");
                    emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);

                    try {
                        context.startActivity(Intent.createChooser(emailIntent, "Send mail..."));

                    }
                    catch (android.content.ActivityNotFoundException ex) {
                        Toast.makeText(context, "There is no email client installed.", Toast.LENGTH_SHORT).show();
                    }
                }
            });

        }

        return convertView;
    }

}
