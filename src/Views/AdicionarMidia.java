package Views;

import Models.Midia;
import Models.Quadrinho;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

public class AdicionarMidia extends JFrame {

    public AdicionarMidia(ArrayList<Midia> data){
        setSize(600, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        GridBagLayout layout = new GridBagLayout();
        setLayout(layout);

        GridBagConstraints c = new GridBagConstraints();
        c.weightx = 1;
        c.insets = new Insets(10, 5, 10, 5);
        c.gridx = 0;
        c.gridy = 0;

        JButton quadrinho = new JButton("Quadrinho");
        JButton filme = new JButton("Filme");
        JButton livro = new JButton("Livro");
        JButton serie = new JButton("Série");

        filme.setEnabled(false);
        livro.setEnabled(false);
        serie.setEnabled(false);

        quadrinho.addActionListener(l -> {
            //data.add(new Quadrinho("", new ArrayList<>()));
            var novoQuadrinho = new Quadrinho("", new ArrayList<>());
            new ExibirEEditarMidia(novoQuadrinho, () -> {
                data.add(novoQuadrinho);
                return null;
            });
            dispose();
        });

        add(filme, c);
        c.gridx++;

        add(livro, c);
        c.gridx++;

        add(serie, c);
        c.gridx++;

        add(quadrinho, c);
        c.gridx++;
        setVisible(true);
    }
}
