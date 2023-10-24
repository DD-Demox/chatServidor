import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private BufferedReader inputCLient;
    private PrintStream outputClient;

    public ClientThread(Socket socket) {
       this.socket = socket;
    }

    @Override
    public void run() {
        try {
            inputCLient = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            outputClient = new PrintStream(socket.getOutputStream(),true);
            while(true){
                String msg = inputCLient.readLine();
                if(msg.equals("sair")){
                    break;
                }
                imprimirParaTodos(msg);
                System.out.println("Mensagem `"+msg+"` foi enviada por"+socket.getLocalAddress().getHostAddress());

            }
        }catch (IOException e){
            System.out.println(e.getStackTrace());
        }
    }

    private void imprimirParaTodos(String msg) {
        for (ClientThread ct: Server.listaCLientesConectados) {
            ct.outputClient.println(msg);
        }
    }

    public void close() {
        try{
            this.socket.close();
        }catch (IOException e){
            System.out.println(e.getStackTrace());
        }
    }
}
