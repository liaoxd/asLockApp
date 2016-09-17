package com.kiplening.demo.activity.Main;

import android.app.AlertDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.os.Bundle;
import android.provider.Settings;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import com.kiplening.androidlib.activity.BaseActivity;
import com.kiplening.demo.R;
import com.kiplening.demo.activity.settings.SettingActivity;
import com.kiplening.demo.common.MyApplication;
import com.kiplening.demo.module.App;
import com.kiplening.demo.tools.DataBaseUtil;
import com.kiplening.demo.tools.ListViewAdapter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.InjectView;

public class MainActivity extends BaseActivity implements MainView {

    private List<Map<String, Object>> listItems;

    private ListViewAdapter listViewAdapter;
    private ArrayList<String> lockList = MyApplication.getLockList();
    private ArrayList<App> lockedApps;
    private DataBaseUtil dataBaseUtil;
    private String status;
    //private ListView myList;
    @InjectView(R.id.list)
    ListView myList;
    @InjectView(R.id.settings)
    Button setting;
    private MainPresenter presenter;

    @Override
    protected void initVariables() {
        presenter = new MainPresenterImpl(this);
    }

    @Override
    protected void initViews(Bundle savedInstanceState) {
        setContentView(R.layout.activity_main);
        ButterKnife.inject(this);
        presenter.checkPromission();
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigationToSetting();
            }
        });
    }

    @Override
    protected void loadData() {
        dataBaseUtil = new DataBaseUtil(MyApplication.getInstance());
        String password = dataBaseUtil.getPWD();
        if (password.equals("null")){

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

    @Override
    public void RequestPromission() {
        new AlertDialog.Builder(this).
                setTitle("设置").
                setMessage("开启usagestats权限")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS);
                        startActivity(intent);
                        //finish();
                    }
                }).show();
    }

    @Override
    public void showList() {
        dataBaseUtil = new DataBaseUtil(MyApplication.getInstance());
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
        //myList = (ListView) findViewById(R.id.list);
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
    public void onDestory() {
        presenter.onDestory();
        super.onDestroy();
    }

    @Override
    public void navigationToSetting() {
        Intent intent = new Intent(this,SettingActivity.class);
        startActivity(intent);
    }
}
