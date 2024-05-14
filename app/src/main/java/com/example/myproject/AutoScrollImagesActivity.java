package com.example.myproject;

import android.os.Bundle;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

public class AutoScrollImagesActivity extends AppCompatActivity {

    private ViewPager2 viewPager;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_home);

        View viewPager;
        viewPager = findViewById(R.id.viewPager);
        int[] imageArray = new int[]{R.mipmap.img1, R.mipmap.img2, R.mipmap.img3,R.mipmap.img4,R.mipmap.img5}; // Add your images here
        ImageAdapter adapter = new ImageAdapter(imageArray);
        viewPager.setAdapter(adapter);

        // 设置自动播放的定时任务
        Runnable update = new Runnable() {
            public void run() {
                if (currentPage >= adapter.getItemCount()) {
                    currentPage = 0;
                }
                viewPager.setCurrentItem(currentPage++, true);
                handler.postDelayed(this, 3000);
            }
        };
        handler.postDelayed(update, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }
}
