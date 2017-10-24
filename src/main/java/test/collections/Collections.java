package test.collections;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class Collections {

    public static class User extends Object {
        final String firstName;
        final String lastName;

        public User(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        @Override
        public String toString() {
            return "User{" +
                    "firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    '}';
        }
    }

    @Test
    public void maps() {
        Map<String, User> map = new HashMap<>();

        map.put("john", new User("john", "smith"));
        map.put("jane", new User("jane", "doe"));

        User john = map.get("john");
        assertEquals(new User("john", "smith"), john);
    }

    @Test
    public void sets() {
        Set<User> set = new HashSet<>();
        set.add(new User("john", "smith"));
        assertTrue(set.contains(new User("john", "smith")));
    }

    @Test
    public void treeSets() {
        Set<User> set = new TreeSet<>();
        set.add(new User("john", "smith"));
        assertTrue(set.contains(new User("john", "smith")));
    }
}
