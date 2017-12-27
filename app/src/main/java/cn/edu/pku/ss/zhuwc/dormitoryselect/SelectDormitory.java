package cn.edu.pku.ss.zhuwc.dormitoryselect;

import android.app.Activity;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONObject;
import org.json.JSONTokener;
import org.w3c.dom.Text;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.StringTokenizer;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;

import cn.edu.pku.ss.zhuwc.app.myApplication;
import cn.edu.pku.ss.zhuwc.bean.bean;
import cn.edu.pku.ss.zhuwc.bean.myArrayAdapter;

public class SelectDormitory extends Activity  implements View.OnClickListener{
    private TextView title,stuname,stuid,stugender;
    private ImageView btn_back,fg1,fg2,fg3,fg4,fg5,fg6,fg7;
    private TableRow  stu1id,stu1code,stu2id,stu2code,stu3id,stu3code,tongzhuren;
    private TextView build1,build2,build3,build4,build5;
    private EditText s1id,s1code,s2id,s2code,s3id,s3code;
    private Button btn_action;
    private Spinner spinner;

    private Handler mHandler = new Handler()
    {
        public void handleMessage(android.os.Message msg)
        {
            switch (msg.what)
            {
                case 0:
                    updatedorm((bean)msg.obj );
                    break;
                case 1:
                    checkRes((bean)msg.obj);
                    Log.d("dormSelect","sucess");
                    break;
                case 3:
                    checkGender((bean)msg.obj);
                default:

                    break;
            }
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_dormitory);
        btn_action=(Button)findViewById(R.id.btn_actions);
        stuname=(TextView)findViewById(R.id.stuname);
        stuid=(TextView)findViewById(R.id.sec_stuid);
        stugender=(TextView)findViewById(R.id.sec_sex);
        stu1id=(TableRow)findViewById(R.id.row_stu1);
        stu1code=(TableRow)findViewById(R.id.row_stu1_code);
        stu2id=(TableRow)findViewById(R.id.row_stu2);
        stu2code=(TableRow)findViewById(R.id.row_stu2_code);
        stu3id=(TableRow)findViewById(R.id.row_stu3);
        stu3code=(TableRow)findViewById(R.id.row_stu3_code);
        tongzhuren=(TableRow)findViewById(R.id.tongzhu);
        title=(TextView)findViewById(R.id.title_mode_name);
        btn_back=(ImageView)findViewById(R.id.btn_back_sec);
        s1id=(EditText)findViewById(R.id.stu1id);
        s2id=(EditText)findViewById(R.id.stu2id);
        s3id=(EditText)findViewById(R.id.stu3id);
        s1code=(EditText)findViewById(R.id.stu1code);
        s2code=(EditText)findViewById(R.id.stu2code);
        s3code=(EditText)findViewById(R.id.stu3code);
        fg1=(ImageView)findViewById(R.id.fenge1);
        fg2=(ImageView)findViewById(R.id.fenge2);
        fg3=(ImageView)findViewById(R.id.fenge3);
        fg4=(ImageView)findViewById(R.id.fenge4);
        fg5=(ImageView)findViewById(R.id.fenge5);
        fg6=(ImageView)findViewById(R.id.fenge6);
        fg7=(ImageView)findViewById(R.id.fenge7) ;
        build1=(TextView)findViewById(R.id.build5);
        build2=(TextView)findViewById(R.id.build8);
        build3=(TextView)findViewById(R.id.build9);
        build4=(TextView)findViewById(R.id.build13);
        build5=(TextView)findViewById(R.id.build14);
        spinner=(Spinner)findViewById(R.id.spinner);
        myApplication application=(myApplication)getApplicationContext();
        stuname.setText(application.getStuinfo().getData().get("name"));
        stuid.setText(application.getStuinfo().getData().get("studentid"));
        stugender.setText(application.getStuinfo().getData().get("gender"));
        String getDormInfo="https://api.mysspku.com/index.php/V1/MobileCourse/getRoom?gender=";
        if(application.getStuinfo().getData().get("gender").equals("男"))
            getDormInfo+="1";
        else
            getDormInfo+="2";
        queryInternet(getDormInfo);
        btn_back.setOnClickListener(this);
        btn_action.setOnClickListener(this);
        initview();
        s1id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==10)
                {String str="https://api.mysspku.com/index.php/V1/MobileCourse/getDetail?stuid="+s1id.getText();
                queryInternet(str);}
            }
        });
        s2id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==10)
                {String str="https://api.mysspku.com/index.php/V1/MobileCourse/getDetail?stuid="+s2id.getText();
                    queryInternet(str);}
            }
        });
        s3id.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(s.toString().length()==10)
                {String str="https://api.mysspku.com/index.php/V1/MobileCourse/getDetail?stuid="+s3id.getText();
                    queryInternet(str);}
            }
        });
    }

    public void getStuGender(String id)
    {

    }
    public void initview()
    {
        int mode=getIntent().getIntExtra("mode",1);
        switch (mode)
        {
            case 1:
                title.setText("单人办理");
                fg1.setVisibility(View.GONE);
                fg2.setVisibility(View.GONE);
                fg3.setVisibility(View.GONE);
                fg4.setVisibility(View.GONE);
                fg5.setVisibility(View.GONE);
                fg6.setVisibility(View.GONE);
                fg7.setVisibility(View.GONE);
                stu1id.setVisibility(View.GONE);
                stu2id.setVisibility(View.GONE);
                stu3id.setVisibility(View.GONE);
                stu1code.setVisibility(View.GONE);
                stu2code.setVisibility(View.GONE);
                stu3code.setVisibility(View.GONE);
                tongzhuren.setVisibility(View.GONE);
                break;
            case 2:
                title.setText("多人办理");
                fg4.setVisibility(View.GONE);
                fg5.setVisibility(View.GONE);
                fg6.setVisibility(View.GONE);
                fg7.setVisibility(View.GONE);
                stu2id.setVisibility(View.GONE);
                stu3id.setVisibility(View.GONE);
                stu2code.setVisibility(View.GONE);
                stu3code.setVisibility(View.GONE);
                break;
            case 3:
                title.setText("多人办理");
                fg6.setVisibility(View.GONE);
                fg7.setVisibility(View.GONE);
                stu3id.setVisibility(View.GONE);
                stu3code.setVisibility(View.GONE);
                break;
            case 4:
                title.setText("多人办理");
                break;
            default:
                break;
        }
    }

    public void updatedorm(bean b)
    {
        build1.setText(b.getData().get("5"));
        build2.setText(b.getData().get("8"));
        build3.setText(b.getData().get("9"));
        build4.setText(b.getData().get("13"));
        build5.setText(b.getData().get("14"));
        ArrayList<String> dorm=new ArrayList<>();
        int i=0;
        if(!b.getData().get("5").equals("0"))
            dorm.add("5");
        if(!b.getData().get("8").equals("0"))
            dorm.add("8");
        if(!b.getData().get("9").equals("0"))
            dorm.add("9");
        if(!b.getData().get("13").equals("0"))
            dorm.add("13");
        if(!b.getData().get("14").equals("0"))
            dorm.add("14");
        ArrayAdapter<String> adapter;
        adapter=new myArrayAdapter(this,dorm);
        spinner.setAdapter(adapter);
    }

    public void checkRes(bean b)
    {
        if(b.getErrcode().equals("0"))
        {
            Intent i=new Intent(SelectDormitory.this,selectResult.class);
            startActivity(i);
            finish();
        }
        else
            Toast.makeText(SelectDormitory.this , "信息填写有误" , Toast.LENGTH_LONG).show();
    }

    public void checkGender(bean b)
    {
        if(!b.getData().get("gender").equals(stugender.getText()))
        {
            Toast.makeText(SelectDormitory.this,"学号不存在或性别不符", Toast.LENGTH_LONG).show();
            s1id.setText("");
            s1id.setHint("请输入合法学号");
            btn_action.setEnabled(false);
        }
        else {
            btn_action.setEnabled(true);
        }
    }
    public String getParm()
    {
        int num=getIntent().getIntExtra("mode",1);
        String src = "{\n" + "\"errcode\":0,\n" + "\"data\":{\n" + "\"num\":" + num + ",\n" + "\"stuid\":" + stuid.getText() + ",\n";
        switch (num){
            case 1:
                break;
            case 2:
                src+="\"stu1id\":" + s1id.getText() + ",\n"+"\"v1code\":"+s1code.getText()+",\n";
                break;
            case 3:
                src+="\"stu1id\":" + s1id.getText() + ",\n"+"\"v1code\":"+s1code.getText()+",\n"+"\"stu2id\":" + s2id.getText() + ",\n"+"\"v2code\":"+s2code.getText()+",\n";
                break;
            case 4:
                src+="\"stu1id\":" + s1id.getText() + ",\n"+"\"v1code\":"+s1code.getText()+",\n"+"\"stu2id\":" + s2id.getText() + ",\n"+"\"v2code\":"+s2code.getText()+",\n"+"\"stu3id\":" + s3id.getText() + ",\n"+"\"v3code\":"+s3code.getText()+",\n";
        }
        src+= "\"buildingNo\":"+spinner.getSelectedItem()  + "\n" + "}\n" + "}";
        Log.d("dormSelect",src);
        return src;
    }

    public void onClick(View v)
    {
        switch (v.getId()){
            case R.id.btn_back_sec:
                Intent i=new Intent();
                setResult(RESULT_OK,i);
                finish();
                break;
            case R.id.btn_actions:
                int mode=getIntent().getIntExtra("mode",1);
                if(mode==2)
                {
                    //Toast.makeText(SelectDormitory.this , "1"+s1id.getText()+"12" , Toast.LENGTH_LONG).show();
                    if(s1id.getText().toString().equals("")||s1code.getText().toString().equals(""))
                    {
                        Toast.makeText(SelectDormitory.this , "同住人信息不能为空" , Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                if(mode==3)
                {
                    if(s1id.getText().toString().equals("")||s1code.getText().toString().equals("")||s2id.getText().toString().equals("")||s2code.getText().toString().equals(""))
                    {
                        Toast.makeText(SelectDormitory.this , "同住人信息不能为空" , Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                if(mode==4)
                {
                    if(s1id.getText().toString().equals("")||s1code.getText().toString().equals("")||s2id.getText().toString().equals("")||s2code.getText().toString().equals("")||s3id.getText().toString().equals("")||s3code.getText().toString().equals(""))
                    {
                        Toast.makeText(SelectDormitory.this , "同住人信息不能为空" , Toast.LENGTH_LONG).show();
                        break;
                    }
                }
                final String address="https://api.mysspku.com/index.php/V1/MobileCourse/SelectRoom";
                queryInternetpost(address);
                break;
            default:
                break;
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
                       Enumeration<String> keys=result.getData().keys();
                        boolean flag=false;
                        while(keys.hasMoreElements())
                        {
                            String str1=keys.nextElement();
                            if(str1.equals("gender"))
                            {
                                flag=true;
                                break;
                            }
                            Log.d("dormSelect" ,str1);
                        }
                        if(flag)
                            msg.what=3;
                        else
                        {
                        Log.d("dormSelect" , "2");
                        msg.what = 0;
                   }
                    }
                    else {
                        Log.d("dormSelect" , "1");
                        msg.what = 1;
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

    public void queryInternetpost(final String address)
    {
        new Thread(new Runnable()
        {
            @Override
            public void run() {
                //API接口
                HttpsURLConnection con = null;
                try
                {
                    byte[]data=getParm().getBytes();
                    SSLContext sc = SSLContext.getInstance("TLS");
                    sc.init(null, new TrustManager[]{new MyTrustManager()}, new SecureRandom());
                    HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
                    HttpsURLConnection.setDefaultHostnameVerifier(new MyHostnameVerifier());

                    URL url = new URL(address);
                    con = (HttpsURLConnection) url.openConnection();

                    con.setRequestMethod("POST");
                    con.setConnectTimeout(8000);
                    con.setReadTimeout(8000);
                    con.setDoInput(true);                  //打开输入流，以便从服务器获取数据
                    con.setDoOutput(true);                 //打开输出流，以便向服务器提交数据
                    con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    //设置请求体的长度
                    con.setRequestProperty("Content-Length", String.valueOf(data.length));
                    //获得输出流，向服务器写入数据
                    OutputStream outputStream = con.getOutputStream();
                    outputStream.write(data);
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
                    Log.d("dormSelect" , String.valueOf(result.getData()));
                    Message msg = new Message();
                    if(result.getData()!=null)
                    {
                        Log.d("dormSelect" , "2");
                        msg.what = 0;
                    }
                    else {
                        Log.d("dormSelect" , "1");
                        msg.what = 1;
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
