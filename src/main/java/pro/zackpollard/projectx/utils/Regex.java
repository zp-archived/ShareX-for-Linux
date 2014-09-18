package pro.zackpollard.projectx.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author DarkSeraphim
 */
@RequiredArgsConstructor
public class Regex {

	@Getter
	private final Pattern regex;

	@Getter
	private final String replacement;

	public String replace(String response) {
		Matcher m = regex.matcher(response);
		if (!m.matches()) {
			return response;
		}
		return m.replaceAll(this.replacement);
	}
}