package com.example.myproject;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.ImageView;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextID;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextPhone;
    private RadioGroup radioGroupIdentity;
    private Button buttonRegister;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register); // 加载布局文件

        // 初始化组件
        editTextName = findViewById(R.id.editTextName);
        editTextID = findViewById(R.id.editTextID);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextPhone = findViewById(R.id.editTextPhone);
        radioGroupIdentity = findViewById(R.id.radioGroupIdentity);
        buttonRegister = findViewById(R.id.buttonRegister);
        imageView = findViewById(R.id.imageView);

        imageView.setImageResource(R.drawable.logo);
        // 注册按钮的点击事件
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = editTextName.getText().toString().trim();
                String id = editTextID.getText().toString().trim();
                String password = editTextPassword.getText().toString();
                String confirmPassword = editTextConfirmPassword.getText().toString().trim();
                String phone = editTextPhone.getText().toString();
                int checkedId = radioGroupIdentity.getCheckedRadioButtonId();

                if (checkedId == -1) {
                    // 没有选择身份
                    Toast.makeText(RegisterActivity.this, "请选择您的身份", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!password.isEmpty() && !confirmPassword.isEmpty()) {
                    if (password.equals(confirmPassword)) {
                        // 密码不匹配
                        Toast.makeText(RegisterActivity.this, "密码不匹配", Toast.LENGTH_SHORT).show();
                        editTextPassword.setError("密码不匹配");
                        editTextConfirmPassword.setError("请重新输入");
                    }
                } else {
                    // 密码字段为空
                    Toast.makeText(RegisterActivity.this, "请输入密码", Toast.LENGTH_SHORT).show();
                }
                if (phone.isEmpty()) {
                    // 没有输入电话
                    Toast.makeText(RegisterActivity.this, "请输入您的联系电话", Toast.LENGTH_SHORT).show();
                    return;
                }
                RadioButton selectedRadioButton = findViewById(checkedId);
                String identity = selectedRadioButton.getText().toString();

                // 这里添加将用户信息保存到数据库的逻辑
                // 以下代码仅为示例，您需要替换为实际的注册逻辑
                Toast.makeText(RegisterActivity.this, "注册成功: " + name, Toast.LENGTH_SHORT).show();

                // 注册成功后，可以跳转到另一个Activity或者结束当前Activity
                // finish(); // 如果需要结束当前Activity

            }
        });
    }
}
