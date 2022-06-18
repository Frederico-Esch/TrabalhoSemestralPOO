package Views;

import Models.Midia;
import Models.Quadrinho;
import Models.Tags;
import Utils.ResourceManager;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableModel;
import java.awt.*;
import java.util.*;
import java.util.List;

public class MenuHome extends JFrame {

    private ArrayList<Midia> data;

    MenuHome(){
        data = new ArrayList<>();

        setTitle("Home");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(500, 300);
        setLocationRelativeTo(null);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);

        /* FIXME
        DefaultTableModel tableModel = new DefaultTableModel();
        JTable table = new JTable(tableModel);

        Arrays.stream(tableColumns).forEach(n -> tableModel.addColumn(n));

        JScrollPane scroll = new JScrollPane(table);
        scroll.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(scroll);
        */

        JButton verTabela = new JButton("Ver Tabela");
        JButton verArvore = new JButton("Ver árvore de conteúdo");
        JButton adicionarMidia = new JButton("Adicionar Mídia");
        JButton salvarDados = new JButton("Salvar dados");
        JButton carregarDados = new JButton("Carregar dados");

        verTabela.addActionListener(l->{
            new TabelaDeMidia(data);
        });

        adicionarMidia.addActionListener(l->{
            new AdicionarMidia(data);
        });

        salvarDados.addActionListener(l->{
            try{
                ResourceManager.save(data);
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        });

        carregarDados.addActionListener(l->{
            try{
                data = ResourceManager.load();
            }catch (Exception e){
                System.out.println(e.getMessage());
            }
        });

        add(verTabela, c);
        c.gridy++;
        add(verArvore, c);
        c.gridy++;
        add(adicionarMidia, c);
        c.gridy++;
        add(salvarDados,c);
        c.gridy++;
        add(carregarDados, c);

        setVisible(true);
    }

    public static void main(String[] args) throws Exception{
        new MenuHome();
    }
}
