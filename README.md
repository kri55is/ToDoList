# Pre-work - Todo

*Todo* is an android app that allows building a todo list and basic todo items management functionality including adding new items, editing and deleting an existing item.

Submitted by: Emilie Brisseau (github user: kri55is)

Time spent: 4 hours spent in total

## User Stories

The following **required** functionality is completed:

* [x] User can **successfully add and remove items** from the todo list
* [x] User can **tap a todo item in the list and bring up an edit screen for the todo item** and then have any changes to the text reflected in the todo list.
* [x] User can **persist todo items** and retrieve them properly on app restart

The following **optional** features are (not yet) implemented:

* [ ] Persist the todo items [into SQLite](http://guides.codepath.com/android/Persisting-Data-to-the-Device#sqlite) instead of a text file
* [ ] Improve style of the todo items in the list [using a custom adapter](http://guides.codepath.com/android/Using-an-ArrayAdapter-with-ListView)
* [ ] Add support for completion due dates for todo items (and display within listview item)
* [ ] Use a [DialogFragment](http://guides.codepath.com/android/Using-DialogFragment) instead of new Activity for editing items
* [ ] Add support for selecting the priority of each todo item (and display in listview item)
* [x] Tweak the style improving the UI / UX, play with colors, images or backgrounds

The following **additional** features *could be* implemented:

Customize style with:
* [ ] splashscreen
* [ ] original icon  
Have a way to tell the item has been done by one of the followinf solutions:
* [ ] have a check button with every item in the list view
* [ ] being able to "cross" an item by swiping it (as a user would do to cross a liine on a paper with a pen). The item would be displayed but striked through.

* [ ] Being able to diplay or not the item that have been done 
* [ ] Being able to create different kind of lists: work/personnal/...
* [ ] Better handling of errors and exceptions
* [ ] Better logging system using more TAGs with LogCat


## Video Walkthrough

Here's a walkthrough of implemented user stories:

<img src='http://i.imgur.com/Uz4jY4Z.gif' width="600" />

GIF created with [LiceCap](http://www.cockos.com/licecap/).

## Project Analysis

As part of your pre-work submission, please reflect on the app and answer the following questions below:

**Question 1:** "What are your reactions to the Android app development platform so far? Compare and contrast Android's approach to layouts and user interfaces in past platforms you've used."

**Answer:** I had used Android Studio in the past to build small apps and I enjoyed the different approaches it gives: we can either go with the UI or edit the xml manually. In the past I didn't use Genymotion to emulate a mobile use, I find it very easy to use and a lot faster 
than the built-in emulator. I also use a real android phone because even if I can only verify if all is all right on this particulat phone and Android version it gives the real impression because in the real life users don't use mouse.
I find it very confortable to use the tools you recommanded when they are finally all installed (some packaged on Android Studio take a very long time).

Except the Android apps I already built in the past I have mostly wrote code without interface so it is difficult for me to compare. I can say that I really enjoy building Android app also because we can see results very fast and that it can be used by everybody.

**Question 2:** "Take a moment to reflect on the `ArrayAdapter` used in your pre-work. How would you describe an adapter in this context and what is its function in Android? Why do you think the adapter is important? Explain the purpose of the `convertView` in the `getView` method of the `ArrayAdapter`."

**Answer:** [Enter your answer here in a paragraph or two].

## Notes

Describe any challenges encountered while building the app.

## License

    Copyright [yyyy] [name of copyright owner]

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

        http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.
