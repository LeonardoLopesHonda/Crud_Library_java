package view.Emprestimo;

import model.EmprestimoModel;
import repository.EmprestimoRepository;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class Emprestimos extends JFrame {
    private JPanel mainPanel;
    private JTable tableEmprestimos;
    private JButton voltarButton;
    private JButton devolverButton;
    private JButton emprestarButton;

    private Long idUsuario;
    private String nomeUsuario;

    public Emprestimos(Long idUsuario, String nomeUsuario) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;

        this.setTitle("Emprestimos Ativos de " + nomeUsuario);
        this.setSize(640, 480);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        this.setVisible(true);

        EmprestimosTabela tabela = new EmprestimosTabela(idUsuario);
        tableEmprestimos.setModel(tabela);
        tableEmprestimos.setAutoCreateRowSorter(true);

        devolverButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DevolverLivro(idUsuario);
                dispose();
            }
        });

        emprestarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new EmprestarLivro(idUsuario, nomeUsuario);
                dispose();
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new BuscarUsuario();
            }
        });
    }

    private static class EmprestimosTabela extends AbstractTableModel {
        private final String[] COLUMNS = {"ID", "Livro", "Data Empréstimo", "Data Devolução Prevista"};
        private List<EmprestimoModel> emprestimos;
        private EmprestimoRepository emprestimoRepository = new EmprestimoRepository();

        public EmprestimosTabela(Long idUsuario) {
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
            EmprestimoModel e = emprestimos.get(rowIndex);
            return switch (columnIndex) {
                case 0 -> e.getIdEmprestimo();
                case 1 -> e.getIdLivro().getTitulo();
                case 2 -> e.getData_emprestimo();
                case 3 -> e.getData_devolucao_prevista();
                default -> "-";
            };
        }

        @Override
        public String getColumnName(int columnIndex) {
            return COLUMNS[columnIndex];
        }

        @Override
        public Class<?> getColumnClass(int columnIndex) {
            if (getRowCount() > 0 && getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }
    }
}

