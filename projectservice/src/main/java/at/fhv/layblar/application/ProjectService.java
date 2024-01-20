package at.fhv.layblar.application;

import java.util.List;

import at.fhv.layblar.application.dto.ProjectDataDTO;
import at.fhv.layblar.application.dto.ProjectInfoDTO;
import at.fhv.layblar.application.dto.ProjectMetaDataDTO;
import at.fhv.layblar.application.dto.ResearcherDTO;
import at.fhv.layblar.commands.CreateProjectCommand;
import at.fhv.layblar.commands.JoinProjectCommand;
import at.fhv.layblar.commands.RegisterResearcherCommand;
import at.fhv.layblar.commands.UpdateProjectCommand;
import at.fhv.layblar.utils.exceptions.ProjectNotFoundException;
import at.fhv.layblar.utils.exceptions.VersionNotMatchingException;
import at.fhv.layblar.utils.exceptions.DeviceCategoryMissing;
import at.fhv.layblar.utils.exceptions.LabelCategoryConflictException;
import at.fhv.layblar.utils.exceptions.NotAuthorizedException;
import at.fhv.layblar.utils.exceptions.ProjectAlreadyJoinedException;
import at.fhv.layblar.utils.exceptions.ProjectMetaDataMissingException;
import at.fhv.layblar.utils.exceptions.ProjectValidityTimeframeException;

public interface ProjectService {
    
    public ResearcherDTO createResearcher(RegisterResearcherCommand command);
    
    public ProjectInfoDTO updateProject(String projectId, UpdateProjectCommand command) throws ProjectNotFoundException, NotAuthorizedException, VersionNotMatchingException, ProjectValidityTimeframeException, LabelCategoryConflictException;

    public ProjectInfoDTO createProject(CreateProjectCommand command) throws NotAuthorizedException, ProjectValidityTimeframeException, LabelCategoryConflictException;

    public ProjectInfoDTO joinProject(String projectId, String householdId, JoinProjectCommand command) throws NotAuthorizedException, ProjectNotFoundException, VersionNotMatchingException, ProjectValidityTimeframeException, ProjectMetaDataMissingException, DeviceCategoryMissing, ProjectAlreadyJoinedException;

    public ProjectInfoDTO getProjectInfo(String projectId) throws NotAuthorizedException, ProjectNotFoundException;

    public ProjectDataDTO getProjectData(String projectId);

    public List<ProjectInfoDTO> getProjects(String reasearcherId, String householdId, Boolean joinable) throws NotAuthorizedException;

    public List<ProjectMetaDataDTO> getProjectDagetProjectHouseholdMetadatata(String projectId, String householdId) throws NotAuthorizedException, ProjectNotFoundException;
}
