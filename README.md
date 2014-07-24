[BITalino](http://www.bitalino.com) DataLogger for Android
===
Android application to store data-logs from the BITalino board in the smartphone. 

## Features
* Select between different bluetooth devices (only works with BITalino protocol compliant devices)
* Configure the sampling rate and the channels to store data
* App external storage mode - use any usb to uSD card converter to get the logs or a file explorer app to access the BITlog folder in your Android.

## Configuration for Eclipse
To run properly this project first you must download the bitalino-java-sdk and the Guava libraries: 
* bitalino-java-sdk https://github.com/BITalinoWorld/java-sdk
* Guava https://code.google.com/p/guava-libraries/

After downloading the code of the datalogger for Android, add them to the build path ( Project -> Properties -> Java Build Path ): 
* bitalinojava-sdk as a project in the Projects tab 
![](https://github.com/BITalinoWorld/android-datalogger-egokituz/blob/master/readme-images/step-by-step/bitalino-logger-conf-projects.png)
* guava-17.0.jar as an external library in Libraries tab
![](https://github.com/BITalinoWorld/android-datalogger-egokituz/blob/master/readme-images/step-by-step/bitalino-logger-conf-external-jars.png)

** VERY IMPORTANT!, change the order in the "Order and Export" tab to avoid runtime errors with the libraries: **
* Before modifying the order:
![Before modifying the order](https://github.com/BITalinoWorld/android-datalogger-egokituz/blob/master/readme-images/step-by-step/bitalino-logger-conf-before.png)
* After modifying the order
![After modifying the order](https://github.com/BITalinoWorld/android-datalogger-egokituz/blob/master/readme-images/step-by-step/bitalino-logger-conf-after.png)

    
## Screenshots
**Application Main Screen:**

![Main screen](https://github.com/BITalinoWorld/android-datalogger-egokituz/blob/master/readme-images/main1.png)

**BITalino configuration screen:**

![Bitalino configuration screen](https://github.com/BITalinoWorld/android-datalogger-egokituz/blob/master/readme-images/Configuration.png)

Credits
===
[Borja Gamecho](https://github.com/bgamecho)

[University of the Basque Country (UPV/EHU)](http://www.ehu.es/)

[EGOKITUZ](http://www.egokituz.org/) laboratory 
