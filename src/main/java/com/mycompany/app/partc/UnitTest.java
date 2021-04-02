import static org.junit.jupiter.api.*;

public class UnitTest1{
      //try to write a unit test
      public void testBookList(){
        List expected = array.asList("Absolute Java", "Savitch", 5, true);
        assertThat(book[0], is(expected));
      }
}
