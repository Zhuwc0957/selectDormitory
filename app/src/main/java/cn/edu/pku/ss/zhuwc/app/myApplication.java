package cn.edu.pku.ss.zhuwc.app;


import android.app.Application;

/**
 * Created by ZhuWC on 2017/12/2.
 */

public class myApplication extends Application {
    public void onCreate()
    {
        super.onCreate();
    }

    private String name;
    private String id;
    private String gender;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }
}
