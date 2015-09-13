package com.phone1000.baike1;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.ActionBar.TabListener;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;

import com.phone1000.baike1.fragment.ListFragment;
import com.phone1000.baike1.fragment.SuggestFragment;
import com.phone1000.baike1.utils.NetUtils;

public class MainActivity extends FragmentActivity {
	
	public NetUtils netUtils=null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		netUtils=new NetUtils();
		
		final Fragment[] lfs=new Fragment[5];
		lfs[0]=new SuggestFragment();
		for(int i=1;i<lfs.length;i++){
			ListFragment temp=new ListFragment();
			lfs[i]=temp;
		}
		
		final ViewPager viewPager=(ViewPager) findViewById(R.id.viewPager);
		
		viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
			
			@Override
			public int getCount() {
				
				return lfs.length;
			}
			
			@Override
			public Fragment getItem(int position) {
				
				return lfs[position];
			}
		});
		
		viewPager.setOnPageChangeListener(new OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				getActionBar().setSelectedNavigationItem(position);
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				
			}
		});
		
		ActionBar actionBar=getActionBar();
		
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);//µ¼º½Ä£Ê½
		
		String[] tabs=getResources().getStringArray(R.array.tabs);
		for(int i=0;i<lfs.length;i++){
			actionBar.addTab(actionBar.newTab().setText(tabs[i]).setTabListener(new TabListener() {
				
				@Override
				public void onTabUnselected(Tab tab, FragmentTransaction ft) {
					
				}
				
				@Override
				public void onTabSelected(Tab tab, FragmentTransaction ft) {
					viewPager.setCurrentItem(tab.getPosition());
				}
				
				@Override
				public void onTabReselected(Tab tab, FragmentTransaction ft) {
					
				}
			}));
			
		}
	}

}
