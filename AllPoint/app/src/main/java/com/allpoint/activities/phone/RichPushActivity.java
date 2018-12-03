package com.allpoint.activities.phone;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import com.allpoint.R;
import com.allpoint.util.Constant;
import com.allpoint.util.Localization;
import com.allpoint.util.Utils;
import com.urbanairship.UAirship;
import com.urbanairship.richpush.RichPushMessage;
import com.urbanairship.widget.UAWebView;

/**
 * RichPushActivity
 *
 * @author: Vyacheslav.Shmakin
 * @version: 23.09.13
 */
public class RichPushActivity extends FragmentActivity {

    protected ImageButton btnMessages;
    protected TextView tvMessage;
    // replace ua
    protected UAWebView messageView;
    protected TextView messageViewTitle;


    protected void afterViews() {
        String messageID = getIntent().getStringExtra(Constant.RICH_PUSH_EXTRA);
        // replace RichPushInbox
        RichPushMessage msg = UAirship.shared().getInbox().getMessage(messageID);
        messageView.getSettings().setJavaScriptEnabled(true);
        messageView.loadRichPushMessage(msg);

        btnMessages.setImageResource(R.drawable.bottom_messages_press);
        tvMessage.setTextColor(getResources().getColor(R.color.textColor));
        btnMessages.setEnabled(false);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rich_push_message);
        initGUIElements();
        afterViews();

    }

    private void initGUIElements() {

        btnMessages = findViewById(R.id.iBtnBottomMessages);
        tvMessage = findViewById(R.id.iTxtBottomMessages);
        messageView = findViewById(R.id.wViewRichPushView);
        messageViewTitle = findViewById(R.id.tvMessageViewTitle);


    }

    @Override
    public void onStart() {
        super.onStart();
        Utils.startFlurry(this, Constant.RICH_PUSH_OPEN_EVENT);
    }

    @Override
    protected void onResume() {
        super.onResume();

        // MainMenu
        messageViewTitle.setText(Localization.getMessageLayoutTitle());
    }

    @Override
    public void onBackPressed() {
        Utils.startActivity(this, MessageActivity.class, false, false, true);
    }

    protected void onIbtnMessageViewBackClicked(View view) {
        onBackPressed();
    }

    public void onTvMessageViewTitleClick(View view) {
        onBackPressed();
    }
}
