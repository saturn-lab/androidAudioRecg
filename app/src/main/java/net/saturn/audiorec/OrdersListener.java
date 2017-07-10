package net.saturn.audiorec;

import android.app.Activity;
import android.app.AlertDialog;
import android.view.View;

/**
 * Created by Administrator on 2017/7/8.
 */

public class OrdersListener implements View.OnClickListener {
    public MainActivity activity;
    static public String message =
            "蓝牙开机, 蓝牙拨打电话, 蓝牙打电话, 蓝牙接听电话,\n" +
            "蓝牙接电话, 蓝牙拒接, 蓝牙播放音乐, 蓝牙开始音乐,\n" +
            "蓝牙暂停音乐, 蓝牙停止音乐, 蓝牙上一首, 蓝牙上一曲,\n" +
            "蓝牙下一首, 蓝牙下一曲, 蓝牙音量增大, 蓝牙声音增大,\n" +
            "蓝牙音量增加, 蓝牙声音增加, 蓝牙音量减小, 蓝牙声音减小,\n" +
            "蓝牙关机, 蓝牙电量提醒, 蓝牙还剩多少电, 蓝牙还剩多少电量";

    public OrdersListener(MainActivity activity) {
        this.activity = activity;
    }
    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.orders){
            AlertDialog.Builder dialog = new AlertDialog.Builder(this.activity);
            dialog.setTitle("指令列表")
                    .setMessage(OrdersListener.message)
                    .setPositiveButton("了解", null)
                    .show();
        }
    }
}
