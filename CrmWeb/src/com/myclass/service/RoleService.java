package com.myclass.service;

import java.util.ArrayList;
import java.util.List;

import com.myclass.dto.RoleDto;
import com.myclass.entity.Role;
import com.myclass.repository.RoleRepository;

public class RoleService {
	
	private RoleRepository roleRepository;
	
	public RoleService() {
		roleRepository = new RoleRepository();
	}
	
	public List<RoleDto> getAll() {
		List<RoleDto> dtos = new ArrayList<RoleDto>();
		// Gọi hàm truy vấn lấy dữ liệu
		List<Role> entities = roleRepository.findAll(); // findAll trả về List<Role>
		// Tham chiếu dữ liệu từ entity -> Dto
		for (Role entity : entities) {
			RoleDto dto = new RoleDto();
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			dto.setDesc(entity.getDescription());
			
			dtos.add(dto);
		}
		// Trả về dto
		return dtos;
	}

	public RoleDto getById(int id) {
		// Gọi hàm truy vấn lấy dữ liệu
		Role entity = roleRepository.findById(id); // findAll trả về List<Role>
		// Tham chiếu dữ liệu từ entity -> Dto
		RoleDto dto = new RoleDto();
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDesc(entity.getDescription());
		// Trả về dto
		return dto;
	}
	
	public int insert(RoleDto dto) {
		Role entity = new Role();
		entity.setName(dto.getName());
		entity.setDescription(dto.getDesc());
		return roleRepository.save(entity);
	}
	
	public int update(RoleDto dto) {
		Role entity = roleRepository.findById(dto.getId());
		if(entity != null) {
			entity.setId(dto.getId());
			entity.setName(dto.getName());
			entity.setDescription(dto.getDesc());
			return roleRepository.edit(entity);
		}
		return -1;
	}
}
