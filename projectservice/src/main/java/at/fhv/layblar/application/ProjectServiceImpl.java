package at.fhv.layblar.application;

import java.util.LinkedList;
import java.util.List;

import at.fhv.layblar.domain.Project;
import at.fhv.layblar.domain.Researcher;
import at.fhv.layblar.infrastructure.ProjectRepository;
import at.fhv.layblar.infrastructure.ResearcherRepository;
import jakarta.ws.rs.core.Response;

public class ProjectServiceImpl implements ProjectService {

    ProjectRepository projectRepository;

    ResearcherRepository researcherRepository;

    @Override
    public ProjectDTO updateProject(String projectId, Project project) {
        
        
        Project projectToUpdate = projectRepository.findById(projectId);
        projectToUpdate.name = project.name;
        projectToUpdate.description = project.description;
        projectToUpdate.startDate = project.startDate;
        projectToUpdate.endDate = project.endDate;
        projectToUpdate.researcherId = project.researcherId;
        projectToUpdate.participants = project.participants;
        projectToUpdate.persist();
        return new ProjectDTO(projectToUpdate.id, projectToUpdate.name, projectToUpdate.description, projectToUpdate.startDate, projectToUpdate.endDate, projectToUpdate.researcherId, projectToUpdate.participants);
    
    }

    @Override
    public ProjectDTO createProject(Project project) {
        projectRepository.persist(project);
        return new ProjectDTO(project.id, project.name, project.description, project.startDate, project.endDate, project.researcherId, project.participants);
    }

    @Override
    public ProjectDTO getProject(String projectId) {
        Project project = projectRepository.findById(projectId);
        return new ProjectDTO(project.id, project.name, project.description, project.startDate, project.endDate, project.researcherId, project.participants);
    }

    @Override
    public ResearcherDTO createResearcher(Researcher researcher) {
        researcherRepository.persist(researcher);
        return new ResearcherDTO(researcher.id, researcher.name, researcher.organization);
    }

    @Override
    public List<ProjectDTO> getProjects() {
        List<Project> projects = projectRepository.listAll();
        List<ProjectDTO> projectDTOs = new LinkedList<>();
        projects.forEach(project -> projectDTOs.add(new ProjectDTO(project.id, project.name, project.description, project.startDate, project.endDate, project.researcherId, project.participants)));
        return projectDTOs;
    }
    
}
