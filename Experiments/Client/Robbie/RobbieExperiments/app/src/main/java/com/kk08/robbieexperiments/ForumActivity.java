package com.kk08.robbieexperiments;

import android.os.StrictMode;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.kk08.robbieexperiments.helpers.DatabaseAdapter;
import com.kk08.robbieexperiments.helpers.Message;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class ForumActivity extends AppCompatActivity {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    private TabItem buyTab;
    private TabItem sellTab;
    private TabItem dataTab;
    public PageAdapter pageAdapter;
    public EditText editAuthor;
    public EditText editQuote;
    public EditText editDelete;
    public TextView httpTextData;

    private static DatabaseAdapter helper;
    private static Context context;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        tabLayout = (TabLayout)findViewById(R.id.tabLayout);
        buyTab = (TabItem)findViewById(R.id.buyPage);
        sellTab = (TabItem)findViewById(R.id.sellPage);
        dataTab = (TabItem)findViewById(R.id.dataPage);
        viewPager = (ViewPager)findViewById(R.id.viewpager);

        helper = new DatabaseAdapter(this.getApplicationContext());
        context = this.getApplicationContext();

        pageAdapter = new PageAdapter(getSupportFragmentManager(), 0, tabLayout.getTabCount());
        viewPager.setAdapter(pageAdapter);


        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                viewPager.setCurrentItem(tab.getPosition());
                if (tab.getPosition() == 0) {
                    pageAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 1) {
                    pageAdapter.notifyDataSetChanged();
                } else if (tab.getPosition() == 2) {
                    pageAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
    }

    public void addData(View view) {
        editAuthor = (EditText)findViewById(R.id.editTextAuthor);
        editQuote = (EditText)findViewById(R.id.editTextQuote);

        String author = editAuthor.getText().toString();
        String quote = editQuote.getText().toString();

        // Validate the input is not empty
        if (author.isEmpty() || quote.isEmpty()) {
            Message.message(view.getContext().getApplicationContext(), "Please enter both an Author and Quote!");
        } else {
            long id = helper.insertData(author, quote);

            if (id <= 0) {
                Message.message(view.getContext().getApplicationContext(), "Insertion Failed!");
            } else {
                Message.message(view.getContext().getApplicationContext(), "Insertion Successful!");
            }

            editAuthor.setText("");
            editQuote.setText("");
        }
    }

    // Uses the DatabaseHelper to retrieve the data and displays it in a toast message
    public static String viewData(View view) {
        String data = helper.getData();
        return data;
    }

    public void deleteData(View view) {
        editDelete = (EditText)findViewById(R.id.editTextDelete);

        String author = editDelete.getText().toString();

        if (author.isEmpty()) {
            Message.message(this.getApplicationContext(), "Please enter an author to delete!");
        } else {
            long id = helper.delete(author);

            if (id <= 0) {
                Message.message(this.getApplicationContext(), "Deletion Failed!");
            } else {
                Message.message(this.getApplicationContext(), "Author DELETED!");
            }

            editDelete.setText("");
        }
    }

    public void getHttpData(View view) throws Exception {
        httpTextData = (TextView)findViewById(R.id.httpTextView);
        StringBuilder textBuilder = new StringBuilder();

        URL url = new URL("http://192.168.1.31:8080/api/v1/animal");
        HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();

        try {
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());

            Reader reader = new BufferedReader(new InputStreamReader(in, Charset.forName(StandardCharsets.UTF_8.name())));

            int count = 0;

            while ((count = reader.read()) != -1) {
                textBuilder.append((char) count);
            }

        } finally {
            urlConnection.disconnect();
        }

        httpTextData.setText(textBuilder.toString());

    }

    public void onClick(View view) throws Exception {
        switch (view.getId()) {
            case R.id.addButton:
                addData(view);
                break;
            case R.id.viewButton:
                String data = viewData(view);
                Message.message(getApplicationContext(), data);
                break;
            case R.id.deleteButton:
                deleteData(view);
            case R.id.httpGetButton:
                getHttpData(view);
        }
    }
}