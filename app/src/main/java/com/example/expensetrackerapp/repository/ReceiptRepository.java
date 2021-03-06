package com.example.expensetrackerapp.repository;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

import com.example.expensetrackerapp.data.ReceiptContract;
import com.example.expensetrackerapp.list_view.placeholder.PlaceholderContent;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class ReceiptRepository {

    private final ReceiptContract.ReceiptEntry.ReceiptDbHelper dbHelper;
    private List<ReceiptEntity> receiptEntities;
    private boolean isRetrieved;

    public ReceiptRepository(ReceiptContract.ReceiptEntry.ReceiptDbHelper dbHelper) {
        this.dbHelper = dbHelper;
        receiptEntities = new ArrayList<>();
        isRetrieved = false;
    }

    public void insert(ReceiptEntity receiptEntity) {
        System.out.println("Performing insertion for " + receiptEntity);
        // Gets the data repository in write mode
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        // Create a new map of values, where column names are the keys
        ContentValues values = new ContentValues();
        values.put(ReceiptContract.ReceiptEntry.COLUMN_NAME_INVOICE_ID, receiptEntity.getInvoiceId());
        values.put(ReceiptContract.ReceiptEntry.COLUMN_NAME_COMPANY_ID, receiptEntity.getCompanyId());
        values.put(ReceiptContract.ReceiptEntry.COLUMN_NAME_TOTAL_AMOUNT, receiptEntity.getTotalAmount());
        values.put(ReceiptContract.ReceiptEntry.COLUMN_NAME_CATEGORY, receiptEntity.getCategory());
        values.put(ReceiptContract.ReceiptEntry.COLUMN_NAME_LAST_UPDATED_TIME, receiptEntity.getLastUpdatedTime());
        values.put(ReceiptContract.ReceiptEntry.COLUMN_NAME_DESCRIPTION, receiptEntity.getDescription());


        // Insert the new row, returning the primary key value of the new row
        long newRowId = db.insert(ReceiptContract.ReceiptEntry.TABLE_NAME, null, values);

        System.out.println("Receipt with id " + newRowId + " has been added");
    }

    public List<ReceiptEntity> retrieve() {
        if (isRetrieved) return receiptEntities;
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        // Define a projection that specifies which columns from the database
        // you will actually use after this query.
        String[] projection = {
                BaseColumns._ID,
                ReceiptContract.ReceiptEntry.COLUMN_NAME_INVOICE_ID,
                ReceiptContract.ReceiptEntry.COLUMN_NAME_COMPANY_ID,
                ReceiptContract.ReceiptEntry.COLUMN_NAME_TOTAL_AMOUNT,
                ReceiptContract.ReceiptEntry.COLUMN_NAME_CATEGORY,
                ReceiptContract.ReceiptEntry.COLUMN_NAME_LAST_UPDATED_TIME,
                ReceiptContract.ReceiptEntry.COLUMN_NAME_DESCRIPTION
        };

        Cursor cursor = db.query(
                ReceiptContract.ReceiptEntry.TABLE_NAME,   // The table to query
                projection,             // The array of columns to return (pass null to get all)
                null,              // The columns for the WHERE clause
                null,          // The values for the WHERE clause
                null,                   // don't group the rows
                null,                   // don't filter by row groups
                null               // The sort order
        );

        List<ReceiptEntity> receiptEntities = new ArrayList<>();
        while (cursor.moveToNext()) {
            String category = cursor.getString(cursor.getColumnIndexOrThrow(ReceiptContract.ReceiptEntry.COLUMN_NAME_CATEGORY));
            int companyId = cursor.getInt(cursor.getColumnIndexOrThrow(ReceiptContract.ReceiptEntry.COLUMN_NAME_COMPANY_ID));
            int totalAmount = cursor.getInt(cursor.getColumnIndexOrThrow(ReceiptContract.ReceiptEntry.COLUMN_NAME_TOTAL_AMOUNT));
            String invoiceId = cursor.getString(cursor.getColumnIndexOrThrow(ReceiptContract.ReceiptEntry.COLUMN_NAME_INVOICE_ID));
            String lastUpdatedTime = cursor.getString(cursor.getColumnIndexOrThrow(ReceiptContract.ReceiptEntry.COLUMN_NAME_INVOICE_ID));
            String description = cursor.getString(cursor.getColumnIndexOrThrow(ReceiptContract.ReceiptEntry.COLUMN_NAME_DESCRIPTION));
            int id = cursor.getInt(cursor.getColumnIndexOrThrow(ReceiptContract.ReceiptEntry._ID));

            ReceiptEntity receiptEntity = new ReceiptEntity(invoiceId, companyId, totalAmount, category, lastUpdatedTime, description);
            receiptEntity.setId(id);
            receiptEntities.add(receiptEntity);
        }
        cursor.close();
        this.receiptEntities = receiptEntities;
        return receiptEntities;
    }

    public List<PlaceholderContent.PlaceholderItem> retrievePlaceHolderItems() {
        System.out.println("Going to retrievePlaceHolderItems");
        List<ReceiptEntity> receiptEntities = retrieve();
        System.out.println("Found to receipts {}" + receiptEntities.size());
        List<PlaceholderContent.PlaceholderItem> placeholderItems = new ArrayList<>();
        for (ReceiptEntity receiptEntity : receiptEntities) {
            System.out.println("Receupt found " + receiptEntity);
            PlaceholderContent.PlaceholderItem placeholderItem = map(receiptEntity);
            placeholderItems.add(placeholderItem);
        }
        System.out.println("Processed receipts {}" + placeholderItems.size());
        System.out.println("Processed receipts {}" + placeholderItems);
        return placeholderItems;
    }

    public Optional<ReceiptEntity> retrieveReceipt(String id) {
        if (id == null) return Optional.empty();
        System.out.println("retrieveReceipt:: " + id);
        int idInteger = -1;
        try {
            idInteger = Integer.parseInt(id);
        } catch (NumberFormatException ex) {
            return Optional.empty();
        }

        List<ReceiptEntity> receiptEntities = retrieve();

        if (receiptEntities.size() > 0) {
            int finalIdInteger = idInteger;
            return receiptEntities.stream().filter(receiptEntity -> receiptEntity.getId() == finalIdInteger)
                    .findFirst();
        } else {
            return Optional.empty();
        }
    }

    public Optional<PlaceholderContent.PlaceholderItem> retrievePlaceholderItem(String id) {
        System.out.println("retrievePlaceholderItem:: " + id);
        return retrieveReceipt(id).map(this::map);
    }

    private PlaceholderContent.PlaceholderItem map(ReceiptEntity receiptEntity) {
        return new PlaceholderContent.PlaceholderItem(
                String.valueOf(receiptEntity.getId()),
                receiptEntity.getDescription(),
                "Category: " + receiptEntity.getCategory()
                        + " \nInvoice Number: " + receiptEntity.getInvoiceId());
    }
}