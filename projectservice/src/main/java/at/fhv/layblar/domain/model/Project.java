package at.fhv.layblar.domain.model;

import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import at.fhv.layblar.application.dto.DeviceCategoryDTO;
import at.fhv.layblar.application.dto.LabelDTO;
import at.fhv.layblar.commands.CreateProjectCommand;
import at.fhv.layblar.commands.JoinProjectCommand;
import at.fhv.layblar.commands.UpdateProjectCommand;
import at.fhv.layblar.events.ProjectCreatedEvent;
import at.fhv.layblar.events.ProjectJoinedEvent;
import at.fhv.layblar.events.ProjectUpdatedEvent;
import at.fhv.layblar.utils.exceptions.DeviceCategoryMissing;
import at.fhv.layblar.utils.exceptions.LabelCategoryConflictException;
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
        this.researcher = event.getResearcher();
    }


    public void apply(ProjectUpdatedEvent event) {
        this.projectName = event.getProjectName();
        this.projectDescription = event.getProjectDescription();
        this.projectDataUseDeclartion = event.getProjectDataUseDeclaration();
        this.startDate = event.getStartDate();
        this.endDate = event.getEndDate();
        updateLabels(event.getLabels());
        updateMetaData(event.getMetaDataInfo());
    }

    public void apply(ProjectJoinedEvent event) {
        this.participants.add(ProjectParticipant.createParticipant(event.getHouseholdId(), event.getHouseholdMetaData()));
    }


    public ProjectCreatedEvent process(CreateProjectCommand command, Researcher researcher) throws ProjectValidityTimeframeException, LabelCategoryConflictException {
        if(command.startDate.isAfter(command.endDate) || command.startDate.isBefore(LocalDateTime.now())){
            throw new ProjectValidityTimeframeException("Start date cannot be in the past or after the end date");
        }
        for (LabelDTO label : command.labels) {
            label.labelId = UUID.randomUUID().toString();
            for(DeviceCategoryDTO category : label.categories){
                if(category.deviceCategoryId == null){
                    category.deviceCategoryId = UUID.randomUUID().toString();
                }
            }
        }
        checkForConflictingLabelCategories(command.labels);
        return ProjectCreatedEvent.create(command, researcher);
    }

    public ProjectUpdatedEvent process(UpdateProjectCommand command) throws ProjectValidityTimeframeException, LabelCategoryConflictException {
        if(LocalDateTime.now().isAfter(startDate)){
            throw new ProjectValidityTimeframeException("Project has already started");
        }
        checkForConflictingLabelCategories(command.labels);
        return ProjectUpdatedEvent.create(command, this);
    }

    public ProjectJoinedEvent process(JoinProjectCommand command) throws ProjectValidityTimeframeException, ProjectMetaDataMissingException, DeviceCategoryMissing {
        if(LocalDateTime.now().isAfter(endDate) || LocalDateTime.now().isBefore(startDate)){
            throw new ProjectValidityTimeframeException("Project has not started or has already ended");
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


    private void checkForConflictingLabelCategories(List<LabelDTO> labels) throws LabelCategoryConflictException {
        boolean hasConflictingLabelCategories = labels.stream()
            .anyMatch(label -> labels.stream()
                .filter(otherLabel -> !otherLabel.labelId.equals(label.labelId))
                .flatMap(otherLabel -> otherLabel.categories.stream())
                .anyMatch(category -> label.categories.stream()
                    .map(cat -> cat.deviceCategoryId)
                    .collect(Collectors.toList())
                    .contains(category.deviceCategoryId)));
        if(hasConflictingLabelCategories){
            throw new LabelCategoryConflictException();
        }
    }

    private void updateLabels(List<Label> labels){
        for (Label label : labels) {
            this.labels.remove(label);
            this.labels.add(label);
        }
    }

    private void updateMetaData(List<ProjectMetaData> metaData){
        for (ProjectMetaData data : metaData) {
            this.metaDataInfo.remove(data);
            this.metaDataInfo.add(data);
        }
    }

}
