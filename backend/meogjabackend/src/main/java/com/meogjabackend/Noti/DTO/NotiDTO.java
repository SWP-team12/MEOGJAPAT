package com.meogjabackend.Noti.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor 
//�ڵ����� ��� �Ű������� �޴� �����ڸ� ����
@Getter 
//Getter ����
@Setter 
//Setter ����
public class NotiDTO {
	private int n_id;
	private int u_id;
	private int m_id;
	private String message;
}
