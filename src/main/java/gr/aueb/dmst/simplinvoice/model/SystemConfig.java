package gr.aueb.dmst.simplinvoice.model;


import javax.persistence.*;

@Entity
public class SystemConfig {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private Long id;

    private String myDataUsername;
    private String myDataApiKey;

    @OneToOne
    @JoinColumn(name = "company_profile_id", referencedColumnName = "id")
    private CompanyProfile companyProfile;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMyDataUsername() {
        return myDataUsername;
    }

    public void setMyDataUsername(String myDataUsername) {
        this.myDataUsername = myDataUsername;
    }

    public String getMyDataApiKey() {
        return myDataApiKey;
    }

    public void setMyDataApiKey(String myDataApiKey) {
        this.myDataApiKey = myDataApiKey;
    }

    public CompanyProfile getCompanyProfile() {
        return companyProfile;
    }

    public void setCompanyProfile(CompanyProfile companyProfile) {
        this.companyProfile = companyProfile;
    }
}
