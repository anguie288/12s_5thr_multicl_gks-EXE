import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;
 
public class id12s_5thr_multicl2_gks {
 
    public static void main(final String[] args) throws IOException {
 
        final Scanner scn = new Scanner(System.in); 
        final String host = "127.0.0.1";
        final int port = 32000;
        
        final Socket s = new Socket(host, port);

        final PrintWriter out = new PrintWriter(s.getOutputStream(), true);
        final BufferedReader in = new BufferedReader(new InputStreamReader(s.getInputStream()));

        final Runnable sendMessage = new Runnable() {
            @Override
            public void run() {
                
                while (true) { 
					final String msg = scn.nextLine(); 
					
                    try { out.println(msg); } 
                    
                    catch (final NullPointerException e){e.printStackTrace();} catch(final Exception e) {System.out.println("Unexcepted Exception");
                        e.printStackTrace();}finally{}

				} 
            }
        };


        final Runnable readMessage = new Runnable() {
            @Override
            public void run() {
                
                while (true) { 
                    try {   
                            String msg = in.readLine(); 
                          
                            if (msg.equals("exit")||msg.equals("logout")||msg.equals(null)) {
                                out.close();in.close(); 
                                break;} 
                            
                            System.out.println(msg);             
                        } 
                    catch (IOException e) { e.printStackTrace(); } 
                } 
            }
        };

        Thread threadsend = new Thread(sendMessage);
        Thread threadread = new Thread(readMessage);

        threadsend.start();
        threadread.start();

        if(!s.isConnected()){
            out.close();in.close();//s.close();  
        }
       
        
    }
}
