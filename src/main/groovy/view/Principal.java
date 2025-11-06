package view;

import javax.swing.*;

public class Principal extends JFrame{
    private JPanel panel1;
    private JMenuBar menu = new JMenuBar();

    public Principal() {
        criarMenuPrincipal();
        this.setTitle("CRUD Library");
        this.setContentPane(panel1);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setVisible(true);
    }

    private void criarMenuPrincipal() {
        this.setJMenuBar(menu);
        JMenu livro = new JMenu("Livro");
        JMenuItem livro_adicionar = new JMenuItem("Adicionar");
        JMenuItem livro_editar = new JMenuItem("Editar");
        JMenuItem livro_remove = new JMenuItem("Remover");
        livro.add(livro_adicionar);
        livro.add(livro_editar);
        livro.add(livro_remove);
        JMenu usuario = new JMenu("Usuário");
        JMenuItem usuario_adicionar = new JMenuItem("Adicionar");
        JMenuItem usuario_editar = new JMenuItem("Editar");
        JMenuItem usuario_remove = new JMenuItem("Remover");
        usuario.add(usuario_adicionar);
        usuario.add(usuario_editar);
        usuario.add(usuario_remove);

        menu.add(livro);
        menu.add(usuario);
    }
}
