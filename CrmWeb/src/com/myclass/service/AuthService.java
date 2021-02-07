package com.myclass.service;

import javax.swing.JButton;

import org.mindrot.jbcrypt.BCrypt;

import com.myclass.dto.UserDto;
import com.myclass.entity.User;
import com.myclass.repository.UserRepository;

public class AuthService {
	
	private UserRepository userRepository;
	
	public AuthService() {
		userRepository = new UserRepository();
	}

	public UserDto login(String email, String password) {
		// B2. TRUY VẤN DB KIỂM TRA EMAIL
		// TH1: Email không tồn tại => Xuất thông báo cho người dùng
		// TH2: Email chính xác => Chuyển qua bước 3
		User user = userRepository.findByEmail(email);
		if(user == null) return null;

		// B3. SO SÁNH KIỂM TRA MẬT KHẨU
		// BCrypt.checkpw(mật khẩu form, mật khẩu db) => true/false
		// TH1: Mật khẩu không khớp => Xuất thông báo cho người dùng
		// TH2: Mật khẩu chính xác => Chuyển qua bước 4
		
		if(BCrypt.checkpw(password, user.getPassword())) {
		UserDto dto = new UserDto();
		
		dto.setId(user.getId());
		dto.setEmail(user.getEmail());
		dto.setPassword(user.getPassword());
		dto.setFullname(user.getFullname());
		dto.setRoleId(user.getRoleId());
		return dto;
		}
		return null;
	}
}
