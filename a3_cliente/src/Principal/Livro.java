package Principal;

import java.awt.GridLayout;
import java.io.Serializable;
import java.util.Iterator;
import java.util.List;
import javax.swing.*;

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
        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(10, 2));

        // Adicione os campos de entrada ao painel
        JTextField idField = new JTextField();
        panel.add(new JLabel("ID do livro:"));
        panel.add(idField);

        JTextField tituloField = new JTextField();
        panel.add(new JLabel("Título:"));
        panel.add(tituloField);

        JTextField autorField = new JTextField();
        panel.add(new JLabel("Autor:"));
        panel.add(autorField);

        JTextField editoraField = new JTextField();
        panel.add(new JLabel("Editora:"));
        panel.add(editoraField);

        JTextField anoField = new JTextField();
        panel.add(new JLabel("Ano de publicação:"));
        panel.add(anoField);

        JTextField colecaoField = new JTextField();
        panel.add(new JLabel("Coleção:"));
        panel.add(colecaoField);

        JTextField assuntoField = new JTextField();
        panel.add(new JLabel("Assunto:"));
        panel.add(assuntoField);

        JTextField sinopseField = new JTextField();
        panel.add(new JLabel("Sinopse:"));
        panel.add(sinopseField);

        JTextField idiomaField = new JTextField();
        panel.add(new JLabel("Idioma:"));
        panel.add(idiomaField);

        // Exiba o painel na janela de diálogo
        while (true) {
            int result = JOptionPane.showConfirmDialog(null, panel, "Adicionar Livro", JOptionPane.OK_CANCEL_OPTION);

            // Verifique se o usuário clicou em "OK"
            if (result == JOptionPane.OK_OPTION) {
                // Verifique se todos os campos foram preenchidos
                if (idField.getText().isEmpty() || tituloField.getText().isEmpty() || autorField.getText().isEmpty()
                        || editoraField.getText().isEmpty() || anoField.getText().isEmpty() || colecaoField.getText().isEmpty()
                        || assuntoField.getText().isEmpty() || sinopseField.getText().isEmpty() || idiomaField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Preencha todos os campos!", "Aviso", JOptionPane.WARNING_MESSAGE);
                } else {
                    break;
                }
            } else {
                // O usuário clicou em "Cancelar" ou fechou a janela, então retorna
                return;
            }
        }

        boolean disponibilidade = false;

        while (true) {
            UIManager.put("OptionPane.yesButtonText", "Sim");
            UIManager.put("OptionPane.noButtonText", "Não");

            int opcaoDisponibilidade = JOptionPane.showConfirmDialog(null, "O livro está disponível?", "Disponibilidade", JOptionPane.YES_NO_OPTION);

            if (opcaoDisponibilidade == JOptionPane.YES_OPTION || opcaoDisponibilidade == JOptionPane.NO_OPTION) {
                disponibilidade = (opcaoDisponibilidade == JOptionPane.YES_OPTION);
                break;
            }
        }

        panel.add(new JLabel("Disponível?"));
        panel.add(new JLabel(disponibilidade ? "Sim" : "Não"));

        // Obtenha os valores dos campos de entrada
        int id = Integer.parseInt(idField.getText());
        String titulo = tituloField.getText();
        String autor = autorField.getText();
        String editora = editoraField.getText();
        int ano = Integer.parseInt(anoField.getText());
        String colecao = colecaoField.getText();
        String assunto = assuntoField.getText();
        String sinopse = sinopseField.getText();
        String idioma = idiomaField.getText();

        // Faça algo com os valores obtidos, como atribuí-los aos atributos da classe
        setId(id);
        setTitulo(titulo);
        setAutor(autor);
        setEditora(editora);
        setAno(ano);
        setColecao(colecao);
        setAssunto(assunto);
        setSinopse(sinopse);
        setIdioma(idioma);
        setDisponivel(disponibilidade);
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
                + "\nDisponível: " + (disponivel ? "Sim" : "Não");
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
