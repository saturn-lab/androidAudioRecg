package net.saturn.audiorec;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;

/**
 * Created by Administrator on 2017/7/8.
 */

public class AboutListener implements View.OnClickListener {
    public MainActivity activity;
    static public String message = "本安卓应用用于清华大学iCenter课程项目演示。\n（" +
            "课号：01510202； 课名：智能硬件与智能系统）\n（课号：01510243； 课名： 大数据与机器智能）\n\n" +
            "智能系统实验室长期招收本校实习学生和SRT学生，从事大数据与深度学习方向科研。有意向同学请联系我们\n\n" +
            "清华大学iCenter智能系统实验室版权所有\n" +
            "http://net.icenter.tsinghua.edu.cn";



    public AboutListener(MainActivity activity) {
        this.activity = activity;
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.about){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this.activity);
            dialog.setTitle("关于")
                    .setMessage(AboutListener.message)
                    .setPositiveButton("了解", null)
                    .show();
        }
    }
}
