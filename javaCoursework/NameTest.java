package javaCoursework;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class NameTest {
    private Name name;

    @BeforeEach
    void setUp() {
        name = new Name();
        name.setName("shrawan mainali"); 
    }

    @Test
    void testSetNameAndGetName() {
        assertEquals("Shrawan Mainali", name.getName(), "Name should be properly formatted");
    }

    @Test
    void testGetShortName() {
        assertEquals("SM", name.getShortName(), "Short name should extract uppercase letters");
    }
}


