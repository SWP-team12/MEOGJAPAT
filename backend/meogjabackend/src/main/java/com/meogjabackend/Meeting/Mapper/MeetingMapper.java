package com.meogjabackend.Meeting.Mapper;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.meogjabackend.Meeting.DTO.MeetingDTO;


@Mapper
public interface MeetingMapper {
	/*��ü Meeting �ҷ�����*/
	List<MeetingDTO> selectMeetingList() throws Exception;
	
	/*Meeting ����*/
	void insertMeeting(MeetingDTO meeting) throws Exception;
	
	/*�� Meeting ���� ����*/
	MeetingDTO selectMeetingDetail(int m_id) throws Exception;
	
	/*Meeting ����*/
	void deleteMeeting(int m_id) throws Exception;
	
	/*status ����*/
	void startMeeting(MeetingDTO meeting) throws Exception;
	
	/*������2�� ����*/
	void joinMeeting2(MeetingDTO meeting) throws Exception;
	
	/*������3�� ����*/
	void joinMeeting3(MeetingDTO meeting) throws Exception;
	
	/*������4�� ����*/
	void joinMeeting4(MeetingDTO meeting) throws Exception;
}