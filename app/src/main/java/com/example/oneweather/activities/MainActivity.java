package com.example.oneweather.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.oneweather.R;
import com.thinkpage.lib.api.TPCity;
import com.thinkpage.lib.api.TPListeners;
import com.thinkpage.lib.api.TPWeatherManager;
import com.thinkpage.lib.api.TPWeatherNow;

import es.dmoral.toasty.Toasty;

public class MainActivity extends BaseActivity
        implements NavigationView.OnNavigationItemSelectedListener, View.OnClickListener {

    private TextView textView;
    private EditText editText;
    private Button button;
    private TPWeatherManager weatherManager;// 知心天气
    private ImageView imageView;
    private int flag = 0;
    private int[] imageIds ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        imageView = findViewById(R.id.imageView);
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toasty.success(MainActivity.this," 小姐姐 (⁎⁍̴̛ᴗ⁍̴̛⁎)  ",Toasty.LENGTH_SHORT).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        textView = findViewById(R.id.main_text);
        editText = findViewById(R.id.main_search);
        button = findViewById(R.id.main_btn);
        textView.setOnClickListener(this);
        button.setOnClickListener(this);

    }



    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_btn:
                changeColor();
                break;
            default:
                break;
        }
    }

    private void changeColor(){
        // 获得天气数据
        weatherManager = TPWeatherManager.sharedWeatherManager();
        // 使用心知天气官网获取的key和用户id初始化WeatherManager
        weatherManager.initWithKeyAndUserId("880mqiee8jhtd1k3","UEEB0F5663");
        //
        String place = editText.getText().toString();
        // 获取北京当前天气，使用简体中文、摄氏度
        weatherManager.getWeatherNow(new TPCity(place)
                , TPWeatherManager.TPWeatherReportLanguage.kSimplifiedChinese
                , TPWeatherManager.TPTemperatureUnit.kCelsius
                , new TPListeners.TPWeatherNowListener() {
                    @Override
                    public void onTPWeatherNowAvailable(TPWeatherNow tpWeatherNow, String s) {
                        if (tpWeatherNow != null) {
                            //weatherNow 就是返回的当前天气信息。
                            textView.setText("天气：" + tpWeatherNow.text + //获取天气文本，例如“晴”、“多云”
                                    "\n" + "风向：" + tpWeatherNow.windDirection + //获取当前风向，例如“西北”
                                    "\n" + "气温：" + tpWeatherNow.temperature + //获取当前当地气温
                                    "\n" + "体感温度：" + tpWeatherNow.feelsLikeTemperature );//获取当前体感温度
                            Toasty.success(MainActivity.this," 信鸽已经到达～～ ",Toasty.LENGTH_SHORT).show();
                        } else {
                            textView.setText(s); //错误信息
                        }
                    }
                });
    }
}
