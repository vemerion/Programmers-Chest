package mod.vemerion.programmerschest.computer;

import mod.vemerion.programmerschest.computer.program.ExitProgram;
import mod.vemerion.programmerschest.computer.program.HelpProgram;
import mod.vemerion.programmerschest.computer.program.InvProgram;
import mod.vemerion.programmerschest.computer.program.InvalidProgram;
import mod.vemerion.programmerschest.computer.program.LsProgram;
import mod.vemerion.programmerschest.computer.program.MkdirProgram;
import mod.vemerion.programmerschest.computer.program.Program;

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
		else if (args[0].equals("ls"))
			return new LsProgram(args);
		else if (args[0].equals("mkdir"))
			return new MkdirProgram(args);
		
		return new InvalidProgram(s.split(" "));
	}
}
