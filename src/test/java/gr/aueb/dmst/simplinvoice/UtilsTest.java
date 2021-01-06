package gr.aueb.dmst.simplinvoice;

import org.junit.jupiter.api.Test;

public class UtilsTest {

    @Test
    void generateMockMyDataMark() {
        for(int i = 0; i < 10; i++)
            System.out.println("Random generated UID-" + i + " " + Utils.generateMockMyDataMark());
    }

}
