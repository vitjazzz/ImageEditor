import com.vitja.controllers.ImageController;

import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

/**
 * Created by Viktor on 26.09.2016.
 */
public class TestImageController {
    private ImageController imageController;

    @Before
    public void init(){
        imageController = new ImageController();
    }

    @Test
    public void testCheckIfImage(){
        assertTrue("1", imageController.checkIfImage(new File("D:\\Інші\\belka00.jpg")));
        assertTrue("2", imageController.checkIfImage(new File("gd.png")));
        assertTrue("3", imageController.checkIfImage(new File("F.gif")));
        assertTrue("4", imageController.checkIfImage(new File("Пваы.bmp")));
        assertTrue("5", imageController.checkIfImage(new File("ііап.jpg")));
        assertFalse("6", imageController.checkIfImage(new File("ffda.ddd")));
    }


}
