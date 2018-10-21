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

import static com.example.oleg.myapplication6.DBHelper.TABLE_STUD;


public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    Button btnAdd, btnRead, btnClear,btnSort,btnFind;
    EditText etSurname, etGroup,etFac,etFind;

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

        btnSort=findViewById(R.id.btnSort);
        btnSort.setOnClickListener(this);

        btnFind=findViewById(R.id.btnFind);
        btnFind.setOnClickListener(this);

        etSurname = (EditText) findViewById(R.id.etSurname);
        etGroup = (EditText) findViewById(R.id.etGroup);
        etFac=findViewById(R.id.etFac);
        etFind=findViewById(R.id.etFind);

        dbHelper = new DBHelper(this);
    }

    @Override
    public void onClick(View v) {

        String surname = etSurname.getText().toString();
        String group = etGroup.getText().toString();
        String fac = etGroup.getText().toString();
        String find=etFind.getText().toString();




        SQLiteDatabase database = dbHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        Cursor c=null;


        switch (v.getId()) {

            case R.id.btnAdd:
                contentValues.put(DBHelper.KEY_SURNAME, surname);
                contentValues.put(DBHelper.KEY_GROUP, group);
                contentValues.put(DBHelper.KEY_FACULTY, fac);

                database.insert(TABLE_STUD, null, contentValues);
                break;

            case R.id.btnRead:
                Cursor cursor = database.query(TABLE_STUD, null, null, null, null, null, null);

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
                database.delete(TABLE_STUD, null, null);
                break;
            case R.id.btnSort:
                c=database.query(TABLE_STUD,null,null,null,null,null,"surname" );
                break;
            case R.id.btnFind:
                String [] selectionArgs=new String[]{find};
                c=database.query(TABLE_STUD,null,"faculty=?",selectionArgs,null,null,null );
                break;
        }
        if (c != null) {
            if (c.moveToFirst()) {
                String str;
                do {
                    str = "";
                    for (String cn : c.getColumnNames()) {
                        str = str.concat(cn + " = "
                                + c.getString(c.getColumnIndex(cn)) + "; ");
                    }
                    Log.d("mLog", str);

                } while (c.moveToNext());
            }
            c.close();
        } else
            Log.d("mLog", "Cursor is null");
        dbHelper.close();
    }
}


