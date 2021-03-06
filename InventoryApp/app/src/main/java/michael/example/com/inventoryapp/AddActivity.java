package michael.example.com.inventoryapp;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import michael.example.com.inventoryapp.data.CrudHelper;
import michael.example.com.inventoryapp.data.ProductInventoryDatabaseHelper;

public class AddActivity extends AppCompatActivity {
    private ProductInventoryDatabaseHelper dbHelper;
    private SQLiteDatabase database;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        dbHelper = new ProductInventoryDatabaseHelper(this);
        database = dbHelper.getReadableDatabase();
        final CrudHelper crudHelper = new CrudHelper(dbHelper);

        Button insertProduct = findViewById(R.id.insert_product);
        Button backToMain = findViewById(R.id.back_to_main);

        final EditText nameText = findViewById(R.id.product_name);
        final EditText quantityText = findViewById(R.id.product_quantity);
        final EditText priceText = findViewById(R.id.product_price);
        final EditText supplierNameText = findViewById(R.id.product_supplier_name);
        final EditText supplierPhoneNumberText = findViewById(R.id.product_supplier_phone_number);

        insertProduct.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String name = nameText.getText().toString();
                String quantity = quantityText.getText().toString();
                String price = priceText.getText().toString();
                String supplierName = supplierNameText.getText().toString();
                String supplierPhoneNumber = supplierPhoneNumberText.getText().toString();

                double parsedPrice;
                int parsedQuantity;

                if(name == null || name.length() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            getApplicationContext().getResources().getString(
                                    R.string.toast_invalid_product_name), Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                try {
                    parsedPrice = Double.parseDouble(price);

                    if (parsedPrice < 0.0) {
                        throw new Exception();
                    }
                } catch (Exception exception) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            getApplicationContext().getResources().getString(R.string.toast_invalid_price),
                            Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                try {
                    parsedQuantity = Integer.parseInt(quantity);

                    if (parsedQuantity < 0) {
                        throw new Exception();
                    }
                } catch (Exception exception) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            getApplicationContext().getResources().getString(R.string.toast_invalid_quantity),
                            Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                if(supplierName == null || supplierName.length() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            getApplicationContext().getResources().getString(R.string.toast_invalid_supplier_name),
                            Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                if(supplierPhoneNumber == null || supplierPhoneNumber.length() == 0) {
                    Toast toast = Toast.makeText(getApplicationContext(),
                            getApplicationContext().getResources().getString(R.string.toast_invalid_supplier_phone_number),
                            Toast.LENGTH_LONG);
                    toast.show();
                    return;
                }

                crudHelper.insertProduct(name, parsedPrice, parsedQuantity, supplierName, supplierPhoneNumber);

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });

        backToMain.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
            }
        });
    }

}
