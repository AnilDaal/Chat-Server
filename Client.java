import java.net.*;
import java.io.*;
public class Client {
    Socket socket;
    BufferedReader read;
    PrintWriter writer;
    public Client() throws IOException{ 
        System.out.println("Send the request to server");
        socket = new Socket("127.0.0.1",7777);
        System.out.println("Connection Done"); 
        read = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        writer = new PrintWriter(socket.getOutputStream());
        startReading();
        startWriter();
    }
    public void startReading(){
        Runnable r1=()->{
            try {
            while(true){
                String msg;
                msg = read.readLine();
                if(msg.equals("exit")){ 
                System.out.println("Server Shutdown");   
                socket.close();
                break;
                }
                System.out.println("Server: "+msg);
            }
        } catch (IOException e) { 
            System.out.println("Client Close");
        }
    }; 
    new Thread(r1).start();
    } 
    public void startWriter() {
        Runnable r2 = ()->{
            System.out.println("Enter Msg...");
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
            } catch (IOException e) {
                System.out.println("Client Close");
            }
        };
        new Thread(r2).start();
    }
    public static void main(String[] args) throws IOException {
        System.out.println("This is client...");
        new Client();
    }
}
