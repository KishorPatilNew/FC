# FC
Project the find Fake gold bar with minimum number of weighings for any possible
fake bar position.

Getting started :
------------------------------

```
Make sure git is installed to clone the git repository.

git clone https://github.com/KishorPatilNew/FC.git

cd FC

```

Install Required Softwares :
------------------------------

```
1. Install Java in your local.
   Add JAVA_HOME environment variable with value of installed jdk dir. 
   Update PATH variable to include JAVA_HOME bin dir.

2. Download and extract latest Apache maven https://maven.apache.org/download.cgi
   Add M2_HOME environment variable with value of installed maven dir. 
   Add M2 environment variable with value of M2_HOME bin dir.
   Update PATH variable to include M2_HOME bin dir.
   
3. Download and install Chrome browser to latest version.
4. If you are using Windows ignore this step.
   Else download chromedriver file of specific OS you are using from
   And replace it at location <your_Workspace>\FC\main\resources\chromedriver.exe
   If downloaded filename is different than 'chromedriver.exe' then update 'chromeDriverFilePath' variable in file <your_Workspace>\FC\src\main\java\tests\fakeBar\DetectFakeBar.java
```

Running the tests:
-------------------
```
Execute below commands to run the script. The script executes as Java application.
From local terminal/command line
1> cd <your_Workspace>\FC project directory
2> mvn clean install
3> mvn compile exec:java

It will start running test locally by opening chrome browser and perform all the actions to find the fake gold bar.
The output will be printed on console as below

***************** Program Output ******************************
Fake Gold Bar is Number :1
Alert message: Yay! You find it!
Weighings:
[0,1,2] < [3,4,5]
[0] > [1]
**** Program Ends!

```

Test script (Spec)
--------------------------
```

Spec: <your_Workspace>\FC\src\main\java\tests\fakeBar\DetectFakeBar.java

Program to detect fake gold bar using minimum number of weighings for any possible fake bar position with given nine fake bars

```

Algorithm
----------------------------
```
The best possible algorithm is divide 9 bars in 3 groups. this gives maximum and minimum 2 weighings to find any possible position of fake bar.
 * Step 1: Weigh first two groups, if they weigh same, then the fake bar exists in third group.
 * 		  If first group weighs more than second, then the fake bar exists in second group.
 *        If first group weighs less than second, then the fake bar exists in first group.
 * Step 2: Step 1 gives the Group which weighs less. 
 * 			Now from this group; weigh first two numbers in this group, if they weigh same, then the fake bar is third number.
 *          if first number is greater than second, then the fake bar is second number.
 *          if first number is less than second, then the fake bar is first number.
 * Step 3: Step 2 confirms which number is the fake bar.
 * This way, It needs minimum and maximum two weighings to find the fake gold bar at any position.
   
```


Framework Details
----------------------------
```
It's a Maven enabled Java Project uses Selenium Webdriver
Uses Xpath/Ids to find elements on page.
Optimized code using dynamic locator strategy.

```


