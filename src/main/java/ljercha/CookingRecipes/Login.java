package ljercha.CookingRecipes;

import java.util.List;

import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.UserError;

public class Login extends LoginFromDesign {

	private MyUI myUI;

	public Login(MyUI myUI) {
	    this.myUI = myUI;	    
	    
	    registerButton.addClickListener(e->myUI.navigateToView(new Register(myUI)));
	    //newRecipe.addClickListener(e->myUI.navigateToView(new NewRecipe()));
	    loginButton.addClickListener(e->logIn());
   
	}
	
	public void logIn() {
		
		if (!login.isValid() || !password.isValid()) {
			return;
		}

		String login = this.login.getValue();
		String password = this.password.getValue();


		User user = UserService.getInstance().findAll(login);
		
		if(user == null) {
			this.login.setComponentError(new UserError("User not found!"));
			return;
		}

		if (password.equals(user.getPassword())) {

			getSession().setAttribute("user", login);

			this.myUI.navigateToView(new AllRecipes(this.myUI));
			
		} else {

			this.password.setComponentError(new UserError("Wrong password!"));

			// Wrong password clear the password field and refocuses it
			this.password.setValue("");
			this.password.focus();

		}
	}
}
	
