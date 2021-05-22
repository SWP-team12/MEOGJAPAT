package com.meogjabackend.Noti.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor 
//자동으로 모든 매개변수를 받는 생성자를 생성
@Getter 
//Getter 생성
@Setter 
//Setter 생성
public class NotiDTO {
	private int n_id;
	private int u_id;
	private int m_id;
	private String message;
}
