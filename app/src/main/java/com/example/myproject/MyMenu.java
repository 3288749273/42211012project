package com.example.myproject;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyMenu extends AppCompatActivity {
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_PICK_PHOTO = 2;
    private static final int REQUEST_PERMISSION = 100;
    private ImageView profileImage;
    private TextView profileName;
    private TextView accountSecurity;
    private TextView helpCenter;
    private TextView about;
    private DatabaseHelper databaseHelper;
    private Uri photoURI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_my);

        profileImage = findViewById(R.id.profile_image);
        profileName = findViewById(R.id.profile_name);
        accountSecurity = findViewById(R.id.account_security);
        helpCenter = findViewById(R.id.help_center);
        about = findViewById(R.id.about);

        databaseHelper = new DatabaseHelper(this);

        // 从数据库中获取用户信息
        loadUserProfile();

        // 设置点击事件
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelfPermission()) {
                    showImageOptions();
                } else {
                    requestPermission();
                }
            }
        });

        accountSecurity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到账号与安全界面
                // startActivity(new Intent(MyMenu.this, AccountSecurityActivity.class));
            }
        });

        helpCenter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到帮助中心界面
                // startActivity(new Intent(MyMenu.this, HelpCenterActivity.class));
            }
        });

        about.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 跳转到关于界面
                // startActivity(new Intent(MyMenu.this, AboutActivity.class));
            }
        });
    }
    private boolean checkSelfPermission() {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    };

    private void requestPermission() {
        ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
    };

    private void loadUserProfile() {
        Cursor cursor = databaseHelper.getUser("1");
        if (cursor.moveToFirst()) {
            String name = cursor.getString(cursor.getColumnIndexOrThrow("name"));
            String profileImagePath = cursor.getString(cursor.getColumnIndexOrThrow("image"));

            profileName.setText(name);
            if (profileImagePath != null && !profileImagePath.isEmpty()) {
                profileImage.setImageURI(Uri.parse(profileImagePath));
                Log.d("MyMenu", "头像路径加载成功: " + profileImagePath);
            } else {
                profileImage.setImageResource(R.drawable.moren); // 默认头像
                Log.d("MyMenu", "使用默认头像");
            }
        }
        cursor.close();
    }


    private void showImageOptions() {
        String[] options = {"拍摄", "从相册选取"};
        new androidx.appcompat.app.AlertDialog.Builder(this)
                .setTitle("选择头像")
                .setItems(options, (dialog, which) -> {
                    switch (which) {
                        case 0:
                            if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
                            } else {
                                captureImage();
                            }
                            break;
                        case 1:
                            if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, REQUEST_PERMISSION);
                            } else {
                                pickImage();
                            }
                            break;
                    }
                })
                .show();
    }

    private void captureImage() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            if (photoFile != null) {
                photoURI = FileProvider.getUriForFile(this, "com.example.myproject.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            }
        } else {
            Toast.makeText(this, "没有可用的相机应用", Toast.LENGTH_SHORT).show();
        }
    }

    private File createImageFile() throws IOException {
        // 创建用于存储图像的文件
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(null);
        return File.createTempFile(imageFileName, ".jpg", storageDir);
    }

    private void pickImage() {
        Intent pickPhotoIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhotoIntent, REQUEST_PICK_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Uri imageUri = null;
            if (requestCode == REQUEST_IMAGE_CAPTURE) {
                imageUri = photoURI;
            } else if (requestCode == REQUEST_PICK_PHOTO && data != null) {
                imageUri = data.getData();
            }
            if (imageUri != null) {
                String imagePath = getRealPathFromURI(imageUri);
                profileImage.setImageURI(imageUri);
                boolean isSaved = databaseHelper.updateUserProfileImage("1", imagePath);
                if (isSaved) {
                    Log.d("MyMenu", "头像路径保存成功: " + imagePath);
                } else {
                    Log.d("MyMenu", "头像路径保存失败: " + imagePath);
                }
            }
            // 重新加载用户信息以确保更新的头像被正确加载
            loadUserProfile();
        }
    }
    private String getRealPathFromURI(Uri contentUri) {
        String[] proj = { MediaStore.Images.Media.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor == null) {
            return contentUri.getPath();
        } else {
            int index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String path = cursor.getString(index);
            cursor.close();
            return path;
        }
    }


    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == REQUEST_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                showImageOptions();
            } else {
                Toast.makeText(this, "权限请求被拒绝", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
