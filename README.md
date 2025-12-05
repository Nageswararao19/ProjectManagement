📱 Project & Tasks Manager App___________________________________________
A simple Android application built using one Activity and two Fragments to manage Projects and their Tasks efficiently. The app features dynamic searching, navigation drawer actions, dialogs for creating new items, and contextual UI behavior.

🚀 Overview_____________________________________
The app consists of:
MainActivity
ProjectsFragment
TasksFragment
When the app launches, it directly opens the ProjectsFragment using a FragmentContainerView.
The top toolbar displays the title Dashboard along with a global search bar.

🧭 Navigation Flow
🔹 App Launch
Opens ProjectsFragment by default.
Displays all projects through an adapter.
🔹 Navigation Drawer
The drawer contains two main actions:
Projects → Navigates to ProjectsFragment
Create New Project → Opens dialog to add a new project


🔍 Global Search Feature
The search bar in MainActivity works for both fragments:

▶️ When in ProjectsFragment
Typing in the search bar:
Filters and displays only matching projects
Search works in real-time using adapter filtering

▶️ When in TasksFragment
Typing in the search bar:
Filters and displays tasks related to the selected project
Also works in real-time
Search behavior automatically adapts to the currently visible fragment.

➕ Creating Projects & Tasks (FAB Behavior)
A Floating Action Button (FAB) in MainActivity changes behavior depending on the active fragment:

🟩 In ProjectsFragment
FAB opens a dialog to Create New Project
And fill the data in all the fields and when you click on crete button that project will add and show in the adapter at 0 index
if you click cancel the dialogue will dismiss
you should have to fill all the data then only it will create

🟦 In TasksFragment
FAB opens a dialog to Create New Task under the current project
The app intelligently detects the active fragment and shows the correct dialog.
And fill the data in all the fields and when you click on crete button that project will add and show in the adapter at 0 index
if you click cancel the dialogue will dismiss
you should have to fill all the data then only it will create

📁 Moving from Project → Tasks
Each project card contains a "View Tasks" button.
When clicked:
It navigates to TasksFragment
Passes the selected Project object
TasksFragment reads the Project object and loads all tasks connected to it
Tasks are displayed through an adapter inside TasksFragment

🗂️ Architecture Summary
Component	Description
MainActivity	Hosts toolbar, search bar, navigation drawer, and FAB
ProjectsFragment	Displays all projects, handles project search, shows dialog to create project
TasksFragment	Displays tasks for a selected project, handles task search, shows dialog to create task
✨ Key Features

✔️ Fragment-based UI
✔️ NavigationDrawer integrated with toolbar
✔️ Context-aware Search (Projects or Tasks)
✔️ FAB button with dual behavior
✔️ Dialogs for creating new projects and tasks
✔️ Clean navigation from projects → tasks
✔️ Adapter-level filtering for efficient search
✔️ Project object passed safely between fragments

📸 UI Summary (Conceptual)
Dashboard Toolbar
Search Bar (Global for both fragments)
Projects List with "View Tasks" button
Tasks List (for selected project only)
Floating Action Button (context-aware)
Navigation Drawer with project shortcuts

🏁 Conclusion
This project demonstrates:
Smart fragment navigation
Reusable and dynamic UI components
Clean data passing between fragments
Adaptive behavior of search and FAB
Efficient list management using adapters

