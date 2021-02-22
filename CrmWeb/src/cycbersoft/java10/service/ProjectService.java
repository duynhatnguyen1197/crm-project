package cycbersoft.java10.service;

import java.util.ArrayList;
import java.util.List;

import cycbersoft.java10.dto.ProjectDto;
import cycbersoft.java10.entity.Project;
import cycbersoft.java10.repository.ProjectRepository;

public class ProjectService {

private ProjectRepository projectRepository;
	
	public ProjectService() {
		projectRepository = new ProjectRepository();
	}
	
	public List<ProjectDto> getAll(){
		List<ProjectDto> 	dtos 		= new ArrayList<ProjectDto>();
		List<Project>		entities 	=  projectRepository.findAll();
		
		for (Project project : entities) {
			ProjectDto dto = new ProjectDto();
			dto.setId(project.getId());
			dto.setName(project.getName());
			dto.setDescription(project.getDescription());
			dto.setStartDate(project.getStartDate());
			dto.setEndDate(project.getEndDate());
			dto.setFullName(project.getFullName());
	//		dto.setCreateUser(project.getCreateUser());
			
			dtos.add(dto);
		}
		return dtos;
	}
	
	public ProjectDto getById(int id) {
		 ProjectDto dto = new ProjectDto();
		 Project entity = projectRepository.findById(id);
		 if (entity != null) {
		 dto.setId(entity.getId());
		 dto.setName(entity.getName());
		 dto.setDescription(entity.getDescription());
		 dto.setStartDate(entity.getStartDate());
		 dto.setEndDate(entity.getEndDate());
		 dto.setCreateUser(entity.getCreateUser());
		 }
		 return dto;
	}
	
	public int insert(ProjectDto dto) {
		
		Project entity = new Project();
		
//		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setStartDate(dto.getStartDate());
		entity.setEndDate(dto.getEndDate());
		entity.setCreateUser(dto.getCreateUser());
		
		return projectRepository.save(entity);
	}
	
	public int update(ProjectDto dto) {
		try {
		Project entity = projectRepository.findById(dto.getId());
		
		if(entity!=null) {
			
			entity.setId(dto.getId());
			entity.setName(dto.getName());
			entity.setDescription(dto.getDescription());
			entity.setStartDate(dto.getStartDate());
			entity.setEndDate(dto.getEndDate());
			entity.setCreateUser(dto.getCreateUser());
				
		}
		return projectRepository.edit(entity);
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return -1;
	}
	
	public int delete(int id) {
		return projectRepository.deleteById(id);
	}
}
