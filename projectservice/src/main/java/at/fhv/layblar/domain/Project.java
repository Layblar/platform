package at.fhv.layblar.domain;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

import at.fhv.layblar.commands.CreateProjectCommand;
import at.fhv.layblar.commands.JoinProjectCommand;
import at.fhv.layblar.commands.UpdateProjectCommand;
import at.fhv.layblar.events.ProjectCreatedEvent;
import at.fhv.layblar.events.ProjectJoinedEvent;
import at.fhv.layblar.events.ProjectUpdatedEvent;
import at.fhv.layblar.utils.exceptions.DeviceCategoryMissing;
import at.fhv.layblar.utils.exceptions.ProjectMetaDataMissingException;
import at.fhv.layblar.utils.exceptions.ProjectValidityTimeframeException;

public class Project {

    public String projectId;
    public String projectName;
    public String projectDescription;
    public String projectDataUseDeclartion;
    public LocalDateTime startDate;
    public LocalDateTime endDate;
    public List<ProjectMetaData> metaDataInfo;
    public List<Label> labels;
    public LocalDateTime createdAt;
    public Researcher researcher;
    public List<ProjectParticipant> participants;

    
    public Project() {}


    public void apply(ProjectCreatedEvent event) {
        this.projectId = event.getProjectId();
        this.projectName = event.getProjectName();
        this.projectDescription = event.getProjectDescription();
        this.projectDataUseDeclartion = event.getProjectDataUseDeclaration();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.metaDataInfo = event.getMetaDataInfo();
        this.labels = event.getLabels();
        this.createdAt = event.getCreatedAt();
        this.participants = new LinkedList<>();
    }


    public void apply(ProjectUpdatedEvent event) {
        this.projectName = event.getProjectName();
        this.projectDescription = event.getProjectDescription();
        this.projectDataUseDeclartion = event.getProjectDataUseDeclaration();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        this.metaDataInfo = event.getMetaDataInfo();
        this.labels = event.getLabels();
    }

    public void apply(ProjectJoinedEvent event) {
        this.participants.add(ProjectParticipant.createParticipant(event.getHouseholdId(), event.getHouseholdMetaData()));
    }


    public ProjectCreatedEvent process(CreateProjectCommand command, Researcher researcher) {
        return ProjectCreatedEvent.create(command, researcher);
    }


    public ProjectUpdatedEvent process(UpdateProjectCommand command) throws ProjectValidityTimeframeException {
        if(LocalDateTime.now().isAfter(startDate)){
            throw new ProjectValidityTimeframeException("Project has already started");
        }
        return ProjectUpdatedEvent.create(command, this);
    }

    public ProjectJoinedEvent process(JoinProjectCommand command) throws ProjectValidityTimeframeException, ProjectMetaDataMissingException, DeviceCategoryMissing {
        if(LocalDateTime.now().isAfter(endDate)){
            throw new ProjectValidityTimeframeException("Validity period of project has ended");
        }

        List<String> metaDataIds = metaDataInfo.stream().map(metadata -> metadata.metaDataId).collect(Collectors.toList());
        List<String> householdmetaDataIds = command.metaData.stream().map(metadata -> metadata.metaDataId).collect(Collectors.toList());
        if(!metaDataIds.equals(householdmetaDataIds)){
            throw new ProjectMetaDataMissingException();
        }

        List<String> deviceCategoryIds = command.devices.stream()
        .flatMap(device -> device.categories.stream())
        .map(category -> category.deviceCategoryId)
        .collect(Collectors.toList());
        for (Label label : labels) {
            if(!label.categories.stream().anyMatch(category -> deviceCategoryIds.contains(category.deviceCategoryId))){
                throw new DeviceCategoryMissing();
            }
        }

        return ProjectJoinedEvent.create(command,this);
    }

}
