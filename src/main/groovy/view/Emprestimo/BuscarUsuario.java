package view.Emprestimo;

import controller.UsuarioController;
import model.UsuarioModel;
import repository.UsuarioRepository;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class BuscarUsuario extends JFrame {
    private JTextField textField1;
    private JButton buttonBuscar;
    private JTable table1;
    private JButton verEmprestimosButton;
    private JPanel mainPanel;

    public BuscarUsuario() {
        this.setTitle("Selecionar usuario para empréstimo");
        this.setSize(640, 480);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

        UsuarioController usuarioController = new UsuarioController();
        UsuarioDeTabela tabela = new UsuarioDeTabela();
        table1.setModel(tabela);
        table1.setAutoCreateRowSorter(true);

        verEmprestimosButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = table1.getSelectedRow();

                if (linhaSelecionada != -1) {
                    Long idUsuario = (Long) table1.getValueAt(linhaSelecionada, 0);
                    String nome = table1.getValueAt(linhaSelecionada, 1).toString();

                    new Emprestimos(idUsuario, nome);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um usuário primeiro!");
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
