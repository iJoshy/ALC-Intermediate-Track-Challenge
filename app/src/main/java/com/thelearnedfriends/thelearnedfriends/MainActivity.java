package com.thelearnedfriends.thelearnedfriends;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.thelearnedfriends.thirdparty.FragmentDrawer;
import com.thelearnedfriends.thirdparty.RoundedImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by portalservices on 8/31/15.
 */

public class MainActivity extends AppCompatActivity implements FragmentDrawer.FragmentDrawerListener
{

    private Toolbar mToolbar;
    private FragmentDrawer drawerFragment;
    TextView toolbarTitle;
    private TextView titleSettings;
    private Typeface myTypeface;

    private BuildAndSendRequest bnsr = null;
    private MaterialDialog dialog;

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    RoundedImageView ivImage;
    private DrawerLayout mDrawer;
    private ActionBarDrawerToggle mDrawerToggle;

    static final int SELECT_PHOTO = 100;
    int REQUEST_CAMERA = 0, SELECT_FILE = 1;
    private Intent intent;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbarTitle = (TextView) mToolbar.findViewById(R.id.toolbar_title);
        myTypeface = Typeface.createFromAsset(getAssets(), "etisalat2.TTF");
        titleSettings = (TextView) findViewById(R.id.shareText);
        titleSettings.setTypeface(myTypeface);
        toolbarTitle.setTypeface(myTypeface);

        setSupportActionBar(mToolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        drawerFragment = (FragmentDrawer)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawerFragment.setUp(R.id.fragment_navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout), mToolbar);
        drawerFragment.setDrawerListener(this);

