package com.myclass.service;

import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.myclass.dto.UserDto;
import com.myclass.dto.UserDto;
import com.myclass.entity.Role;
import com.myclass.entity.User;
import com.myclass.entity.User;
import com.myclass.repository.RoleRepository;
import com.myclass.repository.UserRepository;

public class UserService {

	private UserRepository userRepository;
	private RoleRepository roleRepository;

	public UserService() {
		userRepository = new UserRepository();
		roleRepository = new RoleRepository();
	}

	public List<UserDto> getAll() {
//		List<UserDto> dtos = new ArrayList<UserDto>();
//		// Gọi hàm truy vấn lấy dữ liệu
//		List<User> entities = userRepository.findAll(); // findAll trả về List<User>
//		// Tham chiếu dữ liệu từ entity -> Dto
//		for (User entity : entities) {
//			UserDto dto = new UserDto();
//			dto.setId(entity.getId());
//			dto.setEmail(entity.getEmail());
//			dto.setPassword(entity.getPassword());
//			dto.setFullname(entity.getFullname());
//			dto.setAvatar(entity.getAvatar());
//			dto.setRoleId(entity.getRoleId());
//			
//			// LẤY RA THÔNG TIN Role DỰA VÀ KHÓA NGOẠI
//			Role role = roleRepository.findById(entity.getRoleId());
//			dto.setRoleDesc(role.getDescription());
//			
//			dtos.add(dto);
//		}
//		// Trả về dto
//		return dtos;
		return userRepository.findAllJoin();
	}

	public UserDto getById(int id) {
		UserDto dto = new UserDto();
		// Gọi hàm truy vấn lấy dữ liệu
		User entity = userRepository.findById(id); // findAll trả về List<User>
		// Tham chiếu dữ liệu từ entity -> Dto
		if (entity != null) {
			dto.setId(entity.getId());
			dto.setEmail(entity.getEmail());
			dto.setPassword(entity.getPassword());
			dto.setFullname(entity.getFullname());
			dto.setRoleId(entity.getRoleId());
		}
		// Trả về dto
		return dto;
	}

	public int insert(UserDto userDto) {

		try {
			String hashed = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());
			// B1. THAM CHIẾU DTO -> ENTITY
			User entity = new User(userDto.getEmail(), hashed, userDto.getFullname(),
					userDto.getRoleId());
//			//B2. GỌI HÀM TRUY VẤN THÊM MỚI USER
			return userRepository.save(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}

	public int update(UserDto userDto) {

		try {
			// B1. THAM CHIẾU DTO -> ENTITY
			User entity = userRepository.findById(userDto.getId());
			if(entity != null) {
				entity.setId(userDto.getId());
				entity.setEmail(userDto.getEmail());
				entity.setFullname(userDto.getFullname());
				entity.setRoleId(userDto.getRoleId());
				// NẾU PASS ĐƯỢC NHẬP => THAY ĐỔI PASS
				if(!userDto.getPassword().isEmpty()) {
					String hashed = BCrypt.hashpw(userDto.getPassword(), BCrypt.gensalt());
					entity.setPassword(hashed);
				}
			}
			
			// B2. GỌI HÀM TRUY VẤN CẬP NHẬT USER
			System.out.println(userRepository.edit(entity));
			return userRepository.edit(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	public UserDto getByEmail(String email) {
		UserDto dto = new UserDto();
		// Gọi hàm truy vấn lấy dữ liệu
		User entity = userRepository.findByEmail(email); // findAll trả về List<User>
		// Tham chiếu dữ liệu từ entity -> Dto
		if (entity != null) {
			dto.setId(entity.getId());
			dto.setEmail(entity.getEmail());
			dto.setPassword(entity.getPassword());
			dto.setFullname(entity.getFullname());
			dto.setRoleId(entity.getRoleId());
		}
		// Trả về dto
		return dto;
	}
	
	public void delete(int id) {
		userRepository.deleteById(id);	
	}
}
