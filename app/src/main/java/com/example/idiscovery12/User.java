package com.example.idiscovery12;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.metrics.Event;
import android.os.Bundle;
import android.text.InputType;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

public class User extends AppCompatActivity {
  EditText nm;
  Button delete,search,add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user);
        nm = (EditText) findViewById(R.id.search);
        search = (Button) findViewById(R.id.buttonsearch);
        delete = (Button) findViewById(R.id.delete);
        add=(Button)findViewById(R.id.buttonsearch);
        DBHelper DB= new DBHelper(this);

        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showADDReportDialog();
            }
        });

        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=nm.getText().toString();
                Boolean checkdelete=DB.deleteUserData(name);
                if (checkdelete==true)
                {
                    Toast.makeText(User.this, "Record Deleted  Successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(User.this, "Unable to Delete Record", Toast.LENGTH_SHORT).show();
                }
            }
        });




    }


    private void showADDReportDialog() {
        final long activityId = 0;
        DBHelper DB= new DBHelper(this);
        AlertDialog.Builder builder=new AlertDialog.Builder(this);
        builder.setTitle("Add Report");
        final EditText input=new EditText(this);
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        builder.setPositiveButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String description=input.getText().toString();
                if (!TextUtils.isEmpty(description)){
                    DB.addReport(activityId,description);
                    Toast.makeText(User.this, "Report Added", Toast.LENGTH_SHORT).show();
                }else
                {
                    Toast.makeText(User.this, "Description cant be empty", Toast.LENGTH_SHORT).show();
                }
            }
        }).show();
    }
    private void getAllReportsForActivity(final long activityId) {
        DBHelper DB= new DBHelper(this);
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Report");

        List<String> report=DB.getAllReportsForActivity();
        if (report != null) {
            builder.setMessage((CharSequence) report);
        } else {
            builder.setMessage("No report available");
        }

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // do nothing
            }
        });

        builder.show();
    }




}
