# ForegroundAppInfo
A simple Android library to read an App Information that currently runs in foreground.

## Permissions
Before using it, make sure you already have required permission for getting task information in your `AndroidManifest.xml` (For kitkat and below).
```
<uses-permission android:name="android.permission.GET_TASKS" />
```

For android Lolipop and above, you have to declare UsageStats permission in `AndroidManifest.xml`,
and then make sure users already enable Usage Access for your app.

```
<!-- AndroidManifest.xml -->
<uses-permission android:name="android.permission.PACKAGE_USAGE_STATS"/>
```

```
// YourStuff.kt
...
val info = ForegroundAppInfo(this)
if (info.checkUsageStatsMode() != AppOpsManager.MODE_ALLOWED) {
    // open usage access setting
    startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
}
...
```

# Usage

### Reading the info directly
```
val current: ApplicationInfo = ForegroundAppInfo(this).read()

Log.i(TAG, "Current top app is: ${current.packageName}, uid: ${current.uid}")
```
### Continuously listen the app info
```
val info = ForegroundAppInfo(this)

// start
info.startListening {
    if (it.packageName == context.packageName) {
        Log.i(TAG, "I'm the current top app!")
    } else {
        Log.i(TAG, "Current top app is: ${it.packageName}, uid: ${it.uid}")
    }
}

// Change the pool interval
info.timeout = 2500L // milliseconds

// stop
info.stopListening()
```

# Download

Add JitPack repo to the project `build.gradle`
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}

```

Then add this to your app `build.gradle`
```
dependencies {
	implementation 'com.github.nullxception:ForegroundAppInfo:1.0.1'
}
```
