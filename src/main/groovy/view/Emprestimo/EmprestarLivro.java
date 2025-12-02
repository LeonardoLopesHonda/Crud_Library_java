package view.Emprestimo;

import controller.EmprestimoController;
import controller.LivroController;
import model.LivroModel;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class EmprestarLivro extends JFrame {
    private JTable table1;
    private JButton voltarButton;
    private JButton emprestarButton;
    private JPanel mainPanel;

    private Long idUsuario;
    private String nomeUsuario;

    public EmprestarLivro(Long idUsuario, String nomeUsuario) {
        this.idUsuario = idUsuario;
        this.nomeUsuario = nomeUsuario;

        this.setTitle("Selecionar Livro - " + nomeUsuario);
        this.setSize(640, 480);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

        LivroDeTabela tabela = new LivroDeTabela();
        table1.setModel(tabela);
        table1.setAutoCreateRowSorter(true);

        emprestarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = table1.getSelectedRow();

                if (linhaSelecionada != -1) {
                    Long idLivroSelecionado = Long.parseLong(table1.getValueAt(linhaSelecionada, 0).toString());
                    EmprestimoController emprestimoController = new EmprestimoController();

                    String resultado = emprestimoController.emprestar(idUsuario, idLivroSelecionado);
                    JOptionPane.showMessageDialog(null, resultado);

                    table1.setModel(new LivroDeTabela());
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um livro para emprestar");
                }
            }
        });

        voltarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
                new Emprestimos(idUsuario, nomeUsuario);
            }
        });
    }

    private static class LivroDeTabela extends AbstractTableModel {
        private final String[] COLUMNS = {"Id", "Titulo", "Tema", "Autor", "ISBN", "Data de Publicação", "Quantidade"};
        private List<LivroModel> livros;
        private LivroController livroController = new LivroController();

        public LivroDeTabela() {
            try {
                livros = livroController.buscarDisponiveis();
            } catch (Exception e) {
                e.printStackTrace();
                livros = new ArrayList<>();
            }
        }

        @Override
        public int getRowCount() {
            return livros.size();
        }

        @Override
        public int getColumnCount() {
            return COLUMNS.length;
        }

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            return switch (columnIndex) {
                case 0 -> livros.get(rowIndex).getIdLivro();
                case 1 -> livros.get(rowIndex).getTitulo();
                case 2 -> livros.get(rowIndex).getTema();
                case 3 -> livros.get(rowIndex).getAutor();
                case 4 -> livros.get(rowIndex).getIsbn();
                case 5 -> livros.get(rowIndex).getData_publicacao();
                case 6 -> livros.get(rowIndex).getQuantidade_disponivel();
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
