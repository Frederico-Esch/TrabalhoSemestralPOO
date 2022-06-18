package Views;

import Models.Midia;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.LineBorder;
import javax.swing.border.MatteBorder;
import java.awt.*;
import java.util.ArrayList;

public class TabelaDeMidia extends JFrame {

    private final String[] tableColumns = { "Nome", "Tipo de Mídia", "Status", "Tags", "Visualizar" };

    public TabelaDeMidia(ArrayList<Midia> data){
        setTitle("Tabela");
        setSize(700, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel content = new JPanel();
        content.setLayout(new GridLayout(0, 5));
        content.setBorder(new EmptyBorder(10, 10, 10, 10));
        for (String tableColumn : tableColumns) {
            JLabel label = new JLabel(tableColumn, JLabel.CENTER);
            label.setBorder(new LineBorder(new Color(0,0,0)));
            content.add(label);
        }
        for (Midia midia : data) {
            JLabel label = new JLabel(midia.getNome(), JLabel.CENTER);
            label.setBorder(new MatteBorder(0,1,1,0,new Color(0,0,0)));
            content.add(label);

            label = new JLabel(midia.getTipo().toString(), JLabel.CENTER);
            label.setBorder(new MatteBorder(0,1,1,0,new Color(0,0,0)));
            content.add(label);

            var cor = switch (midia.getState()){
                case TO_READ -> Color.red;
                case PROGRESS -> new Color(250, 200, 0);
                case FINISHED -> new Color(10, 175, 10);
            };
            label = new JLabel(midia.getStateToString(), JLabel.CENTER);
            label.setForeground(cor);
            label.setBorder(new MatteBorder(0,1,1,0,new Color(0,0,0)));
            content.add(label);

            label = new JLabel(midia.getTags().toString(), JLabel.CENTER);
            label.setBorder(new MatteBorder(0,1,1,0,new Color(0,0,0)));
            content.add(label);

            var panel = new JPanel();
            panel.setLayout(new GridBagLayout());
            JButton visualizar = new JButton("Visualizar");
            visualizar.addActionListener(l->new ExibirEEditarMidia(midia));
            panel.add(visualizar);
            panel.setBorder(new MatteBorder(0, 1, 1, 1, new Color(0,0,0)));
            content.add(panel);
        }


        JScrollPane scroll = new JScrollPane(content);
        scroll.setBorder(new EmptyBorder(10, 10, 10, 10));
        add(scroll);

        setVisible(true);
    }
}
