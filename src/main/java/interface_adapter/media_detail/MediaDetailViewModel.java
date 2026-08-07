package interface_adapter.media_detail;

import interface_adapter.ViewModel;

/**
 * ViewModel for the Media Detail View.
 */
public class MediaDetailViewModel extends ViewModel<MediaDetailState> {

    public static final String VIEW_NAME = "media detail";

    public static final String TITLE_LABEL = "Media Detail";
    public static final String BACK_BUTTON_LABEL = "Back";


    public MediaDetailViewModel() {
        super(VIEW_NAME);
        setState(new MediaDetailState());
    }
}
