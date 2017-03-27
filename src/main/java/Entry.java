import org.sql2o.*;
import java.util.List;

public class Entry {
  private String name;
  private String phone_number;
  private String mailing_address;
  private String email_address;

  public Entry(String name, String phone_number, String mailing_address, String email_address) {
    this.name = name;
    this.phone_number = phone_number;
    this.mailing_address = mailing_address;
    this.email_address = email_address;
  }

  @Override
  public boolean equals(Object otherEntry) {
    if (!(otherEntry instanceof Entry)) {
      return false;
    } else {
      Entry newEntry = (Entry) otherEntry;
      return this.getName().equals(newEntry.getName()) && this.getPhoneNumber().equals(newEntry.getPhoneNumber()) &&
      this.getMailingAddress().equals(newEntry.getMailingAddress()) && this.getEmailAddress().equals(newEntry.getEmailAddress());
    }
  }

  public static List<Entry> all() {
    String sql = "SELECT name, phone_number, mailing_address, email_address FROM entries";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Entry.class);
    }
  }

  public String getName() {
    return name;
  }

  public String getPhoneNumber() {
    return phone_number;
  }

  public String getMailingAddress() {
    return mailing_address;
  }

  public String getEmailAddress() {
    return email_address;
  }

  public void save() {
    try(Connection con = DB.sql2o.open()) {
      String sql = "INSERT INTO entries(name, phone_number, mailing_address, email_address) VALUES (:name, :phone_number, :mailing_address, :email_address)";
      con.createQuery(sql).addParameter("name", this.name).addParameter("phone_number", this.phone_number).addParameter("mailing_address", this.mailing_address).addParameter("email_address", this.email_address).executeUpdate();
    }
  }


}
