import java.util.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.*;

import javax.imageio.ImageIO;
import javax.swing.*;

/**
 * ImageBundle is a ResourceBundle that retrieves the content of an image from an image file with supported extension.
 */
public class ImageBundle extends ResourceBundle {
    private static final String RESOURCE = "MyApp";
    private String __fileSuffix;
    /**
     * @uml.property  name="__KEYS"
     */
    private static final Vector<String> __KEYS;
    private static Hashtable<String, ImageIcon> __TABLE;

    static {
        __KEYS  = new Vector<String>();
        __KEYS.addElement(RESOURCE);
        __TABLE = new Hashtable<String, ImageIcon>();
    }

    /**
     * Load image file stored in a JAR classpath
     * @param imageName the filename of the image
     * @return the ImageIcon of the image file
     */
    private ImageIcon __loadImageFromJar(String imageName) {
        String path = ""; //define the file path of the image file in imageName itself

        String imagePath = path + imageName + __fileSuffix;
        ImageIcon icon;
        URL url;

        icon = (ImageIcon)__TABLE.get(imageName);

        if(icon != null)
            return icon;

        url = ImageBundle.class.getResource(imagePath);

        icon = new ImageIcon(url);
        __TABLE.put(imageName, icon);

        return icon;
    }

    /**
     * Load image file stored in a given path
     * @param imageName the filename of the image
     * @return the ImageIcon of the image file
     */
    @SuppressWarnings("unused")
    private ImageIcon __loadImageFromExternalSource(String imageName) {
        String path = System.getenv("MY_APP_HOME_PATH");

        String imagePath = ((path != null) ? (path + imageName + __fileSuffix) : (imageName + __fileSuffix));
        ImageIcon value;

        value = (ImageIcon)__TABLE.get(imageName);

        if(value != null){
            //Outils.debugMessage("Cached " + imagePath + "> " + imageName + ": " + value);
            return value;
        }
        else {
            //Outils.debugMessage("New " + imagePath + "> " + imageName + ": " + value);
        }

        ImageIcon property = null;
        BufferedImage image = null;
        try {
            image = ImageIO.read(new File(imagePath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        property = new ImageIcon(image);

        value = property;
        __TABLE.put(imageName, value);

        return value;
    }

    protected ImageBundle(String suffix) {
        __fileSuffix = suffix;
    }

    public ImageBundle() {
        this("");
    }

    /**
     * @return
     * @uml.property  name="__KEYS"
     */
    public Enumeration<String> getKeys() {
        return __KEYS.elements();
    }

    protected final Object handleGetObject(String key) {
        return __loadImageFromJar(key);
    }
}