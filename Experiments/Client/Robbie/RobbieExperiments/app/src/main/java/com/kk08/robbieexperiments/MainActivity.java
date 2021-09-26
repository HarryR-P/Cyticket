package com.kk08.robbieexperiments;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.kk08.robbieexperiments.helpers.DatabaseAdapter;
import com.kk08.robbieexperiments.helpers.Message;

public class MainActivity extends AppCompatActivity {

    DatabaseAdapter helper;

    Button addButton;
    Button viewButton;

    EditText editAuthor;
    EditText editQuote;
    EditText editDelete;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addButton = findViewById(R.id.addButton);
        viewButton = findViewById(R.id.viewButton);
        editAuthor = findViewById(R.id.editTextAuthor);
        editQuote = findViewById(R.id.editTextQuote);
        editDelete = findViewById(R.id.editTextDelete);

        helper = new DatabaseAdapter(this);
    }


    public void addData(View view) {
        String author = editAuthor.getText().toString();
        String quote = editQuote.getText().toString();

        // Validate the input is not empty
        if (author.isEmpty() || quote.isEmpty()) {
            Message.message(getApplicationContext(), "Please enter both an Author and Quote!");
        } else {
            long id = helper.insertData(author, quote);

            if (id <= 0) {
                Message.message(getApplicationContext(), "Insertion Failed!");
            } else {
                Message.message(getApplicationContext(), "Insertion Successful!");
            }

            editAuthor.setText("");
            editQuote.setText("");
        }
    }

    // Uses the DatabaseHelper to retrieve the data and displays it in a toast message
    public void viewData(View view) {
        String data = helper.getData();
        Message.message(this, data);
    }

    public void deleteData(View view) {
        String author = editDelete.getText().toString();

        if (author.isEmpty()) {
            Message.message(this, "Please enter an author to delete!");
        } else {
            long id = helper.delete(author);

            if (id <= 0) {
                Message.message(this, "Deletion Failed!");
            } else {
                Message.message(this, "Author DELETED!");
            }

            editDelete.setText("");
        }
    }
}