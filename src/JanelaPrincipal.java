import javax.swing.*;

public class JanelaPrincipal {
    public static JFrame janelaPricipal = new JFrame("Servidor Chat");

    public JanelaPrincipal(){
        janelaPricipal.setResizable(false);
        janelaPricipal.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        janelaPricipal.setSize(500,500);
        janelaPricipal.setLocationRelativeTo(null);

        JButton ligarBotao = new JButton("Ligar Servidor");
        ligarBotao.setBounds(200,50,50,20);
        janelaPricipal.add(ligarBotao);

        JTextArea textoServidor = new JTextArea("LOG");
        textoServidor.setBounds(10,100,300,300);
        textoServidor.setLineWrap(true);
        textoServidor.setEditable(false);
        textoServidor.setVisible(true);

        JScrollPane barraTextoServidor = new JScrollPane(textoServidor);
        barraTextoServidor.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        janelaPricipal.add(barraTextoServidor);

        janelaPricipal.setVisible(true);


    }

}

