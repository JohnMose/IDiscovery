package com.example.idiscovery12;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button btnInsert,btnUpdate,btnDelete,btnView;
    EditText txtname,txtlocation,txtdate,txtreport,txttime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtname=findViewById(R.id.name);
        txtlocation=findViewById(R.id.location);
        txtdate=findViewById(R.id.date);
        txttime=findViewById(R.id.time);
        txtreport=findViewById(R.id.report);

        btnInsert=findViewById(R.id.btnInsert);
        btnDelete=findViewById(R.id.btnDelete);
        btnUpdate=findViewById(R.id.btnUpdate);
        btnView=findViewById(R.id.btnView);

        DBHelper DB= new DBHelper(this);

        btnInsert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String name=txtname.getText().toString();
                String location=txtlocation.getText().toString();
                String date=txtdate.getText().toString();
                String time=txttime.getText().toString();
                String report=txtreport.getText().toString();
                if (name.isEmpty() || date.isEmpty() || report.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Required fields are missing!", Toast.LENGTH_SHORT).show();
                    return;
                }

                Boolean checkDBop=DB.insertUserData(name,location,date,time,report);
                if (checkDBop)
                {
                    Toast.makeText(MainActivity.this, "New Record Inserted Successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Unable to Insert Record", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //update onClick
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=txtname.getText().toString();
                String location=txtlocation.getText().toString();
                String date=txtdate.getText().toString();
                String time=txttime.getText().toString();
                String report=txtreport.getText().toString();

                Boolean checkupdate=DB.updateUserData(name,location,date,time,report);
                if (checkupdate==true)
                {
                    Toast.makeText(MainActivity.this, "Updated Successfully", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(MainActivity.this, "Unable to Update Record", Toast.LENGTH_SHORT).show();
                }
            }
        });


        //view record
        btnView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Cursor records =DB.getdata();
                if (records.getCount()==0)
                {
                    Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                    return;
                }
                StringBuffer allRecords=new StringBuffer();
                while(records.moveToNext()){
                    allRecords.append("Name :"+records.getString(0)+"\n");
                    allRecords.append("Location :"+records.getString(1)+"\n");
                    allRecords.append("Date :"+records.getString(2)+"\n");
                    allRecords.append("Time :"+records.getString(3)+"\n");
                    allRecords.append("reporter :"+records.getString(4)+"\n\n");
                }
                AlertDialog.Builder builder=new AlertDialog.Builder(MainActivity.this);
                builder.setCancelable(true);
                builder.setTitle("View all Records");
                builder.setMessage(allRecords.toString());
                builder.show();
            }
        });
        Button button = findViewById(R.id.searchbtn);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), User.class);
                startActivity(intent);
            }
        });

    }

    {

    }
}