package cn.edu.pku.ss.zhuwc.dormitoryselect;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Objects;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import cn.edu.pku.ss.zhuwc.app.myApplication;
import cn.edu.pku.ss.zhuwc.bean.StuInfo;
import cn.edu.pku.ss.zhuwc.bean.bean;

public class MainActivity extends Activity implements View.OnClickListener{

    private EditText edit_StuId;
    private EditText edit_PSW;
    private Button btn_Login;
    private TextInputLayout layout_stuid;
    private TextInputLayout layout_psw;
    private String stuId;
    private String stuPsw;

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case 0:

                    login((bean)msg.obj );

                    break;
                case 1:

                    isExist((bean)msg.obj );

                    break;

                default:

                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        edit_StuId=(EditText)findViewById(R.id.stuid);
        edit_PSW=(EditText)findViewById(R.id.psw);
        btn_Login=(Button)findViewById(R.id.btn_login);
        layout_stuid=(TextInputLayout)findViewById(R.id.stuidWarpper);
        layout_psw=(TextInputLayout)findViewById(R.id.pswWarpper);
        btn_Login.setOnClickListener(this);
    }
    public void onClick(View v)
    {
        stuId = layout_stuid.getEditText().getText().toString();
        stuPsw = layout_psw.getEditText().getText().toString();
        Log.d("selectdor",stuId+" "+stuPsw);
        final String address = "https://api.mysspku.com/index.php/V1/MobileCourse/Login?username="+stuId+"&password="+stuPsw;
        queryInternet(address);
    }

    public void login(bean b)
    {
        if(b.getErrcode().equals("0"))
        {
            final String address_personalInfo = "https://api.mysspku.com/index.php/V1/MobileCourse/getDetail?stuid="+stuId;
            Log.d("dormSelect" , address_personalInfo);
            queryInternet(address_personalInfo);
        }
        else
        {
            Toast.makeText(MainActivity.this , "学号或密码错误" , Toast.LENGTH_LONG).show();
        }
    }

    public void isExist(bean b)
    {
        if(b.getErrcode().equals("0"))
        {
            myApplication application=(myApplication)this.getApplicationContext();
            application.setStuinfo(b);
            Intent i = new Intent(MainActivity.this, personInfo.class);
            startActivity(i);
            this.finish();
        }
        else
        {
            Toast.makeText(MainActivity.this , "学号或密码错误" , Toast.LENGTH_LONG).show();
        }
    }

    public void queryInternet(final String address)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run() {
                //API接口
                HttpsURLConnection con = null;
                try
                {
                    SSLContext sc = SSLContext.getInstance("TLS");
                    sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                    HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());

                    URL url = new URL(address);
                    con = (HttpsURLConnection) url.openConnection();

                    con.setRequestMethod("GET");
                    con.setConnectTimeout(8000);
                    con.setReadTimeout(8000);

                    InputStream in = con.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(in));
                    StringBuilder response = new StringBuilder();

                    String str;
                    while ( (str = reader.readLine()) != null )
                    {
                        response.append(str);
                    }
                    String jsonData = response.toString();

                    Log.d("dormSelect" , jsonData);
                    //调用解析方法
                    bean result=new bean();
                    result=parseJSON(jsonData);
                    Log.d("dormSelect" , "3");
                    Message msg = new Message();
                    if(result.getData().size()>1)
                    {
                        Log.d("dormSelect" , "2");
                        msg.what = 1;
                    }
                    else {
                        Log.d("dormSelect" , "1");
                        msg.what = 0;
                    }
                    msg.obj = result;
                    mHandler.sendMessage(msg);
                }
                catch (Exception e)
                {
                    Log.d("dormSelect", "error");
                    e.printStackTrace();
                }
                finally
                {
                    if (con != null)
                    {
                        con.disconnect();
                    }
                }

            }
        }).start();//将该线程加入资源等待队列
    }

    private bean parseJSON(String jsonData)
    {
        bean res=new bean();
        try
        {
            JSONTokener jsonParser = new JSONTokener(jsonData);
            // 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
            JSONObject loginfos = (JSONObject) jsonParser.nextValue();
            // 接下来的就是JSON对象的操作了
            String errcode=loginfos.getString("errcode");
            res.setErrcode(errcode);
            String datas=loginfos.getString("data");
            JSONTokener jsonParser_data = new JSONTokener(datas);
            // 此时还未读取任何json文本，直接读取就是一个JSONObject对象。
            JSONObject loginfo_res = (JSONObject) jsonParser_data.nextValue();
            Dictionary<String,String> res_data=new Hashtable<>();
            Log.d("selectdor",jsonData);
            Iterator it=loginfo_res.keys();
            while (it.hasNext())
            {
                String str=(String) it.next();
                res_data.put(str,loginfo_res.getString(str));
                Log.d("selectdor",str+","+loginfo_res.getString(str));
            }
            res.setData(res_data);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        return res;
    }
}

class MyTrustManager implements X509TrustManager
{
    @Override
    public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        // TODO Auto-generated method stub
    }
    @Override
    public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        // TODO Auto-generated method stub
    }
    @Override
    public X509Certificate[] getAcceptedIssuers() {
        // TODO Auto-generated method stub
        return null;
    }

}
class MyHostnameVerifier implements HostnameVerifier {
    @Override
    public boolean verify(String hostname, SSLSession session) {
        // TODO Auto-generated method stub
        return true;
    }

}

