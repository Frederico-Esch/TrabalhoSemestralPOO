package Views;

import Models.*;

import javax.print.Doc;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.datatransfer.StringSelection;
import java.awt.event.ActionListener;
import java.awt.event.FocusAdapter;
import java.awt.image.AreaAveragingScaleFilter;
import java.rmi.dgc.VMID;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.stream.Collectors;

public class ExibirEEditarMidia extends JFrame {
    private boolean isEditing = false;
    private Midia source;

    private String newNome;
    private ArrayList<String> newLugares;
    private HashSet<Tags> newTags;
    private MidiaStates newState;
    private HashMap<String, String> newLinks;
    private String newReview;


    private void setupExibir(){
        getContentPane().removeAll();
        revalidate();
        repaint();

        setTitle("Exibir");
        setSize(600, 600);
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
        switch (source.getState()){
            case TO_READ -> label.setForeground(new Color(255, 0,0 ));
            case PROGRESS -> label.setForeground(new Color(250, 200, 0));
            case FINISHED -> label.setForeground(new Color(10, 175, 10));
        }
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

        if(source.getState() == MidiaStates.FINISHED){
            c.gridy++;
            add(new JLabel("Review: "), c);

            c.gridy++;
            JTextArea review = new JTextArea(source.getReview());
            review.setEditable(false);
            add(review, c);
        }

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
        setSize(800, 800);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridBagLayout());

        GridBagConstraints c = new GridBagConstraints();
        c.gridy = 0;
        c.gridx = 0;
        c.weightx = 1;
        c.insets = new Insets(10, 10, 10, 10);
        c.fill = GridBagConstraints.HORIZONTAL;

