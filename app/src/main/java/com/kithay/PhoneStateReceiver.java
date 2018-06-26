package com.kithay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.telephony.SmsManager;
import android.telephony.TelephonyManager;
import android.widget.Toast;

/**
 * Created by Noman Sial on 6/20/2018.
 */

public class PhoneStateReceiver extends BroadcastReceiver {
    private static int lastState = TelephonyManager.CALL_STATE_IDLE;
    private static boolean isIncoming;
    public static String savedNumber;
    public static int savedState;

    @Override
    public void onReceive(Context context, Intent intent) {
            String stateStr = intent.getExtras().getString(TelephonyManager.EXTRA_STATE);
            String number = intent.getExtras().getString(TelephonyManager.EXTRA_INCOMING_NUMBER);
            int state = 0;
            if(stateStr.equals(TelephonyManager.EXTRA_STATE_IDLE)){
                state = TelephonyManager.CALL_STATE_IDLE;
            }
            else if(stateStr.equals(TelephonyManager.EXTRA_STATE_OFFHOOK)){
                state = TelephonyManager.CALL_STATE_OFFHOOK;
            }

            else if(stateStr.equals(TelephonyManager.EXTRA_STATE_RINGING)){
                state = TelephonyManager.CALL_STATE_RINGING;
            }

            onCallStateChanged(context, state, number);
            DBController dbController = new DBController(context);
            String address = dbController.getLastHistory().getAddress();
            sendSMS(context,number,address);
    }

    public void sendSMS(Context context,String phoneNo, String msg) {
        try {
            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(phoneNo, null, msg, null, null);
            Toast.makeText(context, "Message Sent",
                    Toast.LENGTH_LONG).show();
        } catch (Exception ex) {
            Toast.makeText(context,ex.getMessage(),
                    Toast.LENGTH_LONG).show();
            ex.printStackTrace();
        }
    }
    public void onCallStateChanged(Context context, int state, String number) {
        savedState=state;
        if(lastState == state){
            return;
        }
        switch (state) {
            case TelephonyManager.CALL_STATE_RINGING:
                isIncoming = true;
                savedNumber = number;

                Toast.makeText(context, "Incoming Call Ringing "+state , Toast.LENGTH_LONG).show();
                break;
            case TelephonyManager.CALL_STATE_OFFHOOK:
                if(lastState != TelephonyManager.CALL_STATE_RINGING){
                    isIncoming = false;
                    Toast.makeText(context, "Outgoing Call Started "+state , Toast.LENGTH_LONG).show();
                }

                break;
            case TelephonyManager.CALL_STATE_IDLE:
                if(lastState == TelephonyManager.CALL_STATE_RINGING){
                    Toast.makeText(context, "Ringing but no pickup "+state +" " + savedNumber  , Toast.LENGTH_LONG).show();
                }
                else if(isIncoming){

                    Toast.makeText(context, "Incoming "+state+" " + savedNumber  , Toast.LENGTH_LONG).show();
                }
                else{

                    Toast.makeText(context, "outgoing "+state+" " + savedNumber , Toast.LENGTH_LONG).show();

                }

                break;
        }

        lastState = state;
    }

}


















//    String number;
//
//    @Override
//    public void onReceive(Context context, Intent intent) {
//        String state = intent.getStringExtra(TelephonyManager.EXTRA_STATE);
//        String incomingNumber = intent.getStringExtra(TelephonyManager.EXTRA_INCOMING_NUMBER);
//
//        if(state.equals(TelephonyManager.EXTRA_STATE_RINGING)){
//            Toast.makeText(context,"Ringing State Number is -"+incomingNumber,Toast.LENGTH_SHORT).show();
//            number=incomingNumber;
//        }
//        if (state.equals(TelephonyManager.EXTRA_STATE_IDLE)){
//            Toast.makeText(context,"Idle State",Toast.LENGTH_SHORT).show();
//            number=incomingNumber;
//        }
//    }
//
//    public String getPhoneNumber(){
//        return this.number;
//    }