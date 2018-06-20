package com.julie.whowroteit;

import android.os.AsyncTask;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONObject;

/**
 * Created by JULIE on 6/19/2018.
 */

public class FetchBook extends AsyncTask<String, Void, String> {

    // to display the textViews,you need to access the textviews in the asyncTask
    private TextView authorTextView;
    private TextView titleTextView;


    //constructor;Initialize the values and use the constructor in your MainActivity to pass along the textViews to your asyctask
    public FetchBook(TextView authorTitleView, TextView titleTextView) {
        this.authorTextView = authorTitleView;
        this.titleTextView = titleTextView;
    }

    @Override
    protected String doInBackground(String... strings) {
        //calling the getBookInfo() in the networkUtils class
        return NetworkUtils.getBookInfo(strings[0]);
    }

    /*
    this method handles extracting the info you need to display in the UI
    as well as updating the UI
     */

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        //the doInBackground might not return the expected results thus you may
        // need to do the parsing in the try catch block which will handle incorrect or incomplete data
        try {
            //builtin java JSON classes to obtain JSON array of result items in try block
            JSONObject jsonObject = new JSONObject(s);
            JSONArray itemsArray = jsonObject.getJSONArray("items");
            //iterate through the itemsArray,checking each book for title and author ifo if both are not null,
            // exit the loop and update the UI
            for(int i = 0; i<itemsArray.length(); i++){
                JSONObject book = itemsArray.getJSONObject(i);
                String title=null;
                String authors=null;
                JSONObject volumeInfo = book.getJSONObject("volumeInfo");


                try {
                    title = volumeInfo.getString("title");
                    authors = volumeInfo.getString("authors");
                } catch (Exception e){
                    e.printStackTrace();
                }
                //setting the title text view and author text view to the values from the json results

                if (title != null && authors != null){
                    titleTextView.setText(title);
                    authorTextView.setText(authors);
                    return;
                }
            }


            titleTextView.setText("No Results Found");
            authorTextView.setText("");


        } catch (Exception e){
            titleTextView.setText("No Results Found");
            authorTextView.setText("");
            e.printStackTrace();
        }
        }
    }

