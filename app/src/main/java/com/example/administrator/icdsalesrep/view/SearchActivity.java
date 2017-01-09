package com.example.administrator.icdsalesrep.view;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.ImageView;

import com.example.administrator.icdsalesrep.ICdSalesRepApplication;
import com.example.administrator.icdsalesrep.R;
import com.example.administrator.icdsalesrep.adapter.ExpandableListAdapter;
import com.example.administrator.icdsalesrep.network.model.OrderListRequestDetailData;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.InjectView;

/**
 * Created by nitin on 23/08/16.
 */
public class SearchActivity extends AppCompatActivity {

    @InjectView(R.id.lvExp)
    ExpandableListView lvExp;

    @InjectView(R.id.all_rep_checkbox)
    CheckBox allRepcheckBox;

    ExpandableListAdapter listAdapter;

    List<String> listDataHeader;

    private int year;
    private int month;
    private int day;

    public SearchActivity(){

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.search_layout);
        ButterKnife.inject(this);
        setExpandedListIndicator();
        // preparing list data
        prepareListData();
        listAdapter = new ExpandableListAdapter(this, listDataHeader);
        // setting list adapter
        lvExp.setAdapter(listAdapter);

    }

    private void setExpandedListIndicator() {
        lvExp.setGroupIndicator(null);
        lvExp.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View clickedView, int groupPosition, long rowId) {
                ImageView groupIndicator = (ImageView) clickedView.findViewById(R.id.help_group_indicator);
                if (parent.isGroupExpanded(groupPosition)) {
                    parent.collapseGroup(groupPosition);
                    groupIndicator.setImageResource(R.drawable.down);
                } else {
                    parent.expandGroup(groupPosition);
                    groupIndicator.setImageResource(R.drawable.up);
                }
                return true;
            }
        });

        lvExp.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
            @Override
            public void onGroupExpand(final int groupPosition) {
                if (groupPosition == 2) {
                    lvExp.post(new Runnable() {

                        @Override
                        public void run() {
                            lvExp.setSelection(groupPosition);
                            if (lvExp.getChildAt(groupPosition) != null)
                                lvExp.requestChildRectangleOnScreen(lvExp.getChildAt(groupPosition),
                                        new Rect(0, 0, lvExp.getChildAt(groupPosition).getRight(), lvExp.getChildAt(groupPosition).getHeight()), false);
                        }
                    });
                }
            }
        });
    }



    private void prepareListData() {
        listDataHeader = new ArrayList<String>();
        // Adding child data
        listDataHeader.add(getString(R.string.file_search));
        listDataHeader.add(getString(R.string.customer_search));
        listDataHeader.add(getString(R.string.prelim_search));
    }

    EditText datePickerEditView;

    public void setCurrentDate(final EditText datePickerEditView){
        final Calendar c = Calendar.getInstance();
        year  = c.get(Calendar.YEAR);
        month = c.get(Calendar.MONTH);
        day   = c.get(Calendar.DAY_OF_MONTH);
        datePickerEditView.setText(new StringBuilder().append(month + 1)
                .append("-").append(day).append("-").append(year)
                .append(" "));
        datePickerEditView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialogue(datePickerEditView);
            }
        });
    }

    public void showDatePickerDialogue(EditText datePickerEditView) {
        this.datePickerEditView = datePickerEditView;
        new DatePickerDialog(this, pickerListener, year, month,day).show();
    }


    private DatePickerDialog.OnDateSetListener pickerListener = new DatePickerDialog.OnDateSetListener() {

        // when dialog box is closed, below method will be called.
        @Override
        public void onDateSet(DatePicker view, int selectedYear,
                              int selectedMonth, int selectedDay) {

            year  = selectedYear;
            month = selectedMonth;
            day   = selectedDay;
            // Show selected date
            datePickerEditView.setText(new StringBuilder().append(month + 1)
                    .append("/").append(day).append("/").append(year)
                    .append(" "));

            datePickerEditView.setTag("onClick");

        }
    };

    public void initiateSearch(OrderListRequestDetailData orderRequest) {
        if(allRepcheckBox.isChecked())
            orderRequest.allRep ="Y";
        else
            orderRequest.allRep ="N";

        ((ICdSalesRepApplication)getApplication()).setSearchOrderRequest(orderRequest);
        Intent returnIntent = new Intent();
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    /*
    {"ClosedDateFrom":"","StreetName":"","OpenDateTo":"","PreLimDateTo":"","StreetNumber":"123","OrderType":"All",
    "FileNumber":"","CustomerName":"","CustomerLastName":"","CustomerCity":"","AllRep":"N","CustomerFirstName":"",
    "ClosedDateTo":"","PreLimDateFrom":"","OrderStatus":"Opening","LoginSessionKey":"8dad2dea-3a0b-4292-a869-6b24262c4e44",
    "City":"","OpenDateFrom":""}
     */

    /*
    {"ClosedDateFrom":"","StreetName":"qw","OpenDateTo":"","PreLimDateTo":"","StreetNumber":"","OrderType":"All",
    "FileNumber":"","CustomerName":"","CustomerLastName":"","CustomerCity":"","AllRep":"N","CustomerFirstName":"",
    "ClosedDateTo":"","PreLimDateFrom":"",
    "OrderStatus":"Closing","LoginSessionKey":"aeb0c102-cbb6-4f18-b01d-cea96aeb7e7c","City":"","OpenDateFrom":""}
     */

    /*
    {"ClosedDateFrom":"","StreetName":"","OpenDateTo":"","PreLimDateTo":"","StreetNumber":"","OrderType":"Sale",
    "FileNumber":"","CustomerName":"","CustomerLastName":"","CustomerCity":"","AllRep":"N","CustomerFirstName":"",
    "ClosedDateTo":"","PreLimDateFrom":"","OrderStatus":"Opening","LoginSessionKey":"aeb0c102-cbb6-4f18-b01d-cea96aeb7e7c",
    "City":"","OpenDateFrom":""}
     */

    /*
    {"ClosedDateFrom":"","StreetName":"","OpenDateTo":"","PreLimDateTo":"","StreetNumber":"","OrderType":"Sale",
    "FileNumber":"","CustomerName":"","CustomerLastName":"","CustomerCity":"","AllRep":"N","CustomerFirstName":"",
    "ClosedDateTo":"","PreLimDateFrom":"","OrderStatus":"Closing","LoginSessionKey":"aeb0c102-cbb6-4f18-b01d-cea96aeb7e7c","City":"","OpenDateFrom":""}
     */

    /*
    {"ClosedDateFrom":"","StreetName":"","OpenDateTo":"","PreLimDateTo":"","StreetNumber":"","OrderType":"Sale",
    "FileNumber":"","CustomerName":"","CustomerLastName":"","CustomerCity":"","AllRep":"N","CustomerFirstName":"",
    "ClosedDateTo":"","PreLimDateFrom":"","OrderStatus":"Prelim","LoginSessionKey":"aeb0c102-cbb6-4f18-b01d-cea96aeb7e7c","City":"","OpenDateFrom":""}
     */
}
