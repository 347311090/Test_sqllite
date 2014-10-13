package com.example.test_sqllite;

import android.os.Bundle;
import android.app.Activity;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	EditText ed;
	Button cun,du;
	TextView tv;
	String insert;
	int result=0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ed=(EditText) findViewById(R.id.editText1);
		cun=(Button) findViewById(R.id.button1);
		du=(Button) findViewById(R.id.button2);
		tv=(TextView) findViewById(R.id.textView1);
		final SQLiteDatabase liteDatabase=this.openOrCreateDatabase("data.db", MODE_PRIVATE, null);
		String create="create table if not exists biao(_id Integer primary key autoincrement,data text)";
		liteDatabase.execSQL(create);
		//insert="insert into biao(_id,data) values(1,'通过sql插入数据')";
		//liteDatabase.execSQL(insert);
		cun.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String s=ed.getText().toString();
				insert="insert into biao(data) values('" +s+
								"')";
				Log.i("TGA", ""+insert);
				liteDatabase.execSQL(insert);
				Cursor cur=liteDatabase.rawQuery("select LAST_INSERT_ROWID()",null);
				cur.moveToFirst();
//				int numColumn=cur.getColumnIndex("_id");
				result=cur.getInt(0);
				Toast.makeText(MainActivity.this, "保存成功", 0).show();
				
			}
		});
		du.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				String cha="select data from biao where _id=" +result+
						"";
				Cursor cur=liteDatabase.rawQuery(cha, null);
				if(cur!=null){
					cur.moveToFirst();
					int numColumn=cur.getColumnIndex("data");
					String result=cur.getString(numColumn);
					tv.setText(result);
				}
			}
		});
		//liteDatabase.close();
	}

}
