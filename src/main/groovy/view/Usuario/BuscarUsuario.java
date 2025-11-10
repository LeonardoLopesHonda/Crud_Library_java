package view.Usuario;

import controller.UsuarioController;
import model.UsuarioModel;
import repository.UsuarioRepository;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class BuscarUsuario extends JFrame {
    private JTextField textField1;
    private JButton buttonBuscar;
    private JButton removerButton;
    private JButton editarButton;
    private JTable table1;
    private JPanel mainPanel;

    public BuscarUsuario() {
        this.setTitle("Buscar Usuario");
        this.setSize(640, 480);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

        UsuarioController usuarioController = new UsuarioController();
        UsuarioDeTabela tabela = new UsuarioDeTabela();
        table1.setModel(tabela);
        table1.setAutoCreateRowSorter(true);

        removerButton.addActionListener(new ActionListener() {
           @Override
            public void actionPerformed(ActionEvent e) {
               int linhaSelecionada = table1.getSelectedRow();

               if(linhaSelecionada != -1) {
                   Long idPessoaSelecionada = Long.parseLong(table1.getValueAt(linhaSelecionada, 0).toString());

                   try {
                       JOptionPane.showMessageDialog(null, usuarioController.excluir(idPessoaSelecionada));
                       UsuarioDeTabela usuarioDeTabela = new UsuarioDeTabela();
                       table1.setModel(usuarioDeTabela);
                   } catch (SQLException ex) {
                       throw new RuntimeException(ex);
                   }
               } else {
                   JOptionPane.showMessageDialog(null, "Selecione o registro que deseja remover");
               }
           }
        });
        editarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = table1.getSelectedRow();

                if(linhaSelecionada != -1) {
                    UsuarioModel usuarioModel = new UsuarioModel();
                    usuarioModel.setIdUsuario(Long.parseLong(table1.getValueAt(linhaSelecionada, 0).toString()));
                    usuarioModel.setNome(table1.getValueAt(linhaSelecionada, 1).toString());
                    usuarioModel.setSexo(table1.getValueAt(linhaSelecionada, 2).toString());
                    usuarioModel.setNumeroCelular(table1.getValueAt(linhaSelecionada, 3).toString());
                    usuarioModel.setEmail(table1.getValueAt(linhaSelecionada, 4).toString());
                    new EditarUsuario(usuarioModel);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione o registro que deseja remover");
                }
            }
        });
        buttonBuscar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                UsuarioDeTabela usuarioDeTabela = new UsuarioDeTabela(textField1.getText());
                table1.setModel(usuarioDeTabela);
            }
        });
    }

    private static class UsuarioDeTabela extends AbstractTableModel {
        private UsuarioRepository usuarioRepository = new UsuarioRepository();
        private final String[] COLUMNS = new String[] {"Id", "Nome", "Sexo", "Nº de Celular", "Email"};
        private List<UsuarioModel> usuarios;
        public UsuarioDeTabela() {
            usuarios = usuarioRepository.buscarTodos();
        }

        public UsuarioDeTabela(String nomeBusca) {
            usuarios = usuarioRepository.buscarPorNome(nomeBusca);
        }

        @Override
        public int getRowCount() {
            return usuarios.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> usuarios.get(rowIndex).getIdUsuario();
                case 1 -> usuarios.get(rowIndex).getNome();
                case 2 -> usuarios.get(rowIndex).getSexo();
                case 3 -> usuarios.get(rowIndex).getNumeroCelular();
                case 4 -> usuarios.get(rowIndex).getEmail();
                default -> "-";
            };
        }

        @Override
        public String getColumnName(int columnIndex) {
            return COLUMNS[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if(getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }
    }
}
