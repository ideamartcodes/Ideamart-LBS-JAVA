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


public class LbsSheduller {

    private static final Logger LOGGER = Logger.getLogger(LbsSheduller.class.getName());
    private ScheduledExecutorService executorService;
    private LbsResponseMessage lbsResponseMessage = new LbsResponseMessage();

    public void sendRequest(String msisdn) {

        try {
            final LbsRequestSender requestSender = new LbsRequestSender(new URL(getPropertyValue("url")));

            final LbsRequestMessage lbsRequestMessage = new LbsRequestMessage();
            lbsRequestMessage.setApplicationId("APP_ID");
            lbsRequestMessage.setPassword("PASSWORD");
            lbsRequestMessage.setSubscriberId(msisdn);
            lbsRequestMessage.setServiceType(LbsRequestMessage.ServiceType.IMMEDIATE);
            lbsRequestMessage.setResponseTime(LbsRequestMessage.ResponseTime.NO_DELAY);
            lbsRequestMessage.setFreshness(LbsRequestMessage.Freshness.HIGH);
            lbsRequestMessage.setHorizontalAccuracy("1000");

            if(executorService == null) {
                executorService = Executors.newScheduledThreadPool(1);
                executorService.scheduleAtFixedRate(new Runnable() {
                    public void run() {
                        try {

                            captureResponse(requestSender, lbsRequestMessage);

                        } catch(Exception e) {
                            e.printStackTrace();
                        }

                    }

                }, 4, 4, TimeUnit.SECONDS);
            }






        } catch (MalformedURLException e){
            LOGGER.log(Level.ALL, e.getMessage());
        }
    }

    private void captureResponse(LbsRequestSender requestSender, LbsRequestMessage lbsRequestMessage) {
        try{
            lbsResponseMessage = requestSender.sendLbsRequest(lbsRequestMessage);
            LOGGER.info("Starting application...["+lbsResponseMessage+"]");
        } catch (SdpException e) {
            e.printStackTrace();
        }
    }

    private String getPropertyValue(String key) {
        return PropertyLoader.getInstance().getText(key);
    }
}
