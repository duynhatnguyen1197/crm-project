package cycbersoft.java10.service;

import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import cycbersoft.java10.dto.RoleDto;
import cycbersoft.java10.dto.UserDto;
import cycbersoft.java10.entity.Role;
import cycbersoft.java10.entity.User;
import cycbersoft.java10.repository.RoleRepository;
import cycbersoft.java10.repository.UserRepository;

public class UserService {
	
	public static List<UserDto> getAll(){
		List<UserDto> dtos = new ArrayList<UserDto>();
		// gọi hàm chạy câu lệnh truy vấn lấy dữ liệu
		List<User> entities = UserRepository.findAll(); // trả về list<Role>
		// tham chiếu dữ liệu từ entity sang dto
		for ( User entity : entities) {
			UserDto dto = new UserDto();
			dto.setId(entity.getId());
			dto.setEmail(entity.getEmail());
			dto.setFullname(entity.getFullname());
			dto.setAvatar(entity.getAvatar());
			dto.setDescription(entity.getDescription());
			dtos.add(dto);
		}
		// trả về dto
		
		return dtos;
	}
	public static int insert(UserDto dto) {

		try {
			String hashed = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
			User entity = new User();
			entity.setEmail(dto.getEmail());
			entity.setPassword(hashed);
			entity.setFullname(dto.getFullname());
			entity.setAvatar(dto.getAvatar());
			entity.setRole_id(dto.getRole_id());
			return UserRepository.save(entity);
			
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return -1;
	}
	public int update(UserDto dto) {
		try {
			User entity = UserRepository.findById(dto.getId());
			if(entity !=null) {
				entity.setId(dto.getId());
				entity.setEmail(dto.getEmail());
				entity.setFullname(dto.getFullname());
				entity.setAvatar(dto.getAvatar());
				entity.setRole_id(dto.getRole_id());
				// nếu pass dc nhập thì thay đoi pass
				if(!dto.getPassword().isEmpty()) {
					String hashed = BCrypt.hashpw(dto.getPassword(), BCrypt.gensalt());
					entity.setPassword(hashed);
				}
				return UserRepository.edit(entity);
			}	
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
		}
		return -1;
	}

	public UserDto getById(int id) {
		// TODO Auto-generated method stub
		UserDto dto = new UserDto();
		// Gọi hàm truy vấn lấy dữ liệu
		User entity = UserRepository.findById(id); // findAll trả về List<User>
		// Tham chiếu dữ liệu từ entity -> Dto
		if (entity != null) {
			dto.setId(entity.getId());
			dto.setEmail(entity.getEmail());
			dto.setPassword(entity.getPassword());
			dto.setFullname(entity.getFullname());
			dto.setAvatar(entity.getAvatar());
			dto.setRole_id(entity.getRole_id());
		}
		// Trả về dto
		return dto;
	
	}
	public UserDto login(String email) {
		// TODO Auto-generated method stub
		UserDto dto = new UserDto();
		// Gọi hàm truy vấn lấy dữ liệu
		User entity = UserRepository.findByEmail(email); // findAll trả về List<User>
		// Tham chiếu dữ liệu từ entity -> Dto
		if (entity != null) {
			dto.setId(entity.getId());
			dto.setEmail(entity.getEmail());
			dto.setPassword(entity.getPassword());
			dto.setFullname(entity.getFullname());
			dto.setAvatar(entity.getAvatar());
			dto.setRole_id(entity.getRole_id());
		}
		// Trả về dto
		return dto;
	
	}
	public int Delete(int idD) {
		// TODO Auto-generated method stub
		return UserRepository.delete(idD);
	}
}
