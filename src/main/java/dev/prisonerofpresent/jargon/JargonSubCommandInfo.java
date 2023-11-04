package dev.prisonerofpresent.jargon;

public class JargonSubCommandInfo {
	private final String name;
	private final String helpString;
	private final JargonSubCommand callback;

	public JargonSubCommandInfo(String subCommandName, String help, JargonSubCommand handler ) {
		name = subCommandName;
		helpString = help;
		callback = handler;
	}

	public String getName() {
		return name;
	}

	public String getHelpString() {
		return helpString;
	}

	public JargonSubCommand getCallback() {
		return callback;
	}

}































