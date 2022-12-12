# mvi-framework-android
Source code of Model View Intent framework and sample application writen using this framework.
template file -sarath-2022

Language : Kotlin
Architecture :MVI Pattern

Model: This is  to ensure that it will be updated from only one place.
View: represent the UI layer, the activity, or the fragment, it defines the user actions and renders the state emitted by the model.
Intent: Action Either by user or by app itself.

App Description:

UI:
1.Toolbar with navigation components label(Single Activity with two fragments using navigation graph)
2.Button to Place order (Creates an entry in the database using Room database and di(koin) for modules )
3.Recycle view and button with 4 state (New, Preparing, Ready, Delivered)
4.Once the button changes to the delivered state will disappear in 15 seconds


https://user-images.githubusercontent.com/8912909/207075549-0f8cb3ac-778b-49ab-b8f0-d3d37923a62b.mp4

