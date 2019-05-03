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
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;

import java.io.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;

public class ModifEnseignant extends JDialog {
  private ZDialogInfo zInfo = new ZDialogInfo();
  private boolean sendData;
  private JLabel AuthLabel, icon,AjoutLabel, ModLabel,SupLabel,NomLivreLab,ModuleLab,DateLab,Modmodlab,
  ModmoduleLabel;
  private JRadioButton tranche1, tranche2, tranche3;
  private JTextField Auth,Ajout,Mod,NomLivre,Module,Date,Modmod,Modmodule;
  private JButton AjoutLivre,SuppLivre,ModLivre;
  private JComboBox Supp,Modd;

  public ModifEnseignant(JFrame parent, String title, boolean modal){
    super(parent, title, modal);
    this.setSize(600,500);
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

  

    //Ajouter
    JPanel Ajouter = new JPanel();
    Ajouter.setBackground(Color.white);
    Ajouter.setPreferredSize(new Dimension(150,300));
    Ajouter.setBorder(BorderFactory.createTitledBorder("Ajouter"));
    Ajout= new JTextField();
    Ajout.setPreferredSize(new Dimension(100, 25));
    Ajouter.setBorder(BorderFactory.createTitledBorder("Ajouter"));
    AjoutLabel = new JLabel("ISBN :");
    Ajouter.add(AjoutLabel);
    Ajouter.add(Ajout);
    
    NomLivre= new JTextField();
    NomLivre.setPreferredSize(new Dimension(100, 25));
    NomLivreLab = new JLabel("Nom :");
    Ajouter.add(NomLivreLab);
    Ajouter.add(NomLivre);
    
    Module= new JTextField();
    Module.setPreferredSize(new Dimension(100, 25));
    ModuleLab = new JLabel("Module :");
    Ajouter.add(ModuleLab);
    Ajouter.add(Module);
    
//    Date= new JTextField();
//    Date.setPreferredSize(new Dimension(100, 25));
//    DateLab = new JLabel("Date :");
//    Ajouter.add(DateLab);
//    Ajouter.add(Date);
    
    AjoutLivre= new JButton("Ajouter");
    AjoutLivre.setPreferredSize(new Dimension(100, 25));
    Ajouter.add(AjoutLivre);
    
    
    AjoutLivre.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent arg0) {  
        	String numMod="";
        	int ISBN = Integer.parseInt(Ajout.getText());
        	String NomLi = NomLivre.getText();
        	String Mod = Module.getText();
        	
        	try{
        	Connection conn = Fen.getConnection(); 
        	Statement query=conn.createStatement();
       	    ResultSet resultat=query.executeQuery("select idModule from Module where nomModule='"+Mod+"';");
       	    while(resultat.next()){
       	    	 numMod = resultat.getString("idModule"); 
       	    }
       	    
       	    System.out.println("Entre Insert1");
       	    String query2 = " insert into livre (ISBN, NomLivre) values (?, ?)";

       	      // create the mysql insert preparedstatement
       	      PreparedStatement preparedStmt2 = conn.prepareStatement(query2);
       	      preparedStmt2.setInt (1, ISBN);
       	      preparedStmt2.setString (2, NomLi);
       	      // execute the preparedstatement
       	      preparedStmt2.execute();
       	      
       	   System.out.println("Sortie Insert1");
   
//    		String insertDonnerLivre="INSERT into DonnerLivre VALUES ('"+Enseignant.getNumEnseignant()+"','"+ISBN+"','"+date+"');";
//    		ResultSet query3= conn.createStatement().executeQuery(insertDonnerLivre); 
    		
         	System.out.println("Entre Insert2");
       	   
    		String query3 = " insert into DonnerLivre (enseignant_NumEns, livre_ISBN, Date) values (?, ?, ?)";

    		System.out.println(Enseignant.getNumEnseignant());
     	      // create the mysql insert preparedstatement
     	      PreparedStatement preparedStmt3 = conn.prepareStatement(query3);
     	      preparedStmt3.setInt (1, Enseignant.getNumEnseignant());
     	      preparedStmt3.setInt (2, ISBN);
     	      Calendar calendar = Calendar.getInstance();
     	      java.sql.Date startDate = new java.sql.Date(calendar.getTime().getTime());
     	      String startDateString = startDate.toString();
     	      preparedStmt3.setString (3, startDateString);
     	      // execute the preparedstatement
     	      preparedStmt3.execute();

     	      
//    		String insertEstDonnee="INSERT into EstDonnee VALUES ('"+ISBN+"','"+numMod+"');";
//    		ResultSet query4= conn.createStatement().executeQuery(insertEstDonnee); 
     	      
     	     
     	     String query4 = " insert into EstDonne (livre_ISBN, Module_idModule) values (?, ?)";

    	      // create the mysql insert preparedstatement
    	      PreparedStatement preparedStmt4 = conn.prepareStatement(query4);
    	      preparedStmt4.setInt (1, ISBN);
    	      System.out.println(numMod);
    	      preparedStmt4.setString (2, numMod);
    	      // execute the preparedstatement
    	      preparedStmt4.execute();
     	      
    		
        	}catch(Exception exception)
    		{
    			System.out.println("Erreur dans la requete de insert");
    		}
        }
      });
    
    
  //Modifier
    JPanel Modifier = new JPanel();
    Modifier.setBackground(Color.white);
    Modifier.setPreferredSize(new Dimension(150, 300));
    Modifier.setBorder(BorderFactory.createTitledBorder("Modifier"));
    Modd= new JComboBox();
    ModLabel = new JLabel("Livres modifiables :");
    
    try{
    	Connection conn = Fen.getConnection(); 
    	Statement query=conn.createStatement();
   	    ResultSet resultat=query.executeQuery("select DISTINCT NomLivre from DonnerLivre "
   	    		+ "NATURAL JOIN livre"
   	    		+ " where enseignant_NumEns='"+Enseignant.getNumEnseignant()+"';");
   	    while(resultat.next()){
   	    	Modd.addItem(resultat.getString("NomLivre"));
   	    }
    }catch(Exception exception)
	{
		System.out.println("Erreur dans la requete de select supprimer ");
	}
    
    Modifier.add(ModLabel);
    Modifier.add(Modd);
    
    Modmodule= new JTextField();
    ModmoduleLabel = new JLabel("Nouveau Module :");
    Modifier.add(ModmoduleLabel);
    Modifier.add(Modmodule);
    Modmodule.setPreferredSize(new Dimension(100, 25));
    
    
    
