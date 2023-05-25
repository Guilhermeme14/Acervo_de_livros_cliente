
package acervodelivrosui;

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
    @Override
    public String toString() {
        return "Título: " + titulo + "\nAutor: " + autor + "\nEditora: " + editora;
    }

    public void setTitulo(String titulo) {
    this.titulo = titulo;
}


    public void setAutor(String autor) {
    this.autor = autor;
}


    public void setEditora(String editora) {
    this.editora = editora;
}
}
