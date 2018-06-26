package com.kithay;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import java.util.ArrayList;

public class Friend_History extends AppCompatActivity{

    private AppCompatActivity activity = Friend_History.this;
    Context context = Friend_History.this;
    private RecyclerView recyclerViewHistory;
    private ArrayList<HistoryBO> listHistory;
    private HistoryAdapter historyAdapter;
    private DBController mydb;
   // SearchView searchBox;
    //private ArrayList<HistoryBO> filteredList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend__history);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        initViews();
        initObjects();

    }

    private void initViews() {
        recyclerViewHistory = (RecyclerView) findViewById(R.id.recyclerViewHistory);
    }

    private void initObjects() {
        listHistory = new ArrayList<>();
        historyAdapter = new HistoryAdapter(listHistory, this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getApplicationContext());
        recyclerViewHistory.setLayoutManager(mLayoutManager);
        recyclerViewHistory.setItemAnimator(new DefaultItemAnimator());
        recyclerViewHistory.setHasFixedSize(true);
        recyclerViewHistory.setAdapter(historyAdapter);
        mydb = new DBController(activity);

        getDataFromDB();

    }

    private void getDataFromDB() {
        new AsyncTask<Void, Void, Void>() {
            @Override
            protected Void doInBackground(Void... params) {
                listHistory.clear();
                listHistory.addAll(mydb. getAllHistory());

                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                historyAdapter.notifyDataSetChanged();
            }
        }.execute();
    }
}

































//public class Friend_History extends AppCompatActivity {
//
//    ArrayList<HistoryBO> history;
//    HistoryAdapter adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_friend__history);
//
//        history=new ArrayList<HistoryBO>();
//
//        ListView lv=(ListView) findViewById(R.id.lvfriend);
//        adapter=new HistoryAdapter(getApplicationContext(),R.layout.friend_row,history);
//        lv.setAdapter(adapter);
//
//        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View arg1, int position,
//                                    long id) {
//                // TODO Auto-generated method stub
//                Toast.makeText(getApplicationContext(), history.get(position).getName(), Toast.LENGTH_LONG).show();
//
//            }
//        });
//    }
//
//    class HistoryAsynkTask extends AsyncTask<String,String,String>{
//
//        ProgressDialog dialog;
//
//        @Override
//        protected void onPreExecute() {
//            super.onPreExecute();
//            dialog = new ProgressDialog(getApplicationContext());
//            dialog.setMessage("Loading, please wait");
//            dialog.setTitle("Connecting Database");
//            dialog.show();
//            dialog.setCancelable(false);
//        }
//        @Override
//        protected String doInBackground(String... strings) {
//            return null;
//        }
//    }
//
//
//}
