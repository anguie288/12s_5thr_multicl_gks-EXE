

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Random;
import java.util.StringTokenizer;
import java.util.Vector;


public class id12s_5thr_multisr_gks {
    
    
    static Vector<clienthandler> carr = new Vector<clienthandler>(); 
    static int cnum = 0; 
    public static void main(String[] args) throws IOException {

        ServerSocket server = null;
        server = new ServerSocket(32000);
        server.setReuseAddress(true);
             
        while (true) {
                
                Socket s = server.accept();      
                System.out.println("New client connected " + s.getInetAddress().getHostAddress());
                
                clienthandler clientsockth = new clienthandler(s, "client "+cnum); // messages classes
                carr.add(clientsockth); 
                clientsockth.start();
                cnum++;                
        }  
    }
}
    
class clienthandler extends Thread {

        private final Socket clientsocket;
        private String cname; 
        boolean isloggedin; 

        public clienthandler(Socket socket, String cname) {
            this.clientsocket = socket; this.cname = cname;
            this.isloggedin=true; }

        @Override
        public void run() {   // run printers and writers
            
            String received; 
            String instr;

            PrintWriter out = null;
            BufferedReader in = null;
            BufferedReader incl =null;

            while (true) {
                try {
                    in = new BufferedReader(new InputStreamReader(clientsocket.getInputStream()));
                    out = new PrintWriter(clientsocket.getOutputStream(), true);
                    incl =new BufferedReader(new InputStreamReader(System.in)); 
                
                    /*//for loop can omit i line too   // array[i]
                    
                        for(int i=0;i<2;i++) {  // for(String eans : ans)
                              Thread.sleep(300);
                              out.println(line+", " + ans[index]);} */

                    received = in.readLine();
                    //System.out.println(received); 
                
                    if (received.equals("exit") || received.equals("logout") || received.equals(null))  {
                        this.isloggedin=false; 
					    this.clientsocket.close(); in.close(); out.close(); incl.close();
					    break;}  // close connection   // return false;
                
                        String[] ans = {"how are you","hi there ","greetings"};
                        Random random = new Random();
                        int index = random.nextInt(3);
                        //instr = incl.readLine();
                        instr = ans[index];
                
                        StringTokenizer st = new StringTokenizer(received, "#"); 
                        StringTokenizer st2 = new StringTokenizer(instr, "#"); 
                
                        String MsgToSend=null;String Recipient=null;
                	
                        while (st.hasMoreTokens())
                              {Recipient= st.nextToken();}
                        while (st2.hasMoreTokens())
                              {MsgToSend = st2.nextToken();}


                        for (clienthandler mc : id12s_5thr_multisr_gks.carr) { 
                            /*if (mc.cname.equals(recipient) && mc.isloggedin==true) { */
                            out.println(mc.cname+" : "+MsgToSend);  
                            break;  /*} */} 
                  
                        System.out.println(Recipient); 
                        System.out.println(MsgToSend); 

                }catch (IOException e) { e.printStackTrace(); } 
            }   //while         
            
            try{ in.close(); out.close(); clientsocket.close();}
            catch(IOException e){ e.printStackTrace(); } 
            //https://stackoverflow.com/questions/40574796/java-properly-closing-sockets-for-multi-threaded-servers
            // clientSocket = serverSocket.accept();   
            // if (clientSocket != null) 
            //https://stackoverflow.com/questions/969866/java-detect-lost-connection
            // the best way to test connection is to try to read/write from the socket. If the operation fails, then you have lost your connection sometime.
            // Socket socket = new Socket(host, port);
            // boolean connected = socket.isConnected() && !socket.isClosed();
            //https://stackoverflow.com/questions/30352134/why-server-side-shows-a-null-pointer-exception-in-client-server-application
            // Closing the returned OutputStream will close the associated socket.
            // os = socket.getOutputStream();
        }
        
    }
            
            
            
            
           