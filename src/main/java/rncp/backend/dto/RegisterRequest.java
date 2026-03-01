package rncp.backend.dto;

public class RegisterRequest {
    private String firstName ;
    private String lastName;
    private String email;
    private String password;
    private String profession;
    private boolean etudiant;
    private boolean professeur;


    public boolean isEtudiant() {
        return etudiant;
    }

    public void setEtudiant(boolean etudiant) {
        this.etudiant = etudiant;
    }

    public boolean isProfesseur() {
        return professeur;
    }

    public void setProfesseur(boolean professeur) {
        this.professeur = professeur;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }


    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
