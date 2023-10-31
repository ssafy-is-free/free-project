package com.ssafy.backend.domain.util.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;

//@Component
@RequiredArgsConstructor
public class NotificationManager {
	private Logger log = LoggerFactory.getLogger(NotificationManager.class);
	private final MatterMostSender mmSender;

	public void sendNotification(Exception e, String uri, String params) {
		log.info("#### SEND Notification");
		mmSender.sendMessage(e, uri, params);
	}

}