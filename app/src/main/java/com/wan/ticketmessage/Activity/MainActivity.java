package com.wan.ticketmessage.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.cy.cyrvadapter.adapter.RVAdapter;
import com.cy.cyrvadapter.recyclerview.GridRecyclerView;
import com.wan.ticketmessage.BaseActivity;
import com.wan.ticketmessage.Bean.Message;
import com.wan.ticketmessage.R;
import com.wan.ticketmessage.Util.MessageExtract;

import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends BaseActivity implements View.OnClickListener {

    private GridRecyclerView mRv;
    private FloatingActionButton mFloatbutton;
    private List<com.wan.ticketmessage.Bean.Message> messages;
    private RVAdapter<Message> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    @Override
    public void initData() {
        if (LitePal.isExist(Message.class)) {
            updateDate();
        } else {
            LitePal.getDatabase();
            messages = new ArrayList<>();
        }

       /* Message message = MessageExtract.getMessage("【智行】02月23日，玉林（11:40）-桂林，D8482抢票成功，取票号EE86285206，万兴兴 二等座05车厢08D号。");
        Message message1 = MessageExtract.getMessage("【智行】07月23日，玉林（11:40）-桂林，D8482抢票成功，取票号EE86285206，万兴兴 二等座05车厢08D号。");
        message.save();
        message1.save();*/

       /* for (int i = 0; i < 5; i++) {
            com.wan.ticketmessage.Bean.Message message = new com.wan.ticketmessage.Bean.Message("D2354" + i, "玉林" + i, "桂林", "2018/5/6 21:45", "21:52", "5车", "4D号");
            messages.add(message);
        }*/
    }
    //更新日期，判断是否过期
    private void updateDate() {
        messages = LitePal.findAll(Message.class);
        for (int i = 0; i < messages.size(); i++) {
            messages.get(i).setOutDate();
            messages.get(i).save();
        }
    }

    @Override
    public void initView() {
        mRv = findView(R.id.rv);
        mFloatbutton = findView(R.id.floatbutton);
        mFloatbutton.setOnClickListener(this);


        adapter = new RVAdapter<Message>(messages) {
            @Override
            public void bindDataToView(RVViewHolder holder, int position, Message bean, boolean isSelected) {
                holder.setText(R.id.trainNumber, bean.getTrainNumber());
                holder.setText(R.id.busNumber, bean.getBusNumber());
                holder.setText(R.id.startStation, bean.getStartStation());
                holder.setText(R.id.endStation, bean.getEndStation());
                holder.setText(R.id.startTime, bean.getStartTime());
                holder.setText(R.id.arriveTime, bean.getArriveTime());
                holder.setText(R.id.busNumber, bean.getBusNumber());
                holder.setText(R.id.seatNumber, bean.getSeatNumber());
                bean.setOutDate();
                if (bean.isOutDate()) {
                    holder.setVisible(R.id.outImg);
                } else {
                    holder.setViewGone(R.id.outImg);
                }

            }

            @Override
            public int getItemLayoutID(int position, Message bean) {
                return R.layout.item_rv;
            }

            @Override
            public void onItemClick(int position, Message bean) {

            }
        };

        mRv.setAdapter(MainActivity.this, adapter,1, RecyclerView.VERTICAL);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.item_setting:
                Toast.makeText(this, "还在开发中哦！！", Toast.LENGTH_SHORT).show();
                break;
            case R.id.item_description:
                new AlertDialog.Builder(this).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setTitle("使用说明").setMessage("复制智行官方发来的购票成功短信，点击添加按钮，粘贴，即可").show();



                break;
            case R.id.item_about:
                 new AlertDialog.Builder(this).setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setTitle("关于").setMessage("一款提取火车票短信的APP，仿一加的卡券做出来的产物，半吊子的MD设计，凑合的看看吧\n" +
                        "\n" +
                        "\n" +
                        "复制短信内容，即可添加一张火车票").show();
                break;
                default:break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.floatbutton:
                final View view = getLayoutInflater().inflate(R.layout.layout_dialog,null);
                new AlertDialog.Builder(this).setView(view).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                }).setPositiveButton("添加", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextInputEditText textInputEditText = (TextInputEditText) view.findViewById(R.id.ed);
                        getInputData(textInputEditText);
                    }
                }).setTitle("输入内容").show();
                break;
                default:break;
        }
    }

    private void getInputData(TextInputEditText textInputEditText) {
        String data = textInputEditText.getText().toString();
        if (!TextUtils.isEmpty(data)) {
            if (data.startsWith("【智行】")) {
                Message message = MessageExtract.getMessage(data);
                message.save();
                adapter.setList_bean(LitePal.findAll(Message.class));
                Toast.makeText(MainActivity.this, "添加成功！！", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "内容错误!", Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(this, "内容不能为空！！", Toast.LENGTH_SHORT).show();
        }

    }
}
