import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private ObjectInputStream inputCLient;
    private ObjectOutputStream outputClient;
    private boolean ligado;

    public ClientThread(Socket socket) {

        this.socket = socket;
        this.ligado = true;
    }

    @Override
    public void run() {
        try {
            inputCLient = new ObjectInputStream(socket.getInputStream());
            outputClient = new ObjectOutputStream(socket.getOutputStream());
            while(ligado){
                String[] msg = (String[]) inputCLient.readObject();
                switch (msg[0]){
                    case "global":
                        imprimirParaTodos(msg);
                        JanelaPrincipal.textoServidor.append("Mensagem `"+msg[5]+"` foi enviada por "+msg[1]+","+msg[2]+"\n");
                        break;
                    case "sair":
                        ligado = false;
                        JanelaPrincipal.textoServidor.append(msg[5]);
                        break;

                }
            }
            Server.listaCLientesConectados.remove(this);
            inputCLient.close();
            outputClient.close();
            socket.close();
        }catch (IOException e){
            System.out.println(e.getStackTrace());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private void imprimirParaTodos(String[] msg) throws IOException {
        for (ClientThread ct: Server.listaCLientesConectados) {

            ct.outputClient.writeObject(msg);

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
