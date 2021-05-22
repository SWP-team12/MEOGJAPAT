package com.meogjabackend.User.controller;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.meogjabackend.User.DAO.UserDAO;
import com.meogjabackend.User.DTO.InfoDTO;
import com.meogjabackend.User.DTO.UidDTO;
import com.meogjabackend.User.DTO.UserDTO;

class CreateUser {
	public String nickname;
	public String account;
	public int u_age;
	public String sns_id;
}

class UpdateUser {
	public String nickname;
	public String account;
	public String action;
	public boolean run;
	public boolean rude;
}

@RestController
@MapperScan(basePackages="com.meogjabackend.User.DAO")//Ž���� ��Ű�� ����
public class UserController {
	@Autowired
	private UserDAO userDAO;//UserDAO bean�� �ڵ����� ����
	
	/* ȸ������ */
	@RequestMapping(value="/user", method=RequestMethod.POST)
	public UidDTO signup(@RequestBody CreateUser user) throws Exception {
		UserDTO param = new UserDTO(0, user.nickname, user.u_age, user.account, user.sns_id, 0, 0);
		userDAO.signup(param);
		List<UidDTO> userList = userDAO.selectUsers(param);
		return userList.get(0);
	}
	
	/* sns_id�� ȸ�� �˻� */
	@RequestMapping(value ="/user", method=RequestMethod.GET)
	public UidDTO users(@RequestParam(value="sns_id", defaultValue = "") String sns_id) throws Exception { //query String�� sns_id�� �޵��� ����
		//���� ���� sns_id�� ���� UserDTO ��ü ���� �� ��ü�� MyBatis�� �Ķ���ͷ� ����
		UserDTO param = new UserDTO( 0, "0", 0, "0", sns_id, 0, 0);
		
		// ������ ��ü�� �Ķ���ͷ� �����Ͽ� DB�κ��� ����� ����� �ҷ��´�.
		List<UidDTO> userList = userDAO.selectUsers(param);
		
		// DB���� �ҷ��� ���� ���ٸ� 404 Error�� ������.
		if (userList.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found");
		
		return userList.get(0);
	}
	
	
	/* ����� ���� �б� */
	@RequestMapping(value="/user/{u_id}",method=RequestMethod.GET)
	public InfoDTO info(@PathVariable(value="u_id") int u_id) throws Exception { 
		UserDTO param = new UserDTO( u_id, "0", 0, "0", "0", 0, 0);
		List<InfoDTO> userList = userDAO.infoUser(param);
		
		if (userList.isEmpty()) throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No user found");
		
		return userList.get(0);
	}
	 
    /* ����� ���� ���� */
	@RequestMapping(value = "/user/{u_id}", method=RequestMethod.PUT)
	public void modify(@PathVariable("u_id") int u_id, @RequestBody UpdateUser user) throws Exception {
		if (user.action.equals("report"))  {
			
			int run = user.run ? 1 : 0;
			int rude = user.rude ? 1 : 0;
			
			UserDTO param = new UserDTO(u_id, "0", 0, "0", "0", run, rude);
		    userDAO.reportUser(param);
		} 
		else {
			System.out.print(user.action);
			System.out.print("change");
			UserDTO param = new UserDTO(u_id, user.nickname, 0, user.account, "0", 0, 0);
			userDAO.modifyUser(param);
		}
	}
}