        JLabel nome = new JLabel("Nome: ");
        JTextField nomeField = new JTextField();
        nomeField.setText(source.getNome());
        nomeField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                warn();
            }
            @Override
            public void removeUpdate(DocumentEvent e) {
                warn();
            }
            @Override
            public void changedUpdate(DocumentEvent e) {
                warn();
            }

            private void warn(){
                newNome = nomeField.getText();
            }
        });

        add(nome, c);
        c.gridx++;
        c.gridwidth=2;
        add(nomeField, c);
        c.gridwidth=1;
        c.gridy++;
        c.gridx = 0;

        JLabel state = new JLabel("Estado: ");
        JRadioButton toRead = new JRadioButton(source.getStateToString(MidiaStates.TO_READ));
        JRadioButton progress = new JRadioButton(source.getStateToString(MidiaStates.PROGRESS));
        JRadioButton finished = new JRadioButton(source.getStateToString(MidiaStates.FINISHED));

        switch (source.getState()){
            case TO_READ -> toRead.setSelected(true);
            case PROGRESS -> progress.setSelected(true);
            case FINISHED -> finished.setSelected(true);
        }

        ButtonGroup grupoState = new ButtonGroup();
        grupoState.add(toRead);
        grupoState.add(progress);
        grupoState.add(finished);

        ActionListener handler = (l) ->{
            if(toRead.isSelected()) newState = MidiaStates.TO_READ;
            else if(progress.isSelected()) newState = MidiaStates.PROGRESS;
            else newState = MidiaStates.FINISHED;
        };

        toRead.addActionListener(handler);
        progress.addActionListener(handler);
        finished.addActionListener(handler);

        add(state, c);

        c.gridy++;
        add(toRead, c);
        c.gridx++;
        add(progress, c);
        c.gridx++;
        add(finished, c);
        c.gridx = 0;
        c.gridy++;

        JLabel review = new JLabel("Review: ");
        JTextArea reviewTa = new JTextArea();
        reviewTa.setText(source.getReview());
        if(source.getState() != MidiaStates.FINISHED){
            reviewTa.setEnabled(false);
        }else{
            reviewTa.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {warn();}
                @Override
                public void removeUpdate(DocumentEvent e) {warn();}
                @Override
                public void changedUpdate(DocumentEvent e) {warn();}

                private void warn(){
                    newReview = reviewTa.getText();
                }
            });
        }

        add(review, c);
        c.weightx = 0;
        c.weighty = 0;

        c.gridy++;
        c.gridwidth = 3;
        c.gridheight = 3;
        add(reviewTa, c);

        c.gridx = 0;
        c.gridwidth = 1;
        c.gridheight = 1;
        c.gridy+=3;

        c.weightx = 1;
        c.weighty = 0;

        JLabel label = new JLabel("Lugares disponíveis: ");
        add(label, c);
        c.gridy++;

        newLugares = new ArrayList<>();
        source.getLugaresDisponiveis().forEach(e->newLugares.add(e));

        JPanel panel = new JPanel();
        panel.setLayout(new GridBagLayout());
        GridBagConstraints _c = (GridBagConstraints) c.clone();

        setupLugares(_c, panel);

        c.gridwidth = 3;
        add(panel, c);
        c.gridwidth = 1;
        c.gridy++;

        JButton addLugar = new JButton("Adicionar Lugar");
        addLugar.addActionListener(l->{
            newLugares.add("");
            setupLugares(_c, panel);
        });


        add(addLugar, c);
        c.gridy++;

        JLabel tags = new JLabel("Tags: ");
        c.gridy++;

        newTags = new HashSet<>();
        source.getTags().forEach(e->newTags.add(e));
        for (var tag : Tags.values()){
            JCheckBox checkBox = new JCheckBox(tag.toString());
            if(source.getTags().contains(tag)){
                checkBox.setSelected(true);
            }
            checkBox.addActionListener(l->{
                if(checkBox.isSelected()){
                    newTags.add(tag);
                }else{
                    newTags.remove(tag);
                }
            });
            add(checkBox,c);
            c.gridx = (c.gridx+1)%3;
            if(c.gridx == 0) c.gridy++;
        }
        c.gridx = 0;
        c.gridy++;

        JButton cancelar = new JButton("Cancelar");
        cancelar.addActionListener(l-> {
            isEditing = false;
            newLinks = null;
            newLugares = null;
            newReview = null;
            newState = null;
            newNome = null;
            newTags = null;
            setupExibir();
        });
        add(cancelar, c);
        c.gridx++;

        if(source.getTipo() == MidiaType.Quadrinho){
            setupSalvarQuadrinho((Quadrinho) source, c);
        }

        setVisible(true);
    }

    private void setupLugares(GridBagConstraints c, JPanel panel){
        panel.removeAll();
        panel.revalidate();
        panel.repaint();
        c.gridx = 0;
        c.gridy = 0;
        if(source.getTipo() == MidiaType.Quadrinho){
            lugaresQuadrinho((Quadrinho)source, c, panel);
        }else{
            JTextField lugarTf;
            int index = 0;
            for(var lugar : newLugares){
                var finalIndex = index;
                lugarTf = new JTextField(lugar);
                lugarTf.getDocument().addDocumentListener(new DocumentListener() {
                    @Override
                    public void insertUpdate(DocumentEvent e) {warn();}
                    @Override
                    public void removeUpdate(DocumentEvent e) { warn();}
                    @Override
                    public void changedUpdate(DocumentEvent e) { warn();}

                    private void warn(){
                        int _index = finalIndex;
                        System.out.println(_index);
                    }
                });

                panel.add(lugarTf, c);
                c.gridy++;
                index++;
            }
        }

    }

    private void setupSalvarQuadrinho(Quadrinho source, GridBagConstraints c){
        JButton salvar = new JButton("Salvar");
        salvar.addActionListener(l -> {
            if(newNome != null) source.setNome(newNome);
            if(newTags != null) source.setTags(newTags);
            if(newState != null) source.setState(newState);
            if(source.getState() == MidiaStates.FINISHED && newReview != null){
                source.setReview(newReview);
            }

            if(newLugares != null) {
                source.setLugaresDisponiveis(newLugares);
            }

            for(var lugar : source.getLugaresDisponiveis()){
                try {
                    source.setLink(lugar, newLinks.get(lugar));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            setupExibir();
        });

        add(salvar, c);
    }

    private void lugaresQuadrinho(Quadrinho source, GridBagConstraints c, JPanel panel){
        int index = 0;
        newLinks = new HashMap<>();
        for (var lugar : newLugares){
            JTextField lugarTf = new JTextField(lugar);
            var finalIndex = index;
            lugarTf.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) {warn();}
                @Override
                public void removeUpdate(DocumentEvent e) { warn();}

                @Override
                public void changedUpdate(DocumentEvent e) { warn();}

                private void warn(){
                    var old = newLugares.get(finalIndex);
                    var novo = lugarTf.getText();
                    newLugares.set(finalIndex, novo);
                    newLinks.put(novo, newLinks.get(old));
                    newLinks.remove(old);
                }
            });
            panel.add(lugarTf, c);
            index++;
            c.gridx++;

            String linkString;
            if(finalIndex < source.getLugaresDisponiveis().size())
                linkString = source.getLink(source.getLugaresDisponiveis().get(finalIndex));
            else linkString = source.getLink("");

            if (linkString != "Sem link") newLinks.put(lugar, linkString);

            JTextField link = new JTextField(linkString);
            link.getDocument().addDocumentListener(new DocumentListener() {
                @Override
                public void insertUpdate(DocumentEvent e) { warn();}

                @Override
                public void removeUpdate(DocumentEvent e) { warn();}

                @Override
                public void changedUpdate(DocumentEvent e) {warn();}

                private void warn(){
                    newLinks.put(lugarTf.getText(), link.getText());
                }
            });
            panel.add(link, c);
            c.gridx++;

            JButton deletar = new JButton("Deletar Lugar");
            deletar.addActionListener(l->{
                newLugares.remove(finalIndex);
                newLinks.remove(lugar);
                setupLugares(c, panel);
            });
            panel.add(deletar, c);
            c.gridx=0;
            c.gridy++;
        }
    }

    public ExibirEEditarMidia(Midia midia){
        source = midia;
        setupExibir();
    }

    public ExibirEEditarMidia(Midia midia, boolean exibir){
        source = midia;
        if(exibir) setupExibir();
        else setupEditar();
    }
}
