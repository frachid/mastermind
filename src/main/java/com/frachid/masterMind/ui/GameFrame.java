package com.frachid.masterMind.ui;

import java.awt.*;
import javax.swing.*;
import com.frachid.masterMind.business.*;

import static com.frachid.masterMind.business.MasterMindUtils.*;

public class GameFrame extends JFrame {
    private static final String EMPTY_STRING = "";

    private int numeroEssaie = 1;
    private int numeroEntropy = 1;
    private boolean partieFinie = false;

    private String secret;
    private static final int NOMBRE_BOUTON = 10;
    private String[] stringBouton = {"0","1","2","3","4","5","6","7","8","9"};

    private JMenuBar menuBar = new JMenuBar();
    private JMenu file = new JMenu("File");
    private JMenu secretLengthMenu = new JMenu("Secret Length");
    private JMenu informationTheoryMenu = new JMenu("Information Theory");

    private JMenuItem newGame = new JMenuItem("New Game");
    private JMenuItem exit = new JMenuItem("Exit");

    private JCheckBoxMenuItem informationTheoryBox = new JCheckBoxMenuItem("Activate");
    private boolean informationTheory;

    private JRadioButtonMenuItem length3 = new JRadioButtonMenuItem("3");
    private JRadioButtonMenuItem length4 = new JRadioButtonMenuItem("4");
    private JRadioButtonMenuItem length5 = new JRadioButtonMenuItem("5");

    private JButton[] boutons = new JButton[NOMBRE_BOUTON];
    private JButton submit = new JButton("Submit");
    private JButton clear = new JButton("Clear");
    private JButton info = new JButton("Info");
    private JButton best = new JButton("Best");

    private JLabel[] essaies = new JLabel[MAX_ESSAIE];
    private JLabel[] entropies = new JLabel[MAX_ENTROPY];

    Font police = new Font("Arial", Font.BOLD, 20);
    private static final int FENETRE_WIDTH = 700;
    private static final int FENETRE_HEIGHT = 600;
    private JPanel basePanel = new JPanel();
    private JPanel droite = new JPanel();
    private JLabel titreDroite = new JLabel();
    private JPanel tableauDEssaies = new JPanel();
    private JScrollPane scrollEssaie = new JScrollPane(tableauDEssaies);

    private JPanel gauche = new JPanel();
    private JLabel titreGauche = new JLabel();
    private JPanel tableauDInfo = new JPanel();
    private JScrollPane scrollInfo = new JScrollPane(tableauDInfo);
    private JPanel infoPanel = new JPanel();
    private JPanel control = new JPanel();
    private JLabel ecran = new JLabel();
    private JPanel boutonPanel = new JPanel();
    private JPanel numeroPanel = new JPanel();
    private JPanel operationPanel = new JPanel();
    private GameSituation gameSituation;


    public GameFrame(){
        secret = MasterMindUtils.buildSecret();
        gameSituation = new GameSituation(secret);
        this.setTitle("Master Mind");
        this.setSize(FENETRE_WIDTH, FENETRE_HEIGHT);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        initMenu();
        initComposant();
        this.setContentPane(basePanel);

        this.setVisible(true);
    }

    private void initMenu() {

        this.menuBar.add(file);
        this.file.add(newGame);
        newGame.addActionListener(e -> launchNewGame());
        ButtonGroup bg = new ButtonGroup();
        length5.setSelected(true);
        bg.add(length3);
        bg.add(length4);
        bg.add(length5);
        secretLengthMenu.add(length3);
        secretLengthMenu.add(length4);
        secretLengthMenu.add(length5);
        this.file.add(secretLengthMenu);
        //        informationTheoryBox.addActionListener();
        informationTheoryMenu.add(informationTheoryBox);
        informationTheoryBox.setState(true);
        informationTheory = informationTheoryBox.isSelected();
        informationTheoryBox.addActionListener(e -> informationTheoryActivation());
        this.file.add(informationTheoryMenu);
        this.file.add(exit);
        this.setJMenuBar(menuBar);
    }

    private void informationTheoryActivation() {
        informationTheory = informationTheoryBox.isSelected();
        ecriretitreGauche();
        cleanPanelArray(entropies);
        best.setEnabled(informationTheory);
        info.setEnabled(informationTheory && ecran.getText().length() == MasterMindUtils.SECRET_LENGHT);

    }

