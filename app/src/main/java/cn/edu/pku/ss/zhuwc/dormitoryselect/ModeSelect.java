package cn.edu.pku.ss.zhuwc.dormitoryselect;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class ModeSelect extends Activity implements View.OnClickListener{

    private ImageView btn_back;
    private TextView txt_single;
    private TextView txt_double;
    private TextView txt_trible;
    private TextView txt_four;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode_select);
        btn_back=(ImageView) findViewById(R.id.btn_back);
        txt_single=(TextView)findViewById(R.id.signle_mode);
        txt_double=(TextView)findViewById(R.id.double_mode);
        txt_trible=(TextView)findViewById(R.id.trible_mode);
        txt_four=(TextView)findViewById(R.id.four_mode);
        btn_back.setOnClickListener(this);
        txt_single.setOnClickListener(this);
        txt_double.setOnClickListener(this);
        txt_trible.setOnClickListener(this);
        txt_four.setOnClickListener(this);
    }

    @Override
    public void onClick(View v){
        Intent it=new Intent(ModeSelect.this,SelectDormitory.class);
        it=new Intent(ModeSelect.this,SelectDormitory.class);
        switch (v.getId()){
            case R.id.btn_back:
                Intent i=new Intent();
                setResult(RESULT_OK,i);
                finish();
                break;
            case R.id.signle_mode:
                it.putExtra("mode",1);
                startActivityForResult(it,1);//在一个主界面(主Activity)通过意图跳转至多个不同子Activity上去，当子模块的代码执行完毕后再次返回主页面，将子activity中得到的数据显示在主界面/完成的数据交给主Activity处理。
                break;
            case R.id.double_mode:
                it.putExtra("mode",2);
                startActivityForResult(it,1);//在一个主界面(主Activity)通过意图跳转至多个不同子Activity上去，当子模块的代码执行完毕后再次返回主页面，将子activity中得到的数据显示在主界面/完成的数据交给主Activity处理。
                break;
            case R.id.trible_mode:
                it.putExtra("mode",3);
                startActivityForResult(it,1);//在一个主界面(主Activity)通过意图跳转至多个不同子Activity上去，当子模块的代码执行完毕后再次返回主页面，将子activity中得到的数据显示在主界面/完成的数据交给主Activity处理。
                break;
            case R.id.four_mode:
                it.putExtra("mode",4);
                startActivityForResult(it,1);//在一个主界面(主Activity)通过意图跳转至多个不同子Activity上去，当子模块的代码执行完毕后再次返回主页面，将子activity中得到的数据显示在主界面/完成的数据交给主Activity处理。
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
}
