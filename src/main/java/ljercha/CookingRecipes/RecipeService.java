package ljercha.CookingRecipes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RecipeService {


	private static RecipeService instance;
	private static final Logger LOGGER = Logger.getLogger(RecipeService.class.getName());

	private final HashMap<Long, Recipe> recipes = new HashMap<>();
	private long nextId = 0;

	private RecipeService() {
	}
	
	public ArrayList<Recipe> findAll()
	{
		ArrayList<Recipe> arrayList = new ArrayList<Recipe>();
		
		for (Recipe recipe : recipes.values()) {
			arrayList.add(recipe);
		}
		
		return arrayList;
	}

	/**
	 * @return a reference to an example facade for Customer objects.
	 */
	static RecipeService getInstance() {
		if (instance == null) {
			instance = new RecipeService();
			instance.ensureTestData();
		}
		return instance;
	}

	/**
	 * Finds all Customer's that match given filter.
	 *
	 * @param stringFilter
	 *            filter that returned objects should match or null/empty string
	 *            if all objects should be returned.
	 * @return list a Customer objects
	 */
	public synchronized Recipe findAll(String stringFilter) {
		for (Recipe recipe : recipes.values()) {
			try {
				if(recipe.getTitle().equals(stringFilter)) {
					return recipe.clone();
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(RecipeService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		
		return null;
	}

	/**
	 * Finds all Customer's that match given filter and limits the resultset.
	 *
	 * @param stringFilter
	 *            filter that returned objects should match or null/empty string
	 *            if all objects should be returned.
	 * @param start
	 *            the index of first result
	 * @param maxresults
	 *            maximum result count
	 * @return list a Customer objects
	 */
	public synchronized List<Recipe> findAll(String stringFilter, int start, int maxresults) {
		ArrayList<Recipe> arrayList = new ArrayList<>();
		for (Recipe recipe : recipes.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| recipe.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(recipe.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<Recipe>() {

			@Override
			public int compare(Recipe o1, Recipe o2) {
				return (int) (o2.getId() - o1.getId());
			}
		});
		int end = start + maxresults;
		if (end > arrayList.size()) {
			end = arrayList.size();
		}
		return arrayList.subList(start, end);
	}

	/**
	 * @return the amount of all customers in the system
	 */
	public synchronized long count() {
		return recipes.size();
	}

	/**
	 * Deletes a customer from a system
	 *
	 * @param value
	 *            the Customer to be deleted
	 */
	public synchronized void delete(Recipe value) {
		recipes.remove(value.getId());
	}

	/**
	 * Persists or updates customer in the system. Also assigns an identifier
	 * for new Customer instances.
	 *
	 * @param entry
	 */
	public synchronized void save(Recipe entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE,
					"User is null.");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (Recipe) entry.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		recipes.put(entry.getId(), entry);
	}

	/**
	 * Sample data generation
	 */
	public void ensureTestData() {
		if (recipes.isEmpty()) {
			final String[] titles = new String[] {
					"Jajka sadzone inaczej",
					"Pizza",
					"Tradycyjna zupa pomidorowa z makaronem",
					"Jajecznica według Gordona Ramsaya",
					"Naleśniki z serem",
					"Kotlety mielone Magdy Gessler",
					"Gofry lekkie i chrupiące"
			};			

			final String[] description = new String[] {
					"Wbic slomke do jajka i wypic przez slomke",
					"Kupic placki tortilla, oblac ketchupem, posypac szynka i potartym serem",
					"Zalac pomidory wrzatkiem, gotowac godzine, doprawic sola i pieprzem",
					"Jajka usmazyc na maśle na małym ogniu ciągle mieszając",
					"Zrobic ciasto nalesnikowe z 1 jajka szklanki maki i mleka, nafaszerowac twarogiem ze smietana i cukrem",
					"Usmazyc miesio mielone z suszonymi pomidorami i pietruszka ulepione w pulpety",
					"##Lekkie jak piorko, pyszne z bita smietana i owocami"
					+ "###Składniki" 
					+ "- 2 szklanki maki "
					+ "- 2 szklanki mleka"
					+ "- 1 lyzka proszku do pieczenia- szczypta soli"
					+ "- 1/3 szklanki oleju- 2 jajka, zoltka odzielone od bialek"
					+ "###Przepis"
					+ "Rozgrzac gofrownice. Wszystkie skladniki oprocz bialek ubic mikserem na gladka mase. Bialka ubic na sztywna piane i delikatnie wymieszac z ciastem."
					
			};
			final String[] owners = new String[] {
					"Gabrielle",
					"Gabrielle",
					"Brian",
					"Eduardo",
					"Angel",
					"Koen",
					"Alejandro"
			};
			
			Random r = new Random(0);
			int i = 0;
			for (String name : titles) {
				Recipe c = new Recipe();
				c.setTitle(name);
				c.setDescription(description[i]);
				c.setOwner(owners[i]);
				save(c);
				i++;
			}
		}
	}
}
