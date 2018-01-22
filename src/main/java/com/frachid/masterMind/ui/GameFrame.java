package com.frachid.masterMind.ui;

import java.awt.*;
import javax.swing.*;
import com.frachid.masterMind.business.Essaie;
import com.frachid.masterMind.business.GameSituation;
import com.frachid.masterMind.business.MasterMindUtils;
import com.frachid.masterMind.business.Response;

import static com.frachid.masterMind.business.MasterMindUtils.*;

public class GameFrame extends JFrame {
    private int numeroEssaie = 1;
    private boolean partieFinie = false;
    private boolean informationTheorie = false;

    private String secret;
    private static final int NOMBRE_BOUTON = 10;
    private String[] stringBouton = {"0","1","2","3","4","5","6","7","8","9"};
    private JButton[] boutons = new JButton[NOMBRE_BOUTON];
    private JButton submit = new JButton("Submit");
    private JButton clear = new JButton("Clear");
    private JButton info = new JButton("Info");

    private JLabel[] essaies = new JLabel[MAX_ESSAIE];

    Font police = new Font("Arial", Font.BOLD, 20);
    private static final int FENETRE_WIDTH = 600;
    private static final int FENETRE_HEIGHT = 600;
    private JPanel basePanel = new JPanel();
    private JPanel droite = new JPanel();
    private JLabel titreDroite = new JLabel();
    private JPanel tableauDEssaies = new JPanel();
    private JScrollPane scroll = new JScrollPane(tableauDEssaies);
    private JPanel gauche = new JPanel();
    private JPanel haut = new JPanel();
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
        initComposant();
        this.setContentPane(basePanel);

        this.setVisible(true);
    }

    private void initComposant() {
        gauche.setPreferredSize(new Dimension(300,600));
        droite.setPreferredSize(new Dimension(300,600));
        gauche.setBackground(Color.GREEN);
        droite.setBackground(Color.ORANGE);
        basePanel.setLayout(new BorderLayout());
        basePanel.add(gauche, BorderLayout.WEST);
        basePanel.add(droite, BorderLayout.CENTER);
        initGauche();
        initDroite();
    }

    private void initGauche() {
        gauche.setLayout(new BorderLayout());
        haut.setBackground(Color.BLUE);
        haut.setPreferredSize(new Dimension(300,400));
        gauche.add(haut, BorderLayout.NORTH);
        control.setBackground(Color.GRAY);
        control.setPreferredSize(new Dimension(300,200));
        gauche.add(control, BorderLayout.CENTER);
        initControl();
    }

    private void initControl() {

        control.setLayout(new BorderLayout());
        ecran.setText("");
        ecran.setFont(police);
        ecran.setHorizontalAlignment(JLabel.RIGHT);
        ecran.setPreferredSize(new Dimension(300,30));
        control.add(ecran,BorderLayout.NORTH);
        boutonPanel.setBackground(Color.YELLOW);
        boutonPanel.setPreferredSize(new Dimension(300,150));
        control.add(boutonPanel, BorderLayout.CENTER);
        initBoutonPanel();
    }

    private void initBoutonPanel() {
        boutonPanel.setLayout(new BorderLayout());
        boutonPanel.add(operationPanel,BorderLayout.SOUTH);
        operationPanel.setLayout(new GridLayout(1,2));
        operationPanel.add(submit);
        operationPanel.add(clear);
        if(informationTheorie){
            operationPanel.add(info);
        }
        submit.addActionListener(e -> submit());
        clear.addActionListener(e -> clear());
        info.addActionListener(e -> info());
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

    private void info() {
        String guess = ecran.getText();
        Essaie essaie = new Essaie(gameSituation,guess);
        double informationNeeded = gameSituation.getNeededInformation();
        double entropy = essaie.getEntropy();


        System.out.println("_____________________INFO________________________");
        System.out.println("informationNeeded = " + String.format( "%.2f", informationNeeded ));
        System.out.println("guess = "+guess);
        System.out.println("entropy = " + String.format( "%.2f", entropy ));
    }

    private void submit() {
        String guess = ecran.getText();
        Essaie essaie = new Essaie(gameSituation,guess);
        System.out.println("_____________________SUBMIT________________________");
        Response response = essaie.getResponse();
        essaie.processResponse();
        String resultat = response.toString();
        System.out.println("guess = "+guess);
        System.out.println("resultat = "+resultat);

        essaies[numeroEssaie-1].setText("Essai "+numeroEssaie +": " + ecran.getText() +" : "+response);
        numeroEssaie++;
        ecran.setText("");
        if(informationTheorie){
            double informationNeeded = gameSituation.getNeededInformation();
            System.out.println("informationNeeded = " + String.format( "%.2f", informationNeeded ));
        }
        if(!resultat.equalsIgnoreCase(TOUT_BON)){
            if(numeroEssaie<=MAX_ESSAIE){
                ecriretitreDroite();
                submit.setEnabled(false);
                info.setEnabled(false);
                for(int i=0;i<NOMBRE_BOUTON;i++){
                    boutons[i].setEnabled(true);
                }
            }else{
                finDePartie(false);
            }

        }else{
            finDePartie(true);
        }
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


    private void clear() {
//        guess="";
        if(partieFinie){
            toutABlanc();
            partieFinie = false;
        }
        ecran.setText("");

        info.setEnabled(false);
        submit.setEnabled(false);
        for(int i=0;i<NOMBRE_BOUTON;i++){
            boutons[i].setEnabled(true);
        }


    }

    private void toutABlanc() {
        secret = MasterMindUtils.buildSecret();
        numeroEssaie=1;
        for(int i=0;i<MAX_ESSAIE;i++){
            essaies[i].setText("");
        }
        ecriretitreDroite();

        clear.setText("Clear");
    }

    private void addCharToResponse(String caracterLieAuBouton) {

        ecran.setText(ecran.getText()+caracterLieAuBouton);
        if(ecran.getText().length()==SECRET_LENGHT){
            info.setEnabled(true);
            submit.setEnabled(true);
            for(int i=0;i<NOMBRE_BOUTON;i++){
                boutons[i].setEnabled(false);
            }
        }
    }

    private void initDroite() {

        droite.setLayout(new BorderLayout());
        titreDroite.setFont(police);
        titreDroite.setHorizontalAlignment(JLabel.CENTER);
        titreDroite.setPreferredSize(new Dimension(300,30));
        ecriretitreDroite();
        droite.add(titreDroite, BorderLayout.NORTH);
        tableauDEssaies.setBackground(Color.RED);
        tableauDEssaies.setLayout(new GridLayout(MAX_ESSAIE,1));
        for(int i=0;i<MAX_ESSAIE;i++){
            essaies[i]=new JLabel();
            essaies[i].setFont(police);
            essaies[i].setHorizontalAlignment(JLabel.CENTER);
            essaies[i].setPreferredSize(new Dimension(300,30));
            tableauDEssaies.add(essaies[i]);
        }
        droite.add(scroll, BorderLayout.CENTER);
    }

    private void ecriretitreDroite() {
        int essaiesRestants = (MAX_ESSAIE-numeroEssaie+1);
        titreDroite.setText( essaiesRestants + "Essais restant");
    }
}
