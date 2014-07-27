package io.github.nick11roberts.pdf_drive;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


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
