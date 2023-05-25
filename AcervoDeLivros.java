package acervodelivros;


import acervodelivros.Livro;
import java.util.ArrayList;

public class AcervoDeLivros {
    private ArrayList<Livro> livros;

    public AcervoDeLivros() {
        livros = new ArrayList<>();
    }

    public void adicionarLivro(Livro livro) {
        livros.add(livro);
    }

    public void removerLivro(int indice) {
        if (indice >= 0 && indice < livros.size()) {
            livros.remove(indice);
        } else {
            System.out.println("Índice inválido.");
        }
    }

    public boolean editarLivro(String titulo, String novoTitulo, String novoAutor, String novaEditora) {
        for (Livro livro : livros) {
            if (livro.getTitulo().equals(titulo)) {
                livro.setTitulo(novoTitulo);
                livro.setAutor(novoAutor);
                livro.setEditora(novaEditora);
                return true;
            }
        }
        return false;
    }

    public ArrayList<Livro> getLivros() {
        return livros;
    }
}
