package com.example.myproject;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class HomeMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_home);

        // 上导航
        LinearLayout homequestions = findViewById(R.id.homequsetions);
        LinearLayout homebook = findViewById(R.id.homebook);
        LinearLayout homeduty = findViewById(R.id.homeduty);
        LinearLayout homemore = findViewById(R.id.homemore);
        // 下导航
        RelativeLayout menu02 = findViewById(R.id.menu02);
        RelativeLayout menu03 = findViewById(R.id.menu03);
        RelativeLayout menu04 = findViewById(R.id.menu04);

        TextView morenews = findViewById(R.id.morenews);
        LinearLayout LinearLayoutnews = findViewById(R.id.LinearLayoutnews);

        homequestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenu.this, FAQActivity.class);
                startActivity(intent);
            }
        });

        homebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                navigateToAppointmentPage();
            }
        });

        homeduty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenu.this, DutyHome.class);
                startActivity(intent);
            }
        });

        homemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenu.this, CategoriesMenu.class);
                startActivity(intent);
            }
        });

        menu02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenu.this, CategoriesMenu.class);
                startActivity(intent);
            }
        });

        menu03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenu.this, MessagesMenu.class);
                startActivity(intent);
            }
        });

        menu04.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenu.this, MyMenu.class);
                startActivity(intent);
            }
        });

        morenews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenu.this, MessagesMenu.class);
                startActivity(intent);
            }
        });

        LinearLayoutnews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenu.this, NewsHome.class);
                startActivity(intent);
            }
        });
    }

    private void navigateToAppointmentPage() {
        SharedPreferences sharedPreferences = getSharedPreferences("user_prefs", MODE_PRIVATE);
        String identity = sharedPreferences.getString("user_identity", null);

        if (identity != null) {
            if (identity.equals("老师")) {
                Intent intent = new Intent(HomeMenu.this, TeacherAppointmentActivity.class);
                startActivity(intent);
            } else if (identity.equals("学生")) {
                Intent intent = new Intent(HomeMenu.this, StudentAppointmentActivity.class);
                startActivity(intent);
            }
        } else {
            // 处理未登录或身份信息丢失的情况
            Toast.makeText(HomeMenu.this, "未找到身份信息，请重新登录。", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(HomeMenu.this, LoginActivity.class);
            startActivity(intent);
        }
    }
}
