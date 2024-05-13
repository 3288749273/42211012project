package com.example.myproject;

import android.os.Bundle;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomePageActivity extends AppCompatActivity {
    GridView simpleGrid;
    int icons[] = {R.drawable.ic_register, R.drawable.ic_vpn, R.drawable.ic_zhengbanhua, R.drawable.ic_book, R.drawable.ic_location, R.drawable.ic_time, R.drawable.ic_more};
    String names[] = {"认证服务", "校外访问", "正版化服务", "线下维修预约 ", "线下值班位置", "线下值班时间", "更多"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        simpleGrid = findViewById(R.id.gridview); // 初始化GridView
        MyAdapter myAdapter = new MyAdapter(this, names, icons);
        simpleGrid.setAdapter(myAdapter); // 设置适配器

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            switch (item.getItemId()) {
                case R.id.menu_home:
                    selectedFragment = new HomeFragment();
                    break;
                case R.id.menu_categories:
                    selectedFragment = new CategoriesFragment();
                    break;
                case R.id.menu_news:
                    selectedFragment = new NewsFragment();
                    break;
                case R.id.menu_messages:
                    selectedFragment = new MessagesFragment();
                    break;
                case R.id.menu_my:
                    selectedFragment = new MyFragment();
                    break;
            }
            if (selectedFragment != null) {
                FragmentManager fragmentManager = getSupportFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.replace(R.id.container, selectedFragment);
                fragmentTransaction.commit();
            }
            return true;
        });
    }
}
