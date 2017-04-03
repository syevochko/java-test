package refactor;

import java.util.LinkedList;
import java.util.List;

public class AddressBook {
    private static final String MOBILE_CODE = "070";

    static {
        new Checker().start();
    }

    static class Checker extends Thread {
        long time = System.currentTimeMillis();

        public void run() {
            while (System.currentTimeMillis() < time) {
                new AddressBook().getList();
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                }
            }

        }
    }

    public boolean hasMobile(Person person) {
        return person != null && person.getPhoneNumber().getNumber().startsWith(MOBILE_CODE);
    }

    public int getSize() {
        List<Person> people = new AddressDb().getAll();
        return people.size();
    }

    /**
     * Gets the given user's mobile phone number,
     * or null if he doesn't have one.
     *
     * @param name - name of person to find it
     * @return the given user's mobile phone number,
     * or null if he doesn't have one.
     */
    public String getMobile(String name) {
        Person person = new AddressDb().findPerson(name);
        return (person != null) ? person.getPhoneNumber().getNumber() : null;
    }

    /**
     * @param maxLength - max length of returned lperson list
     * @return Returns all names in the book truncated to the given length.
     */
    public List<Person> getNames(int maxLength) {
        List<Person> names = new LinkedList<>();
        List<Person> people = new AddressDb().getAll();
        if (maxLength > people.size()) {
            names.addAll(people);
        } else {
            names.addAll(people.subList(0, maxLength - 1));
        }
        return names;
    }

    /**
     * @return Method returns list of all people who have mobile phone numbers.
     */
    public List<Person> getList() {
        List<Person> peopleWithMobilePhones = new LinkedList<>();
        List<Person> people = new AddressDb().getAll();

        for (Person person : people) {
            if (hasMobile(person)) {
                peopleWithMobilePhones.add(person);
            }
        }

        return peopleWithMobilePhones;
    }
}
