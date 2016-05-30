package com.example.robot;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import android.os.AsyncTask;

public class MyAsyncTask extends AsyncTask<String, Void, String>
{
	private String httpUrl = "http://www.tuling123.com/openapi/api?key=998b2b06f27b018cd039005f6a3f6039&info=";
	private HttpClient mHttpClient;
	private HttpGet mHttpGet;
	private HttpResponse mHttpResponse;
	private HttpEntity mHttpEntity;
	private InputStream in;
	private GetDataListener listener;
	public MyAsyncTask(GetDataListener listener)
	{
		this.listener = listener;
	}
	@Override
	protected String doInBackground(String... params)
	{
		String url = httpUrl + params[0];
		try
		{
			mHttpClient = new DefaultHttpClient();
			mHttpGet = new HttpGet(url);
			mHttpResponse = mHttpClient.execute(mHttpGet);
			mHttpEntity = mHttpResponse.getEntity();
			in = mHttpEntity.getContent();
			BufferedReader br = new BufferedReader(new InputStreamReader(in));
			String line = null;
			StringBuffer sb = new StringBuffer();
			while ((line = br.readLine()) != null)
			{
				sb.append(line);
			}
			JSONObject jsonStr = new JSONObject(sb.toString());
			String text = jsonStr.getString("text");
			if(jsonStr.getString("code").equals("302000") || jsonStr.getString("code").equals("308000"))
			{
				JSONArray list = jsonStr.getJSONArray("list");
				String detailurl = list.getJSONObject(0).getString("detailurl");
				text += detailurl;
			}
			else if(jsonStr.getString("code").equals("200000"))
			{
				String picURL = jsonStr.getString("url");
				text += picURL;
			}
			return text;
		} catch (Exception e)
		{
		}

		
		return "";

	}

	@Override
	protected void onPostExecute(String result)
	{
		// TODO Auto-generated method stub
		listener.getData(result);
		super.onPostExecute(result);
	}
}