    private void initComposant() {
        gauche.setPreferredSize(new Dimension(300,600));
        droite.setPreferredSize(new Dimension(400, 600));
        gauche.setBackground(Color.GREEN);
        droite.setBackground(Color.ORANGE);
        basePanel.setLayout(new BorderLayout());
        basePanel.add(gauche, BorderLayout.WEST);
        basePanel.add(droite, BorderLayout.CENTER);
        initGauche();
        initDroite();
    }

    private void initDroite() {

        droite.setLayout(new BorderLayout());
        titreDroite.setFont(police);
        titreDroite.setHorizontalAlignment(JLabel.CENTER);
        titreDroite.setPreferredSize(new Dimension(300, 30));
        ecriretitreDroite();
        droite.add(titreDroite, BorderLayout.NORTH);
        tableauDEssaies.setBackground(Color.RED);
        tableauDEssaies.setLayout(new GridLayout(MAX_ESSAIE, 1));
        for (int i = 0; i < MAX_ESSAIE; i++) {
            essaies[i] = new JLabel();
            essaies[i].setFont(police);
            essaies[i].setHorizontalAlignment(JLabel.CENTER);
            essaies[i].setPreferredSize(new Dimension(300, 30));
            tableauDEssaies.add(essaies[i]);
        }
        droite.add(scrollEssaie, BorderLayout.CENTER);
    }

    private void ecriretitreDroite() {
        int essaiesRestants = (MAX_ESSAIE - numeroEssaie + 1);
        titreDroite.setText(essaiesRestants + "Essais restant");
    }

    private void initGauche() {
        gauche.setLayout(new BorderLayout());
        infoPanel.setBackground(Color.green);
        infoPanel.setPreferredSize(new Dimension(300, 370));
        gauche.add(infoPanel, BorderLayout.NORTH);
        control.setBackground(Color.GRAY);
        control.setPreferredSize(new Dimension(300, 230));
        gauche.add(control, BorderLayout.CENTER);
        initControl();
        initInfoPanel();
    }

    private void initInfoPanel() {
        infoPanel.setLayout(new BorderLayout());
        titreGauche.setFont(police);
        titreGauche.setHorizontalAlignment(JLabel.CENTER);
        titreGauche.setPreferredSize(new Dimension(300, 30));
        ecriretitreGauche();
        infoPanel.add(titreGauche, BorderLayout.NORTH);
        tableauDInfo.setBackground(Color.BLUE);
        tableauDInfo.setLayout(new GridLayout(MAX_ENTROPY, 1));
        for (int i = 0; i < MAX_ENTROPY; i++) {
            entropies[i] = new JLabel();
            entropies[i].setFont(police);
            entropies[i].setHorizontalAlignment(JLabel.CENTER);
            entropies[i].setPreferredSize(new Dimension(300, 30));
            tableauDInfo.add(entropies[i]);
        }
        infoPanel.add(scrollInfo, BorderLayout.CENTER);
    }

    private void ecriretitreGauche() {
        if (informationTheory) {
            Double neededInfo = gameSituation.getNeededInformation();
            titreGauche.setText("Information Needed = " + String.format("%.2f", neededInfo));
        } else {
            titreGauche.setText("");
        }
    }

    private void initControl() {
        control.setLayout(new BorderLayout());
        ecran.setText("");
        ecran.setFont(police);
        ecran.setHorizontalAlignment(JLabel.RIGHT);
        ecran.setPreferredSize(new Dimension(300,30));
        control.add(ecran,BorderLayout.NORTH);
        boutonPanel.setBackground(Color.YELLOW);
        boutonPanel.setPreferredSize(new Dimension(300, 200));
        control.add(boutonPanel, BorderLayout.CENTER);
        initBoutonPanel();
    }

    private void initBoutonPanel() {
        boutonPanel.setLayout(new BorderLayout());
        initOperationPanel();
        boutonPanel.add(operationPanel,BorderLayout.SOUTH);
        numeroPanel.setBackground(Color.MAGENTA);
        boutonPanel.add(numeroPanel,BorderLayout.CENTER);
        numeroPanel.setLayout(new GridLayout(2,5));
        for(int i = 0;i<NOMBRE_BOUTON;i++){
            final String caracterLieAuBouton = stringBouton[i];
            boutons[i] = new JButton(caracterLieAuBouton);
            boutons[i].addActionListener(e -> addCharToResponse(caracterLieAuBouton));
            numeroPanel.add(boutons[i]);
        }
        submit.setEnabled(false);
        info.setEnabled(false);
    }

