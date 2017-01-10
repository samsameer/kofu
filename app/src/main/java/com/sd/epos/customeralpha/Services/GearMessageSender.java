package com.sd.epos.customeralpha.Services;

import android.content.Context;
import android.content.Intent;

/**
 * Created by Jabbir on 11/03/2016.
 */
public class GearMessageSender  {
    public static final String GEAR_BIG_SPLIT ="#~#"  ;
    public static final String GEAR_SEGMENT_SPLIT ="~;"  ;

    public static final String GEAR_ACTION_SNOOZE = "snooze";
    public static final String GEAR_ACTION_CANCEL = "cancel";

    //this is for Gear to know how many actions to display
    public static final String GEAR_ACTION_BOTH = "both";


    Context mContext= null;

    public void sendReminder(Context context, String reminder){
        mContext = context;
        Intent sapService = new Intent(context, SAPServiceProvider.class);
        sapService.putExtra(SAPServiceProvider.EXTRA_PAY_LOAD, reminder);
        mContext.startService(sapService);
    }



    public static String buildMessage(String title, String shortDescription,
                                      String fullDescription, String tripGroupId,
                                      String mode, String offerAction ){
        String bigSplit = GEAR_BIG_SPLIT;
        String result="";
        result+= title + bigSplit + shortDescription + bigSplit;

        result += fullDescription + bigSplit + tripGroupId + bigSplit + mode + bigSplit+ offerAction;

        return result;


    }

}
