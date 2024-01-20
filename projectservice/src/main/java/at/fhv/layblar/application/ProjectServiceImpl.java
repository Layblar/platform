package at.fhv.layblar.application;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.eclipse.microprofile.jwt.JsonWebToken;

import at.fhv.layblar.application.dto.ProjectDataDTO;
import at.fhv.layblar.application.dto.ProjectInfoDTO;
import at.fhv.layblar.application.dto.ProjectMetaDataDTO;
import at.fhv.layblar.application.dto.ResearcherDTO;
import at.fhv.layblar.commands.CreateProjectCommand;
import at.fhv.layblar.commands.JoinProjectCommand;
import at.fhv.layblar.commands.RegisterResearcherCommand;
import at.fhv.layblar.commands.UpdateProjectCommand;
import at.fhv.layblar.domain.model.Project;
import at.fhv.layblar.domain.model.ProjectParticipant;
import at.fhv.layblar.domain.model.Researcher;
import at.fhv.layblar.domain.readmodel.ProjectLabeledData;
import at.fhv.layblar.domain.readmodel.ProjectReadModel;
import at.fhv.layblar.events.ProjectCreatedEvent;
import at.fhv.layblar.events.ProjectEvent;
import at.fhv.layblar.events.ProjectJoinedEvent;
import at.fhv.layblar.events.ProjectUpdatedEvent;
import at.fhv.layblar.utils.EntityBuilder;
import at.fhv.layblar.utils.exceptions.ProjectNotFoundException;
import at.fhv.layblar.utils.exceptions.DeviceCategoryMissing;
import at.fhv.layblar.utils.exceptions.LabelCategoryConflictException;
import at.fhv.layblar.utils.exceptions.NotAuthorizedException;
import at.fhv.layblar.utils.exceptions.ProjectAlreadyJoinedException;
import at.fhv.layblar.utils.exceptions.ProjectMetaDataMissingException;
import at.fhv.layblar.utils.exceptions.ProjectValidityTimeframeException;
import at.fhv.layblar.utils.exceptions.VersionNotMatchingException;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;

@ApplicationScoped
public class ProjectServiceImpl implements ProjectService {

    @Inject
    JsonWebToken jsonWebToken;

    @Override
    @Transactional
    public ResearcherDTO createResearcher(RegisterResearcherCommand command) {
        Researcher researcher = Researcher.creatResearcher(command);
        Researcher.persist(researcher);
        return ResearcherDTO.createResearcherDTO(researcher);
    }

    @Override
    @Transactional
    public ProjectInfoDTO createProject(CreateProjectCommand command) throws NotAuthorizedException, ProjectValidityTimeframeException, LabelCategoryConflictException {
        Optional<Researcher> optResearcher = Researcher.findByIdOptional(jsonWebToken.getClaim("researcherId"));
        if(optResearcher.isEmpty()){
            throw new NotAuthorizedException("");
        }
        Project project = new Project();
        ProjectCreatedEvent event = project.process(command, optResearcher.get());
        event.persist();
        project.apply(event);
        return ProjectInfoDTO.createProjectInfoDTO(project);
    }

    @Override
    @Transactional
    public ProjectInfoDTO updateProject(String projectId, UpdateProjectCommand command) throws ProjectNotFoundException, NotAuthorizedException, VersionNotMatchingException, ProjectValidityTimeframeException, LabelCategoryConflictException {
        List<ProjectEvent> events = getEventsByEntityId(projectId);
        if(events.size() == 0){
            throw new ProjectNotFoundException("The project was not found");
        }
        Project project = EntityBuilder.buildProjectEntity(events);
        validateProjectResearcher(project);
        ProjectUpdatedEvent event = project.process(command);
        checkForVersionMismatch(events, project);
        event.persist();
        project.apply(event);
        return ProjectInfoDTO.createProjectInfoDTO(project);
    }

    @Override
    @Transactional
    public ProjectInfoDTO joinProject(String projectId, String householdId, JoinProjectCommand command)
            throws NotAuthorizedException, ProjectNotFoundException, VersionNotMatchingException, ProjectValidityTimeframeException, ProjectMetaDataMissingException, DeviceCategoryMissing, ProjectAlreadyJoinedException {
        validateHouseholdId(householdId);
        command.householdId = householdId;
        List<ProjectEvent> events = getEventsByEntityId(projectId);
        if(events.size() == 0){
            throw new ProjectNotFoundException("The project was not found");
        }
        Project project = EntityBuilder.buildProjectEntity(events);
        ProjectJoinedEvent event = project.process(command);
        checkForVersionMismatch(events, project);
        event.persist();
        project.apply(event);
        return ProjectInfoDTO.createProjectInfoDTO(project);
    }

