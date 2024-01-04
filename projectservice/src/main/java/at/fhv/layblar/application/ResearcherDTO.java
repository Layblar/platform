package at.fhv.layblar.application;

public class ResearcherDTO {
    public String id;
    public String name;
    public String organization;


    public ResearcherDTO(String id, String name, String organization) {
        this.id = id;
        this.name = name;
        this.organization = organization;
    }


    public String getId() {
        return this.id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganization() {
        return this.organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }

}
