package io.github.nick11roberts.pdf_drive;

import java.io.IOException;

import com.google.android.gms.common.api.GoogleApiClient;
import com.google.api.services.drive.DriveScopes;
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
	private static final String appKey = "rcdpg0hv3w2whqp";
    private static final String appSecret = "vl380k4q0icywbs";
    private static final int REQUEST_LINK_TO_DBX = 0;
    private DbxAccountManager mDbxAcctMgr;
    
    private Bitmap imageFromCamera;
	
    // Global constants for Android specific values. 
    private static final int FLAG_ACTIVITY_CLEAR_TOP = 67108864;
    private static final int CAMERA_PIC_REQUEST = 1337;
    //private static final int CHOOSE_ACCOUNT = 0;

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
        	camCall();
        }
        
        
        final Button uploadButton = (Button) findViewById((Integer) R.id.uploadButton);
        final Button cancelButton = (Button) findViewById((Integer) R.id.cancelButton);
        
        
        uploadButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {   
	        	
	        	// Call upload to Google Drive routine here. 
	        	//toDrive();
	        	
	        	// Scratch that, call upload to Dropbox method here. 
	        	
	        	
	        	
	    		toDropbox();
	        	
	        	
	        	bringMainActivityToFront();
	        	
	        	
	        	
	        }

			
	    });
        
        
        
        cancelButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {
	        	
	        	// Delete the image or PDF object/file. 
	        	
	        	bringMainActivityToFront();
	        	
	        	
	        	
	        }
	    });
        
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
	
	
	
	private void toDrive(){
		/*AccountManager am = AccountManager.get(activity);
		am.getAuthToken(am.getAccounts())[0],
		    "oauth2:" + DriveScopes.DRIVE,
		    new Bundle(),
		    true,
		    new OnTokenAcquired(),
		    null);*/
		
		
		
		
	}
	
	private void toDropbox() {
		// TODO Auto-generated method stub
		
		mDbxAcctMgr = DbxAccountManager.getInstance(getApplicationContext(), appKey, appSecret);
		mDbxAcctMgr.startLink((Activity)this, REQUEST_LINK_TO_DBX);
		
		System.out.println("CALLS TO DROPBAWKS");
		
		
	}
	
	private void dropboxFileManagingMachine() throws InvalidPathException, IOException{
		// LINK TO USER
		
		System.out.println("DROB FILE MAN WORKS");
		
		
		// CREATE DROPBOX FILE SYSTEM
		DbxFileSystem dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());
		
		// CREATE DROPBOX FILE
		DbxFile testFile = dbxFs.create(new DbxPath("hello.txt"));
		try {
		    testFile.writeString("Hello Dropbox!");
		} finally {
		    testFile.close();
		}
		
		//////// REPLACE ABOVE WITH 
		// imageFromCamera
		
		
	}
	
	
	// Called by startActivityForResult method. 
		protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
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
		    
		    if (requestCode == REQUEST_LINK_TO_DBX) {
		    	
		    	System.out.println("CALLS REQUEST LINK TO DBX");
		    	
	            if (resultCode == Activity.RESULT_OK) {
	            	
	            	System.out.println("CALLS REQUEST RESULT OK");
	            	
	                ///// CALL OUR DROPBOX UPLOAD METHOD
	            	try {
						dropboxFileManagingMachine();
					} catch (InvalidPathException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	            	
	            	
	            	
	            } else {
	            	Toast toast = Toast.makeText(PreviewActivity.this, getResources().getString(R.string.dropbox_fail), 
	        				Toast.LENGTH_SHORT);
	            	toast.show();
	            	// Tell the user that they have made a terrible mistake and must be punished. Muahaha. 
	            }
	        } else {
	            super.onActivityResult(requestCode, resultCode, data);
	        }
		}
}

