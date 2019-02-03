package com.learner.fifawc2018wallpaper;

import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.learner.fifawc2018wallpaper.Model.Common;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageOptions;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

public class WallpaperCroperActivity extends AppCompatActivity implements CropImageView.OnSetImageUriCompleteListener,CropImageView.OnCropImageCompleteListener{

    CropImageView cropImageView;
    private CropImageOptions mOptions;
    Bitmap bitmap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_croper);
        cropImageView = findViewById(R.id.croper_image_view);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if(Common.SELECTED_WALLPAPER_BITMAP!=null){
            cropImageView.setImageBitmap(Common.SELECTED_WALLPAPER_BITMAP);
            //cropImageView.setImageUriAsync(Uri.parse(Common.SELECTED_WALLPAPER.getImageUrl()));
            //cropImageView.setImageResource(R.drawable.fifa_world_cup_2018);
        }

        mOptions = new CropImageOptions();

        cropImageView.setGuidelines(CropImageView.Guidelines.ON);
        cropImageView.setCropShape(CropImageView.CropShape.RECTANGLE);


    }

    @Override
    protected void onStart() {
        super.onStart();
        cropImageView.setOnSetImageUriCompleteListener(this);
        cropImageView.setOnCropImageCompleteListener(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        cropImageView.setOnSetImageUriCompleteListener(null);
        cropImageView.setOnCropImageCompleteListener(null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(com.theartofdev.edmodo.cropper.R.menu.crop_image_menu, menu);

        if (!mOptions.allowRotation) {
            menu.removeItem(com.theartofdev.edmodo.cropper.R.id.crop_image_menu_rotate_left);
            menu.removeItem(com.theartofdev.edmodo.cropper.R.id.crop_image_menu_rotate_right);
        } else if (mOptions.allowCounterRotation) {
            menu.findItem(com.theartofdev.edmodo.cropper.R.id.crop_image_menu_rotate_left).setVisible(true);
        }

        if (!mOptions.allowFlipping) {
            menu.removeItem(com.theartofdev.edmodo.cropper.R.id.crop_image_menu_flip);
        }

        if (mOptions.cropMenuCropButtonTitle != null) {
            menu.findItem(com.theartofdev.edmodo.cropper.R.id.crop_image_menu_crop).setTitle(mOptions.cropMenuCropButtonTitle);
        }

        Drawable cropIcon = null;
        try {
            if (mOptions.cropMenuCropButtonIcon != 0) {
                cropIcon = ContextCompat.getDrawable(this, mOptions.cropMenuCropButtonIcon);
                menu.findItem(com.theartofdev.edmodo.cropper.R.id.crop_image_menu_crop).setIcon(cropIcon);
            }
        } catch (Exception e) {
            Log.w("AIC", "Failed to read menu crop drawable", e);
        }

        if (mOptions.activityMenuIconColor != 0) {
            updateMenuItemIconColor(
                    menu, com.theartofdev.edmodo.cropper.R.id.crop_image_menu_rotate_left, mOptions.activityMenuIconColor);
            updateMenuItemIconColor(
                    menu, com.theartofdev.edmodo.cropper.R.id.crop_image_menu_rotate_right, mOptions.activityMenuIconColor);
            updateMenuItemIconColor(menu, com.theartofdev.edmodo.cropper.R.id.crop_image_menu_flip, mOptions.activityMenuIconColor);
            if (cropIcon != null) {
                updateMenuItemIconColor(menu, com.theartofdev.edmodo.cropper.R.id.crop_image_menu_crop, mOptions.activityMenuIconColor);
            }
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == com.theartofdev.edmodo.cropper.R.id.crop_image_menu_crop) {
            cropImage();
            return true;
        }
        if (item.getItemId() == com.theartofdev.edmodo.cropper.R.id.crop_image_menu_rotate_left) {
            rotateImage(-mOptions.rotationDegrees);
            return true;
        }
        if (item.getItemId() == com.theartofdev.edmodo.cropper.R.id.crop_image_menu_rotate_right) {
            rotateImage(mOptions.rotationDegrees);
            return true;
        }
        if (item.getItemId() == com.theartofdev.edmodo.cropper.R.id.crop_image_menu_flip_horizontally) {
            cropImageView.flipImageHorizontally();
            return true;
        }
        if (item.getItemId() == com.theartofdev.edmodo.cropper.R.id.crop_image_menu_flip_vertically) {
            cropImageView.flipImageVertically();
            return true;
        }
        if (item.getItemId() == android.R.id.home) {
            setResultCancel();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void updateMenuItemIconColor(Menu menu, int itemId, int color) {
        MenuItem menuItem = menu.findItem(itemId);
        if (menuItem != null) {
            Drawable menuItemIcon = menuItem.getIcon();
            if (menuItemIcon != null) {
                try {
                    menuItemIcon.mutate();
                    menuItemIcon.setColorFilter(color, PorterDuff.Mode.SRC_ATOP);
                    menuItem.setIcon(menuItemIcon);
                } catch (Exception e) {
                    Log.w("AIC", "Failed to update menu item color", e);
                }
            }
        }
    }

    /** Get intent instance to be used for the result of this activity. */
    protected Intent getResultIntent(Uri uri, Exception error, int sampleSize) {
        CropImage.ActivityResult result =
                new CropImage.ActivityResult(
                        cropImageView.getImageUri(),
                        uri,
                        error,
                        cropImageView.getCropPoints(),
                        cropImageView.getCropRect(),
                        cropImageView.getRotatedDegrees(),
                        cropImageView.getWholeImageRect(),
                        sampleSize);
        Intent intent = new Intent();
        intent.putExtras(getIntent());
        intent.putExtra(CropImage.CROP_IMAGE_EXTRA_RESULT, result);
        return intent;
    }

    /** Cancel of cropping activity. */
    protected void setResultCancel() {
        setResult(RESULT_CANCELED);
        finish();
    }

    /** Result with cropped image data or error if failed. */
    protected void setResult(Uri uri, Exception error, int sampleSize) {
        cropImageView.setImageUriAsync(uri);
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(this);
        try {
            InputStream inputStream = getContentResolver().openInputStream(uri);
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            wallpaperManager.setBitmap(bitmap);
            //Snackbar.make(linearLayout,"Wallpaper set",Snackbar.LENGTH_SHORT).show();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        /*int resultCode = error == null ? RESULT_OK : CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE;
        setResult(resultCode, getResultIntent(uri, error, sampleSize));
        finish();*/
    }

    /**
     * Get Android uri to save the cropped image into.<br>
     * Use the given in options or create a temp file.
     */
    protected Uri getOutputUri() {
        Uri outputUri = mOptions.outputUri;
        if (outputUri == null || outputUri.equals(Uri.EMPTY)) {
            try {
                String ext =
                        mOptions.outputCompressFormat == Bitmap.CompressFormat.JPEG
                                ? ".jpg"
                                : mOptions.outputCompressFormat == Bitmap.CompressFormat.PNG ? ".png" : ".webp";
                outputUri = Uri.fromFile(File.createTempFile("cropped", ext, getCacheDir()));
            } catch (IOException e) {
                throw new RuntimeException("Failed to create temp file for output image", e);
            }
        }
        return outputUri;
    }

    protected void rotateImage(int degrees) {
        cropImageView.rotateImage(degrees);
    }

    protected void cropImage() {
        if (mOptions.noOutputImage) {
            setResult(null, null, 1);
        } else {
            Uri outputUri = getOutputUri();
            cropImageView.saveCroppedImageAsync(
                    outputUri,
                    mOptions.outputCompressFormat,
                    mOptions.outputCompressQuality,
                    mOptions.outputRequestWidth,
                    mOptions.outputRequestHeight,
                    mOptions.outputRequestSizeOptions);
        }
    }

    @Override
    public void onCropImageComplete(CropImageView view, CropImageView.CropResult result) {
        setResult(result.getUri(), result.getError(), result.getSampleSize());
    }

    @Override
    public void onSetImageUriComplete(CropImageView view, Uri uri, Exception error) {
        if (error == null) {
            if (mOptions.initialCropWindowRectangle != null) {
                cropImageView.setCropRect(mOptions.initialCropWindowRectangle);
            }
            if (mOptions.initialRotation > -1) {
                cropImageView.setRotatedDegrees(mOptions.initialRotation);
            }
        } else {
            setResult(null, error, 1);
        }
    }
}
