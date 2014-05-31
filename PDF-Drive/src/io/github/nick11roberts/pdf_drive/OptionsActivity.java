package io.github.nick11roberts.pdf_drive;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.os.Build;

public class OptionsActivity extends Activity {


	public Options OptionClass = new Options();
	
	
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        
        
        
	    final Button optionsButton = (Button) findViewById((Integer) R.id.optionsButton);
	    final EditText titleEditText = (EditText) findViewById( R.id.inputTextDocumentTitle);
	    final EditText numOfPagesEditText = (EditText) findViewById( R.id.inputTextNumOfPages);
	    final EditText folderEditText = (EditText) findViewById( R.id.inputTextFolder);
	    
	    
	    
	    optionsButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	
	        	
	        	
	        	// Set all of the OptionClass variables with their corresponding inputs.
	        	
	        	if(titleEditText.getText().toString().isEmpty() && 
	        			numOfPagesEditText.getText().toString().isEmpty()){ // Checks if it is empty. 
	        		Toast toast = Toast.makeText(OptionsActivity.this, getResources().getString(R.string.toast_title_and_page_num), 
	        				Toast.LENGTH_SHORT);
	        		toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
	        		toast.show();
	        	    
	        	}
	        	
	        	else if(titleEditText.getText().toString().isEmpty()){ // Checks if it is empty.
	        		
	        		Toast toast = Toast.makeText(OptionsActivity.this, getResources().getString(R.string.toast_title), 
	        				Toast.LENGTH_SHORT);
	        		toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
	        		toast.show();
	        	    
	        	}

	        	else if(numOfPagesEditText.getText().toString().isEmpty()){ // Checks if it is empty.
	        		
	        		Toast toast = Toast.makeText(OptionsActivity.this, getResources().getString(R.string.toast_page_num), 
	        				Toast.LENGTH_SHORT);
	        		toast.setGravity(Gravity.TOP|Gravity.LEFT, 0, 0);
	        		toast.show();
	        	    
	        	}
	        	
	        	// Folder field can be empty. 
	        	
	        	
	        	
	        	
	        	// ADD MORE FIELD OPTIONS HERE AS MORE FIELDS BECOME AVAILABLE. 
	        	
	        	if( !(titleEditText.getText().toString().isEmpty()) && 
	        			!(numOfPagesEditText.getText().toString().isEmpty()) ){ 
	        		// Folder field is optional, so allow user to proceed if both fields are nonempty. 
	        		// Set OptionClass object values. 
	        		OptionClass.setTitle(titleEditText.getText().toString());
	        		OptionClass.setNumberOfPages(Integer.parseInt(numOfPagesEditText.getText().toString()));
	        		OptionClass.setFolder(folderEditText.getText().toString());
	        		
	        		// Finally, launch intent. 
	        		Intent launchPreviewIntent = new Intent(OptionsActivity.this, PreviewActivity.class);
	        		launchPreviewIntent.putExtra("OptionItems", OptionClass); // Serially adds object to intent to pass it. 
	        		startActivity(launchPreviewIntent);
	        	}	
	        	
	        }
	        
	    });

    }
	
}