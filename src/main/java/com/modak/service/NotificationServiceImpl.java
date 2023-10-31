package com.modak.service;

import com.modak.Gateway;
import com.modak.entity.Notification;
import com.modak.entity.NotificationTypeEnum;
import com.modak.repository.NotificationRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NotificationServiceImpl implements NotificationService {

	private final NotificationRepository notificationRepository;
	private final Gateway gateway;

	public NotificationServiceImpl(NotificationRepository notificationRepository, Gateway gateway ) {
		this.notificationRepository = notificationRepository;
		this.gateway = gateway;
	}

	@Override
	public void send(String type, String userId, String message) {

		Optional<NotificationTypeEnum> notificationTypeEnum = NotificationTypeEnum.getNotificationType(type);
		if (notificationTypeEnum.isEmpty() ) return;

		switch (notificationTypeEnum.get()){
			case MARKETING:{
				sendMarketingMessage( type,  userId,  message);
				break;
			}
			case STATUS:{
				sendStatusMessage( type,  userId,  message);
				break;
			}
			case NEWS:{
				sendNewsMessage( type,  userId,  message);
				break;
			}
			case UPDATE:{
				sendUpdateMessage( type,  userId,  message);
				break;
			}
		}
	}

	private void sendMarketingMessage(String type, String userId, String message) {
		Optional<ArrayList<Notification>> queryResult = notificationRepository.findTodayUserTypeMessage(
				userId, NotificationTypeEnum.MARKETING, LocalDateTime.now());
		if (queryResult.isEmpty()){
			gateway.send(userId, message);
		} else {
			//Marketing: not more than 3 per hour for each recipient
			if (countMarketingMessages(queryResult) < 3) {
				gateway.send(userId, message);
			}
		}
		saveMessage(userId,type,message);
	}

	private void sendUpdateMessage(String type, String userId, String message) {
		gateway.send(userId, message);
		saveMessage(userId,type,message);
	}

	private void saveMessage(String userId, String type, String message) {
		Notification notification = new Notification();
		notification.setCreationDate(LocalDateTime.now());
		notification.setMessage(message);
		notification.setType(NotificationTypeEnum.valueOf(type.toUpperCase()));
		notification.setUserId(userId);
		notificationRepository.save(notification);
	}

	private void sendStatusMessage(String type, String userId, String message) {
		Optional<ArrayList<Notification>> queryResult = notificationRepository.findTodayUserTypeMessage(
				userId, NotificationTypeEnum.STATUS, LocalDateTime.now());
		if (queryResult.isEmpty()){
			gateway.send(userId, message);
		} else {
			//Status: not more than 2 per minute for each recipient
			if (countStatusMessages(queryResult) < 2) {
				gateway.send(userId, message);
			}
		}
		saveMessage(userId,type,message);
	}

	private Integer countStatusMessages(Optional<ArrayList<Notification>> queryResult) {
		return queryResult.get().stream().filter(notification -> notification.getCreationDate().getHour() == LocalDateTime.now().getHour()
				&& notification.getCreationDate().getMinute() == LocalDateTime.now().getMinute() ).collect(Collectors.reducing(0, e -> 1, Integer::sum));
	}

	private Integer countMarketingMessages(Optional<ArrayList<Notification>> queryResult) {
		return queryResult.get().stream().filter(notification -> notification.getCreationDate().getHour() == LocalDateTime.now().getHour() )
				.collect(Collectors.reducing(0, e -> 1, Integer::sum));
	}

	private void sendNewsMessage(String type, String userId, String message) {
		Optional<ArrayList<Notification>> queryResult = notificationRepository.findTodayUserTypeMessage(
				userId, NotificationTypeEnum.NEWS, LocalDateTime.now());

		if (queryResult.isEmpty()){
			gateway.send(userId, message);
		} else {
			//News: not more than 1 per day for each recipient
			if (queryResult.get().size() < 1) {
				gateway.send(userId,message);
			}
		}
		saveMessage(userId,type,message);

	}
}


