package Principal;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Principal {

    public static void main(String[] args) {
        try {

            // Endereço e porta do socket
            Socket socket = new Socket("localhost", 4444);
            System.out.println("Conectado ao servidor.");

            // Fluxo de entrada e saída de dados
            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            while (true) {
                int escolha = Integer.parseInt(JOptionPane.showInputDialog(null, "1) Adicionar livro \n2) Atualizar informações de um livro \n3) Remover livro \n4) Consultar livro \n5) Sair"));
                out.writeInt(escolha); // Envia o processo escolhido para o servidor
                out.flush();

                switch (escolha) {
                    case 1 -> {
                        // Adicionar
                        Livro livro = new Livro();
                        livro.adicionar();
                        System.out.println("Livro '" + livro.getTitulo() + "' com ID " + livro.getId() + " foi adicionado.");

                        // Envia o livro para o servidor
                        out.writeObject(livro);
                        out.flush();
                    }
                    case 2 -> {
                        // Atualizar
                        int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Informe o ID do livro a ser atualizado: "));

                        // Escreve as novas informações do livro
                        Livro livroAtualizado = new Livro();
                        livroAtualizado.atualizar(id);

                        // Envia as informações atualizadas para o servidor
                        out.writeObject(livroAtualizado);
                        out.flush();

                    }

                    case 3 -> {
                        // Remover
                        int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Informe o ID do livro a ser deletado: "));
                    }

                    case 4 -> {
                        // Consultar
                        int id = Integer.parseInt(JOptionPane.showInputDialog(null, "Informe o ID do livro a ser consultado: "));

                        // Envia o ID para o servidor
                        out.writeInt(id);
                        out.flush();

                        // Recebe o livro pedido ao servidor
                        Livro livroConsultado = (Livro) in.readObject();
                        if (livroConsultado != null) {
                            JOptionPane.showMessageDialog(null, "Livro consultado: " + livroConsultado.consultarInfo(id));

                        } else {
                            JOptionPane.showMessageDialog(null, "Não existe livro com ID " + id + ".");
                        }
                    }

                    case 5 -> {
                        // Sair
                    }

                    default ->
                        System.out.println("Entrada inválida.");
                }

            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Exceção: " + e.getMessage());
        }
    }
}
