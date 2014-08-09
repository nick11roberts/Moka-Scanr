Moka Scanr
=========

This is a free and open source Android application which allows users to upload images as PDF files to their Dropbox account.


This software is written using the ADT bundle available from (http://developer.android.com/sdk/index.html), and so the folder structure follows
what is typical of the ADT bundle (which comes with the Eclipse IDE) 
or the Eclipse IDE with the ADT plugin (available from the Eclipse marketplace). 


When building, add your own app key and secret to a new xml file in 'Document Uploader/res/values/' called 'keys.xml' and use the following format (keep item names, but replace values with your key/secret): 
````xml
<?xml version="1.0" encoding="utf-8"?>
<resources>
   <item name="dropbox_app_key" type="string">app_key_here</item>
   <item name="dropbox_app_secret" type="string">app_secret_here</item>
   <item name="dropbox_app_key_manifest" type="string">db-app_key_here</item>
</resources>
````
In the case of dropbox_app_key_manifest, the format is db-x, x being your app key, it is important to keep the 'db-' prefix. 

