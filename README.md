# Seefood Application
### CEG4110 (Software Engineering) Group 6

Our SeeFood Android Application is designed as an entertaining piece of software that enables users to share the experience of Artificial Intelligence. Our mobile implementation of SeeFood will use a simple UI, web services, and a local device environment to unveil the enjoyment of testing the shrewdness of Machine Learning. The basic use of this SeeFood application is to be able to reliably detect if an image is of any kind of food. The application will allow the user to either take multiple images or upload from their phone’s gallery and be able to receive a reasonably-timed response from the AI informing the user if the image(s) contain food or not. This response would also indicate a confidence scale to graphically illustrate the AI’s confidence of its response to the image. Furthermore, this application will allow the user to view past images submitted by all users for analysis by the AI in a gallery format. This application should be functional as well as graphically appealing to the user. This will be accomplished by designing and implementing an Android application that uses an API to interact with the AI that will be hosted on an Amazon Web Services EC2 Virtual Machine.

The application can be launched on an android device by cloning the project, opening it up in android studio, and launching the application on the device. Below shows screenshots of the application in action.

<img src="https://user-images.githubusercontent.com/22596783/49658039-0f1fdd00-fa0f-11e8-95b9-d19eec7eaab5.jpg" width="300" height="550">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://user-images.githubusercontent.com/22596783/49658074-265eca80-fa0f-11e8-9d0e-1a4618efb72b.jpg" width="300" height="550">

Above, you can see the home page of the application. It gives the user the option to capture an image, upload an image, or visit the gallery. It also has a help dialog on the top right corner of the page that gives the user information on the application and how to use it.

Once the user selects/captures an image, the user will be redirected to the second page to confirm their image selection. This is where
they have the option to add more images by selecting either the upload image icon (right icon) or capture image icon (left icon). The user can also remove specific images by selecting the "x" on the top right corner of the image. The user can then select the eye icon in the middle to send their selected images to the AI and get redirected to the application gallery where they will be able to select any 
of the tested images and view the response from the AI.

<img src="https://user-images.githubusercontent.com/22596783/49658227-91a89c80-fa0f-11e8-974f-287c72687fb6.jpg" width="300" height="550">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://user-images.githubusercontent.com/22596783/49658302-bbfa5a00-fa0f-11e8-8bcb-1b2bc6881c84.jpg" width="300" height="550">

The gallery also gives the user the capability to like their favorite images by clicking on the image's heart icon. They can then filter based on their favorited images and only be able to see those by clicking on the heart icon on the top right corner of the gallery view (as shown below). Users are also able to delete images from the gallery if they decide they do not want to see an image anymore. This is done by clicking on the edit icon on the top right of the gallery view, selecting the "x" of the images they wish to delete, and clicking done.

<img src="https://user-images.githubusercontent.com/22596783/49658619-75f1c600-fa10-11e8-929e-2361a7e2ce0e.jpg" width="300" height="550">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<img src="https://user-images.githubusercontent.com/22596783/49658655-915cd100-fa10-11e8-8c57-40187155a2c5.jpg" width="300" height="550">
