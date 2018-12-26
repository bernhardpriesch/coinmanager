package at.priesch.coinmanager.servicecomponents.i18n;

import java.util.ResourceBundle;

public class Translator
{
    private ResourceBundle resourceBundle = null;

    public Translator(final ResourceBundle resourceBundle)
    {
        this.resourceBundle = resourceBundle;
    }

    public String getTranslatedText (final String key)
    {
        return resourceBundle.getString (key);
    }
}
