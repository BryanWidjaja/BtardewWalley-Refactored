package views;

import states.TutorialState;

public class TutorialView {
	private TutorialState state;

	public TutorialView(TutorialState state) {
		super();
		this.state = state;
	}

	public void setState(TutorialState state) {
		this.state = state;
	}
}
