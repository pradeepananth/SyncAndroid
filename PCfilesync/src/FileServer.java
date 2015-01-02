 import java.io.*;
import java.net.*;

public class FileServer {

    public static void main(String[] args) throws IOException {
        long start = System.currentTimeMillis();
        int bytesRead=0;
        int counter = 0;
        byte[] buffer = new byte[8192];
        // create socket
       
        while (true) {
         
        	 ServerSocket servsock = new ServerSocket(9000);
             System.out.println("Waiting...");
             //accept socket connection
             Socket sock = servsock.accept();
             System.out.println("Accepted connection : " + sock);
          counter++;
       //receive file
            InputStream is = sock.getInputStream();
            FileOutputStream fos = new FileOutputStream("C:\\Users\\Ap\\Documents\\ACN\\Shared\\file"+counter+".jpg"); // destination path and name of file
            BufferedOutputStream bos = new BufferedOutputStream(fos);
            
            while ((bytesRead = is.read(buffer)) > 0)
            {
            	bos.write(buffer, 0, bytesRead);
            }
            
            bos.flush();
            long end = System.currentTimeMillis();
            System.out.println(end-start);
            bos.close();

            servsock.close();
           
          }
    }

}