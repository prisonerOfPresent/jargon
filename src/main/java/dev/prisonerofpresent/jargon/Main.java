package dev.prisonerofpresent.jargon;

public class Main{

	public static void main(String[] args) {
		Jargon.initialize(args,"jargon-cli","--");
		Jargon.addSubCommand("build","Fake build command",() -> System.out.println("I just built a CLI tool from scratch!"));
		Jargon.processSubCommands();
	}
}
