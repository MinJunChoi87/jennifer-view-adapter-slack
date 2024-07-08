package com.aries.slack;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import com.aries.extension.data.EventData;
import com.aries.extension.handler.EventHandler;
import com.aries.extension.util.LogUtil;
import com.aries.slack.entity.SlackProp;
import com.aries.slack.util.ConfUtil;
import com.aries.slack.util.SlackClient;

import com.aries.slack.entity.SlackData;

/**
 * The main logic for the extension
 *
 */
public class SlackAdapter implements EventHandler{

	/**
	 * Format the date and time
	 */
	private SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

	public void on(EventData[] eventData) {
//		SlackProp slackProperties = ConfUtil._getSlackProperties();
		SlackProp slackProperties = ConfUtil.getSlackProperties();

		for (EventData event : eventData) {
			String message = getBody(event);
			String pretext = getPreText(event);

			SlackData slackMessage = new SlackData(slackProperties, message, pretext, event);
			String result = new SlackClient(slackMessage).push().trim();
			if(!result.trim().equalsIgnoreCase("ok"))
				LogUtil.error("Failed to push message to Slack");
		}
	}

	private String getBody(EventData event){
		StringBuilder messageBody = new StringBuilder();
		messageBody.append(String.format("대상: %s%n", event.instanceName));
		messageBody.append(String.format("이벤트 메시지: %s%n", event.errorType));
		messageBody.append(String.format("등급: %s%n", event.eventLevel));
		messageBody.append(String.format("시간: %s```%n", sdf.format(new Date(event.time))));
		return messageBody.toString();
	}
	
	private String getPreText(EventData event) {
		StringBuilder pretext = new StringBuilder();
		pretext.append(String.format("[%s]",  event.instanceName));
		pretext.append(String.format("[%s]",  event.eventLevel));
		pretext.append(String.format("[%s] 이벤트 발생 알림 %n",  event.errorType));
		pretext.append("상세내역\n");
		return pretext.toString();
	}

	public static void main(String[] args) {
		EventData event = new EventData((short) 1004, new ArrayList<String>(), "제니퍼", System.currentTimeMillis(), 1000, "Groupware", "", "SERVICE_EXCEPTION", "", "FATAL", "", -1, "SYSTEM", "", "/service.jsp", -123123123, "", null);
		new SlackAdapter().on(new EventData[] { event });
	}
}
