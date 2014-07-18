package io.github.nick11roberts.pdf_drive;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import com.dropbox.sync.android.DbxAccountManager;
import com.dropbox.sync.android.DbxFile;
import com.dropbox.sync.android.DbxFileSystem;
import com.dropbox.sync.android.DbxPath;
import com.dropbox.sync.android.DbxPath.InvalidPathException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.itextpdf.text.pdf.*;
import com.itextpdf.text.*;






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
    
    //private Bitmap imageFromCamera;
    private ArrayList<Bitmap> imagesFromCamera = new ArrayList<Bitmap>();
    private int camIndex = 0;
    private File tmpPdfFile;
	
    private int sameFileIndex = 0;
    
    // Global constants for Android specific values. 
    private static final int FLAG_ACTIVITY_CLEAR_TOP = 67108864;
    private static final int CAMERA_PIC_REQUEST = 1337;
    //private static final int CHOOSE_ACCOUNT = 0;

    
    
    @Override
	protected void onResume() {
		super.onResume();
		
		
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
        	
        	camCall();
        }
        
        
        tmpPdfFile = new File(getFilesDir() + "/" + "tmp.pdf");
        
        final Button uploadButton = (Button) findViewById((Integer) R.id.uploadButton);
        final Button cancelButton = (Button) findViewById((Integer) R.id.cancelButton);
        
        
        uploadButton.setOnClickListener(new View.OnClickListener() {
	        public void onClick(View v) {   
	        	
	        	// Call upload to Google Drive routine here. 
	        	//toDrive();
	        	
	        	// Scratch that, call upload to Dropbox method here. 
	        	
	        	
	        	if (mDbxAcctMgr.hasLinkedAccount()) {
	    			
	    			try {
	    				dropboxFileManagingMachine();
	    			} catch (InvalidPathException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			} catch (IOException e) {
	    				// TODO Auto-generated catch block
	    				e.printStackTrace();
	    			} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	    		} else{
	    			
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
		
		
		
		
	}
	
	
	public static Bitmap rotateImage(Bitmap src, float degree) 
	{
	        // create new matrix
	        Matrix matrix = new Matrix();
	        // setup rotation degree
	        matrix.postRotate(degree);
	        Bitmap bmp = Bitmap.createBitmap(src, 0, 0, src.getWidth(), src.getHeight(), matrix, true);
	        return bmp;
	}
	
	
	public void addImagesToPDF () throws Exception
	{           
		
		int indentation = 0;
		
		Document document = new Document();
		
		Bitmap bmpImage = imagesFromCamera.get(camIndex-1);
		
		
		
		ByteArrayOutputStream stream = new ByteArrayOutputStream();

		bmpImage.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] imagebytes = stream.toByteArray();

        stream.close();
        stream = null;

        
        
        PdfWriter.getInstance(document, new FileOutputStream(tmpPdfFile));
        document.open();

        Image image = Image.getInstance(imagebytes);
        
        image.rotate();
        
        
        float scaler = ((document.getPageSize().getWidth()) / image.getWidth()) * 100;
        
        
        
        
        image.scalePercent(scaler);


        //image.scaleAbsolute(bmpImage.getWidth(),
        //		bmpImage.getHeight());
        image.setAlignment(Image.MIDDLE |Image.ALIGN_MIDDLE);

        document.add(image);
        document.close();
		
    }
	
	
	
	
	
	
	
	private void dropboxFileManagingMachine() throws Exception{
		
		
		
		
		
		
		String fileName = optionClassPrev.getTitle(); // CHANGE THIS EVENTUALLY
		
		
		DbxPath path = new DbxPath(DbxPath.ROOT, fileName );
		
		
		
		if(!optionClassPrev.getFolder().isEmpty()){ // Check if the user wants a new folder
			DbxPath optUsrPath = new DbxPath("/" + optionClassPrev.getFolder());
			path = new DbxPath(optUsrPath, fileName );
		}// no new folder, ROOT then (path doesn't change)... 
		
		
		
		addImagesToPDF(); // make PDF file locally... 
		
		/*
		FileInputStream iStream = new FileInputStream(tmpPdfFile);
		byte[] bytes = new byte[(int)tmpPdfFile.length()];
		iStream.read(bytes);
		iStream.close();
		*/
		
		// CREATE DROPBOX FILE SYSTEM
		DbxFileSystem dbxFs = DbxFileSystem.forAccount(mDbxAcctMgr.getLinkedAccount());
		
		// CREATE DROPBOX FILE IF NOT EXIST
		if (!dbxFs.exists(path)) {
			DbxFile file = dbxFs.create(path);
			file.writeFromExistingFile(tmpPdfFile, true);
			//try {
			//	file.writeString("Hello Dropbox!!!");
			//} finally {
				file.close();
			//}
		}else{
			
			sameFileIndex++;
			String indexString = "(" + Integer.toString(sameFileIndex) + ")";
			String newTitle = optionClassPrev.getTitle();
			int dotIndex = newTitle.lastIndexOf(".");
			if(dotIndex != -1){ // if it has a file extension... 
				String beforeDot = newTitle.substring(0, dotIndex);
				String afterDot = newTitle.substring(dotIndex, newTitle.length());
				newTitle = beforeDot + indexString +  afterDot;
			}else{ // no file extension... 
				newTitle = optionClassPrev.getTitle() + indexString;
			}
			
			optionClassPrev.setTitle(newTitle);
			
			dropboxFileManagingMachine();
        	
		}
		
		
		
		
		
		bringMainActivityToFront();
		
		
	}
	
	
	
	
	
	
	
	// Called by startActivityForResult method. 
		protected void onActivityResult(int requestCode, int resultCode, Intent data) { 
			
			
			
		    if (requestCode == CAMERA_PIC_REQUEST) {
		    	
		    	//Append Bitmap and increment index... 
		    	
		    	camIndex++;
		    	imagesFromCamera.add( (Bitmap)data.getExtras().get("data") );
		    	
		    	
		    	
		    }
		    
		    
		    
		    else if (requestCode == REQUEST_LINK_TO_DBX) {
		    	
	            if (resultCode == Activity.RESULT_OK) {
	            	
	            	try {
					
						dropboxFileManagingMachine();
					
					} catch (InvalidPathException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
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

