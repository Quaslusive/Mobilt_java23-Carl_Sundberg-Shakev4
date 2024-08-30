I havecreated and application that utilizes the accelerometer and gyroscope sensors to detect motion and orientation changes. 
The app is structured around the `MainActivity`, which implements the `SensorEventListener` interface. 
The accelerometer data is used to update seekbars and detect shaking motion, displaying a Toast message when significant shaking occurs. 
Additionally, the gyroscope data is used to rotate an `ImageView` ("Weird Ball") around the z-axis, creating a dynamic visual effect. 
The app also uses the accelerometer's x and y values to determine the device's flatness, changing the background color of a `FLATHET` to green for maximum flatness and red otherwise. 


![Screenshot_20240830_162732](https://github.com/user-attachments/assets/4a56af5a-96c4-4415-9d40-cf808860be29)
