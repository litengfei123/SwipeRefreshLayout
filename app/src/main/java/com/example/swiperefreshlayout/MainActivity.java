package com.example.swiperefreshlayout;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.example.swiperefreshlayout.RefreshLayout.OnLoadListener;

import android.R.color;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_main);

		// 模拟一些数据
		final List<String> datas = new ArrayList<String>();
		for (int i = 0; i < 20; i++) {
			datas.add("item - " + i);
		}

		// 构造适配器
		final BaseAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, datas);
		// 获取listview实例
		ListView listView = (ListView) findViewById(R.id.listView);
		listView.setAdapter(adapter);

		// 获取RefreshLayout实例
		final RefreshLayout myRefreshListView = (RefreshLayout) findViewById(R.id.swipe_layout);

		// 设置下拉刷新时的颜色值,颜色值需要定义在xml中
		myRefreshListView.setColorSchemeResources(color.holo_blue_light, color.holo_red_light,
				color.holo_orange_light, color.holo_green_light);
		// 设置下拉刷新监听器
		myRefreshListView.setOnRefreshListener(new OnRefreshListener() {

			@Override
			public void onRefresh() {

				Toast.makeText(MainActivity.this, "refresh", Toast.LENGTH_SHORT).show();

				myRefreshListView.postDelayed(new Runnable() {

					@Override
					public void run() {
						// 更新数据
						datas.add("下拉" + new Date().toString());
						adapter.notifyDataSetChanged();
						// 更新完后调用该方法结束刷新
						myRefreshListView.setRefreshing(false);
					}
				}, 2000);
			}
		});

		// 加载监听器
		myRefreshListView.setOnLoadListener(new OnLoadListener() {

			@Override
			public void onLoad() {

				Toast.makeText(MainActivity.this, "load", Toast.LENGTH_SHORT).show();

				myRefreshListView.postDelayed(new Runnable() {

					@Override
					public void run() {
						datas.add("上拉" + new Date().toString());
						adapter.notifyDataSetChanged();
						// 加载完后调用该方法
						myRefreshListView.setLoading(false);
					}
				}, 2000);

			}
		});
	}

}