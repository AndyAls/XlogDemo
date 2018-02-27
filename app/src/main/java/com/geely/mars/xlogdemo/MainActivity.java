package com.geely.mars.xlogdemo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.geely.mars.xlogdemo.xlog.LogUtils;
import com.geely.mars.xlogdemo.xlog.XLog;

public class MainActivity extends AppCompatActivity {

    String json = "{\"value\":null,\"objValue\":[{\"name\":\"日程管理\",\"datas\":[{\"id\":\"e583e01c98cc4025a8a540646de7bf9e\",\"name\":\"邮件\",\"picture\":\"/newemail/newemail.jpg\",\"order\":1,\"status\":\"1\",\"display\":\"0\",\"type\":\"3\",\"android_access_url\":\"enterpriseEmail\",\"ios_access_url\":\"enterpriseEmail\",\"remarks\":\"\"},{\"id\":\"ec83268d07bf4cbcb55eaa60426e597f\",\"name\":\"流程\",\"picture\":\"/newbmp/newbmp.jpg\",\"order\":2,\"status\":\"1\",\"display\":\"0\",\"type\":\"3\",\"android_access_url\":\"bpm\",\"ios_access_url\":\"bpm\",\"remarks\":\"http://180.97.80.232:8899/WorkflowService.asmx/GetToDoListCount\"},{\"id\":\"e95725deb1f24457b039f1dc66c8daa9\",\"name\":\"CPC\",\"picture\":\"/newcpc/newcpc.jpg\",\"order\":3,\"status\":\"1\",\"display\":\"0\",\"type\":\"2\",\"android_access_url\":\"cpc\",\"ios_access_url\":\"cpc\",\"remarks\":\"https://cpcweb.geely.com/wf/index.jsp\"},{\"id\":\"6d731adcbcf04251b896b407833bfd13\",\"name\":\"日历\",\"picture\":\"/newdate/newdate.jpg\",\"order\":5,\"status\":\"1\",\"display\":\"0\",\"type\":\"3\",\"android_access_url\":\"calendar\",\"ios_access_url\":\"calendar\",\"remarks\":\"this is a remark url\"}]},{\"name\":\"外部应用\",\"datas\":[{\"id\":\"64b40f23c1744bf78ba8e7a27552c7c5\",\"name\":\"吉淘淘\",\"picture\":\"/JTT_ICO/JTT_ICO.jpg\",\"order\":12,\"status\":\"1\",\"display\":\"0\",\"type\":\"2\",\"android_access_url\":\"JTT\",\"ios_access_url\":\"JTT\",\"remarks\":\"http://10.200.145.82:8080/Mall_Phone/home/OAToIndex.do\"}]},{\"name\":\"知识分享\",\"datas\":[{\"id\":\"b779195a15b04f43ad3208c00b4a2530\",\"name\":\"新闻\",\"picture\":\"/newnews/newnews.jpg\",\"order\":4,\"status\":\"1\",\"display\":\"0\",\"type\":\"3\",\"android_access_url\":\"notice\",\"ios_access_url\":\"notice\",\"remarks\":\"\"}]},{\"name\":\"业务\",\"datas\":[{\"id\":\"dbbd56eab3e34d8b99bb001af82d7038\",\"name\":\"阳光系统\",\"picture\":\"/SUN_ICO/SUN_ICO.jpg\",\"order\":12,\"status\":\"1\",\"display\":\"1\",\"type\":\"2\",\"android_access_url\":\"SUN\",\"ios_access_url\":\"SUN\",\"remarks\":\"http://10.86.94.235:8088/kpi_mobile/SSOlogin.do\"}]},{\"name\":\"EHR\",\"datas\":[{\"id\":\"51af28bb5abc41b0b28394ddce70e719\",\"name\":\"吉伴伴\",\"picture\":\"/JBB_ICO/JBB_ICO.jpg\",\"order\":8,\"status\":\"1\",\"display\":\"1\",\"type\":\"2\",\"android_access_url\":\"JBB\",\"ios_access_url\":\"JBB\",\"remarks\":\"http://10.200.188.89:8080/phone_portal/home/OAToIndex.do\"},{\"id\":\"e807f89fe6bf42118eb960576ee65ee4\",\"name\":\"考勤\",\"picture\":\"/ADC_ICO/ADC_ICO.jpg\",\"order\":9,\"status\":\"1\",\"display\":\"1\",\"type\":\"2\",\"android_access_url\":\"ADC\",\"ios_access_url\":\"ADC\",\"remarks\":\"http://10.86.94.235:1092/QiyePro/login4AD.do\"},{\"id\":\"a60f03b1c02c4ecabfac6c55c0f0e9cb\",\"name\":\"绩效\",\"picture\":\"/KPI_ICO/KPI_ICO.jpg\",\"order\":11,\"status\":\"1\",\"display\":\"1\",\"type\":\"2\",\"android_access_url\":\"KPI\",\"ios_access_url\":\"KPI\",\"remarks\":\"http://10.86.94.235:1092/GL_mobile/home/login4AD.do\"}]},{\"name\":\"BI报表\",\"datas\":[{\"id\":\"2d8535894e0f4a12b5a8031d1f0562b7\",\"name\":\"分析\",\"picture\":\"/WechatIMG266/WechatIMG266.jpg\",\"order\":7,\"status\":\"1\",\"display\":\"1\",\"type\":\"2\",\"android_access_url\":\"BI\",\"ios_access_url\":\"BI\",\"remarks\":\"http://imt.evun.cn/mt/wechat/loginIMTForOA\"}]}],\"ok\":true}";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        XLog.initTAG(this);
    }

    public void onClick(View view) {
        switch (view.getId()){
            case R.id.button:
                XLog.json(json);
                XLog.json("---",json);
                break;
            case R.id.button1:
                XLog.i("....","button1");
                break;
            case R.id.button2:
                XLog.i(this,"button2");
                break;
            case R.id.button3:
                Peason peason = new Peason();
                break;
            case R.id.button4:
                XLog.i("tag","button4","button4---","button4++++");
                break;
            default:
                break;
        }
    }
}
