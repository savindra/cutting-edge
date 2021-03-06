package lk.ac.iit.humzearch.app;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseACL;
import com.parse.ParseCrashReporting;
import com.parse.ParseUser;

public class ParseApplication extends Application {

	  @Override
	  public void onCreate() {
	    super.onCreate();

	    // Initialize Crash Reporting.
	    ParseCrashReporting.enable(this);

	    // Enable Local Datastore.
	    //Parse.enableLocalDatastore(this);

	    // Add your initialization code here
	    Parse.initialize(this, "puhgB4SKXYi0xf4OPkcoOW5eobRqe4ixjcmf0BA3", "Y5FOUaOgiGBklu0XhoeikKYRy4aehW1D5xQQRxuF");


	    //ParseUser.enableAutomaticUser();
	    ParseACL defaultACL = new ParseACL();
	    // Optionally enable public read access.
	    // defaultACL.setPublicReadAccess(true);
	    ParseACL.setDefaultACL(defaultACL, true);
	  }
	}
