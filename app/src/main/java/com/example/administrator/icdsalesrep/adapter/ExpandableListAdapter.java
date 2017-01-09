package com.example.administrator.icdsalesrep.adapter;

/**
 * Created by nitin on 24/08/16.
 */

import android.content.Context;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.administrator.icdsalesrep.R;
import com.example.administrator.icdsalesrep.network.model.OrderListRequestDetailData;
import com.example.administrator.icdsalesrep.view.SearchActivity;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class ExpandableListAdapter extends BaseExpandableListAdapter{

    private Context _context;
    private List<String> _listDataHeader; // header titles

    public ExpandableListAdapter(Context context, List<String> listDataHeader) {
        this._context = context;
        this._listDataHeader = listDataHeader;
    }


    static class FileSearchViewHolder {
        @InjectView(R.id.file_no) EditText file_no;
        @InjectView(R.id.street_numer) EditText street_numer;
        @InjectView(R.id.street_name) EditText street_name;
        @InjectView(R.id.city) EditText city;
        @InjectView(R.id.opendate_from) EditText opendate_from;
        @InjectView(R.id.opendate_to) EditText opendate_to;
        @InjectView(R.id.close_date_from) EditText close_date_from;
        @InjectView(R.id.close_date_to) EditText close_date_to;

        @InjectView(R.id.close_date_search) ImageView close_date_search;
        @InjectView(R.id.opendate_search) ImageView opendate_search;
        @InjectView(R.id.city_search) ImageView city_search;
        @InjectView(R.id.street_name_search) ImageView street_name_search;
        @InjectView(R.id.street_numerSearch) ImageView street_numerSearch;
        @InjectView(R.id.fileSearch) ImageView fileSearch;



        public FileSearchViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class CustomerSearchViewHolder {

        @InjectView(R.id.company_name) EditText company_name;
        @InjectView(R.id.fname_lname) EditText fname_lname;
        @InjectView(R.id.city_optional) EditText city_optional;

        @InjectView(R.id.company_Search) ImageView company_Search;
        @InjectView(R.id.fname_lname_Search) ImageView fname_lname_Search;
        @InjectView(R.id.city_optional_Search) ImageView city_optional_Search;

        public CustomerSearchViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
    }

    static class PrelimSearchViewHolder {

        @InjectView(R.id.preim_date_from) EditText preim_date_from;
        @InjectView(R.id.prelim_date_to) EditText prelim_date_to;
        @InjectView(R.id.prelim_search) ImageView prelim_search;

        public PrelimSearchViewHolder(View view) {
            ButterKnife.inject(this, view);
        }
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


            LayoutInflater infalInflater = (LayoutInflater) this._context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        switch (groupPosition){
            case 0: convertView = infalInflater.inflate(R.layout.file_search, null);
                FileSearchViewHolder  viewHolder = new FileSearchViewHolder(convertView);
                fileSearchMetaData(viewHolder);
                break;
            case 1:convertView = infalInflater.inflate(R.layout.customer_search, null);
                customerSearchMetaData(new CustomerSearchViewHolder(convertView));
                break;
            case 2:
                convertView = infalInflater.inflate(R.layout.prelim_search, null);
                prelimSearchMetaData(new PrelimSearchViewHolder(convertView));
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

            convertView = infalInflater.inflate(R.layout.list_group, null);
       }

        TextView lblListHeader = (TextView) convertView
                .findViewById(R.id.lblListHeader);
        lblListHeader.setTextColor(_context.getResources().getColor(R.color.white));
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



    private void initiateSearchForThisView(final ImageView view,final Object viewHolder ) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                performSearch(viewHolder);
            }
        });

    }

    private void initiateSearchForThisView(final EditText view,final Object viewHolder ) {
        view.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch(viewHolder);
                    return true;
                }
                return false;
            }
        });

    }

    private void performSearch(Object viewHolder) {
        OrderListRequestDetailData orderRequest = new OrderListRequestDetailData();
        if(viewHolder instanceof FileSearchViewHolder) {

            orderRequest.fileNumber = ((FileSearchViewHolder)viewHolder).file_no.getText() != null ? ((FileSearchViewHolder)viewHolder).file_no.getText().toString() : "";
            orderRequest.streetNumber = ((FileSearchViewHolder)viewHolder).street_numer.getText() != null ? ((FileSearchViewHolder)viewHolder).street_numer.getText().toString() : "";
            orderRequest.city = ((FileSearchViewHolder)viewHolder).city.getText() != null ? ((FileSearchViewHolder)viewHolder).city.getText().toString() : "";
            orderRequest.openDateFrom = ((FileSearchViewHolder)viewHolder).opendate_from.getText() != null && ((String)((FileSearchViewHolder)viewHolder).opendate_from.getTag()) !=null ? ((FileSearchViewHolder)viewHolder).opendate_from.getText().toString() : "";
            orderRequest.openDateTo = ((FileSearchViewHolder)viewHolder).opendate_to.getText() != null && ((String)((FileSearchViewHolder)viewHolder).opendate_to.getTag()) !=null ? ((FileSearchViewHolder)viewHolder).opendate_to.getText().toString() : "";
            orderRequest.closedDateFrom = ((FileSearchViewHolder)viewHolder).close_date_from.getText() != null &&   ((String)((FileSearchViewHolder)viewHolder).close_date_from.getTag()) !=null? ((FileSearchViewHolder)viewHolder).close_date_from.getText().toString() : "";
            orderRequest.closedDateTo = ((FileSearchViewHolder)viewHolder).close_date_to.getText() != null && ((String)((FileSearchViewHolder)viewHolder).close_date_to.getTag()) !=null  ? ((FileSearchViewHolder)viewHolder).close_date_to.getText().toString() : "";
        }else if (viewHolder instanceof CustomerSearchViewHolder){
            orderRequest.customerName= ((CustomerSearchViewHolder)viewHolder).company_name.getText() != null ? ((CustomerSearchViewHolder)viewHolder).company_name.getText().toString() : "";
            orderRequest.customerFirstName = ((CustomerSearchViewHolder)viewHolder).fname_lname.getText() != null ? ((CustomerSearchViewHolder)viewHolder).fname_lname.getText().toString() : "";
            orderRequest.customerCity = ((CustomerSearchViewHolder)viewHolder).city_optional.getText() != null ? ((CustomerSearchViewHolder)viewHolder).city_optional.getText().toString() : "";

        }else if(viewHolder instanceof PrelimSearchViewHolder){
            orderRequest.preLimDateTo= ((PrelimSearchViewHolder)viewHolder).prelim_date_to.getText() != null /*&& ((String)((PrelimSearchViewHolder)viewHolder).prelim_date_to.getTag()) !=null */? ((PrelimSearchViewHolder)viewHolder).prelim_date_to.getText().toString() : "";
            orderRequest.preLimDateFrom = ((PrelimSearchViewHolder)viewHolder).preim_date_from.getText() != null /*&& ((String)((PrelimSearchViewHolder)viewHolder).preim_date_from.getTag()) !=null*/ ? ((PrelimSearchViewHolder)viewHolder).preim_date_from.getText().toString() : "";

        }
        ((SearchActivity)_context).initiateSearch(orderRequest);
    }

    private void fileSearchMetaData(final FileSearchViewHolder viewHolder) {
        ((SearchActivity)_context).setCurrentDate(viewHolder.opendate_from);
        ((SearchActivity)_context).setCurrentDate(viewHolder.opendate_to);
        ((SearchActivity)_context).setCurrentDate(viewHolder.close_date_from);
        ((SearchActivity)_context).setCurrentDate(viewHolder.close_date_to);
        initiateSearchForThisView(viewHolder.fileSearch,viewHolder);
        initiateSearchForThisView(viewHolder.street_numerSearch,viewHolder);
        initiateSearchForThisView(viewHolder.street_name_search,viewHolder);
        initiateSearchForThisView(viewHolder.city_search,viewHolder);
        initiateSearchForThisView(viewHolder.opendate_search,viewHolder);
        initiateSearchForThisView(viewHolder.close_date_search,viewHolder);

        initiateSearchForThisView(viewHolder.file_no,viewHolder);
        initiateSearchForThisView(viewHolder.street_numer,viewHolder);
        initiateSearchForThisView(viewHolder.city,viewHolder);
        initiateSearchForThisView(viewHolder.street_name,viewHolder);




    }

    private void customerSearchMetaData(final CustomerSearchViewHolder customerSearchViewHolder) {
        initiateSearchForThisView(customerSearchViewHolder.company_Search,customerSearchViewHolder);
        initiateSearchForThisView(customerSearchViewHolder.fname_lname_Search,customerSearchViewHolder);
        initiateSearchForThisView(customerSearchViewHolder.city_optional_Search,customerSearchViewHolder);

        initiateSearchForThisView(customerSearchViewHolder.city_optional,customerSearchViewHolder);
        initiateSearchForThisView(customerSearchViewHolder.company_name,customerSearchViewHolder);
        initiateSearchForThisView(customerSearchViewHolder.fname_lname,customerSearchViewHolder);
    }


    private void prelimSearchMetaData(PrelimSearchViewHolder prelimSearchViewHolder) {
        ((SearchActivity)_context).setCurrentDate(prelimSearchViewHolder.preim_date_from);
        ((SearchActivity)_context).setCurrentDate(prelimSearchViewHolder.prelim_date_to);
        initiateSearchForThisView(prelimSearchViewHolder.prelim_search,prelimSearchViewHolder);
    }
}
