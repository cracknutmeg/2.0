package com.allpoint.activities;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.allpoint.AtmFinderApplication;
import com.allpoint.R;
import com.allpoint.util.Constant;
import com.allpoint.util.Utils;

public class CardSuccessActivity extends Activity {

    ImageView cardIcon;
    TextView cardNoIs, cardValidMsg, cardTitleMsg, cardSuccesFail;

//	@ViewById(R.id.atmpassSignUpButton)
//	Button atmpassSignUp;

    String muskCard;

    AtmFinderApplication appContext;
    private TextView done_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.card_success);

        muskCard = getIntent().getStringExtra("card_number");
        appContext = (AtmFinderApplication) getApplicationContext();

        done_button = findViewById(R.id.done_button);
        cardNoIs = findViewById(R.id.txt_CardNo);
        cardValidMsg = findViewById(R.id.txtCardValidMsg);
        cardTitleMsg = findViewById(R.id.cardTitleMsg);
        cardIcon = findViewById(R.id.validCardIcon);
        cardSuccesFail = findViewById(R.id.cardSuccessFail);

        done_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onIbtnContinueClicked();

            }
        });
    }

    @SuppressWarnings("deprecation")
    @SuppressLint("NewApi")
    @Override
    protected void onStart() {
        if (!Utils.getLoginStatus()) {
            showDialogAlert("Login to Proceed");
        } else {
            cardNoIs.setText(muskCard);
        }
        if (appContext.isCardSuccess) {

            cardTitleMsg.setText(getResources()
                    .getString(R.string.card_success));
            cardSuccesFail.setText(getResources().getString(
                    R.string.card_success_msg));
            //atmpassSignUp.setVisibility(View.GONE);
            cardIcon.setVisibility(View.VISIBLE);
            cardValidMsg.setText(getResources().getString(R.string.success_message));
        } else {

            cardTitleMsg.setText(getResources().getString(
                    R.string.card_failue_title));
            cardSuccesFail.setText(getResources().getString(
                    R.string.card_fail_msg));
            cardSuccesFail.setTextColor(getResources().getColor(R.color.red));
            //atmpassSignUp.setVisibility(View.VISIBLE);
            cardIcon.setVisibility(View.VISIBLE);
            cardValidMsg.setText(getResources().getString(
                    R.string.failure_message));

            // cardIcon.setBackground(getResources().getDrawable(R.drawable.card_invalid));

            int sdk = android.os.Build.VERSION.SDK_INT;
            if (sdk < android.os.Build.VERSION_CODES.JELLY_BEAN) {
                cardIcon.setBackgroundDrawable(getResources().getDrawable(R.drawable.card_invalid));
            } else {
                cardIcon.setBackground(getResources().getDrawable(
                        R.drawable.card_invalid));
            }
        }
        super.onStart();
    }

    void onIbtnContinueClicked() {

        if (appContext.isCardSuccess) {

            if (Utils.getLoginStatus()) {

                //History Remove
                if (Constant.HISTORY_BUTTON_DISABLE) {

                    //showDialogHistoryDisable(getResources().getString(R.string.en_card_history_disable));
                    finish();

                } else {

                    if (Utils.isTablet()) {

                        startActivity(new Intent(
                                CardSuccessActivity.this,
                                com.allpoint.activities.tablet.CardListActivity.class));
                        finish();
                        // activityClass =
                        // com.allpoint.activities.tablet.CardListActivity.class;
                    } else {
                        startActivity(new Intent(
                                CardSuccessActivity.this,
                                com.allpoint.activities.phone.CardListActivity.class));
                        finish();

                    }
                    // Utils.startActivity(CardSuccessActivity.this, activityClass,
                    // false, false, true);

                }


            } else {
                Utils.showDialogAlert("User need to login first !",
                        CardSuccessActivity.this);
            }

        } else {
            finish();
        }

    }

	/*@Click(R.id.atmpassSignUpButton)
	void onIbtnATMPassSigunpClicked() {
		String url = Constant.ATMPASS_LINK;
		Intent i = new Intent(Intent.ACTION_VIEW);
		i.setData(Uri.parse(url));
		startActivity(i);
	}*/

    public void showDialogAlert(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(
                CardSuccessActivity.this).create();
        alertDialog.setMessage(message);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        Utils.startActivity(CardSuccessActivity.this,
                                LoginActivity.class, false, false, true);

                    }
                });
        alertDialog.show();
    }

    public void showDialogHistoryDisable(String message) {

        AlertDialog alertDialog = new AlertDialog.Builder(
                CardSuccessActivity.this).create();
        alertDialog.setMessage(message);
        alertDialog.setCancelable(false);

        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        finish();

                    }
                });
        alertDialog.show();
    }
}
