Time Spent: ~11 hours total

Since receiving the project Tuesday I spent around an hour per night Tu-F getting started 
then put in an additional 2 hours Saturday and around 4 hours today (Sunday) :)

Bonus/Notes:

- Used MVP design architecture
- Written entirely in Kotlin save for the Room database implementation to show familiarity in Java
- The app only hits the endpoint once (unless manually refreshed), and then subsequent data accesses 
are done through local storage with Room database.
- With the end user in mind I chose to focus a significant amount of my development effort toward
ensuring reliability of the app in all possible states. I did a fair amount of manual testing to 
ensure that the app behaves correctly when rotated/backgrounded and handles permissions gracefully.
- Wrote unit tests for both of my presenters to show familiarity with Mockito.