# BarView

[![Codacy Badge](https://api.codacy.com/project/badge/Grade/896439f51e884b569b90e34527f06676)](https://app.codacy.com/manual/kharsh39/barview-android?utm_source=github.com&utm_medium=referral&utm_content=krharsh17/barview-android&utm_campaign=Badge_Grade_Dashboard)
[![Build Status](https://travis-ci.com/krharsh17/barview-android.svg?branch=develop)](https://travis-ci.com/krharsh17/barview-android) 	![JitPack](https://img.shields.io/jitpack/v/github/krharsh17/barview-android?color=%23FFAE42) 	![GitHub release (latest by date)](https://img.shields.io/github/v/release/krharsh17/barview-android)

[![](https://jitpack.io/v/krharsh17/barview-android/month.svg)](https://jitpack.io/#krharsh17/barview-android)

![](https://forthebadge.com/images/badges/built-for-android.svg)  ![](https://forthebadge.com/images/badges/check-it-out.svg)

BarView is a native android UI library that allows representation of data in bar graph with slick look and smooth animations. Currently the library is in a very early stage so it does not look very good 😋.

This project has been selected for **[GirlScript Summer of Code 2020](https://www.gssoc.tech/)**. To begin contributing
-  Go through the [guidelines](https://github.com/krharsh17/barview-android/blob/develop/CONTRIBUTING.md)
-  Fork a copy of the project to your profile
-  Go through the issues and comment on one to get it assigned to yourself
-  Discuss on the [gitter](https://gitter.im/barview-android/community) or [slack](https://gssoc20.slack.com/) channel if you have any doubts
-  Make the changes in code and create a PR on the main repository
-  Ask for a review from a mentor/admin and wait until your code gets merged!

## Table of Contents
-  [Goal](#goal)
-  [Current Progress](#current-progress)
-  [Installation](#installation)
-  [Usage](#usage)

## Goal

![Design](https://i.ibb.co/C8XtG1F/Metric-Screen-Bar-Graph.png)

## Current Progress

![Progress](https://i.imgur.com/ZKLBH03.png)

## Installation

-  In your project level build.gradle
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

-  In your app level build.gradle
```
dependencies {
	        implementation 'com.github.krharsh17:barview-android:1.0'
	}
```

## Usage

-  XML
```
<in.krharsh17.barview.BarView
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:id="@+id/barview"
        app:barHeight="20"
        app:valueTextSize="9"
        app:labelTextSize="18"
        app:labelTextColor="#424242"
        app:barGroupSpacing="16"
        app:rippleColor="#EEEEEE"
        app:valueTextColor="#FFFFFF"
        />
```

-  Important
	- All `app:` attributes are optional
	- The default values of `app:` attributes are mentioned above
	- `barGroupSpacing` & `barHeight` are in dp, while `valueTextSize` & `labelTextSize` are in sp


-  Java
```

BarView barview = findViewById(R.id.barview);

ArrayList<BarModel> barModels = new ArrayList<>();

barModels.add(new BarModel(
    "label_text",
    "value_text",
    "color_hex",
    fill_ratio_float
    )
);

//Add more BarModels here..

barview.setData(barModels);

```

## System Requirements

-  Git
-  Android Studio

## Learning Resources

-  https://developer.android.com/studio/install
-  https://try.github.io/

The demo apk for the project is available [here](https://github.com/krharsh17/barview-android/blob/develop/demo-apk/app-debug.apk)
