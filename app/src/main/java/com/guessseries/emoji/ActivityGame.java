package com.guessseries.emoji;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;
import java.util.Random;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.reward.RewardItem;
import com.google.android.gms.ads.reward.RewardedVideoAd;
import com.google.android.gms.ads.reward.RewardedVideoAdListener;

import android.app.ActionBar.LayoutParams;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View.OnClickListener;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import static com.guessseries.emoji.CommonUtilities.hangiHarfler;
import static com.guessseries.emoji.CommonUtilities.words;

public class ActivityGame extends AppCompatActivity implements RewardedVideoAdListener
		 {
	private MediaPlayer mediaPlayer;
	private ArrayList<Integer> ramdomNubers;

	private AlertDialog.Builder builder;
	private String[] cevapKullanici;
	private TextView txt_yanlis;
	private JSONArray products = null;
	private SharedPreferences prefs;
	private ArrayList<Integer> oncekiRandomlar = new ArrayList<Integer>();
	private ArrayList<Button> btnAnswer = new ArrayList<Button>();
	private ArrayList<Button> btnAllKeys = new ArrayList<Button>();
	private ArrayList<Button> btnOpenKeys = new ArrayList<Button>();
	private ArrayList<Button> btnInvisibleKeys = new ArrayList<Button>();
	private Button[] btnKeysSuccesion;
	private LinearLayout layout;
	private int lastOne = 0;
	public static int scor = 0;
	private ImageView img_one;
	private ImageView img_two;
	private ImageView img_tree;
			 private LinearLayout photo_friends;
	private ImageView img_four;
	private ImageView img_full_screen;
	private Button key_btn_0, key_btn_1, key_btn_2, key_btn_3, key_btn_4,
			key_btn_5, key_btn_6, key_btn_7, key_btn_8, key_btn_9, key_btn_10,
			key_btn_11, btn_scor, btn_level, btn_izle,

			 key_btn_12,
			 key_btn_13,
			 key_btn_14,
			 key_btn_15,
			 key_btn_16,
			 key_btn_17,
			 key_btn_18,
			 key_btn_19;

			 public static int countclick=7;

             private AdView adView;

			 private ImageButton btn_help;

			 final String FOLDER = "2Image1City";
			 private String filepath = Environment.getExternalStorageDirectory()
					 .getPath();
			 private File file = new File(filepath, FOLDER);

			 private InterstitialAd interstitialAd;
			 private static final String AD_UNIT_ID = "ca-app-pub-6027750153735611/1516054621";
			 private static final String APP_ID = "ca-app-pub-6027750153735611/2422953947";

			 private RewardedVideoAd mRewardedVideoAd;

			 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);

				 FullScreencall();


		//getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
		//WindowManager.LayoutParams.FLAG_FULLSCREEN);
		setContentView(R.layout.activity_game_blue);

				 if (!file.exists()) {
					 file.mkdirs();
				 }


				 MobileAds.initialize(this, APP_ID);

				 photo_friends = (LinearLayout) findViewById(R.id.photo_lnr);

				 mRewardedVideoAd = MobileAds.getRewardedVideoAdInstance(this);
				 mRewardedVideoAd.setRewardedVideoAdListener(this);
				 loadRewardedVideoAd();




                 interstitialAd = new InterstitialAd(this);
				 interstitialAd.setAdUnitId(AD_UNIT_ID);

				 // Check the logcat output for your hashed device ID to get test ads on
				 // a physical device.
				 AdRequest adRequest = new AdRequest.Builder()
						 .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
						 .addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE").build();

				 // Load the interstitial ad.
				 interstitialAd.loadAd(adRequest);

				 // Set the AdListener.

				 interstitialAd.setAdListener(new AdListener() {
					 public void onAdLoaded() {
						 Log.d("", "onAdLoaded");
						 // Toast.makeText(YoutubePlay.this, "onAdLoaded",
						 // Toast.LENGTH_SHORT).show();
						 if (interstitialAd.isLoaded()) {
							 interstitialAd.show();
						 } else {
							 Log.d("here",
									 "Interstitial ad was not ready to be shown.");
						 }

					 }
				 });




		prefs = this.getSharedPreferences("userInfo", Context.MODE_PRIVATE);

		lastOne = prefs.getInt("lastOne", 0);
		scor = prefs.getInt("scor", 0);

		loadStringFromAsset();
		findViewbyid();
		setImagesResources(lastOne);
		addButtonToLayout(lastOne);
		setKeyValues(lastOne);





		btn_izle.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				getScreenShotToShare();

			}
		});

		img_one.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//
			//	img_full_screen.setVisibility(View.VISIBLE);
				loadImageFromAsset(img_full_screen, lastOne, 0);
			}
		});

		img_two.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//
			//	img_full_screen.setVisibility(View.VISIBLE);
				loadImageFromAsset(img_full_screen, lastOne, 1);
			}
		});
		img_tree.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//
			//	img_full_screen.setVisibility(View.VISIBLE);
				loadImageFromAsset(img_full_screen, lastOne, 2);
			}
		});
		img_four.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//
			//	img_full_screen.setVisibility(View.VISIBLE);
				loadImageFromAsset(img_full_screen, lastOne, 3);
			}
		});
		img_full_screen.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//
				img_full_screen.setVisibility(View.GONE);

			}
		});

		btn_help.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View arg0) {
				//
				harfSecenekleriAlert();

			}
		});

		mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.key);
		if (mediaPlayer != null) {
			mediaPlayer.setVolume((7 * (0.1f)), 7 * (0.1f));
		}
	}

	public void stopPlaying() {

		if (mediaPlayer != null) {

			mediaPlayer.stop();
			mediaPlayer.release();

		}

	}

	@Override
	public void onBackPressed() {

		if (mediaPlayer != null) {
			stopPlaying();
		}

		finish();
	}

	private void setImagesResources(int lastOneHere) {

		loadImageFromAsset(img_one, lastOne, 0);
		loadImageFromAsset(img_two, lastOne, 1);
		loadImageFromAsset(img_tree, lastOne, 2);
		loadImageFromAsset(img_four, lastOne, 3);
	}

	private void findViewbyid() {
		btn_help = (ImageButton) findViewById(R.id.btn_help);
		builder = new AlertDialog.Builder(this);
		txt_yanlis = (TextView) findViewById(R.id.txt_yanlis);
		layout = (LinearLayout) findViewById(R.id.linear_answer);
		img_one = (ImageView) findViewById(R.id.img_one);
		img_two = (ImageView) findViewById(R.id.img_two);
		img_tree = (ImageView) findViewById(R.id.img_tree);
		img_four = (ImageView) findViewById(R.id.img_four);
		img_full_screen = (ImageView) findViewById(R.id.img_full_screen);
		key_btn_0 = (Button) findViewById(R.id.key_btn_0);
		key_btn_1 = (Button) findViewById(R.id.key_btn_1);
		key_btn_2 = (Button) findViewById(R.id.key_btn_2);
		key_btn_3 = (Button) findViewById(R.id.key_btn_3);
		key_btn_4 = (Button) findViewById(R.id.key_btn_4);
		key_btn_5 = (Button) findViewById(R.id.key_btn_5);
		key_btn_6 = (Button) findViewById(R.id.key_btn_6);
		key_btn_7 = (Button) findViewById(R.id.key_btn_7);
		key_btn_8 = (Button) findViewById(R.id.key_btn_8);
		key_btn_9 = (Button) findViewById(R.id.key_btn_9);
		key_btn_10 = (Button) findViewById(R.id.key_btn_10);
		key_btn_11 = (Button) findViewById(R.id.key_btn_11);


		key_btn_12 = (Button) findViewById(R.id.key_btn_12);
		key_btn_13 = (Button) findViewById(R.id.key_btn_13);
		key_btn_14 = (Button) findViewById(R.id.key_btn_14);
		key_btn_15 = (Button) findViewById(R.id.key_btn_15);
		key_btn_16 = (Button) findViewById(R.id.key_btn_16);
		key_btn_17 = (Button) findViewById(R.id.key_btn_17);
		key_btn_18 = (Button) findViewById(R.id.key_btn_18);
		key_btn_19 = (Button) findViewById(R.id.key_btn_19);








		btn_izle = (Button) findViewById(R.id.btn_share);

		btn_scor = (Button) findViewById(R.id.btn_scor);
		btn_level = (Button) findViewById(R.id.btn_level);

		btn_scor.setText(String.valueOf(scor));

		btn_level.setText("LEVEL "+String.valueOf(lastOne + 1));

		btnKeysSuccesion = new Button[20];

		btnKeysSuccesion[0] = key_btn_0;
		btnKeysSuccesion[1] = key_btn_1;
		btnKeysSuccesion[2] = key_btn_2;
		btnKeysSuccesion[3] = key_btn_3;
		btnKeysSuccesion[4] = key_btn_4;
		btnKeysSuccesion[5] = key_btn_5;
		btnKeysSuccesion[6] = key_btn_6;
		btnKeysSuccesion[7] = key_btn_7;
		btnKeysSuccesion[8] = key_btn_8;
		btnKeysSuccesion[9] = key_btn_9;
		btnKeysSuccesion[10] = key_btn_10;
		btnKeysSuccesion[11] = key_btn_11;

		btnKeysSuccesion[12] = key_btn_12;
		btnKeysSuccesion[13] = key_btn_13;
		btnKeysSuccesion[14] = key_btn_14;
		btnKeysSuccesion[15] = key_btn_15;
		btnKeysSuccesion[16] = key_btn_16;
		btnKeysSuccesion[17] = key_btn_17;
		btnKeysSuccesion[18] = key_btn_18;
		btnKeysSuccesion[19] = key_btn_19;


		for (int i = 0; i < btnKeysSuccesion.length; i++) {
			btnKeysSuccesion[i].setOnClickListener(key_btn_click);
		}



		// boslukkontrol();

	}








	private OnClickListener btnAnswer_click = new OnClickListener() {

		@Override
		public void onClick(View v) {

			removeLetter(((Button) v).getText().toString(), ((Button) v));

			countclick++;

			if(countclick%20 ==0) {

				reklamcagir();
			}
		}
	};



	private OnClickListener key_btn_click = new OnClickListener() {

		@Override
		public void onClick(View v) {

			addLetter(((Button) v).getText().toString(), ((Button) v));

			countclick++;

			if(countclick%20 ==0) {

				reklamcagir();
			}
		}
	};

	private void removeLetter(String letter, Button btnA) {

		int i = 0;
		while (i < btnAllKeys.size()) {

			if (btnAllKeys.get(i).getText().toString().equals(letter)) {
				btnA.setText("");
				btnA.setBackgroundResource(R.drawable.cevapletters);
				btnAllKeys.get(i).setVisibility(View.VISIBLE);
				btnAllKeys.remove(i);
				txt_yanlis.setVisibility(View.INVISIBLE);
				stopPlaying();
				mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.delete_letters);
				mediaPlayer.start();
				break;
			}
			i++;
		}

	}

	private void addLetter(String letter, Button btnKeys) {
		String answer = "";
		int i = 0;
		while (i < btnAnswer.size()) {

			if (((Button) btnAnswer.get(i)).getText().toString().equals("")) {
				((Button) btnAnswer.get(i)).setText(letter);
				((Button) btnAnswer.get(i))
						.setBackgroundResource(R.drawable.cevapletters);
				btnKeys.setVisibility(View.INVISIBLE);
				btnAllKeys.add(btnKeys);
				stopPlaying();
				mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.clickk);
				mediaPlayer.start();
				break;
			}
			i++;
		}

		for (int j = 0; j < btnAnswer.size(); j++) {
			answer = answer + ((Button) btnAnswer.get(j)).getText().toString();
		}

		if (answer.equals(words[lastOne][1])) {
			reset();
		} else {
			char[] Cevap = words[lastOne][1].toCharArray();
			char[] verilenCevap = answer.toCharArray();
			if (verilenCevap.length == Cevap.length) {
				stopPlaying();
				mediaPlayer = MediaPlayer
						.create(getBaseContext(), R.raw.error);
				mediaPlayer.start();
				txt_yanlis.setVisibility(View.VISIBLE);
			}

		}

	}

	private void setKeyValues(int lastOneHere) {
		sorulacakSorulariBelirle();
		String cevabimiz = words[lastOneHere][2] + "" + words[lastOneHere][1];
		char[] cevap = cevabimiz.toCharArray();

		cevapKullanici = new String[words[lastOneHere][1].length()];
		for (int i = 0; i < words[lastOneHere][1].length(); i++) {
			cevapKullanici[i] = "";
		}

		for (int i = 0; i < btnKeysSuccesion.length; i++) {
			btnKeysSuccesion[i].setText("" + cevap[hangiHarfler[i]]);

		}
	}

	private void cevapkelimeleri(int lastOneHere) {

		cevapkelimeleri_karistir(lastOne);

		char[] cevap = words[lastOneHere][1].toCharArray();

		cevapKullanici = new String[words[lastOneHere][1].length()];
		for (int i = 0; i < words[lastOneHere][1].length(); i++) {
			cevapKullanici[i] = "";
		}

		for (int i = 0; i < btnKeysSuccesion.length; i++) {

			btnKeysSuccesion[i].setVisibility(Button.INVISIBLE);
		}

		for (int i = 0; i < cevap.length; i++) {

			btnKeysSuccesion[i].setVisibility(Button.VISIBLE);

			btnKeysSuccesion[i].setText("" + cevap[hangiHarfler[i]]);



		}
	}

	private void addButtonToLayout(int lastOneHere) {
		int len = words[lastOneHere][1].length();

		LayoutParams params;

		params = new LayoutParams(getResources().getDimensionPixelSize(
				R.dimen.value40), getResources().getDimensionPixelSize(
				R.dimen.value40));

		params.leftMargin = 0;
		params.rightMargin = 0;
		params.topMargin = 0;
		params.bottomMargin = 0;

		for (int i = 0; i < len; i++) {

			Button btnTag = new Button(ActivityGame.this);
			btnTag.setText("");

			btnTag.setId(i);
			btnTag.setTextColor(Color.BLACK);
			btnTag.setTextSize(19);

			btnTag.setLayoutParams(params);
			btnTag.setGravity(Gravity.CENTER);
			btnTag.setOnClickListener(btnAnswer_click);

			btnTag.setBackgroundResource(R.drawable.cevapletters);



			layout.addView(btnTag);

			btnAnswer.add(btnTag);

		}
	}



	private void sorulacakSorulariBelirle() {

		hangiHarfler = new int[20];
		for (int i = 0; i < hangiHarfler.length; i++) {
			hangiHarfler[i] = -1;
		}

		Random r = new Random();
		int a = 0;
		while (true) {

			int random = r.nextInt(20 - 0) + 0;
			boolean ekle = true;
			for (int j = 0; j < hangiHarfler.length; j++) {

				if (hangiHarfler[j] == random) {
					ekle = false;

				}
			}
			if (ekle == true) {
				hangiHarfler[a] = random;
				a++;
			}
			boolean sonuc = true;
			for (int i = 0; i < hangiHarfler.length; i++) {

				if (hangiHarfler[i] == -1) {
					sonuc = false;
				}
			}

			if (sonuc == true) {
				break;
			}
		}

	}

	private void cevapkelimeleri_karistir(int uzunluk) {

		hangiHarfler = new int[words[uzunluk][1].length()];

		for (int i = 0; i < hangiHarfler.length; i++) {
			hangiHarfler[i] = -1;
		}

		Random r = new Random();
		int a = 0;
		while (true) {

			int random = r.nextInt(words[uzunluk][1].length() - 0) + 0;
			boolean ekle = true;
			for (int j = 0; j < hangiHarfler.length; j++) {

				if (hangiHarfler[j] == random) {
					ekle = false;

				}
			}
			if (ekle == true) {
				hangiHarfler[a] = random;
				a++;

			}
			boolean sonuc = true;
			for (int i = 0; i < hangiHarfler.length; i++) {

				if (hangiHarfler[i] == -1) {
					sonuc = false;
				}
			}

			if (sonuc == true) {

				break;
			}


		}

	}

	private void loadImageFromAsset(ImageView mImage, int sira, int hangisi) {

		try {
			InputStream ims = getAssets().open(
					"answers_img/" + sira + "_" + hangisi + ".jpg");
			Drawable d = Drawable.createFromStream(ims, null);
			mImage.setImageDrawable(d);
		} catch (IOException ex) {

			String a = ex.toString();
			a = a + "";
			return;
		}
	}

	private void loadStringFromAsset() {
		JSONObject jObj = null;
		try {

			InputStream is = getAssets().open("answerstexts.txt");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			jObj = new JSONObject(new String(buffer));





		} catch (IOException ex) {
			return;
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {

			products = jObj.getJSONArray("words");

			words = new String[products.length()][3];
			for (int i = 0; i < products.length(); i++) {
				JSONObject c = products.getJSONObject(i);

				words[i][0] = c.getString("id");
				words[i][1] = c.getString("a_key");
				words[i][2] = c.getString("s_key");





			}
		} catch (JSONException e) {
			e.printStackTrace();
		}

	}

			 private void harfSecenekleriAlert() {

				 final Dialog dialog = new Dialog(this);
				 // hide to default title for Dialog
				 dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

				 // inflate the layout dialog_layout.xml and set it as contentView
				 LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				 View view = inflater.inflate(R.layout.stardialog, null, false);
				 dialog.setCanceledOnTouchOutside(false);
				 dialog.setContentView(view);
				 dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));



				 Button btnbirharf = (Button) dialog.findViewById(R.id.btn_birharf);



				 btnbirharf.setOnClickListener(new OnClickListener() {
					 @Override
					 public void onClick(View v) {

						 harfEkle();
						 dialog.dismiss();

					 }
				 });

				 Button btncvpgoster = (Button) dialog.findViewById(R.id.btn_cvpgoster);



				 btncvpgoster.setOnClickListener(new OnClickListener() {
					 @Override
					 public void onClick(View v) {

						 if (scor >= 5) {

							 scor = scor - 5;
							 prefs.edit().putInt("scor", scor).commit();
							 btn_scor.setText(String.valueOf(prefs.getInt("scor", 0)));

							 harfSil();

							 dialog.dismiss();

						 }

						 else {

							 Toast.makeText(getApplicationContext(),
									 "Your credit is insufficient, you can earn credit by watching ads.", Toast.LENGTH_LONG)
									 .show();

							 dialog.dismiss();

						 }

					 }
				 });

				 Button btnkapat = (Button) dialog.findViewById(R.id.btn_kapat);


				 btnkapat.setOnClickListener(new OnClickListener() {
					 @Override
					 public void onClick(View v) {
						 // Close the dialog
						 dialog.dismiss();
					 }
				 });


				 Button btn_reklamizle = (Button) dialog.findViewById(R.id.btn_reklamizle);


				 btn_reklamizle.setOnClickListener(new OnClickListener() {
					 @Override
					 public void onClick(View v) {
						 // Close the dialog


						 if (mRewardedVideoAd.isLoaded()) {
							 mRewardedVideoAd.show();

						 }

						 else {

							 Toast.makeText(getApplicationContext(), "Adds not ready, please try again :)\n", Toast.LENGTH_SHORT).show();
						 }


					     }
				 });

				 // Display the dialog
				 dialog.show();

			 }

	private int oncekiRandomlarKontrol() {
		ramdomNubers = new ArrayList<Integer>();
		for (int i = 0; i < btnAnswer.size(); i++) {

			if (!oncekiRandomlar.contains(i)) {
				ramdomNubers.add(i);
			}

		}
		Random r = new Random();
		int i1 = r.nextInt(ramdomNubers.size() - 0) + 0;

		return ramdomNubers.get(i1);
	}



	private void harfSil() {

		String butunharfler = words[lastOne][2] + words[lastOne][1];

		char[] Sil = butunharfler.toCharArray();

		btnAllKeys.clear();
		btnAnswer.clear();
		btnOpenKeys.clear();

		layout.removeAllViews();

		key_btn_0.setVisibility(View.VISIBLE);
		for (int i = 0; i < btnKeysSuccesion.length; i++) {
			btnKeysSuccesion[i].setVisibility(View.VISIBLE);
		}

		for (int i = 0; i < btnInvisibleKeys.size(); i++) {
			btnInvisibleKeys.get(i).setVisibility(View.VISIBLE);
		}

		btnInvisibleKeys.clear();
		setImagesResources(lastOne);
		addButtonToLayout(lastOne);
		cevapkelimeleri(lastOne);

	}

	private void harfEkle() {

		char[] Cevap = words[lastOne][1].toCharArray();

		int i1 = 0;
		for (int i = 0; i < Cevap.length; i++) {

			if (((Button) btnAnswer.get(i)).getText().equals(""))

			{

				i1 = i;
				break;
			}

		}

		if (scor >= 2) {

			stopPlaying();
			mediaPlayer = MediaPlayer.create(getBaseContext(),
					R.raw.credit_one);
			mediaPlayer.start();
			// oncekiRandomlar.add(i1);
			scor = scor - 2;
			prefs.edit().putInt("scor", scor).commit();
			btn_scor.setText(String.valueOf(prefs.getInt("scor", 0)));

			((Button) btnAnswer.get(i1)).setOnClickListener(bosClickListener);
			((Button) btnAnswer.get(i1)).setText("" + Cevap[i1]);
			((Button) btnAnswer.get(i1))
					.setBackgroundResource(R.drawable.cevapletters);
			if (!btnAnswer.contains(((Button) btnAnswer.get(i1)))) {
				btnAllKeys.add(((Button) btnAnswer.get(i1)));
			}

			if (!btnOpenKeys.contains(((Button) btnAnswer.get(i1)))) {
				btnOpenKeys.add((Button) btnAnswer.get(i1));
			} else {

				harfEkle();
			}

			int b = 0;
			while (b < btnKeysSuccesion.length) {
				if (!btnInvisibleKeys.contains(btnKeysSuccesion[b])
						&& btnKeysSuccesion[b].getText().equals("" + Cevap[i1])) {
					btnInvisibleKeys.add(btnKeysSuccesion[b]);
					btnKeysSuccesion[b].setVisibility(View.INVISIBLE);
					break;
				}
				b++;
			}

			String answer = "";

			for (int j = 0; j < btnAnswer.size(); j++) {
				answer = answer
						+ ((Button) btnAnswer.get(j)).getText().toString();
			}

			if (answer.equals(words[lastOne][1])) {
				reset();
			} else {
				char[] verilenCevap = answer.toCharArray();
				if (verilenCevap.length == Cevap.length) {
					stopPlaying();
					mediaPlayer = MediaPlayer.create(getBaseContext(),
							R.raw.error);
					mediaPlayer.start();
					txt_yanlis.setVisibility(View.VISIBLE);
				}

			}
		}

		else {

			Toast.makeText(getApplicationContext(), "Your credit is insufficient, you can earn credit by watching ads.",
					Toast.LENGTH_SHORT).show();

		}

	}

	private void reset() {

		stopPlaying();
		mediaPlayer = MediaPlayer.create(getBaseContext(), R.raw.next);
		mediaPlayer.start();

		final Context context = this;
		final Dialog dialog = new Dialog(context);

		dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
		Window window = dialog.getWindow();
		//window.setBackgroundDrawableResource(R.drawable.arkaplandogru);

		dialog.setContentView(R.layout.diyalog);
		// dialog.setTitle("Başlık");

		dialog.setCancelable(false);

		// Custom dialogumuzdaki elemanları setleyelim
		TextView text = (TextView) dialog.findViewById(R.id.textView1);
		text.setText("CORRECT ANSWER!");

		ImageView image = (ImageView) dialog.findViewById(R.id.imageView1);
		image.setImageResource(R.drawable.bonus_txt);

		Button evetButton = (Button) dialog.findViewById(R.id.buttonEvet);

		evetButton.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {




				if (lastOne == 81) {
					finish();

					Intent intent = new Intent(ActivityGame.this, MainActivity.class);
					startActivity(intent);
					overridePendingTransition(R.anim.slideleft_toright,
							R.anim.slideright_toleft);

					Toast.makeText(
							getApplicationContext(),
							"Congratulations! You Solved All Questions .. :)",
							Toast.LENGTH_LONG).show();
				}
					else {	img_full_screen.setVisibility(View.INVISIBLE);
					txt_yanlis.setVisibility(View.INVISIBLE);
					lastOne++;
					btn_level.setText("LEVEL "+String.valueOf(lastOne + 1));

					btn_scor.setText(String.valueOf(scor));
					scor = scor + 1;

					btn_scor.setText(String.valueOf(scor));
					prefs.edit().putInt("lastOne", lastOne).commit();
					prefs.edit().putInt("scor", scor).commit();
					btnAllKeys.clear();
					btnAnswer.clear();
					btnOpenKeys.clear();
					oncekiRandomlar.clear();
					layout.removeAllViews();

					key_btn_0.setVisibility(View.VISIBLE);
					for (int i = 0; i < btnKeysSuccesion.length; i++) {
						btnKeysSuccesion[i].setVisibility(View.VISIBLE);
					}

					for (int i = 0; i < btnInvisibleKeys.size(); i++) {
						btnInvisibleKeys.get(i).setVisibility(View.VISIBLE);
					}

					btnInvisibleKeys.clear();
					setImagesResources(lastOne);
					addButtonToLayout(lastOne);
					setKeyValues(lastOne);
					dialog.dismiss();}


			}
		});

		dialog.show();

	}





	OnClickListener bosClickListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub

		}
	};



			 public boolean onKeyDown(int keyCode, KeyEvent event) {

				 if (keyCode == KeyEvent.KEYCODE_BACK) {

					 finish();
					 Intent intent = new Intent(ActivityGame.this, MainActivity.class);
					 startActivity(intent);
					 overridePendingTransition(R.anim.slideleft_toright,
							 R.anim.slideright_toleft);

				 }
				 return super.onKeyDown(keyCode, event);
			 }



			 private void getScreenShotToShare() {
				 View maimPic = findViewById(R.id.photo_lnr);
				 maimPic.setDrawingCacheEnabled(true);

				 maimPic.buildDrawingCache(true);
				 Bitmap b = Bitmap.createBitmap(maimPic.getDrawingCache());
				 maimPic.setDrawingCacheEnabled(false);

				 String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
						 Locale.getDefault()).format(new Date());

				 File myPath = new File(file, timeStamp + "" + "screenshootknowledge.jpg");
				 FileOutputStream fos = null;
				 try {
					 fos = new FileOutputStream(myPath);
					 b.compress(Bitmap.CompressFormat.JPEG, 100, fos);
					 fos.flush();
					 fos.close();
					 MediaStore.Images.Media.insertImage(getContentResolver(), b,
							 "Screen", "screen");
				 } catch (FileNotFoundException e) {
					 //
					 e.printStackTrace();
				 } catch (Exception e) {
					 //
					 e.printStackTrace();
				 }

				 maimPic.setDrawingCacheEnabled(false);

				// Toast.makeText(getBaseContext(), "Photo saved to gallery",
				//		 Toast.LENGTH_LONG).show();
				 shareImage(myPath.getPath().toString());
			 }

			 private void shareImage(String imagePath) {
				 File DoutFile = new File(imagePath);
				 Uri uri = Uri.fromFile(DoutFile);
				 Intent shareIntent = new Intent();
				 shareIntent.setAction(Intent.ACTION_SEND);
				 shareIntent.putExtra(Intent.EXTRA_STREAM, uri);
				 shareIntent.setType("image/*");
				 shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "https://play.google.com/store/apps/details?id=com.guessseries.emoji");
				 startActivity(Intent.createChooser(shareIntent, "Share image via:"));

			 }
			 public void FullScreencall() {
				 if(Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
					 View v = this.getWindow().getDecorView();
					 v.setSystemUiVisibility(View.GONE);
				 } else if(Build.VERSION.SDK_INT >= 19) {
					 //for new api versions.
					 View decorView = getWindow().getDecorView();
					 int uiOptions = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY;
					 decorView.setSystemUiVisibility(uiOptions);
				 }
			 }



			 private void loadRewardedVideoAd() {
				 if (!mRewardedVideoAd.isLoaded()) {
					 mRewardedVideoAd.loadAd(APP_ID, new AdRequest.Builder().build());
				 }
			 }

			 private void addCoins() {

				 scor = scor + 3;
				 prefs.edit().putInt("scor", scor).commit();
				 btn_scor.setText(String.valueOf(prefs.getInt("scor", 0)));


			 }

			 @Override
			 public void onRewardedVideoAdLeftApplication() {
				 Toast.makeText(this, "onRewardedVideoAdLeftApplication", Toast.LENGTH_SHORT).show();




			 }

			 @Override
			 public void onRewardedVideoAdClosed() {


				 loadRewardedVideoAd();




			 }

			 @Override
			 public void onRewardedVideoAdFailedToLoad(int errorCode) {
				 //  Toast.makeText(this, "onRewardedVideoAdFailedToLoad", Toast.LENGTH_SHORT).show();
			 }

			 @Override
			 public void onRewardedVideoCompleted() {

			 }

			 @Override
			 public void onRewardedVideoAdLoaded() {
				 // Toast.makeText(this, "onRewardedVideoAdLoaded", Toast.LENGTH_SHORT).show();
			 }

			 @Override
			 public void onRewardedVideoAdOpened() {
				 // Toast.makeText(this, "onRewardedVideoAdOpened", Toast.LENGTH_SHORT).show();
			 }

			 @Override
			 public void onRewarded(RewardItem reward) {

				 addCoins();

				// Toast.makeText(getApplicationContext(),"Tebrikler 3 kredi kazandınız!",Toast.LENGTH_SHORT).show();

			 }

			 @Override
			 public void onRewardedVideoStarted() {
				 // Toast.makeText(this, "onRewardedVideoStarted", Toast.LENGTH_SHORT).show();
			 }



			 public void reklamcagir() {


				 interstitialAd = new InterstitialAd(this);
				 interstitialAd.setAdUnitId(AD_UNIT_ID);

				 // Check the logcat output for your hashed device ID to get test ads on
				 // a physical device.
				 AdRequest adRequest = new AdRequest.Builder()
						 .addTestDevice(AdRequest.DEVICE_ID_EMULATOR)
						 .addTestDevice("INSERT_YOUR_HASHED_DEVICE_ID_HERE").build();

				 // Load the interstitial ad.
				 interstitialAd.loadAd(adRequest);

				 // Set the AdListener.

				 interstitialAd.setAdListener(new AdListener() {
					 public void onAdLoaded() {
						 Log.d("", "onAdLoaded");
						 // Toast.makeText(YoutubePlay.this, "onAdLoaded",
						 // Toast.LENGTH_SHORT).show();
						 if (interstitialAd.isLoaded()) {
							 interstitialAd.show();
						 } else {
							 Log.d("here",
									 "Interstitial ad was not ready to be shown.");
						 }

					 }
				 });

			 }



			 public void boslukkontrol() {



				 if(key_btn_0.getText()=="")
				 {

					 key_btn_0.setBackgroundResource(R.drawable.imageview_empty);
				 }


				 if(key_btn_1.getText()=="")		{

					 key_btn_1.setBackgroundResource(R.drawable.imageview_empty);
				 }

				 if(key_btn_2.getText()=="")		{

					 key_btn_2.setBackgroundResource(R.drawable.imageview_empty);
				 }

				 if(key_btn_3.getText()=="")		{

					 key_btn_3.setBackgroundResource(R.drawable.imageview_empty);
				 }

				 if(key_btn_4.getText()=="")		{

					 key_btn_4.setBackgroundResource(R.drawable.imageview_empty);
				 }


				 if(key_btn_5.getText()=="")		{

					 key_btn_5.setBackgroundResource(R.drawable.imageview_empty);
				 }


				 if(key_btn_6.getText()=="")		{

					 key_btn_6.setBackgroundResource(R.drawable.imageview_empty);
				 }

				 if(key_btn_7.getText()=="")		{

					 key_btn_7.setBackgroundResource(R.drawable.imageview_empty);
				 }

				 if(key_btn_8.getText()=="")		{

					 key_btn_8.setBackgroundResource(R.drawable.imageview_empty);
				 }

				 if(key_btn_9.getText()=="")		{

					 key_btn_9.setBackgroundResource(R.drawable.imageview_empty);
				 }


				 if(key_btn_10.getText()=="")		{

					 key_btn_10.setBackgroundResource(R.drawable.imageview_empty);
				 }


				 if(key_btn_11.getText()=="")		{

					 key_btn_11.setBackgroundResource(R.drawable.imageview_empty);
				 }

				 if(key_btn_12.getText()=="")		{

					 key_btn_12.setBackgroundResource(R.drawable.imageview_empty);
				 }

				 if(key_btn_13.getText()=="")		{

					 key_btn_13.setBackgroundResource(R.drawable.imageview_empty);
				 }


				 if(key_btn_14.getText()=="")		{

					 key_btn_14.setBackgroundResource(R.drawable.imageview_empty);
				 }


				 if(key_btn_15.getText()=="")		{

					 key_btn_15.setBackgroundResource(R.drawable.imageview_empty);
				 }

				 if(key_btn_16.getText()=="")		{

					 key_btn_16.setBackgroundResource(R.drawable.imageview_empty);
				 }

				 if(key_btn_17.getText()=="")		{

					 key_btn_17.setBackgroundResource(R.drawable.imageview_empty);
				 }


				 if(key_btn_18.getText()=="")		{

					 key_btn_18.setBackgroundResource(R.drawable.imageview_empty);
				 }

				 if(key_btn_19.getText()=="")		{

					 key_btn_19.setBackgroundResource(R.drawable.imageview_empty);
				 }

			 }



		 }


