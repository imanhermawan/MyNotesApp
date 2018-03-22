package id.iman.dicodingnotesapp;

import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static id.iman.dicodingnotesapp.DatabaseContract.CONTENT_URI;
import static id.iman.dicodingnotesapp.DatabaseContract.NoteColumns.DATE;
import static id.iman.dicodingnotesapp.DatabaseContract.NoteColumns.DESCRIPTION;
import static id.iman.dicodingnotesapp.DatabaseContract.NoteColumns.TITLE;

public class FormActivity extends AppCompatActivity implements View.OnClickListener {
    EditText edtTitle, edtDescription;
    Button btnSubmit;

    public static String EXTRA_NOTE_ITEM = "extra_note_item";
    private NoteItem noteItem = null;
    private boolean isUpdate = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form);

        edtTitle = (EditText)findViewById(R.id.edt_title);
        edtDescription = (EditText)findViewById(R.id.edt_description);
        btnSubmit = (Button)findViewById(R.id.btn_submit);
        btnSubmit.setOnClickListener(this);

        Uri uri = getIntent().getData();

        if (uri != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);

            if (cursor != null){

                if(cursor.moveToFirst()) noteItem = new NoteItem(cursor);
                cursor.close();
            }
        }

        String actionBarTitle = null;
        String btnActionTitle = null;
        if (noteItem != null){
            isUpdate = true;
            actionBarTitle = "Update";
            btnActionTitle = "Simpan";

            edtTitle.setText(noteItem.getTitle());
            edtDescription.setText(noteItem.getDescription());

        }else{
            actionBarTitle = "Tambah Baru";
            btnActionTitle = "Submit";
        }
        btnSubmit.setText(btnActionTitle);
        getSupportActionBar().setTitle(actionBarTitle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_submit){
            String title = edtTitle.getText().toString().trim();
            String description = edtDescription.getText().toString().trim();

            boolean isEmptyField = false;
            if (TextUtils.isEmpty(title)){
                isEmptyField = true;
                edtTitle.setError("Field tidak boleh kosong");
            }

            if (!isEmptyField){
                ContentValues mContentValues = new ContentValues();
                mContentValues.put(TITLE, title);
                mContentValues.put(DESCRIPTION, description);
                mContentValues.put(DATE, getCurrentDate());

                if (isUpdate){
                    Uri uri = getIntent().getData();
                    getContentResolver().update(uri, mContentValues, null, null);

                    Toast.makeText(this, "Satu catatan berhasil diupdate", Toast.LENGTH_SHORT).show();
                }else{
                    getContentResolver().insert(CONTENT_URI, mContentValues);

                    Toast.makeText(this, "Satu catatan berhasil diinputkan", Toast.LENGTH_SHORT).show();
                }

                finish();

            }
        }
    }

    private String getCurrentDate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
}
