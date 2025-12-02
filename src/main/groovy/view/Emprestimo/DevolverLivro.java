package view.Emprestimo;

import controller.EmprestimoController;
import controller.LivroController;
import controller.UsuarioController;
import model.EmprestimoModel;
import repository.EmprestimoRepository;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class DevolverLivro extends JFrame{
    private JTable table1;
    private JButton devolverButton;
    private JButton voltarButton;
    private JPanel mainPanel;

    private Long idUsuario;
    private EmprestimoController emprestimoController;

    public DevolverLivro(Long idUsuario) {
        this.idUsuario = idUsuario;
        this.emprestimoController = new EmprestimoController();

        this.setTitle("Devolver Empréstimos Ativos");
        this.setSize(640, 480);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

        EmprestimoDeTabela tabela = new EmprestimoDeTabela(idUsuario);
        table1.setModel(tabela);
        table1.setAutoCreateRowSorter(true);

        devolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = table1.getSelectedRow();
                if (linhaSelecionada != -1) {
                    Long idEmprestimo = Long.parseLong(table1.getValueAt(linhaSelecionada, 0).toString());
                    String resultado = emprestimoController.devolver(idEmprestimo);
                    JOptionPane.showMessageDialog(null, resultado);

                    EmprestimoDeTabela novaTabela = new EmprestimoDeTabela(idUsuario);
                    table1.setModel(novaTabela);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um empréstimo para devolver.");
                }
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    UsuarioController usuarioController = new UsuarioController();
                    String nomeUsuario = usuarioController.buscarPorId(idUsuario).getNome();
                    new Emprestimos(idUsuario, nomeUsuario);
                } catch (SQLException ex) {
                    ex.printStackTrace();
                } finally {
                    dispose();
                }
            }
        });
    }

    private static class EmprestimoDeTabela extends AbstractTableModel {
        private EmprestimoRepository emprestimoRepository = new EmprestimoRepository();
        private final String[] COLUMNS = new String[] {
                "Id", "Livro", "Data Empréstimo", "Devolução Prevista", "Expirado"
        };
        private List<EmprestimoModel> emprestimos;

        public EmprestimoDeTabela(Long idUsuario) {
            emprestimos = emprestimoRepository.buscarEmprestimosAtivosPorUsuario(idUsuario);
        }

        @Override
        public int getRowCount() {
            return emprestimos.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            EmprestimoModel emprestimo = emprestimos.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> emprestimo.getIdEmprestimo();
                case 1 -> emprestimo.getIdLivro().getTitulo();
                case 2 -> emprestimo.getData_emprestimo();
                case 3 -> emprestimo.getData_devolucao_prevista();
                case 4 -> emprestimo.isExpirado() ? "Sim" : "Não";
                default -> "-";
            };
        }

        @Override
        public String getColumnName(int columnIndex) {
            return COLUMNS[columnIndex];
        }
    }
}
