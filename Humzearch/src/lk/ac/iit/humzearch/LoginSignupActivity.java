package lk.ac.iit.humzearch;

import lk.ac.iit.humzearch.adapter.ViewPagerAdapter;
import lk.ac.iit.humzearch.util.SlidingTabLayout;
import lk.ac.iit.humzearch.util.SlidingTabLayout.TabColorizer;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class LoginSignupActivity extends ActionBarActivity {
	
	Toolbar toolbar;
	ViewPager pager;
	ViewPagerAdapter adapter;
	SlidingTabLayout tabs;
	CharSequence titles[]={"Login","Sign Up"};
    int Numboftabs =2;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.login_signup);
        
        toolbar = (Toolbar) findViewById(R.id.tool_bar);
        toolbar.setLogo(R.drawable.ic_launcher);
        setSupportActionBar(toolbar);
        
        adapter = new ViewPagerAdapter(getSupportFragmentManager(), titles, Numboftabs);
        
        pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
        
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);
        tabs.setDistributeEvenly(true);
        
        tabs.setCustomTabColorizer(new TabColorizer() {
			
			@Override
			public int getIndicatorColor(int position) {
				return getResources().getColor(R.color.tabsScrollColor);
			}
		});
        
        tabs.setViewPager(pager);
        
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
