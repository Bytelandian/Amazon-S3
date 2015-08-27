# Amazon-S3
Application to store and retreive files from AWS S3 cloud storage. The files stored are encrypted using RSA and AES based encryption.

### Design
For design see [Documentation.pdf](Documentation.pdf).

### Features
1. Ability to select an encryption key pair to be used.
2. Ability to select a local file to be stored in cloud.
3. Ability to select a directory file to be stored in cloud.
4. List the files stored on S3.
5. Retrieve back the files stored on S3.
6. Show progress of operation to the user.

### How to Run
**Application require AWS Toolkit.**

#### Requirements
1. AWS Account and S3 Enabled.
2. The app requires Access Key ID and Secret Access Key to use AWS API. These can be added to the credentials file present in "/home/username/.aws/" folder.

#### Steps using Terminal

>Compile

    javac -cp lib/aws-java-sdk-1.9.23.jar:lib/commons-logging-1.1.3.jar:lib/jackson-databind-2.3.2.jar:lib/jackson-core-2.3.2.jar:lib/jackson-annotations-2.3.0.jar:lib/httpclient-4.3.jar:lib/httpcore-4.3.jar:lib/joda-time-2.2.jar *.java

>Run

    java -cp .:lib/aws-java-sdk-1.9.23.jar:.:lib/commons-logging-1.1.3.jar:lib/jackson-databind-2.3.2.jar:lib/jackson-core-2.3.2.jar:lib/jackson-annotations-2.3.0.jar:lib/httpclient-4.3.jar:lib/httpcore-4.3.jar:lib/joda-time-2.2.jar MyAWS

#### Steps for compilation using Eclipse IDE (3.6 or above version):

1. Install AWS Toolkit for Eclipse.

    1. Open Help → Install New Software.
    2. Enter "http://aws.amazon.com/eclipse" in the text box labeled “Work with” at the top of the dialog.
    3. Select “AWS Toolkit for Eclipse” from the list below.
    4. Click “Next.” Eclipse will guide you through the remaining installation steps.
2. Download the latest AWS Java SDK from the following link:
http://sdk-for-java.amazonwebservices.com/latest/aws-java-sdk.zip
3. Extract it into a directory.
4. Set the SDK directory in Eclipse Preferences.
Window → Preferences → AWS Toolkit → AWS SDK For Java
5. Import the project 'MyAWS' into Eclipse.
Goto File → Import → Existing Project into Workspace and then select the MyAWS folder.
6. The App can be executed by choosing Run option in the Run menu.

#### To use Proxy
Update file [proxy](proxy.properties), set useProxy=True and add host,port, username and password.


### Interface
The app gives 3 options:

1. Choose File: This will open a dialog box to choose a file to upload on cloud. On selecting the file, file upload starts and its progress is shown.
2. Choose Folder: This will open a dialog box to choose a directory to upload on cloud. The directory and all the files and directories contained it are uploaded on the cloud.
3. List Object on S3: This will show a list of all files present on the S3 storage. To download a file, double click on the file name and choose the location to download the file. Then, download will start and progress is shown.
