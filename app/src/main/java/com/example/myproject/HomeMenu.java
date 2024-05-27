package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class HomeMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_home);
        //上导航
        LinearLayout homequestions = findViewById(R.id.homequsetions);
        LinearLayout homebook = findViewById(R.id.homebook);
        LinearLayout homeduty = findViewById(R.id.homeduty);
        LinearLayout homemore = findViewById(R.id.homemore);
        //下导航
        RelativeLayout menu01 = findViewById(R.id.menu01);
        RelativeLayout menu02 = findViewById(R.id.menu02);
        RelativeLayout menu03 = findViewById(R.id.menu03);
        RelativeLayout menu04 = findViewById(R.id.menu04);

        homequestions.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenu.this, QuestionsHome.class);
                startActivity(intent);
            }
        });
        homebook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenu.this, HomeMenu.class);
                startActivity(intent);
            }
        });
        homeduty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenu.this, HomeMenu.class);
                startActivity(intent);
            }
        });
        homemore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenu.this, HomeMenu.class);
                startActivity(intent);
            }
        });

        menu01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeMenu.this, HomeMenu.class);
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
    }
}
