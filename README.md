# MovieMatch
MovieMatch is a social meida-logging application that allows users to search for movies and TV shows, receive 
personalized recommendations, and share their opinions. The project was created to address the difficultey of 
finding trustworthy media reccomentations and meaningful spaces for discussion. It provides a supportive community 
where users can discuver reliable reviews and connect with others through messaging and social interactions.

For more information, please check our presentation slides.

## Table of Contents

1. [Application Features and Usage Guide](#application-features-and-usage-guide)
2. [Installation Instructions](#installation-instructions)
3. [Common Issues and Troubleshooting](#common-issues-and-troubleshooting)
4. [Feedback](#feedback)
5. [Contributing](#contributing)
6. [License](#license)
7. [Authors and Contributors](#authors-and-contributors)

## Application Features and Usage Guide
1. Registeration/Login Page

| Sign up page | Login page |
| --- | --- |
| <img src="docs/images/signup-page.png" alt="Sign up page" width="420"> | <img src="docs/images/login-page.png" alt="Login page" width="420"> |

- The signup page is the first screen that shows up when the program runs successfully. 
- The signup and login pages allow users to create and access their MovieMatch accounts. 
- During sign-up, users enter a username, display name, password, repeated password, security question, and security answer. The security question is used later if the user needs to reset their password. 
- After creating an account, users can go to the login page and sign in with their username and password.

2. Homepage and Recommendations

| Home page | Recommendations page |
| --- | --- |
| <img src="docs/images/home-page.png" alt="Home page" width="420"> | <img src="docs/images/recommendations-page.png" alt="Recommendations page" width="420"> |

- After logging in, users are taken to the home page, where they can see a personalized welcome message and a preview of recommended movies or TV shows.
- Users can select "See all recommendations" to open a full recommendations page with more suggested media based on their preferences and activity.
- From the home page, users can also navigate to search, find other users, open their account page, or change their settings.

3. Media Search and Details

| Search page | Search results page |
| --- | --- |
| <img src="docs/images/search-page.png" alt="Search page" height="320"> | <img src="docs/images/search-results-page.png" alt="Search results page" height="320"> |

- Users can search for movies and TV shows by entering a keyword on the search page.
- After opening a media details page, users can add media to their watchlist if they want to watch it later, or mark it as watched to add it to their watch history. Users must add a media item to their watch history before they can write a review for it.
- Search results display matching media with posters, titles, release years, and filter options for language, genre, rating, and year.

| Media details page | Community reviews and comments |
| --- | --- |
| <img src="docs/images/media-detail-page.png" alt="Media details page" width="420"> | <img src="docs/images/community-reviews-comments-page.jpg" alt="Community reviews and comments" width="420"> |

- On each media details page, users can read community reviews, write their own reviews, and reply to other users through comments. 
- Users can also edit or delete their own reviews and comments, and like or unlike reviews and comments from the community.

4. Search for Users

| Find users page | Other user profile page |
| --- | --- |
| <img src="docs/images/find-users-page.png" alt="Find users page" width="420"> | <img src="docs/images/other-user-profile-page.png" alt="Other user profile page" width="420"> |

| Messaging page | Watch history page |
| --- | --- |
| <img src="docs/images/messaging-page.png" alt="Messaging page" width="420"> | <img src="docs/images/watch-history-page.png" alt="Watch history page" width="420"> |
| Watchlist page |  |
| <img src="docs/images/watchlist-page.png" alt="Watchlist page" width="420"> |  |

- Users can search for other MovieMatch users by username or display name.
- After selecting a user, they can view that user's profile, including the user's username, display name, watchlist, watch history, and messaging options.
- Users can message other users from the profile page, view the media they want to watch, view the media they have already watched, or block users when needed.

5. Your Account

| Account page | Watchlist page |
| --- | --- |
| <img src="docs/images/my-account-page.png" alt="My account page" width="420"> | <img src="docs/images/my-watchlist-page.png" alt="My watchlist page" width="420"> |
| Watch history page | My reviews page |
| <img src="docs/images/my-watch-history-page.png" alt="My watch history page" width="420"> | <img src="docs/images/my-reviews-page.png" alt="My reviews page" width="420"> |
| My comments page | Blocked users page |
| <img src="docs/images/my-comments-page.png" alt="My comments page" width="420"> | <img src="docs/images/my-blocked-users-page.png" alt="My blocked users page" width="420"> |

- The account page lets users manage their profile and navigate to their saved media, reviews, comments, blocked users, and account settings.
- Users can view their watchlist and watch history with the media title, date added or watched, and poster image.
- The My Reviews page shows reviews the user has written, including the media poster, title, rating, created or updated time, likes, and review text.
- The Comments tab shows comments the user has written on reviews, with the related media, original review text, comment text, date, likes, and edit or delete options.
- Users can also view blocked users, change their username or display name, reset their password, log out, or delete their account.

6. Settings

| Settings page | Dark mode settings page |
| --- | --- |
| <img src="docs/images/settings-page.png" alt="Settings page" width="420"> | <img src="docs/images/settings-dark-mode-page.png" alt="Dark mode settings page" width="420"> |

- The settings page lets users customize their MovieMatch experience by switching between light mode and dark mode.
- Users can adjust the app's text size using the slider, making the interface easier to read based on their preference.
- Adult recommendations are turned off by default. Users can choose whether to show adult recommendations from this page.

## Installation Instructions

### Preresquisties
- Java JDK 17
- Apache Maven 3.0 or later
- MongoDB 8.0
- Git

### System Compatibility
MovieMatch has been tested on macOS and Windows

### Installation Steps

### Common Issues
- MongoDB connection errors
- Missing TMDB access
- Incorrect JAVA version

## Feedback
We welcome feedback that can help to improve MovieMatch. If you encounter a bug,
have a feature suggestion, or notice an issue with the documentation, please
open an issue in the
[MovieMatch GitHub repository](https://github.com/elodiefan/MovieMatch/issues).

A valid feedback should include: a clear description of problem, any idea of fixing
that and the expected behavior.

Before creating new issue, please check whether a similar issue has already reported.

## Contributing
Contributions of MovieMatch are welcomed. You may contribute by fixing bugs, adding
features or updating documentents.

Steps of forking reposity:
1. Open the
[MovieMatch GitHub repository](https://github.com/elodiefan/MovieMatch).
2. Select **Fork** in the upper-right corner of the GitHub page.
3. Choose your GitHub account as the owner and select **Create fork**.
4. Clone your fork:

   ```bash
   git clone https://github.com/<your-username>/MovieMatch.git
   cd MovieMatch
   ```

## License

MovieMatch is licensed under the [MIT License](LICENSE).


## Authors and contributors
Elodie， Enzo， Kiersten， Lily， Yidan
