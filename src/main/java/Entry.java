import org.sql2o.*;
import java.util.List;

public class Entry {
  private int id;
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
      this.getMailingAddress().equals(newEntry.getMailingAddress()) && this.getEmailAddress().equals(newEntry.getEmailAddress()) && this.getId() == newEntry.getId();
    }
  }

  public static List<Entry> all() {
    String sql = "SELECT id, name, phone_number, mailing_address, email_address FROM entries";
    try(Connection con = DB.sql2o.open()) {
      return con.createQuery(sql).executeAndFetch(Entry.class);
    }
  }

  public int getId() {
    return id;
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
      this.id = (int) con.createQuery(sql, true).addParameter("name", this.name).addParameter("phone_number", this.phone_number).addParameter("mailing_address", this.mailing_address).addParameter("email_address", this.email_address).executeUpdate().getKey();
    }
  }

  public static Entry find(int id) {
    try(Connection con = DB.sql2o.open()) {
      String sql = "SELECT * FROM entries where id=:id";
      Entry entry = con.createQuery(sql).addParameter("id", id).executeAndFetchFirst(Entry.class);
      return entry;
    }
  }
}


// create database nate_address_book;
// \c nate_address_book;
// CREATE TABLE entries (id serial PRIMARY KEY, name varchar, phone_number varchar, mailing_address varchar, email_address varchar);
// CREATE DATABASE nate_address_book_test WITH TEMPLATE nate_address_book;
