import org.sql2o.*;
import org.junit.*;
import static org.junit.Assert.*;

public class EntryTest {

  @Before
  public void setUp() {
    DB.sql2o = new Sql2o("jdbc:postgresql://localhost:5432/nate_address_book_test", null, null);
  }

  @After
  public void tearDown() {
    try(Connection con = DB.sql2o.open()) {
      String deleteEntriesQuery = "DELETE FROM entries *;";
      // String deleteCategoriesQuery = "DELETE FROM categories *;";
      con.createQuery(deleteEntriesQuery).executeUpdate();
      // con.createQuery(deleteCategoriesQuery).executeUpdate();
    }
  }

  @Test
  public void equals_returnsTrueIfEntriesAreTheSame() {
    Entry firstEntry = new Entry("Bob", "Snob", "Glob", "Clob");
    Entry secondEntry = new Entry("Bob", "Snob", "Glob", "Clob");
    assertTrue(firstEntry.equals(secondEntry));
  }

  @Test
  public void save_returnsTrueIfEntriesAreTheSame() {
    Entry myEntry = new Entry("Bill", "510-654-4720", "1569 Solano Ave, Berkeley, CA 94707", "bon@billgrahamfoundation.org");
    myEntry.save();
    assertTrue(Entry.all().get(0).equals(myEntry));
  }

  @Test
  public void all_returnsAllInstancesOfEntry_true() {
    Entry firstEntry = new Entry("Bonnie", "510-654-4720", "1569 Solano Ave, Berkeley, CA 94707", "bon@billgrahamfoundation.org");
    firstEntry.save();
    Entry secondEntry = new Entry("Info", "510-644-2020", "2020 Addison Street, Berkeley, CA 94704", "info@freightandsalvage.org");
    secondEntry.save();
    assertEquals(true, Entry.all().get(0).equals(firstEntry));
    assertEquals(true, Entry.all().get(1).equals(secondEntry));
  }
}
