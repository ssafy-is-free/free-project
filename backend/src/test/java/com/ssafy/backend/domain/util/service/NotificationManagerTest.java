package com.ssafy.backend.domain.util.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.ssafy.backend.global.response.exception.CustomException;
import com.ssafy.backend.global.response.exception.CustomExceptionStatus;

@SpringBootTest
public class NotificationManagerTest {

	@Autowired
	private NotificationManager notificationManager;

	@Test
	@DisplayName("메타모스트 알림 정상 작동 테스트")
	public void notificationTest() {
		notificationManager.sendNotification(new CustomException(CustomExceptionStatus.NOT_FOUND_USER), "/test",
			"testParams");
	}

}
