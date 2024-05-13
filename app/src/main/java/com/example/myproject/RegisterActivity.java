package com.example.myproject;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegisterActivity extends AppCompatActivity {

    private EditText editTextName;
    private EditText editTextID;
    private EditText editTextPassword;
    private EditText editTextConfirmPassword;
    private EditText editTextPhone;
    private RadioGroup radioGroupIdentity;
    private Button buttonRegister;
    private DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        databaseHelper = new DatabaseHelper(this);

        editTextName = findViewById(R.id.editTextName);
        editTextID = findViewById(R.id.editTextID);
        editTextPassword = findViewById(R.id.editTextPassword);
        editTextConfirmPassword = findViewById(R.id.editTextConfirmPassword);
        editTextPhone = findViewById(R.id.editTextPhone);
        radioGroupIdentity = findViewById(R.id.radioGroupIdentity);
        buttonRegister = findViewById(R.id.buttonRegister);

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
                    Toast.makeText(RegisterActivity.this, "请选择您的身份", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (!password.equals(confirmPassword)) {
                    Toast.makeText(RegisterActivity.this, "密码不匹配", Toast.LENGTH_SHORT).show();
                    editTextPassword.setError("密码不匹配");
                    editTextConfirmPassword.setError("请重新输入");
                    return;
                }

                if (phone.isEmpty()) {
                    Toast.makeText(RegisterActivity.this, "请输入您的联系电话", Toast.LENGTH_SHORT).show();
                    return;
                }

                RadioButton selectedRadioButton = findViewById(checkedId);
                String identity = selectedRadioButton.getText().toString();

                // Add user to the database
                boolean isInserted = databaseHelper.addUser(name, id, password, confirmPassword, phone);
                if (isInserted) {
                    Toast.makeText(RegisterActivity.this, "注册成功: " + name, Toast.LENGTH_SHORT).show();
                    // 注册成功后，跳转到HomePageActivity
                    Intent intent = new Intent(RegisterActivity.this, HomePageActivity.class);
                    startActivity(intent);
                    // 可选：结束当前的 RegisterActivity
                    // finish();
                } else {
                    Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
