package com.kiplening.demo.service;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import java.util.ArrayList;

/**
 * Created by MOON on 1/23/2016.
 */
public class Receiver extends BroadcastReceiver {
    static private ArrayList<String> lockList = new ArrayList<String>();
    static private Context mainContext ;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (mainContext == null){
            lockList = (ArrayList<String>) intent.getStringArrayListExtra("lockList").clone();
            this.mainContext = context;
        }
        if (intent.getAction().equals("android.intent.action.SET_BROADCAST")){
            System.out.println("checkbox broadcast is working.");
            if(intent.getStringExtra("status").equals("true")){
                System.out.println("checkbox broadcast is true.");
                Intent servIntent = new Intent(context, LockService.class);
                servIntent.putStringArrayListExtra("lockList", lockList);
                servIntent.putExtra("status","true");
                mainContext.startService(servIntent);
            }else if (intent.getStringExtra("status").equals("false")){
                System.out.println("checkbox broadcast is false.");
                Intent servIntent = new Intent(context, LockService.class);
                servIntent.putStringArrayListExtra("lockList", lockList);
                servIntent.putExtra("status","false");
                mainContext.startService(servIntent);
            }
            return;
        }
        if (intent.getAction().equals("android.intent.action.MAIN_BROADCAST")){
            //this.lockList = intent.getStringArrayListExtra("lockList");
            if (intent.getStringExtra("status").equals("false")){
                Intent servIntent = new Intent(context, LockService.class);
                servIntent.putStringArrayListExtra("lockList", lockList);
                servIntent.putExtra("status","false");
                mainContext.startService(servIntent);
                return;
            }else {
                Intent servIntent = new Intent(context, LockService.class);
                servIntent.putStringArrayListExtra("lockList", lockList);
                servIntent.putExtra("status","true");
                mainContext.startService(servIntent);
                return;
            }

        }
        System.out.print("don't work");
    }
}
