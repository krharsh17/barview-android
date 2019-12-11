
# BarView

[![Build status](https://ci.appveyor.com/api/projects/status/7jihqvjy458qhko8?svg=true)](https://ci.appveyor.com/project/krharsh17/barview-android) 	![JitPack](https://img.shields.io/jitpack/v/github/krharsh17/barview-android?color=%23FFAE42) 	![GitHub release (latest by date)](https://img.shields.io/github/v/release/krharsh17/barview-android)

## Installation

- In your project level build.gradle
```
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```

- In your app level build.gradle
```
dependencies {
	        implementation 'com.github.krharsh17:barview-android:1.0'
	}
```

## Usage

- XML
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

- Important
	- All `app:` attributes are optional
	- The default values of `app:` attributes are mentioned above
	- `barGroupSpacing` & `barHeight` are in dp, while `valueTextSize` & `labelTextSize` are in sp


- Java
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
