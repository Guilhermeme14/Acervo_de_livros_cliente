package Principal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.border.EmptyBorder;

public class ClienteGUI extends JFrame {

    // Socket e fluxo de dados
    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    // Botões
    private final JButton botaoAdicionar;
    private final JButton botaoRemover;
    private final JButton botaoConsultar;
    private final JButton botaoSair;

    public ClienteGUI() {
        // Configuração do Look and Feel Nimbus
        try {
            UIManager.setLookAndFeel("javax.swing.plaf.nimbus.NimbusLookAndFeel");
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            System.out.println("Exceção ao criar UI: " + e.getMessage());
        }

        // Configurações da janela principal
        setTitle("Acervo");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);

        // Painel principal
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(10, 10, 10, 10));
        setContentPane(contentPane);
        contentPane.setLayout(new GridBagLayout());

        // Criação dos componentes da interface
        botaoAdicionar = criarBotao("Adicionar");
        botaoRemover = criarBotao("Remover");
        botaoConsultar = criarBotao("Consultar");
        botaoSair = criarBotao("Sair");

        // Adiciona os componentes ao painel
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPane.add(botaoAdicionar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        contentPane.add(botaoRemover, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        contentPane.add(botaoConsultar, gbc);

        gbc.gridx = 1;
        gbc.gridy = 1;
        contentPane.add(botaoSair, gbc);

        // Evento de clique do botão "Adicionar"
        botaoAdicionar.addActionListener((ActionEvent e) -> {
            adicionarLivro();
        });

        // Evento de clique do botão "Remover"
        botaoRemover.addActionListener((ActionEvent e) -> {
            removerLivro();
        });

        // Evento de clique do botão "Consultar"
        botaoConsultar.addActionListener((ActionEvent e) -> {
            consultarLivro();
        });

        // Evento de clique do botão "Consultar"
        botaoSair.addActionListener((ActionEvent e) -> {
            sair();
        });

        // Conecta ao servidor e exibe a janela principal
        comunicarComServer();
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Método para criar um botão com determinada fonte e tamanho
    private JButton criarBotao(String rotulo) {
        JButton botao = new JButton(rotulo);
        botao.setPreferredSize(new Dimension(120, 40));
        botao.setFont(new Font("Arial", Font.PLAIN, 16));
        return botao;

    }

    private void comunicarComServer() {
        try {
            // Endereço e porta do socket
            socket = new Socket("localhost", 4444);
            System.out.println("Conectado ao servidor.");

            // Fluxo de entrada e saída de dados
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

        } catch (IOException e) {
            System.out.println("Exceção ao conectar ao servidor: " + e.getMessage());
        }
    }

    private void adicionarLivro() {
        try {
            // Cria um novo livro com base nos dados informados
            Livro livro = new Livro();
            livro.adicionar();

            // Envia o livro para o servidor
            out.writeInt(1); // Código para adicionar livro
            out.writeObject(livro);
            out.writeBoolean(livro.isDisponivel()); // Envia o status de disponibilidade do livro
            out.flush();

            JOptionPane.showMessageDialog(null, "Livro '" + livro.getTitulo() + "' com ID " + livro.getId() + " foi adicionado.");

        } catch (IOException e) {
            System.out.println("Exceção ao enviar livro: " + e.getMessage());
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
            System.out.println("Exceção ao remover livro: " + e.getMessage());
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
                JOptionPane.showMessageDialog(null, infoLivro, "Informacoes sobre o livro", JOptionPane.INFORMATION_MESSAGE);
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
