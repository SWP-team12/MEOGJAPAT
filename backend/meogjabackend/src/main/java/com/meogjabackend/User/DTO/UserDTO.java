package com.meogjabackend.User.DTO;



import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor 
// �ڵ����� ��� �Ű������� �޴� �����ڸ� ����
@Getter 
// Getter ����
@Setter 
// Setter ����
public class UserDTO {
    private int u_id;
    private String nickname;
    private int u_age;
    private String account;
    private String sns_id;
    private int run_count;
    private int rude_count;  
}
