# AndroidFilePicker
android file picker ,you can choose image,video,file,etc..
Image selector library for Android. Support single choice、multi-choice、cropping image and preview image.

![](https://raw.githubusercontent.com/ioneday/ImageSelector/master/screenshot/Screenshot1.jpg)
![](https://raw.githubusercontent.com/ioneday/ImageSelector/master/screenshot/Screenshot2.jpg)
![](https://raw.githubusercontent.com/ioneday/ImageSelector/master/screenshot/Screenshot3.jpg)

![](https://raw.githubusercontent.com/ioneday/ImageSelector/master/screenshot/Screenshot4.jpg)
![](https://raw.githubusercontent.com/ioneday/ImageSelector/master/screenshot/Screenshot5.jpg)

## Quick start

1) Add Library module as a dependency in your build.gradle file.

or

```xml
dependencies {
    compile 'com.android.support:recyclerview-v7:22.2.1'
    compile 'com.github.bumptech.glide:glide:3.6.1'
    compile 'com.commit451:PhotoView:1.2.4'
    compile 'com.isseiaoki:simplecropview:1.0.13'
    compile 'com.yongchun:com.yongchun.imageselector:1.1.0'
}
```

2) Declare permission in your AndroidManifest.xml

```xml
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
<uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
```

3) 图片选择 

```java
ImagePickActivity.start(ImageActivity.this, maxSelectNum, mode, isShow, isPreview, isCrop);
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
     */
    public static void start(Activity activity, int maxSelectNum, int mode, boolean isShow, boolean enablePreview, boolean enableCrop) {
        Intent intent = new Intent(activity, ImagePickActivity.class);
        intent.putExtra(EXTRA_MAX_SELECT_NUM, maxSelectNum);
        intent.putExtra(EXTRA_SELECT_MODE, mode);
        intent.putExtra(EXTRA_SHOW_CAMERA, isShow);
        intent.putExtra(EXTRA_ENABLE_PREVIEW, enablePreview);
        intent.putExtra(EXTRA_ENABLE_CROP, enableCrop);
        activity.startActivityForResult(intent, REQUEST_IMAGE);
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
VideoPickActivity.start(VideoActivity.this, maxSelectNum, isShow);
```
same this

```java
 /**
     * 启动视频选择
     *
     * @param activity
     * @param maxSelectNum 最大选择数量
     * @param isShow       是否展示摄像头
     */
    public static void start(Activity activity, int maxSelectNum, boolean isShow) {
        Intent intent = new Intent(activity, VideoPickActivity.class);
        intent.putExtra(EXTRA_MAX_SELECT_NUM, maxSelectNum);
        intent.putExtra(EXTRA_SHOW_CAMERA, isShow);
        activity.startActivityForResult(intent, REQUEST_VIDEO);
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
NormalFilePickActivity.start(FileActivity.this, maxSelectNum, mSuffix);
```
same this

```java
/**
     * 启动文件选择
     *
     * @param activity
     * @param maxSelectNum 最大选择数量
     * @param mSuffix 文件格式集合
     */
    public static void start(Activity activity, int maxSelectNum, String[] mSuffix) {
        Intent intent = new Intent(activity, NormalFilePickActivity.class);
        intent.putExtra(EXTRA_MAX_SELECT_NUM, maxSelectNum);
        intent.putExtra(EXTRA_STUFFIX, mSuffix);
        activity.startActivityForResult(intent, REQUEST_FILE);
    }
```
 Receive result in your onActivityResult Method

``` java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == VideoPickActivity.REQUEST_VIDEO) {
            ArrayList<NormalFile> normalFiles = data.getParcelableArrayListExtra(Constant.RESULT_PICK_FILE);
            // do something...  
        }
    }
```

5) 音频选择 

```java
AudioPickActivity.start(AudioActivity.this, maxSelectNum, isNeedRecorder);
```
same this

```java
/**
     * 启动录音选择
     *
     * @param activity
     * @param maxSelectNum   最大选择数量
     * @param isNeedRecorder 是否需要录音
     */
    public static void start(Activity activity, int maxSelectNum, boolean isNeedRecorder) {
        Intent intent = new Intent(activity, AudioPickActivity.class);
        intent.putExtra(EXTRA_MAX_SELECT_NUM, maxSelectNum);
        intent.putExtra(IS_NEED_RECORDER, isNeedRecorder);
        activity.startActivityForResult(intent, REQUEST_AUDIO);
    }
```
 Receive result in your onActivityResult Method

``` java
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && requestCode == VideoPickActivity.REQUEST_VIDEO) {
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
