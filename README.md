# Project 3 - *Instagram*

**Instagram** is a photo sharing app using Parse as its backend.

Time spent: **18** hours spent in total

## User Stories

The following **required** functionality is completed:

- [x] Style the login page to look like the real Instagram login page.
- [x] User can sign up to create a new account using Parse authentication
- [x] User can log in and log out of his or her account
- [x] The current signed in user is persisted across app restarts
- [x] User can take a photo, add a caption, and post it to "Instagram"
- [x] User can view the last 20 posts submitted to "Instagram"
- [x] User can pull to refresh the last 20 posts submitted to "Instagram"
- [x] User can tap a post to view post details, including timestamp and caption.

The following **optional** features are implemented:

- [x] Style the feed to look like the real Instagram feed.
- [x] User should switch between different tabs - viewing all posts (feed view), capture (camera and photo gallery view) and profile tabs (posts made) using a Bottom Navigation View.
- [ ] User can load more posts once he or she reaches the bottom of the feed using infinite scrolling.
- [x] Show the username and creation time for each post
- [ ] After the user submits a new post, show an indeterminate progress bar while the post is being uploaded to Parse
- User Profiles:
   - [x] Allow the logged in user to add a profile photo
   - [x] Display the profile photo with each post
   - [ ] Tapping on a post's username or profile photo goes to that user's profile page
- [ ] User can comment on a post and see all comments for each post in the post details screen.
- [ ] User can like a post and see number of likes for each post in the post details screen.
- [x] Create a custom Camera View on your phone.
- [x] Run your app on your phone and use the camera to take the photo


The following **additional** features are implemented:

- [x] User can switch between front-facing and back-facing cameras.
- [x] For taking profile pictures, camera opens in front-facing mode.
- [x] After submitting a new post, the user is automatically taken to the timeline fragment.
- [x] Custom CameraView lets user take photos in squares, just like old Instagram format.

Please list two areas of the assignment you'd like to **discuss further with your peers** during the next class (examples include better ways to implement something, how to extend your app in certain ways, etc):

1. I'd like to further discuss how to style UI, especially in apps with many fragments and activities.
2. I would want to dive deeper into how Parse and ParseObjects work, especially getting and setting info with the server.

## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/link/to/your/gif/file.gif' title='Video Walkthrough' width='' alt='Video Walkthrough' />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Credits

List an 3rd party libraries, icons, graphics, or other assets you used in your app.

- [Android Async Http Client](http://loopj.com/android-async-http/) - networking library
- Glide Image Loading and Transformations
- ButterKnife view binding
- Parse backend
- Parceler parcelable ease
- PermissionDispatcher for getting permissions


## Notes

Doing the UI at the end was hard, but it was a really fun finish to the project. Dealing with all the different fragments got to be a little confusing, but by the end I think I got the hang of it.

## License

    Copyright 2018 Alex Goodman

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
