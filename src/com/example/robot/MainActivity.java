package com.example.robot;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity implements GetDataListener,OnItemLongClickListener
{
	private ListView listView;
	private List<ContentBean> list;
	private ContentBean bean;
	private EditText editText;	
	private MyAdapter adapter;
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		listView = (ListView) findViewById(R.id.lv);
		editText = (EditText) findViewById(R.id.edittext);
		list = new ArrayList<ContentBean>();
		adapter = new MyAdapter(list, MainActivity.this);
		bean = new ContentBean();
		bean.content = getRandomWelcome();
		bean.FLAG = 2;
		list.add(bean);
		listView.setAdapter(adapter);
	}

	private String getRandomWelcome()
	{
		String[] arrary;
		arrary = this.getResources().getStringArray(R.array.arrary);
		int index = (int) (Math.random() * (arrary.length));
		return arrary[index];
	}

	public void click(View v)
	{
		if(editText.getText().toString().equals(""))
			return;
		bean = new ContentBean();
		String str = editText.getText().toString();
		editText.setText("");
		bean.content = str;
		bean.FLAG = 1;
		list.add(bean);
		adapter.notifyDataSetChanged();
		String nospace = str.replace(" ", "");
		String noenter = nospace.replace("\n", "");
		new MyAsyncTask(this).execute(noenter);
	}
	@Override
	public void getData(String str)
	{
		bean = new ContentBean();
		bean.content = str;
		bean.FLAG = 2;
		list.add(bean);
		adapter.notifyDataSetChanged();
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3)
	{
		ClipboardManager cmb = (ClipboardManager)this.getSystemService(Context.CLIPBOARD_SERVICE);
		TextView tv = (TextView) arg1.findViewById(R.id.tv);
        cmb.setText(tv.getText().toString());
		
        //ToastUtil.show("已复制");
        return false;

	}
}
