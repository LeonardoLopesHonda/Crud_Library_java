package view.Usuario;

import controller.UsuarioController;
import model.UsuarioModel;

import javax.swing.*;
import javax.swing.text.MaskFormatter;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.text.ParseException;

public class EditarUsuario extends JFrame {
    private JTextField fieldNome;
    private JTextField fieldSexo;
    private JFormattedTextField fieldTelefone;
    private JFormattedTextField fieldEmail;
    private JButton EditarButton;
    private JPanel mainPanel;
    private UsuarioController usuarioController;

    public EditarUsuario(UsuarioModel usuario) {
        usuarioController = new UsuarioController();
        this.setTitle("Editar Usuario");
        this.setContentPane(mainPanel);
        this.setSize(640, 480);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        try {
            MaskFormatter telefoneMask = new MaskFormatter("(##) ####-####");
            telefoneMask.setPlaceholderCharacter(' ');
            telefoneMask.install(fieldTelefone);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }

        fieldEmail.setInputVerifier(new InputVerifier() {
            @Override
            public boolean verify(JComponent input) {
                JTextField fieldEmail = (JTextField) input;
                String email = fieldEmail.getText().trim();
                if(email.isEmpty()) return false;

                String emailRegex = "^[\\w.-]+@[\\w.-]+\\.[a-zA-Z]{2,}$";
                boolean isValidEmail = email.matches(emailRegex);

                if(!isValidEmail) {
                    JOptionPane.showMessageDialog(input, "Email inválido. Digite um email no formato exemplo@dominio.com", "Erro de Validação - Email", JOptionPane.ERROR_MESSAGE);
                };

                return isValidEmail;
            }
        });
        this.setVisible(true);
        fieldNome.setText(usuario.getNome());
        fieldSexo.setText(usuario.getSexo());
        fieldTelefone.setText(usuario.getNumeroCelular());
        fieldEmail.setText(usuario.getEmail());

        EditarButton.addActionListener(new ActionListener() {
           @Override
           public void actionPerformed(ActionEvent e) {
               usuario.setNome(fieldNome.getText());
               usuario.setSexo(fieldSexo.getText());
               usuario.setNumeroCelular(fieldTelefone.getText());
               usuario.setEmail(fieldEmail.getText());

               try {
                   JOptionPane.showMessageDialog(null, usuarioController.editar(usuario));
               } catch (SQLException ex) {
                   throw new RuntimeException(ex);
               }
           }
        });
    }
}
