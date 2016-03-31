package ljercha.CookingRecipes;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {

	private static UserService instance;
	private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

	private final HashMap<Long, User> users = new HashMap<>();
	private long nextId = 0;

	private UserService() {
	}

	/**
	 * @return a reference to an example facade for Customer objects.
	 */
	static UserService getInstance() {
		if (instance == null) {
			instance = new UserService();
			instance.ensureTestData();
		}
		return instance;
	}

	/**
	 * @return all available Customer objects.
	 */
	

	/**
	 * Finds all Customer's that match given filter.
	 *
	 * @param stringFilter
	 *            filter that returned objects should match or null/empty string
	 *            if all objects should be returned.
	 * @return list a Customer objects
	 */
	public synchronized User findAll(String stringFilter) {
		for (User user : users.values()) {
			try {
				if(user.getLogin().equals(stringFilter)) {
					return user.clone();
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
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
	public synchronized List<User> findAll(String stringFilter, int start, int maxresults) {
		ArrayList<User> arrayList = new ArrayList<>();
		for (User contact : users.values()) {
			try {
				boolean passesFilter = (stringFilter == null || stringFilter.isEmpty())
						|| contact.toString().toLowerCase().contains(stringFilter.toLowerCase());
				if (passesFilter) {
					arrayList.add(contact.clone());
				}
			} catch (CloneNotSupportedException ex) {
				Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		Collections.sort(arrayList, new Comparator<User>() {

			@Override
			public int compare(User o1, User o2) {
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
		return users.size();
	}

	/**
	 * Deletes a customer from a system
	 *
	 * @param value
	 *            the Customer to be deleted
	 */
	public synchronized void delete(User value) {
		users.remove(value.getId());
	}

	/**
	 * Persists or updates customer in the system. Also assigns an identifier
	 * for new Customer instances.
	 *
	 * @param entry
	 */
	public synchronized void save(User entry) {
		if (entry == null) {
			LOGGER.log(Level.SEVERE,
					"User is null.");
			return;
		}
		if (entry.getId() == null) {
			entry.setId(nextId++);
		}
		try {
			entry = (User) entry.clone();
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		users.put(entry.getId(), entry);
	}

	/**
	 * Sample data generation
	 */
	public void ensureTestData() {
		if (users.isEmpty()) {
			final String[] names = new String[] { "Gabrielle Patel", "Brian Robinson", "Eduardo Haugen",
					"Koen Johansen", "Alejandro Macdonald", "Angel Karlsson"};
			Random r = new Random(0);
			for (String name : names) {
				String[] split = name.split(" ");
				User c = new User();
				c.setLogin(split[0]);
				c.setEmail(split[0].toLowerCase() + "@" + split[1].toLowerCase() + ".com");
				c.setPassword("1234");
				save(c);
			}
		}
	}
}
