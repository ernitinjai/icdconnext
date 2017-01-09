package com.example.administrator.icdsalesrep.adapter;

/**
 * Created by nitin on 24/08/16.
 */

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseExpandableListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.example.administrator.icdsalesrep.R;
import com.example.administrator.icdsalesrep.network.model.Contact;
import com.example.administrator.icdsalesrep.network.model.Document;
import com.example.administrator.icdsalesrep.network.model.Documents;
import com.example.administrator.icdsalesrep.network.model.ICDService;
import com.example.administrator.icdsalesrep.network.model.OrderList;
import com.example.administrator.icdsalesrep.util.Utility;
import com.example.administrator.icdsalesrep.view.DocumentViewerActivity;
import com.example.administrator.icdsalesrep.view.OrderDetailActivity;

import java.util.ArrayList;
import java.util.List;

public class ExpandableOrderListAdapter extends BaseExpandableListAdapter {

    private Activity _context;
    private List<String> _listDataHeader; // header titles
    // child data in format of header title, child title
    private OrderList orderList;

    public ExpandableOrderListAdapter(Activity context, List<String> listDataHeader,
                                      OrderList orderList) {
        this._context = context;
        this._listDataHeader = listDataHeader;
        this.orderList = orderList;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return "";
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        //if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (groupPosition){
            case 0: convertView = infalInflater.inflate(R.layout.contact_layout, null);
                final ListView contactList = (ListView) convertView
                        .findViewById(R.id.contact_list);
                contactList.setFocusable(true);
                ContactListAdapter clAdapter = new ContactListAdapter(_context,(ArrayList<Contact>) orderList.contacts);
                contactList.setAdapter(clAdapter);
                Utility.setListViewHeightBasedOnChildren(contactList);



                    break;
            case 1:convertView = infalInflater.inflate(R.layout.icd_services_layout, null);
                ListView icdServiceList = (ListView) convertView.findViewById(R.id.icd_services_list);
                final ArrayList<ICDService> icdServices = (ArrayList<ICDService>) orderList.iCDServices;

                if(icdServices == null || icdServices.isEmpty()){
                    TextView text =  (TextView) convertView
                            .findViewById(R.id.no_doc_avail);
                    text.setVisibility(View.VISIBLE);
                    icdServiceList.setVisibility(View.GONE);
                    TextView req_prelim = (TextView) convertView.findViewById(R.id.req_prelim);
                    if(!orderList.isPreLimReady.equalsIgnoreCase("Y")){
                        req_prelim.setVisibility(View.INVISIBLE);
                    }
                }else {
                    ICDServiceListAdapter icdServiceAdapter = new ICDServiceListAdapter(_context,icdServices );
                    icdServiceList.setAdapter(icdServiceAdapter);
                    Utility.setListViewHeightBasedOnChildren(icdServiceList);
                    TextView req_prelim = (TextView) convertView.findViewById(R.id.req_prelim);
                    if(orderList.isPreLimReady.equalsIgnoreCase("Y")){
                        req_prelim.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ((OrderDetailActivity) _context).requestPrlims();
                            }
                        });
                    }else{
                        req_prelim.setVisibility(View.INVISIBLE);
                    }
                }
                break;
            case 2:
                convertView = infalInflater.inflate(R.layout.documents_layout, null);
                final ListView documentList = (ListView) convertView
                        .findViewById(R.id.document_list);
                final Documents doc= ((OrderDetailActivity)_context).documents ;
                if(doc == null){
                    TextView text =  (TextView) convertView
                            .findViewById(R.id.no_doc_avail);
                    text.setVisibility(View.VISIBLE);
                    documentList.setVisibility(View.GONE);
                }else {
                    final ArrayList<Document> document = (ArrayList<Document>)doc.documents;
                    DocumentListAdapter doclistAdapter = new DocumentListAdapter(_context, document);
                    documentList.setAdapter(doclistAdapter);
                    Utility.setListViewHeightBasedOnChildren(documentList);

                    documentList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                        @Override
                        public void onItemClick(AdapterView<?> parent, View view, int position,
                                                long id) {
                            String docUrl = document.get(position).documentDownloadUrl;
                            String docname = document.get(position).documentName;
                            Intent intent = new Intent(_context, DocumentViewerActivity.class);
                            intent.putExtra("PDFURL", docUrl);
                            intent.putExtra("docName", docname);
                            _context.startActivity(intent);
                        }
                    });
                }

                break;

        }

        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return 1;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this._listDataHeader.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this._listDataHeader.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = infalInflater.inflate(R.layout.order_list_group, null);
        }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        //lblListHeader.setTypeface(null, Typeface.BOLD);
        lblListHeader.setText(headerTitle);

        return convertView;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }


}
