package command;

public interface Command {
	void execute();
	default boolean canExecute() {
		return true;
	}
}
