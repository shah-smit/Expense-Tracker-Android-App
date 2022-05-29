package com.example.expensetrackerapp.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.expensetrackerapp.data.CompanyContract;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class CompanyRepository {

    private final CompanyContract.CompanyEntry.CompanyDbHelper dbHelper;

    public CompanyRepository(CompanyContract.CompanyEntry.CompanyDbHelper dbHelper) {
        this.dbHelper = dbHelper;
    }

    public void insert(CompanyEntity companyEntity) {
        System.out.println("Performing insertion for " + companyEntity);
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(CompanyContract.CompanyEntry.COLUMN_NAME_NAME, companyEntity.getCompanyName());
        values.put(CompanyContract.CompanyEntry.COLUMN_NAME_ADDRESS, companyEntity.getCompanyAddress());
        values.put(CompanyContract.CompanyEntry.COLUMN_NAME_DESCRIPTION, companyEntity.getCompanyDescription());
        values.put(CompanyContract.CompanyEntry.COLUMN_NAME_OUTLET, companyEntity.getOutlet());
        values.put(CompanyContract.CompanyEntry.COLUMN_NAME_LAST_UPDATED_TIME, companyEntity.getLastUpdatedTime());


        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(CompanyContract.CompanyEntry.TABLE_NAME, null, values);

        System.out.println("Company with id " + newRowId + " has been added");
    }

    public List<CompanyEntity> retrieve() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                CompanyContract.CompanyEntry.COLUMN_NAME_NAME,
                CompanyContract.CompanyEntry.COLUMN_NAME_ADDRESS,
                CompanyContract.CompanyEntry.COLUMN_NAME_DESCRIPTION,
                CompanyContract.CompanyEntry.COLUMN_NAME_OUTLET,
                CompanyContract.CompanyEntry.COLUMN_NAME_LAST_UPDATED_TIME
        };

        Cursor cursor = db.query(
                CompanyContract.CompanyEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<CompanyEntity> companyEntities = new ArrayList<>();
        while (cursor.moveToNext()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow(CompanyContract.CompanyEntry.COLUMN_NAME_NAME));
            String address = cursor.getString(cursor.getColumnIndexOrThrow(CompanyContract.CompanyEntry.COLUMN_NAME_ADDRESS));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(CompanyContract.CompanyEntry.COLUMN_NAME_DESCRIPTION));
            String outlet = cursor.getString(cursor.getColumnIndexOrThrow(CompanyContract.CompanyEntry.COLUMN_NAME_OUTLET));
            String lastUpdatedTime = cursor.getString(cursor.getColumnIndexOrThrow(CompanyContract.CompanyEntry.COLUMN_NAME_LAST_UPDATED_TIME));

            CompanyEntity companyEntity = new CompanyEntity(name, address, description, outlet, lastUpdatedTime);
            companyEntities.add(companyEntity);
        }
        cursor.close();
        return companyEntities;
    }
}
