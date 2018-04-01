package com.mJunction.drm.serviceImpl;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.mJunction.drm.common.FormatDate;
import com.mJunction.drm.common.PropertyFileReaderService;
import com.mJunction.drm.common.exception.FileDeleteException;
import com.mJunction.drm.common.exception.FileReadException;
import com.mJunction.drm.common.exception.ReinitiateBidderException;
import com.mJunction.drm.common.validation.ErrorCodes;
import com.mJunction.drm.pojo.BidderPojo;
import com.mJunction.drm.pojo.DataTableObjectBidder;
import com.mJunction.drm.service.BidderService;
import com.mJunction.drm.utility.ReadDateFormatConfig;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.codec.binary.*;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Created by siddhartha.kumar on 4/11/2017.
 */

@Service
public class BidderServiceImpl implements BidderService{

    private static final Logger LOGGER = LoggerFactory.getLogger(BidderServiceImpl.class);

    @Autowired
    private PropertyFileReaderService propertyFileReaderService;

    @Override
    public BidderPojo populateBidderSync(BidderPojo bidderPojo){
        Properties prop = this.propertyFileReaderService.getProperty();
        if(!bidderPojo.getAuthTokenHtml().equalsIgnoreCase(bidderPojo.getInSessionAuthToken())){
            bidderPojo.addValidationError(ErrorCodes.AUTHENTICATION_FAILED.getCode(),
                    ErrorCodes.AUTHENTICATION_FAILED.getDescription(),"authentication","");
            return bidderPojo;
        }

        try {
            if ("Y".equalsIgnoreCase(bidderPojo.getCheckWebServiceOrDb()) ||
                    "N".equalsIgnoreCase(bidderPojo.getCheckWebServiceOrDb())) {

                List<BidderPojo>  listOfBidderSyncFiles = null;
                String orgFileName = "";
                File folder = new File(prop.getProperty("bidderSyncPath"));
                File[] listOfFiles = folder.listFiles();

                if(Objects.nonNull(listOfFiles) && listOfFiles.length > 0){
                    listOfBidderSyncFiles =  Arrays.stream(listOfFiles)
                            .filter(file->Objects.nonNull(file) && file.isFile())
                            .map(file->this.getBidderPojo(file.getName().split("_")))
                            .filter(bidderPojo1 -> Objects.nonNull(bidderPojo1))
                            .collect(Collectors.toList());
                }

                DataTableObjectBidder dataTableObjectBidder = new DataTableObjectBidder();
                dataTableObjectBidder.setAaData(listOfBidderSyncFiles);

                bidderPojo.setJsonStringForBidderSyncPopulation(new GsonBuilder().setPrettyPrinting().create()
                        .toJson(dataTableObjectBidder));
            }
        }catch (Exception e){
            LOGGER.error("[populateBidderSync] : Exception : ",e);
        }

        return bidderPojo;
    }

