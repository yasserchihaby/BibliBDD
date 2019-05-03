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
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextArea;
import javax.swing.ButtonGroup;
import javax.swing.JTextField;

import java.io.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

public class Etudiant extends JDialog {
	private ZDialogInfo zInfo = new ZDialogInfo();
	private boolean sendData;
	private JLabel AnneeLabel, SemLabel, modlabel, icon , parlabel;
	private JComboBox Sem, annee, mod , par;

	public Etudiant(JFrame parent, String title, boolean modal) {
		super(parent, title, modal);
		this.setSize(550, 500);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JDialog.DO_NOTHING_ON_CLOSE);
		this.initComponent();
		// this.setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("Icone.png")));
		this.setBackground(Color.red);
	}

	public ZDialogInfo showZDialog() {
		this.sendData = false;
		this.setVisible(true);
		return this.zInfo;
	}

	private void initComponent() {
		// Ic�ne
		icon = new JLabel();
		JPanel panIcon = new JPanel();
		panIcon.setBackground(Color.white);
		panIcon.setLayout(new BorderLayout());
		panIcon.add(icon);

		// Annee
		JPanel Annee = new JPanel();
		Annee.setBackground(Color.white);
		Annee.setPreferredSize(new Dimension(220, 70));
		Annee.setBorder(BorderFactory.createTitledBorder("Annee"));
		annee = new JComboBox();
		annee.addItem("Sans Importance");
		annee.addItem("ESIR1");
		annee.addItem("ESIR2");
		annee.addItem("ESIR3");
		AnneeLabel = new JLabel("Annee :");
		Annee.add(AnneeLabel);
		Annee.add(annee);

		// Semestre
		JPanel Semestre = new JPanel();
		Semestre.setBackground(Color.white);
		Semestre.setPreferredSize(new Dimension(220, 70));
		Semestre.setBorder(BorderFactory.createTitledBorder("Semestre"));
		Sem = new JComboBox();
		Sem.addItem("Sans Importance");
		Sem.addItem("S5");
		Sem.addItem("S6");
		Sem.addItem("S7");
		Sem.addItem("S8");
		Sem.addItem("S9");
		Sem.addItem("S10");
		SemLabel = new JLabel("Semestre :");
		Semestre.add(SemLabel);
		Semestre.add(Sem);

		// Module
		JPanel Module = new JPanel();
		Module.setBackground(Color.white);
		Module.setPreferredSize(new Dimension(220, 70));
		Module.setBorder(BorderFactory.createTitledBorder("Module"));
		mod = new JComboBox();
		mod.addItem("Sans Importance");

		try {
			Connection conn = Fen.getConnection();
			Statement query = conn.createStatement();
			ResultSet resultat = query
					.executeQuery("select DISTINCT NomModule from Module;");
			while (resultat.next()) {
				mod.addItem(resultat.getString("NomModule"));
			}
		} catch (Exception exception) {
			System.out.println("Erreur dans la requete de select supprimer ");
		}

		modlabel = new JLabel("Module :");
		Module.add(modlabel);
		Module.add(mod);
		
		// Parcours
				JPanel Parcours = new JPanel();
				Parcours.setBackground(Color.white);
				Parcours.setPreferredSize(new Dimension(220, 70));
				Parcours.setBorder(BorderFactory.createTitledBorder("Parcours"));
				par = new JComboBox();
				par.addItem("Sans Importance");

				try {
					Connection conn = Fen.getConnection();
					Statement query = conn.createStatement();
					ResultSet resultat = query
							.executeQuery("select DISTINCT NomParcours from Parcours;");
					while (resultat.next()) {
						par.addItem(resultat.getString("NomParcours"));
					}
				} catch (Exception exception) {
					System.out.println("Erreur dans la requete de select supprimer ");
				}

				parlabel = new JLabel("Parcours :");
				Parcours.add(parlabel);
				Parcours.add(par);

		// Affichage
		JPanel Affichage = new JPanel();
		Affichage.setBackground(Color.white);
		Affichage.setPreferredSize(new Dimension(500, 300));
		Affichage.setBorder(BorderFactory
				.createTitledBorder("Les livres disponibles"));
		JTextArea zoneTexte = new JTextArea();
		zoneTexte.setSize(new Dimension(450, 190));
		Affichage.add(zoneTexte);

		JPanel content = new JPanel();
		content.setBackground(Color.white);
		content.add(Annee);
		content.add(Semestre);
		content.add(Module);
		content.add(Parcours);
		content.add(Affichage);
		

		JPanel control = new JPanel();
		JButton okBouton = new JButton("Rechercher");

		okBouton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				String Module = mod.getSelectedItem().toString();
				String Semestre = Sem.getSelectedItem().toString();
				String Annee = annee.getSelectedItem().toString();
				String Parcours = par.getSelectedItem().toString();
				
				try {
					String zTexte = "";
					Connection conn = Fen.getConnection();
					int idModule = 0;
					if(Semestre.equalsIgnoreCase("Sans Importance") && Annee.equalsIgnoreCase("Sans Importance") && Parcours.equalsIgnoreCase("Sans Importance")&& !Module.equalsIgnoreCase("Sans Importance")){
					
						Statement query = conn.createStatement();
						ResultSet resultat = query.executeQuery("select idModule from Module where nomModule='"
									+ Module + "';");
					while (resultat.next()) {
						idModule = Integer.parseInt(resultat.getString("idModule"));
					}
				
					
					int ISBN = 0;
					Statement query2 = conn.createStatement();
					ResultSet resultat2 = query2.executeQuery("select livre_ISBN from EstDonne where Module_idModule='"+ idModule + "';");
					while (resultat2.next()) {
						ISBN = Integer.parseInt(resultat2.getString("livre_ISBN"));
						
						Statement query3 = conn.createStatement();
						ResultSet resultat3 = query3.executeQuery("SELECT NomLivre From livre"+ " WHERE ISBN='"+ ISBN + "' ;");

						while (resultat3.next()) {
							zTexte += "Nom du livre : "
									+ resultat3.getString("NomLivre") + "\n";
						}
						zoneTexte.setText(zTexte);
					}
				}
					
					
				 if(Semestre.equalsIgnoreCase("Sans Importance") && Annee.equalsIgnoreCase("Sans Importance") && 
							Parcours.equalsIgnoreCase("Sans Importance") &&  Module.equalsIgnoreCase("Sans Importance")){
						Statement query4= conn.createStatement();
						ResultSet resultat = query4.executeQuery("select NomLivre from livre;");
						
						while (resultat.next()) {
							zTexte += "Nom du livre : "
									+ resultat.getString("NomLivre") + "\n";
						}
						zoneTexte.setText(zTexte);
						
					}
					
				} catch (Exception exception) {
					System.out
							.println("Erreur dans la requete de select Rechercher ");
				}
				
				

			}
		});

		JButton cancelBouton = new JButton("Annuler");
		cancelBouton.addActionListener(new ActionListener() {
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
