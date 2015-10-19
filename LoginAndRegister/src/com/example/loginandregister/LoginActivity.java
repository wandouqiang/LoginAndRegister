package com.example.loginandregister;

import android.app.Activity;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends Activity {

	private EditText name,password;
	private Button login,reset,register;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);
		
        name = (EditText) findViewById(R.id.name);
		password = (EditText) findViewById(R.id.psd);
		login = (Button) findViewById(R.id.login);
		reset = (Button) findViewById(R.id.reset);
		register=(Button)findViewById(R.id.register);
		
		login.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
 
				final Handler myHandler=new Handler(){					
					public void handleMessage(Message msg) {
						boolean b=(Boolean)msg.obj;
						if(b){
							Intent intent=new Intent(LoginActivity.this,MainActivity.class);
							Bundle bundle=new Bundle();
							bundle.putString("name", name.getText().toString());
							bundle.putString("psd", password.getText().toString());
							intent.putExtras(bundle);
							startActivity(intent);
							finish();
							Toast.makeText(LoginActivity.this, "登录成功", 1000).show();
						}else{
							Toast.makeText(LoginActivity.this, "用户名或密码错误，登录失败", 1000).show();
						}
					}
				};
				
				ConnectivityManager con=(ConnectivityManager)getSystemService(LoginActivity.CONNECTIVITY_SERVICE);  
				boolean wifi=con.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();  
				boolean internet=con.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnectedOrConnecting();  
				if(wifi|internet){  
				    //执行相关操作  
					new Thread(){
						public void run() {
							boolean b=checkUser(name.getText().toString(),password.getText().toString());						
							Message message=new Message();
							message.obj=b;
							myHandler.sendMessage(message);
						}
					}.start();	
					
				}else{  
				    Toast.makeText(getApplicationContext(),  
				            "亲，网络连了么？", Toast.LENGTH_LONG)  
				            .show();  
				}  
			}
		});
		reset.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				name.setText("");
				password.setText("");
			}
		});
		register.setOnClickListener(new OnClickListener() {			
			public void onClick(View v) {
				Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
				startActivity(intent);
				finish();
			}
		});
	}
	public boolean checkUser(String name, String psd) {		
		LoginToServer loginToServer = new LoginToServer();
		String response = loginToServer.doGet(name, psd);		
		if ("true".equals(response)) {
			return true;
		} else {
			return false;
		}
	}
}
