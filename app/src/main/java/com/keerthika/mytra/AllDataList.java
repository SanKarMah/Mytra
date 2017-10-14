package com.keerthika.mytra;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.keerthika.mytra.DataModel.DataModel;
import com.keerthika.mytra.database.DBManager;

import java.util.ArrayList;

/**
 * Created by Appstek O 7 on 27-09-2017.
 */

public class AllDataList extends AppCompatActivity {
    private DBManager dbManager;
    CustomAdapter customAdapter;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_data_view);
        ListView listView = (ListView)findViewById(R.id.list_view);
        dbManager = new DBManager(this);
        dbManager.open();

        customAdapter = new CustomAdapter(getApplicationContext(),dbManager.getList());
        listView.setAdapter(customAdapter);
    }




}
 class CustomAdapter extends BaseAdapter {

     ArrayList<DataModel> dataModelArrayList;
    Context context;
    private static LayoutInflater inflater=null;
    public CustomAdapter(Context contexted, ArrayList<DataModel> dataModels) {
        // TODO Auto-generated constructor stub
        context=contexted;
        dataModelArrayList = dataModels;
        inflater = ( LayoutInflater )context.
                getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return dataModelArrayList.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    public class Holder
    {
        TextView tv_name,tv_phone,tv_haidresser,tv_datetime;

    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=new Holder();
        View rowView;
        rowView = inflater.inflate(R.layout.list_row, null);
        holder.tv_name=(TextView) rowView.findViewById(R.id.tv_name);
        holder.tv_phone=(TextView) rowView.findViewById(R.id.tv_Phone);
        holder.tv_haidresser=(TextView) rowView.findViewById(R.id.tv_hairdresser);
        holder.tv_datetime=(TextView) rowView.findViewById(R.id.tv_datetime);

        holder.tv_name.setText("Name : " + dataModelArrayList.get(position).getNAME());
        holder.tv_phone.setText("Phone : " + dataModelArrayList.get(position).getPHONENUMBER());
        holder.tv_haidresser.setText("Hairdresser : " + dataModelArrayList.get(position).getHAIRDRESSER());
        holder.tv_datetime.setText("Date : " + dataModelArrayList.get(position).getDATE_TIME() +" , "+ dataModelArrayList.get(position).getTIME());

        return rowView;
    }

}