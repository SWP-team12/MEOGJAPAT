package com.meogjabackend.User.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor 
//�ڵ����� ��� �Ű������� �޴� �����ڸ� ����
@Getter 
//Getter ����
@Setter 
//Setter ����
public class InfoDTO {
    private String nickname;
    private String account;
    private int u_age;
}
