package mod.vemerion.programmerschest.console;

public class HelpProgram extends Program {

	public HelpProgram(String[] args) {
		super(args);
	}

	@Override
	public boolean isClientOnlyProgram() {
		return true;
	}

	@Override
	public void run(Console console) {
		console.println("List of commands: help, ");
	}

}
