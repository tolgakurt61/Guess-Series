package com.guessseries.emoji;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import androidx.appcompat.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
	private Button img_play,reset,howto,aciklama;



	private int kontroloyla;
	private int DEFAULT;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.giris);


		final Dialog dialog = new Dialog(this);








		img_play = (Button) findViewById(R.id.img_play);
		reset = (Button) findViewById(R.id.btn_reset);
		howto = (Button) findViewById(R.id.btn_howto);
		aciklama = (Button) findViewById(R.id.btn_aciklama);




		SharedPreferences prefs = getSharedPreferences(
				"userInfo", MODE_PRIVATE);

		kontroloyla = prefs.getInt("kontroloyla",DEFAULT);

		if(kontroloyla != 0) {

			howto.setVisibility(Button.INVISIBLE);

		}



		aciklama.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub


				// inflate the layout dialog_layout.xml and set it as contentView
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.acikla_popup, null, false);
				dialog.setCanceledOnTouchOutside(false);
				dialog.setContentView(view);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

				TextView aciklamametni = (TextView) dialog.findViewById(R.id.btn_cvpgoster);

				aciklamametni.setText("Guess the right answers!.You can earn credits by watching ads or responding correctly to questions.You can vote and comment to support the application.");


				Button btnkapat = (Button) dialog.findViewById(R.id.btn_kapat);


				btnkapat.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// Close the dialog
						dialog.dismiss();
					}
				});

				// Display the dialog
				dialog.show();




	}
		});
		

		img_play.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						ActivityGame.class);
				startActivity(intent);
				finish();

				overridePendingTransition(R.anim.slideright_toleftiki,
						R.anim.slideleft_torightiki);
			}
		});



		reset.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub



				// inflate the layout dialog_layout.xml and set it as contentView
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.reset_popup, null, false);
				dialog.setCanceledOnTouchOutside(false);
				dialog.setContentView(view);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

				TextView resettext = (TextView) dialog.findViewById(R.id.btn_cvpgoster);



				Button btnbirharf = (Button) dialog.findViewById(R.id.btn_birharf);


				btnbirharf.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						SharedPreferences.Editor prefs = getSharedPreferences(
								"userInfo", MODE_PRIVATE).edit();


						prefs.putInt("lastOne", 0);
						prefs.putInt("scor", 5);
						prefs.commit();

						Toast.makeText(getApplicationContext(),
								"Reset completed!", Toast.LENGTH_SHORT).show();

						dialog.dismiss();

					}
				});



				Button btnkapat = (Button) dialog.findViewById(R.id.btn_kapat);


				btnkapat.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// Close the dialog
						dialog.dismiss();
					}
				});

				// Display the dialog
				dialog.show();


			}
		});




		howto.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {




				// inflate the layout dialog_layout.xml and set it as contentView
				LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
				View view = inflater.inflate(R.layout.rate_popup, null, false);
				dialog.setCanceledOnTouchOutside(false);
				dialog.setContentView(view);
				dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));

				TextView  ratetext = (TextView) dialog.findViewById(R.id.btn_cvpgoster);


				Button btnbirharf = (Button) dialog.findViewById(R.id.btn_birharf);


				btnbirharf.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {

						final String appPackageName = getPackageName();
						try {
							startActivity(new Intent(
									Intent.ACTION_VIEW,
									Uri.parse("https://play.google.com/store/apps/details?id=com.guessseries.emoji"
											+ appPackageName)));


							SharedPreferences.Editor prefs = getSharedPreferences(
									"userInfo", MODE_PRIVATE).edit();

							prefs.putInt("lastOne", 0);
							prefs.putInt("scor", ActivityGame.scor + 10);
							prefs.putInt("kontroloyla", kontroloyla=kontroloyla+1);
							prefs.commit();




							howto.setVisibility(Button.INVISIBLE);

							dialog.dismiss();


						} catch (android.content.ActivityNotFoundException anfe) {
							startActivity(new Intent(
									Intent.ACTION_VIEW,
									Uri.parse("https://play.google.com/store/apps/details?id=com.guessseries.emoji")));
						}

					}
				});



				Button btnkapat = (Button) dialog.findViewById(R.id.btn_kapat);


				btnkapat.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						// Close the dialog
						dialog.dismiss();
					}
				});

				// Display the dialog
				dialog.show();



			}
		});

	}



}