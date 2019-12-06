import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class CaptureScreen {

//    capture screenshot as an image and save it into a file
        public static void saveScreenshot() {

            final String SCREENSHOT_PATH = "Results\\screenshot.jpg";
            final String SCREENSHOT_FORMAT = "jpg";

//    delete screenshot if exists
            try{
                Files.deleteIfExists(Paths.get(SCREENSHOT_PATH));
            } catch(
                    NoSuchFileException e){
                System.out.println("No such file/directory exists");
            } catch (IOException e) {
                System.out.println("Another file/directory exception");
            }

// get new screenshot and save it to the file
        try {
                Robot robot = new Robot();
                Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());
                BufferedImage screenFullImage = robot.createScreenCapture(screenRect);
                ImageIO.write(screenFullImage, SCREENSHOT_FORMAT, new File(Constants.MAIN_PATH + SCREENSHOT_PATH));
                System.out.println("Page screenshot is saved to: " + SCREENSHOT_PATH);
            } catch (IOException e) {
                e.printStackTrace();
            } catch (AWTException e) {
                e.printStackTrace();
            }
        }
    }

