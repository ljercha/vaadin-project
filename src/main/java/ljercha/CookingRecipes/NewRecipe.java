package ljercha.CookingRecipes;

import com.vaadin.data.validator.EmailValidator;
import com.vaadin.data.validator.StringLengthValidator;
import com.vaadin.server.UserError;
import com.vaadin.server.VaadinSession;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.Button;


public class NewRecipe extends RecipeFormDesign implements Button.ClickListener {

	
	
	private MyUI myUI;

	private Recipe recipe;
	
	public NewRecipe(MyUI myUI, Recipe recipe) {
	    this.myUI = myUI;

	    this.recipe = recipe;

	    title.addValidator(new StringLengthValidator("The title must be between 4-40 letters ( {0})", 4, 40, false));
	    description.addValidator(new StringLengthValidator("The description must be between 10-255 letters ({0})", 10, 255, false));
	    title.setValidationVisible(true);
	    description.setValidationVisible(true);
	       
	    save.addClickListener(this);
	    delete.addClickListener(this);
	    

    	delete.setVisible(false);    	
    	save.setVisible(false);
    	
	    if(recipe.getId() == null)
	    {
	    	title.setEnabled(true);
	    	description.setEnabled(true);
	    	save.setVisible(true);
	    } else if(VaadinSession.getCurrent().getAttribute("user") != null && VaadinSession.getCurrent().getAttribute("user").equals(owner.getValue()))
	    {
	    	save.setVisible(true);
	    		
	    	delete.setVisible(true);
	    }
	}
	
	public void accessRecipe()
	{
    	delete.setVisible(false);    	
    	save.setVisible(false);
    	title.setEnabled(false);
    	description.setEnabled(false);
    	
	    if(VaadinSession.getCurrent() != null && owner.getValue() == null)
	    {
	    	save.setVisible(true);
	    	title.setEnabled(true);
	    	description.setEnabled(true);
	    } else if(owner.getValue() != null)
	    {
	    	if(VaadinSession.getCurrent() != null)
	    		if(VaadinSession.getCurrent().getAttribute("user") != null && VaadinSession.getCurrent().getAttribute("user").equals(owner.getValue()))
	    		{
		    	save.setVisible(true);
		    	delete.setVisible(true);

		    	title.setEnabled(true);
		    	description.setEnabled(true);
	    		}

	    }
	}
	
	@Override
	public void buttonClick(ClickEvent event) {
		if(event.getButton().getStyleName().equals("primary"))
		{
		if(!(title.isValid() && description.isValid()))
			return;
		
		
		recipe.setTitle(title.getValue());
		recipe.setDescription(description.getValue());
		recipe.setOwner((String)getSession().getAttribute("user"));

		RecipeService.getInstance().save(recipe);
		
		this.myUI.navigateToView(new AllRecipes(this.myUI));		
		}
		else {
			
			RecipeService.getInstance().delete(recipe);
			
			this.myUI.navigateToView(new AllRecipes(this.myUI));		
		}
	}
}