        // Find our drawer view
        mDrawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        mDrawerToggle = new ActionBarDrawerToggle(this, mDrawer,
                R.drawable.ic_drawer, R.string.drawer_open, R.string.drawer_close) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
                System.out.print("<-::::: Navigation CLosed  :::->");
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                System.out.print("<-::::: Navigation Opened  :::->");
                loadNavPicture();
            }
        };

        // Set the drawer toggle as the DrawerListener
        mDrawer.setDrawerListener(mDrawerToggle);


        // display the first navigation drawer view on app launch
        displayView(0);

        preferences = this.getSharedPreferences("MyPreferences", Context.MODE_PRIVATE);
        editor = preferences.edit();

        editor.putString("LASTFUNCTION", "HOME");
        editor.commit();

        ivImage = (RoundedImageView) findViewById(R.id.profileImgNav);

        loadNavPicture();

    }

    public void loadNavPicture()
    {
        String mypic = "";
        mypic = preferences.getString("MYPIC","0");
        //System.out.println("home destination ::::: "+mypic);
        if (!mypic.equals("0"))
        {
            loadpicture(mypic);
            System.out.println("mypic path :::: "+mypic);
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
    }


    @Override
    public void onBackPressed()
    {
        moveTaskToBack(true);
    }

    public void loadpicture(String mypic)
    {
        String selectedImagePath = mypic;

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        editor.putString("MYPIC", selectedImagePath);
        editor.commit();

        ivImage.setImageBitmap(bm);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        /*
        if (id == R.id.logo)
        {
            displayView(0);
            return true;
        }
        */

        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onDrawerItemSelected(View view, int position)
    {
        displayView(position);
    }

    private void displayView(int position)
    {
        Fragment fragment = null;
        String title = getString(R.string.app_name);
        int level = 0;

        switch (position)
        {
            case 0:
                //Toast.makeText(getApplicationContext(), "Show My Account", Toast.LENGTH_SHORT).show();
                fragment = new TimelineFragment();
                title = "Java Developers in Lagos";
                level = 0;
                break;

            case 1:
                //Toast.makeText(getApplicationContext(), "Prepaid", Toast.LENGTH_SHORT).show();
                fragment = new PrepaidFragment();
                title = getString(R.string.title_newsfeed);
                level = 1;
                break;
            case 2:
                //Toast.makeText(getApplicationContext(), "Data Services", Toast.LENGTH_SHORT).show();
                fragment = new ProfileFragment();
                title = getString(R.string.title_profile);
                level = 2;
                break;
            case 3:
                //Toast.makeText(getApplicationContext(), "Migration", Toast.LENGTH_SHORT).show();
                fragment = new StoresFragment();
                title = getString(R.string.title_stores);
                level = 3;
                break;
            case 4:
                //Toast.makeText(getApplicationContext(), "Migration", Toast.LENGTH_SHORT).show();
                fragment = new SupportFragment();
                title = "support";
                level = 4;
                break;
            case 5:
                //Toast.makeText(getApplicationContext(), "Migration", Toast.LENGTH_SHORT).show();
                fragment = new SocialFragment();
                title = "share";
                level = 5;
                break;

            default:
                break;
        }


        getFragment(fragment, title, level);

    }

    public void getFragment(Fragment fragment, String title, int level)
    {
        if (fragment != null)
        {
            String screenname = "";
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            switch (level)
            {
                case 0:
                    screenname = "Java Developers in Lagos";
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.fade_in,android.R.anim.fade_out);
                    break;
                case 1:
                    screenname = "News Feed";
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.fade_in,android.R.anim.fade_out);
                    break;
                case 2:
                    screenname = "Profiles";
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.fade_in,android.R.anim.fade_out);
                    break;
                case 3:
                    screenname = "Etisalat Stores";
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.fade_in,android.R.anim.fade_out);
                    break;
                case 4:
                    screenname = "Support";
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.fade_in,android.R.anim.fade_out);
                    break;
                case 5:
                    screenname = "Share";
                    fragmentTransaction.setCustomAnimations(android.R.anim.fade_in,android.R.anim.fade_out,android.R.anim.fade_in,android.R.anim.fade_out);
                    break;
                default:
                    break;
            }

            fragmentTransaction.replace(R.id.container_body, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

            // set the toolbar title
            toolbarTitle.setText(title);
        }
    }

    public void Logout(View v)
    {

        getBaseContext().getSharedPreferences("MyPreferences", 0).edit().clear().commit();

        intent = new Intent(getBaseContext(), LoginActivity.class);
        startActivity(intent);

        finish();

    }

    public void profileImg(View v)
    {
        final CharSequence[] items = { "take photo", "choose from library", "cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
        builder.setTitle("change profile photo !");
        builder.setItems(items, new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int item)
            {
                if (items[item].equals("take photo"))
                {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, REQUEST_CAMERA);
                }
                else if (items[item].equals("choose from library"))
                {
                    Intent intent = new Intent( Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    intent.setType("image/*");
                    startActivityForResult(Intent.createChooser(intent, "Select File"), SELECT_FILE);
                }
                else if (items[item].equals("cancel"))
                {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK)
        {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    private void onCaptureImageResult(Intent data)
    {
        Bitmap thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 90, bytes);

        File destination = new File(Environment.getExternalStorageDirectory(), System.currentTimeMillis() + ".jpg");

        FileOutputStream fo;
        try
        {
            destination.createNewFile();
            fo = new FileOutputStream(destination);
            fo.write(bytes.toByteArray());
            fo.close();
        }
        catch (FileNotFoundException e)
        {
            e.printStackTrace();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }

        ivImage.setImageBitmap(thumbnail);
    }

    @SuppressWarnings("deprecation")
    private void onSelectFromGalleryResult(Intent data)
    {
        Uri selectedImageUri = data.getData();
        String[] projection = { MediaStore.MediaColumns.DATA };
        Cursor cursor = managedQuery(selectedImageUri, projection, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
        cursor.moveToFirst();

        String selectedImagePath = cursor.getString(column_index);

        Bitmap bm;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(selectedImagePath, options);
        final int REQUIRED_SIZE = 200;
        int scale = 1;
        while (options.outWidth / scale / 2 >= REQUIRED_SIZE && options.outHeight / scale / 2 >= REQUIRED_SIZE)
            scale *= 2;
        options.inSampleSize = scale;
        options.inJustDecodeBounds = false;
        bm = BitmapFactory.decodeFile(selectedImagePath, options);

        //System.out.println("onSelectFromGalleryResult destination ::::: "+selectedImagePath);
        editor.putString("MYPIC", selectedImagePath);
        editor.commit();

        ivImage.setImageBitmap(bm);
    }

}
