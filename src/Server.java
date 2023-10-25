import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

public class Server {
    private int port = 5000;
    public static ArrayList<ClientThread> listaCLientesConectados = new ArrayList<>();


    Server(){
        run(this.port);
    }
    Server(int port){
        run(port);
    }

    private void run(int port){
        try(ServerSocket serverSocket = new ServerSocket(10000)){
//            System.out.println("Servidor conectado");
            boolean ligado = true;

            while(ligado){


//                System.out.println("Servidor conectado");
                JanelaPrincipal.textoServidor.append("Servidor conectado\n");
                Socket socket = serverSocket.accept();

                ClientThread cliente = new ClientThread(socket);
                listaCLientesConectados.add(cliente);
                JanelaPrincipal.textoServidor.append("Novo Cliente conectado. Ip: "+socket.getInetAddress().getHostAddress());
                cliente.start();
            }
            for (ClientThread ct: Server.listaCLientesConectados) {
                ct.close();
            }
            serverSocket.close();
        }catch (IOException e){
            System.out.println(e.getStackTrace());
        }



    }
}
