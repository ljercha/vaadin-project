package ljercha.CookingRecipes;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button;


public class NewRecipe extends RecipeFormDesign implements Button.ClickListener {

	
	
	private MyUI myUI;

	public NewRecipe(MyUI myUI) {
	    this.myUI = myUI;

	    title.addValidator(new StringLengthValidator("The title must be between 4-40 letters ( {0})", 4, 40, false));
	    description.addValidator(new StringLengthValidator("The description must be between 10-255 letters ({0})", 10, 255, false));
	    title.setValidationVisible(true);
	    description.setValidationVisible(true);
	       
	    save.addClickListener(this);
	    

	    accessRecipe();
	}
	
	public void accessRecipe()
	{
    	delete.setVisible(false);    	
    	save.setVisible(false);
    	
	    if(VaadinSession.getCurrent() != null && owner.getValue() == null)
	    {
	    	save.setVisible(true);
	    } else if(owner.getValue() != null)
	    {
	    	if(VaadinSession.getCurrent() != null)
	    		if(VaadinSession.getCurrent().getAttribute("user") != null && VaadinSession.getCurrent().getAttribute("user").equals(owner.getValue()))
	    		{
		    	save.setVisible(true);
		    	delete.setVisible(true);
	    		}

	    }
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		
		if(!(title.isValid() && description.isValid()))
			return;
		
		
		Recipe c = new Recipe();
		c.setTitle(title.getValue());
		c.setDescription(description.getValue());
		c.setOwner((String)getSession().getAttribute("user"));

		RecipeService.getInstance().save(c);
		
		this.myUI.navigateToView(new AllRecipes(this.myUI));		
	}
}
