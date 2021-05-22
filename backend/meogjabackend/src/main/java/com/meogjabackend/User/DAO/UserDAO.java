package com.meogjabackend.User.DAO;


import java.util.List;

import com.meogjabackend.User.DTO.InfoDTO;
import com.meogjabackend.User.DTO.UidDTO;
import com.meogjabackend.User.DTO.UserDTO;

public interface UserDAO {
	List<UidDTO> selectUsers(UserDTO param) throws Exception;
	
	List<InfoDTO> infoUser(UserDTO param) throws Exception;
	
	List<UidDTO> signup(UserDTO param) throws Exception;
	
	List<UserDTO> reportUser(UserDTO param) throws Exception;
	
	List<UserDTO> modifyUser(UserDTO param) throws Exception;
}