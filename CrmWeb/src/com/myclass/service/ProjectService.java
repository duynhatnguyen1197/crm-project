package com.myclass.service;

import java.util.ArrayList;
import java.util.List;

import com.myclass.dto.ProjectDto;
import com.myclass.entity.Project;
import com.myclass.repository.ProjectRepository;

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
			dto.setCreateUser(project.getCreateUser());
			
			dtos.add(dto);
		}
		return dtos;
	}
	
	public ProjectDto getById(int id) {
		 ProjectDto dto = new ProjectDto();
		 Project entity = projectRepository.findById(id);
		 
		 dto.setId(entity.getId());
		 dto.setName(entity.getName());
		 dto.setDescription(entity.getDescription());
		 dto.setStartDate(entity.getStartDate());
		 dto.setEndDate(entity.getEndDate());
		 dto.setCreateUser(entity.getCreateUser());
		 
		 return dto;
	}
	
	public int insert(ProjectDto dto) {
		
		Project entity = new Project();
		
		entity.setId(dto.getId());
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		entity.setStartDate(dto.getStartDate());
		entity.setEndDate(dto.getEndDate());
		entity.setCreateUser(dto.getCreateUser());
		
		return projectRepository.save(entity);
	}
	
	public int update(ProjectDto dto) {
		Project entity = projectRepository.findById(dto.getId());
		
		if(entity!=null) {
			
			entity.setId(dto.getId());
			entity.setName(dto.getName());
			entity.setDescription(dto.getDescription());
			entity.setStartDate(dto.getStartDate());
			entity.setEndDate(dto.getEndDate());
			entity.setCreateUser(dto.getCreateUser());
				
		}
		
		return projectRepository.save(entity);
	}
	
	public void delete(int id) {
		projectRepository.deleteById(id);
	}
}
