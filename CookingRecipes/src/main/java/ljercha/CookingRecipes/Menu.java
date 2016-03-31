package ljercha.CookingRecipes;

import com.vaadin.event.ShortcutAction.KeyCode;

public class Menu extends MenuDesign {

	
	private MyUI myUI;

	public Menu(MyUI myUI) {
	    this.myUI = myUI;

	    login.addClickListener(e->myUI.navigateToView(new Login()));
	    newRecipe.addClickListener(e->myUI.navigateToView(new NewRecipe()));
	}
}