    @Override
    public ProjectInfoDTO getProjectInfo(String projectId) throws NotAuthorizedException, ProjectNotFoundException {
        Optional<ProjectReadModel> optProject = ProjectReadModel.findByIdOptional(projectId);
        if(optProject.isEmpty()){
            throw new ProjectNotFoundException("The project was not found");
        }
        validateProjectResearcher(optProject.get());
        return ProjectInfoDTO.createProjectInfoDTO(optProject.get());
    }

    @Override
    public List<ProjectDataDTO> getProjectData(String projectId, Integer pageIndex, Integer pageSize) throws NotAuthorizedException, ProjectNotFoundException {
        Optional<ProjectReadModel> optProject = ProjectReadModel.findByIdOptional(projectId);
        if(optProject.isEmpty()){
            throw new ProjectNotFoundException("The project was not found");
        }
        validateProjectResearcher(optProject.get());
        List<ProjectLabeledData> labeledData = ProjectLabeledData.find("projectId", projectId).page(pageIndex, pageSize).list();
        return labeledData.stream().map(
            data -> ProjectDataDTO.createProjectDataDTO(data)
        ).collect(Collectors.toList());
    }

    @Override
    public List<ProjectInfoDTO> getProjects(String researcherId) throws NotAuthorizedException {
        List<ProjectReadModel> projects = ProjectReadModel.listAll();
        if(researcherId != null){
            if(!jsonWebToken.containsClaim("researcherId") || !jsonWebToken.getClaim("researcherId").equals(researcherId)){
                throw new NotAuthorizedException("Not authorized");
            }
            return ProjectReadModel.findByResearcherId(researcherId).stream().map(
                project -> ProjectInfoDTO.createProjectInfoDTO(project)
            ).collect(Collectors.toList());
        }
        return projects.stream().map(
            project -> ProjectInfoDTO.createProjectInfoDTO(project)
        ).collect(Collectors.toList());
    }

    @Override
    public List<ProjectMetaDataDTO> getProjectDagetProjectHouseholdMetadatata(String projectId, String householdId) throws NotAuthorizedException, ProjectNotFoundException {
        validateHouseholdId(householdId);
        Optional<ProjectReadModel> optProject = ProjectReadModel.findByIdOptional(projectId);
        if(optProject.isEmpty()){
            throw new ProjectNotFoundException("The project was not found");
        }
        Optional<ProjectParticipant> optParticipant = optProject.get().participants.stream().filter(particitpant -> particitpant.householdId.equals(householdId)).findAny();
        if(optParticipant.isPresent()){
            return optParticipant.get().householdMetaData.stream().map(data -> ProjectMetaDataDTO.createProjectMetaDataDTO(data)).collect(Collectors.toList());
        }
        throw new NotAuthorizedException("Household is not part of project");
    }


    private void validateProjectResearcher(Project project) throws NotAuthorizedException {
        if(!jsonWebToken.getClaim("researcherId").equals(project.researcher.researcherId)) {
            throw new NotAuthorizedException("Not Authorized to do this action");
        }
    }

    private void validateProjectResearcher(ProjectReadModel project) throws NotAuthorizedException {
        if(!jsonWebToken.getClaim("researcherId").equals(project.researcher.researcherId)) {
            throw new NotAuthorizedException("Not Authorized to do this action");
        }
    }

    private void validateHouseholdId(String householdId) throws NotAuthorizedException {
        if(!jsonWebToken.getClaim("householdId").equals(householdId)){
            throw new NotAuthorizedException("Users not authorized to do this action");
        }
    }

    private List<ProjectEvent> getEventsByEntityId(String entittyId) {
        return ProjectEvent.find("entityId", entittyId).list();
    }

    private void checkForVersionMismatch(List<ProjectEvent> events, Project project) throws VersionNotMatchingException {
        if(events.size() != getEventsByEntityId(project.projectId).size()){
            throw new VersionNotMatchingException();
        }
    }
      
}
