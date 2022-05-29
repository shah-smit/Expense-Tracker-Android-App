package com.example.expensetrackerapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public final class CompanyContract {
    private CompanyContract() {
    }

    public static class CompanyEntry implements BaseColumns {
        public static final String TABLE_NAME = "company";
        public static final String COLUMN_NAME_NAME = "name";
        public static final String COLUMN_NAME_ADDRESS = "address";
        public static final String COLUMN_NAME_DESCRIPTION = "description";
        public static final String COLUMN_NAME_OUTLET = "outlet";
        public static final String COLUMN_NAME_LAST_UPDATED_TIME = "lastUpdatedTime";

        private static final String SQL_CREATE_ENTRIES =
                "CREATE TABLE " + CompanyContract.CompanyEntry.TABLE_NAME + " (" +
                        CompanyContract.CompanyEntry._ID + " INTEGER PRIMARY KEY," +
                        CompanyContract.CompanyEntry.COLUMN_NAME_NAME + " TEXT," +
                        CompanyContract.CompanyEntry.COLUMN_NAME_ADDRESS + " TEXT," +
                        CompanyContract.CompanyEntry.COLUMN_NAME_DESCRIPTION + " TEXT," +
                        CompanyContract.CompanyEntry.COLUMN_NAME_OUTLET + " TEXT," +
                        CompanyContract.CompanyEntry.COLUMN_NAME_LAST_UPDATED_TIME + " TEXT)";

        private static final String SQL_DELETE_ENTRIES =
                "DROP TABLE IF EXISTS " + CompanyContract.CompanyEntry.TABLE_NAME;

        public static class CompanyDbHelper extends SQLiteOpenHelper {
            // If you change the database schema, you must increment the database version.
            public static final int DATABASE_VERSION = 1;
            public static final String DATABASE_NAME = "ExpenseTracker.db";

            public CompanyDbHelper(Context context) {
                super(context, DATABASE_NAME, null, DATABASE_VERSION);
            }
            public void onCreate(SQLiteDatabase db) {
                db.execSQL(SQL_CREATE_ENTRIES);
            }
            public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                // This database is only a cache for online data, so its upgrade policy is
                // to simply to discard the data and start over
                db.execSQL(SQL_DELETE_ENTRIES);
                onCreate(db);
            }
            public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
                onUpgrade(db, oldVersion, newVersion);
            }
        }
    }
}
