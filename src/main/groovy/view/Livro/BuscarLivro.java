package view.Livro;

import controller.LivroController;
import model.LivroModel;
import repository.LivroRepository;

import javax.swing.*;
import javax.swing.table.AbstractTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class BuscarLivro extends JFrame {
    private JTextField textField1;
    private JButton buscarButton;
    private JTable table1;
    private JButton editarButton;
    private JButton removerButton;
    private JPanel mainPanel;

    public BuscarLivro() {
        this.setTitle("Buscar Livro");
        this.setSize(640, 480);
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.setVisible(true);

        LivroController livroController = new LivroController();
        BuscarLivro.LivroDeTabela tabela = new LivroDeTabela();
        table1.setModel(tabela);
        table1.setAutoCreateRowSorter(true);

        removerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int linhaSelecionada = table1.getSelectedRow();

                if(linhaSelecionada != -1) {
                    Long idPessoaSelecionada = Long.parseLong(table1.getValueAt(linhaSelecionada, 0).toString());

                    try {
                        JOptionPane.showMessageDialog(null, livroController.excluir(idPessoaSelecionada));
                        BuscarLivro.LivroDeTabela livroDeTabela = new LivroDeTabela();
                        table1.setModel(livroDeTabela);
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
                    LivroModel livroModel = new LivroModel();
                    livroModel.setIdLivro(Long.parseLong(table1.getValueAt(linhaSelecionada, 0).toString()));
                    livroModel.setTitulo(table1.getValueAt(linhaSelecionada, 1).toString());
                    livroModel.setTema(table1.getValueAt(linhaSelecionada, 2).toString());
                    livroModel.setAutor(table1.getValueAt(linhaSelecionada, 3).toString());
                    livroModel.setIsbn(table1.getValueAt(linhaSelecionada, 4).toString());
                    livroModel.setData_publicacao(table1.getValueAt(linhaSelecionada, 5).toString());
                    livroModel.setQuantidade_disponivel(Integer.parseInt(table1.getValueAt(linhaSelecionada, 6).toString()));
                    new EditarLivro(livroModel);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione o registro que deseja editar");
                }
            }
        });
        buscarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                BuscarLivro.LivroDeTabela livroDeTabela = new LivroDeTabela(textField1.getText());
                table1.setModel(livroDeTabela);
            }
        });
    }

    private static class LivroDeTabela extends AbstractTableModel {
        private LivroRepository livroRepository = new LivroRepository();
        private final String[] COLUMNS = new String[] {"Id", "Titulo", "Tema", "Autor", "ISBN", "Data de Publicação", "Quantidade"};
        private List<LivroModel> livros;

        public LivroDeTabela() {
            livros = livroRepository.buscarTodos();
        }

        public LivroDeTabela(String titulo) {
            livros = livroRepository.buscarPorTitulo(titulo.trim());
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
            if(getValueAt(0, columnIndex) != null) {
                return getValueAt(0, columnIndex).getClass();
            } else {
                return Object.class;
            }
        }
    }

}
