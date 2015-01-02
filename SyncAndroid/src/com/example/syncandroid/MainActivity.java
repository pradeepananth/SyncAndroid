package com.example.syncandroid;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.UnknownHostException;
import android.app.Activity;
import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.StrictMode;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.syncandroid.R;
public class MainActivity extends Activity {


    private static final int SELECT_PICTURE = 1;
    
    private String selectedImagePath;
    private ImageView img;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        System.out.println("34");
        img = (ImageView) findViewById(R.id.ivPic);
        System.out.println("36");
        ((Button) findViewById(R.id.bBrowse))
                .setOnClickListener(new OnClickListener() {
                    public void onClick(View arg0) {
                        System.out.println("40");
                        Intent intent = new Intent();
                        intent.setType("image/*");
                        intent.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(
                                Intent.createChooser(intent, "Select File"),
                                SELECT_PICTURE);
                        System.out.println("47");
                    }
                });
        ;
        
        ((Button) findViewById(R.id.bReceive))
        .setOnClickListener(new OnClickListener() {
            public void onClick(View arg0) {
            	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                
               
                int bytesRead=0;
                int counter = 0;
                ServerSocket servsock =null;
				try {
					servsock = new ServerSocket(5557);
				
                  System.out.println("Waiting...");

                  Socket sock= servsock.accept();
				
                  System.out.println("Accepted connection : " + sock);
                  counter++;
               // receive file
                    InputStream is = sock.getInputStream();
                   
                   // FileOutputStream fos = new FileOutputStream("/storage/sdcard/Android/data/com.android.browser/files/Download/Receivefile"+counter+".jpg"); // destination path and name of file
                    FileOutputStream fos = new FileOutputStream("/storage/sdcard0/Download/Receivedfile"+counter+".pdf"); // destination path and name of file
                    BufferedOutputStream bos = new BufferedOutputStream(fos);
                    
                    
                    byte[] buffer = new byte[8192]; 
                    while ((bytesRead = is.read(buffer)) > 0)
                    {
                    	bos.write(buffer, 0, bytesRead);
                    }
                    TextView receive = (TextView) findViewById(R.id.tvReceived);
                    receive.setText("PC Sync Status: Success");
                    bos.flush();
                    bos.close();
                    servsock.close();
                  sock.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			
                  }
                
            }
        );
;
        System.out.println("51");
        Button send = (Button) findViewById(R.id.bSend);
        final TextView status = (TextView) findViewById(R.id.tvStatus);

        send.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
            	StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
                StrictMode.setThreadPolicy(policy);
                
                Socket sock;
                try {
                    //sock = new Socket("192.168.0.20",9000);
                	
                	sock = new Socket("192.168.137.3", 9000);
                    System.out.println("Connecting...");

                     // sendfile
                          File myFile = null;
						
							myFile = new File (selectedImagePath);
						
                          byte [] mybytearray  = new byte [(int)myFile.length()];
                          FileInputStream fis = new FileInputStream(myFile);
                          BufferedInputStream bis = new BufferedInputStream(fis);
                          bis.read(mybytearray,0,mybytearray.length);
                          OutputStream os = sock.getOutputStream();
                          System.out.println("Sending...");
                          os.write(mybytearray,0,mybytearray.length);
                          os.flush();
                          TextView send = (TextView) findViewById(R.id.tvSent);
                          send.setText("MobileSync status: Success");

                        sock.close();
                } catch (UnknownHostException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                } catch (IOException e) {
                    // TODO Auto-generated catch block
                	e.printStackTrace();
                }



            }
        });
    }
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_PICTURE) {
                Uri selectedImageUri = data.getData();
                /*for external storage
                 * final String id = DocumentsContract.getDocumentId(selectedImageUri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));
*/
              
                selectedImagePath = getRealPathFromURI(selectedImageUri);
                       
                TextView path = (TextView) findViewById(R.id.tvPath);
                path.setText("Image Path : " + selectedImagePath);
                img.setImageURI(selectedImageUri);
            }
        }
    }
    public String getRealPathFromURI(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        @SuppressWarnings("deprecation")
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        int column_index = cursor
                .getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
   /*for External storage
    *  public String getPath(Uri uri) {
    	String res = null;
    	//String[] projection = { MediaStore.MediaColumns.DATA};
    	
    	String[] projection = {"_data" };
    	if(uri != null && "content".equals(uri.getScheme())){
        Cursor cursor = this.getContentResolver().query(uri, projection, null, null, null);
        if(cursor != null && cursor.moveToFirst()){
      	res = cursor.getString(0);
     }
        cursor.close();  }
        
       
     return res;

    }*/
}
