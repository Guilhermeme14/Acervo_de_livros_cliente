package acervodelivros;

import acervodelivros.Livro;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.table.DefaultTableModel;

public class AcervoDeLivrosUI {
    private JFrame menuFrame;
    private JFrame resultadoFrame;
    private JFrame tabelaFrame;
    private JFrame editarFrame;
    private AcervoDeLivros acervo;
    private DefaultTableModel tableModel;
    private JButton btnEditarLivro;

    public AcervoDeLivrosUI() {
        menuFrame = new JFrame("Menu");
        resultadoFrame = new JFrame("Resultado");
        tabelaFrame = new JFrame("Tabela de Livros");
        editarFrame = new JFrame("Editar Livro");
        acervo = new AcervoDeLivros();
        tableModel = new DefaultTableModel(new Object[]{"Título", "Autor", "Editora"}, 0);
        btnEditarLivro = new JButton("Editar");
    }

    public void exibir() {
        // Configurações da janela de menu
        menuFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        menuFrame.setLayout(new GridLayout(4, 1));
        menuFrame.setSize(300, 250);
        menuFrame.setLocationRelativeTo(null);

        // Botão "Adicionar"
        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirFormularioAdicionar();
            }
        });
        menuFrame.add(btnAdicionar);

        // Botão "Remover"
        JButton btnRemover = new JButton("Remover");
        btnRemover.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirResultado("Remover Livro");
            }
        });
        menuFrame.add(btnRemover);

        // Botão "Editar"
        JButton btnEditar = new JButton("Editar");
        btnEditar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirTabelaEditar();
            }
        });
        menuFrame.add(btnEditar);

        // Botão "Consultar"
        JButton btnConsultar = new JButton("Consultar");
        btnConsultar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                exibirTabela();
            }
        });
        menuFrame.add(btnConsultar);

        // Exibe a janela de menu
        menuFrame.setVisible(true);
    }

    private void exibirFormularioAdicionar() {
        JPanel panel = new JPanel(new GridLayout(9, 2));

        JLabel lblTitulo = new JLabel("Título: ");
        JTextField txtTitulo = new JTextField();
        panel.add(lblTitulo);
        panel.add(txtTitulo);

        JLabel lblAutor = new JLabel("Autor: ");
        JTextField txtAutor = new JTextField();
        panel.add(lblAutor);
        panel.add(txtAutor);

        JLabel lblEditora = new JLabel("Editora: ");
        JTextField txtEditora = new JTextField();
        panel.add(lblEditora);
        panel.add(txtEditora);

        JButton btnAdicionar = new JButton("Adicionar");
        btnAdicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String titulo = txtTitulo.getText();
                String autor = txtAutor.getText();
                String editora = txtEditora.getText();

                Livro livro = new Livro(titulo, autor, editora);
                acervo.adicionarLivro(livro);

                txtTitulo.setText("");
                txtAutor.setText("");
                txtEditora.setText("");

                JOptionPane.showMessageDialog(null, "Livro adicionado com sucesso.");
            }
        });

        panel.add(btnAdicionar);

        resultadoFrame.getContentPane().removeAll();
        resultadoFrame.getContentPane().add(panel, BorderLayout.CENTER);
        resultadoFrame.setSize(300, 300);
        resultadoFrame.setLocationRelativeTo(null);
        resultadoFrame.setVisible(true);
    }

    private void exibirTabelaEditar() {
        ArrayList<Livro> livros = acervo.getLivros();

        tableModel.setRowCount(0);
        for (Livro livro : livros) {
            Object[] row = {livro.getTitulo(), livro.getAutor(), livro.getEditora()};
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        editarFrame.getContentPane().removeAll();
        editarFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);

        // Adicionar botão "Editar" na tabela
        JPanel panel = new JPanel();
        panel.add(btnEditarLivro);
        editarFrame.getContentPane().add(panel, BorderLayout.SOUTH);

        btnEditarLivro.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow = table.getSelectedRow();
                if (selectedRow != -1) {
                    String titulo = (String) table.getValueAt(selectedRow, 0);
                    exibirFormularioEditar(titulo);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um livro para editar.");
                }
            }
        });

        editarFrame.setSize(500, 300);
        editarFrame.setLocationRelativeTo(null);
        editarFrame.setVisible(true);
    }

    private void exibirFormularioEditar(String titulo) {
    JPanel panel = new JPanel();
    panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

    JLabel lblTitulo = new JLabel("Título: ");
    JTextField txtTitulo = new JTextField(titulo);
    txtTitulo.setEditable(false);
    panel.add(lblTitulo);
    panel.add(txtTitulo);

    JLabel lblNovoTitulo = new JLabel("Novo Título: ");
    JTextField txtNovoTitulo = new JTextField();
    panel.add(lblNovoTitulo);
    panel.add(txtNovoTitulo);

    JLabel lblNovoAutor = new JLabel("Novo Autor: ");
    JTextField txtNovoAutor = new JTextField();
    panel.add(lblNovoAutor);
    panel.add(txtNovoAutor);

    JLabel lblNovaEditora = new JLabel("Nova Editora: ");
    JTextField txtNovaEditora = new JTextField();
    panel.add(lblNovaEditora);
    panel.add(txtNovaEditora);

    JButton btnEditar = new JButton("Editar");
    btnEditar.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            String novoTitulo = txtNovoTitulo.getText();
            String novoAutor = txtNovoAutor.getText();
            String novaEditora = txtNovaEditora.getText();

            boolean livroEditado = acervo.editarLivro(titulo, novoTitulo, novoAutor, novaEditora);

            if (livroEditado) {
                JOptionPane.showMessageDialog(null, "Livro editado com sucesso.");
                exibirTabelaEditar(); // Atualiza a tabela após a edição
            } else {
                JOptionPane.showMessageDialog(null, "Livro não encontrado.");
            }

            txtNovoTitulo.setText("");
            txtNovoAutor.setText("");
            txtNovaEditora.setText("");
        }
    });

    panel.add(btnEditar);

    editarFrame.getContentPane().removeAll();
    editarFrame.getContentPane().add(panel, BorderLayout.CENTER);
    editarFrame.setSize(300, 200);
    editarFrame.setLocationRelativeTo(null);
    editarFrame.setVisible(true);
}


    private void exibirResultado(String mensagem) {
        JLabel label = new JLabel(mensagem);
        label.setHorizontalAlignment(JLabel.CENTER);

        resultadoFrame.getContentPane().removeAll();
        resultadoFrame.getContentPane().add(label, BorderLayout.CENTER);
        resultadoFrame.setSize(300, 100);
        resultadoFrame.setLocationRelativeTo(null);
        resultadoFrame.setVisible(true);
    }

    private void exibirTabela() {
        ArrayList<Livro> livros = acervo.getLivros();

        tableModel.setRowCount(0);
        for (Livro livro : livros) {
            Object[] row = {livro.getTitulo(), livro.getAutor(), livro.getEditora()};
            tableModel.addRow(row);
        }

        JTable table = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(table);

        tabelaFrame.getContentPane().removeAll();
        tabelaFrame.getContentPane().add(scrollPane, BorderLayout.CENTER);
        tabelaFrame.setSize(500, 300);
        tabelaFrame.setLocationRelativeTo(null);
        tabelaFrame.setVisible(true);
    }

    public static void main(String[] args) {
        AcervoDeLivrosUI acervoUI = new AcervoDeLivrosUI();
        acervoUI.exibir();
    }
}

 

