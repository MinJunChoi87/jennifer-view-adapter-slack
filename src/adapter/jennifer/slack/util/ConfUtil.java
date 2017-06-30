package adapter.jennifer.slack.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

import com.jennifersoft.view.adapter.util.LogUtil;
import com.jennifersoft.view.config.ConfigValue;
import adapter.jennifer.slack.entity.SlackProp;
import com.jennifersoft.view.extension.util.PropertyUtil;

/**
 * Load adapter configuration
 */
public class ConfUtil {

	/**
	 * The adapter ID
	 */
	private static final String ADAPTER_ID = "slack";
	
	/**
	 * Get a configuration value using the provided key
	 * @param key configuration key. Set this key value in the adapter configuration menu in JENNIFER client.
	 * @return String configuration value
	 */
	public static String getValue(String key){
		return PropertyUtil.getValue(ADAPTER_ID, key);
	}


	private static final SlackProp slackProperties = new SlackProp();
	/**
	 * Get the slack properties
	 * @return SlackProp slack properties
	 */
	public static SlackProp getSlackProperties() {
		slackProperties.setChannel(getValue("slack_channel"));
		slackProperties.setColor(getValue("message_color"));
		slackProperties.setIconEmoji(getValue("icon_emoji"));
		slackProperties.setUserName(getValue("slack_username"));
		slackProperties.setWebHookUrl(getValue("slack_webhook"));

		return  slackProperties;
	}
}
