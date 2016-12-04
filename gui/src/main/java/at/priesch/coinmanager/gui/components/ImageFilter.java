package at.priesch.coinmanager.gui.components;

import java.io.File;

import javax.swing.filechooser.FileFilter;

/**
 * @author Bernhard PRIESCH
 */
public class ImageFilter
    extends FileFilter
{

    // Accept all directories and all gif, jpg, tiff, or png files.
    @Override
    public boolean accept (final File f)
    {
        if (f.isDirectory ())
        {
            return true;
        }

        String extension = ImageUtils.getExtension (f);
        if (extension != null)
        {
            if (extension.equals (ImageUtils.tiff) || extension.equals (ImageUtils.tif) || extension.equals (ImageUtils.gif) || extension.equals (ImageUtils.jpeg) || extension.equals (ImageUtils.jpg) || extension.equals (ImageUtils.png))
            {
                return true;
            }
            else
            {
                return false;
            }
        }

        return false;
    }

    // The description of this filter
    @Override
    public String getDescription ()
    {
        return "Just Images";
    }
}
