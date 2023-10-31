

package com.modak;

import com.modak.entity.Notification;
import com.modak.entity.NotificationTypeEnum;
import com.modak.repository.NotificationRepository;
import com.modak.service.NotificationServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@SpringBootTest
class ModakApplicationTests {

	@MockBean
	private NotificationRepository notificationRepositoryMock;

	@Test
	@DisplayName("Test if Status message is not sent more than 2 per minute for each recipient")
	void test_status_limit() throws Exception {

		ArrayList myList1 = new ArrayList();
		ArrayList myList2 = new ArrayList();
		ArrayList myList3 = new ArrayList();

		Notification notification1 = new Notification();
		notification1.setCreationDate(LocalDateTime.now());
		notification1.setMessage("status1");
		notification1.setType(NotificationTypeEnum.STATUS);
		notification1.setUserId("user");

		Notification notification2 = new Notification();
		notification2.setCreationDate(LocalDateTime.now().plusSeconds(1l));
		notification2.setMessage("status2");
		notification2.setType(NotificationTypeEnum.STATUS);
		notification2.setUserId("user");

		myList2.add(notification1);

		myList3.add(notification1);
		myList3.add(notification2);

		Optional<ArrayList<Notification>> myOptionalList1 = Optional.of(myList1);
		Optional<ArrayList<Notification>> myOptionalList2 = Optional.of(myList2);
		Optional<ArrayList<Notification>> myOptionalList3 = Optional.of(myList3);

		Gateway gateway = new Gateway();
		Gateway gatewaySpy = spy(gateway);
		NotificationServiceImpl notificationServiceImpl = new NotificationServiceImpl(notificationRepositoryMock, gatewaySpy);

		Mockito.when(notificationRepositoryMock.findTodayUserTypeMessage(any(), any(), any()))
				.thenReturn(myOptionalList1)
				.thenReturn(myOptionalList2)
				.thenReturn(myOptionalList3)
				.thenReturn(myOptionalList3)
				.thenReturn(myOptionalList3);

		notificationServiceImpl.send("status", "user", "update 1 status");
		notificationServiceImpl.send("status", "user", "update 2 status");
		notificationServiceImpl.send("status", "user", "update 3 status");
		notificationServiceImpl.send("status", "user", "update 4 status");
		notificationServiceImpl.send("status", "user", "update 5 status");

		verify(gatewaySpy, times(2)).send(any(),any());

	}

	/*
	@Test
	@DisplayName("Test if Status message sent  2 per minute for each recipient")
	void test_status_limit2() throws Exception {

		ArrayList myList1 = new ArrayList();
		ArrayList myList2 = new ArrayList();
		ArrayList myList3 = new ArrayList();

		Notification notification1 = new Notification();
		notification1.setCreationDate(LocalDateTime.now());
		notification1.setMessage("status1");
		notification1.setType(NotificationTypeEnum.STATUS);
		notification1.setUserId("user");

		Notification notification2 = new Notification();
		notification2.setCreationDate(LocalDateTime.now().plusSeconds(1l));
		notification2.setMessage("status2");
		notification2.setType(NotificationTypeEnum.STATUS);
		notification2.setUserId("user");

		myList2.add(notification1);

		myList3.add(notification1);
		myList3.add(notification2);

		Optional<ArrayList<Notification>> myOptionalList1 = Optional.of(myList1);
		Optional<ArrayList<Notification>> myOptionalList2 = Optional.of(myList2);
		Optional<ArrayList<Notification>> myOptionalList3 = Optional.of(myList3);

		Gateway gateway = new Gateway();
		Gateway gatewaySpy = spy(gateway);
		NotificationServiceImpl notificationServiceImpl = new NotificationServiceImpl(notificationRepositoryMock, gatewaySpy);

		Mockito.when(notificationRepositoryMock.findTodayUserTypeMessage(any(), any(), any()))
				.thenReturn(myOptionalList1)
				.thenReturn(myOptionalList2)
				.thenReturn(myOptionalList3);

		notificationServiceImpl.send("status", "user", "update 1 status");
		notificationServiceImpl.send("status", "user", "update 2 status");
		notificationServiceImpl.send("status", "user", "update 3 status");

		TimeUnit.SECONDS.sleep(61);

		ArrayList myList4 = new ArrayList();
		ArrayList myList5 = new ArrayList();
		ArrayList myList6 = new ArrayList();

		Notification notification3 = new Notification();
		notification3.setCreationDate(LocalDateTime.now());
		notification3.setMessage("status3");
		notification3.setType(NotificationTypeEnum.STATUS);
		notification3.setUserId("user");

		Notification notification4 = new Notification();
		notification4.setCreationDate(LocalDateTime.now().plusSeconds(1l));
		notification4.setMessage("status4");
		notification4.setType(NotificationTypeEnum.STATUS);
		notification4.setUserId("user");

		myList5.add(notification3);

		myList6.add(notification3);
		myList6.add(notification4);

		Optional<ArrayList<Notification>> myOptionalList4 = Optional.of(myList4);
		Optional<ArrayList<Notification>> myOptionalList5 = Optional.of(myList5);
		Optional<ArrayList<Notification>> myOptionalList6 = Optional.of(myList6);

		Mockito.when(notificationRepositoryMock.findTodayUserTypeMessage(any(), any(), any()))
				.thenReturn(myOptionalList4)
				.thenReturn(myOptionalList5)
				.thenReturn(myOptionalList6);

		notificationServiceImpl.send("status", "user", "update 4 status");
		notificationServiceImpl.send("status", "user", "update 5 status");
		notificationServiceImpl.send("status", "user", "update 6 status");

		verify(gatewaySpy, times(4)).send(any(),any());

	}

	 */

