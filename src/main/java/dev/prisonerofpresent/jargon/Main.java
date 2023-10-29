package dev.prisonerofpresent.jargon;

import java.util.Map;
import java.util.HashMap;

@FunctionalInterface
interface JargonSubCommand {
	void process();
}

class JargonSubCommandInfo {
	//TODO :  generate proper getters and setters
	private final String name;
	private final String helpString;
	public final JargonSubCommand callback;

	public JargonSubCommandInfo(String subCommandName, String help, JargonSubCommand handler ) {
		name = subCommandName;
		helpString = help;
		callback = handler;
	}
}

class Jargon {
	private static Jargon INSTANCE = null;
	String[] args = null;
	String commandName = null;
	Map<String,JargonSubCommandInfo> subCommandMap = null;
	String prefix = null;

	private Jargon() {}

	public static void initialize(String[] commandLineArgs, String commandName,String prefix) {
		INSTANCE = new Jargon();
		INSTANCE.prefix = prefix;
		INSTANCE.args = commandLineArgs;
		INSTANCE.commandName = commandName;
		INSTANCE.subCommandMap = new HashMap<>();
	}

	public static Jargon getInstance() {
		return INSTANCE;
	}

	public static void processSubCommands() {
		// iterate the passed arguments.
		// then make sure that the proper subcommand is executed
		if ( INSTANCE.args == null || INSTANCE.args.length == 0  ) {
			INSTANCE.printUsage();
		} else {
			for ( String subcommand : INSTANCE.args ) {
				// we need to build some sort of map that helps with execution of this subcommand and it's definition.
				// For now we can just try hardcoding a help subcommand.
				// ANother thing to note is that some poeple like to use - as the start of their subcommand, some like to use --
				// So we should provide a way to define the prefix that they want to use.
				// We should be able to generate the help tree ourselves.
				// When the consumer of the library wants to add a subcmmand, they should provide the following things : 
				// Name of the subcommand, help string / description of the subcommand and the callback to the subcommand.
				// callback should be a function with no arguments.
				// We can define a functional interface, and make sure that interface is being used to define the method callbacks.
				// so the use will be able to pass in anonymous functions ( lambdas )
				if ( INSTANCE.subCommandMap.containsKey(subcommand) ){
					INSTANCE.subCommandMap.get(subcommand).callback.process();
				} else {
					System.out.println("Unknown sub command " + subcommand);
				}
		}
		}
	}

	public void printUsage() {
		System.out.println("Usage : " + commandName + " <args> ("+ prefix + "help for help)." );
	}

	public static void addSubCommand( String name, String help, JargonSubCommand callback ) {
		JargonSubCommandInfo info = new JargonSubCommandInfo(INSTANCE.prefix + name,help,callback);
		INSTANCE.subCommandMap.putIfAbsent(INSTANCE.prefix + name, info);
	}
}


public class Main{

	public static void main(String[] args) {
		Jargon.initialize(args,"jargon-cli","--");
		Jargon.addSubCommand("help","Prints help information",() -> System.out.println("Calling for help!"));
		Jargon.processSubCommands();
	}
}
