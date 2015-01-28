package com.uf.dancemarathon;

import java.io.IOException;

import org.json.JSONException;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebChromeClient;
import android.webkit.WebView;

public class GameActivity extends ActionBarActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_game);
		
		//Hide action bar
		getSupportActionBar().hide();
		
		//Get webview
		WebView gameView = (WebView) findViewById(R.id.game_view);
		gameView.getSettings().setJavaScriptEnabled(true);
		gameView.getSettings().setSupportMultipleWindows(true);
		gameView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
		gameView.setWebChromeClient(new WebChromeClient(){

			/* (non-Javadoc)
			 * @see android.webkit.WebChromeClient#onCreateWindow(android.webkit.WebView, boolean, boolean, android.os.Message)
			 */
			@Override
			public boolean onCreateWindow(WebView view, boolean isDialog,
					boolean isUserGesture, Message resultMsg) {
				// TODO Auto-generated method stub
				
				//Send new window back to the same webview in our app
				//The game uses new windows to refresh the cards
				WebView.WebViewTransport transport = (WebView.WebViewTransport) resultMsg.obj;
	            transport.setWebView(view);
	            resultMsg.sendToTarget();
	            
	            return true;
			}
			
		});
		
		//Attempt to load game
		String gameURL;
		try {
			gameURL = new ConfigFileReader(this).getSetting("gamePath");
			gameView.loadUrl(gameURL);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.game, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
