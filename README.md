# Square Demo

SquareDemo shows a list of employees parsed from an employee directory API

Focus Areas - Architecture
Model-View-Viewmodel (MVVM) design pattern: UI data survives screen configuration changes.
E.g. No unnecessary API requests need to be made when the user rotates their screen.
Images are cached on disk and memory, minimizing the need to download images from the web.
Each class is focused on a single responsibility as much as possible.

Copied-in code: ViewModelProviderFactory.java.  Viewmodels do not play nicely with Dagger by default.
This is a very well-known problem, and this class is a common method of allowing Viewmodels to be used with Dagger.

Device focused: Phone

Time spent on project: 5 hours

Developed by: Leon Cam