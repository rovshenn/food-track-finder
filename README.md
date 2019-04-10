# Open Food Trucks Today

This program displays food trucks that are currently open.

## Getting Started

To develop and deploy this project locally you can use IDE of your choice that supports Java. Intelij Idea community edition is recommended.

### Prerequisites

You need to have Java 1.8+ installed on your machine before building and running this app. To copy this code download the code as a zip file or clone it
to your local machine use below git command (I assume you already have git installed locally).

```
git clone https://github.com/rovshenn/food-track-finder.git
cd food-track-finder
```

### Installing

To build the app run the following command from the project root dir, in my case "D:\git\food-track-finder\"

```
gradlew build
```

Your project should have compiled and you should see a new folder called build. Now we need to package our app as a runnable jar,
so we can run it anywhere. Run below command to package the build.

```
gradlew fatJar
```

Finally navigate to the folder where the runnable jar was created. In my case it is "D:\git\food-track-finder\build\libs"
and run the jar using command below

```
cd build\libs
java -jar food-track-finder-0.1.jar
```

And you should see data as shown below

```
D:\git\food-track-finder\build\libs>java -jar food-track-finder-0.1.jar
NAME                          ADDRESS
Mob Dog                       720 MARKET ST
Alfaro Truck                  306 VALENCIA ST
San Pancho's Tacos            491 BAY SHORE BLVD
Tacos El Primo                345 WILLIAMS AVE
El Tonayense #60              2355 FOLSOM ST
Wu Wei LLC dba MoBowl         370 DRUMM ST
Santana ESG, Inc.             200 SHOTWELL ST
Tacos Santana                 2142 JERROLD AVE
Linda's Catering              260 TOWNSEND ST
Subs on Hubs                  Assessors Block 8711/Lot021

Press any key to see more...

San Francisco's Hometown Creamery281 GEARY ST

We hope you found a nice place!
```

## Acknowledgments

* Thanks to Intelij Idea developers for a free IDEA
* Gradle developers for a great build tool
* Github for a free hosting, etc.
