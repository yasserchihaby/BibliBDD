package biblio;
public class ZDialogInfo {
  private String nom, prenom, sexe, age, groupesanguin, taille, numero, mail, post;

  public ZDialogInfo(){}
  public ZDialogInfo( String nom, String prenom, String sexe, String age, String groupesanguin, String taille, String numero, String mail, String post){
    this.nom = nom;
    this.prenom= prenom;
    this.sexe = sexe;
    this.age = age;
    this.groupesanguin = groupesanguin;
    this.taille = taille;
    this.numero=numero;
    this.mail= mail;
    this.post=post;
    
  }

  public String toString(){
    String str;
    if(this.nom != null && this.prenom != null && this.sexe != null && this.taille != null && this.age != null && this.groupesanguin != null
                    && this.numero != null && this.post != null ){
      str = "Description"+ "\n";
      str += "Nom : " + this.nom + "\n";
      str += "Prénom : " + this.prenom + "\n";
      str += "Sexe : " + this.sexe + "\n";
      str += "Age : " + this.age + "\n";
      str += "Groupe Sanguin : " + this.groupesanguin + "\n";
      str += "Taille : " + this.taille + "\n";
      str += "Numero de téléphone : " + this.numero + "\n";
      str += "Mail : " + this.mail + "\n";
      str += "Adresse : " + this.post + "\n";



    }
    else{
      str = "Aucune information !";
    }
    return str;
  }
}