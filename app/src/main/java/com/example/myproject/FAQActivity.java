package com.example.myproject;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;
import java.util.List;

public class FAQActivity extends AppCompatActivity {

    private ListView listViewFAQs;
    private EditText editTextSearch;
    private List<FAQ> faqList;
    private List<FAQ> filteredFaqList;
    private FAQAdapter faqAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_questions);

        listViewFAQs = findViewById(R.id.listViewFAQs);
        editTextSearch = findViewById(R.id.editTextSearch);

        faqList = new ArrayList<>();
        // 添加常见问题数据
        faqList.add(new FAQ("几个无线网络之间的区别?", "CMCC：学校在学生公寓与移动营业商合作为学生提供宽带网络服务。\n" +
                "China-Net：学校在学生公寓与电信营业商合作为学生提供宽带网络服务。\n" +
                "iSwufe：是西南财经大学校园网，覆盖公共区域，为全校师生和临时来访人员提供校园网接入服务。iSwufe的临时访客是通过手机验证码登录，不需办理运营商服务，每天可使用三次，每次为三小时。\n" +
                "eduroam：专为科研和教育机构开发的安全的环球跨域无线漫游认证服务，我校师生可以在联盟成员单位免费使用无线网络。\n"));
        faqList.add(new FAQ("eduroam全球无线漫游", "我校已开通eduroam全球无线漫游服务。eduroam（educationroaming）是专为科研和教育机构开发的安全的环球跨域无线漫游认证服务。\n" +
                "我校师生在第一次使用eduroam前需要修改一次自己的上网密码，以激活统一身份认证帐号eduroam功能。密码修改地址为：https://id.swufe.edu.cn/user/。也可在移动校园-微服务-校园网自助服务-重置密码处修改密码。上网密码修改成功后，在校内公共区域搜索WIFI信号，连接“eduroam”，在“身份”处输入自己的上网帐号和学校身份全称即“学号@swufe.edu.cn”，在“密码”处输入统一身份认证密码连接即可。\n"));
        faqList.add(new FAQ("无线网络使用方法", "无线网络使用前需要先进行身份认证，身份认证的网址为：\n" +
                "（1）iSwufe：http://i.swufe.edu.cn/net/（移动和电信都能使用）\n" +
                "（2）电信用户连接：https://info.swufe.edu.cn/wlan/SWUFE-DX.html\n" +
                "（3）移动用户连接：http://ietc.swufe.edu.cn/wlan/CMCC-swufe.html\n"));
        faqList.add(new FAQ("学生公寓有线网络", "在学生公寓，用户需通过iNode客户端认证才能接入校园网。\n" +
                "iNode客户端下载地址：https://info.swufe.edu.cn 首页>校园网服务>认证计费>客户端下载。\n"));
        faqList.add(new FAQ("iNode客户端使用方法", "iNode客户端安装完成以后，按照提示输入正确的用户名以及密码（UUID账号密码，移动用户要在用户名后添加“@cm”），输入密码时请注意大小写、标点符号，并使用英文输入。域请选择订购的网络套餐对应运营商；点击连接旁的小三角，在属性里的“选择网卡”一项中，选择名称中带有“PCIe”或“Realtek”的（如果使用了转换接口，请选择带有“USB”的）、或选择不带“WIFI”、 “wireless”、“Bluetooth”、“WLAN”或“本地连接”的。"));
        faqList.add(new FAQ("WebVPN", "校园网拥有大量数字资源和众多应用系统，如图书馆数字图书，教务系统，办公自动化系统等，由于资源厂商版权保护和系统安全要求，这些资源无法在校外直接使用。为了方便师生在校外学习工作，学校提供了WebVPN服务，可以在校外访问校内资源。\n" +
                "全校师生可通过统一身份认证账号进行使用。\n" +
                "使用方法\n" +
                "1.访问“https://webvpn.swufe.edu.cn”，直接使用统一身份认证账号登录，即可访问校内资源。\n" +
                "2.在微信“移动校园”公众号中点击“融合门户”，点击“SWUFE移动校园”中的“webvpn”进行登录即可使用。\n"));
        faqList.add(new FAQ("正版化服务", "西南财经大学与微软（中国）有限公司签订合作协议，从2013年开始，我校参加微软校园软件正版化推进工作，免费为学生提供正版操作系统和软件。\n" +
                "正版化网址：https://info.swufe.edu.cn/ 校园网服务>正版化服务\n"));

        filteredFaqList = new ArrayList<>(faqList);
        faqAdapter = new FAQAdapter(this, filteredFaqList);
        listViewFAQs.setAdapter(faqAdapter);

        editTextSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Not needed
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterFAQs(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Not needed
            }
        });
    }

    private void filterFAQs(String query) {
        filteredFaqList.clear();
        if (query.isEmpty()) {
            filteredFaqList.addAll(faqList);
        } else {
            for (FAQ faq : faqList) {
                if (faq.getQuestion().toLowerCase().contains(query.toLowerCase())) {
                    filteredFaqList.add(faq);
                }
            }
        }
        faqAdapter.notifyDataSetChanged();
    }
}
