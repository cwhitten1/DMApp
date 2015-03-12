package com.uf.dancemarathon;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Locale;

import org.json.JSONArray;
import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.uf.dancemarathon.FontSetter.fontName;

/**
 * @author Chris Whitten This class provides the image information to the mtk
 *         grid view. See the Android developer guide for more information.
 */
public class MtkAdapter extends BaseAdapter {
	private Context mContext;
	private ArrayList<Kids> kids = new ArrayList<Kids>();
	public KidsLoader loader;
	
	// Array of kids who have Milestones on YouTube
	private String [] milestone_name = {
			"Ayden M.", "Anna Rose", 
			"Nathan F.", "Geoffrey P.", 
			"Kasey V.", "Alyssa Mu.", 
			"Hyla M.", "Ava M.", 
			"Tyler P.", "Tyler S.",
			"Catriona C.", "Jessica",
			"Alyssa Ma.", "Zander W.",
			"Michael S.", "Miranda L.",
			"Nick M."};
	
	// Milestone YouTube IDs
	private String[] milestone = {
		"Iz_TYdJE-fM", // Ayden
		"HGpu_SIposk", // Anna Rose
		"W2IOh6_uqD0", // Nathan F
		"Ae5pgULMCyw", // Geoffrey
		"pQ15zlnb2ts", // Kasey
		"X4aY3Zd_Iuw", // Alyssa Mu
		"YFdlEa-t7kw", // Hyla
		"H937Bdls_VE", // Ava
		"46J9JKLYRT0", // Tyler P.
		"DjkiKfeeIRY", // Tyler S.
		"QgdWh9dOcLI", // Catriona 
		"-0BwBQk4ZW4", // Jessica
		"zaeB-IZTCg8", // Alyssa Ma
		"tN_nf45CRUQ", // Zander
		"VBiZWCsOQuA", // Michael S
		"GZReuPztjUg", // Miranda L.
		"Lahp4X1t6kI" // Nick M.
	};


	public MtkAdapter (Context c) {
		mContext = c;
		loader= new KidsLoader();
		loader.execute();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return this.kids.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return this.kids.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		String image_name = this.kids.get(position).getImage_name()
				.toLowerCase(Locale.ENGLISH);
		image_name = image_name.replace(".png", "");
		int imageID = mContext.getResources().getIdentifier(image_name,
				"drawable", "com.example.dancemarathon");
		return imageID;
	}

	public String loadJSONFromAsset() throws IOException {
		String json = "";
		try {

			InputStream is = mContext.getAssets().open("data.json");

			BufferedReader reader = new BufferedReader(new InputStreamReader(is));
			String line = "";
			while((line = reader.readLine()) != null)
				json += line;
			

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		
		return json;

	}

	
	public Kids getKid(int position)
	{
		return this.kids.get(position);
	}
	
    /*private view holder class*/
    private class ViewHolder {
        CircleView pic = new CircleView (mContext);
        TextView name = new TextView(mContext);
    }
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
    	ViewHolder holder = null;
    	
    	Kids currKid = kids.get(position);
		int imageId = currKid.getImageId(mContext);
		
	    if (convertView == null) 
	    {	
		    LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	        convertView = inflater.inflate(R.layout.gridview_item, null);
	        holder = new ViewHolder();
	        
	        holder.name = (TextView) convertView.findViewById(R.id.grid_kid_name);
	        holder.pic = (CircleView) convertView.findViewById(R.id.grid_kid_pic);
	        
	 		convertView.setTag(holder);
	    } 
	    else 
	    {
	    	holder = (ViewHolder) convertView.getTag();
	    }
	    
	    holder.pic.setImageResource(imageId);
        holder.name.setText(this.kids.get(position).getName());
        
        // Set font
        FontSetter.setFont(mContext, fontName.P, holder.name);
        
        // Set orange border for even positions, blue for odd
 		if (position % 2 == 0) {
 			holder.pic.setBorderColor(mContext.getResources().getColor(R.color.dm_orange_primary));
 		} 
 		else
 		{
 			holder.pic.setBorderColor(mContext.getResources().getColor(R.color.dm_blue_primary));
 		}

	    return convertView;
	}
	
	
	private class KidsLoader extends AsyncTask<Void, Void, Void>
	 {
		@Override
		protected Void doInBackground(Void... params) {
			ParseTheKids();
			return null;
		}

		/* (non-Javadoc)
		 * @see android.os.AsyncTask#onPostExecute(java.lang.Object)
		 */
		@Override
		protected void onPostExecute(Void result) {	
			
		}
		
		private void ParseTheKids() {
			
			ArrayList<Kids> loadedKids = new ArrayList<Kids>();
			try {
				JSONArray data_arr = new JSONArray(loadJSONFromAsset());

				for (int i = 0; i < data_arr.length(); i++) 
				{
					String image_name = data_arr.getJSONObject(i)
							.getString("image");
					String story = data_arr.getJSONObject(i).getString("story");
					String name = data_arr.getJSONObject(i).getString("name");
					int age = Integer.parseInt(data_arr.getJSONObject(i).getString(
							"ageYear"));
					String youtube_id = "";
					
					for(int j = 0; j < milestone_name.length; j++)
					{
						if (name.equals(milestone_name[j]))
						{
							youtube_id = milestone[j];
							break;
						}
					}
					
					if (story.length() < 5)
					{
						story = "No story available.";
					}

					
					Kids k = new Kids(name, age, story, image_name, youtube_id);
					loadedKids.add(k);
					
					
				}
				
				// Alphabetizes arraylist by name 
				Collections.sort(loadedKids, Kids.COMPARE_BY_NAME);
				
			
				for(int i=0; i < loadedKids.size(); i++)
				{
					Thread.sleep(20);
					kids.add(loadedKids.get(i));
					
					((Activity) mContext).runOnUiThread(new Runnable(){
	
						@Override
						public void run() {
							// TODO Auto-generated method stub
							MtkAdapter.this.notifyDataSetChanged();
						}
						
					});		
				}
				
			} 
			catch (IOException e) {
				e.printStackTrace();
			} 
			catch (JSONException e) {
				e.printStackTrace();
			} 
			catch (InterruptedException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			catch (ParseException e)
			{
				e.printStackTrace();
			}
			
		}
		
	 }
}
