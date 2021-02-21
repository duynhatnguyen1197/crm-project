package cycbersoft.java10.service;

import java.util.ArrayList;
import java.util.List;

import cycbersoft.java10.dto.RoleDto;
import cycbersoft.java10.entity.Role;
import cycbersoft.java10.repository.RoleRepository;


public class RoleService {
	
	private RoleRepository roleRepository;
	
	public  RoleService() {
		roleRepository = new RoleRepository();
	}
	

	public  List<RoleDto> getAll(){
		List<RoleDto> dtos = new ArrayList<RoleDto>();
		// gọi hàm chạy câu lệnh truy vấn lấy dữ liệu
		List<Role> entities = RoleRepository.findAll(); // trả về list<Role>
		// tham chiếu dữ liệu từ entity sang dto
		for ( Role entity : entities) {
			RoleDto dto = new RoleDto();
			dto.setId(entity.getId());
			dto.setName(entity.getName());
			dto.setDescription(entity.getDescription());
			dtos.add(dto);
		}
		// trả về dto
		
		return dtos;
	}
	public RoleDto getById(int id) {
		RoleDto dto = new RoleDto();
		Role entity = roleRepository.findById(id);
		dto.setId(entity.getId());
		dto.setName(entity.getName());
		dto.setDescription(entity.getDescription());
		
		return dto;
	}
	
	public int insert(RoleDto dto) {
		
		Role entity = new Role();
		entity.setName(dto.getName());
		entity.setDescription(dto.getDescription());
		 
		return roleRepository.save(entity);
	}

	
	public int update(RoleDto dto) {
		Role entity = roleRepository.findById(dto.getId());
		if(entity != null) {
			
			entity.setName(dto.getName());
			entity.setDescription(dto.getDescription());
			entity.setId(dto.getId());
			return roleRepository.edit(entity);
			
		}
		return -1;
	}


	public int drop(int idD) {
		return roleRepository.delete(idD);
		
		
	}
}
