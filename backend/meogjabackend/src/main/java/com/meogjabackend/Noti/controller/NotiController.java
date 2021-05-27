package com.meogjabackend.Noti.controller;

import java.util.List;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.meogjabackend.Noti.DAO.NotiDAO;
import com.meogjabackend.Noti.DTO.NotiDTO;

class CreateNotification {
	public int u_id;
	public int m_id;
	public String message;
}

@RestController
@MapperScan(basePackages="com.meogjabackend.Noti.DAO")
public class NotiController {
	@Autowired
	private NotiDAO notiDAO;
	
	/* �˸����� */
	@RequestMapping(value="/notification", method=RequestMethod.POST)
	public List<NotiDTO> makenoti(@RequestBody CreateNotification notification) throws Exception { //query String�� sns_id�� �޵��� ����
		NotiDTO param = new NotiDTO(0, notification.u_id, notification.m_id, notification.message);//���� ���� nickname�� ���� UserDTO ��ü ���� �� ��ü�� MyBatis�� �Ķ���ͷ� ����
		List<NotiDTO> userList = notiDAO.makenoti(param);// ������ ��ü�� �Ķ���ͷ� �����Ͽ� DB�κ��� ����� ����� �ҷ��´�.
		return userList;
	}
	
	/* �˸���� */
	//query String�� u_id�� �޵��� ����
	@RequestMapping(value="/notification", method=RequestMethod.GET)
	public List<NotiDTO> shownoti(@RequestParam(value="u_id") int u_id) throws Exception { 
		// ���� ���� nickname�� ���� UserDTO ��ü ����, �� ��ü�� MyBatis�� �Ķ���ͷ� ����
		NotiDTO param = new NotiDTO(0, u_id, 0, "");
		List<NotiDTO> userList = notiDAO.shownoti(param);// ������ ��ü�� �Ķ���ͷ� �����Ͽ� DB�κ��� ����� ����� �ҷ��´�.
		return userList;
	}
}
