package com.stepin.coronaapp.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;


import com.stepin.coronaapp.R;
import com.stepin.coronaapp.model.CheckData;
import com.stepin.coronaapp.model.ChvMotherLocal;
import com.stepin.coronaapp.model.ChvVisitLocal;
import com.stepin.coronaapp.model.EnforceLocal;
import com.stepin.coronaapp.model.GbvLocal;
import com.stepin.coronaapp.model.ReportLocal;
import com.stepin.coronaapp.model.TracingOfficer;
import com.stepin.coronaapp.model.TracingPassenger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ExpandableListViewAdapter extends BaseExpandableListAdapter {

    private Context context;

    // group titles
    private List<String> listDataGroup;

    // child data in format of header title, child title
    private HashMap<String, List<String>> listDataChild;

    public ExpandableListViewAdapter(Context context, List<String> listDataGroup,
                                     HashMap<String, List<String>> listChildData) {
        this.context = context;
        this.listDataGroup = listDataGroup;
        this.listDataChild = listChildData;
    }

    @Override
    public Object getChild(int groupPosition, int childPosititon) {
        return this.listDataChild.get(this.listDataGroup.get(groupPosition))
                .get(childPosititon);
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public View getChildView(int groupPosition, final int childPosition,
                             boolean isLastChild, View convertView, ViewGroup parent) {

        final String childText = (String) getChild(groupPosition, childPosition);

        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_row_child, null);
        }

        TextView textViewChild = convertView
                .findViewById(R.id.textViewChild);

        textViewChild.setText(childText);
        return convertView;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        if(this.listDataChild.get(this.listDataGroup.get(groupPosition)) == null)
            return 0;
        return this.listDataChild.get(this.listDataGroup.get(groupPosition))
                .size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return this.listDataGroup.get(groupPosition);
    }

    @Override
    public int getGroupCount() {
        return this.listDataGroup.size();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded,
                             View convertView, ViewGroup parent) {
        String headerTitle = (String) getGroup(groupPosition);
        String headerCount = String.valueOf(getChildrenCount(groupPosition));
        if (convertView == null) {
            LayoutInflater layoutInflater = (LayoutInflater) this.context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = layoutInflater.inflate(R.layout.list_row_group, null);
        }

        TextView textViewGroup = convertView
                .findViewById(R.id.textViewGroup);
        TextView count_txt = convertView.findViewById(R.id.count);
        textViewGroup.setTypeface(null, Typeface.BOLD);
        textViewGroup.setText(headerTitle);
        count_txt.setTypeface(null,Typeface.BOLD);
        count_txt.setText(headerCount);

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
    public void reportitemRemoved(String str, List<ReportLocal> gbv_list_all, int childPosition) {
        HashMap<String, List<String>> tempChildList = listDataChild;
        List<String> stringList = listDataChild.get(str);
        List<String> _stringList = stringList;
        ReportLocal gbvLocal = gbv_list_all.get(childPosition);
        for (int i = 0; i < stringList.size(); i++){
            if (stringList.get(i).equalsIgnoreCase(String.valueOf(gbvLocal.getCreated()))){
                stringList.remove(i);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    listDataChild.replace(str, _stringList, stringList);
                }
                notifyDataSetChanged();
                break;
            }
        }

        notifyDataSetChanged();
    }
    public void checkitemRemoved(String str, List<CheckData> gbv_list_all, int childPosition) {
        HashMap<String, List<String>> tempChildList = listDataChild;
        List<String> stringList = listDataChild.get(str);
        List<String> _stringList = stringList;
        CheckData gbvLocal = gbv_list_all.get(childPosition);
        for (int i = 0; i < stringList.size(); i++){
            if (stringList.get(i).equalsIgnoreCase(String.valueOf(gbvLocal.getCreated_time()))){
                stringList.remove(i);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    listDataChild.replace(str, _stringList, stringList);
                }
                notifyDataSetChanged();
                break;
            }
        }

        notifyDataSetChanged();
    }
    public void enforceitemRemoved(String str, List<EnforceLocal> gbv_list_all, int childPosition) {
        HashMap<String, List<String>> tempChildList = listDataChild;
        List<String> stringList = listDataChild.get(str);
        List<String> _stringList = stringList;
        EnforceLocal gbvLocal = gbv_list_all.get(childPosition);
        for (int i = 0; i < stringList.size(); i++){
            if (stringList.get(i).equalsIgnoreCase(String.valueOf(gbvLocal.getCreated()))){
                stringList.remove(i);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    listDataChild.replace(str, _stringList, stringList);
                }
                notifyDataSetChanged();
                break;
            }
        }

        notifyDataSetChanged();
    }
    public void officeritemRemoved(String str, List<TracingOfficer> gbv_list_all, int childPosition) {
        HashMap<String, List<String>> tempChildList = listDataChild;
        List<String> stringList = listDataChild.get(str);
        List<String> _stringList = stringList;
        TracingOfficer gbvLocal = gbv_list_all.get(childPosition);
        for (int i = 0; i < stringList.size(); i++){
            if (stringList.get(i).equalsIgnoreCase(String.valueOf(gbvLocal.getCreated_time()))){
                stringList.remove(i);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    listDataChild.replace(str, _stringList, stringList);
                }
                notifyDataSetChanged();
                break;
            }
        }

        notifyDataSetChanged();
    }
    public void passengeritemRemoved(String str, List<TracingPassenger> gbv_list_all, int childPosition) {
        HashMap<String, List<String>> tempChildList = listDataChild;
        List<String> stringList = listDataChild.get(str);
        List<String> _stringList = stringList;
        TracingPassenger gbvLocal = gbv_list_all.get(childPosition);
        for (int i = 0; i < stringList.size(); i++){
            if (stringList.get(i).equalsIgnoreCase(String.valueOf(gbvLocal.getCreated()))){
                stringList.remove(i);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    listDataChild.replace(str, _stringList, stringList);
                }
                notifyDataSetChanged();
                break;
            }
        }

        notifyDataSetChanged();
    }
    public void visititemRemoved(String str, List<ChvVisitLocal> gbv_list_all, int childPosition) {
        HashMap<String, List<String>> tempChildList = listDataChild;
        List<String> stringList = listDataChild.get(str);
        List<String> _stringList = stringList;
        ChvVisitLocal gbvLocal = gbv_list_all.get(childPosition);
        for (int i = 0; i < stringList.size(); i++){
            if (stringList.get(i).equalsIgnoreCase(String.valueOf(gbvLocal.getCreated_time()))){
                stringList.remove(i);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    listDataChild.replace(str, _stringList, stringList);
                }
                notifyDataSetChanged();
                break;
            }
        }

        notifyDataSetChanged();
    }
    public void motheritemRemoved(String str, List<ChvMotherLocal> gbv_list_all, int childPosition) {
        HashMap<String, List<String>> tempChildList = listDataChild;
        List<String> stringList = listDataChild.get(str);
        List<String> _stringList = stringList;
        ChvMotherLocal gbvLocal = gbv_list_all.get(childPosition);
        for (int i = 0; i < stringList.size(); i++){
            if (stringList.get(i).equalsIgnoreCase(String.valueOf(gbvLocal.getCreated_time()))){
                stringList.remove(i);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    listDataChild.replace(str, _stringList, stringList);
                }
                notifyDataSetChanged();
                break;
            }
        }

        notifyDataSetChanged();
    }
    public void gbvitemRemoved(String str, List<GbvLocal> gbv_list_all, int childPosition) {
        HashMap<String, List<String>> tempChildList = listDataChild;
        List<String> stringList = listDataChild.get(str);
        List<String> _stringList = stringList;
        GbvLocal gbvLocal = gbv_list_all.get(childPosition);
        for (int i = 0; i < stringList.size(); i++){
            if (stringList.get(i).equalsIgnoreCase(String.valueOf(gbvLocal.getCreated()))){
                stringList.remove(i);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    listDataChild.replace(str, _stringList, stringList);
                }
                notifyDataSetChanged();
                break;
            }
        }

        notifyDataSetChanged();
    }
    public void reportAllRemoved(String str) {
        HashMap<String, List<String>> tempChildList = listDataChild;
        List<String> stringList = listDataChild.get(str);
        List<String> _stringList = stringList;
        stringList.clear();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            listDataChild.replace(str, _stringList, stringList);
        }
        notifyDataSetChanged();

    }
}
