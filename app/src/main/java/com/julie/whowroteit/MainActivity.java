package com.julie.whowroteit;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {
    private Button searchButton;
    private TextView authorTextView, titleTextView;
    private EditText inputBook;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        searchButton = (Button)findViewById(R.id.searchButton);
        authorTextView = (TextView) findViewById(R.id.authorTextView);
        titleTextView = (TextView)findViewById(R.id.titleTextView);
        inputBook = (EditText) findViewById(R.id.inputBook);

        searchButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                searchBooks();
            }
        });



    }

    private void searchBooks() {
        //convertig the string from EditText to a string and assigning it to a variable
        String queryString = inputBook.getText().toString();

        //hides the keyboard when the search button is pressed
        InputMethodManager inputManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        inputManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        //manage network state and empty search field case
        ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();


        if (networkInfo != null && networkInfo.isConnected() && queryString.length()!=0) {
            //launches the async task
            new FetchBook(titleTextView, authorTextView).execute(queryString);
            authorTextView.setText("");
            titleTextView.setText("Loading ........ !");


        }

        else {
            if (queryString.length() == 0) {
                authorTextView.setText("");
                titleTextView.setText("Please enter a search term");
            } else {
                authorTextView.setText("");
                titleTextView.setText("Please check your network connection and try again.");
            }
        }
    }
}
