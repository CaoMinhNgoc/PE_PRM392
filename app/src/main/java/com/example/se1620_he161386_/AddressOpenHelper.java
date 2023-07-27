package com.example.se1620_he161386_;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class AddressOpenHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "AddressDb";
    public static final int DATABASE_VERSION = 1;
    public static final String TABLE_NAME = "Address";
    public static final String COLUMN_ID = "id";
    public static final String COLUMN_STREET = "street";
    public static final String COLUMN_CITY = "city";
    public static final String COLUMN_ZIPCODE = "zipcode";

    public AddressOpenHelper(Context context) {
        super(context, "AddressDb", null, 1);
    }

    @Override
    public void onCreate(@NonNull SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABLE_NAME + "(" +
                       COLUMN_ID + " INTEGER PRIMARY KEY, " +
                       COLUMN_STREET + " TEXT NOT NULL, " +
                       COLUMN_CITY + " TEXT NOT NULL, " +
                       COLUMN_ZIPCODE + " TEXT NOT NULL" + ")" ;
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(@NonNull SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void add(Address address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_ID, address.getId());
        values.put(COLUMN_STREET, address.getStreet());
        values.put(COLUMN_CITY, address.getCity());
        values.put(COLUMN_ZIPCODE, address.getZipcode());

        long result = db.insertWithOnConflict(TABLE_NAME, null, values, SQLiteDatabase.CONFLICT_REPLACE);
        db.close();
    }

    public Address getById(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_NAME, new String[]{COLUMN_ID, COLUMN_STREET, COLUMN_CITY,
                COLUMN_ZIPCODE}, COLUMN_ID + "= ?", new String[]{String.valueOf(id)},
                null, null, null, null);
        
        Address address = null;
        if (cursor != null && cursor.moveToFirst()) {
            int addressId = cursor.getInt(0);
            String street = cursor.getString(1);
            String city = cursor.getString(2);
            String zipCode = cursor.getString(3);
            address = new Address(addressId, street, city, zipCode);
        }

        if (cursor != null) {
            cursor.close();
        }

        return address;
    }

    public List<Address> getAll(){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
        List<Address> addressList = new ArrayList<>();

        while (cursor.moveToNext()){
            int addressId = cursor.getInt(0);
            String street = cursor.getString(1);
            String city = cursor.getString(2);
            String zipCode = cursor.getString(3);
            Address address = new Address(addressId, street, city, zipCode);
            addressList.add(address);
        }

        cursor.close();
        return addressList;
    }

    public List<Address> search(ArrayList<String> searchTexts){
        List<Address> addressList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String selection = COLUMN_ID + " LIKE ? AND " + COLUMN_STREET +" LIKE ? AND "
                           + COLUMN_CITY + " LIKE ? AND " + COLUMN_ZIPCODE + " LIKE ?";
        String[] selectionArgs = new String[]{"%" + searchTexts.get(0) + "%",
                "%" + searchTexts.get(1) + "%", "%" + searchTexts.get(2) + "%",
                "%" + searchTexts.get(3) + "%"};

        Cursor cursor = db.query(TABLE_NAME, null, selection, selectionArgs,
                    null, null, null);

        while (cursor.moveToNext()){
            int addressId = cursor.getInt(0);
            String street = cursor.getString(1);
            String city = cursor.getString(2);
            String zipCode = cursor.getString(3);
            Address address = new Address(addressId, street, city, zipCode);
            addressList.add(address);
        }

        return addressList;
    }

    public int update(@NonNull Address address){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(COLUMN_STREET, address.getStreet());
        values.put(COLUMN_CITY, address.getCity());
        values.put(COLUMN_ZIPCODE, address.getZipcode());

       return db.update(TABLE_NAME, values, COLUMN_ID + "= ?",
                new String[]{String.valueOf(address.getId())});
    }

    public void delete(@NonNull Address address){
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_ID + "= ?",
                new String[]{String.valueOf(address.getId())});
        db.close();
    }
}
