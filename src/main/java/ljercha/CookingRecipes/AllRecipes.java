package ljercha.CookingRecipes;

import java.util.ArrayList;

import com.vaadin.data.Property;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.fieldgroup.BeanFieldGroup;
import com.vaadin.data.util.BeanItemContainer;

public class AllRecipes extends AllRecipesDesign {

	private MyUI myUI;

	public AllRecipes(MyUI myUI) {
		this.myUI = myUI;
		initList();

		grid.addSelectionListener(event -> {
			//	NewRecipe recipe = new NewRecipe(myUI, RecipeService.getInstance().findAll().get(tableView.getValue()));
				//myUI.navigateToView(recipe);

				NewRecipe viewRecipe = new NewRecipe(myUI);
		        Recipe recipe = (Recipe) event.getSelected().iterator().next();

			    BeanFieldGroup.bindFieldsUnbuffered(recipe, viewRecipe);
			    viewRecipe.accessRecipe();
				myUI.navigateToView(viewRecipe);

		});
	}

	public void initList() {
		ArrayList<Recipe> recipes = RecipeService.getInstance().findAll();

	    grid.setColumns("title", "description", "owner");
		grid.setContainerDataSource(new BeanItemContainer<Recipe>(Recipe.class,recipes));
	}

}
