package com.kiplening.demo.activity;

import android.app.AlertDialog;
import android.app.usage.UsageStatsManager;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;

import com.kiplening.demo.R;
import com.kiplening.demo.activity.settings.SettingActivity;
import com.kiplening.demo.module.App;
import com.kiplening.demo.tools.DataBaseUtil;
import com.kiplening.demo.tools.ListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private List<Map<String, Object>> listItems;
    private ListView myList;
    private ListViewAdapter listViewAdapter;
    private ArrayList<String> lockList = new ArrayList<>();
    private ArrayList<App> lockedApps;
    private DataBaseUtil dataBaseUtil;
    private String status;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        String password = dataBaseUtil.getPWD();
        if (password.equals("null")){

        }
        int currentVersion = android.os.Build.VERSION.SDK_INT;
        if (currentVersion > 20) {
            if (!isNoSwitch()) {
                new AlertDialog.Builder(this).
                        setTitle("设置").
                        setMessage("开启usagestats权限")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                                startActivity(intent);
                                finish();
                            }
                        }).show();
            } else {
                jumpIn();
            }
        } else {
            jumpIn();
        }
    }

    public boolean isSystemApp(PackageInfo pInfo) {
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_SYSTEM) != 0);
    }

    public boolean isSystemUpdateApp(PackageInfo pInfo) {
        return ((pInfo.applicationInfo.flags & ApplicationInfo.FLAG_UPDATED_SYSTEM_APP) != 0);
    }

    public boolean isUserApp(PackageInfo pInfo) {
        return (!isSystemApp(pInfo) && !isSystemUpdateApp(pInfo));
    }

    public boolean isLocked(PackageInfo pInfo, ArrayList<App> lockedApps) {
        for (App a : lockedApps) {
            if (pInfo.applicationInfo.packageName.equals(a.getPackageName())) {

                return true;
            }
        }
        return false;
    }

    private boolean isNoSwitch() {
        long ts = System.currentTimeMillis();
        UsageStatsManager usageStatsManager = (UsageStatsManager) getApplicationContext()
                .getSystemService("usagestats");
        List queryUsageStats = usageStatsManager.queryUsageStats(
                UsageStatsManager.INTERVAL_BEST, 0, ts);
        return !(queryUsageStats == null || queryUsageStats.isEmpty());
    }

    /**
     * 在首页判断完手机的
     */
    private void jumpIn() {
        dataBaseUtil = new DataBaseUtil(this);
        lockedApps = dataBaseUtil.getAll();
        status = dataBaseUtil.getStatus();
        if (status.equals("error")) {
            ContentValues cv = new ContentValues();
            cv.put("status", "true");
            //db.insert("settings", null, cv);
        }
        listItems = new ArrayList<>();
        ArrayList<String> appList = new ArrayList<>();
        List<PackageInfo> packages = getPackageManager()
                .getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            PackageInfo packageInfo = packages.get(i);
            if (isUserApp(packageInfo)) {
                appList.add(packageInfo.packageName);
                Map<String, Object> map = new HashMap<>();
                map.put("info", "installed app");
                map.put("name",
                        packageInfo.applicationInfo.loadLabel(
                                getPackageManager()).toString());
                map.put("packageName", packageInfo.applicationInfo.packageName);
                map.put("icon", packageInfo.applicationInfo
                        .loadIcon(getPackageManager()));
                if (isLocked(packageInfo, lockedApps)) {
                    map.put("flag", "已锁定");
                    lockList.add(packageInfo.applicationInfo.packageName);
                } else {
                    map.put("flag", "锁定");
                }

                //lockList.add(packageInfo.applicationInfo.packageName);
                listItems.add(map);
                Log.i("test", packageInfo.applicationInfo.loadLabel(
                        getPackageManager()).toString());
            }
        }
        myList = (ListView) findViewById(R.id.list);
        listViewAdapter = new ListViewAdapter(this, listItems);
        myList.setAdapter(listViewAdapter);

        if (status.equals("true")) {
            Intent intent = new Intent("android.intent.action.MAIN_BROADCAST");
            intent.putStringArrayListExtra("lockList", lockList);
            intent.putExtra("status", "true");
            sendBroadcast(intent);
        } else {
            Intent intent = new Intent("android.intent.action.MAIN_BROADCAST");
            intent.putStringArrayListExtra("lockList", lockList);
            intent.putExtra("status", "false");
            sendBroadcast(intent);
        }
    }

    @Override
    protected void onStop() {
//        lockedApps = dataBaseUtil.getAll(db);
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (KeyEvent.KEYCODE_BACK == event.getKeyCode()) {
            finish();
        }
        if (KeyEvent.KEYCODE_HOME == event.getKeyCode()) {
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent i = new Intent(MainActivity.this, SettingActivity.class);
            status = dataBaseUtil.getStatus();
            i.putExtra("status", status);
            startActivity(i);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
