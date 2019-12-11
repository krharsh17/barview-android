# barview-android

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

barview.setData(barModels);
```
