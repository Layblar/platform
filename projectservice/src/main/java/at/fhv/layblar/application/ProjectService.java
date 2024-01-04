package at.fhv.layblar.application;

import java.util.List;

import at.fhv.layblar.domain.Project;
import at.fhv.layblar.domain.Researcher;

public interface ProjectService {
    
    public ProjectDTO updateProject(String projectId, Project project);

    public ProjectDTO createProject(Project project);

    public ProjectDTO getProject(String projectId);

    public ResearcherDTO createResearcher(Researcher researcher);

    public List<ProjectDTO> getProjects();
}
