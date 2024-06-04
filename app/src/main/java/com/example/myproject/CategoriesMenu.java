package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class CategoriesMenu extends AppCompatActivity {
    private GridView mGridView;
    private CategoriesAdapter mCategoriesAdapter;
    private String[] categoryNames = new String[]{"常见问题", "线下维修预约", "教程", "线下值班信息",
            "认证计费","正版化服务", "云邮箱服务", "校外访问", "修改密码", "提问留言","网络测速","答疑群",
            "管理"};
    private int[] categoryIcons = {R.mipmap.ic_question, R.mipmap.ic_book, R.mipmap.ic_test,
            R.mipmap.ic_location, R.mipmap.ic_jifei, R.mipmap.ic_zhengbanhua, R.mipmap.ic_email,
            R.mipmap.ic_vpn, R.mipmap.ic_password,R.mipmap.ic_liuyan,R.mipmap.ic_speed,R.mipmap.ic_qq,
            R.mipmap.ic_manage};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_categories); // 确保布局文件名称正确
        initView();
    }

    private void initView() {
        mGridView = findViewById(R.id.grid_view);
        List<Category> categoryList = new ArrayList<>();
        for (int i = 0; i < categoryNames.length; i++) {
            Category category = new Category();
            category.setName(categoryNames[i]);
            category.setIcon(categoryIcons[i]);
            categoryList.add(category);
        }
        mCategoriesAdapter = new CategoriesAdapter(this, categoryList);
        mGridView.setAdapter(mCategoriesAdapter);

        // 设置点击事件处理器
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        startActivity(new Intent(CategoriesMenu.this, FAQActivity.class));
                        break;
                    case 12:
                        startActivity(new Intent(CategoriesMenu.this, ManageActivity.class));


                    default:
                        break;
                }
            }
        });
    }
}
