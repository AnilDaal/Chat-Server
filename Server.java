import java.io.*;
import java.net.*;
public class Server {
    ServerSocket server;
    Socket socket;
    BufferedReader read;
    PrintWriter writer;
    public Server(){
        try {
            server = new ServerSocket(7777);
            System.out.println("Server is ready for request");
            socket = server.accept();
            read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            writer = new PrintWriter(socket.getOutputStream());
            startReading();
            startWriter();

      }
      catch(Exception e){
        System.out.println("Exception: "+e);
      }
    }
    public static void main(String[] args) {
        new Server();
    }
    public void startReading(){ 
        Runnable r1=()->{
         try {
             while(true){
                String msg;
                msg = read.readLine();
                if(msg.equals("exit")){
                System.out.println("Client Shutdown"); 
                socket.close();
                break;  
                }
                System.err.println("Client: "+msg);
            }
        } catch (IOException e) {
            System.out.println("Server Close");
        }
    }; 
    new Thread(r1).start();
    } 
    public void startWriter() {
        Runnable r2 = ()->{
            System.out.println("Enter msg...");
            try {
            while(true && !socket.isClosed()){
                BufferedReader read1 = new BufferedReader(new InputStreamReader(System.in));
                String content;
                content = read1.readLine();
                writer.println(content);
                writer.flush();
                if(content.equals("exit")){
                socket.close();
                break;
                }
            }
            }
            catch (IOException e) {
                System.out.println("Server Close");
            }
        };
        new Thread(r2).start();
    }
}
