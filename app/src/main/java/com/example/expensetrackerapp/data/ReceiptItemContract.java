package com.example.expensetrackerapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;

public class ReceiptItemContract {
    private ReceiptItemContract() {
    }

    public static class ReceiptItemEntry implements BaseColumns {
        public static final String TABLE_NAME = "receipt_item";
        public static final String COLUMN_NAME_RECEIPT_ID = "company_id";
        public static final String COLUMN_NAME_ITEM_NAME = "gst_amount";
        public static final String COLUMN_NAME_ITEM_DESCRIPTION = "service_tax_amount";
        public static final String COLUMN_NAME_ITEM_AMOUNT = "total_amount";
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
