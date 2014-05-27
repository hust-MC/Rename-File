package com.Emercy.rename;

import java.io.File;
import java.text.ParseException;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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

	public void widget_init()
	{
		button = (Button) findViewById(R.id.start);         //initial all the widgets
	}

	public void setListener()
	{
		button.setOnClickListener(new OnClickListener()
		{
			@Override
			public void onClick(View v)                    //Listen to the start button
			{
				Thread renaming = new Thread(new Runnable()
				{
					@Override
					public void run()
					{
						//						new Handler().postDelayed(new Runnable()
						//						{
						//							@Override
						//							public void run()
						//							{
						rename();
						renameFlag = true;
						//							}
						//						}, 3000);
					}
				});
				renaming.start();
				AlertDialog dialog = new AlertDialog.Builder(MainActivity.this)
						.setView(rename_layout).show();
				while (!renameFlag)
					;
				dialog.dismiss();
			}
		});
	}

	public void rename()                                //start to rename
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

			//			Toast.makeText(this, "修改成功", Toast.LENGTH_SHORT).show();
		}
		else
		{
			//			Toast.makeText(this, "未查找到目录", Toast.LENGTH_SHORT).show();
		}
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		inflater = (LayoutInflater) getSystemService(this.LAYOUT_INFLATER_SERVICE);
		rename_layout = (LinearLayout) inflater
				.inflate(R.layout.renaming, null);
		widget_init();
		setListener();

		handler = new Handler()                               //receive messages from the thread
		{
			@Override
			public void handleMessage(Message msg)
			{
				super.handleMessage(msg);
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
