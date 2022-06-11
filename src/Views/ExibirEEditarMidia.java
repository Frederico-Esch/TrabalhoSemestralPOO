package Views;

import Models.Midia;
import Models.Quadrinho;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.rmi.dgc.VMID;
import java.util.stream.Collectors;

public class ExibirEEditarMidia extends JFrame {
    private boolean isEditing = false;
    private Midia source;

    private void setupExibir(){
        getContentPane().removeAll();
        revalidate();
        repaint();

        setTitle("Exibir");
        setSize(500, 500);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridx = 0;
        c.gridy = 0;
        c.weightx = 1;
        c.gridwidth = 2;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.insets = new Insets(10, 10, 10, 10);

        JLabel label = new JLabel("Nome: " + source.getNome(), JLabel.CENTER);
        add(label, c);
        c.gridy++;

        c.gridwidth=1;
        c.weightx = 0;
        c.fill = GridBagConstraints.NONE;
        label = new JLabel("Status: ");
        add(label, c);
        c.gridx++;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        label = new JLabel(source.getStateToString());
        label.setForeground(new Color(255, 0,0 ));
        add(label, c);

        c.gridx = 0;
        c.gridwidth = 2;

        label = new JLabel("Tags: " + source.getTags().toString());
        c.gridy++;
        add(label, c);

        switch (source.getTipo()){
            case Quadrinho:
                setupQuadrinhoExibir(c);
                break;

            default:
                //NOOP
                break;
        }

        setVisible(true);
    }

    private void setupQuadrinhoExibir(GridBagConstraints c){
        Quadrinho quadrinho = (Quadrinho) source;

        JLabel label = new JLabel("Lugares para Ler e Links: ");
        c.gridy++;
        add(label, c);

        var zip = quadrinho.getLugaresDisponiveis().stream().map(l-> {
            String result;
            try {
                result = quadrinho.getLink(l);
            }catch (Exception e){
                result = "Sem link disponível";
            }
            return l + ": " + result;
        }).collect(Collectors.toList());

        JList lista = new JList(zip.toArray());
        lista.addListSelectionListener(l->{
            var link = (String)lista.getSelectedValue();
            link = link.split(": ")[1];
            StringSelection linkParaOClipBoard = new StringSelection(link);
            Toolkit.getDefaultToolkit().getSystemClipboard().setContents(linkParaOClipBoard, null);
            JOptionPane.showMessageDialog(null, "Link Copiado para o clipboard!", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
        });
        c.gridy++;
        add(lista, c);

        JButton editar = new JButton("Editar");
        editar.addActionListener(l->{
            isEditing = true;
            setupEditar();
        });
        c.gridy++;
        add(editar, c);
    }

    private void setupEditar(){
        getContentPane().removeAll();
        revalidate();
        repaint();

        setTitle("Editar");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        c.weightx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;

        JButton exibir = new JButton("Exibir");
        exibir.addActionListener(l-> {
            isEditing = false;
            setupExibir();
        });

        add(exibir, c);

        setVisible(true);
    }

    private void salvar(){
        //NOOP
    }

    public ExibirEEditarMidia(Midia midia){
        source = midia;
        setupExibir();
    }
}
