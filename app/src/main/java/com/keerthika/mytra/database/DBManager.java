package com.keerthika.mytra.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.keerthika.mytra.DataModel.DataModel;

import java.util.ArrayList;

/**
 * Created by Keerthika Manchala on 26-09-2017.
 */

public class DBManager {
    private DataBase dbHelper;

    private Context context;

    private SQLiteDatabase database;

    public DBManager(Context c) {
        context = c;
    }

    public DBManager open() throws SQLException {
        dbHelper = new DataBase(context);
        database = dbHelper.getWritableDatabase();
        return this;
    }

    public void close() {
        dbHelper.close();
    }

    /*public void insert(String name, String desc,String email,String comments,String rating,String hairdresseer) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DataBase.NAME, name);
        contentValue.put(DataBase.PHONENUMBER, desc);
        contentValue.put(DataBase.EMAIL, email);
        contentValue.put(DataBase.COMMENTS, comments);
        contentValue.put(DataBase.RATING, rating);
        contentValue.put(DataBase.HAIRDRESSER, hairdresseer);
        database.insert(DataBase.TABLE_NAME, null, contentValue);
    }*/

    public void insert(DataModel dataModel) {
        ContentValues contentValue = new ContentValues();
        contentValue.put(DataBase.NAME, dataModel.getNAME());
        contentValue.put(DataBase.PHONENUMBER, dataModel.getPHONENUMBER());
        contentValue.put(DataBase.EMAIL, dataModel.getEMAIL());
        contentValue.put(DataBase.COMMENTS, dataModel.getCOMMENTS());
        contentValue.put(DataBase.RATING, dataModel.getRATING());
        contentValue.put(DataBase.HAIRDRESSER, dataModel.getHAIRDRESSER());
        contentValue.put(DataBase.DATE_TIME, dataModel.getDATE_TIME());
        contentValue.put(DataBase.TIME_VALUE, dataModel.getTIME());
        database.insert(DataBase.TABLE_NAME, null, contentValue);
    }


    public Cursor fetch() {
        String[] columns = new String[] { DataBase._ID, DataBase.NAME, DataBase.PHONENUMBER };
        Cursor cursor = database.query(DataBase.TABLE_NAME, columns, null, null, null, null, null);
        if (cursor != null) {
            cursor.moveToFirst();
        }
        return cursor;
    }
    public ArrayList<DataModel> getList(){
        Cursor cursor = dbHelper.getReadableDatabase().rawQuery("SELECT * FROM "+DataBase.TABLE_NAME, null);
        ArrayList<DataModel> names=new ArrayList<DataModel>();
        if (cursor != null)
        {


            if (cursor.moveToFirst()) {
                do {
                   /* Contact contact = new Contact();
                    contact.setID(Integer.parseInt(cursor.getString(0)));
                    contact.setName(cursor.getString(1));
                    contact.setPhoneNumber(cursor.getString(2));
                    // Adding contact to list
                    contactList.add(contact);
*/
                    DataModel dataModel = new DataModel();
                    dataModel.setNAME(cursor.getString(1));
                    dataModel.setPHONENUMBER(cursor.getString(2));
                    dataModel.setEMAIL(cursor.getString(3));
                    dataModel.setCOMMENTS(cursor.getString(4));
                    dataModel.setRATING(cursor.getString(5));
                    dataModel.setHAIRDRESSER(cursor.getString(6));
                    dataModel.setDATE_TIME(cursor.getString(7));
                    dataModel.setTIME(cursor.getString(8));
                    names.add(dataModel);

                } while (cursor.moveToNext());
            }


            /*if (cursor.moveToFirst()) {
                for(int i = 0; i < cursor.getCount(); i ++){
                    //names.add(cursor.getString(cursor.getColumnIndex("name")));
                    DataModel dataModel = new DataModel();
                    dataModel.setNAME(cursor.getString(cursor.getColumnIndex(DataBase.NAME)));
                    dataModel.setPHONENUMBER(cursor.getString(cursor.getColumnIndex(DataBase.PHONENUMBER)));
                    dataModel.setEMAIL(cursor.getString(cursor.getColumnIndex(DataBase.EMAIL)));
                    dataModel.setCOMMENTS(cursor.getString(cursor.getColumnIndex(DataBase.COMMENTS)));
                    dataModel.setRATING(cursor.getString(cursor.getColumnIndex(DataBase.RATING)));
                    dataModel.setHAIRDRESSER(cursor.getString(cursor.getColumnIndex(DataBase.HAIRDRESSER)));
                    dataModel.setDATE_TIME(cursor.getString(cursor.getColumnIndex(DataBase.DATE_TIME)));
                    dataModel.setTIME(cursor.getString(cursor.getColumnIndex(DataBase.TIME_VALUE)));
                    names.add(dataModel);
                }
            }*/
            cursor.close();
        }
        return names;
    }
    public int update(long _id, String name, String desc) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(DataBase.NAME, name);
        contentValues.put(DataBase.PHONENUMBER, desc);
        int i = database.update(DataBase.TABLE_NAME, contentValues, DataBase._ID + " = " + _id, null);
        return i;
    }

    public void delete(long _id) {
        database.delete(DataBase.TABLE_NAME, DataBase._ID + "=" + _id, null);
    }
}
