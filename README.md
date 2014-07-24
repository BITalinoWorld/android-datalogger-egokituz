[BITalino](http://www.bitalino.com) DataLogger for Android
===
Android application to store data-logs from the BITalino board in the smartphone. 

## Features 
Unmarked features are going to be added eventually
- [x] Select between different bluetooth devices (only works with BITalino protocol compliant devices)
- [x] Configure the sampling rate and the channels to store data. Channel labels are sorted with the Board model order, if you have a Bitalino Plugged or Freestyle models they could be in a different order
- [x] App external storage mode 
  - Use a file explorer app to access the BITlog folder in your Android
  - Use any usb to uSD card converter to get the logs 
- [x] [OpenSignals](http://bitalino.com/index.php/software) like logs with selected analog channel and digital input data. 
- [ ] App internal storage mode (disabled)
  - Send logs via email (not working yet)

## Configuration for Eclipse
1. To run properly this project first you must download the bitalino-java-sdk and the Guava libraries: 
  * bitalino-java-sdk https://github.com/BITalinoWorld/java-sdk
  * Guava https://code.google.com/p/guava-libraries/
2. After downloading the code of the datalogger for Android, add the previously mentioned projects to the build path ( Project -> Properties -> Java Build Path )
  * bitalinojava-sdk as a project in the Projects tab 
![](https://github.com/BITalinoWorld/android-datalogger-egokituz/blob/master/readme-images/step-by-step/bitalino-logger-conf-projects.png)
  * guava-17.0.jar as an external library in Libraries tab
![](https://github.com/BITalinoWorld/android-datalogger-egokituz/blob/master/readme-images/step-by-step/bitalino-logger-conf-external-jars.png) 
3. VERY IMPORTANT!, change the order in the "Order and Export" tab to avoid runtime errors with the libraries 
  * Before modifying the order:
![Before modifying the order](https://github.com/BITalinoWorld/android-datalogger-egokituz/blob/master/readme-images/step-by-step/bitalino-logger-conf-before.png)
  * After modifying the order:
![After modifying the order](https://github.com/BITalinoWorld/android-datalogger-egokituz/blob/master/readme-images/step-by-step/bitalino-logger-conf-after.png)

4. Run the application and enjoy gathering data from your BITalino device :)
 
## Screenshots
#### **Application Main Screen:**

![Main screen](https://github.com/BITalinoWorld/android-datalogger-egokituz/blob/master/readme-images/main1.png)

#### **BITalino configuration screen:**

![Bitalino configuration screen](https://github.com/BITalinoWorld/android-datalogger-egokituz/blob/master/readme-images/Configuration.png)

##Credits
[Borja Gamecho](https://github.com/bgamecho)

[University of the Basque Country (UPV/EHU)](http://www.ehu.es/)

[EGOKITUZ](http://www.egokituz.org/) laboratory 
