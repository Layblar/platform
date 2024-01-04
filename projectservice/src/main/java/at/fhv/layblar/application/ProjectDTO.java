package at.fhv.layblar.application;

import java.util.List;

public class ProjectDTO {
    
    public String id;
    public String name;
    public String description;
    public String startDate;
    public String endDate;
    public String researcherId;
    public List<String> participants;



    public ProjectDTO(String id, String name, String description, String startDate, String endDate, String researcherId, List<String> participants) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.researcherId = researcherId;
        this.participants = participants;
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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartDate() {
        return this.startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return this.endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getResearcherId() {
        return this.researcherId;
    }

    public void setResearcherId(String researcherId) {
        this.researcherId = researcherId;
    }

    public List<String> getParticipants() {
        return this.participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

}
