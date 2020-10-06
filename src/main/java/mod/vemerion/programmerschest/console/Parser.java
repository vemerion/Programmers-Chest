package mod.vemerion.programmerschest.console;

public class Parser {
	public Program parse(String s) {
		s = s.trim().toLowerCase();
		String[] args = s.split("\\p{Z}+");
		if (args.length == 0)
			return new InvalidProgram(args);
		else if (args[0].equals("help"))
			return new HelpProgram(args);
		else if (args[0].equals("inv"))
			return new InvProgram(args);
		else if (args[0].equals("exit"))
			return new ExitProgram(args);
		
		return new InvalidProgram(s.split(" "));
	}
}
