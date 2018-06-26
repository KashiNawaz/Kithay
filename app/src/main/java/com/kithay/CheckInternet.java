package com.kithay;

import android.app.Service;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.widget.Toast;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

/**
 * Created by Noman Sial on 6/24/2018.
 */

public class CheckInternet extends AsyncTask<String,Void,Integer>{
    Context context;
    String type;

    public CheckInternet(Context context) {
        this.context=context;
    }



    public  boolean isOnline()
    {
        ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Service.CONNECTIVITY_SERVICE);

        if (connectivityManager!=null)
        {
            NetworkInfo info=connectivityManager.getActiveNetworkInfo();
            if (info!=null)
            {
                type=info.getTypeName();
                if (info.getState()==NetworkInfo.State.CONNECTED)
                {
                    return true;
                }
            }
        }
        return false;
    }



    @Override
    protected Integer doInBackground(String... params) {

        Integer result=0;
        try {
            Socket socket=new Socket();
            SocketAddress socketAddress=new InetSocketAddress("8.8.8.8",53);
            socket.connect(socketAddress,1500);
            socket.close();
            result=1;
        } catch (IOException e) {
            e.printStackTrace();
            result=0;
        }

        return result;
    }

    @Override
    protected void onPostExecute(Integer result) {
        if (isOnline())
        {
            if (result==1)
            {
                Toast.makeText(context, "  internet available "+"Type: "+type, Toast.LENGTH_SHORT).show();
            }

            if(result==0)
            {
                Toast.makeText(context, " No internet available ", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
            Toast.makeText(context, " No internet available ", Toast.LENGTH_SHORT).show();
        }
        super.onPostExecute(result);
    }
}

