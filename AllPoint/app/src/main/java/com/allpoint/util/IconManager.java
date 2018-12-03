/**
 * @ com.allpoint.util.IconManager
 */
package com.allpoint.util;


import android.content.Context;

import com.allpoint.R;

import java.util.HashMap;
import java.util.Map;

/**
 * IconManager
 *
 * @author: Vyacheslav.Shmakin
 * @version: 08.01.14
 */
public class IconManager {
    private final Map<String, Integer> icons = new HashMap<>();
    private final Map<String, Integer> pins = new HashMap<>();

    public static IconManager getInstance(Context context) {
        return new IconManager();
    }

    public Integer getIconByName(final String name) {
        return icons.get(name);
    }

    public Integer getPinByName(final String name) {
        return pins.get(name);
    }

    public IconManager() {
        icons.put("711.png", R.drawable.retailer_seven_eleven);
        icons.put("costco.png", R.drawable.retailer_costco);
        icons.put("cvs.png", R.drawable.retailer_cvs);
        icons.put("hess.png", R.drawable.retailer_hess);
        icons.put("krogers.png", R.drawable.retailer_krogers);
        icons.put("kangaroo.png", R.drawable.retailer_kangaroo);
        icons.put("safeway.png", R.drawable.retailer_safeway);
        icons.put("sunoco.png", R.drawable.retailer_sunoco);
        icons.put("target.png", R.drawable.retailer_target);
        icons.put("walgreens.png", R.drawable.retailer_walgreens);
        icons.put("winn-dixie_logo.png", R.drawable.retailer_winndixie);

        //new map icon Added
        icons.put("qt.png", R.drawable.retailer_qt);
        icons.put("wegmans.png", R.drawable.retailer_wegmans);

        icons.put("chevron_logo.png", R.drawable.retailer_chevron);
        icons.put("dashin.png", R.drawable.retailer_dashin);

        icons.put("bi-lo_logo.png", R.drawable.retailer_bilo);
        icons.put("harveys_logo.png", R.drawable.retailer_harveys);

        //added 2017-08-30
        icons.put("riteaid_logo.png", R.drawable.retailer_riteaid);
        icons.put("getgo_logo.png", R.drawable.retailer_getgo);
        icons.put("speedway_logo.png", R.drawable.retailer_speedway);


        pins.put("711_pin.png", R.drawable.retailer_seven_eleven_pin);
        pins.put("costco_pin.png", R.drawable.retailer_costco_pin);
        pins.put("cvs_pin.png", R.drawable.retailer_cvs_pin);
        pins.put("hess_pin.png", R.drawable.retailer_hess_pin);
        pins.put("krogers_pin.png", R.drawable.retailer_krogers_pin);
        pins.put("kangaroo_pin.png", R.drawable.retailer_kangaroo_pin);
        pins.put("safeway_pin.png", R.drawable.retailer_safeway_pin);
        pins.put("sunoco_pin.png", R.drawable.retailer_sunoco_pin);
        pins.put("target_pin.png", R.drawable.retailer_target_pin);
        pins.put("walgreens_pin.png", R.drawable.retailer_walgreens_pin);
        pins.put("winndixie_pin.png", R.drawable.retailer_winndixie_pin);

        //new map icon pin Added
        pins.put("qt_pin.png", R.drawable.retailer_qt_pin);
        pins.put("wegmans_pin.png", R.drawable.retailer_wegmans_pin);

        pins.put("chevron_pin.png", R.drawable.retailer_chevron_pin);
        pins.put("dashin_pin.png", R.drawable.retailer_dashin_pin);

        pins.put("bilo_pin.png", R.drawable.retailer_bilo_pin);
        pins.put("harveys_pin.png", R.drawable.retailer_harveys_pin);

        //added 2017-08-30
        pins.put("riteaid_pin.png", R.drawable.retailer_riteaid_pin);
        pins.put("getgo_pin.png", R.drawable.retailer_getgo_pin);
        pins.put("speedway_pin.png", R.drawable.retailer_speedway_pin);

    }
}
