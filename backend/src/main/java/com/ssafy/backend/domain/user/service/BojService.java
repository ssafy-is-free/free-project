package com.ssafy.backend.domain.user.service;

public interface BojService {

	//백준 아이디 저장
	void saveId(long userId, String bojId);

	//백준 아이디 중복 체크
	void checkDuplicateId(String bojId);
}