    private void initOperationPanel() {
        operationPanel = new JPanel();
        operationPanel.setLayout(new GridLayout(2, 2));
        operationPanel.add(submit);
        operationPanel.add(clear);
        operationPanel.add(info);
        operationPanel.add(best);
        submit.addActionListener(e -> onSubmit());
        clear.addActionListener(e -> onClear());
        info.addActionListener(e -> onInfo());
        best.addActionListener(e -> onBest());

    }

    private void onInfo() {
        String guess = ecran.getText();
        Essaie essaie = new Essaie(gameSituation,guess);
        double entropy = essaie.getEntropy();
        entropies[numeroEntropy - 1].setText("Entropy for " + guess + " : " + String.format("%.2f", entropy));
        numeroEntropy++;
        //        onClear();
    }

    private void onBest() {

        String best = InformationCalculator.theGuessThatGivesTheBiggestEntropy(gameSituation.getPossibleSolutions());
        Essaie essaie = new Essaie(gameSituation, best);
        double entropy = essaie.getEntropy();
        boolean isGuessAPossibleSolution = gameSituation.getPossibleSolutions().contains(best);
        if (isGuessAPossibleSolution) {
            entropies[numeroEntropy - 1].setText("Best guess is " + best + " / " + String.format("%.2f", entropy));
        } else {
            entropies[numeroEntropy - 1].setText("**Best guess is " + best + " / " + String.format("%.2f", entropy) + "**");
        }
        entropies[numeroEntropy - 1].setText("Best guess is " + best + " / " + String.format("%.2f", entropy));
        numeroEntropy++;
    }

    private void onSubmit() {
        String guess = ecran.getText();
        Essaie essaie = new Essaie(gameSituation,guess);
        Response response = essaie.getResponse();
        Double entropy = essaie.getEntropy();
        essaie.processResponse();
        String resultat = response.toString();
        Double info = essaie.getGeneratedInfo();
        String infoEntropyString = "( " + String.format("%.2f", info) + " ; " + String.format("%.2f", entropy) + " )";
        String answerString = "Essai " + numeroEssaie + ": " + ecran.getText() + " : " + response;

        essaies[numeroEssaie - 1].setText(answerString + infoEntropyString);
        numeroEssaie++;
        if (informationTheory) {
            updateInfoPanel();
        }

        if(!resultat.equalsIgnoreCase(TOUT_BON)){
            if(numeroEssaie<=MAX_ESSAIE){
                ecriretitreDroite();
                onClear();
            }else{
                finDePartie(false);
            }

        }else{
            finDePartie(true);
        }
    }

    private void updateInfoPanel() {
        ecriretitreGauche();
        numeroEntropy = 1;
        cleanPanelArray(entropies);

    }

    private void finDePartie(boolean victoire) {
        String resultaFinal = victoire ? GAGNE : PERDU;
        titreDroite.setText( "Vous avez "+resultaFinal+" ("+secret+")");
        submit.setEnabled(false);
        info.setEnabled(false);
        for(int i=0;i<NOMBRE_BOUTON;i++){
            boutons[i].setEnabled(false);
        }
        partieFinie = true;
        clear.setText("New Game");

    }

    private void onClear() {
        if(partieFinie){
            launchNewGame();
        }
        ecran.setText("");

        info.setEnabled(false);
        submit.setEnabled(false);
        for(int i=0;i<NOMBRE_BOUTON;i++){
            boutons[i].setEnabled(true);
        }
    }

    private void launchNewGame() {
        secret = MasterMindUtils.buildSecret();
        gameSituation = new GameSituation(secret);
        numeroEssaie=1;
        cleanPanelArray(essaies);
        ecriretitreDroite();

        partieFinie = false;
        clear.setText("Clear");
        updateInfoPanel();
    }

    private void addCharToResponse(String caracterLieAuBouton) {

        ecran.setText(ecran.getText()+caracterLieAuBouton);
        if(ecran.getText().length()==SECRET_LENGHT){
            submit.setEnabled(true);
            if (numeroEntropy <= MAX_ENTROPY) {
                info.setEnabled(informationTheory);
            }
            for(int i=0;i<NOMBRE_BOUTON;i++){
                boutons[i].setEnabled(false);
            }
        }
    }

    private void cleanPanelArray(JLabel[] panelArray) {
        for (JLabel label : panelArray) {
            label.setText(EMPTY_STRING);
        }
    }
}
