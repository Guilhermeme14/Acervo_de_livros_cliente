package Principal;

import java.io.Serializable;
import java.util.List;
import javax.swing.*;

public class Livro implements Serializable {

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

    public void setDisponivel(boolean disponivel) {
        this.disponivel = disponivel;
    }

    public void adicionar() {
        // Cria uma janela 'panel' e o layout
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // Define os campos, seus rótulos, e os adiciona no 'panel'
        JTextField idCampo = new JTextField(10);
        panel.add(new JLabel("Digite o ID do livro: "));
        panel.add(idCampo);

        JTextField tituloCampo = new JTextField(10);
        panel.add(new JLabel("Digite o título: "));
        panel.add(tituloCampo);

        JTextField autorCampo = new JTextField(10);
        panel.add(new JLabel("Digite o autor: "));
        panel.add(autorCampo);

        JTextField editoraCampo = new JTextField(10);
        panel.add(new JLabel("Digite a editora: "));
        panel.add(editoraCampo);

        JTextField anoCampo = new JTextField(10);
        panel.add(new JLabel("Digite o ano de publicação: "));
        panel.add(anoCampo);

        JTextField colecaoCampo = new JTextField(10);
        panel.add(new JLabel("Digite a coleção: "));
        panel.add(colecaoCampo);

        JTextField assuntoCampo = new JTextField(10);
        panel.add(new JLabel("Digite o assunto: "));
        panel.add(assuntoCampo);

        JTextArea sinopseCampo = new JTextArea(5, 5); // Define uma área maior de texto para a sinopse
        sinopseCampo.setLineWrap(true); // Quebra de linhas automática
        (sinopseCampo).setBorder(new JTextField().getBorder()); // Borda igual ao JTextField
        panel.add(new JLabel("Digite a sinopse: ")); // Rótulo
        panel.add(new JScrollPane(sinopseCampo)); // JScrollPane para os rótulos não se moverem ao digitar

        String[] idiomaOpcoes = {"Portugues", "Ingles", "Espanhol"}; // Idiomas que podem ser escolhidos
        JComboBox<String> menuEscolherIdioma = new JComboBox<>(idiomaOpcoes); // Menu dropdown com as escolhas
        panel.add(new JLabel("Escolha um idioma: ")); // Rótulo
        panel.add(menuEscolherIdioma); // Adiciona menu ao 'panel'

        UIManager.put("OptionPane.yesButtonText", "Sim");
        UIManager.put("OptionPane.noButtonText", "Não");
        int opcaoDisponibilidade = JOptionPane.showConfirmDialog(null, "O livro está disponível?", "Disponibilidade", JOptionPane.YES_NO_OPTION);
        boolean disponibilidade = (opcaoDisponibilidade == JOptionPane.YES_OPTION);

        // Exibe o painel na janela de diálogo
        int opcao = JOptionPane.showOptionDialog(
                null,
                panel,
                "Adicionar Livro",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                null,
                null
        );

        // Adiciona o livro se o botão OK for selecionado
        if (opcao == JOptionPane.OK_OPTION) {
            setId(Integer.parseInt(idCampo.getText()));
            setTitulo(tituloCampo.getText());
            setAutor(autorCampo.getText());
            setEditora(editoraCampo.getText());
            setColecao(colecaoCampo.getText());
            setAssunto(assuntoCampo.getText());
            setSinopse(sinopseCampo.getText());
            setIdioma((String) menuEscolherIdioma.getEditor().getItem());
            setAno(Integer.parseInt(anoCampo.getText()));
            setDisponivel(disponibilidade);
        }

        // Não adiciona o livro se o botão CANCEL for selecionado
        if (opcao == JOptionPane.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(null, "Ação cancelada.");
        }
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
        for (Livro livro : livroAcervo) {
            if (livro.getId() == id) {
                livroAcervo.remove(livro);
                return true;
            }
        }
        return false;
    }
}
