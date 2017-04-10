# AndroidFilePicker
android file picker ,you can choose image,video,file,etc..
Image selector library for Android. Support single choice、multi-choice、cropping image and preview image.

![](https://github.com/BrillantZhao/AndroidFilePicker/blob/master/screenshot/device-2017-04-10-114356.png)
![](https://github.com/BrillantZhao/AndroidFilePicker/blob/master/screenshot/device-2017-04-10-114406.png)
![](https://github.com/BrillantZhao/AndroidFilePicker/blob/master/screenshot/device-2017-04-10-114415.png)

## Quick start

1) Add Library module as a dependency in your build.gradle file.

or

```xml
dependencies {
    compile 'com.github.BrillantZhao:AndroidFilePicker:1.0.2'
}
```

2) Declare permission in your AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

3) 图片选择 

```java
int mode = selectMode.getCheckedRadioButtonId() == R.id.mode_multiple ? ImagePickActivity.MODE_MULTIPLE : ImagePickActivity.MODE_SINGLE;
boolean isShow = showCamera.getCheckedRadioButtonId() == R.id.camera_yes ? true : false;
boolean isPreview = enablePreview.getCheckedRadioButtonId() == R.id.preview_enable ? true : false;
boolean isCrop = enableCrop.getCheckedRadioButtonId() == R.id.crop_enable ? true : false;

ImagePickActivity.start(ImageActivity.this, maxSelectNum, mode, isShow, isPreview, isCrop, ImagePickActivity.REQUEST_IMAGE);
```
same this

```java
    /**
     * 启动图片选择页面
     *
     * @param activity
     * @param maxSelectNum  最大选择图片数量
     * @param mode          单选or多选
     * @param isShow        是否展示照相机
     * @param enablePreview 是否允许预览
     * @param enableCrop    是否允许裁剪
     * @param requestCode   请求码
     */
    public static void start(Activity activity, int maxSelectNum, int mode, boolean isShow,
                             boolean enablePreview, boolean enableCrop, int requestCode) {
        Intent intent = new Intent(activity, ImagePickActivity.class);
        intent.putExtra(EXTRA_MAX_SELECT_NUM, maxSelectNum);
        intent.putExtra(EXTRA_SELECT_MODE, mode);
        intent.putExtra(EXTRA_SHOW_CAMERA, isShow);
        intent.putExtra(EXTRA_ENABLE_PREVIEW, enablePreview);
        intent.putExtra(EXTRA_ENABLE_CROP, enableCrop);
        activity.startActivityForResult(intent, requestCode);
    }
```
 Receive result in your onActivityResult Method

``` java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == ImagePickActivity.REQUEST_IMAGE) {
            ArrayList<ImageFile> images = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
            // do something ...
        }
    }
```
4) 视频选择 

```java
boolean isShow = showCamera.getCheckedRadioButtonId() == R.id.camera_yes ? true : false;

VideoPickActivity.start(VideoActivity.this, maxSelectNum, isShow,VideoPickActivity.REQUEST_VIDEO);
```
same this

```java
       /**
      * 启动视频选择
      *
      * @param activity
      * @param maxSelectNum 最大选择数量
      * @param isShow       是否展示摄像头
      * @param requestCode   请求码
      */
     public static void start(Activity activity, int maxSelectNum, boolean isShow, int requestCode) {
         Intent intent = new Intent(activity, VideoPickActivity.class);
         intent.putExtra(EXTRA_MAX_SELECT_NUM, maxSelectNum);
         intent.putExtra(EXTRA_SHOW_CAMERA, isShow);
         activity.startActivityForResult(intent, requestCode);
     }
```
 Receive result in your onActivityResult Method

``` java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == VideoPickActivity.REQUEST_VIDEO) {
            ArrayList<VideoFile> videoFiles = data.getParcelableArrayListExtra(Constant.RESULT_PICK_VIDEO);
            // do something...
        }
    }
```

5) 文件选择 

```java
String[] mSuffix = {"txt"};

NormalFilePickActivity.start(FileActivity.this, maxSelectNum, mSuffix, NormalFilePickActivity.REQUEST_FILE);
```
same this

```java
    /**
     * 启动文件选择
     *
     * @param activity
     * @param maxSelectNum 最大选择数量
     * @param mSuffix 文件格式集合
     * @param requestCode   请求码
     */
    public static void start(Activity activity, int maxSelectNum, String[] mSuffix, int requestCode) {
        Intent intent = new Intent(activity, NormalFilePickActivity.class);
        intent.putExtra(EXTRA_MAX_SELECT_NUM, maxSelectNum);
        intent.putExtra(EXTRA_STUFFIX, mSuffix);
        activity.startActivityForResult(intent, requestCode);
    }
```
 Receive result in your onActivityResult Method

``` java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == NormalFilePickActivity.REQUEST_FILE) {
            ArrayList<NormalFile> normalFiles = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
            // do something...
        }
    }
```

5) 音频选择 

```java
boolean isNeedRecorder = showCamera.getCheckedRadioButtonId() == R.id.camera_yes ? true : false;

AudioPickActivity.start(AudioActivity.this, maxSelectNum, isNeedRecorder, AudioPickActivity.REQUEST_AUDIO);
```
same this

```java
    /**
     * 启动录音选择
     *
     * @param activity
     * @param maxSelectNum   最大选择数量
     * @param isNeedRecorder 是否需要录音
     * @param requestCode   请求码
     */
    public static void start(Activity activity, int maxSelectNum, boolean isNeedRecorder, int requestCode) {
        Intent intent = new Intent(activity, AudioPickActivity.class);
        intent.putExtra(EXTRA_MAX_SELECT_NUM, maxSelectNum);
        intent.putExtra(IS_NEED_RECORDER, isNeedRecorder);
        activity.startActivityForResult(intent, requestCode);
    }
```
 Receive result in your onActivityResult Method

``` java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == AudioPickActivity.REQUEST_AUDIO) {
            ArrayList<AudioFile> audioFiles = data.getParcelableArrayListExtra(Constant.RESULT_PICK_VIDEO);
            // do something...
        }
    }
```

## Thanks

* [Glide](https://github.com/bumptech/glide)

* [PhotoView](https://github.com/chrisbanes/PhotoView)

* [simplecropview](https://github.com/IsseiAoki/SimpleCropView)

###License
>The MIT License (MIT)

>Copyright (c) 2015 Dee

>Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

>The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.
