package com.meogjabackend.Meeting.Service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.meogjabackend.Meeting.DTO.MeetingDTO;
import com.meogjabackend.Meeting.Mapper.MeetingMapper;



@Service
public class MeetingServiceImpl implements MeetingService {
	@Autowired
	private MeetingMapper meetingMapper;
	
	/*��ü Meeting �ҷ�����*/
	public List<MeetingDTO> selectMeetingList() throws Exception {
		return meetingMapper.selectMeetingList();
	}

	/*Meeting ����*/
	public void insertMeeting(MeetingDTO meeting) throws Exception {
		meetingMapper.insertMeeting(meeting);
	}

	/*�� Meeting ���� ����*/
	public MeetingDTO selectMeetingDetail(int m_id) throws Exception {
		MeetingDTO meeting = meetingMapper.selectMeetingDetail(m_id);
		return meeting;
	}

	/*Meeting ����*/
	public void deleteMeeting(int m_id) throws Exception {
		meetingMapper.deleteMeeting(m_id);
	}
	
	/*status ����*/
	public void startMeeting(MeetingDTO meeting) throws Exception{
		meetingMapper.startMeeting(meeting);
	}
	
	/*������2�� ����*/
	public void joinMeeting2(MeetingDTO meeting) throws Exception{
		meetingMapper.joinMeeting2(meeting);
	}

	/*������3�� ����*/
	public void joinMeeting3(MeetingDTO meeting) throws Exception{
		meetingMapper.joinMeeting3(meeting);
	}

	/*������4�� ����*/
	public void joinMeeting4(MeetingDTO meeting) throws Exception{
		meetingMapper.joinMeeting4(meeting);
	}
}