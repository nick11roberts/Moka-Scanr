package io.github.nick11roberts.pdf_drive;

import java.io.IOException;

import com.google.android.gms.common.api.GoogleApiClient;
//import com.google.api.services.drive.DriveScopes;
import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxException;
import com.dropbox.sync.android.DbxException.Unauthorized;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileInfo;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.DbxPath.InvalidPathException;

import android.accounts.AccountManager;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;
import android.os.Build;

public class PreviewActivity extends Activity {

	// We need this because the passed options object cannot be made public. 
	private Options optionClassPrev = new Options();
	
	// The object for interacting with 
	//private GoogleApiClient mGoogleApiClient;
	
	// stuff for Dropbox

	private static final String appKey = "qahwki8qn4p53oi";
    private static final String appSecret = "aseeqi78l8nnuuz";
    
    private static final int REQUEST_LINK_TO_DBX = 0;
    private DbxAccountManager mDbxAcctMgr;
    
    private Bitmap imageFromCamera;
	
    // Global constants for Android specific values. 
    private static final int FLAG_ACTIVITY_CLEAR_TOP = 67108864;
    private static final int CAMERA_PIC_REQUEST = 1337;
    //private static final int CHOOSE_ACCOUNT = 0;

    
    
    @Override
	protected void onResume() {
		super.onResume();
		System.out.println("onResume called... ");
		
	}
    
    
    
    
    
	@Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview);
        
        // Receive options object from OptionsActivity class which launched this activity.
    	Intent launchPreviewIntent = getIntent();
        Options optionClassOp = (Options)launchPreviewIntent.getSerializableExtra("OptionItems");
        
        // Set our current object with the values stored in the passed, (currently non public) options object. 
        optionClassPrev.setTitle(optionClassOp.getTitle());
        optionClassPrev.setNumberOfPages(optionClassOp.getNumberOfPages());
        optionClassPrev.setFolder(optionClassOp.getFolder());
        
        // Preview's options object will be used here and elsewhere. 
        for(int i = 0; i <= optionClassPrev.getNumberOfPages()-1; i++){
        	//Temporarily commented out for debugging purposes. 
        	//camCall();
        }
        
        
        final Button uploadButton = (Button) findViewById((Integer) R.id.uploadButton);
        final Button cancelButton = (Button) findViewById((Integer) R.id.cancelButton);
        
        
        uploadButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {   
	        	
	        	// Call upload to Google Drive routine here. 
	        	//toDrive();
	        	
	        	// Scratch that, call upload to Dropbox method here. 
	        	
	        	
	        	if (mDbxAcctMgr.hasLinkedAccount()) {
	    			System.out.println("onResume called... Account linked...");
	    			try {
	    				dropboxFileManagingMachine();
	    			} catch (InvalidPathException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			} catch (IOException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			}
	    		} else{
	    			System.out.println("onResume called... Account NOT linked...");
	    			toDropboxNotLinked(); // Will eventually bring the main activity to the front... 
	    		}
	        	
	        	
	        	
	        	
	    		
	        	
	        	
	        	
	        	
	        	
	        	
	        }

			
	    });
        
        
        
        cancelButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	
	        	// Delete the image or PDF object/file. 
	        	
	        	bringMainActivityToFront();
	        	
	        	
	        	
	        }
	    });
        
        // Set Dropbox acctmgr instance
        mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(), appKey, appSecret);
        
    }
	
	
	
	
	
	
	// Called by both upload and cancel buttons. 
	protected void bringMainActivityToFront(){
		Intent resetMainIntent = new Intent(PreviewActivity.this, MainActivity.class);
		resetMainIntent.addFlags(FLAG_ACTIVITY_CLEAR_TOP);
    	startActivity(resetMainIntent);
	}
	
	
	
	// Called by the onCreate method. 
	protected void camCall(){

    	Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
    	startActivityForResult(cameraIntent, CAMERA_PIC_REQUEST);

	}
	
	
	
	
	private void toDropboxNotLinked() {
		// TODO Auto-generated method stub
		
		
		mDbxAcctMgr.startLink((Activity)this, REQUEST_LINK_TO_DBX);
		
		System.out.println("start link... ");
		
		
	}
	
	private void dropboxFileManagingMachine() throws InvalidPathException, IOException{
		
		
		
		System.out.println("DROPBOX FILE MANAGING THING WORKS");
		
		
		String fileName = optionClassPrev.getTitle(); // CHANGE THIS EVENTUALLY
		
		
		DbxPath path = new DbxPath(DbxPath.ROOT, fileName );
		
		if(!optionClassPrev.getFolder().isEmpty()){ // Check if the user wants a new folder
			DbxPath optUsrPath = new DbxPath("/" + optionClassPrev.getFolder());
			path = new DbxPath(optUsrPath, fileName );
		}
		else{ 
			// no new folder, ROOT then (path doesn't change)... 
		}
		
		
		
		// CREATE DROPBOX FILE SYSTEM
		DbxFileSystem dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());
		
		// CREATE DROPBOX FILE IF NOT EXIST
		if (!dbxFs.exists(path)) {
			DbxFile testFile = dbxFs.create(path);
			try {
			    testFile.writeString("Hello Dropbox!!!");
			} finally {
			    testFile.close();
			}
		}else{
			Toast toast = Toast.makeText(PreviewActivity.this, getResources().getString(R.string.dropbox_fail_file_exists), 
    				Toast.LENGTH_SHORT);
			
			//Overwrite (y/n)? fragment inflates
			
        	toast.show();
		}
		
		
		//////// REPLACE ABOVE WITH 
		// imageFromCamera
		
		
		bringMainActivityToFront();
		
		
	}
	
	
	
	
	// Called by startActivityForResult method. 
		protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
			
			System.out.println("Reaches onActivityResult...");
			
		    if (requestCode == CAMERA_PIC_REQUEST) {
		    	imageFromCamera = (Bitmap) data.getExtras().get("data");
		    	
		    	//add to a Bitmap array of numOfPages in length?
		    	
		    }
		    
		    /*
		    if (requestCode == CHOOSE_ACCOUNT && resultCode == RESULT_OK && data != null) {
		    	Bitmap imageFromCamera = (Bitmap) data.getExtras().get("data");
		    	
		    	//add to a Bitmap array of numOfPages in length?
		    	
		    }
		    */	
		    
		    else if (requestCode == REQUEST_LINK_TO_DBX) {
		    	System.out.println("requestCode...");
	            if (resultCode == Activity.RESULT_OK) {
	            	System.out.println("result is okay...");
	            	try {
	            		System.out.println("in try... should call file man...");
						dropboxFileManagingMachine();
					} catch (InvalidPathException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            } 
	            else {
	            	Toast toast = Toast.makeText(PreviewActivity.this, getResources().getString(R.string.dropbox_fail), 
	        				Toast.LENGTH_SHORT);
	            	toast.show();
	            	// Tell the user that they have made a terrible mistake and must be punished. Muahaha.
	            }
	        } 
		    
		    
		    
		    else {
	            super.onActivityResult(requestCode, resultCode, data);
	        }
		    
		    
		    
		}
}

