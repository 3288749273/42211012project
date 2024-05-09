package com.example.myproject;
import android.os.Bundle;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;


public class StartPage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.start_page); // 设置布局文件

            // 获取ImageView的引用
        ImageView imageView = findViewById(R.id.imageView);

            // 获取要设置的图片资源ID
        int imageResourceId = R.drawable.your_image; // 替换为你的图片资源名

            // 将图片资源设置到ImageView
        imageView.setImageResource(imageResourceId);

            // 如果需要，可以设置图片的缩放类型
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }
}

