package biblio;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Enseignant extends JDialog {
  private ZDialogInfo zInfo = new ZDialogInfo();
  private boolean sendData;
  private JLabel AuthLabel, icon,mdplabel;
  private JRadioButton tranche1, tranche2, tranche3;
  private JTextField Auth,mdp;
  public String NumEnseignant ;
  static public int NumEns;
  
  static public int getNumEnseignant(){
	  return NumEns; 
  }
  
  
  private boolean IlExiste() {
	  int numE=0 ;
	  try{
	  Connection conn = Fen.getConnection(); 
	  Statement query=conn.createStatement();
	  ResultSet resultat=query.executeQuery("select * from enseignant WHERE NumEns='"+getNumEnseignant()+"';");
	  while(resultat.next()){
		  numE = resultat.getInt("NUmEns");
	  }
	  }catch(Exception exception)
		{
			System.out.println("Erreur dans la requete de select");
		}
	  
	  return numE==getNumEnseignant(); 
  }

  public Enseignant(JFrame parent, String title, boolean modal){
    super(parent, title, modal);
    this.setSize(300,200);
    this.setLocationRelativeTo(null);
    this.setResizable(false);
    this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
    this.initComponent();
   // this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Icone.png")));
    this.setBackground(Color.red);
    }

  public ZDialogInfo showZDialog(){
    this.sendData = false;
    this.setVisible(true);      
    return this.zInfo;      
  }

  private void initComponent(){
    //Ic�ne
    icon = new JLabel();
    JPanel panIcon = new JPanel();
    panIcon.setBackground(Color.white);
    panIcon.setLayout(new BorderLayout());
    panIcon.add(icon);

  

    //Num Id
    JPanel Authentification = new JPanel();
    Authentification.setBackground(Color.white);
    Authentification.setPreferredSize(new Dimension(290, 60));
    Authentification.setBorder(BorderFactory.createTitledBorder("Authentification"));
    Auth= new JTextField();
    Auth.setPreferredSize(new Dimension(100, 25));
    Authentification.setBorder(BorderFactory.createTitledBorder("Authentification"));
    AuthLabel = new JLabel("Numero Enseignant :");
    Authentification.add(AuthLabel);
    Authentification.add(Auth);
    
    //Mdp
    JPanel Mdp = new JPanel();
    Mdp.setBackground(Color.white);
    Mdp.setPreferredSize(new Dimension(290, 60));
    Mdp.setBorder(BorderFactory.createTitledBorder("Mot de passe"));
    mdp= new JPasswordField();
    mdp.setPreferredSize(new Dimension(100, 25));
    Mdp.setBorder(BorderFactory.createTitledBorder("Mot de passe"));
    mdplabel = new JLabel("Mot de passe :");
    Mdp.add(mdplabel);
    Mdp.add(mdp);
    
    

    JPanel content = new JPanel();
    content.setBackground(Color.white);
    content.add(Authentification);
    content.add(Mdp);
    

    JPanel control = new JPanel();
    JButton okBouton = new JButton("Se connecter");
   
    
    okBouton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {  
    	  NumEnseignant = Auth.getText();
    	    NumEns = Integer.parseInt(NumEnseignant);
    	  if(IlExiste()){
    	
    	  ModifEnseignant et = new ModifEnseignant(null, "ModifEnseignant", true);
          ZDialogInfo zInfo = et.showZDialog(); 
            JOptionPane jop = new JOptionPane();
           jop.showMessageDialog(null, zInfo.toString(), "At'Home", JOptionPane.INFORMATION_MESSAGE);
    	  } 
    	  else{
    		 System.out.println("N'est pas dans la bdd");
    	  }
    	  
      }
    });

    JButton cancelBouton = new JButton("Annuler");
    cancelBouton.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent arg0) {
    		
    	  setVisible(false);
      }      
    });

    control.add(okBouton);
    control.add(cancelBouton);

    this.getContentPane().add(panIcon, BorderLayout.WEST);
    this.getContentPane().add(content, BorderLayout.CENTER);
    this.getContentPane().add(control, BorderLayout.SOUTH);
   
    
    
  }


}
