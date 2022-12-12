
# Multibank MVI

Application for restaurant to manage their order, 

1- User click button (Place Order) to create order with unique number and it will add on the queue list. First In first out


2- Order have 4 states (New, Preparing, Ready, Delivered)


3- User click on the button state of each order to switch it to the next state


4- Delivered state order will disappear from the list after 15 seconds.

 

5- User can navigate to a Detail page of the order.


## Language and Architecture
KOTLIN

MVI
## UI and Component Description
1.Toolbar with navigation components label(Single Activity with two fragments using navigation graph)

2.Button to Place order (Creates an entry in the database using Room database and di(koin) for modules )

3.Recycle view and button with 4 state (New, Preparing, Ready, Delivered)

4.Once the button changes to the delivered state will disappear in 15 seconds


https://user-images.githubusercontent.com/8912909/207075549-0f8cb3ac-778b-49ab-b8f0-d3d37923a62b.mp4

