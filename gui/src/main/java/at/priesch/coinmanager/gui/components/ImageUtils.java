package at.priesch.coinmanager.gui.components;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

/**
 * @author Bernhard PRIESCH
 */
public class ImageUtils
{
    public final static String jpeg = "jpeg";
    public final static String jpg  = "jpg";
    public final static String gif  = "gif";
    public final static String tiff = "tiff";
    public final static String tif  = "tif";
    public final static String png  = "png";

    /*
     * Get the extension of a file.
     */
    public static String getExtension (final File f)
    {
        String ext = null;
        String s = f.getName ();
        int i = s.lastIndexOf ('.');

        if (i > 0 && i < s.length () - 1)
        {
            ext = s.substring (i + 1).toLowerCase ();
        }
        return ext;
    }

    public static byte[] image2byteArray (final ImageIcon image)
    {
        BufferedImage img = new BufferedImage (image.getIconWidth (), image.getIconHeight (), BufferedImage.TYPE_INT_ARGB);

        img.getGraphics ().drawImage (image.getImage (), 0, 0, null);
        ByteArrayOutputStream baos = new ByteArrayOutputStream ();
        try
        {
            ImageIO.write (img, "png", baos);
        }
        catch (IOException e)
        {
            e.printStackTrace ();
        }
        
        return baos.toByteArray ();
    }

}
