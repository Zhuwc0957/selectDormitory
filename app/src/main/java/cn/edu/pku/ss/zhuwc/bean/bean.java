package cn.edu.pku.ss.zhuwc.bean;

import java.util.Dictionary;

/**
 * Created by ZhuWC on 2017/11/29.
 */

public class bean {
    private String errcode;
    private Dictionary<String,String> data;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public Dictionary<String, String> getData() {
        return data;
    }

    public void setData(Dictionary<String, String> data) {
        this.data = data;
    }

}
