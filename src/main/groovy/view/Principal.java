package view;

import view.Livro.BuscarLivro;
import view.Livro.CadastroLivro;
import view.Livro.EditarLivro;
import view.Usuario.BuscarUsuario;
import view.Usuario.CadastroUsuario;
import view.Usuario.EditarUsuario;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

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
        JMenuItem livro_buscar = new JMenuItem("Buscar");
        JMenuItem livro_remove = new JMenuItem("Remover");
        livro.add(livro_adicionar);
        livro.add(livro_buscar);
        livro.add(livro_remove);
        JMenu usuario = new JMenu("Usuário");
        JMenuItem usuario_adicionar = new JMenuItem("Adicionar");
        JMenuItem usuario_buscar = new JMenuItem("Buscar");
        JMenuItem usuario_remove = new JMenuItem("Remover");
        usuario.add(usuario_adicionar);
        usuario.add(usuario_buscar);
        usuario.add(usuario_remove);
        JMenu emprestimo = new JMenu("Empréstimos");
        JMenuItem emprestimo_emprestar = new JMenuItem("Emprestar/Devolver");
        emprestimo.add(emprestimo_emprestar);

        menu.add(emprestimo);
        menu.add(usuario);
        menu.add(livro);

        emprestimo_emprestar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.Emprestimo.BuscarUsuario buscar = new view.Emprestimo.BuscarUsuario();
            }
        });
        usuario_adicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CadastroUsuario cadastro = new CadastroUsuario();
            }
        });
        usuario_buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscarUsuario editar = new BuscarUsuario();
            }
        });
        usuario_remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscarUsuario buscar = new BuscarUsuario();
            }
        });

        livro_adicionar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                CadastroLivro cadastro = new CadastroLivro();
            }
        });
        livro_buscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscarLivro editar = new BuscarLivro();
            }
        });
        livro_remove.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscarLivro buscar = new BuscarLivro();
            }
        });
    }
}
