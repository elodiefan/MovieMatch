package views;

import java.awt.Component;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import interface_adapter.search_user.SearchUserController;
import interface_adapter.search_user.SearchUserState;
import interface_adapter.search_user.SearchUserViewModel;
import use_case.search_user.UserSummary;

/**
 * The View for searching for other users.
 * <p>
 * Results are drawn straight onto this panel rather than a separate results
 * view, so you can refine a keyword without navigating back and forth.
 */
public class SearchUserView extends JPanel implements PropertyChangeListener {

    private static final int FIELD_COLUMNS = 20;
    private static final int RESULTS_HEIGHT = 220;

    private final String viewName = SearchUserViewModel.VIEW_NAME;
    private final SearchUserViewModel searchUserViewModel;
    private SearchUserController searchUserController;

    private final JTextField searchInput;
    private final JLabel message;
    private final JPanel resultsPanel;

    public SearchUserView(SearchUserViewModel searchUserViewModel) {
        this.searchUserViewModel = searchUserViewModel;
        this.searchUserViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel(SearchUserViewModel.TITLE_LABEL);
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        searchInput = new JTextField(FIELD_COLUMNS);
        final JButton searchButton = new JButton(SearchUserViewModel.SEARCH_BUTTON_LABEL);
        final JPanel searchPanel = new JPanel();
        searchPanel.add(searchInput);
        searchPanel.add(searchButton);

        message = new JLabel(SearchUserViewModel.PROMPT_LABEL);
        message.setAlignmentX(Component.CENTER_ALIGNMENT);

        resultsPanel = new JPanel();
        resultsPanel.setLayout(new BoxLayout(resultsPanel, BoxLayout.Y_AXIS));
        final JScrollPane resultsScroll = new JScrollPane(resultsPanel);
        resultsScroll.setPreferredSize(new Dimension(searchInput.getPreferredSize().width, RESULTS_HEIGHT));

        final JButton backButton = new JButton(SearchUserViewModel.BACK_BUTTON_LABEL);
        final JPanel backPanel = new JPanel();
        backPanel.add(backButton);

        // Enter in the text field searches too, since that is what people expect.
        searchButton.addActionListener(event -> this.search());
        searchInput.addActionListener(event -> this.search());
        backButton.addActionListener(event -> searchUserController.switchToHomePageView());

        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        this.add(title);
        this.add(searchPanel);
        this.add(message);
        this.add(resultsScroll);
        this.add(backPanel);
    }

    private void search() {
        searchUserController.execute(searchInput.getText());
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SearchUserState state = (SearchUserState) evt.getNewValue();

        resultsPanel.removeAll();

        if (state.getSearchError() != null) {
            message.setText(state.getSearchError());
        }
        else if (state.getResults().isEmpty() && state.isSearched()) {
            message.setText(SearchUserViewModel.NO_RESULTS_LABEL + state.getKeyword());
        }
        else {
            message.setText(SearchUserViewModel.PROMPT_LABEL);
            state.getResults().forEach(this::addUserResult);
        }

        this.revalidate();
        this.repaint();
    }

    private void addUserResult(UserSummary user) {
        final JPanel userPanel = new JPanel();
        userPanel.add(new JLabel(user.getDisplayName() + " (" + user.getUsername() + ")"));

        final JButton viewProfileButton = new JButton(SearchUserViewModel.VIEW_PROFILE_BUTTON_LABEL);
        viewProfileButton.addActionListener(
                event -> searchUserController.openProfile(user.getUsername(), user.getDisplayName()));
        userPanel.add(viewProfileButton);

        resultsPanel.add(userPanel);
    }

    public String getViewName() {
        return viewName;
    }

    public void setSearchUserController(SearchUserController searchUserController) {
        this.searchUserController = searchUserController;
    }
}
