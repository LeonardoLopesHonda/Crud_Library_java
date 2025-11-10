package view.Livro;

import controller.LivroController;
import model.LivroModel;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

public class EditarLivro extends JFrame {
    private JTextField fieldTitulo;
    private JTextField fieldTema;
    private JTextField fieldAutor;
    private JTextField fieldIsbn;
    private JButton editarButton;
    private JFormattedTextField fieldData;
    private JTextField fieldQuantidade;
    private JPanel mainPanel;
    private LivroController livroController;

    public EditarLivro(LivroModel livro) {
        livroController = new LivroController();
        this.setTitle("Editar Livro");
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
        fieldTitulo.setText(livro.getTitulo());
        fieldTema.setText(livro.getTema());
        fieldAutor.setText(livro.getAutor());
        fieldIsbn.setText(livro.getIsbn());
        fieldData.setText(livro.getData_publicacao());
        fieldQuantidade.setText(Integer.toString(livro.getQuantidade_disponivel()));

        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                livro.setTitulo(fieldTitulo.getText());
                livro.setTema(fieldTema.getText());
                livro.setAutor(fieldAutor.getText());
                livro.setIsbn(fieldIsbn.getText());
                livro.setData_publicacao(fieldData.getText());
                livro.setQuantidade_disponivel(Integer.parseInt(fieldQuantidade.getText()));
                try {
                    JOptionPane.showMessageDialog(null, livroController.editar(livro));
                } catch (SQLException ex) {
                    throw new RuntimeException(ex);
                }
            }
        });
    }
}
