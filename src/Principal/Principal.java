package Principal;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import javax.swing.JOptionPane;

public class Principal {

    public static void main(String[] args) {
        try {

            Socket socket = new Socket("localhost", 4444);
            ObjectOutputStream dadosout = new ObjectOutputStream(socket.getOutputStream());

            String titulo = JOptionPane.showInputDialog(null, "Digite o título do livro: ");
            String autor = JOptionPane.showInputDialog(null, "Digite o autor: ");
            String editora = JOptionPane.showInputDialog(null, "Digite a editora: ");
            int ano = Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o ano de publicação: "));
            String colecao = JOptionPane.showInputDialog(null, "Digite o coleção: ");
            String assunto = JOptionPane.showInputDialog(null, "Digite o assunto: ");
            String sinopse = JOptionPane.showInputDialog(null, "Digite a sinopse: ");
            String idioma = JOptionPane.showInputDialog(null, "Digite o idioma: ");

            Livro livro = new Livro(titulo, autor, editora, ano, colecao, assunto, sinopse, idioma, true);
            dadosout.writeObject(livro);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}