package states;

import views.TutorialView;

public abstract class TutorialState {
	protected TutorialView tutorialView;

	public TutorialState(TutorialView tutorialView) {
		super();
		this.tutorialView = tutorialView;
	}
	
	public abstract void nextState();
	public abstract void prevState();
	public abstract void getStateInfo();
}
