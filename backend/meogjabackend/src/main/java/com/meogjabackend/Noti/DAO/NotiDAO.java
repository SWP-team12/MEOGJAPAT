package com.meogjabackend.Noti.DAO;

import java.util.List;

import com.meogjabackend.Noti.DTO.NotiDTO;

public interface NotiDAO {
	
	List<NotiDTO> makenoti(NotiDTO param) throws Exception;
	
	List<NotiDTO> shownoti(NotiDTO param) throws Exception;

}
