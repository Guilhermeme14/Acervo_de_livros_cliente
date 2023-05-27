package Principal;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.text.MaskFormatter;

public class Principal extends JFrame {

    private Socket socket;
    private ObjectOutputStream out;
    private ObjectInputStream in;

    private JButton buttonAdicionar;
    private JButton buttonAtualizar;
    private JButton buttonRemover;
    private JButton buttonConsultar;
    private JButton buttonSair;

    public Principal() {
        // Configurações da janela
        setTitle("Cliente");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);
        setLayout(new FlowLayout());

        // Componentes da interface
        buttonAdicionar = new JButton("Adicionar");
        buttonAtualizar = new JButton("Atualizar");
        buttonRemover = new JButton("Remover");
        buttonConsultar = new JButton("Consultar");
        buttonSair = new JButton("Sair");

        // Adiciona os componentes à janela
        add(buttonAdicionar);
        add(buttonAtualizar);
        add(buttonRemover);
        add(buttonConsultar);
        add(buttonSair);

        // Evento de clique do botão "Adicionar"
        buttonAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                adicionarLivro();
            }
        });

        // Evento de clique do botão "Atualizar"
        buttonAtualizar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                atualizarLivro();
            }
        });

        // Evento de clique do botão "Remover"
        buttonRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerLivro();
            }
        });

        // Evento de clique do botão "Consultar"
        buttonConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                consultarLivro();
            }
        });

        // Evento de clique do botão "Sair"
        buttonSair.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sair();
            }
        });

        // Conecta ao servidor
        connectToServer();
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

    private void adicionarLivro() {
        try {
            // Cria um novo livro com base nos dados informados
            Livro livro = new Livro();
            livro.adicionar();

            // Envia o livro para o servidor
            out.writeInt(1); // Código para adicionar livro
            out.writeObject(livro);
            out.flush();

            JOptionPane.showMessageDialog(null,"Livro '" + livro.getTitulo() + "' com ID " + livro.getId() + " foi adicionado.");

        } catch (IOException e) {
            System.out.println("Exceção ao enviar livro: " + e.toString());
        }
    }

    private void atualizarLivro() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do livro a ser atualizado:"));

            // Envia o ID para o servidor
            out.writeInt(2); // Código para atualizar livro
            out.writeInt(id);
            out.flush();

            // Recebe a confirmação do servidor
            boolean encontrado = in.readBoolean();

            if (encontrado) {
                // Livro encontrado, permite a atualização
                Livro livroAtualizado = new Livro();
                livroAtualizado.atualizar(id);

                // Envia o livro atualizado para o servidor
                out.writeObject(livroAtualizado);
                out.flush();

                JOptionPane.showMessageDialog(null, "Livro com ID " + id + " atualizado.");

            } else {
                JOptionPane.showMessageDialog(null,"Livro com ID " + id + " não encontrado.");
            }

        } catch (IOException e) {
            System.out.println("Exceção ao atualizar livro: " + e.toString());
        }
    }

    private void removerLivro() {
        try {
            int id = Integer.parseInt(JOptionPane.showInputDialog("Digite o ID do livro a ser removido:"));

            // Envia o ID para o servidor
            out.writeInt(4); // Código para remover livro
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
            out.writeInt(5); // Código para sair
            out.flush();

            // Fecha a conexão
            socket.close();

            // Encerra a aplicação
            System.exit(0);

        } catch (IOException e) {
            System.out.println("Exceção ao sair: " + e.toString());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new Principal().setVisible(true);
            }
        });
    }
}
