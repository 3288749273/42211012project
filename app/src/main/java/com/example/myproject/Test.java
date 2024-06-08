package com.example.myproject;

import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Test extends AppCompatActivity {

    private RecyclerView pdfRecyclerView;
    private List<String> displayNames = Arrays.asList(
            "C盘清理及分区教程",
            "网络连接教程",
            "西南财经大学 Office安装教程",
            "西南财经大学 Visio安装教程",
            "西南财经大学MATLAB软件安装教程",
            "西南财经大学SAS安装教程",
            "西南财经大学WPS安装教程",
            "重装系统教程"
    );

    private Map<String, String> pdfMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.test);

        // 初始化映射关系
        pdfMap = new HashMap<>();
        pdfMap.put("C盘清理及分区教程", "3");
        pdfMap.put("网络连接教程", "1.pdf");
        pdfMap.put("西南财经大学 Office安装教程", "4");
        pdfMap.put("西南财经大学 Visio安装教程", "7");
        pdfMap.put("西南财经大学MATLAB软件安装教程", "6");
        pdfMap.put("西南财经大学SAS安装教程", "8");
        pdfMap.put("西南财经大学WPS安装教程", "5");
        pdfMap.put("重装系统教程", "2.pdf");

        pdfRecyclerView = findViewById(R.id.pdfRecyclerView);
        pdfRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        pdfRecyclerView.setAdapter(new PdfAdapter(displayNames, this::downloadPdf));
    }

    private void downloadPdf(String displayName) {
        String pdfFileName = pdfMap.get(displayName);
        if (pdfFileName == null) {
            Toast.makeText(this, "Error: File not found", Toast.LENGTH_SHORT).show();
            return;
        }

        try {
            InputStream is = getAssets().open("pdfs/" + pdfFileName);
            File outputFile = new File(getExternalFilesDir(null), displayName + ".pdf");
            FileOutputStream fos = new FileOutputStream(outputFile);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                fos.write(buffer, 0, length);
            }
            fos.close();
            is.close();
            Toast.makeText(this, "Downloaded: " + displayName, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Error downloading " + displayName, Toast.LENGTH_SHORT).show();
        }
    }
}
