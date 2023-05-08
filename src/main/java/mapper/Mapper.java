package mapper;

import org.springframework.stereotype.Component;

import com.web.clothes.ClothesWeb.dto.UserRequestDto;
import com.web.clothes.ClothesWeb.entity.User;

@Component
public class Mapper {
	public User userRquestDtoMapToUser(UserRequestDto userRequestDto) {
		User user = new User();
		user.setUserName(userRequestDto.getUsername());
        user.setPassword(userRequestDto.getPassword());
        user.setFullName(userRequestDto.getFullname());
        user.setAddress(userRequestDto.getAddress());
        user.setPhone(userRequestDto.getPhone());
        user.setEmail(userRequestDto.getEmail());
        return user;
	}
}
