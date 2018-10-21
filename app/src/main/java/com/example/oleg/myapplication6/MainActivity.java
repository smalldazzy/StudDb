package com.example.oleg.myapplication6;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnAdd, btnRead, btnClear;
    EditText etSurname, etGroup,etFac;

    DBHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnAdd = (Button) findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(this);

        btnRead = (Button) findViewById(R.id.btnRead);
        btnRead.setOnClickListener(this);

        btnClear = (Button) findViewById(R.id.btnClear);
        btnClear.setOnClickListener(this);

        etSurname = (EditText) findViewById(R.id.etSurname);
        etGroup = (EditText) findViewById(R.id.etGroup);
        etFac=findViewById(R.id.etFac);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {

        String surname = etSurname.getText().toString();
        String group = etGroup.getText().toString();
        String fac = etGroup.getText().toString();



        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();


        switch (v.getId()) {

            case R.id.btnAdd:
                contentValues.put(DBHelper.KEY_SURNAME, surname);
                contentValues.put(DBHelper.KEY_GROUP, group);
                contentValues.put(DBHelper.KEY_FACULTY, fac);

                database.insert(DBHelper.TABLE_STUD, null, contentValues);
                break;

            case R.id.btnRead:
                Cursor cursor = database.query(DBHelper.TABLE_STUD, null, null, null, null, null, null);

                if (cursor.moveToFirst()) {
                    int idIndex = cursor.getColumnIndex(DBHelper.KEY_ID);
                    int surnameIndex = cursor.getColumnIndex(DBHelper.KEY_SURNAME);
                    int groupIndex = cursor.getColumnIndex(DBHelper.KEY_GROUP);
                    int facIndex = cursor.getColumnIndex(DBHelper.KEY_FACULTY);
                    do {
                        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                        builder.setTitle("Записи")
                                .setMessage("ID = " + cursor.getInt(idIndex) +
                                        ", surname = " + cursor.getString(surnameIndex) +
                                        ", group = " + cursor.getString(groupIndex) +
                                        ", faculty = " + cursor.getString(facIndex))
                                .setCancelable(false)
                                .setNegativeButton("previous",
                                        new DialogInterface.OnClickListener() {
                                            public void onClick(DialogInterface dialog, int id) {
                                                dialog.cancel();
                                            }
                                        });
                        AlertDialog alert = builder.create();
                        alert.show();

                        Log.d("mLog", "ID = " + cursor.getInt(idIndex) +
                                ", surname = " + cursor.getString(surnameIndex) +
                                ", group = " + cursor.getString(groupIndex) +
                                ", faculty = " + cursor.getString(facIndex));
                    } while (cursor.moveToNext());
                } else
                    Log.d("mLog","0 rows");

                cursor.close();
                break;

            case R.id.btnClear:
                database.delete(DBHelper.TABLE_STUD, null, null);
                break;
        }
        dbHelper.close();
    }
}


