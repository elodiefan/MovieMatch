package view;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

import entity.Media;
import interface_adapter.ViewManagerModel;
import java.util.concurrent.ExecutionException;

import javax.swing.SwingWorker;

import interface_adapter.filter.FilterController;
import interface_adapter.search.SearchController;
import interface_adapter.media_detail.MediaDetailController;
import interface_adapter.search_result.SearchResultState;
import interface_adapter.search_result.SearchResultViewModel;
import use_case.filter.FilterCriteria;

/** View for displaying and filtering search results. */
public class SearchResultView extends JPanel
        implements PropertyChangeListener {
    private static final int TITLE_FILTER_SPACING = 5;
    private static final int FILTER_PANEL_HEIGHT = 300;

    private static final int FILTER_FIELD_WIDTH = 6;
    private static final int RESULT_PANEL_WIDTH = 500;
    private static final int RESULT_PANEL_HEIGHT = 45;

    private static final int ACTION_GENRE_ID = 28;
    private static final int ADVENTURE_GENRE_ID = 12;
    private static final int ANIMATION_GENRE_ID = 16;
    private static final int COMEDY_GENRE_ID = 35;
    private static final int CRIME_GENRE_ID = 80;
    private static final int DOCUMENTARY_GENRE_ID = 99;
    private static final int DRAMA_GENRE_ID = 18;
    private static final int FAMILY_GENRE_ID = 10751;
    private static final int FANTASY_GENRE_ID = 14;
    private static final int HORROR_GENRE_ID = 27;
    private static final int ROMANCE_GENRE_ID = 10749;
    private static final int SCIENCE_FICTION_GENRE_ID = 878;
    private static final int THRILLER_GENRE_ID = 53;

    private final String viewName = SearchResultViewModel.VIEW_NAME;

    private final SearchResultViewModel searchResultViewModel;

    private MediaDetailController mediaDetailController;
    private FilterController filterController;

    private static final String LOAD_MORE_LABEL = "Load more results";
    private static final String LOADING_LABEL = "Loading...";

    private final JPanel resultsPanel;
    private final JButton backButton;
    private JButton loadMoreButton;
    private SearchController searchController;

    private final JCheckBox englishCheckBox =
            new JCheckBox("English");
    private final JCheckBox frenchCheckBox =
            new JCheckBox("French");
    private final JCheckBox spanishCheckBox =
            new JCheckBox("Spanish");
    private final JCheckBox chineseCheckBox =
            new JCheckBox("Chinese");
    private final JCheckBox japaneseCheckBox =
            new JCheckBox("Japanese");
    private final JCheckBox koreanCheckBox =
            new JCheckBox("Korean");

    private final JCheckBox actionCheckBox =
            new JCheckBox("Action");
    private final JCheckBox adventureCheckBox =
            new JCheckBox("Adventure");
    private final JCheckBox animationCheckBox =
            new JCheckBox("Animation");
    private final JCheckBox comedyCheckBox =
            new JCheckBox("Comedy");
    private final JCheckBox crimeCheckBox =
            new JCheckBox("Crime");
    private final JCheckBox documentaryCheckBox =
            new JCheckBox("Documentary");
    private final JCheckBox dramaCheckBox =
            new JCheckBox("Drama");
    private final JCheckBox familyCheckBox =
            new JCheckBox("Family");
    private final JCheckBox fantasyCheckBox =
            new JCheckBox("Fantasy");
    private final JCheckBox horrorCheckBox =
            new JCheckBox("Horror");
    private final JCheckBox romanceCheckBox =
            new JCheckBox("Romance");
    private final JCheckBox scienceFictionCheckBox =
            new JCheckBox("Science Fiction");
    private final JCheckBox thrillerCheckBox =
            new JCheckBox("Thriller");

    private final JTextField minimumRatingField =
            new JTextField(FILTER_FIELD_WIDTH);
    private final JTextField earliestYearField =
            new JTextField(FILTER_FIELD_WIDTH);
    private final JTextField latestYearField =
            new JTextField(FILTER_FIELD_WIDTH);

    private final JLabel messageLabel = new JLabel(" ");

    public SearchResultView(
            SearchResultViewModel searchResultViewModel,
            ViewManagerModel viewManagerModel,
            String searchViewName) {

        this.searchResultViewModel = searchResultViewModel;
        this.searchResultViewModel.addPropertyChangeListener(this);

        final JLabel title = new JLabel("Search Results");
        title.setAlignmentX(Component.CENTER_ALIGNMENT);

        backButton = new JButton("Back");

        // Back went nowhere, which stranded the user on the results screen.
        backButton.addActionListener(
                event -> viewManagerModel.switchView(searchViewName)
        );

        resultsPanel = new JPanel();
        resultsPanel.setLayout(
                new BoxLayout(resultsPanel, BoxLayout.Y_AXIS)
        );

        final JScrollPane resultsScrollPane =
                new JScrollPane(resultsPanel);

        resultsScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );
        resultsScrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        // Adds 5 pixels of vertical spacing between the title and filter panel.
        final JPanel topPanel = new JPanel();
        topPanel.setLayout(
                new BoxLayout(topPanel, BoxLayout.Y_AXIS)
        );
        topPanel.add(title);
        topPanel.add(Box.createVerticalStrut(TITLE_FILTER_SPACING));

        final JScrollPane filterScrollPane =
                new JScrollPane(createFilterPanel());

        filterScrollPane.setPreferredSize(
                new Dimension(0, FILTER_PANEL_HEIGHT)
        );
        filterScrollPane.setVerticalScrollBarPolicy(
                JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED
        );
        filterScrollPane.setHorizontalScrollBarPolicy(
                JScrollPane.HORIZONTAL_SCROLLBAR_NEVER
        );

        topPanel.add(filterScrollPane);

        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(resultsScrollPane, BorderLayout.CENTER);
        // A search only fetches a few pages at a time, so this is how the user
        // asks for the next block rather than waiting for thousands of results.
        loadMoreButton = new JButton(LOAD_MORE_LABEL);
        loadMoreButton.setVisible(false);
        loadMoreButton.addActionListener(event -> loadMore());

        final JPanel southPanel = new JPanel(new BorderLayout());
        final JPanel loadMorePanel = new JPanel();
        loadMorePanel.add(loadMoreButton);
        southPanel.add(loadMorePanel, BorderLayout.NORTH);
        southPanel.add(backButton, BorderLayout.SOUTH);

        this.add(southPanel, BorderLayout.SOUTH);
    }

    private JPanel createFilterPanel() {
        final JPanel filterPanel = new JPanel();
        filterPanel.setLayout(
                new BoxLayout(filterPanel, BoxLayout.Y_AXIS)
        );
        filterPanel.setBorder(
                BorderFactory.createTitledBorder("Filters")
        );

        filterPanel.add(createLanguagePanel());
        filterPanel.add(createGenrePanel());
        filterPanel.add(createNumberPanel());
        filterPanel.add(createFilterButtonPanel());
        filterPanel.add(messageLabel);

        return filterPanel;
    }

    private JPanel createLanguagePanel() {
        final JPanel languagePanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        languagePanel.setBorder(
                BorderFactory.createTitledBorder("Language")
        );

        languagePanel.add(englishCheckBox);
        languagePanel.add(frenchCheckBox);
        languagePanel.add(spanishCheckBox);
        languagePanel.add(chineseCheckBox);
        languagePanel.add(japaneseCheckBox);
        languagePanel.add(koreanCheckBox);

        return languagePanel;
    }

    private JPanel createGenrePanel() {
        final JPanel genrePanel = new JPanel(
                new GridLayout(0, 4)
        );

        genrePanel.setBorder(
                BorderFactory.createTitledBorder("Genre")
        );

        genrePanel.add(actionCheckBox);
        genrePanel.add(adventureCheckBox);
        genrePanel.add(animationCheckBox);
        genrePanel.add(comedyCheckBox);
        genrePanel.add(crimeCheckBox);
        genrePanel.add(documentaryCheckBox);
        genrePanel.add(dramaCheckBox);
        genrePanel.add(familyCheckBox);
        genrePanel.add(fantasyCheckBox);
        genrePanel.add(horrorCheckBox);
        genrePanel.add(romanceCheckBox);
        genrePanel.add(scienceFictionCheckBox);
        genrePanel.add(thrillerCheckBox);

        return genrePanel;
    }

    private JPanel createNumberPanel() {
        final JPanel numberPanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        numberPanel.add(new JLabel("Minimum rating:"));
        numberPanel.add(minimumRatingField);

        numberPanel.add(new JLabel("Earliest year:"));
        numberPanel.add(earliestYearField);

        numberPanel.add(new JLabel("Latest year:"));
        numberPanel.add(latestYearField);

        return numberPanel;
    }

    private JPanel createFilterButtonPanel() {
        final JPanel buttonPanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        final JButton applyButton =
                new JButton("Apply Filters");
        final JButton clearButton =
                new JButton("Clear Filters");

        applyButton.addActionListener(
                event -> applyFilters()
        );

        clearButton.addActionListener(
                event -> clearFilters()
        );

        buttonPanel.add(applyButton);
        buttonPanel.add(clearButton);

        return buttonPanel;
    }

    private void applyFilters() {
        if (filterController == null) {
            showInputError("Filter is not connected.");
        }
        else {
            try {
                final FilterCriteria criteria =
                        new FilterCriteria(
                                getSelectedLanguages(),
                                parseDouble(minimumRatingField),
                                getSelectedGenreIds(),
                                parseInteger(earliestYearField),
                                parseInteger(latestYearField)
                        );

                messageLabel.setText(" ");
                filterController.execute(criteria);
            }
            catch (NumberFormatException exception) {
                showInputError(
                        "Rating and years must contain valid numbers."
                );
            }
        }
    }

    private void clearFilters() {
        clearLanguageSelections();
        clearGenreSelections();

        minimumRatingField.setText("");
        earliestYearField.setText("");
        latestYearField.setText("");
        messageLabel.setText(" ");

        if (filterController != null) {
            final FilterCriteria emptyCriteria =
                    new FilterCriteria(
                            new ArrayList<>(),
                            null,
                            new ArrayList<>(),
                            null,
                            null
                    );

            filterController.execute(emptyCriteria);
        }
    }

    private List<String> getSelectedLanguages() {
        final List<String> languages = new ArrayList<>();

        addLanguageIfSelected(
                englishCheckBox, "en", languages
        );
        addLanguageIfSelected(
                frenchCheckBox, "fr", languages
        );
        addLanguageIfSelected(
                spanishCheckBox, "es", languages
        );
        addLanguageIfSelected(
                chineseCheckBox, "zh", languages
        );
        addLanguageIfSelected(
                japaneseCheckBox, "ja", languages
        );
        addLanguageIfSelected(
                koreanCheckBox, "ko", languages
        );

        return languages;
    }

    private void addLanguageIfSelected(
            JCheckBox checkBox,
            String languageCode,
            List<String> languages) {

        if (checkBox.isSelected()) {
            languages.add(languageCode);
        }
    }

    private List<Integer> getSelectedGenreIds() {
        final List<Integer> genreIds = new ArrayList<>();

        addGenreIfSelected(
                actionCheckBox, ACTION_GENRE_ID, genreIds
        );
        addGenreIfSelected(
                adventureCheckBox, ADVENTURE_GENRE_ID, genreIds
        );
        addGenreIfSelected(
                animationCheckBox, ANIMATION_GENRE_ID, genreIds
        );
        addGenreIfSelected(
                comedyCheckBox, COMEDY_GENRE_ID, genreIds
        );
        addGenreIfSelected(
                crimeCheckBox, CRIME_GENRE_ID, genreIds
        );
        addGenreIfSelected(
                documentaryCheckBox, DOCUMENTARY_GENRE_ID, genreIds
        );
        addGenreIfSelected(
                dramaCheckBox, DRAMA_GENRE_ID, genreIds
        );
        addGenreIfSelected(
                familyCheckBox, FAMILY_GENRE_ID, genreIds
        );
        addGenreIfSelected(
                fantasyCheckBox, FANTASY_GENRE_ID, genreIds
        );
        addGenreIfSelected(
                horrorCheckBox, HORROR_GENRE_ID, genreIds
        );
        addGenreIfSelected(
                romanceCheckBox, ROMANCE_GENRE_ID, genreIds
        );
        addGenreIfSelected(
                scienceFictionCheckBox,
                SCIENCE_FICTION_GENRE_ID,
                genreIds
        );
        addGenreIfSelected(
                thrillerCheckBox, THRILLER_GENRE_ID, genreIds
        );

        return genreIds;
    }

    private void addGenreIfSelected(
            JCheckBox checkBox,
            int genreId,
            List<Integer> genreIds) {

        if (checkBox.isSelected()) {
            genreIds.add(genreId);
        }
    }

    private Double parseDouble(JTextField field) {
        final String text = field.getText().trim();
        Double result = null;

        if (!text.isEmpty()) {
            result = Double.valueOf(text);
        }

        return result;
    }

    private Integer parseInteger(JTextField field) {
        final String text = field.getText().trim();
        Integer result = null;

        if (!text.isEmpty()) {
            result = Integer.valueOf(text);
        }

        return result;
    }

    private void showInputError(String message) {
        messageLabel.setText(message);

        JOptionPane.showMessageDialog(
                this,
                message,
                "Invalid Filter",
                JOptionPane.ERROR_MESSAGE
        );
    }

    private void clearLanguageSelections() {
        englishCheckBox.setSelected(false);
        frenchCheckBox.setSelected(false);
        spanishCheckBox.setSelected(false);
        chineseCheckBox.setSelected(false);
        japaneseCheckBox.setSelected(false);
        koreanCheckBox.setSelected(false);
    }

    private void clearGenreSelections() {
        actionCheckBox.setSelected(false);
        adventureCheckBox.setSelected(false);
        animationCheckBox.setSelected(false);
        comedyCheckBox.setSelected(false);
        crimeCheckBox.setSelected(false);
        documentaryCheckBox.setSelected(false);
        dramaCheckBox.setSelected(false);
        familyCheckBox.setSelected(false);
        fantasyCheckBox.setSelected(false);
        horrorCheckBox.setSelected(false);
        romanceCheckBox.setSelected(false);
        scienceFictionCheckBox.setSelected(false);
        thrillerCheckBox.setSelected(false);
    }

    public void setFilterController(
            FilterController filterController) {
        this.filterController = filterController;
    }

    public void setMediaDetailController(
            MediaDetailController mediaDetailController) {
        this.mediaDetailController = mediaDetailController;
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final SearchResultState state =
                (SearchResultState) evt.getNewValue();

        resultsPanel.removeAll();

        if (state.getFilterError() != null) {
            messageLabel.setText(state.getFilterError());
        }
        else if (state.getResults() == null
                || state.getResults().isEmpty()) {
            messageLabel.setText("No results found.");
        }
        else {
            messageLabel.setText(countMessage(state));
            state.getResults().forEach(this::addMediaResult);
        }

        // Only offer more when the source actually has more to give.
        loadMoreButton.setVisible(state.isMoreAvailable());
        loadMoreButton.setEnabled(true);
        loadMoreButton.setText(LOAD_MORE_LABEL);

        resultsPanel.revalidate();
        resultsPanel.repaint();
        this.revalidate();
        this.repaint();
    }

    /** Describes how much of the result set is on screen. */
    private String countMessage(SearchResultState state) {
        final int shown = state.getResults().size();
        final int loaded = state.getOriginalResults().size();
        final int total = state.getTotalResults();

        final String message;
        if (shown < loaded) {
            message = shown + " of " + loaded + " loaded result(s) match your filters.";
        }
        else if (state.isMoreAvailable() && total > loaded) {
            message = "Showing " + shown + " of " + total + " result(s).";
        }
        else {
            message = shown + " result(s) found.";
        }
        return message;
    }

    /** Fetches the next block of results without blocking the window. */
    private void loadMore() {
        final SearchResultState state = searchResultViewModel.getState();
        final String keyword = state.getKeyword();
        final int nextPage = state.getNextPage();

        loadMoreButton.setEnabled(false);
        loadMoreButton.setText(LOADING_LABEL);

        new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                searchController.loadMore(keyword, nextPage);
                return null;
            }

            @Override
            protected void done() {
                loadMoreButton.setEnabled(true);
                loadMoreButton.setText(LOAD_MORE_LABEL);
                try {
                    get();
                }
                catch (InterruptedException exception) {
                    Thread.currentThread().interrupt();
                }
                catch (ExecutionException exception) {
                    ErrorReporter.show(SearchResultView.this, exception.getCause());
                }
            }
        }.execute();
    }

    public void setSearchController(SearchController searchController) {
        this.searchController = searchController;
    }

    private void addMediaResult(Media media) {
        final JPanel mediaPanel =
                new JPanel(new FlowLayout(FlowLayout.LEFT));

        mediaPanel.setMaximumSize(
                new Dimension(
                        RESULT_PANEL_WIDTH, RESULT_PANEL_HEIGHT
                )
        );

        final JLabel mediaLabel =
                new JLabel(
                        media.getTitle()
                                + " ("
                                + media.getReleaseYear()
                                + ")"
                );

        final JButton detailButton =
                new JButton("Detail");

        detailButton.addActionListener(event -> showMediaDetail(media));

        mediaPanel.add(mediaLabel);
        mediaPanel.add(detailButton);
        resultsPanel.add(mediaPanel);
    }

    private void showMediaDetail(Media media) {
        if (mediaDetailController != null) {
            mediaDetailController.execute(media);
        }
        else {
            JOptionPane.showMessageDialog(
                    this,
                    "Media detail is not connected.",
                    "Detail Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    public String getViewName() {
        return viewName;
    }
}
