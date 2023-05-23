import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AcervoDeLivrosUI extends Application {
    private ObservableList<Livro> livros;
    private TableView<Livro> tabelaLivros;
    private TextField campoTitulo;
    private TextField campoAutor;
    private TextField campoEditora;

    public AcervoDeLivrosUI() {
        livros = FXCollections.observableArrayList();
    }

    public void adicionarLivro(Livro livro) {
        livros.add(livro);
    }

    public void removerLivro(Livro livro) {
        livros.remove(livro);
    }

    @Override
    public void start(Stage primaryStage) {
        tabelaLivros = new TableView<>();
        tabelaLivros.setEditable(false);

        TableColumn<Livro, String> colunaTitulo = new TableColumn<>("Título");
        colunaTitulo.setCellValueFactory(new PropertyValueFactory<>("titulo"));

        TableColumn<Livro, String> colunaAutor = new TableColumn<>("Autor");
        colunaAutor.setCellValueFactory(new PropertyValueFactory<>("autor"));

        TableColumn<Livro, String> colunaEditora = new TableColumn<>("Editora");
        colunaEditora.setCellValueFactory(new PropertyValueFactory<>("editora"));

        tabelaLivros.getColumns().addAll(colunaTitulo, colunaAutor, colunaEditora);

        Button adicionarButton = new Button("Adicionar Livro");
        adicionarButton.setOnAction(e -> exibirFormularioAdicaoLivro());

        Button removerButton = new Button("Remover Livro");
        removerButton.setOnAction(e -> removerLivroSelecionado());

        VBox vbox = new VBox(10);
        vbox.setPadding(new Insets(10));
        vbox.getChildren().addAll(tabelaLivros, adicionarButton, removerButton);

        Scene scene = new Scene(vbox, 400, 300);
        primaryStage.setTitle("Acervo de Livros");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void exibirFormularioAdicaoLivro() {
        Dialog<Livro> dialog = new Dialog<>();
        dialog.setTitle("Adicionar Livro");
        dialog.setHeaderText("Preencha as informações do livro");

        ButtonType adicionarButtonType = new ButtonType("Adicionar", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(adicionarButtonType, ButtonType.CANCEL);

        campoTitulo = new TextField();
        campoTitulo.setPromptText("Título");
        campoAutor = new TextField();
        campoAutor.setPromptText("Autor");
        campoEditora = new TextField();
        campoEditora.setPromptText("Editora");

        dialog.getDialogPane().setContent(new VBox(10, campoTitulo, campoAutor, campoEditora));

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == adicionarButtonType) {
                String titulo = campoTitulo.getText();
                String autor = campoAutor.getText();
                String editora = campoEditora.getText();
                Livro livro = new Livro(titulo, autor, editora);
                adicionarLivro(livro);
                return livro;
            }
            return null;
        });

        dialog.showAndWait().ifPresent(livro -> {
            campoTitulo.clear();
            campoAutor.clear();
            campoEditora.clear();
            tabelaLivros.getItems().add(livro);
        });
    }

    private void removerLivroSelecionado() {
        Livro livroSelecionado = tabelaLivros.getSelectionModel().getSelectedItem();
        if (livroSelecionado != null) {
            tabelaLivros.getItems().remove(livroSelecionado);
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}

class Livro {
    private String titulo;
    private String autor;
    private String editora;

    public Livro(String titulo, String autor, String editora) {
        this.titulo = titulo;
        this.autor = autor;
        this.editora = editora;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getAutor() {
        return autor;
    }

    public String getEditora() {
        return editora;
    }
}