# Seefood Application - Beta Version
### CEG4110 (Software Engineering) Deliverable 3 - Group 6

Our SeeFood Android Application is designed as an entertaining piece of software that enables users to share the experience of Artificial Intelligence. Our mobile implementation of SeeFood will use a simple UI, web services, and a local device environment to unveil the enjoyment of testing the shrewdness of Machine Learning. The basic use of this SeeFood application is to be able to reliably detect if an image is of any kind of food. The application will allow the user to either take multiple images or upload from their phone’s gallery and be able to receive a reasonably-timed response from the AI informing the user if the image(s) contain food or not. This response would also indicate a confidence scale to graphically illustrate the AI’s confidence of its response to the image. Furthermore, this application will allow the user to view past images submitted by all users for analysis by the AI in a gallery format. This application should be functional as well as graphically appealing to the user. This will be accomplished by designing and implementing an Android application that uses an API to interact with the AI that will be hosted on an Amazon Web Services EC2 Virtual Machine.

As the Beta version of our product, our application currently allows the user to perform major use-cases, implements all high priority functional requirements, and has an incomplete UI.
This means that our application currently satisfies the requirement of sending images to the server to be tested by the AI, 
recieving responses from the AI, saving the images and responses to the application's gallery, uploads images to be tested from 
gallery, uploads images taken from camera to be tested, compatible with common image types, and has the ability to send multiple 
images at once. In order to use our application, the EC2 server instance must be started with it's python server running.

Below shows screenshots of the application in action.

<img src="https://user-images.githubusercontent.com/22596783/48750265-3e330200-ec4c-11e8-89e5-fd0e3f7d1b63.png" width="300" height="550">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://user-images.githubusercontent.com/22596783/48750267-425f1f80-ec4c-11e8-89eb-0bbd8fd5f159.png" width="300" height="550">

Above, you can see the home page of the application. It gives the user the option to capture an image, upload an image, or visit the gallery.
Once the user selects/captures an image, the user will be redirected to the second page to confirm their image selection. This is where
they have the option to add more images by selected either upload image or capture image. The user can also remove specific images by
selecting the "x" on the top right corner of the image. The user can then select "TEST" to send their selected images to the AI.

<img src="https://user-images.githubusercontent.com/22596783/48750273-468b3d00-ec4c-11e8-9b6d-a4e68694eb63.png" width="300" height="550">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://user-images.githubusercontent.com/22596783/48750261-38d5b780-ec4c-11e8-9d2f-7b9bfe40bf0e.png" width="300" height="550">
<img src="https://user-images.githubusercontent.com/22596783/48750282-4c811e00-ec4c-11e8-9e47-0587510410b1.png" width="300" height="550">

Once the user selects to test their image, they will be redirected to the application gallery where they will be able to select any 
of the tested images and view the response from the AI.
