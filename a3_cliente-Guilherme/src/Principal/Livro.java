package Principal;

import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.swing.JOptionPane;

public class Livro implements Serializable {
private static final long serialVersionUID = -1355659110151542561L;


    private int id;
    private String titulo;
    private String autor;
    private String editora;
    private int ano;
    private String colecao;
    private String assunto;
    private String sinopse;
    private String idioma;
    private boolean disponivel;

    public Livro() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public String getColecao() {
        return colecao;
    }

    public void setColecao(String colecao) {
        this.colecao = colecao;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void adicionar() {
        setId(Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o ID do livro: ")));
        setTitulo(JOptionPane.showInputDialog(null, "Digite o título: "));
        setAutor(JOptionPane.showInputDialog(null, "Digite o autor: "));
        setEditora(JOptionPane.showInputDialog(null, "Digite a editora: "));
        setAno(Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o ano de publicação: ")));
        setColecao(JOptionPane.showInputDialog(null, "Digite a coleção: "));
        setAssunto(JOptionPane.showInputDialog(null, "Digite o assunto: "));
        setSinopse(JOptionPane.showInputDialog(null, "Digite a sinopse: "));
        setIdioma(JOptionPane.showInputDialog(null, "Digite o idioma: "));
    }

    public void atualizar(int id) {
        setTitulo(JOptionPane.showInputDialog(null, "Digite o título do livro: "));
        setAutor(JOptionPane.showInputDialog(null, "Digite o autor: "));
        setEditora(JOptionPane.showInputDialog(null, "Digite a editora: "));
        setAno(Integer.parseInt(JOptionPane.showInputDialog(null, "Digite o ano de publicação: ")));
        setColecao(JOptionPane.showInputDialog(null, "Digite a coleção: "));
        setAssunto(JOptionPane.showInputDialog(null, "Digite o assunto: "));
        setSinopse(JOptionPane.showInputDialog(null, "Digite a sinopse: "));
        setIdioma(JOptionPane.showInputDialog(null, "Digite o idioma: "));
    }

    public static boolean foiAtualizado(List<Livro> livroAcervo, Livro livroAtualizado) {
        for (int i = 0; i < livroAcervo.size(); i++) {
            Livro livro = livroAcervo.get(i);
            // Se o ID bater, substitui as informações do livro com as do livro atualizado
            if (livro.getId() == livroAtualizado.getId()) {
                livroAcervo.set(i, livroAtualizado);
                return true;
            }
        }
        return false;
    }

    public String consultarInfo(int id) {
        return "\nTítulo: " + getTitulo()
                + "\nAutor: " + getAutor()
                + "\nEditora: " + getEditora()
                + "\nAno de publicação: " + getAno()
                + "\nColeção: " + getColecao()
                + "\nAssunto: " + getAssunto()
                + "\nSinopse: " + getSinopse()
                + "\nIdioma: " + getIdioma()
                + "\nDisponível: " + isDisponivel();
    }

    public static Livro consultarId(List<Livro> livroAcervo, int id) {
        for (Livro livro : livroAcervo) {
            if (livro.getId() == id) {
                return livro;
            }
        }
        return null;
    }
public static boolean remover(List<Livro> livroAcervo, int id) {
    Iterator<Livro> iterator = livroAcervo.iterator();
    while (iterator.hasNext()) {
        Livro livro = iterator.next();
        if (livro.getId() == id) {
            iterator.remove();
            return true;
        }
    }
    return false;
}
}