//    Modmod= new JTextField();
//    Modmod.setPreferredSize(new Dimension(100, 25));
//    Modmodlab = new JLabel("Nouvelle Date:");
//    Modifier.add(Modmodlab);
//    Modifier.add(Modmod);
    
    ModLivre= new JButton("Modifier");
    ModLivre.setPreferredSize(new Dimension(100, 25));
    Modifier.add(ModLivre);
    
    
    ModLivre.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent arg0) {  
      	  
        }
      });
    
    
  //Supprimer
    JPanel Supprimer = new JPanel();
    Supprimer.setBackground(Color.white);
    Supprimer.setPreferredSize(new Dimension(290, 100));
    Supprimer.setBorder(BorderFactory.createTitledBorder("Supprimer"));
    Supp= new  JComboBox();
    
    try{
    	Connection conn = Fen.getConnection(); 
    	Statement query=conn.createStatement();
   	    ResultSet resultat=query.executeQuery("select DISTINCT NomLivre from DonnerLivre "
   	    		+ "NATURAL JOIN livre"
   	    		+ " where enseignant_NumEns='"+Enseignant.getNumEnseignant()+"';");
   	    while(resultat.next()){
   	    	Supp.addItem(resultat.getString("NomLivre"));
   	    }
    }catch(Exception exception)
	{
		System.out.println("Erreur dans la requete de select supprimer ");
	}
   
    
    SupLabel = new JLabel("Livres supprimables : ");
    Supprimer.add(SupLabel);
    Supprimer.add(Supp);
    
    SuppLivre= new JButton("Supprimer");
    SuppLivre.setPreferredSize(new Dimension(100, 25));
    Supprimer.add(SuppLivre);
    
    SuppLivre.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent arg0) {  
        	
        	
        	
        	
        	String nomliv = Supp.getSelectedItem().toString();
        	try{
            	Connection conn = Fen.getConnection(); 
        	String query = "delete from livre where NomLivre = ?";
            PreparedStatement preparedStmt = conn.prepareStatement(query);
            preparedStmt.setString (1, nomliv);
            
            

            // execute the preparedstatement
            preparedStmt.execute();
        	}catch(Exception exception)
        	{
        		System.out.println("Erreur dans la requete de select supprimer ");
        	}
        }
      });
    

    JPanel content = new JPanel();
    content.setBackground(Color.white);
    content.add(Ajouter);
    content.add(Modifier);
    content.add(Supprimer);
   
    

    JPanel control = new JPanel();
    //JButton okBouton = new JButton("");
    
//    okBouton.addActionListener(new ActionListener(){
//      public void actionPerformed(ActionEvent arg0) {  
//    	  
//      }
//    });

    JButton cancelBouton = new JButton("Annuler");
    cancelBouton.addActionListener(new ActionListener(){
    public void actionPerformed(ActionEvent arg0) {
    		
    	  setVisible(false);
      }      
    });

   // control.add(okBouton);
    control.add(cancelBouton);

    this.getContentPane().add(panIcon, BorderLayout.WEST);
    this.getContentPane().add(content, BorderLayout.CENTER);
    this.getContentPane().add(control, BorderLayout.SOUTH);
   
    
    
  }


}

