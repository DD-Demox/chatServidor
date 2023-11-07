import java.io.*;
import java.net.Socket;

public class ClientThread extends Thread {
    private Socket socket;
    private ObjectInputStream inputCLient;
    private ObjectOutputStream outputClient;
    private boolean ligado;
    private String nome;

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
                        mandaMsgParaTodos(msg);
                        JanelaPrincipal.textoServidor.append("Mensagem `"+msg[5]+"` foi enviada por "+msg[1]+","+msg[2]+"\n");
                        break;
                    case "nome":
                        this.nome = msg[1];
                        atualizarListaClientesConectados();
                        break;
                    case "msgPrivada":
                        mandarMsgPrivado(msg);
                        break;
                    case "sair":
                        ligado = false;
                        JanelaPrincipal.textoServidor.append(msg[5]);
                        break;

                }
            }
            Server.listaCLientesConectados.remove(this);
            atualizarListaClientesConectados();
            inputCLient.close();
            outputClient.close();
            socket.close();
        }catch (IOException e){
            System.out.println(e.getStackTrace());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public String getNome() {
        return this.nome;
    }

    private void atualizarListaClientesConectados() throws IOException {
        String[] listaNomeCliente = new String[Server.listaCLientesConectados.size()+1];
        listaNomeCliente[0] = "atualizarLista";
        int i =1;
        for(ClientThread ct : Server.listaCLientesConectados){
            listaNomeCliente[i] = ct.getNome();
            i++;
        }
        for(ClientThread ct : Server.listaCLientesConectados){
            ct.outputClient.writeObject(listaNomeCliente);
        }
    }

    private void mandaMsgParaTodos(String[] msg) throws IOException {
        for (ClientThread ct: Server.listaCLientesConectados) {

            ct.outputClient.writeObject(msg);

        }
    }
    private void mandarMsgPrivado(String[] msg) throws IOException {
        ClientThread ctprivado = null;
        for(ClientThread ct : Server.listaCLientesConectados){
            if(msg[4].equals(ct.getNome())){
                ctprivado = ct;
                break;
            }
        }
        if(ctprivado != null){
            String[] mensagemAEnviar = new String[3];
            mensagemAEnviar[0]="msgPrivada";
            mensagemAEnviar[1]=msg[4];
            mensagemAEnviar[2]=msg[2]+": "+msg[5];
            outputClient.writeObject(mensagemAEnviar);
            mensagemAEnviar[1]=msg[2];
            ctprivado.outputClient.writeObject(mensagemAEnviar);
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
