package com.meogjabackend.User.DTO;



import lombok.AllArgsConstructor;

import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor 
// 자동으로 모든 매개변수를 받는 생성자를 생성
@Getter 
// Getter 생성
@Setter 
// Setter 생성
public class UserDTO {
    private int u_id;
    private String nickname;
    private int u_age;
    private String account;
    private String sns_id;
    private int run_count;
    private int rude_count;  
}
