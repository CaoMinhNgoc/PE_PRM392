package com.example.se1620_he161386_;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private EditText edt_id, edt_street, edt_city, edt_zipcode;
    private Button btn_add, btn_update, btn_delete, btn_list;
    private RecyclerView rv_result;
    private List<Address> addressList;
    private AddressOpenHelper openHelper;
    private AddressAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_id = findViewById(R.id.edt_id);
        edt_street = findViewById(R.id. edt_street);
        edt_city = findViewById(R.id.edt_city);
        edt_zipcode = findViewById(R.id.edt_zipcode);

        btn_add = findViewById(R.id.btn_add);
        btn_update = findViewById(R.id.btn_update);
        btn_delete = findViewById(R.id.btn_delete);
        btn_list = findViewById(R.id.btn_list);

        openHelper = new AddressOpenHelper(this);
        addressList = new ArrayList<>();

        rv_result = findViewById(R.id.rv_result);
        rv_result.setLayoutManager(new LinearLayoutManager(this));
        adapter = new AddressAdapter(addressList);
        rv_result.setAdapter(adapter);

        //Add address
        btn_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    int id = Integer.parseInt(edt_id.getText().toString().trim());
                    String street = edt_street.getText().toString().trim();
                    String city = edt_city.getText().toString().trim();
                    String zipcode = edt_zipcode.getText().toString().trim();
                    if(String.valueOf(id).isEmpty() || street.isEmpty() || city.isEmpty() || zipcode.isEmpty()){
                        Toast.makeText(MainActivity.this, "Empty field(s)", Toast.LENGTH_SHORT)
                             .show();
                    } else {
                        Address address = new Address(id, street, city, zipcode);
                        openHelper.add(address);
                        Toast.makeText(MainActivity.this, "Add successfully", Toast.LENGTH_SHORT)
                             .show();
                    }

                    addressList = openHelper.getAll();
                    rv_result.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    adapter = new AddressAdapter(addressList);
                    rv_result.setAdapter(adapter);
                    clearEditTextFields();
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        //List
        btn_list.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    String id = edt_id.getText().toString();
                    String street = edt_street.getText().toString();
                    String city = edt_city.getText().toString();
                    String zipcode = edt_zipcode.getText().toString();

                    if(id.isEmpty() && street.isEmpty() && city.isEmpty() && zipcode.isEmpty()){
                        addressList = openHelper.getAll();
                    } else {
                        ArrayList<String> searchTexts = new ArrayList<>();
                        searchTexts.add(id);
                        searchTexts.add(street);
                        searchTexts.add(city);
                        searchTexts.add(zipcode);

                        addressList = openHelper.search(searchTexts);
                    }

                    rv_result.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    adapter = new AddressAdapter(addressList);
                    rv_result.setAdapter(adapter);
                    clearEditTextFields();
                } catch (Exception e) {
                }
            }
        });

        //Update
        btn_update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int id = Integer.parseInt(edt_id.getText().toString().trim());
                    String street = edt_street.getText().toString().trim();
                    String city = edt_city.getText().toString().trim();
                    String zipcode = edt_zipcode.getText().toString().trim();

                    Address address = openHelper.getById(id);
                    address.setStreet(street);
                    address.setCity(city);
                    address.setZipcode(zipcode);

                    openHelper.update(address);

                    addressList = openHelper.getAll();
                    Toast.makeText(MainActivity.this, "Update successfully", Toast.LENGTH_SHORT).show();

                    addressList = openHelper.getAll();
                    rv_result.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    adapter = new AddressAdapter(addressList);
                    rv_result.setAdapter(adapter);
                } catch (Exception e) {
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        //Delete
        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try{
                    int id = Integer.parseInt(edt_id.getText().toString().trim());
                    Address address = openHelper.getById(id);
                    openHelper.delete(address);
                    Toast.makeText(MainActivity.this, "Delete successfully", Toast.LENGTH_SHORT).show();

                    addressList = openHelper.getAll();
                    rv_result.setLayoutManager(new LinearLayoutManager(MainActivity.this));
                    adapter = new AddressAdapter(addressList);
                    rv_result.setAdapter(adapter);
                    clearEditTextFields();
                } catch (Exception e){
                    Toast.makeText(MainActivity.this,e.getMessage(),Toast.LENGTH_SHORT).show();
                }
            }
        });

        addressList = openHelper.getAll();
        rv_result.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new AddressAdapter(addressList);
        rv_result.setAdapter(adapter);
    }

    private void clearEditTextFields() {
        edt_id.setText("");
        edt_street.setText("");
        edt_city.setText("");
        edt_zipcode.setText("");
    }
}