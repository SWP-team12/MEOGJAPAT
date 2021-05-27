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
	
	/* 알림생성 */
	@RequestMapping(value="/notification", method=RequestMethod.POST)
	public List<NotiDTO> makenoti(@RequestBody CreateNotification notification) throws Exception { //query String로 sns_id를 받도록 설정
		NotiDTO param = new NotiDTO(0, notification.u_id, notification.m_id, notification.message);//전달 받은 nickname을 받은 UserDTO 객체 생성 이 객체는 MyBatis에 파라미터로 전달
		List<NotiDTO> userList = notiDAO.makenoti(param);// 생성한 객체를 파라미터로 전달하여 DB로부터 사용자 목록을 불러온다.
		return userList;
	}
	
	/* 알림목록 */
	//query String로 u_id를 받도록 설정
	@RequestMapping(value="/notification", method=RequestMethod.GET)
	public List<NotiDTO> shownoti(@RequestParam(value="u_id") int u_id) throws Exception { 
		// 전달 받은 nickname을 받은 UserDTO 객체 생성, 이 객체는 MyBatis에 파라미터로 전달
		NotiDTO param = new NotiDTO(0, u_id, 0, "");
		List<NotiDTO> userList = notiDAO.shownoti(param);// 생성한 객체를 파라미터로 전달하여 DB로부터 사용자 목록을 불러온다.
		return userList;
	}
}
