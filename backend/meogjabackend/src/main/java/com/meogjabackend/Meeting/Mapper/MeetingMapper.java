package com.meogjabackend.Meeting.Mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.meogjabackend.Meeting.DTO.MeetingDTO;


@Mapper
public interface MeetingMapper {
	/*전체 Meeting 불러오기*/
	List<MeetingDTO> selectMeetingList() throws Exception;
	
	/*Meeting 생성*/
	void insertMeeting(MeetingDTO meeting) throws Exception;
	
	/*상세 Meeting 정보 보기*/
	MeetingDTO selectMeetingDetail(int m_id) throws Exception;
	
	/*Meeting 삭제*/
	void deleteMeeting(int m_id) throws Exception;
	
	/*status 변경*/
	void startMeeting(MeetingDTO meeting) throws Exception;
	
	/*참가자2로 참가*/
	void joinMeeting2(MeetingDTO meeting) throws Exception;
	
	/*참가자3로 참가*/
	void joinMeeting3(MeetingDTO meeting) throws Exception;
	
	/*참가자4로 참가*/
	void joinMeeting4(MeetingDTO meeting) throws Exception;
}