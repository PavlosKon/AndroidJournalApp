# AndroidJournalApp

# CONCEPT
A personal journal app for Android using Java and Firebase.

The app is based on a concept in psychoanalysis called 'Shadow Work',  which was popularized by the psychiatrist and psychoanalyst Carl Jung.
It's an analysis of the self which looks at the parts of ourselves that we reject because we don't want to deal with or acknowledge them.

# OVERVIEW
The way that this app utilizes that concept is by presenting the user with a question of a deeper nature (e.g. What is your deepest fear, What is the saddest memory of your childhood)
to prompt the user to self-reflect and search within for the answers.

These questions are sourced from a Pool of Questions which is driven by the community - the users of the app. Each user has the ability to submit questions to the pool for the rest of the users to upvote or downvote.
If a question gathers enough upvotes then it goes into the Approved Questions Pool, from which a question is presented to the user at random each day.

# FEATURES
This app has the following features:
  - User Authentication: Secure login and registration functionalities using Firebase Authentication.
  - Create new journal entries which are automatically stamped with the current date.
  - The user can submit their own questions to the questions pool. Each question can be approved or rejected by the community via way of downvotes/upvotes.
  - Firebase Realtime Database: Store user data, journal entries and user submitted questions securely in the cloud.
  - Add new questions to the questions pool. Upvote or downvote existing ones which are pending approval by the community.
  - Change app theme to Day/Night mode.

# DEPENDENCIES
  - Firebase Authentication: For user authentication.
  - Firebase Realtime Database: For cloud database management.
  - Scalable DP: For scalable size unit that helps with UI consistency across different devices.

# NOTES
The app is currently only available in Greek, as it was developed for the purposes of the certificate program.
    