    @Override
    public BidderPojo reinitiateBidder(BidderPojo bidderPojo) throws FileNotFoundException, FileReadException, ReinitiateBidderException, FileDeleteException {


        BufferedReader bufferedReader = null;
        String fileName = null;
        try {
            Properties prop = this.propertyFileReaderService.getProperty();
            String reinitiateUrlBidder = prop.getProperty("reinitiateUrlBidder");

//        BufferedReader br = new BufferedReader(new FileReader(new File(
//                ReadDateFormatConfig.getFilePathBidderSync() + clientNameBidder
//                        + "_" + fileNameBidder + "_" + dateTimeBidder+"_"+formatTime+".xml")));

            fileName = new StringBuilder(prop.getProperty("bidderSyncPath"))
                    .append(bidderPojo.getClientName())
                    .append("_").append(bidderPojo.getFileName())
                    .append("_")
                    .append(bidderPojo.getDateTimeStamp())
                    .append("_")
                    .append(bidderPojo.getOnlyTimeStamp())
                    .append(".xml").toString();

            bufferedReader = new BufferedReader(new FileReader(new File(fileName)));

            StringBuilder sb = new StringBuilder();
            String line = "";

            while ((line = bufferedReader.readLine()) != null){
                sb.append(line.trim());
            }

            String newXml = sb.toString().replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();


           // authString = userId + ":" + password;
          //  authStringEnc = org.apache.commons.codec.binary.Base64.encodeBase64("user:password".getBytes());
          String authStringEnc = new String(org.apache.commons.codec.binary.Base64.encodeBase64("user:password".getBytes()));
          Client client1 = Client.create();
            WebResource webResource = client1.resource(reinitiateUrlBidder
                    + "bidder_sync_return");

            ClientResponse clientResponse = webResource.type("application/xml")
                    .header("Authorization", "Basic " + authStringEnc)
                    .post(ClientResponse.class, newXml);

            if (clientResponse.getStatus() != 200) {
                throw new ReinitiateBidderException("Failed : HTTP error code : "
                        + clientResponse.getStatus() + " Could Not Reinitiate Bidder");
            }





        }catch (IOException ioexcep){
            LOGGER.error("[reinitiateBidder] : IOException : ",ioexcep);
            throw new FileReadException("Unable to read the file :" + fileName);
        }finally {
            if(bufferedReader != null) try {
                bufferedReader.close();
            } catch (IOException e) {
                LOGGER.error("[reinitiateBidder] : Unable to close the Buffered Reader : Exception : ",e);
            }

            File fin = new File(fileName);

            try {
                FileUtils.forceDelete(fin);
            } catch (IOException e) {
                LOGGER.error("[reinitiateBidder] : Unable to delete file : [" + fileName + "] : Exception : ",e);
                throw new FileDeleteException("Unable to delete file : [" + fileName + "]");
            }
        }
        return bidderPojo;
    }

    @Override
    public BidderPojo viewXmlBidder(BidderPojo bidderPojo) throws FileNotFoundException, FileReadException {

        String fileName = "";
        BufferedReader bufferedReader = null;
        try {
            Properties prop = this.propertyFileReaderService.getProperty();
            fileName = new StringBuilder(prop.getProperty("bidderSyncPath"))
                    .append(bidderPojo.getClientName())
                    .append("_")
                    .append(bidderPojo.getFileName())
                    .append("_")
                    .append(bidderPojo.getDateTimeStamp())
                    .append("_")
                    .append(bidderPojo.getOnlyTimeStamp())
                    .append(".xml").toString();

            BufferedReader br = new BufferedReader(new FileReader(fileName));
            String line;
            StringBuilder sb = new StringBuilder();

            while ((line = br.readLine()) != null) {
                sb.append(line.trim());
            }


           String newXml = sb.toString().replaceAll("\\<\\?xml(.+?)\\?\\>", "").trim();
            bidderPojo.setJsonStringXmlBidder(new GsonBuilder().setPrettyPrinting().create().toJson(newXml));

        }catch (IOException ioexcep){
            LOGGER.error("[viewXmlBidder] : IOException : ",ioexcep);
            throw new FileReadException("Unable to read the file :" + fileName);
        }finally {
            if(bufferedReader != null) try {
                bufferedReader.close();
            } catch (IOException e) {
                LOGGER.error("[viewXmlBidder] : Unable to close the Buffered Reader : Exception : ",e);
            }
        }
        return bidderPojo;
    }

    /**
     * Utility Method for populateBidderSync for making a BidderPojo object from a String array
     * @param stringArr
     * @return
     */
    private static BidderPojo getBidderPojo(String[] stringArr){

        if(stringArr.length >= 4) {
            BidderPojo bidderPojo = new BidderPojo();
            bidderPojo.setClientName(stringArr[0]);
            bidderPojo.setFileName(stringArr[1]);
            bidderPojo.setDateTimeStamp(stringArr[2]);
            bidderPojo.setOnlyTimeStamp(stringArr[3].replace("-", ":"));
            return bidderPojo;
        }
        return null;
    }
}
