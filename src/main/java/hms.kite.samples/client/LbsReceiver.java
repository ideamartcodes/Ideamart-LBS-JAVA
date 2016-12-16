/*
 *   (C) Copyright Ideamart.
 */
package hms.kite.samples.client;

import hms.kite.samples.api.SdpException;
import hms.kite.samples.api.lbs.LbsRequestSender;
import hms.kite.samples.api.lbs.LbsRequestMessage;
import hms.kite.samples.api.lbs.LbsResponseMessage;
import hms.kite.samples.util.PropertyLoader;


import java.net.MalformedURLException;
import java.net.URL;
import java.util.Arrays;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

public class LbsReceiver  {

    private static final Logger LOGGER = Logger.getLogger(LbsReceiver.class.getName());

    public static void main (String[] args) throws Exception {
        String msisdn;
        LOGGER.info("Enter msisdn : ");
        Scanner in = new Scanner(System.in);
        msisdn = in.nextLine();

        LbsSheduller lbsSheduller = new LbsSheduller();
        lbsSheduller.sendRequest(msisdn);
    }

}
