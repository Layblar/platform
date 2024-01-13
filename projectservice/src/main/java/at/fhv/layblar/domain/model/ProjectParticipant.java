package at.fhv.layblar.domain.model;

import java.util.List;

public class ProjectParticipant {

    public String householdId;
    public List<ProjectMetaData> householdMetaData;    

    private ProjectParticipant(String householdId, List<ProjectMetaData> householdMetaData) {
        this.householdId = householdId;
        this.householdMetaData = householdMetaData;
    }

    public static ProjectParticipant createParticipant(String householdId, List<ProjectMetaData> householdMetaData) {
        return new ProjectParticipant(householdId, householdMetaData);
    }

}
