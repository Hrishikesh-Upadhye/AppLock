package com.deva.applock;

import android.app.ActivityManager;
import android.app.IntentService;
import android.app.Service;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.R.attr.tag;

/**
 * Created by HP on 05/01/2017.
 */


public class MyService extends Service
{
    private static Timer timer = new Timer();
    public Boolean userAuth = false;
    private Context ctx;
    public String pActivity="";
    String tag="TestService";
    ArrayList<String> apps = new ArrayList<String>();




    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        ctx = this;
        startService();
    }

    private void startService()
    {
        timer.scheduleAtFixedRate(new mainTask(), 0, 500);
    }

    private class mainTask extends TimerTask
    {
        public void run()
        {
            toastHandler.sendEmptyMessage(0);
        }
    }

    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Log.i(tag, "Service started...");
        Toast.makeText(this, "Service Started ...", Toast.LENGTH_SHORT).show();
    }

    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "Service Stopped ...", Toast.LENGTH_SHORT).show();
        startService(new Intent(this, MyService.class));
    }

    private final Handler toastHandler = new Handler()
    {

        @Override
        public void handleMessage(Message msg)
        {
            String activityOnTop;
            CharSequence Char = "com.whatsapp";
            CharSequence Char1 = "com.deva.applock.MainActivity";

            PackageManager packageManager = getPackageManager();
            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> appList = packageManager.queryIntentActivities(mainIntent, 0);
            Collections.sort(appList, new ResolveInfo.DisplayNameComparator(packageManager));
            List<PackageInfo> packs = packageManager.getInstalledPackages(0);
            for(int i=0; i < packs.size(); i++) {
                PackageInfo p = packs.get(i);
                ApplicationInfo a = p.applicationInfo;
                // skip system apps if they shall not be included
                if((a.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                    continue;
                }
                apps.add(p.packageName);
            }

            ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager.getRunningTasks(1);
    //        ActivityManager.RunningTaskInfo ar = RunningTask.get(0);
            Log.d("topActivity", "CURRENT Activity ::" + RunningTask.get(0).topActivity.getClassName());
            ComponentName componentInfo = RunningTask.get(0).topActivity;
            activityOnTop = componentInfo.getPackageName();
//            activityOnTop=ar.topActivity.getClassName();

//             for (Object object : apps) {
//                 Log.i(tag, "Service started..."+ activityOnTop);

// Provide the packagename(s) of apps here, you want to show password activity
                if ((activityOnTop.contains(Char)) || (activityOnTop.contains(Char1)))
                //if (activityOnTop.contains("com.whatsapp"))

                {
                    Intent i = new Intent(com.deva.applock.MyService.this, com.deva.applock.LockScreen.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    Toast.makeText(MyService.this, pActivity, Toast.LENGTH_SHORT).show();
                    pActivity = activityOnTop.toString();
                }


//            }



//            if(activityOnTop.equals("com.deva.applock.MainActivity"))
//            {
//                pActivity = activityOnTop.toString();
//            }
//            else
//            {
//                if(activityOnTop.equals(pActivity) || activityOnTop.equals("com.whatsapp.Main"))
//                {
//
//                    Toast.makeText(MyService.this, pActivity, Toast.LENGTH_SHORT).show();
//                    pActivity = activityOnTop.toString();
//
//                }
//                else
//                {
//                    if (activityOnTop.equals("com.whatsapp.Main")) {
//                        Intent i = new Intent(MyService.this, MainActivity.class);
//                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                        startActivity(i);
//                        Toast.makeText(MyService.this, pActivity, Toast.LENGTH_SHORT).show();
//                        pActivity = activityOnTop.toString();
//
//                    }
//                }
//            }


//         if(activityOnTop.equals("com.watsapp.Main"))
//         {
//
//           Intent i = new Intent(MyService.this, MainActivity.class);
//             i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//              startActivity(i);
//              Toast.makeText(MyService.this, pActivity, Toast.LENGTH_SHORT).show();
//
//          }
//         else
//         {
//     //     Toast.makeText(MyService.this, "Hi", Toast.LENGTH_SHORT).show();
//         }



        }
    };
}

/*
public class MyService extends IntentService{

    private static Timer timer = new Timer();
    public Boolean userAuth = false;
    private Context ctx;
    public String pActivity="";
    ArrayList<String> apps = new ArrayList<String>();
    String tag="TestService";

    //String cs = "whatsapp";

    CharSequence Char = (CharSequence) "com.whatsapp";
    @Override
    public IBinder onBind(Intent arg0)
    {
        return null;
    }

    @Override
    public void onCreate()
    {
        super.onCreate();
        Toast.makeText(this, "Service Started ...", Toast.LENGTH_SHORT).show();

        ctx = this;
        startService();

    }

    private void startService()
    {
        timer.scheduleAtFixedRate(new mainTask(), 0, 500);
    }

    private class mainTask extends TimerTask
    {
        Intent i = new Intent();
        public void run()
        {
            onHandleIntent(i);
        }
    }


    public MyService() {
        super(MyService.class.getName());
    }


    @Override
    public void onStart(Intent intent, int startId) {
        super.onStart(intent, startId);
        Toast.makeText(this, "Service Started ...", Toast.LENGTH_SHORT).show();
        Log.i(tag, "Service started...");
    }

    public void onDestroy()
    {
        super.onDestroy();
        Toast.makeText(this, "Service Stopped ...", Toast.LENGTH_SHORT).show();
 //       startService(new Intent(this, MyService.class));
    }


        @Override
        protected void onHandleIntent(Intent intent) {

            String activityOnTop;

            PackageManager packageManager = getPackageManager();
            Intent mainIntent = new Intent(Intent.ACTION_MAIN, null);
            mainIntent.addCategory(Intent.CATEGORY_LAUNCHER);

            List<ResolveInfo> appList = packageManager.queryIntentActivities(mainIntent, 0);
            Collections.sort(appList, new ResolveInfo.DisplayNameComparator(packageManager));
            List<PackageInfo> packs = packageManager.getInstalledPackages(0);
            for (int i = 0; i < packs.size(); i++) {
                PackageInfo p = packs.get(i);
                ApplicationInfo a = p.applicationInfo;
                // skip system apps if they shall not be included
                if ((a.flags & ApplicationInfo.FLAG_SYSTEM) == 1) {
                    continue;
                }
                apps.add(p.packageName);
            }

            ActivityManager mActivityManager = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
            List<ActivityManager.RunningTaskInfo> RunningTask = mActivityManager.getRunningTasks(1);
            ActivityManager.RunningTaskInfo ar = RunningTask.get(0);
            activityOnTop = ar.topActivity.getClassName();

            for (Object object : apps) {

// Provide the packagename(s) of apps here, you want to show password activity
                if ((activityOnTop.contains(Char)) || (activityOnTop.equals("com.deva.applock")))
                //if (activityOnTop.contains("com.whatsapp"))

                {
                    Intent i = new Intent(MyService.this, LockScreen.class);
                    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(i);
                    Toast.makeText(MyService.this, pActivity, Toast.LENGTH_SHORT).show();
                    pActivity = activityOnTop.toString();
                }


            }
        }


}

*/