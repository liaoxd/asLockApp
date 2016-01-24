package com.kiplening.demo.service;

import android.app.ActivityManager;
import android.app.Service;
import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.PowerManager;
import android.support.annotation.Nullable;
import android.util.Log;

import com.kiplening.demo.activity.UnLockActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by MOON on 1/19/2016.
 */
public class LockService extends Service{

    private final String TAG = "LockService";
    private Handler mHandler = null;
    private final static int LOOPHANDLER = 0;
    private HandlerThread handlerThread = null;

    private ArrayList<String> lockName = new ArrayList<String>();
    private boolean isUnLockActivity = false;
    private String status;

    //设定检测时间的间隔，间隔太长可能造成已进入软件还未加锁的情况，
    //间隔时间太短则会加大CPU的负荷，程序更耗电。内存占用更大
    private static long cycleTime = 100;

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        handlerThread = new HandlerThread("count_thread");
        handlerThread.start();
        lockName = intent.getStringArrayListExtra("lockList");
        status = intent.getStringExtra("status");
        for (String l:lockName) {
            System.out.print("锁定");
            System.out.println(l);
        }

        if (status.equals("false")){
            return Service.START_STICKY;
        }else{
            //开始循环检查
            mHandler = new Handler(handlerThread.getLooper()) {
                public void dispatchMessage(android.os.Message msg) {
                    switch (msg.what) {
                        case LOOPHANDLER:
                            //Log.i(TAG,"do something..."+(System.currentTimeMillis()/1000));
                            /**
                             * 这里需要注意的是：isLockName是用来判断当前的topActivity是不是我们需要加锁的应用
                             * 同时还是需要做一个判断，就是是否已经对这个app加过锁了，不然会出现一个问题
                             * 当我们打开app时，启动我们的加锁界面，解锁之后，回到了app,但是这时候又发现栈顶app是
                             * 需要加锁的app,那么这时候又启动了我们加锁界面，这样就出现死循环了。
                             * 可以自行的实验一下
                             * 所以这里用isUnLockActivity变量来做判断的
                            */
                            try {
                                if(isLockName() && !isUnLockActivity){
                                    if (status.equals("true")){
                                        Log.i(TAG, "locking...");
                                        //调用了解锁界面之后，需要设置一下isUnLockActivity的值

                                        Intent intent = new Intent(LockService.this,UnLockActivity.class);

                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        startActivity(intent);

                                        isUnLockActivity = true;
                                    }

                                }
                            } catch (PackageManager.NameNotFoundException e) {
                                e.printStackTrace();
                            }
                            break;
                    }
                    mHandler.sendEmptyMessageDelayed(LOOPHANDLER, cycleTime);
                }
            };
            mHandler.sendEmptyMessage(LOOPHANDLER);
            //return Service.START_STICKY;

            return Service.START_STICKY;
        }

    }

    private boolean isLockName() throws PackageManager.NameNotFoundException {
        // TODO Auto-generated method stub
        List<PackageInfo> packages = getPackageManager()
                .getInstalledPackages(0);
        ActivityManager mActivityManager;
        mActivityManager = (ActivityManager) this.getSystemService(Context.ACTIVITY_SERVICE);

        String packageName = null,lable;
        if (Build.VERSION.SDK_INT > 20) {
            UsageStatsManager usageStatsManager = (UsageStatsManager) getApplicationContext()
                    .getSystemService("usagestats");


            long ts = System.currentTimeMillis();
            List<UsageStats> queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_BEST,0, ts);

            UsageStats recentStats = null;
            for (UsageStats usageStats : queryUsageStats) {
                if (recentStats == null || recentStats.getLastTimeUsed() < usageStats.getLastTimeUsed()) {
                    recentStats = usageStats;
                }
            }
            packageName = recentStats.getPackageName();
        } else{
            // 5.0之前
            // 获取正在运行的任务栈(一个应用程序占用一个任务栈) 最近使用的任务栈会在最前面
            // 1表示给集合设置的最大容量 List<RunningTaskInfo> infos = am.getRunningTasks(1);
            // 获取最近运行的任务栈中的栈顶Activity(即用户当前操作的activity)的包名
            packageName = mActivityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
            //Log.i(TAG,packageName);
        }



        /*
        ComponentName topActivity = mActivityManager.getRunningTasks(1).get(0).topActivity;
        String packageName = topActivity.getPackageName();
        */
        Context context = getApplicationContext();
        PowerManager
                pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);


        //String packageName = ""; /* Android5.0之后获取程序锁的方式是不一样的*/
/*据说能成功的方法,在魅族上面测试不通过
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.KITKAT_WATCH) {
        // 5.0及其以后的版本
            System.out.println("hahahahahahaha");
            List<ActivityManager.RunningAppProcessInfo> tasks = mActivityManager.getRunningAppProcesses();
            if (null != tasks && tasks.size() > 0) {
                System.out.print("hahahahahahaha");
                packageName = tasks.get(0).processName;
            }
        } else{
            // 5.0之前
            // 获取正在运行的任务栈(一个应用程序占用一个任务栈) 最近使用的任务栈会在最前面
            // 1表示给集合设置的最大容量 List<RunningTaskInfo> infos = am.getRunningTasks(1);
            // 获取最近运行的任务栈中的栈顶Activity(即用户当前操作的activity)的包名
            packageName = mActivityManager.getRunningTasks(1).get(0).topActivity.getPackageName();
        }
 */


        boolean isScreenOn = pm.isScreenOn();//如果为true，则表示屏幕“亮”了，否则屏幕“暗”了。
        //如果当前的Activity是桌面app,那么就需要将isUnLockActivity清空值
        if(getHomes().contains(packageName)){
            isUnLockActivity = false;
        }

        //Log.v("LockService", "packageName == " + packageName);
        System.out.println(lockName.size());
        System.out.println(status);
        System.out.println(status.equals("false"));
        for (int i = 0; i < lockName.size(); i++) {
            System.out.println(lockName.get(i));
            if(lockName.get(i).equals(packageName)){
                Log.v("LockService", "Locking ...." + packageName);
                return true;
            }
        }

        return false;
    }

    private List<String> getHomes() {
        // TODO Auto-generated method stub
        List<String> names = new ArrayList<String>();
        PackageManager packageManager = this.getPackageManager();
        //属性
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        List<ResolveInfo> resolveInfo = packageManager.queryIntentActivities(intent,
                PackageManager.MATCH_DEFAULT_ONLY);
        for(ResolveInfo ri : resolveInfo){
            names.add(ri.activityInfo.packageName);
//            System.out.println(ri.activityInfo.packageName);
        }
        return names;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
