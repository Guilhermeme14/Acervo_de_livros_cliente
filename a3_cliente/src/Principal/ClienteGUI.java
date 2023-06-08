package Principal;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class ClienteGUI extends JFrame implements ActionListener {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private JButton buttonAdicionar;
    private JButton buttonRemover;
    private JButton buttonConsultar;
    private JButton buttonSair;

    public ClienteGUI() {
        // Configuração do Look and Feel Nimbus
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        createMainFrame();

        // Conecta ao servidor
        connectToServer();
    }

    private void createMainFrame() {
        // Configurações da janela principal
        setTitle("Acervo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Painel principal
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());

        // Criação dos componentes
        buttonAdicionar = createButton("Adicionar");
        buttonRemover = createButton("Remover");
        buttonConsultar = createButton("Consultar");
        buttonSair = createButton("Sair");

        // Adiciona os componentes ao painel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(buttonAdicionar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        contentPane.add(buttonRemover, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(buttonConsultar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPane.add(buttonSair, gbc);

        // Eventos de clique dos botões
        buttonAdicionar.addActionListener(this);
        buttonRemover.addActionListener(this);
        buttonConsultar.addActionListener(this);
        buttonSair.addActionListener(this);

        // Exibe a janela principal
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(120, 40));
        button.setFont(new Font("Arial", Font.PLAIN, 16));
        return button;
    }

    private void connectToServer() {
        try {
            // Endereço e porta do socket
            socket = new Socket("localhost", 4444);
            System.out.println("Conectado ao servidor.");

            // Fluxo de entrada e saída de dados
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            System.out.println("Exceção ao conectar ao servidor: " + e.toString());
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == buttonAdicionar) {
            adicionarLivro();
        } else if (e.getSource() == buttonRemover) {
            removerLivro();
        } else if (e.getSource() == buttonConsultar) {
            consultarLivro();
        } else if (e.getSource() == buttonSair) {
            sair();
        }
    }

    private void adicionarLivro() {
        try {
            // Cria um novo livro com base nos dados informados
            Livro livro = new Livro();
            livro.adicionar();

            // Verifique se o usuário clicou em "Cancelar"
            if (livro.getTitulo() == null) {
                return; // Retorna sem fazer mais nada
            }

            // Envia o livro para o servidor
            out.writeInt(1); // Código para adicionar livro
            out.writeObject(livro);
            out.writeBoolean(livro.isDisponivel()); // Envia o status de disponibilidade do livro
            out.flush();

            JOptionPane.showMessageDialog(null, "Livro '" + livro.getTitulo() + "' com ID " + livro.getId() + " foi adicionado.");

        } catch (IOException e) {
            System.out.println("Exceção ao enviar livro: " + e.toString());
        }
    }

    private void removerLivro() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do livro a ser removido:"));

            // Envia o ID para o servidor
            out.writeInt(2); // Código para remover livro
            out.writeInt(id);
            out.flush();

            // Recebe a confirmação de remoção do servidor
            boolean removido = in.readBoolean();

            if (removido) {
                JOptionPane.showMessageDialog(null, "Livro removido com sucesso.");
            } else {
                JOptionPane.showMessageDialog(null, "Livro não encontrado.");
            }

        } catch (IOException e) {
            System.out.println("Exceção ao remover livro: " + e.toString());
        }
    }

    private void consultarLivro() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do livro a ser consultado:"));

            // Envia o ID para o servidor
            out.writeInt(3); // Código para consultar livro
            out.writeInt(id);
            out.flush();

            // Recebe o livro consultado do servidor
            Livro livroConsultado = (Livro) in.readObject();

            if (livroConsultado != null) {
                String infoLivro = livroConsultado.consultarInfo(id);
                JOptionPane.showMessageDialog(null, "Informações do livro:\n" + infoLivro);

            } else {
                JOptionPane.showMessageDialog(null, "Não existe livro com ID " + id + ".");
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exceção ao consultar livro: " + e.toString());
        }
    }

    private void sair() {
        try {
            // Envia código de saída para o servidor
            out.writeInt(4); // Código para sair

            // Fecha a conexão
            socket.close();

            // Encerra a aplicação
            System.exit(0);

        } catch (IOException e) {
            System.out.println("Exceção ao sair: " + e.toString());
        }
    }
}
