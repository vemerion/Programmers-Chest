package mod.vemerion.programmerschest.console;

public abstract class Program {
	
	protected String[] args;
	
	public Program(String[] args) {
		this.args = args;
	}

	public abstract boolean isClientOnlyProgram();

	public abstract void run(Console console);

}
