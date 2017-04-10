package com.vincent.filepicker.activity;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.vincent.filepicker.Constant;
import com.vincent.filepicker.DividerGridItemDecoration;
import com.vincent.filepicker.IntentCacheHelper;
import com.vincent.filepicker.R;
import com.vincent.filepicker.adapter.ImagePickAdapter;
import com.vincent.filepicker.adapter.OnSelectStateListener;
import com.vincent.filepicker.filter.FileFilter;
import com.vincent.filepicker.filter.callback.FilterResultCallback;
import com.vincent.filepicker.filter.entity.Directory;
import com.vincent.filepicker.filter.entity.ImageFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.vincent.filepicker.activity.ImageBrowserActivity.IMAGE_BROWSER_SELECTED_NUMBER;

/**
 * Created by Vincent Woo
 * Date: 2016/10/12
 * Time: 16:41
 */

public class ImagePickActivity extends BaseActivity {

    //=== request code
    public final static int REQUEST_IMAGE = 66;

    public final static int REQUEST_CAMERA = 67;

    public final static int MODE_MULTIPLE = 1;

    public final static int MODE_SINGLE = 2;

    //===
    public static final int DEFAULT_MAX_NUMBER = 9;

    public static final int COLUMN_NUMBER = 3;

    private int mCurrentNumber = 0;

    private Toolbar mTbImagePick;

    private RecyclerView mRecyclerView;

    private ImagePickAdapter mAdapter;

    private ArrayList<ImageFile> mSelectedList = new ArrayList<>();

    //=== 传递过来的参数name
    public final static String EXTRA_SELECT_MODE = "SelectMode";

    public final static String EXTRA_SHOW_CAMERA = "ShowCamera";

    public final static String EXTRA_ENABLE_PREVIEW = "EnablePreview";

    public final static String EXTRA_ENABLE_CROP = "EnableCrop";

    public final static String EXTRA_MAX_SELECT_NUM = "MaxSelectNum";

    //===
    private int maxSelectNum = 9;

    private int selectMode = MODE_MULTIPLE;

    private boolean showCamera = true;

    private boolean enablePreview = true;

    private boolean enableCrop = false;

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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setContentView(R.layout.activity_image_pick);

        maxSelectNum = getIntent().getIntExtra(EXTRA_MAX_SELECT_NUM, DEFAULT_MAX_NUMBER);
        selectMode = getIntent().getIntExtra(EXTRA_SELECT_MODE, MODE_MULTIPLE);
        showCamera = getIntent().getBooleanExtra(EXTRA_SHOW_CAMERA, true);
        enablePreview = getIntent().getBooleanExtra(EXTRA_ENABLE_PREVIEW, true);
        enableCrop = getIntent().getBooleanExtra(EXTRA_ENABLE_CROP, false);

        if (selectMode == MODE_MULTIPLE) {
            enableCrop = false;
        } else {
            maxSelectNum = 1;
            enablePreview = false;
        }
        initView();
        super.onCreate(savedInstanceState);
    }

    @Override
    void permissionGranted() {
        loadData();
    }

    private void initView() {
        mTbImagePick = (Toolbar) findViewById(R.id.tb_image_pick);
        mTbImagePick.setTitle(mCurrentNumber + "/" + maxSelectNum);
        setSupportActionBar(mTbImagePick);
        mTbImagePick.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mRecyclerView = (RecyclerView) findViewById(R.id.rv_image_pick);
        GridLayoutManager layoutManager = new GridLayoutManager(this, COLUMN_NUMBER);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.addItemDecoration(new DividerGridItemDecoration(this));
        mAdapter = new ImagePickAdapter(this, showCamera, maxSelectNum);
        mRecyclerView.setAdapter(mAdapter);

        mAdapter.setOnSelectStateListener(new OnSelectStateListener<ImageFile>() {
            @Override
            public void OnSelectStateChanged(boolean state, ImageFile file) {
                if (state) {
                    mSelectedList.add(file);
                    mCurrentNumber++;
                } else {
                    mSelectedList.remove(file);
                    mCurrentNumber--;
                }
                mTbImagePick.setTitle(mCurrentNumber + "/" + maxSelectNum);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case Constant.REQUEST_CODE_TAKE_IMAGE:
                if (resultCode == RESULT_OK) {
                    Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
                    if (mAdapter.mImagePath != null) {
                        File file = new File(mAdapter.mImagePath);
                        Uri contentUri = Uri.fromFile(file);
                        mediaScanIntent.setData(contentUri);
                        sendBroadcast(mediaScanIntent);
                        loadData();
                    }
                }
                break;
            case Constant.REQUEST_CODE_BROWSER_IMAGE:
                if (resultCode == RESULT_OK) {
                    //ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_BROWSER_IMAGE);
                    ArrayList<ImageFile> list = (ArrayList<ImageFile>) IntentCacheHelper.getInstance().getIntentValue(Constant.RESULT_BROWSER_IMAGE);
                    mCurrentNumber = data.getIntExtra(IMAGE_BROWSER_SELECTED_NUMBER, 0);
                    mAdapter.setCurrentNumber(mCurrentNumber);
                    mTbImagePick.setTitle(mCurrentNumber + "/" + maxSelectNum);
                    refreshSelectedList(list);
                    mAdapter.refresh(list);
                }
                break;
            case ImageCropActivity.REQUEST_CROP: {
                if (resultCode == RESULT_OK) {
                    ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
                    onSelectDone(list);
                }
                break;
            }
        }
    }

    private void loadData() {
        FileFilter.getImages(this, new FilterResultCallback<ImageFile>() {
            @Override
            public void onResult(List<Directory<ImageFile>> directories) {
                List<ImageFile> list = new ArrayList<>();
                for (Directory<ImageFile> directory : directories) {
                    list.addAll(directory.getFiles());
                }

                for (ImageFile file : mSelectedList) {
                    int index = list.indexOf(file);
                    if (index != -1) {
                        list.get(index).setSelected(true);
                    }
                }
                mAdapter.refresh(list);
            }
        });
    }

    private void refreshSelectedList(List<ImageFile> list) {
        for (ImageFile file : list) {
            if (file.isSelected() && !mSelectedList.contains(file)) {
                mSelectedList.add(file);
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_image_pick, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_done) {
            if (enableCrop && mSelectedList.size() > 0) {
                startCrop(mSelectedList.get(0));
            } else {
                onSelectDone(mSelectedList);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * @param imageFile
     */
    public void startCrop(ImageFile imageFile) {
        ImageCropActivity.startCrop(this, imageFile);
    }

    /**
     * @param mSelectedList
     */
    public void onSelectDone(ArrayList<ImageFile> mSelectedList) {
        Intent intent = new Intent();
        intent.putParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE, mSelectedList);
        setResult(RESULT_OK, intent);
        finish();
    }
}
