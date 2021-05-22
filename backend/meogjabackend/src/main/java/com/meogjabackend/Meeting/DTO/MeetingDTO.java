package com.meogjabackend.Meeting.DTO;



import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDateTime;

@AllArgsConstructor
@Data
public class MeetingDTO {
	
	private int m_id;

	private int area;
	
	private String menu;

	private String place;

	private LocalDateTime time;

	private int m_number;
	
	private boolean amity;

	private int m_age;
	
	private int status;

	private int participant_2;	
	
	private int participant_3;

	private int participant_4;	
	
	private int u_id;

}