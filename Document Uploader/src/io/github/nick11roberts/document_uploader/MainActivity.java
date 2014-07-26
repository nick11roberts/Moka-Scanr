package io.github.nick11roberts.document_uploader;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.os.Build;
import io.github.nick11roberts.document_uploader.R;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    
	    final Button mainButton = (Button) findViewById((Integer) R.id.mainButton);
	    
	    
	    mainButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	
	        	Intent launchOptionsActivityIntent = new Intent(MainActivity.this, OptionsActivity.class);
	        	startActivity(launchOptionsActivityIntent);
	        }
	    });

    }

}
