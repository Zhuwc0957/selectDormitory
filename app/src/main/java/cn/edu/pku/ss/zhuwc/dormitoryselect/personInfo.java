package cn.edu.pku.ss.zhuwc.dormitoryselect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.renderscript.ScriptGroup;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import cn.edu.pku.ss.zhuwc.app.myApplication;
import cn.edu.pku.ss.zhuwc.bean.StuInfo;
import cn.edu.pku.ss.zhuwc.bean.bean;

public class personInfo extends AppCompatActivity  implements View.OnClickListener{

    private TextView txt_name;
    private TextView txt_id;
    private TextView txt_sex;
    private TextView txt_vcode;
    private TextView txt_grade;
    private TextView txt_loc;
    private TextView txt_buliding;
    private TextView txt_room;
    private Button btn_action;
    private ImageView logout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        txt_name=(TextView) findViewById(R.id.pstu_name);
        txt_id=(TextView)findViewById(R.id.pstu_id);
        txt_sex=(TextView)findViewById(R.id.pstu_gender);
        txt_vcode=(TextView)findViewById(R.id.pstu_veriCode);
        txt_grade=(TextView)findViewById(R.id.pstu_grade);
        txt_loc=(TextView)findViewById(R.id.pstu_loc);
        txt_buliding=(TextView)findViewById(R.id.pstu_buliding);
        txt_room=(TextView)findViewById(R.id.pstu_room);
        btn_action=(Button) findViewById(R.id.btn_action);
        logout=(ImageView)findViewById(R.id.logout);
        initview();
        btn_action.setOnClickListener(this);
        logout.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        switch (v.getId()) {
            case R.id.btn_action:
            if (btn_action.getText().equals("退出"))
                this.finish();
            else {
                Intent i = new Intent(this, ModeSelect.class);
                startActivityForResult(i, 1);
            }
            break;
            case R.id.logout:
                Intent i=new Intent(this,MainActivity.class);
                startActivity(i);
                this.finish();
                break;
            default:break;
        }
    }

    protected void onActivityResult(int requestCode,int resultCode,Intent data)//子activity执行结束后回调该函数
    {
        if(requestCode==1&&resultCode==RESULT_OK)//requestCode为1即为selectcity结束后的返回
        {

        }
    }
    public void initview()
    {
        boolean flag=false;
        myApplication application=(myApplication)getApplicationContext();
        bean stuinfo = (bean) application.getStuinfo();
        Log.d("selectdor",stuinfo.getData().get("studentid"));
        txt_name.setText(stuinfo.getData().get("name"));
        txt_id.setText(stuinfo.getData().get("studentid"));
        txt_sex.setText(stuinfo.getData().get("gender"));
        txt_vcode.setText(stuinfo.getData().get("vcode"));
        txt_grade.setText(stuinfo.getData().get("grade"));
        txt_loc.setText(stuinfo.getData().get("location"));
        if(stuinfo.getData().get("building")!=null)
        {
            txt_buliding.setText(stuinfo.getData().get("building"));
            txt_room.setText(stuinfo.getData().get("room"));
            flag=true;
        }
        else
        {
            txt_buliding.setText("未分配");
            txt_room.setText("未分配");
        }
        if(flag)
        {
            btn_action.setText("退出");
        }
    }
}
