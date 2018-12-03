/**
 * @ TabBarFragment
 */
package com.allpoint.activities.phone.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.PopupMenu;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.allpoint.R;
import com.allpoint.activities.tablet.fragments.SettingsFragment;
import com.allpoint.util.Constant;
import com.allpoint.util.Localization;
import com.allpoint.util.Utils;
import com.urbanairship.UAirship;
import com.urbanairship.richpush.RichPushInbox;

/**
 * TabBarFragment
 *
 * @author: Mikhail.Shalagin & Vyacheslav.Shmakin
 * @version: 23.09.13
 */
public class TabBarFragment extends Fragment implements RichPushInbox.Listener, SettingsFragment.SettingsChangeListener {

    protected Utils utils;

    private RelativeLayout messageCountLayout;
    private TextView numberOfMessagesText;
    private TextView mainMenuTrans;
    private TextView tTxtBottomHome;
    private TextView itxtBottomSearch;
    private TextView itxtBottomTransaction;
    private TextView itxtBottomMessages;
    private TextView itxtBottomMore;

    @Override
    public void onResume() {
        super.onResume();
        Utils.showMessageCounter(messageCountLayout, numberOfMessagesText);

        //UAirship.shared().getRichPushManager().addListener(this);

        tTxtBottomHome.setText(Localization.getMainMenuTabHome());
        itxtBottomSearch.setText(Localization.getMainMenuSearchTitle());
        itxtBottomMessages.setText(Localization.getMainMenuMessagesTitle());

        //History Change
        if (Constant.HISTORY_BUTTON_DISABLE) {
            itxtBottomTransaction.setText(Localization.getMainMenuAboutTitle());
            //set bottom bar image
            //iBtnBottomTransaction.setImageResource(R.drawable.bottom_about);

            // Manually set tab about drawable into xml

            //More Button Disable
            itxtBottomMore.setText(Localization.getMainMenuSettingsTitle());

        } else {
            itxtBottomTransaction.setText(Localization.getMainMenuTransTitle());
            //set bottom bar image
            //iBtnBottomTransaction.setImageResource(R.drawable.bottom_history);

            //More Button Enable
            itxtBottomMore.setText(Localization.getMainMenuTabMore());
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate((R.layout.bottom_bar_fragment), null);
        utils = Utils.getInstance();
        initGUIEelement(view);
        return view;
    }

    private void initGUIEelement(View view) {


        messageCountLayout = view.findViewById(R.id.layoutBarMessageCount);
        numberOfMessagesText = view.findViewById(R.id.tvBarNumberOfMessages);
        // ImageButtons start
        ImageButton iBtnBottomMore = view.findViewById(R.id.iBtnBottomMore);
        ImageButton iBtnBottomMessages = view.findViewById(R.id.iBtnBottomMessages);
        ImageButton iBtnBottomTransaction = view.findViewById(R.id.iBtnBottomTransaction);
        ImageButton iBtnBottomSearch = view.findViewById(R.id.iBtnBottomSearch);
        ImageButton iBtnBottomHome = view.findViewById(R.id.iBtnBottomHome);
        // ImageButtons end

        mainMenuTrans = view.findViewById(R.id.tvMenuTrans);
        itxtBottomMore = view.findViewById(R.id.iTxtBottomMore);
        tTxtBottomHome = view.findViewById(R.id.iTxtBottomHome);
        itxtBottomSearch = view.findViewById(R.id.iTxtBottomSearch);
        itxtBottomTransaction = view.findViewById(R.id.iTxtBottomTransaction);
        itxtBottomMessages = view.findViewById(R.id.iTxtBottomMessages);

        iBtnBottomTransaction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onIbtnBottomTransactionClicked();

            }
        });

        iBtnBottomMessages.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onIbtnBottomMessagesClicked();

            }
        });

        iBtnBottomSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onIbtnBottomSearchClicked();

            }
        });
        iBtnBottomHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onIbtnBottomHomeClicked();

            }
        });

        iBtnBottomMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                onIbtnBottomSettingsClicked();

            }
        });
    }

    @Override
    public void onPause() {
        //TODO check this
        UAirship.shared().getInbox().removeListener(this);
        super.onPause();
    }

    void onIbtnBottomTransactionClicked() {

        //History Change
        if (Constant.HISTORY_BUTTON_DISABLE) {

//			startActivity(new Intent(this.getActivity(),
//					com.allpoint.activities.phone.AboutActivity.class));

            Utils.startActivity(this.getActivity(),
                    com.allpoint.activities.phone.AboutActivity.class, false,
                    false, false);

        } else {

            startActivity(new Intent(this.getActivity(),
                    com.allpoint.activities.phone.CardListActivity.class));
        }


    }

    void onIbtnBottomMessagesClicked() {

        Utils.startActivity(this.getActivity(),
                com.allpoint.activities.phone.MessageActivity.class, false,
                false, false);
    }


    void onIbtnBottomSearchClicked() {

        Utils.startActivity(this.getActivity(),
                com.allpoint.activities.phone.MainActivity.class, false,
                false, false);
    }


    void onIbtnBottomHomeClicked() {
        Utils.startActivity(this.getActivity(),
                com.allpoint.activities.phone.MainMenuActivity.class, false,
                false, false);
    }


    void onIbtnBottomSettingsClicked() {

        if (Constant.HISTORY_BUTTON_DISABLE) {
//			 Utils.startActivity(this.getActivity(),
//			 com.allpoint.activities.phone.SettingsActivity.class, false, false,
//			 !Utils.isOnMainActivity());

            Utils.startActivity(this.getActivity(),
                    com.allpoint.activities.phone.SettingsActivity.class, false,
                    false, false);

        } else {
            showPopUpMenu();
        }


    }

    private void showPopUpMenu() {
        // TODO Auto-generated method stub
        // Utils.startActivity(this.getActivity(),
        // com.allpoint.activities.phone.SettingsActivity.class, false, false,
        // !Utils.isOnMainActivity());

        // Creating the instance of PopupMenu
        PopupMenu popup = new PopupMenu(getActivity(), itxtBottomMore);
        // Inflating the Popup using xml file

        if (Constant.HISTORY_BUTTON_DISABLE) {
            popup.getMenuInflater().inflate(R.menu.popup_menu_about, popup.getMenu());
        } else {
            popup.getMenuInflater().inflate(R.menu.popup_menu, popup.getMenu());
        }


        // registering popup with OnMenuItemClickListener
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getTitle().equals("About")) {
                    Utils.startActivity(getActivity(),
                            com.allpoint.activities.phone.AboutActivity.class,
                            false, false, false);
                } else {
                    Utils.startActivity(
                            getActivity(),
                            com.allpoint.activities.phone.SettingsActivity.class,
                            false, false, false);
                }

                return true;
            }
        });

        popup.show();// showing popup menu
    }

  /*  @Override
    public void onUpdateMessages(boolean isSuccess) {
        Utils.showMessageCounter(messageCountLayout, numberOfMessagesText);
    }

    @Override
    public void onUpdateUser(boolean b) {
    }*/

    @Override
    public void onSettingsChanged() {
        // TODO Auto-generated method stub
        tTxtBottomHome.setText(Localization.getMainMenuTabHome());
        itxtBottomSearch.setText(Localization.getMainMenuSearchTitle());
        itxtBottomTransaction.setText(Localization.getMainMenuTransTitle());
        itxtBottomMessages.setText(Localization.getMainMenuMessagesTitle());
        itxtBottomMore.setText(Localization.getMainMenuMessagesTitle());
    }

    @Override
    public void onSettingsShowed() {
        // TODO Auto-generated method stub

    }

    @Override
    public void onSettingsDismissed() {
        // TODO Auto-generated method stub

    }

    public void changeText() {
        // this textview should be bound in the fragment onCreate as a member
        // variable
        tTxtBottomHome.setText(Localization.getMainMenuTabHome());
        itxtBottomSearch.setText(Localization.getMainMenuSearchTitle());
        itxtBottomTransaction.setText(Localization.getMainMenuTransTitle());
        itxtBottomMessages.setText(Localization.getMainMenuMessagesTitle());
        itxtBottomMore.setText(Localization.getMainMenuTabMore());
    }

    @Override
    public void onInboxUpdated() {

        Utils.showMessageCounter(messageCountLayout, numberOfMessagesText);
    }

    // replace ua
    /*
     * @Override public void onRetrieveMessage(boolean b, String s) { }
     */
}
