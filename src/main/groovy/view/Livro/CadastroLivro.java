package view.Livro;

import controller.LivroController;
import model.LivroModel;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

public class CadastroLivro extends JFrame {
    private JTextField fieldTitulo;
    private JTextField fieldTema;
    private JTextField fieldAutor;
    private JTextField fieldIsbn;
    private JButton cadastrarButton;
    private JTextField fieldQuantidade;
    private JFormattedTextField fieldData;
    private JPanel mainPanel;
    private LivroController livroController;

    public CadastroLivro() {
        livroController = new LivroController();
        this.setTitle("Salvar Usuario");
        this.setContentPane(mainPanel);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        try {
            MaskFormatter dataMask = new MaskFormatter("##/##/####");
            dataMask.setPlaceholderCharacter(' ');
            dataMask.install(fieldData);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        this.setVisible(true);

        cadastrarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                LivroModel livro = new LivroModel();
                livro.setTitulo(fieldTitulo.getText());
                livro.setTema(fieldTema.getText());
                livro.setAutor(fieldAutor.getText());
                livro.setIsbn(fieldIsbn.getText());
                livro.setData_publicacao(fieldData.getText());
                livro.setQuantidade_disponivel(Integer.parseInt(fieldQuantidade.getText()));

                try {
                    JOptionPane.showMessageDialog(null, livroController.salvar(livro));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
