##How to update your apktool to v2.0
###Solving brut.androlib.AndrolibException
**Note:** This installation is for Mac OS X. 
This guide is targeted towards solving the following error:
```
I: Baksmaling…
I: Loading resource table...
Exception in thread "main" brut.androlib.AndrolibException: Could not decode arsc file
	at brut.androlib.res.decoder.ARSCDecoder.decode(ARSCDecoder.java:56)
	at brut.androlib.res.AndrolibResources.getResPackagesFromApk(AndrolibResources.java:491)
	at brut.androlib.res.AndrolibResources.loadMainPkg(AndrolibResources.java:74)
	at brut.androlib.res.AndrolibResources.getResTable(AndrolibResources.java:66)
	at brut.androlib.Androlib.getResTable(Androlib.java:50)
	at brut.androlib.ApkDecoder.getResTable(Androlib.java:189)
	at brut.androlib.ApkDecoder.decode(ApkDecoder.java:114)
	at brut.apktool.Main.cmdDecode(Main.java:146)
	at brut.apktool.Main.main(Main.java:77)
Caused by: java.io.IOException: Expected: 0x001c0001, got: 0x00000000
	at brut.util.ExtDataInput.skipCheckInt(ExtDataInput.java:48)
	at brut.androlib.res.decoder.StringBlock.read(StringBlock.java:44)
	at brut.androlib.res.decoder.ARSCDecoder.readPackage(ARSCDecoder.java:102)
	at brut.androlib.res.decoder.ARSCDecoder.readTable(ARSCDecoder.java:83)
	at brut.androlib.res.decoder.ARSCDecoder.decode(ARSCDecoder.java:83)
	… 8 more
```
* Download the script from the following URL and name it "apktool". Note that the file name has no file extension. 
	- https://github.com/iBotPeaches/Apktool/blob/master/scripts/osx/apktool

* Download the latest jar file from the following URL and rename it to "apktool.jar"
	- https://bitbucket.org/iBotPeaches/apktool/downloads

* Move both files (apktool.jar & apktool) to a folder called tools and run the following command.
	- Command: `chmod +x apktool`

* **Important step:** If you had an older version of apktool installed then please navigate to 
this directory (/Users/you_name/Library/apktool/framework/) and delete the "1.apk" file.

* Try using the apktool to make sure everything is working.
