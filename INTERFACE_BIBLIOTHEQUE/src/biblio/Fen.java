package biblio;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class Fen extends JFrame {
  private JButton bouton = new JButton("Je suis etudiant");
  private JButton bouton1 = new JButton("Je suis enseignant");
  private static final long serialVersionUID = 1L; 
  private String url = "jdbc:mysql://mysql.istic.univ-rennes1.fr/base_ajafar";
  private String utilisateur = "user_ajafar";
  private String motDePasse = "jaafar";
  static Connection conn;
  
  private void connectToDatabase(){
  	
  	try{
  		/* Connexion à la base de données */
  	      Class.forName("com.mysql.jdbc.Driver");
  		conn=DriverManager.getConnection(url, utilisateur, motDePasse);
  		System.out.print("Connected");
  	} catch(Exception e){
  		System.err.println(e.getMessage());
  	}
  }


  /**
   * Procedure, where the database connection should be implemented. 
   */
  private void disconnectFromDatabase(){
  	try{
  		conn.close();
  	} catch(Exception e){
  		System.err.println(e.getMessage());
  	}
  }
  
  static public Connection getConnection(){
	  return conn;
  }
  


  public Fen(){      
    this.setTitle("Bibliotheque");
    this.setSize(300, 90);
    this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    this.setLocationRelativeTo(null);      
    this.getContentPane().setLayout(new FlowLayout());
    this.getContentPane().add(bouton);
    this.getContentPane().add(bouton1);
    
    //this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Icone.png")));
    



    bouton.addActionListener(new ActionListener(){
      public void actionPerformed(ActionEvent arg0) {
    	  connectToDatabase();
        Etudiant et = new Etudiant(null, "Etudiant", true);
      ZDialogInfo zInfo = et.showZDialog(); 
        JOptionPane jop = new JOptionPane();
       jop.showMessageDialog(null, zInfo.toString(), "At'Home", JOptionPane.INFORMATION_MESSAGE);
       
      }         
    }); 
    
    bouton1.addActionListener(new ActionListener(){
        public void actionPerformed(ActionEvent arg0) {
        	connectToDatabase();
        	  Enseignant et = new Enseignant(null, "Enseignant", true);
              ZDialogInfo zInfo = et.showZDialog(); 
                JOptionPane jop = new JOptionPane();
               jop.showMessageDialog(null, zInfo.toString(), "At'Home", JOptionPane.INFORMATION_MESSAGE);
        }         
      });
    

    
    this.setVisible(true);      
  }
   
  public static void main(String[] main){
    Fen fen = new Fen();
  }   
}

