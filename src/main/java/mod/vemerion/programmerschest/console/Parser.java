package mod.vemerion.programmerschest.console;

public class Parser {
	public Program parse(String s) {
		s = s.trim().toLowerCase();
		String[] args = s.split("\\p{Z}+");
		if (args.length == 0)
			return new InvalidProgram(s.split(" "));
		else if (args[0].equals("help"))
			return new HelpProgram(args);
		
		return new InvalidProgram(s.split(" "));
	}
}
