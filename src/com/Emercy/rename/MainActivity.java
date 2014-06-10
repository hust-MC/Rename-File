package com.Emercy.rename;

import java.io.File;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends Activity
{

	File file, f;
	File[] s;
	Button button;
	String path, name;
	Thread t;
	static LayoutInflater inflater;
	LinearLayout rename_layout;
	Boolean renameFlag = false;
	Handler handler;
	AlertDialog dialog;

	public void widget_init()                                         //initial all the widgets
	{
		button = (Button) findViewById(R.id.start);
	}

	public void setListener()                                         //set all Listeners
	{
		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)
			{
				inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
				rename_layout = (LinearLayout) inflater
						.inflate(R.layout.renaming, null);
				Thread renaming = new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						Message msg = Message.obtain();
						msg.arg1 = rename();
						handler.sendMessage(msg);
					}
				});
				renaming.start();
				dialog = new AlertDialog.Builder(MainActivity.this)
						.setView(rename_layout).show();
			}
		});
	}

	public int rename()
	{
		path = "/sdcard/UCDownloads/cache/htt/";
		file = new File(path);
		if (file.exists())
		{
			s = file.listFiles();

			for (int i = 0; i < s.length; i++)
			{
				name = s[i].getName();
				if (!name.endsWith(".jpg"))
				{
					s[i].renameTo(new File(path + name + ".jpg"));
				}
			}
			return 1;
		}
		else
		{
			return 0;
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		widget_init();
		setListener();

		handler = new Handler()
		{
			@Override
			public void handleMessage(Message msg)
			{
				new Handler().postDelayed(new Runnable()
				{
					@Override
					public void run()
					{
						dialog.dismiss();
					}
				}, 1000);
				super.handleMessage(msg);
				if(msg.arg1 == 1)
				{
					Toast.makeText(MainActivity.this, "转换完毕", Toast.LENGTH_SHORT).show();
					
				}
				else
				{
					Toast.makeText(MainActivity.this, "转换失败，没有发现目标文件", Toast.LENGTH_SHORT).show();
				}
			}
		};

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}
}
