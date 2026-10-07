import org.example.StringProcessor;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class StringProcessorTest {

    @Test
    public void testProcessBasic() {
        StringProcessor processor = new StringProcessor();

        String input = "12.5 text 14-30 more 100.0 data! Some long sentence here? End.";
        String delimiters = "., !?-";

        String result = processor.process(input, delimiters);

        assertNotNull(result);
        assertTrue(result.contains("Найдено чисел: 2"));
        assertTrue(result.contains("Найдено валидное время: 14-30"));
        assertTrue(result.contains("Найдено лексем: 10"));
    }

    @Test
    public void testEmptyInput() {
        StringProcessor processor = new StringProcessor();
        String result = processor.process("", " ");
        assertNotNull(result);
        assertTrue(result.contains("Найдено лексем: 0"));
    }
}