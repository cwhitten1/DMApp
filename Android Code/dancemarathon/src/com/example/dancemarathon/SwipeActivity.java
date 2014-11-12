package com.example.dancemarathon;

import java.util.Locale;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

public class SwipeActivity extends ActionBarActivity
{

	/**
	 * The {@link android.support.v4.view.PagerAdapter} that will provide
	 * fragments for each of the sections. We use a {@link FragmentPagerAdapter}
	 * derivative, which will keep every loaded fragment in memory. If this
	 * becomes too memory intensive, it may be best to switch to a
	 * {@link android.support.v4.app.FragmentStatePagerAdapter}.
	 */
	SectionsPagerAdapter mSectionsPagerAdapter;

	/**
	 * The {@link ViewPager} that will host the section contents.
	 */
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_swipe);
		
		// Hide actionbar
		this.getSupportActionBar().hide();
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the activity.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());

		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		
		// Set the ViewPager to the center
		mViewPager.setCurrentItem(1, false);
		
		// Change PagerTabBar spacing
		PagerTabStrip tabStrip = (PagerTabStrip) findViewById(R.id.pager_title_strip);
		tabStrip.setTextSpacing(1);
		tabStrip.setTextSize(TypedValue.COMPLEX_UNIT_PT, 7);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.swipe, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings)
		{
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
	 * one of the sections/tabs/pages.
	 */
	public class SectionsPagerAdapter extends FragmentStatePagerAdapter
	{

		public SectionsPagerAdapter(FragmentManager fm)
		{
			super(fm);
		}

		@Override
		public Fragment getItem(int position)
		{
			// getItem is called to instantiate the fragment for the given page.
			// Return the fragment that corresponds to the position
			switch (position)
			{
			case 0:return TimelineFragment.newInstance();
			case 1:return HomeFragment.newInstance();
			case 2:return MtkFragment.newInstance();
			}
			return null;
		}

		@Override
		public int getCount()
		{
			// Show 3 total pages.
			return 3;
		}

		@Override
		public CharSequence getPageTitle(int position)
		{
			Locale l = Locale.getDefault();
			switch (position)
			{
			case 0:return getString(R.string.title_section1).toUpperCase(l);
			case 1:return getString(R.string.title_section2).toUpperCase(l);
			case 2:return getString(R.string.title_section3).toUpperCase(l);
			}
			return null;
		}
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment
	{
		/**
		 * The fragment argument representing the section number for this
		 * fragment.
		 */
		private static final String ARG_SECTION_NUMBER = "section_number";

		/**
		 * Returns a new instance of this fragment for the given section number.
		 */
		public static PlaceholderFragment newInstance(int sectionNumber)
		{
			PlaceholderFragment fragment = new PlaceholderFragment();
			Bundle args = new Bundle();
			args.putInt(ARG_SECTION_NUMBER, sectionNumber);
			fragment.setArguments(args);
			return fragment;
		}

		public PlaceholderFragment()
		{
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState)
		{
			View rootView = inflater.inflate(R.layout.fragment_swipe,
					container, false);
			return rootView;
		}

	}
	
	// onClick method to
	public void openLink(View view)
	{
		// Get media type from tag
	    String media = (String)view.getTag();

	    Intent intent = new Intent();
	    
	    // Associate the respective intent with the social media
	    if (media.equals("Facebook"))
	    {
	    	intent = getOpenFacebookIntent(this);
	    	
	    }
	    else if (media.equals("Twitter"))
	    {
	    	intent = getOpenTwitterIntent(this);
	    	
	    }
	    else if (media.equals("Instagram"))
	    {
	    	intent = getOpenInstagramIntent(this);
	    }
	    else if (media.equals("YouTube"))
	    {
	    	intent = getOpenYouTubeIntent(this);
	    }
	    else
	    {
	    	intent = new Intent(Intent.ACTION_VIEW, Uri.parse(media));
	    }
	    startActivity(intent);
	}

	
	// Intent to open Facebook in either its respective app or the browser
	public static Intent getOpenFacebookIntent(Context context)
	{
		// Open Facebook page in Facebook app
		try
		{
			context.getPackageManager().getPackageInfo("com.facebook.katana", 0);
			return new Intent(Intent.ACTION_VIEW, Uri.parse("fb://page/116374146706"));
		}
		// Open Facebook page in browser
		catch (Exception e)
		{
			return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/floridaDM"));
		}
	}
	
	// Intent to open Twitter in either its respective app or the browser
	public static Intent getOpenTwitterIntent(Context context)
	{
		// Open Twitter profile in Twitter app
		try
		{
		    context.getPackageManager().getPackageInfo("com.twitter.android", 0);
		    return new Intent(Intent.ACTION_VIEW, Uri.parse("twitter://user?user_id=34755385"));
		}
		// Open Twitter profile in browser
		catch (Exception e)
		{
		    return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.twitter.com/floridadm"));
		}
	}
	
	// Intent to open Instagram in either its respective app or the browser
	public static Intent getOpenInstagramIntent(Context context)
	{
		// Open Instagram profile in Instagram app
		try{
			context.getPackageManager().getLaunchIntentForPackage("com.instagram.android");
			return new Intent(Intent.ACTION_VIEW, Uri.parse("http://instagram.com/dmatuf"));
		}
		// Open Instagram profile in browser
		catch (Exception e)
		{
			return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.instagram.com/dmatuf"));
		}
	}
	
	// Intent to open YouTube in either its respective app or the browser
	public static Intent getOpenYouTubeIntent(Context context)
	{
		
		// Open YouTube channel in YouTube app
		try
		{
			context.getPackageManager().getPackageInfo("com.google.android.youtube", 0);
			return new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:"));
		}
		// Open YouTube channel in browser
		catch (Exception e)
		{
			return new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.youtube.com/user/UFDanceMarathon"));
		}
	}
}

