package ljercha.CookingRecipes;

import com.vaadin.event.ShortcutAction.KeyCode;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class Menu extends MenuDesign implements Button.ClickListener {

	
	private MyUI myUI;

	public Menu(MyUI myUI) {
	    this.myUI = myUI;

	    login.addClickListener(e->myUI.navigateToView(new Login(myUI)));
	    logout.addClickListener(this);
	    newRecipe.addClickListener(e->myUI.navigateToView(new NewRecipe(myUI)));
	    
	}
	
	public void updateMenu() {
		if(getSession() != null && getSession().getAttribute("user") != null)
		{
			newRecipe.setVisible(true);
			login.setVisible(false);
			logout.setVisible(true);
			loginLabel.setValue("Welcome back " + getSession().getAttribute("user") + " :)");
			loginLabel.setVisible(true);
		}
		else {

			newRecipe.setVisible(false);
			login.setVisible(true);
			logout.setVisible(false);
			loginLabel.setVisible(false);
		}
	}
	
	
	@Override
	public void buttonClick(ClickEvent event) {
		getSession().setAttribute("user", null);

		this.myUI.navigateToView(new AllRecipes(this.myUI));		
	}
}
