* In order to pull an apk from a non rooted device you are going to need the following tools
	- Android Debug Bridge (adb) //In near future I'll writeup a guide on how to setup adb on Ubuntu.
	- Linux operating system such as Ubuntu //I'm using Kail Linux VM.
	- An Android device //I'm using Nexus 5.
	- Package name of the apk your try to extract. 
	
**Important Note:** This process does not work if you encrypted your android device.
* One quick and easy way to find out the package name is by navigating to Google Play Store 
in a web browser and search for your app. Copy the URL of that page and paste it some place 
where you can read it clearly. The value of the id parameter is your package name.
	- For example the following URL is of the gmail app and its package name is "com.google.android.gm".
	- URL: https://play.google.com/store/apps/details?id=com.google.android.gm&hl=en
	
* Bootup your linux and connect your device to it. I'm using VMPlayer on a MacBook Pro, so after 
I plug in the phone I'm ask to choose which os to connect the phone to. Select "Connect to Linux".

* Open terminal app (a command prompt) and run the following commands:
Command1: `adb backup -apk -nosystem packageNameOfTheDesiredAPK`

* Aprove the backup from you device by selecting "Back up my data". After the process finishes you will
have a .ab file in your current working directory. 

Command2: `dd if=mybackup.ab bs=24 skip=1|openssl zlib -d > mybackup.tar` //This command convert the .ab file into a .tar file.

Command3: `tar xvf mybackup.tar` //Extracts the .tar file to your current working directory.

* Browse through the extracted tar files and folders to find the apk.


