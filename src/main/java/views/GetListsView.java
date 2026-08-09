package views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.MalformedURLException;
import java.net.URI;
import java.util.List;
import java.util.concurrent.ExecutionException;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingWorker;

import interface_adapter.get_lists.GetListRow;
import interface_adapter.get_lists.GetListsController;
import interface_adapter.get_lists.GetListsState;
import interface_adapter.get_lists.GetListsViewModel;
import interface_adapter.media_detail.MediaDetailController;

/**
 * The View for a user's personal account lists.
 */

public class GetListsView extends JPanel implements PropertyChangeListener {

    private static final int TEXT_PADDING = 10;
    private static final int CARD_GAP = 10;
    private static final int POSTER_WIDTH = 80;
    private static final int POSTER_HEIGHT = 120;
    private static final String POSTER_BASE_URL =
            "https://image.tmdb.org/t/p/w185";

    private final String viewName = "view lists";
    private GetListsViewModel getListsViewModel;
    private GetListsController getListsController;
    private MediaDetailController mediaDetailController;

    private final JLabel viewMessage;
    private final JTextArea userList;
    private final JPanel listPanel;

    public GetListsView(GetListsViewModel getListsViewModel) {
        this.getListsViewModel = getListsViewModel;
        this.getListsViewModel.addPropertyChangeListener(this);

        viewMessage = new JLabel("", SwingConstants.CENTER);
        UiTheme.asTitle(viewMessage);
        final JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.add(viewMessage, BorderLayout.CENTER);
        labelPanel.setBorder(BorderFactory.createEmptyBorder(0, 0, TEXT_PADDING, 0));

        userList = new JTextArea();
        // This is a read-out of the user's list, not somewhere to type.
        userList.setEditable(false);
        userList.setLineWrap(true);
        userList.setWrapStyleWord(true);
        userList.setBorder(BorderFactory.createEmptyBorder(
                TEXT_PADDING, TEXT_PADDING, TEXT_PADDING, TEXT_PADDING));
        listPanel = new JPanel();
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setBorder(BorderFactory.createEmptyBorder(
                TEXT_PADDING, TEXT_PADDING, TEXT_PADDING, TEXT_PADDING));
        final JScrollPane scrollPane = new JScrollPane(listPanel);

        final JPanel returnPanel = new JPanel();
        final JButton returnButton = new JButton(GetListsViewModel.RETURN_BUTTON);
        returnPanel.add(returnButton);

        returnButton.addActionListener(
                new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        final GetListsState state = getListsViewModel.getState();
                        getListsController.switchToAccountView(state.getUsername(), state.getDisplayName());
                    }
                }
        );

        // BorderLayout so the list itself takes all the spare room when the
        // window is resized, rather than the heading and button drifting apart.
        this.setLayout(new BorderLayout());
        this.add(labelPanel, BorderLayout.NORTH);
        this.add(scrollPane, BorderLayout.CENTER);
        this.add(returnPanel, BorderLayout.SOUTH);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        if (evt.getPropertyName().equals("state")) {
            final GetListsState state = (GetListsState) evt.getNewValue();
            viewMessage.setText(state.getDisplayName() + state.getListLabel());
            updateListContent(state);
        }
    }

    private void updateListContent(GetListsState state) {
        listPanel.removeAll();
        final List<GetListRow> rows = state.getListRows();
        if (rows.isEmpty()) {
            userList.setText(state.getDisplayText());
            userList.setCaretPosition(0);
            listPanel.add(userList);
        }
        else {
            for (GetListRow row : rows) {
                listPanel.add(createListCard(row));
                listPanel.add(Box.createVerticalStrut(CARD_GAP));
            }
        }
        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createListCard(GetListRow row) {
        final JPanel card = new JPanel(new BorderLayout(CARD_GAP, 0));
        card.setBorder(BorderFactory.createEmptyBorder(
                CARD_GAP, CARD_GAP, CARD_GAP, CARD_GAP));
        card.setAlignmentX(Component.LEFT_ALIGNMENT);
        final JLabel posterLabel = createPosterLabel(row.getPosterPath());
        addMediaClickListener(posterLabel, row);
        card.add(posterLabel, BorderLayout.WEST);

        final JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));
        final JLabel titleLabel = new JLabel(row.getMediaTitle());
        addMediaClickListener(titleLabel, row);
        final JLabel dateLabel = new JLabel(formatLoggedAt(row.getLoggedAt()));
        textPanel.add(titleLabel);
        textPanel.add(dateLabel);
        card.add(textPanel, BorderLayout.CENTER);
        return card;
    }

    private JLabel createPosterLabel(String posterPath) {
        final JLabel posterLabel = new JLabel();
        posterLabel.setPreferredSize(new Dimension(POSTER_WIDTH,
                POSTER_HEIGHT));
        posterLabel.setHorizontalAlignment(JLabel.CENTER);
        posterLabel.setVerticalAlignment(JLabel.CENTER);
        updatePoster(posterLabel, posterPath);
        return posterLabel;
    }

    private void updatePoster(JLabel posterLabel, String posterPath) {
        posterLabel.setIcon(null);
        if (posterPath == null || posterPath.isEmpty()) {
            posterLabel.setText("Poster unavailable");
        }
        else {
            posterLabel.setText("Loading...");
            loadPosterImage(posterLabel, posterPath);
        }
    }

    private void loadPosterImage(JLabel posterLabel, String posterPath) {
        new SwingWorker<ImageIcon, Void>() {
            @Override
            protected ImageIcon doInBackground()
                    throws MalformedURLException {
                final ImageIcon original = new ImageIcon(URI.create(
                        POSTER_BASE_URL + posterPath).toURL());
                final Image scaled = original.getImage().getScaledInstance(
                        POSTER_WIDTH, POSTER_HEIGHT, Image.SCALE_SMOOTH);
                return new ImageIcon(scaled);
            }

            @Override
            protected void done() {
                try {
                    posterLabel.setIcon(get());
                    posterLabel.setText("");
                }
                catch (InterruptedException | ExecutionException error) {
                    posterLabel.setText("Poster unavailable");
                    Thread.currentThread().interrupt();
                }
            }
        }.execute();
    }

    private String formatLoggedAt(String loggedAt) {
        final String formattedDate;
        if (loggedAt == null || loggedAt.length() < TEXT_PADDING) {
            formattedDate = "";
        }
        else {
            formattedDate = loggedAt.substring(0, TEXT_PADDING);
        }
        return formattedDate;
    }

    private void addMediaClickListener(Component component, GetListRow row) {
        component.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent event) {
                openMediaDetail(row);
            }
        });
    }

    private void openMediaDetail(GetListRow row) {
        if (mediaDetailController != null) {
            mediaDetailController.execute(row.getMediaId(),
                    row.getMediaType(), row.getMediaTitle(), 0,
                    row.getPosterPath());
        }
    }

    public void setGetListsController(GetListsController getListsController) {
        this.getListsController = getListsController;
    }

    public void setMediaDetailController(
            MediaDetailController inputMediaDetailController) {
        this.mediaDetailController = inputMediaDetailController;
    }

    public String getViewName() {
        return viewName;
    }
}
