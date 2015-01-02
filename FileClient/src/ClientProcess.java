import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;


public class ClientProcess {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		Socket sock;
		//File folder = new File("C:\\Users\\Ap\\Documents\\ACN\\Shared\\");
		//File[] listoffiles= folder.listFiles();
        try {
            sock = new Socket("192.168.137.50",5557);
            System.out.println("Connecting...");

             // sendfiles	
                 //for(File myFile:listoffiles){
            File myFile = null;
			
			myFile = new File ("C:\\Users\\Ap\\Documents\\ACN\\Shared\\AcademicCalendarSpring2015.pdf");
                  byte [] mybytearray  = new byte [(int)myFile.length()];
                  FileInputStream fis = new FileInputStream(myFile);
                  BufferedInputStream bis = new BufferedInputStream(fis);
                  bis.read(mybytearray,0,mybytearray.length);
                  OutputStream os = sock.getOutputStream();
                  System.out.println("Sending...");
                  os.write(mybytearray,0,mybytearray.length);
                  os.flush();
                  System.out.println("successfully sent...");
               //  }

                sock.close();
        } catch (UnknownHostException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
        	e.printStackTrace();
        }



	}

}
