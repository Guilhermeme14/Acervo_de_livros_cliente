package acervodelivrosui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Cliente extends JFrame {
    private DefaultTableModel tabelaModelo;
    private JTable tabelaLivros;
    private JTextField campoTitulo;
    private JTextField campoAutor;
    private JTextField campoEditora;

    public Cliente() {
        tabelaModelo = new DefaultTableModel();
        tabelaModelo.addColumn("Título");
        tabelaModelo.addColumn("Autor");
        tabelaModelo.addColumn("Editora");

        tabelaLivros = new JTable(tabelaModelo);
        tabelaLivros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JButton adicionarButton = new JButton("Adicionar Livro");
        adicionarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirFormularioAdicaoLivro();
            }
        });

        JButton removerButton = new JButton("Remover Livro");
        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removerLivroSelecionado();
            }
        });

        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(new JScrollPane(tabelaLivros), BorderLayout.CENTER);
        panel.add(adicionarButton, BorderLayout.PAGE_END);
        panel.add(removerButton, BorderLayout.LINE_END);

        setTitle("Acervo de Livros");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);
        add(panel);
    }

    private void exibirFormularioAdicaoLivro() {
        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        JLabel labelTitulo = new JLabel("Título:");
        JLabel labelAutor = new JLabel("Autor:");
        JLabel labelEditora = new JLabel("Editora:");
        campoTitulo = new JTextField();
        campoAutor = new JTextField();
        campoEditora = new JTextField();
        panel.add(labelTitulo);
        panel.add(campoTitulo);
        panel.add(labelAutor);
        panel.add(campoAutor);
        panel.add(labelEditora);
        panel.add(campoEditora);

        int resultado = JOptionPane.showConfirmDialog(this, panel, "Adicionar Livro",
                JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);
        if (resultado == JOptionPane.OK_OPTION) {
            String titulo = campoTitulo.getText();
            String autor = campoAutor.getText();
            String editora = campoEditora.getText();
            Livro livro = new Livro(titulo, autor, editora);
            adicionarLivro(livro);
        }
    }

    private void adicionarLivro(Livro livro) {
        tabelaModelo.addRow(new Object[]{livro.getTitulo(), livro.getAutor(), livro.getEditora()});
    }

    private void removerLivroSelecionado() {
        int selectedRow = tabelaLivros.getSelectedRow();
        if (selectedRow != -1) {
            tabelaModelo.removeRow(selectedRow);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                Cliente acervoDeLivrosUI = new Cliente();
                acervoDeLivrosUI.setVisible(true);
            }
        });
    }
}