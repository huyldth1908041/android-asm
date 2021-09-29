package com.t1908e.sqllitedemo.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CursorAdapter;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.t1908e.sqllitedemo.R;
import com.t1908e.sqllitedemo.adapter.CategoryAdapter;
import com.t1908e.sqllitedemo.db.DBHelper;
import com.t1908e.sqllitedemo.entity.Category;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText edName;
    private EditText edDescription;
    private EditText edDetail;
    private EditText edAmount;
    Spinner spinner;
    private Button btnAdd;
    private DBHelper dbHelper;
    SimpleCursorAdapter simpleCursorAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        edName = findViewById(R.id.edPaymentName);
        edDescription = findViewById(R.id.edDescription);
        edDetail = findViewById(R.id.edDetail);
        edAmount = findViewById(R.id.editPaymentAmount);
        btnAdd = findViewById(R.id.btnAdd);
        spinner = findViewById(R.id.spinner);
        dbHelper = new DBHelper(this);
        ArrayList<Category> cats = new ArrayList<>();
        Cursor cursor = dbHelper.getAllCategories();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(cursor.getColumnIndex(dbHelper.ID));
            String name = cursor.getString(cursor.getColumnIndex(dbHelper.NAME));
            cats.add(new Category(id, name));
        }
        CategoryAdapter categoryAdapter = new CategoryAdapter(cats, this);
        spinner.setAdapter(categoryAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        btnAdd.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnAdd:
                addPayment();
                break;
            default:
                break;
        }

    }

    private void addPayment() {
        //Log.d("TAG", spinner.getSelectedItem().toString());
        String amountString = edAmount.getText().toString();
        double amount = 0;
        try {
            amount = Double.parseDouble(amountString);

        } catch (Exception exception) {
            amount = 0;
        }
        if (amount <= 0) {
            Toast.makeText(this, "Plz enter a valid amount", Toast.LENGTH_SHORT).show();
            return;
        }
        String s = dbHelper.addPayment(edName.getText().toString(), 1, edDetail.getText().toString(), 1, edDescription.getText().toString());
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ListUserActivity.class);
        startActivity(intent);

    }


}