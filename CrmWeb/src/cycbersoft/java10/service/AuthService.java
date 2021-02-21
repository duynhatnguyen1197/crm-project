package cycbersoft.java10.service;

import org.mindrot.jbcrypt.BCrypt;

import cycbersoft.java10.dto.UserDto;
import cycbersoft.java10.entity.Role;
import cycbersoft.java10.entity.User;
import cycbersoft.java10.repository.RoleRepository;
import cycbersoft.java10.repository.UserRepository;

public class AuthService {
	
	private UserRepository userRepository;
	private static  RoleRepository roleRepository;
	
	public AuthService() {
		userRepository = new UserRepository();
		roleRepository = new RoleRepository();
	}
	public static  UserDto login(String email, String password) {
				// Gọi hàm truy vấn lấy dữ liệu
		User user = UserRepository.findByEmail(email); // findAll trả về List<User>
		// Tham chiếu dữ liệu từ entity -> Dto
		if (email == null) 
			return null;
		if(!BCrypt.checkpw(password, user.getPassword())){
			return null;
		}
		Role role = roleRepository.findById(user.getRole_id());
		UserDto dto = new UserDto();
		dto.setId(user.getId());
		dto.setEmail(user.getEmail());
		dto.setFullname(user.getFullname());
		dto.setRoleName(role.getName());
		return dto;
		// Trả về dto
		
	
	}
}
