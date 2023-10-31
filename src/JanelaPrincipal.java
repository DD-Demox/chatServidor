import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class JanelaPrincipal {
    public static JFrame janelaPricipal = new JFrame("Servidor Chat");
    public static JTextArea textoServidor;
    public JanelaPrincipal(){
        janelaPricipal.setResizable(true);
        janelaPricipal.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        janelaPricipal.setSize(500,500);
        janelaPricipal.setLocationRelativeTo(null);
        janelaPricipal.getContentPane().setLayout(null);
        janelaPricipal.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        JButton ligarBotao = new JButton("Ligar Servidor");
        ligarBotao.setBounds(200,50,150,20);
        janelaPricipal.add(ligarBotao);

        textoServidor = new JTextArea();
        textoServidor.setLineWrap(true);
        textoServidor.setEditable(false);
        textoServidor.setVisible(true);

        JScrollPane barraTextoServidor = new JScrollPane(textoServidor);
        barraTextoServidor.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        barraTextoServidor.setBounds(10,100,450,300);
        janelaPricipal.add(barraTextoServidor);
        janelaPricipal.setVisible(true);

        ligarBotao.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Runnable r = new Runnable() {
                    @Override
                    public void run() {
                        Server server =new Server();
                    }
                };
                new Thread(r).start();
            }
        });

    }

}

