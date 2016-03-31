package ljercha.CookingRecipes;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.UserError;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;

public class Register extends RegisterFormDesign implements Button.ClickListener {

	
	private MyUI myUI;

	public Register(MyUI myUI) {
	    this.myUI = myUI;
	    

	    login.addValidator(new StringLengthValidator("The login must be 4-10 letters ( {0})",4, 10, true));
	    email.addValidator(new EmailValidator("The name must be 1-10 letters (was {0})"));
	    login.setValidationVisible(true);
	    email.setValidationVisible(true);
	    password.setValidationVisible(true);
	    
	    password.addValidator(new StringLengthValidator("The name must be minimum 8 letters",8, 20, true));
   
	    register.addClickListener(this);
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		
		if(!(login.isValid() && email.isValid() && password.isValid()))
			return;
		if(UserService.getInstance().findAll(login.getValue()) != null) {
			login.setComponentError(new UserError("The user already exist!"));
			return;
		}
		
		User c = new User();
		c.setLogin(login.getValue());
		c.setEmail(email.getValue());
		c.setPassword(password.getValue());
		UserService.getInstance().save(c);
		
		getSession().setAttribute("user", null);
		this.myUI.navigateToView(new AllRecipes(this.myUI));		
	}
}
