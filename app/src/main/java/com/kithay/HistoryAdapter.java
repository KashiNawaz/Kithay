package com.kithay;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

import static com.kithay.History.DEFAULT;

/**
 * Created by Noman Sial on 6/23/2018.
 */

public class HistoryAdapter extends RecyclerView.Adapter<HistoryAdapter.HistoryViewHolder>  {

    private ArrayList<HistoryBO> listHistory;
    private Context mContext;
    private ArrayList<HistoryBO> mFilteredList;


    public HistoryAdapter(ArrayList<HistoryBO> listHistory, Context mContext) {
        this.listHistory = listHistory;
        this.mContext = mContext;
        this.mFilteredList = listHistory;


    }

    public class HistoryViewHolder extends RecyclerView.ViewHolder {

        public TextView textViewDate;
        public TextView textViewTime;
        public TextView textViewAddress;
        public TextView textViewBattery;
        public  Button btnMapView;

        public HistoryViewHolder(View view) {
            super(view);
            textViewAddress = (TextView) view.findViewById(R.id.tvFrndAddress);
            textViewBattery = (TextView) view.findViewById(R.id.tvBattery);
            textViewDate = (TextView) view.findViewById(R.id.tvDate);
            textViewTime = (TextView) view.findViewById(R.id.tvTime);
            btnMapView=(Button)  view.findViewById(R.id.btnmapView);


        }
    }

    private void showLocationOnMap(String latPref, String langPref) {
        try {
            String uri = String.format(Locale.ENGLISH, "geo:%f,%f", Float.parseFloat(latPref), Float.parseFloat(langPref));
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
            mContext.startActivity(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public HistoryViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.friend_row, parent, false);





        return new HistoryViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull final HistoryViewHolder holder, final int position) {

        holder.textViewDate.setText(listHistory.get(position).getDate());
        holder.textViewTime.setText(listHistory.get(position).gettime());
        holder.textViewAddress.setText(listHistory.get(position).getAddress());
        holder.textViewBattery.setText("Battery : "+listHistory.get(position).getBattery());

        holder.btnMapView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showLocationOnMap(listHistory.get(position).getLat(),listHistory.get(position).getLang());
            }
        });
//        holder.btnMapView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    String latPref=listHistory.get(position).getLat();
//                    String langPref=listHistory.get(position).getLang();
//                    String coord="geo:"+latPref+","+langPref;
//                    Uri gmmIntentUri = Uri.parse(coord);
//                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
//                    mapIntent.setPackage("com.google.android.apps.maps");
//
//                    Log.d("Map Button"," Number: "+ position +" Latitude: "+ latPref+" Number: "+ position +" Longitude: "+ langPref);
//              //      Toast.makeText(this,"Number: "+ position +" Lat: "+ latPref+"Number: "+ position +" Lat: "+ langPref,Toast.LENGTH_LONG).show();
////                    if (mapIntent.resolveActivity(getPackageManager()) != null) {
////                        startActivity(mapIntent);
////                    }
//                }
//        });
    }


    @Override
    public int getItemCount() {
        return mFilteredList.size();
    }



}




























//public class HistoryAdapter extends ArrayAdapter<HistoryBO> {
//    ArrayList<HistoryBO> history;
//    //HistoryBO historyBO;
//    DBController mydb;
//    LayoutInflater li;
//    int Resource;
//    ViewHolder holder;
//
//    public HistoryAdapter(@NonNull Context context, int resource, ArrayList<HistoryBO> objects) {
//        super(context, resource);
//        li=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        Resource=resource;
//        history=objects;
//        mydb=new DBController(context);
//    }
//
//    @Override
//    public View getView(int position, View convertView, ViewGroup parent) {
//
//        View v=convertView;
//        if(v==null)
//        {
//            holder=new ViewHolder();
//            v=li.inflate(Resource,null);
//            holder.txtAddress=(TextView) v.findViewById(R.id.tvFrndAddress);
//            holder.txtBattery=(TextView) v.findViewById(R.id.tvBattery);
//            holder.txtDate=(TextView) v.findViewById(R.id.tvDate);
//            holder.txtTime=(TextView) v.findViewById(R.id.tvTime);
//            holder.btnMapView=(Button) v.findViewById(R.id.btnmapView);
//
//            v.setTag(holder);
//        }
//        else {
//            holder=(ViewHolder) v.getTag();
//        }
//
//        //set Data
//        history= new ArrayList<HistoryBO>();
//        history=mydb.getAllHistory();
//        holder.txtAddress.setText(history.get(position).getAddress());
//        holder.txtBattery.setText("Battery: " + history.get(position).getBattery());
//        holder.txtDate.setText("Date: " + history.get(position).getDate());
//        holder.txtTime.setText("Time: " + history.get(position).gettime());
//
//        return v;
//    }
//
//    static class ViewHolder {
//        public Button btnMapView;
//        public TextView txtAddress;
//        public TextView txtBattery;
//        public TextView txtDate;
//        public TextView txtTime;
//    }
//}
