package cn.edu.pku.ss.zhuwc.dormitoryselect;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class selectResult extends Activity implements View.OnClickListener {

    private Button btn_home;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_result);
        btn_home=(Button)findViewById(R.id.btn_back_index);
        btn_home.setOnClickListener(this);
    }

    public void onClick(View v)
    {
        Intent i=new Intent(this,personInfo.class);
        i.putExtra("type",1);
        startActivity(i);
        finish();
    }
}
