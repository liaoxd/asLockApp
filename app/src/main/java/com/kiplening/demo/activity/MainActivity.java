package com.kiplening.demo.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ListView;

import com.kiplening.demo.R;
import com.kiplening.demo.module.App;
import com.kiplening.demo.service.Receiver;
import com.kiplening.demo.tools.DataBaseHelper;
import com.kiplening.demo.tools.DataBaseUtil;
import com.kiplening.demo.tools.ListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static MainActivity instance;
    private List<Map<String, Object>> listItems;
    private ListView myList;
    private ListViewAdapter listViewAdapter;
    private ArrayList<String> lockList = new ArrayList<String>();

    private ArrayList<App> lockedApps;

    private SQLiteDatabase db;

    private String dataBaseName = "kiplening";
    private String tableName = "app";
    public final DataBaseHelper helper = new DataBaseHelper(this,dataBaseName,null,1,null);
    private DataBaseUtil dataBaseUtil = new DataBaseUtil();

    private String status;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        instance = this;

        db = helper.getWritableDatabase();
        lockedApps = dataBaseUtil.getAll(db);
        status = dataBaseUtil.getStatus(db);
        if (status.equals("error")){
            ContentValues cv = new ContentValues();
            cv.put("status", "true");
            db.insert("settings", null, cv);
        }
        listItems = new ArrayList<Map<String,Object>>();
        //Intent intent = getIntent();
        ArrayList<String> appList = new ArrayList<String>();
        List<PackageInfo> packages = getPackageManager()
                .getInstalledPackages(0);
        for (int i = 0; i < packages.size(); i++) {
            // for (ResolveInfo resolveInfo : allMatches) {
            PackageInfo packageInfo = packages.get(i);


            if (isUserApp(packageInfo)) {
                appList.add(packageInfo.packageName);
                Map<String, Object> map = new HashMap<String, Object>();
                map.put("info", "installed app");
                map.put("name",
                        packageInfo.applicationInfo.loadLabel(
                                getPackageManager()).toString());
                map.put("packageName", packageInfo.applicationInfo.packageName);
                map.put("icon", packageInfo.applicationInfo
                        .loadIcon(getPackageManager()));
                if (isLocked(packageInfo,lockedApps)){
                    map.put("flag", "已锁定");
                    lockList.add(packageInfo.applicationInfo.packageName);
                }else {
                    map.put("flag", "锁定");
                }

                //lockList.add(packageInfo.applicationInfo.packageName);
                listItems.add(map);
                Log.i("test", packageInfo.applicationInfo.loadLabel(
                        getPackageManager()).toString());
            }
        }
        myList = (ListView)findViewById(R.id.list);
        listViewAdapter = new ListViewAdapter(this,listItems);
        myList.setAdapter(listViewAdapter);
        Receiver r = new Receiver();

        if (status.equals("true")){
            Intent intent = new Intent("android.intent.action.MAIN_BROADCAST");
            intent.putStringArrayListExtra("lockList", lockList);
            intent.putExtra("status", "true");
            sendBroadcast(intent);
        }else{
            Intent intent = new Intent("android.intent.action.MAIN_BROADCAST");
            intent.putStringArrayListExtra("lockList", lockList);
            intent.putExtra("status","false");
            sendBroadcast(intent);
        }



    }

    @Override
    protected void onStop() {
        lockedApps = dataBaseUtil.getAll(db);
        super.onStop();
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
    public boolean isLocked(PackageInfo pInfo,ArrayList<App> lockedApps){
        for (App a:lockedApps) {
            if (pInfo.applicationInfo.packageName.equals(a.getPackageName())){

                return true;
            }
        }
        return false;
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
            Intent i = new Intent(MainActivity.this,SettingActivity.class);
            status = dataBaseUtil.getStatus(db);
            i.putExtra("status",status);
            startActivity(i);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
