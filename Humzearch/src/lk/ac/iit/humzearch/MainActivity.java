package lk.ac.iit.humzearch;

import com.parse.ParseObject;
import com.parse.ParseUser;

import lk.ac.iit.humzearch.adapter.ViewPagerAdapter;
import lk.ac.iit.humzearch.app.ParseApplication;
import lk.ac.iit.humzearch.util.SlidingTabLayout;
import lk.ac.iit.humzearch.util.SlidingTabLayout.TabColorizer;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;


@SuppressWarnings("deprecation")
public class MainActivity extends ActionBarActivity {
	
	private ParseUser currentUser;
	private Intent intent;
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		currentUser = ParseUser.getCurrentUser();
		
		if(currentUser == null){
			intent = new Intent(this, LoginSignupActivity.class);
		}else {
			intent = new Intent(this, MainMenuActivity.class);
		}
		
		this.startActivity(intent);
        this.finish();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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
