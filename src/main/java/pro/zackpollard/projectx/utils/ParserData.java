package pro.zackpollard.projectx.utils;

import lombok.Getter;

import java.util.Collections;
import java.util.List;

/**
 * @author DarkSeraphim
 */
public class ParserData<Result> {
	@Getter
	private final Result result;

	@Getter
	private final List<String> warnings;

	@Getter
	private final List<Exception> exceptions;

	public ParserData(Result result, List<String> warnings, List<Exception> exceptions) {
		this.result = result;
		this.warnings = Collections.unmodifiableList(warnings != null ? warnings : Collections.<String>emptyList());
		this.exceptions = Collections.unmodifiableList(exceptions != null ? exceptions : Collections.<Exception>emptyList());
	}

	public boolean wasSuccessful() {
		return this.result != null && this.exceptions.isEmpty();
	}

	public boolean hasWarnings() {
		return !this.warnings.isEmpty();
	}
}
