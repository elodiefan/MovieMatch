package use_case.delete_account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import entity.StandardUserFactory;
import entity.User;

/** Tests for {@link DeleteAccountInteractor}. */
class DeleteAccountInteractorTest {

    @Test
    void correctSecurityAnswerDeletesAccountAndChatHistory() {
        final RecordingUserDao userDao = new RecordingUserDao("blue");
        final RecordingMessageDao messageDao = new RecordingMessageDao();
        final RecordingPresenter presenter = new RecordingPresenter();
        final DeleteAccountInteractor interactor = new DeleteAccountInteractor(
                userDao, messageDao, presenter, new StandardUserFactory());

        interactor.execute(input("blue"));

        assertNotNull(userDao.deletedUser);
        assertEquals("bob", userDao.deletedUser.getUsername());
        assertNull(userDao.currentUsername);
        assertEquals("bob", messageDao.deletedUsername);
        assertNotNull(presenter.success);
        assertNull(presenter.failure);
    }

    @Test
    void incorrectSecurityAnswerIsReportedWithoutDeletingAccount() {
        final RecordingUserDao userDao = new RecordingUserDao("blue");
        final RecordingMessageDao messageDao = new RecordingMessageDao();
        final RecordingPresenter presenter = new RecordingPresenter();
        final DeleteAccountInteractor interactor = new DeleteAccountInteractor(
                userDao, messageDao, presenter, new StandardUserFactory());

        interactor.execute(input("wrong"));

        assertNull(userDao.deletedUser);
        assertNull(messageDao.deletedUsername);
        assertNull(presenter.success);
        assertEquals("Incorrect security answer.", presenter.failure);
    }

    @Test
    void switchToPersonalAccountViewIsPassedToPresenter() {
        final RecordingPresenter presenter = new RecordingPresenter();
        final DeleteAccountInteractor interactor = new DeleteAccountInteractor(
                new RecordingUserDao("blue"), new RecordingMessageDao(), presenter,
                new StandardUserFactory());

        interactor.switchToPersonalAccountView();

        assertTrue(presenter.switchedViews);
    }

    private static DeleteAccountInputData input(final String answer) {
        return new DeleteAccountInputData(
                "bob", "Bob", "password", "Favourite colour?", answer);
    }

    private static final class RecordingUserDao
            implements DeleteAccountUserDataAccessInterface {
        private final String securityAnswer;
        private String currentUsername = "bob";
        private User deletedUser;

        private RecordingUserDao(final String inputSecurityAnswer) {
            securityAnswer = inputSecurityAnswer;
        }

        @Override
        public void deleteAccount(final User user) {
            deletedUser = user;
        }

        @Override
        public void setCurrentUsername(final String username) {
            currentUsername = username;
        }

        @Override
        public String getCurrentSecurityAnswer() {
            return securityAnswer;
        }
    }

    private static final class RecordingMessageDao
            implements DeleteAccountMessageDataAccessInterface {
        private String deletedUsername;

        @Override
        public void deleteChatHistory(final String username) {
            deletedUsername = username;
        }
    }

    private static final class RecordingPresenter implements DeleteAccountOutputBoundary {
        private DeleteAccountOutputData success;
        private String failure;
        private boolean switchedViews;

        @Override
        public void prepareSuccessView(final DeleteAccountOutputData outputData) {
            success = outputData;
        }

        @Override
        public void prepareFailView(final String errorMessage) {
            failure = errorMessage;
        }

        @Override
        public void switchToPersonalAccountView() {
            switchedViews = true;
        }
    }
}
