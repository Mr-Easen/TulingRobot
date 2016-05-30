package com.example.robot;

import java.util.List;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

public class MyAdapter extends BaseAdapter
{
	private LayoutInflater inflater;
	private List<ContentBean> list;
	private Context mContext;
	public MyAdapter(List<ContentBean> list,Context context)
	{
		this.list = list;
		inflater = LayoutInflater.from(context);
		this.mContext = context;
	}
	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public Object getItem(int arg0)
	{
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0)
	{
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View arg1, ViewGroup arg2)
	{
		if(list.get(arg0).FLAG == 1)
		{
			arg1 = inflater.inflate(R.layout.right, null);
		}
		if(list.get(arg0).FLAG == 2)
		{
			arg1 = inflater.inflate(R.layout.left, null);
		}
		final TextView tv = (TextView) arg1.findViewById(R.id.tv);
		tv.setText(list.get(arg0).content);
		tv.setOnLongClickListener(new OnLongClickListener()
		{
			
			@SuppressWarnings("deprecation")
			@Override
			public boolean onLongClick(View v)
			{
				ClipboardManager cmb = (ClipboardManager)mContext.getSystemService(Context.CLIPBOARD_SERVICE);
				cmb.setText(tv.getText().toString());
				/*ClipData clip = cmb.getPrimaryClip();
				cmb.setPrimaryClip(clip);*/
				Toast.makeText(mContext, "已复制到粘贴板", Toast.LENGTH_SHORT).show();
				return true;
			}
		});
		return arg1;
	}

}