	@Test
	@DisplayName("Test if News: not more than 1 per day for each recipient")
	void test_news_limit() throws Exception {

		ArrayList myList1 = new ArrayList();
		ArrayList myList2 = new ArrayList();
		ArrayList myList3 = new ArrayList();

		Notification notification1 = new Notification();
		notification1.setCreationDate(LocalDateTime.now());
		notification1.setMessage("status1");
		notification1.setType(NotificationTypeEnum.NEWS);
		notification1.setUserId("user");

		Notification notification2 = new Notification();
		notification2.setCreationDate(LocalDateTime.now());
		notification2.setMessage("status2");
		notification2.setType(NotificationTypeEnum.NEWS);
		notification2.setUserId("user");

		myList2.add(notification1);

		myList3.add(notification1);
		myList3.add(notification2);

		Optional<ArrayList<Notification>> myOptionalList1 = Optional.of(myList1);
		Optional<ArrayList<Notification>> myOptionalList2 = Optional.of(myList2);
		Optional<ArrayList<Notification>> myOptionalList3 = Optional.of(myList3);

		Gateway gateway = new Gateway();
		Gateway gatewaySpy = spy(gateway);
		NotificationServiceImpl notificationServiceImpl = new NotificationServiceImpl(notificationRepositoryMock, gatewaySpy);

		Mockito.when(notificationRepositoryMock.findTodayUserTypeMessage(any(), any(), any()))
				.thenReturn(myOptionalList1)
				.thenReturn(myOptionalList2)
				.thenReturn(myOptionalList3)
				.thenReturn(myOptionalList3)
				.thenReturn(myOptionalList3);

		notificationServiceImpl.send("news", "user", "update 1 news");
		notificationServiceImpl.send("news", "user", "update 2 news");
		notificationServiceImpl.send("news", "user", "update 3 news");
		notificationServiceImpl.send("news", "user", "update 4 news");
		notificationServiceImpl.send("news", "user", "update 5 news");

		verify(gatewaySpy, times(1)).send(any(),any());
	}

	@Test
	@DisplayName("Test if Marketing: not more than 3 per hour for each recipient")
	void test_Marketing_limit() throws Exception {

		ArrayList myList1 = new ArrayList();
		ArrayList myList2 = new ArrayList();
		ArrayList myList3 = new ArrayList();
		ArrayList myList4 = new ArrayList();

		Notification notification1 = new Notification();
		notification1.setCreationDate(LocalDateTime.now());
		notification1.setMessage("status1");
		notification1.setType(NotificationTypeEnum.MARKETING);
		notification1.setUserId("user");

		Notification notification2 = new Notification();
		notification2.setCreationDate(LocalDateTime.now());
		notification2.setMessage("status2");
		notification2.setType(NotificationTypeEnum.MARKETING);
		notification2.setUserId("user");

		Notification notification3 = new Notification();
		notification3.setCreationDate(LocalDateTime.now());
		notification3.setMessage("status3");
		notification3.setType(NotificationTypeEnum.MARKETING);
		notification3.setUserId("user");

		myList2.add(notification1);

		myList3.add(notification1);
		myList3.add(notification2);

		myList4.add(notification1);
		myList4.add(notification2);
		myList4.add(notification3);

		Optional<ArrayList<Notification>> myOptionalList1 = Optional.of(myList1);
		Optional<ArrayList<Notification>> myOptionalList2 = Optional.of(myList2);
		Optional<ArrayList<Notification>> myOptionalList3 = Optional.of(myList3);
		Optional<ArrayList<Notification>> myOptionalList4 = Optional.of(myList4);

		Gateway gateway = new Gateway();
		Gateway gatewaySpy = spy(gateway);
		NotificationServiceImpl notificationServiceImpl = new NotificationServiceImpl(notificationRepositoryMock, gatewaySpy);

		Mockito.when(notificationRepositoryMock.findTodayUserTypeMessage(any(), any(), any()))
				.thenReturn(myOptionalList1)
				.thenReturn(myOptionalList2)
				.thenReturn(myOptionalList3)
				.thenReturn(myOptionalList4)
				.thenReturn(myOptionalList4);

		notificationServiceImpl.send("marketing", "user", "update 1 marketing");
		notificationServiceImpl.send("marketing", "user", "update 2 marketing");
		notificationServiceImpl.send("marketing", "user", "update 3 marketing");
		notificationServiceImpl.send("marketing", "user", "update 4 marketing");
		notificationServiceImpl.send("marketing", "user", "update 5 marketing");

		verify(gatewaySpy, times(3)).send(any(),any());
	}

	@Test
	@DisplayName("Test if wrongType is provided. No message should be sent")
	void test_wrongType() throws Exception {
		Gateway gateway = new Gateway();
		Gateway gatewaySpy = spy(gateway);
		NotificationServiceImpl notificationServiceImpl = new NotificationServiceImpl(notificationRepositoryMock, gatewaySpy);

		notificationServiceImpl.send("wrongType", "user", "update 1 marketing");
		notificationServiceImpl.send("anotherWrongType", "user", "update 2 marketing");

		verify(gatewaySpy, times(0)).send(any(),any());
	}

}
