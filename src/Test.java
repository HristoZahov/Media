import Classes.Media;

import static Utilities.MediaUtilities.getSearchedSQL;
import static Utilities.MediaUtilities.updateMedia;

public class Test {
    public static void main(String[] args) {
        Media media = new Media(1,"Gr","Peshev",2007,"Novel","Hi",5,"qwert");
        updateMedia(media);
    }
}
