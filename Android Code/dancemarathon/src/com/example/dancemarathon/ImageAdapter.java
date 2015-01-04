package com.example.dancemarathon;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Map;

import xmlwise.Plist;
import android.content.Context;
import android.support.v7.internal.widget.ActionBarOverlayLayout.LayoutParams;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
/**
 * @author Chris Whitten
 * This class provides the image information to the mtk grid view. See the Android developer guide for more information.
 */
public class ImageAdapter extends BaseAdapter
{
	private Context mContext;
	private ArrayList<Kids> kidIds = new ArrayList<Kids>();
	
	public ImageAdapter(Context c)
	{
		mContext = c;
	}

	@Override
	public int getCount()
	{
		// TODO Auto-generated method stub
		return imageIds.length;
	}

	@Override
	public Object getItem(int position)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position)
	{
		// TODO Auto-generated method stub
		return imageIds[position];
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent)
	{	
		
		TextView textView = new TextView(mContext);
		CircleView imageView;
        if (convertView == null) 
        {  // if it's not recycled, initialize some attributes
            textView.setText("Name" + position);
        	
        	imageView = new CircleView(mContext);
            imageView.setLayoutParams(new GridView.LayoutParams(100, 100));
            imageView.setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            imageView.setPadding(1, 1, 1, 1);
        }
        
        else
        {
            imageView = (CircleView) convertView;
        }
        
        imageView.setImageResource(imageIds[position]);
        
        if (position % 2 == 0)
        {
        	imageView.setBorderColor(0x99F37021);
        }
        else
        {
        	imageView.setBorderColor(0x99014083);
        }
        
        
        return imageView;
	}
	
	public ArrayList<Kids> test()
	{
		Map<String, Object> kid = null;
		try
		{
			InputStream inputStream = mContext.getResources().openRawResource(R.raw.kids);
			BufferedReader br = null;
			try	
			{
				br = new BufferedReader(new InputStreamReader(inputStream));
				StringBuilder sb = new StringBuilder();
				String line;
				while ((line = br.readLine()) != null) 
				{
					sb.append(line);
				}
				
				kid = Plist.fromXml(sb.toString());
				
				for (Map.Entry<String, Object> entry : kid.entrySet()) 
				{
					kidIds.add((Kids)entry.getValue());
				}
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			} 
			finally 
			{
				br.close();
			}
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
		}
		
		return kidIds;
	}
	
	private Integer[] imageIds = {
			R.drawable.aleahn, 		R.drawable.alisonj,
			R.drawable.alysiag, 	R.drawable.alyssama,
			R.drawable.alyssamu,	R.drawable.annarose,
			R.drawable.avam, 		R.drawable.aydenm,
			R.drawable.baileyw, 	R.drawable.brightfamily,
			R.drawable.caitlynd,	R.drawable.camdena,
			R.drawable.catherinem, 	R.drawable.catrionac,
			R.drawable.cierrak, 	R.drawable.garrettl,
			R.drawable.geoffreyp, 	R.drawable.hylam,
			R.drawable.izabellan, 	R.drawable.jackm,
			R.drawable.jakec,		R.drawable.jennad,
			R.drawable.joshuaw,		R.drawable.kaedynb,
			R.drawable.kaseyv,		R.drawable.kayleee,
			R.drawable.kendalll,	R.drawable.madisong,
			R.drawable.masonh,		R.drawable.mattewc,
			R.drawable.michaels,	R.drawable.mirandal,
			R.drawable.nathanf,		R.drawable.nathanw,
			R.drawable.nickm,		R.drawable.randyg,
			R.drawable.ryant,		R.drawable.sagep,
			R.drawable.samn,		R.drawable.tylerp,
			R.drawable.tylers,		R.drawable.williamc,
			R.drawable.wyattt,		R.drawable.zanderw

	};
}
