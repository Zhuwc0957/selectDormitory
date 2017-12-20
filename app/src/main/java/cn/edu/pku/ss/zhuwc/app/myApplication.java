package cn.edu.pku.ss.zhuwc.app;


import android.app.Application;

import cn.edu.pku.ss.zhuwc.bean.bean;

/**
 * Created by ZhuWC on 2017/12/2.
 */

public class myApplication extends Application {
    public void onCreate()
    {
        super.onCreate();
    }

   private bean stuinfo;

    public bean getStuinfo() {
        return stuinfo;
    }

    public void setStuinfo(bean stuinfo) {
        this.stuinfo = stuinfo;
    }
}
