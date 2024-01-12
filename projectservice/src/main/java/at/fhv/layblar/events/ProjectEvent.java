package at.fhv.layblar.events;

import jakarta.persistence.Entity;

@Entity
public abstract class ProjectEvent extends Event {

    static final String PROJECT_ID = "projectId";
    static final String PROJECT_NAME = "projectName";
    static final String PROJECT_DESCRIPTION = "projectDescription";
    static final String PROJECT_DATA_USE_DECLARATION = "projectDataUseDeclaration";
    static final String START_DATE = "startDate";
    static final String END_DATE = "endDate";
    static final String META_DATA_INFO = "metaDataInfo";
    static final String LABELS = "labels";
    static final String CREATED_AT = "createdAt";
    static final String RESEARCHER = "researcher";
    static final String PARTICIPANTS = "participants";

    public abstract void accept(ProjectEventVisitor visitor);
    
    public ProjectEvent(){
        super();
        this.entityType = "Project";
    }

}
